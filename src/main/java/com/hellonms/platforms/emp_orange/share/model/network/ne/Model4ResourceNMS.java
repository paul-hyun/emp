/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_util.number.UtilNumber;

/**
 * <p>
 * NMS Resource
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 25.
 * @modified 2016. 1. 25.
 * @author cchyun
 */
@SuppressWarnings("serial")
public class Model4ResourceNMS implements ModelIf {

	public class FileSystem implements Serializable {
		public String file_system_name;
		public long file_system_size;
		public long file_system_used;
		public double file_system_usage;

		public FileSystem(String file_system_name, long file_system_size, long file_system_used, double file_system_usage) {
			this.file_system_name = file_system_name;
			this.file_system_size = file_system_size;
			this.file_system_used = file_system_used;
			this.file_system_usage = file_system_usage;
		}
	}

	public class NetInterface implements Serializable {
		public String net_interface_name;
		public String net_interface_description;
		public String net_interface_hwaddr;
		public String net_interface_address;
		public long net_interface_speed;
		public long net_interface_rx_bytes;
		public long net_interface_rx_bytes_diff;
		public long net_interface_tx_bytes;
		public long net_interface_tx_bytes_diff;

		public NetInterface(String net_interface_name, String net_interface_description, String net_interface_hwaddr, String net_interface_address, long net_interface_speed, long net_interface_rx_bytes, long net_interface_tx_bytes) {
			this.net_interface_name = net_interface_name;
			this.net_interface_description = net_interface_description;
			this.net_interface_hwaddr = net_interface_hwaddr;
			this.net_interface_address = net_interface_address;
			this.net_interface_speed = net_interface_speed;
			this.net_interface_rx_bytes = net_interface_rx_bytes;
			this.net_interface_rx_bytes_diff = 0L;
			this.net_interface_tx_bytes = net_interface_tx_bytes;
			this.net_interface_tx_bytes_diff = 0L;
		}

		public String net_interface_bps_display(long value) {
			long speed = net_interface_speed;
			long div = 1;
			while (1000 < speed) {
				div *= 1000;
				speed /= 1000;
			}
			double rx_bps_diff = value * 8 / div;

			StringBuilder stringBuilder = new StringBuilder();
			if (rx_bps_diff < 1000) {
				stringBuilder.append(" ");
			}
			if (rx_bps_diff < 100) {
				stringBuilder.append(" ");
			}
			if (rx_bps_diff < 10) {
				stringBuilder.append(" ");
			}
			stringBuilder.append(UtilNumber.format(rx_bps_diff));
			stringBuilder.append(" ");
			if (div == 1000L) {
				stringBuilder.append("K");
			} else if (div == 1000000L) {
				stringBuilder.append("M");
			} else if (div == 1000000000L) {
				stringBuilder.append("G");
			} else if (div == 1000000000000L) {
				stringBuilder.append("T");
			}
			return stringBuilder.toString();
		}
	}

