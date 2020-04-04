package com.hellonms.platforms.emp_onion.client_swt.data.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.Viewer;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTreeIf;

/**
 * <p>
 * 트리의 데이터를 처리하기 위한 공통적인 기능을 미리 구현한 추상 클래스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public abstract class DataTreeAt extends ColumnLabelProvider implements DataTreeIf {

	public static final Object[] EMPTY = {};

	protected Map<ILabelProviderListener, IBaseLabelProvider> listenerMap = new HashMap<ILabelProviderListener, IBaseLabelProvider>();

	protected List<PanelTreeIf> panelTreeList = new ArrayList<PanelTreeIf>();

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		listenerMap.put(listener, this);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		listenerMap.remove(listener);
	}

	@Override
	public void addPanelTree(PanelTreeIf panelTree) {
		panelTreeList.add(panelTree);
	}

	@Override
	public void removePanelTree(PanelTreeIf panelTree) {
		panelTreeList.remove(panelTree);
	}

	@Override
	public void refresh() {
		if (panelTreeList.size() > 0) {
			panelTreeList.get(0).getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					for (PanelTreeIf panelTree : panelTreeList) {
						panelTree.refresh();
					}
				}
			});
		}
	}

	@Override
	public void setDatas(Object... datas) {

	}

}
