package com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.widgets.Display;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4Network;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmSummary;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;

/**
 * <p>
 * ModelClient4NetworkTree
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ModelClient4NetworkTree {

	public interface ModelClient4NetworkTreeListenerIf {

		public void refresh();

	}

	public interface ModelClient4EventListenerIf {

		public void refresh(Model4Event[] model4Events);

	}

	public class NODE {

		private Object value;

		private NODE parent;

		private boolean management;

		private int cf;

		private int cf_unack;

		private int critical;

		private int critical_unack;

		private int major;

		private int major_unack;

		private int minor;

		private int minor_unack;

		private long alarm_summary_timestamp;

		private boolean blink_alarm_summary_flag = false;

		private Set<NODE> childMap = new TreeSet<NODE>(new Comparator<NODE>() {
			@Override
			public int compare(NODE o1, NODE o2) {
				return node_comparator.compare(o1, o2);
			}
		});

		private Comparator<NODE> node_comparator = new Comparator<NODE>() {
			@Override
			public int compare(NODE o1, NODE o2) {
				if (o1.isNeGroup() && o2.isNeGroup()) {
					return o1.getId() - o2.getId();
				} else if (o1.isNe() && o2.isNe()) {
					return o1.getId() - o2.getId();
				} else if (o1.isNeGroup() && o2.isNe()) {
					return -1;
				} else if (o1.isNe() && o2.isNeGroup()) {
					return 1;
				} else {
					return 0;
				}
			}
		};

		public NODE(NODE parent, Model4NeGroup neGroup, boolean management) {
			this(parent, (Object) neGroup);
			this.management = management;
		}

		public NODE(NODE parent, Model4Ne ne) {
			this(parent, (Object) ne);
		}

		private NODE(NODE parent, Object value) {
			this.parent = parent;
			this.value = value;

			if (parent != null) {
				parent.childMap.add(this);
			}
		}

		public boolean isNeGroup() {
			return value instanceof Model4NeGroup;
		}

		public boolean isNe() {
			return value instanceof Model4Ne;
		}

		public boolean isManagement() {
			return management;
		}

		public Object getValue() {
			return value;
		}

		public int getId() {
			if (value instanceof Model4NeGroup) {
				return ((Model4NeGroup) value).getNe_group_id();
			} else if (value instanceof Model4Ne) {
				return ((Model4Ne) value).getNe_id();
			} else {
				return 0;
			}
		}

		public String getName() {
			if (value instanceof Model4NeGroup) {
				return ((Model4NeGroup) value).getNe_group_name();
			} else if (value instanceof Model4Ne) {
				return ((Model4Ne) value).getNe_name();
			} else {
				return "";
			}
		}

		public String getIcon() {
			if (value instanceof Model4NeGroup) {
				return ((Model4NeGroup) value).getNe_group_icon();
			} else if (value instanceof Model4Ne) {
				return ((Model4Ne) value).getNe_icon();
			} else {
				return "";
			}
		}

		public int getMap_location_x() {
			if (value instanceof Model4NeGroup) {
				return ((Model4NeGroup) value).getNe_group_map_location_x();
			} else if (value instanceof Model4Ne) {
				return ((Model4Ne) value).getNe_map_location_x();
			} else {
				return 0;
			}
		}

		public int getMap_location_y() {
			if (value instanceof Model4NeGroup) {
				return ((Model4NeGroup) value).getNe_group_map_location_y();
			} else if (value instanceof Model4Ne) {
				return ((Model4Ne) value).getNe_map_location_y();
			} else {
				return 0;
			}
		}

		public NODE getParent() {
			return parent;
		}

		private void dispose() {
			if (parent != null) {
				parent.childMap.remove(this);
			}
		}

		public NODE[] getChilds() {
			return childMap.toArray(new NODE[0]);
		}

		public void setManagement(boolean management) {
			this.management = management;
		}

		public void setValue(Object value) {
			if (isNeGroup() && value instanceof Model4NeGroup) {
				this.value = value;
			} else if (isNe() && value instanceof Model4Ne) {
				this.value = value;
			}
		}

		public int getCf() {
			return cf;
		}

		public int getCf_unack() {
			return cf_unack;
		}

		public int getCritical() {
			return critical;
		}

		public int getCritical_unack() {
			return critical_unack;
		}

		public int getMajor() {
			return major;
		}

		public int getMajor_unack() {
			return major_unack;
		}

		public int getMinor() {
			return minor;
		}

		public int getMinor_unack() {
			return minor_unack;
		}

		public SEVERITY getSeverity() {
			if (0 < cf) {
				return SEVERITY.COMMUNICATION_FAIL;
			} else if (0 < critical) {
				return SEVERITY.CRITICAL;
			} else if (0 < major) {
				return SEVERITY.MAJOR;
			} else if (0 < minor) {
				return SEVERITY.MINOR;
			} else {
				return SEVERITY.CLEAR;
			}
		}

		public boolean isIconBlink() {
			return 0 < alarm_summary_timestamp;
		}

		public void stopIconBlink(boolean include_child) {
			if (include_child && isNeGroup()) {
				alarm_summary_timestamp = 0;
				__stopIconBlink();
				rootNode.syncAlarmSummary();
			} else if (isNe()) {
				__stopIconBlink();
				rootNode.syncAlarmSummary();
			}
		}

		private void __stopIconBlink() {
			alarm_summary_timestamp = 0;

			if (isNeGroup()) {
				for (NODE childNode : getChilds()) {
					childNode.__stopIconBlink();
				}
			}
		}

		private void syncAlarmSummary(Model4AlarmSummary alarmSummary) {
			if (isNe()) {
				int cf;
				int cf_unack;
				int critical;
				int critical_unack;
				int major;
				int major_unack;
				int minor;
				int minor_unack;

				if (alarmSummary == null) {
					cf = 0;
					cf_unack = 0;
					critical = 0;
					critical_unack = 0;
					major = 0;
					major_unack = 0;
					minor = 0;
					minor_unack = 0;
				} else {
					cf = alarmSummary.getCommunication_fail_count();
					cf_unack = alarmSummary.getCommunication_fail_unack_count();
					critical = alarmSummary.getCritical_count();
					critical_unack = alarmSummary.getCritical_unack_count();
					major = alarmSummary.getMajor_count();
					major_unack = alarmSummary.getMajor_unack_count();
					minor = alarmSummary.getMinor_count();
					minor_unack = alarmSummary.getMinor_unack_count();
				}

				if (this.cf != cf || this.cf_unack != cf_unack || this.critical != critical || this.critical_unack != critical_unack || this.major != major || this.major_unack != major_unack || this.minor != minor || this.minor_unack != minor_unack) {
					this.alarm_summary_timestamp = blink_alarm_summary_flag ? System.currentTimeMillis() : 0;
				}

				this.cf = cf;
				this.cf_unack = cf_unack;
				this.critical = critical;
				this.critical_unack = critical_unack;
				this.major = major;
				this.major_unack = major_unack;
				this.minor = minor;
				this.minor_unack = minor_unack;

				blink_alarm_summary_flag = true;
			}
		}

		private void syncAlarmSummary() {
			if (isNeGroup()) {
				int cf = 0;
				int cf_unack = 0;
				int critical = 0;
				int critical_unack = 0;
				int major = 0;
				int major_unack = 0;
				int minor = 0;
				int minor_unack = 0;
				long alarm_summary_timestamp = 0;

				for (NODE childNode : getChilds()) {
					childNode.syncAlarmSummary();

					cf += childNode.cf;
					cf_unack += childNode.cf_unack;
					critical += childNode.critical;
					critical_unack += childNode.critical_unack;
					major += childNode.major;
					major_unack += childNode.major_unack;
					minor += childNode.minor;
					minor_unack += childNode.minor_unack;
					alarm_summary_timestamp = Math.max(alarm_summary_timestamp, childNode.alarm_summary_timestamp);
				}

				if (!isManagement()) {
					cf = 0;
					cf_unack = 0;
					critical = 0;
					critical_unack = 0;
					major = 0;
					major_unack = 0;
					minor = 0;
					minor_unack = 0;
				}

				this.alarm_summary_timestamp = alarm_summary_timestamp;
				this.cf = cf;
				this.cf_unack = cf_unack;
				this.critical = critical;
				this.critical_unack = critical_unack;
				this.major = major;
				this.major_unack = major_unack;
				this.minor = minor;
				this.minor_unack = minor_unack;

				blink_alarm_summary_flag = true;
			}
		}

		@Override
		public String toString() {
			return getName();
		}

	}

	private static ModelClient4NetworkTree instance;

	public static ModelClient4NetworkTree getInstance() {
		if (instance == null) {
			instance = new ModelClient4NetworkTree();
		}
		return instance;
	}

	private long sequenceNetwork = -1L;

	private long sequenceFault = -1L;

	private Map<Integer, NODE> neGroupMap = new HashMap<Integer, NODE>();

	private Map<Integer, NODE> neMap = new HashMap<Integer, NODE>();

	private NODE rootNode;

	private Model4NetworkLink[] network_links = {};

	private List<ModelClient4NetworkTreeListenerIf> listenerList = new ArrayList<ModelClient4NetworkTreeListenerIf>();

	private List<ModelClient4EventListenerIf> eventListenerList = new ArrayList<ModelClient4EventListenerIf>();

	private Model4Event[] events = {};

	private Model4Alarm[] alarmActives = {};

	private Model4Alarm[] alarmHistories = {};

	private final ReentrantLock lock = new ReentrantLock();

	private Page4NetworkTreeAdvisor advisor = new Page4NetworkTreeAdvisor();

	private ModelClient4NetworkTree() {
		Model4NeGroup neGroup = new Model4NeGroup();
		neGroup.setNe_group_id(Model4NeGroup.ROOT_NE_GROUP_ID);
		neGroup.setNe_group_name("NETWORK");
		neGroup.setNe_group_icon("/data/image/node_icon/NETWORK.png");
		neGroup.setNe_group_map_bg_image("");
		rootNode = new NODE(null, neGroup);

		neGroupMap.put(rootNode.getId(), rootNode);
	}

	public void addModelClient4NetworkTreeListener(ModelClient4NetworkTreeListenerIf listener) {
		listenerList.add(listener);
	}

	public void removeModelClient4NetworkTreeListener(ModelClient4NetworkTreeListenerIf listener) {
		listenerList.remove(listener);
	}

	public void addModelClient4EventListener(ModelClient4EventListenerIf listener) {
		eventListenerList.add(listener);
	}

	public void removeModelClient4EventListener(ModelClient4EventListenerIf listener) {
		eventListenerList.remove(listener);
	}

	public void refresh() throws EmpException {
		if (Display.getDefault() != null && Thread.currentThread() == Display.getDefault().getThread()) {
			throw new RuntimeException("Can't access refresh in gui thread!!!");
		}
		lock.lock();
		try {
			ModelDisplay4Network modelDisplay4Network = advisor.queryNetwork(sequenceNetwork, sequenceFault);
			if (modelDisplay4Network != null) {
				sequenceNetwork = modelDisplay4Network.getSequenceNetwork();
				sequenceFault = modelDisplay4Network.getSequenceFault();
				boolean sync_ne_groups = refreshNeGroup(modelDisplay4Network.getTreeNode4NeGroup());
				boolean sync_nes = refreshNe(modelDisplay4Network.getModel4Nes());
				boolean sync_netrowk_links = refreshNetworkLink(modelDisplay4Network.getModel4NetworkLinks());
				boolean sync_alarm_summarys = refreshAlarmSummary(modelDisplay4Network.getModel4AlarmSummaries());
				boolean sync_event_console = refreshEventConsole(modelDisplay4Network.getModel4Events());
				boolean sync_alarm_active_console = refreshAlarmActiveConsole(modelDisplay4Network.getModel4AlarmActives());
				boolean sync_alarm_history_console = refreshAlarmHistoryConsole(modelDisplay4Network.getModel4AlarmHistories());
				if (sync_event_console) {
					fireModelClient4EventChanged(modelDisplay4Network.getModel4Events());
				}
				if (sync_ne_groups || sync_nes || sync_netrowk_links || sync_alarm_summarys || sync_event_console || sync_alarm_active_console || sync_alarm_history_console) {
					fireModelClient4NetworkTreeChanged();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	private void fireModelClient4NetworkTreeChanged() {
		for (ModelClient4NetworkTreeListenerIf listener : listenerList.toArray(new ModelClient4NetworkTreeListenerIf[0])) {
			listener.refresh();
		}
	}

	private void fireModelClient4EventChanged(Model4Event[] model4Events) {
		for (ModelClient4EventListenerIf listener : eventListenerList.toArray(new ModelClient4EventListenerIf[0])) {
			listener.refresh(model4Events);
		}
	}

	private void refreshChild(Map<Integer, TreeNode4NeGroup> neGroupMap, TreeNode4NeGroup treeNode4NeGroup) {
		neGroupMap.put(treeNode4NeGroup.getNe_group_id(), treeNode4NeGroup);
		for (TreeNode4NeGroup child : treeNode4NeGroup.getChild_list()) {
			refreshChild(neGroupMap, child);
		}
	}

	private boolean refreshNeGroup(TreeNode4NeGroup treeNode4NeGroup) {
		if (treeNode4NeGroup == null) {
			return false;
		}

		Map<Integer, TreeNode4NeGroup> newMap = new LinkedHashMap<Integer, TreeNode4NeGroup>();
		newMap.put(treeNode4NeGroup.getNe_group_id(), treeNode4NeGroup);
		for (TreeNode4NeGroup child : treeNode4NeGroup.getChild_list()) {
			refreshChild(newMap, child);
		}

		Map<Integer, NODE> oldMap = new LinkedHashMap<Integer, NODE>();
		for (NODE neGroup : neGroupMap.values().toArray(new NODE[0])) {
			oldMap.put(neGroup.getId(), neGroup);
		}

		// 삭제: OLD에는 있으나 NEW에는 없는 경우
		for (int old_ne_group_id : oldMap.keySet().toArray(new Integer[0])) {
			if (!newMap.containsKey(old_ne_group_id)) {
				oldMap.remove(old_ne_group_id);

				NODE neGroup = neGroupMap.remove(old_ne_group_id);
				neGroup.dispose();
			}
		}

		// 수정: OLD와 NEW에 둘다 있는 경우
		for (int old_ne_group_id : oldMap.keySet().toArray(new Integer[0])) {
			if (newMap.containsKey(old_ne_group_id)) {
				NODE old_neGroup = oldMap.remove(old_ne_group_id);
				TreeNode4NeGroup newTreeNode = newMap.remove(old_ne_group_id);
				Model4NeGroup new_neGroup = newTreeNode.getNe_group();

				if (old_neGroup.parent != null && ((Model4NeGroup) old_neGroup.value).getParent_ne_group_id() != new_neGroup.getParent_ne_group_id()) {
					old_neGroup.dispose();
					NODE parentNode = neGroupMap.get(new_neGroup.getParent_ne_group_id());
					old_neGroup.parent = parentNode;
					parentNode.childMap.add(old_neGroup);
				}

				old_neGroup.value = new_neGroup;
				old_neGroup.management = newTreeNode.isManage_ne_group();
			}
		}

		// 추가: NEW에는 있고 OLD에는 없는 경우
		for (int new_ne_group_id : newMap.keySet().toArray(new Integer[0])) {
			if (!oldMap.containsKey(new_ne_group_id)) {
				TreeNode4NeGroup newTreeNode = newMap.remove(new_ne_group_id);
				Model4NeGroup new_neGroup = newTreeNode.getNe_group();

				NODE parent_neGroup = neGroupMap.get(new_neGroup.getParent_ne_group_id());
				if (parent_neGroup == null) {
					System.out.println("parent null ne group : " + new_neGroup);
				}
				neGroupMap.put(new_neGroup.getNe_group_id(), new NODE(parent_neGroup, new_neGroup, newTreeNode.isManage_ne_group()));
			}
		}

		return true;
	}

	private boolean refreshNe(Model4Ne[] updatedNes) {
		if (updatedNes == null) {
			return false;
		}

		Map<Integer, Model4Ne> newMap = new LinkedHashMap<Integer, Model4Ne>();
		for (Model4Ne ne : updatedNes) {
			newMap.put(ne.getNe_id(), ne);
		}

		Map<Integer, NODE> oldMap = new LinkedHashMap<Integer, NODE>();
		for (NODE ne : neMap.values().toArray(new NODE[0])) {
			oldMap.put(ne.getId(), ne);
		}

		// 삭제: OLD에는 있으나 NEW에는 없는 경우
		for (int old_ne_group_id : oldMap.keySet().toArray(new Integer[0])) {
			if (!newMap.containsKey(old_ne_group_id)) {
				oldMap.remove(old_ne_group_id);

				NODE ne = neMap.remove(old_ne_group_id);
				ne.dispose();
			}
		}

		// 수정: OLD와 NEW에 둘다 있는 경우
		for (int old_ne_group_id : oldMap.keySet().toArray(new Integer[0])) {
			if (newMap.containsKey(old_ne_group_id)) {
				NODE old_ne = oldMap.remove(old_ne_group_id);
				Model4Ne new_ne = newMap.remove(old_ne_group_id);

				if (old_ne.parent != null && ((Model4Ne) old_ne.value).getNe_group_id() != new_ne.getNe_group_id()) {
					old_ne.dispose();
					NODE parentNode = neGroupMap.get(new_ne.getNe_group_id());
					old_ne.parent = parentNode;
					parentNode.childMap.add(old_ne);
				}

				old_ne.value = new_ne;
			}
		}

		// 추가: NEW에는 있고 OLD에는 없는 경우
		for (int new_ne_group_id : newMap.keySet().toArray(new Integer[0])) {
			if (!oldMap.containsKey(new_ne_group_id)) {
				Model4Ne new_ne = newMap.remove(new_ne_group_id);

				NODE parent_neGroup = neGroupMap.get(new_ne.getNe_group_id());
				if (parent_neGroup == null) {
					System.out.println("parent null ne : " + new_ne);
				}
				neMap.put(new_ne.getNe_id(), new NODE(parent_neGroup, new_ne));
			}
		}

		return true;
	}

	private boolean refreshNetworkLink(Model4NetworkLink[] network_links) {
		if (network_links == null) {
			return false;
		}

		this.network_links = network_links;

		return true;
	}

	private boolean refreshAlarmSummary(Model4AlarmSummary[] alarmSummarys) {
		if (alarmSummarys == null) {
			return false;
		}

		Map<Integer, Model4AlarmSummary> alarmSummaryMap = new TreeMap<Integer, Model4AlarmSummary>();
		for (Model4AlarmSummary alarmSummary : alarmSummarys) {
			alarmSummaryMap.put(alarmSummary.getNe_id(), alarmSummary);
		}

		for (NODE node : neMap.values().toArray(new NODE[0])) {
			Model4AlarmSummary alarmSummary = alarmSummaryMap.get(node.getId());
			if (alarmSummary == null) {
				alarmSummary = new Model4AlarmSummary();
				alarmSummary.setNe_id(node.getId());
			}
			node.syncAlarmSummary(alarmSummary);
		}
		rootNode.syncAlarmSummary();

		return true;
	}

	private boolean refreshEventConsole(Model4Event[] model4Events) {
		if (model4Events == null) {
			return false;
		}

		events = model4Events;
		return true;
	}

	private boolean refreshAlarmActiveConsole(Model4Alarm[] model4AlarmActives) {
		if (model4AlarmActives == null) {
			return false;
		}

		alarmActives = model4AlarmActives;
		return true;
	}

	private boolean refreshAlarmHistoryConsole(Model4Alarm[] model4AlarmHistories) {
		if (model4AlarmHistories == null) {
			return false;
		}

		alarmHistories = model4AlarmHistories;
		return true;
	}

	public NODE getRootNode() {
		return rootNode;
	}

	public NODE getValidNode(NODE node) {
		if (node.isNeGroup()) {
			NODE thisNode = neGroupMap.get(node.getId());
			if (thisNode == null && node.parent != null) {
				thisNode = neGroupMap.get(node.parent.getId());
			}
			if (thisNode != null) {
				return thisNode;
			}
		} else if (node.isNe()) {
			NODE thisNode = neMap.get(node.getId());
			if (thisNode == null && node.parent != null) {
				thisNode = neGroupMap.get(node.parent.getId());
			}
			if (thisNode != null) {
				return thisNode;
			}
		}
		return getRootNode();
	}

	public NODE getNeGroup(int ne_group_id) throws EmpException {
		NODE thisNode = neGroupMap.get(ne_group_id);
		if (thisNode == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP_ID, ne_group_id);
		}

		return thisNode;
	}

	public NODE[] getListChildNeGroup(int ne_group_id) throws EmpException {
		NODE thisNode = neGroupMap.get(ne_group_id);
		if (thisNode == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP_ID, ne_group_id);
		}

		List<NODE> childList = new ArrayList<NODE>();
		for (NODE childNode : thisNode.getChilds()) {
			if (childNode.isNeGroup()) {
				childList.add(childNode);
			}
		}
		return childList.toArray(new NODE[0]);
	}

	public NODE[] getPathNeGroup(int ne_group_id) throws EmpException {
		List<NODE> pathList = new ArrayList<NODE>();

		while (true) {
			NODE node = neGroupMap.get(ne_group_id);
			if (node == null) {
				break;
			}
			pathList.add(0, node);

			if (ne_group_id == Model4NeGroup.ROOT_NE_GROUP_ID) {
				break;
			} else {
				ne_group_id = ((Model4NeGroup) node.value).getParent_ne_group_id();
			}
		}

		return pathList.toArray(new NODE[0]);
	}

	public NODE getNe(int ne_id) throws EmpException {
		NODE thisNode = neMap.get(ne_id);
		if (thisNode == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_ID, ne_id);
		}

		return thisNode;
	}

	public NODE getFirstNe() throws EmpException {
		NODE firstNe = getFirstNe(getRootNode());
		if (firstNe == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS);
		}

		return firstNe;
	}

	public NODE getFirstNe(NODE node) {
		NODE firstNode = null;
		try {
			NODE[] nodes = getListChild(node.getId());
			for (NODE child : nodes) {
				if (child.isNeGroup()) {
					firstNode = getFirstNe(child);
					if (firstNode != null) {
						return firstNode;
					}
				} else if (child.isNe() && !((Model4Ne) child.value).isNMS()) {
					return child;
				}
			}
		} catch (EmpException e) {
		}

		return null;
	}

	public NODE getFirstNe(Set<Integer> neGroupIdFilter, Set<Integer> neIdFilter) throws EmpException {
		NODE firstNe = getFirstNe(rootNode, neGroupIdFilter, neIdFilter);
		if (firstNe == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS);
		}

		return firstNe;
	}

	private NODE getFirstNe(NODE node, Set<Integer> neGroupIdFilter, Set<Integer> neIdFilter) {
		NODE firstNode = null;
		try {
			NODE[] nodes = getListChild(node.getId());
			for (NODE child : nodes) {
				if (child.isNeGroup() && !neGroupIdFilter.contains(child.getId())) {
					firstNode = getFirstNe(child, neGroupIdFilter, neIdFilter);
					if (firstNode != null) {
						return firstNode;
					}
				} else if (child.isNe() && !((Model4Ne) child.value).isNMS() && !neIdFilter.contains(child.getId())) {
					return child;
				}
			}
		} catch (EmpException e) {
		}

		return null;
	}

	public NODE[] getListAllChildNe(int ne_group_id) throws EmpException {
		NODE thisNode = neGroupMap.get(ne_group_id);
		if (thisNode == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP_ID, ne_group_id);
		}

		List<NODE> childList = new ArrayList<NODE>();
		getListAllChildNe(thisNode, childList);
		return childList.toArray(new NODE[0]);
	}

	private void getListAllChildNe(NODE node, List<NODE> childList) throws EmpException {
		for (NODE childNode : node.getChilds()) {
			if (childNode.isNeGroup()) {
				getListAllChildNe(childNode, childList);
			} else if (childNode.isNe()) {
				childList.add(childNode);
			}
		}
	}

	public NODE[] getListChildNe(int ne_group_id) throws EmpException {
		NODE thisNode = neGroupMap.get(ne_group_id);
		if (thisNode == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP_ID, ne_group_id);
		}

		List<NODE> childList = new ArrayList<NODE>();
		for (NODE childNode : thisNode.getChilds()) {
			if (childNode.isNe()) {
				childList.add(childNode);
			}
		}
		return childList.toArray(new NODE[0]);
	}

	public NODE[] getPathNe(int ne_id) throws EmpException {
		List<NODE> pathList = new ArrayList<NODE>();

		NODE thisNode = neMap.get(ne_id);
		if (thisNode == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_ID, ne_id);
		}
		pathList.add(0, thisNode);

		int ne_group_id = ((Model4Ne) thisNode.value).getNe_group_id();

		while (true) {
			NODE node = neGroupMap.get(ne_group_id);
			if (node == null) {
				break;
			}
			pathList.add(0, node);

			if (ne_group_id == Model4NeGroup.ROOT_NE_GROUP_ID) {
				break;
			} else {
				ne_group_id = ((Model4NeGroup) node.value).getParent_ne_group_id();
			}
		}

		return pathList.toArray(new NODE[0]);
	}

	public NODE getParent(NODE node) {
		int ne_group_id = 0;
		if (node.isNeGroup() && node.getId() != Model4NeGroup.ROOT_NE_GROUP_ID) {
			ne_group_id = ((Model4NeGroup) node.value).getParent_ne_group_id();
		} else if (node.isNe()) {
			ne_group_id = ((Model4Ne) node.value).getNe_group_id();
		} else {
			return null;
		}

		NODE nodeTree = neGroupMap.get(ne_group_id);
		return nodeTree == null ? null : nodeTree;
	}

	public NODE[] getListChild(int ne_group_id) throws EmpException {
		NODE thisNode = neGroupMap.get(ne_group_id);
		if (thisNode == null) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_GROUP_ID, ne_group_id);
		}

		List<Object> childList = new ArrayList<Object>();
		for (NODE childNode : thisNode.getChilds()) {
			childList.add(childNode);
		}
		return childList.toArray(new NODE[0]);
	}

	public String getNeGroupName(int ne_id) {
		String ne_group_name = "";

		NODE ne = neMap.get(ne_id);
		if (ne != null) {
			ne_group_name = ((Model4Ne) ne.value).getNe_group_name();
		}

		return ne_group_name;
	}

	public boolean isChildNe(int ne_group_id, int ne_id) {
		NODE node = neMap.get(ne_id);
		if (node == null || node.parent == null) {
			return false;
		} else if (node.parent.getId() == ne_group_id) {
			return true;
		} else {
			return isChildNeGroup(ne_group_id, node.parent.getId());
		}
	}

	public boolean isChildNeGroup(int parent_ne_group_id, int child_ne_group_id) {
		NODE node = neGroupMap.get(child_ne_group_id);
		if (node == null || node.parent == null) {
			return false;
		} else if (node.parent.getId() == parent_ne_group_id) {
			return true;
		} else {
			return isChildNeGroup(parent_ne_group_id, node.parent.getId());
		}
	}

	public boolean existChild(int neGroupId) {
		try {
			NODE[] children = getListChild(neGroupId);
			if (children != null && children.length > 0) {
				return true;
			}
		} catch (EmpException e) {
		}
		return false;
	}

	public NODE[] getPath(NODE node) {
		try {
			if (node.isNeGroup()) {
				return getPathNeGroup(node.getId());
			} else if (node.isNe()) {
				return getPathNe(node.getId());
			}
		} catch (EmpException e) {
		}
		return null;
	}

	public Model4NetworkLink[] getNetworkLinks() {
		return network_links;
	}

	public Model4Event[] getEvents() {
		return events;
	}

	public Model4Alarm[] getAlarmActives() {
		return alarmActives;
	}

	public Model4Alarm[] getAlarmHistories() {
		return alarmHistories;
	}

}
