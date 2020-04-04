/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Worker for NMS
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 25.
 * @modified 2016. 1. 25.
 * @author cchyun
 */
public class Worker4NMS implements Worker4NMSIf {

	private static int resource_current_period = 2 * 1000;

	private static int resource_history_period = 60 * 1000;

	private static int resource_history_count = 60;

	private Sigar sigar;

	private static final BlackBox blackBox = new BlackBox(Worker4NMS.class);

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4NMSIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		this.sigar = new Sigar();

		Timer timer = new Timer("Worker4NMS:Timer:EMS");
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				EmpContext context = new EmpContext(Worker4NMS.this);
				try {
					checkResource(context);
				} catch (Exception e) {
					if (blackBox.isEnabledFor(LEVEL.Fatal)) {
						blackBox.log(LEVEL.Fatal, null, e);
					}
				} finally {
					context.close();
				}
			}
		}, 0, resource_current_period);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	private static String CACHE_CURRENT_KEY = UtilString.format("{}.{}", Worker4NMS.class.getName(), "resource_current");
	private static String CACHE_HISTORY_KEY = UtilString.format("{}.{}", Worker4NMS.class.getName(), "resource_history");
	static {
		UtilCache.buildCache(CACHE_CURRENT_KEY, resource_history_period / resource_current_period, resource_history_period / 1000);
		UtilCache.buildCache(CACHE_HISTORY_KEY, resource_history_count, resource_history_count * resource_history_period / 1000);
	}

	private Model4ResourceNMS resource_nms;

	private void checkResource(EmpContext context) {
		try {
			Model4ResourceNMS resource_nms = new Model4ResourceNMS();
			resource_nms.setCollect_time(new Date());

			CpuInfo[] cpus = sigar.getCpuInfoList();
			resource_nms.setCpu_count(cpus.length);
			for (CpuInfo cpu : cpus) {
				resource_nms.setCpu_model(cpu.getModel());
				resource_nms.setCpu_clock(Math.max(resource_nms.getCpu_clock(), cpu.getMhz()));
			}
			CpuPerc cpu_perc = sigar.getCpuPerc();
			resource_nms.setCpu_usage((1 - cpu_perc.getIdle()) * 100);

			Mem mem = sigar.getMem();
			resource_nms.setMem_total(mem.getRam());
			resource_nms.setMem_used(mem.getUsed());
			resource_nms.setMem_usage(mem.getUsedPercent());

			FileSystem[] file_systems = sigar.getFileSystemList();
			for (FileSystem file_system : file_systems) {
				if (file_system.getType() == FileSystem.TYPE_LOCAL_DISK) {
					FileSystemUsage file_system_usage = sigar.getFileSystemUsage(file_system.getDirName());
					if (file_system_usage != null) {
						resource_nms.addFile_system(file_system.getDirName(), file_system_usage.getTotal(), file_system_usage.getUsed(), file_system_usage.getUsePercent());
					}
				}
			}

			for (String net_interface_name : sigar.getNetInterfaceList()) {
				NetInterfaceConfig net_interface_config = sigar.getNetInterfaceConfig(net_interface_name);
				if (net_interface_config != null && net_interface_config.getType().equalsIgnoreCase("Ethernet") && !net_interface_config.getAddress().equals("0.0.0.0")) {
					NetInterfaceStat net_interface_stat = sigar.getNetInterfaceStat(net_interface_name);
					if (net_interface_stat != null) {
						resource_nms.addNet_interface(net_interface_name, net_interface_config.getDescription(), net_interface_config.getHwaddr(), net_interface_config.getAddress(), net_interface_stat.getSpeed(), net_interface_stat.getRxBytes(), net_interface_stat.getTxBytes());
					}
				}
			}

			this.resource_nms = resource_nms.toDiff(this.resource_nms);
			UtilCache.put(CACHE_CURRENT_KEY, resource_nms.getCollect_time().getTime(), this.resource_nms);
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e);
			}
		}
	}

	@Override
	public Model4ResourceNMS createResourceNMS(EmpContext context, Date collect_time) throws EmpException {
		Model4ResourceNMS resource_nms = Model4ResourceNMS.createResourceNMS(collect_time, UtilCache.getAll(CACHE_CURRENT_KEY).values().toArray(new Model4ResourceNMS[0]), 60000);
		if (resource_nms != null) {
			UtilCache.put(CACHE_HISTORY_KEY, resource_nms.getCollect_time().getTime(), resource_nms);
		}
		return resource_nms;
	}

	@Override
	public Model4ResourceNMS[] queryListResourceNMS(EmpContext context) throws EmpException {
		ConcurrentMap<Comparable<?>, Object> history_map = UtilCache.getAll(CACHE_HISTORY_KEY);
		Map<Long, Model4ResourceNMS> return_map = new TreeMap<Long, Model4ResourceNMS>();
		for (Object value : history_map.values()) {
			Model4ResourceNMS resource_nms = (Model4ResourceNMS) value;
			return_map.put(resource_nms.getCollect_time().getTime(), resource_nms);
		}
		return return_map.values().toArray(new Model4ResourceNMS[0]);
	}

}
