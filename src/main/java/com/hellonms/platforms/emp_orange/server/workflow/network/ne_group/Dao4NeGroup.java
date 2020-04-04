/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_group;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.DaoIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.driver.mybatis.Driver4MybatisIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;
import com.hellonms.platforms.emp_util.cache.UtilCache;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * NeGroup Dao
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 19.
 * @modified 2015. 3. 19.
 * @author cchyun
 * 
 */
public class Dao4NeGroup implements Dao4NeGroupIf {

	protected Driver4MybatisIf driver4Mybatis;

	private static String CACHE_ID_KEY = UtilString.format("{}.{}", Dao4NeGroup.class.getName(), "ne_group_id");
	private static String CACHE_TREE_KEY = UtilString.format("{}.{}", Dao4NeGroup.class.getName(), "ne_group_tree");
	static {
		UtilCache.buildCache(CACHE_ID_KEY, 256, 300);
		UtilCache.buildCache(CACHE_TREE_KEY, 256, 300);
	}

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Dao4NeGroup.class);

	/**
	 * Lock 객체 (C/U/D)
	 */
	private final ReentrantLock lock = new ReentrantLock();

	@Override
	public Class<? extends DaoIf> getDefine_class() {
		return Dao4NeGroupIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		driver4Mybatis = (Driver4MybatisIf) WorkflowMap.getDriver(Driver4MybatisIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NeGroup createNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		try {
			lock.lock();

			// 상위그룹 존재여부 확인
			Model4NeGroup parent = queryNeGroup(context, ne_group.getParent_ne_group_id());

			// 2. 동일한 이름 존재여부 확인
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.clear();
			parameter.put("parent_ne_group_id", ne_group.getParent_ne_group_id());
			parameter.put("ne_group_name", ne_group.getNe_group_name());

			Model4NeGroup exists_group = (Model4NeGroup) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeGroupByName", parameter);
			if (exists_group != null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group.getNe_group_name());
			}

			parameter.clear();
			parameter.put("left_bound", parent.getRight_bound());
			parameter.put("right_bound", parent.getRight_bound());
			parameter.put("offset_bound", 2);
			driver4Mybatis.update(context, getDefine_class(), "shiftLeftBound", parameter);
			driver4Mybatis.update(context, getDefine_class(), "shiftRightBound", parameter);

			ne_group.setLeft_bound(parent.getRight_bound());
			ne_group.setRight_bound(parent.getRight_bound() + 1);
			ne_group.setCreator(context.getUser_account());
			driver4Mybatis.insert(context, getDefine_class(), "createNeGroup", ne_group);

			int ne_group_id = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCurrval_ne_group_id", ne_group);
			ne_group.setNe_group_id(ne_group_id);

			clearCache();
			return queryNeGroup(context, ne_group_id);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Model4NeGroup queryNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		Model4NeGroup ne_group = (Model4NeGroup) UtilCache.get(CACHE_ID_KEY, ne_group_id);

		if (ne_group == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("ne_group_id", ne_group_id);

			ne_group = (Model4NeGroup) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeGroup", parameter);
			if (ne_group == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group_id);
			}
			UtilCache.put(CACHE_ID_KEY, ne_group_id, ne_group);
		}
		return ne_group.copy();
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeGroup", parameter);

		return list.toArray(new Model4NeGroup[0]);
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("startNo", startNo);
		parameter.put("count", count);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeGroup", parameter);

		return list.toArray(new Model4NeGroup[0]);
	}

	@Override
	public Model4NeGroup[] queryAllNeGroup(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startNo", 0);
		parameter.put("count", 999999);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeGroup", parameter);

		return list.toArray(new Model4NeGroup[0]);
	}

	@Override
	public Model4NeGroup[] queryAllNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("ne_group_id", ne_group_id);
		parameter.put("startNo", 0);
		parameter.put("count", 999999);

		List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeGroup", parameter);

		return list.toArray(new Model4NeGroup[0]);
	}

	@Override
	public TreeNode4NeGroup queryTreeNeGroup(EmpContext context) throws EmpException {
		int user_id = (context.getUser_id() != 0) ? context.getUser_id() : 0;
		TreeNode4NeGroup tree = (TreeNode4NeGroup) UtilCache.get(CACHE_TREE_KEY, user_id);

		if (tree == null) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			if (context.getUser_id() != 0) {
				parameter.put("user_id", context.getUser_id());
			}
			parameter.put("startNo", 0);
			parameter.put("count", 999999);

			List<?> list = driver4Mybatis.selectList(context, getDefine_class(), "queryListNeGroup", parameter);

			// 1. 최 상위 tree
			tree = new TreeNode4NeGroup();
			tree.setNe_group(queryNeGroup(context, Model4NeGroup.ROOT_NE_GROUP_ID));
			tree.setManage_ne_group(false);
			Map<Integer, TreeNode4NeGroup> tree_map = new LinkedHashMap<Integer, TreeNode4NeGroup>();
			tree_map.put(Model4NeGroup.ROOT_NE_GROUP_ID, tree);

			// 2. 개별 처리
			for (Object aaa : list) {
				Model4NeGroup ne_group = (Model4NeGroup) aaa;
				TreeNode4NeGroup node_this = tree_map.get(ne_group.getNe_group_id());
				if (node_this == null) {
					node_this = new TreeNode4NeGroup();
					node_this.setNe_group(ne_group);
					node_this.setManage_ne_group(true);
					tree_map.put(ne_group.getNe_group_id(), node_this);
				} else {
					node_this.setNe_group(ne_group);
					node_this.setManage_ne_group(true);
				}

				TreeNode4NeGroup node_parent = tree_map.get(ne_group.getParent_ne_group_id());
				if (node_parent == null) {
					node_parent = newVirtualTreeNode(context, tree_map, ne_group.getParent_ne_group_id());
				}
				if (node_this.getNe_group_id() != node_parent.getNe_group_id()) {
					node_parent.addChild(node_this);
				}
			}

			UtilCache.put(CACHE_TREE_KEY, user_id, tree);
		}
		return tree.copy();
	}

	/**
	 * <p>
	 * 관리되지 않는 TreeNode를 생성한다.
	 * </p>
	 * 
	 * @param context
	 * @param tree_map
	 * @param ne_group_id
	 * @return
	 * @throws EmpException
	 */
	private TreeNode4NeGroup newVirtualTreeNode(EmpContext context, Map<Integer, TreeNode4NeGroup> tree_map, int ne_group_id) throws EmpException {
		Model4NeGroup ne_group = queryNeGroup(context, ne_group_id);
		TreeNode4NeGroup node_this = new TreeNode4NeGroup();
		node_this.setNe_group(ne_group);
		node_this.setManage_ne_group(false);
		tree_map.put(ne_group_id, node_this);

		TreeNode4NeGroup node_parent = tree_map.get(ne_group.getParent_ne_group_id());
		if (node_parent == null) {
			node_parent = newVirtualTreeNode(context, tree_map, ne_group.getParent_ne_group_id());
		}
		node_parent.addChild(node_this);

		return node_this;
	}

	@Override
	public int queryCountNeGroup(EmpContext context) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountNeGroup", parameter);

		return count;
	}

	@Override
	public int queryCountNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (context.getUser_id() != 0) {
			parameter.put("user_id", context.getUser_id());
		}
		parameter.put("ne_group_id", ne_group_id);

		int count = (Integer) driver4Mybatis.selectOne(context, getDefine_class(), "queryCountNeGroup", parameter);

		return count;
	}

	@Override
	public Model4NeGroup updateNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		try {
			lock.lock();

			// 1. 존재여부 확인
			Model4NeGroup exists = queryNeGroup(context, ne_group.getNe_group_id());
			if (exists == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group.getNe_group_id());
			}
			if (!exists.getAccess().isUpdate()) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYUPDATE, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group.getNe_group_name());
			}

			if (ne_group.getParent_ne_group_id() != exists.getParent_ne_group_id()) { // 이동인 경우
				try {
					Map<String, Object> parameter = new HashMap<String, Object>();

					// 1. PARENT NE그룹 존재여부 확인
					Model4NeGroup parent = queryNeGroup(context, ne_group.getParent_ne_group_id());

					// 2. 동일한 이름 존재여부 확인
					parameter.clear();
					parameter.put("parent_ne_group_id", ne_group.getParent_ne_group_id());
					parameter.put("ne_group_name", ne_group.getNe_group_name());

					Model4NeGroup duplicate = (Model4NeGroup) driver4Mybatis.selectOne(context, getDefine_class(), "queryNeGroupByName", parameter);
					if (duplicate != null) {
						throw new EmpException(ERROR_CODE_ORANGE.MODEL_ALREADYEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group.getNe_group_name());
					}

					int old_left_bound = exists.getLeft_bound();
					int old_right_bound = exists.getRight_bound();
					int old_offset_bound = old_right_bound - old_left_bound + 1;
					@SuppressWarnings("unused")
					int parent_left_bound = parent.getLeft_bound();
					int parent_right_bound = parent.getRight_bound();
					int move_offset_bound = parent_right_bound - old_left_bound;

					// 2.1. 이동할 정보 reverse
					parameter.clear();
					parameter.put("left_bound", old_left_bound);
					parameter.put("right_bound", old_right_bound);
					driver4Mybatis.update(context, getDefine_class(), "reverseBounds", parameter);

					// 2.2. 이전공간 제거
					parameter.clear();
					parameter.put("left_bound", old_right_bound);
					parameter.put("right_bound", old_right_bound);
					parameter.put("offset_bound", -old_offset_bound);
					driver4Mybatis.update(context, getDefine_class(), "shiftLeftBound", parameter);
					driver4Mybatis.update(context, getDefine_class(), "shiftRightBound", parameter);

					// 2.2.1. 이전 공간 제거에 따른 값 수정
					if (old_right_bound < parent_right_bound) {
						parent_left_bound -= old_offset_bound;
						parent_right_bound -= old_offset_bound;
						move_offset_bound -= old_offset_bound;
					}

					// 2.3. 이동할 자리 확보
					parameter.clear();
					parameter.put("left_bound", parent_right_bound);
					parameter.put("right_bound", parent_right_bound);
					parameter.put("offset_bound", old_offset_bound);
					driver4Mybatis.update(context, getDefine_class(), "shiftLeftBound", parameter);
					driver4Mybatis.update(context, getDefine_class(), "shiftRightBound", parameter);

					// 2.4. 이동
					parameter.clear();
					parameter.put("left_bound", old_left_bound);
					parameter.put("right_bound", old_right_bound);
					parameter.put("offset_bound", move_offset_bound);
					driver4Mybatis.update(context, getDefine_class(), "moveReversedBounds", parameter);

					parameter.clear();
					parameter.put("ne_group_id", ne_group.getNe_group_id());
					parameter.put("parent_ne_group_id", ne_group.getParent_ne_group_id());
					parameter.put("updater", context.getUser_account());
					driver4Mybatis.update(context, getDefine_class(), "moveNeGroup", parameter);
				} finally {
					clearCache(); // move 로직을 수행하면 성공여부와 상관없이 cache를 초기화 한다.
				}
			}

			// 1.1. 존재하면 정보 수정
			ne_group.setUpdater(context.getUser_account());
			ne_group.setUpdate_time(new Date());
			@SuppressWarnings("unused")
			int count = driver4Mybatis.update(context, getDefine_class(), "updateNeGroup", ne_group);

			clearCache();
			return queryNeGroup(context, ne_group.getNe_group_id());
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Model4NeGroup updateNeGroupMapLocation(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		Model4NeGroup exists = queryNeGroup(context, ne_group.getNe_group_id());
		if (exists == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group.getNe_group_id());
		}
		// read only라도 좌표는 수정할 수 있음.
		// if (!exists.getAccess().isUpdate()) {
		// throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYUPDATE, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group.getNe_group_name());
		// }

		@SuppressWarnings("unused")
		int count = driver4Mybatis.update(context, getDefine_class(), "updateNeGroupMapLocation", ne_group);

		clearCache();
		return queryNeGroup(context, ne_group.getNe_group_id());
	}

	@Override
	public Model4NeGroup deleteNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		try {
			lock.lock();

			Map<String, Object> parameter = new HashMap<String, Object>();

			// 1. 존재여부 확인
			Model4NeGroup ne_group = queryNeGroup(context, ne_group_id);
			if (ne_group == null) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group_id);
			}
			if (!ne_group.getAccess().isDelete()) {
				throw new EmpException(ERROR_CODE_ORANGE.MODEL_DENYDELETE, MESSAGE_CODE_ORANGE.NE_GROUP, ne_group.getNe_group_name());
			}

			// 1.1. 존재하면 삭제
			parameter.clear();
			parameter.put("ne_group_id", ne_group_id);
			parameter.put("left_bound", ne_group.getLeft_bound());
			parameter.put("right_bound", ne_group.getRight_bound());
			parameter.put("updater", context.getUser_account());
			parameter.put("update_time", new Date());
			@SuppressWarnings("unused")
			int count = driver4Mybatis.update(context, getDefine_class(), "deleteNeGroup", parameter);

			// 1.2 left_bound, right_bound 감소
			parameter.clear();
			parameter.put("left_bound", ne_group.getLeft_bound());
			parameter.put("right_bound", ne_group.getRight_bound());
			parameter.put("offset_bound", ne_group.getLeft_bound() - ne_group.getRight_bound() - 1);
			parameter.put("updater", context.getUser_account());
			parameter.put("update_time", new Date());
			driver4Mybatis.update(context, getDefine_class(), "shiftLeftBound", parameter);
			driver4Mybatis.update(context, getDefine_class(), "shiftRightBound", parameter);

			clearCache();
			return ne_group;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		long update_seq_network = (Long) driver4Mybatis.selectOne(context, getDefine_class(), "queryNextUpdate_seq_network", null);
		return update_seq_network;
	}

	@Override
	public void clearCache() {
		UtilCache.removeAll(CACHE_ID_KEY);
		UtilCache.removeAll(CACHE_TREE_KEY);
	}

}