	public static Model4ResourceNMS createResourceNMS(Date collect_time, Model4ResourceNMS[] resource_nmss, int period) {
		Model4ResourceNMS resource_nms = new Model4ResourceNMS();
		int total_count = 0;
		for (Model4ResourceNMS aaa : resource_nmss) {
			if (collect_time.getTime() <= aaa.getCollect_time().getTime() + period) {
				resource_nms.cpu_model = aaa.cpu_model;
				resource_nms.cpu_count = aaa.cpu_count;
				resource_nms.cpu_clock = aaa.cpu_clock;
				resource_nms.cpu_usage += aaa.cpu_usage;
				resource_nms.mem_total = aaa.mem_total;
				resource_nms.mem_used = aaa.mem_used;
				resource_nms.mem_usage += aaa.mem_usage;
				for (FileSystem bbb : aaa.file_system_map.values()) {
					FileSystem file_system = resource_nms.file_system_map.get(bbb.file_system_name);
					if (file_system == null) {
						resource_nms.addFile_system(bbb.file_system_name, bbb.file_system_size, bbb.file_system_used, 0);
						file_system = resource_nms.file_system_map.get(bbb.file_system_name);
					}
					file_system.file_system_usage += bbb.file_system_usage;
				}
				for (NetInterface bbb : aaa.net_interface_map.values()) {
					NetInterface net_interface = resource_nms.net_interface_map.get(bbb.net_interface_name);
					if (net_interface == null) {
						resource_nms.addNet_interface(bbb.net_interface_name, bbb.net_interface_description, bbb.net_interface_hwaddr, bbb.net_interface_address, bbb.net_interface_speed, 0, 0);
						net_interface = resource_nms.net_interface_map.get(bbb.net_interface_name);
					}
					net_interface.net_interface_rx_bytes = Math.max(net_interface.net_interface_rx_bytes, bbb.net_interface_rx_bytes);
					net_interface.net_interface_rx_bytes_diff += bbb.net_interface_rx_bytes_diff;
					net_interface.net_interface_tx_bytes = Math.max(net_interface.net_interface_tx_bytes, bbb.net_interface_tx_bytes);
					net_interface.net_interface_tx_bytes_diff += bbb.net_interface_tx_bytes_diff;
				}
				total_count++;
			}
		}
		if (0 < total_count) {
			resource_nms.cpu_usage /= total_count;
			resource_nms.mem_usage /= total_count;
			for (FileSystem file_system : resource_nms.file_system_map.values()) {
				file_system.file_system_usage /= total_count;
			}
			resource_nms.setCollect_time(collect_time);
			return resource_nms;
		} else {
			return null;
		}
	}

	private String cpu_model;

	private int cpu_count;

	private int cpu_clock;

	private double cpu_usage;

	private long mem_total;

	private long mem_used;

	private double mem_usage;

	private Map<String, FileSystem> file_system_map = new LinkedHashMap<String, FileSystem>();

	private Map<String, NetInterface> net_interface_map = new LinkedHashMap<String, NetInterface>();

	private Date collect_time;

	public String getCpu_model() {
		return cpu_model;
	}

	public void setCpu_model(String cpu_model) {
		this.cpu_model = cpu_model;
	}

	public int getCpu_count() {
		return cpu_count;
	}

	public void setCpu_count(int cpu_count) {
		this.cpu_count = cpu_count;
	}

	public int getCpu_clock() {
		return cpu_clock;
	}

	public void setCpu_clock(int cpu_clock) {
		this.cpu_clock = cpu_clock;
	}

	public double getCpu_usage() {
		return cpu_usage;
	}

	public void setCpu_usage(double cpu_usage) {
		this.cpu_usage = cpu_usage;
	}

	public String getCpu_usage_display() {
		StringBuilder stringBuilder = new StringBuilder();
		if (cpu_usage < 100) {
			stringBuilder.append(" ");
		}
		if (cpu_usage < 10) {
			stringBuilder.append(" ");
		}
		stringBuilder.append(UtilNumber.format(cpu_usage));
		stringBuilder.append(" %");
		return stringBuilder.toString();
	}

	public long getMem_total() {
		return mem_total;
	}

	public void setMem_total(long mem_total) {
		this.mem_total = mem_total;
	}

	public long getMem_used() {
		return mem_used;
	}

	public void setMem_used(long mem_used) {
		this.mem_used = mem_used;
	}

	public double getMem_usage() {
		return mem_usage;
	}

	public void setMem_usage(double mem_usage) {
		this.mem_usage = mem_usage;
	}

	public String getMem_usage_display() {
		StringBuilder stringBuilder = new StringBuilder();
		if (mem_usage < 100) {
			stringBuilder.append(" ");
		}
		if (mem_usage < 10) {
			stringBuilder.append(" ");
		}
		stringBuilder.append(UtilNumber.format(mem_usage));
		stringBuilder.append(" %");
		return stringBuilder.toString();
	}

