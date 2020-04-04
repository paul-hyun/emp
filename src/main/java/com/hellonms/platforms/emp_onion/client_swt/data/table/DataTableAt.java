package com.hellonms.platforms.emp_onion.client_swt.data.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.Viewer;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;

/**
 * <p>
 * 테이블의 데이터를 처리하기 위한 공통적인 기능을 미리 구현한 추상 클래스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public abstract class DataTableAt implements DataTableIf {

	protected Map<ILabelProviderListener, IBaseLabelProvider> listenerMap = new HashMap<ILabelProviderListener, IBaseLabelProvider>();

	protected List<PanelTableIf> panelTableList = new ArrayList<PanelTableIf>();

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
	final public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void addPanelTable(PanelTableIf panelTable) {
		panelTableList.add(panelTable);
	}

	@Override
	public void removePanelTable(PanelTableIf panelTable) {
		panelTableList.remove(panelTable);
	}

	@Override
	public void refresh() {
		if (panelTableList.size() > 0) {
			panelTableList.get(0).getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					for (PanelTableIf panelTable : panelTableList) {
						panelTable.refresh();
					}
				}
			});
		}
	}

}
