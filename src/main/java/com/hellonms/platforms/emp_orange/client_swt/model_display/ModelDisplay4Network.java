package com.hellonms.platforms.emp_orange.client_swt.model_display;

import java.io.Serializable;

import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmSummary;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;

@SuppressWarnings("serial")
public class ModelDisplay4Network implements Serializable {

	public static final int MAX_CONSOLE_COUNT = 1000;

	private long sequenceNetwork;

	private long sequenceFault;

	private long maxEventId;

	private TreeNode4NeGroup treeNode4NeGroup;

	private Model4Ne[] model4Nes;

	private Model4NetworkLink[] model4NetworkLinks;

	private Model4AlarmSummary[] model4AlarmSummaries;

	private Model4Event[] model4Events;

	private Model4Alarm[] model4AlarmActives;

	private Model4Alarm[] model4AlarmHistories;

	public ModelDisplay4Network() {

	}

	public long getSequenceNetwork() {
		return sequenceNetwork;
	}

	public void setSequenceNetwork(long sequenceNetwork) {
		this.sequenceNetwork = sequenceNetwork;
	}

	public long getSequenceFault() {
		return sequenceFault;
	}

	public void setSequenceFault(long sequenceFault) {
		this.sequenceFault = sequenceFault;
	}

	public long getMaxEventId() {
		return maxEventId;
	}

	public void setMaxEventId(long maxEventId) {
		this.maxEventId = maxEventId;
	}

	public TreeNode4NeGroup getTreeNode4NeGroup() {
		return treeNode4NeGroup;
	}

	public void setTreeNode4NeGroup(TreeNode4NeGroup treeNode4NeGroup) {
		this.treeNode4NeGroup = treeNode4NeGroup;
	}

	public Model4Ne[] getModel4Nes() {
		return model4Nes;
	}

	public void setModel4Nes(Model4Ne[] model4Nes) {
		this.model4Nes = model4Nes;
	}

	public Model4NetworkLink[] getModel4NetworkLinks() {
		return model4NetworkLinks;
	}

	public void setModel4NetworkLinks(Model4NetworkLink[] model4NetworkLinks) {
		this.model4NetworkLinks = model4NetworkLinks;
	}

	public Model4AlarmSummary[] getModel4AlarmSummaries() {
		return model4AlarmSummaries;
	}

	public void setModel4AlarmSummaries(Model4AlarmSummary[] model4AlarmSummaries) {
		this.model4AlarmSummaries = model4AlarmSummaries;
	}

	public Model4Event[] getModel4Events() {
		return model4Events;
	}

	public void setModel4Events(Model4Event[] model4Events) {
		this.model4Events = model4Events;
	}

	public Model4Alarm[] getModel4AlarmActives() {
		return model4AlarmActives;
	}

	public void setModel4AlarmActives(Model4Alarm[] model4AlarmActives) {
		this.model4AlarmActives = model4AlarmActives;
	}

	public Model4Alarm[] getModel4AlarmHistories() {
		return model4AlarmHistories;
	}

	public void setModel4AlarmHistories(Model4Alarm[] model4AlarmHistories) {
		this.model4AlarmHistories = model4AlarmHistories;
	}

}