	public void addFile_system(String file_system_name, long file_system_size, long file_system_used, double file_system_usage) {
		file_system_map.put(file_system_name, new FileSystem(file_system_name, file_system_size, file_system_used, file_system_usage));
	}

	public FileSystem[] getFile_systems() {
		return file_system_map.values().toArray(new FileSystem[0]);
	}

	public void addNet_interface(String net_interface_name, String net_interface_description, String net_interface_hwaddr, String net_interface_address, long net_interface_speed, long net_interface_rx_bytes, long net_interface_tx_bytes) {
		net_interface_map.put(net_interface_name, new NetInterface(net_interface_name, net_interface_description, net_interface_hwaddr, net_interface_address, net_interface_speed, net_interface_rx_bytes, net_interface_tx_bytes));
	}

	public NetInterface[] getNet_interfaces() {
		return net_interface_map.values().toArray(new NetInterface[0]);
	}

	public Date getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Date collect_time) {
		this.collect_time = collect_time;
	}

	@Override
	public Model4ResourceNMS copy() {
		Model4ResourceNMS model = new Model4ResourceNMS();
		return model;
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("cpu_model").append(S_DL).append(cpu_model).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("cpu_count").append(S_DL).append(cpu_count).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("cpu_clock").append(S_DL).append(cpu_clock).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("cpu_usage").append(S_DL).append(cpu_usage).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("mem_total").append(S_DL).append(mem_total).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("mem_used").append(S_DL).append(mem_used).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("mem_usage").append(S_DL).append(mem_usage).append(S_NL);
		for (FileSystem file_system : file_system_map.values()) {
			stringBuilder.append(indent).append(S_TB).append("file_system").append(S_DL).append(file_system.file_system_name).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("size").append(S_DL).append(file_system.file_system_size).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("used").append(S_DL).append(file_system.file_system_used).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("usage").append(S_DL).append(file_system.file_system_usage).append(S_NL);
		}
		for (NetInterface net_interface : net_interface_map.values()) {
			stringBuilder.append(indent).append(S_TB).append("file_system").append(S_DL).append(net_interface.net_interface_name).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("description").append(S_DL).append(net_interface.net_interface_description).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("hwaddr").append(S_DL).append(net_interface.net_interface_hwaddr).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("address").append(S_DL).append(net_interface.net_interface_address).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("speed").append(S_DL).append(net_interface.net_interface_speed).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("rx_bytes").append(S_DL).append(net_interface.net_interface_rx_bytes).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("rx_bytes_diff").append(S_DL).append(net_interface.net_interface_rx_bytes_diff).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("tx_bytes").append(S_DL).append(net_interface.net_interface_tx_bytes).append(S_NL);
			stringBuilder.append(indent).append(S_TB).append(S_TB).append("tx_bytes_diff").append(S_DL).append(net_interface.net_interface_tx_bytes_diff).append(S_NL);
		}
		stringBuilder.append(indent).append(S_TB).append("collect_time").append(S_DL).append(collect_time).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

	public Model4ResourceNMS toDiff(Model4ResourceNMS resource_nms) {
		if (resource_nms != null) {
			for (NetInterface net_interface : net_interface_map.values()) {
				NetInterface net_interface_prev = resource_nms.net_interface_map.get(net_interface.net_interface_name);
				if (net_interface_prev != null) {
					if (net_interface_prev.net_interface_rx_bytes < net_interface.net_interface_rx_bytes) {
						net_interface.net_interface_rx_bytes_diff = net_interface.net_interface_rx_bytes - net_interface_prev.net_interface_rx_bytes;
					}
					if (net_interface_prev.net_interface_tx_bytes < net_interface.net_interface_tx_bytes) {
						net_interface.net_interface_tx_bytes_diff = net_interface.net_interface_tx_bytes - net_interface_prev.net_interface_tx_bytes;
					}
				}
			}
		}
		return this;
	}

}
