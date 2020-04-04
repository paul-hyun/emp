/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.link;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Network Link
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 25.
 * @modified 2016. 1. 25.
 * @author cchyun
 *
 */
public class Dao4NetworkLink implements Dao4NetworkLinkIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ID_KEY = UtilString.format("{}.{}", Dao4NetworkLink.class.getName(), "ne_id");
	static {
		UtilCache.buildCache(CACHE_ID_KEY, 256, 300);
	}

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4NetworkLinkIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NetworkLink createNetworkLink(EmpContext context, Model4NetworkLink network_link) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_group_id_from", network_link.getNe_group_id_from());
		parameter.put("ne_id_from", network_link.getNe_id_from());
		parameter.put("ne_group_id_to", network_link.getNe_group_id_to());
		parameter.put("ne_id_to", network_link.getNe_id_to());

		Model4NetworkLink exists = (Model4NetworkLink) driver4Mybatis.selectOne(context, getDefine_class(), "queryNetworkLink", parameter);
		if (exists != null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.LINK);
		}

		network_link.setCreator(context.getUser_account());
		driver4Mybatis.insert(context, getDefine_class(), "createNetworkLink", network_link);

		int network_link_id = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrval_network_link_id", network_link);
		network_link.setNetwork_link_id(network_link_id);

		clearCache();
		return queryNetworkLink(context, network_link_id);
	}

	@Override
	public Model4NetworkLink queryNetworkLink(EmpContext context, int network_link_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("network_link_id", network_link_id);

		Model4NetworkLink network_link = (Model4NetworkLink) driver4Mybatis.selectOne(context, getDefine_class(), "queryNetworkLink", parameter);
		if (network_link == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.LINK, network_link_id);
		}

		return network_link;
	}

	@Override
	public Model4NetworkLink[] queryListNetworkLink(EmpContext context, int startNo, int count) throws EmpException {
		Model4NetworkLink[] network_links = (Model4NetworkLink[]) UtilCache.get(CACHE_ID_KEY, context.getUser_session_id());

		if (network_links == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("startNo", startNo);
			parameter.put("count", count);

			List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNetworkLink", parameter);
			network_links = list.toArray(new Model4NetworkLink[0]);
			UtilCache.put(CACHE_ID_KEY, context.getUser_session_id(), network_links);
		}
		return network_links;
	}

	@Override
	public Model4NetworkLink deleteNetworkLink(EmpContext context, int network_link_id) throws EmpException {
		Model4NetworkLink network_link = queryNetworkLink(context, network_link_id);
		if (network_link == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.LINK, network_link_id);
		}

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("network_link_id", network_link_id);

		@SuppressWarnings("unused")
		int count = driver4Mybatis.delete(context, getDefine_class(), "deleteNetworkLink", parameter);

		clearCache();
		return network_link;
	}

	@Override
	public long queryCurrUpdate_seq_network(EmpContext context) throws EmpException {
		long update_seq_network = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrUpdate_seq_network", null);
		return update_seq_network;
	}

	@Override
	public long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		long update_seq_network = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryNextUpdate_seq_network", null);
		return update_seq_network;
	}

	protected void clearCache() {
		UtilCache.removeAll(CACHE_ID_KEY);
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();

		@SuppressWarnings("unused")
		int count = (Integer) driver4Mybatis.delete(context, getDefine_class(), "truncate", parameter);
	}

}
