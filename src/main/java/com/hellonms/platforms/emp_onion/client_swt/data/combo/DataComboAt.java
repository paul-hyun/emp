package com.hellonms.platforms.emp_onion.client_swt.data.combo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.Viewer;

import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;

/**
 * <p>
 * 콤보의 데이터를 처리하기 위한 공통적인 기능을 미리 구현한 추상 클래스
 * </p>
 * 
 * @since 1.6
 * @create 2015. 5. 21.
 * @modified 2015. 5. 21.
 * @author jungsun
 *
 */
public abstract class DataComboAt implements DataComboIf {

	protected Map<ILabelProviderListener, IBaseLabelProvider> listenerMap = new HashMap<ILabelProviderListener, IBaseLabelProvider>();

	protected List<SelectorCombo> viewerList = new ArrayList<SelectorCombo>();

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
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void addSelector(SelectorCombo viewer) {
		viewerList.add(viewer);
	}

	@Override
	public void removeSelector(SelectorCombo viewer) {
		viewerList.remove(viewer);
	}

	@Override
	public void refresh() {
		if (viewerList.size() > 0) {
			viewerList.get(0).getControl().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					for (ComboViewer viewer : viewerList) {
						viewer.refresh();
					}
				}
			});
		}
	}

	@Override
	public Object getItem(int index) {
		return null;
	}

	@Override
	public boolean isNeedUpdate(Object... datas) {
		return false;
	}

	@Override
	public Object findItem(Object element) {
		return getDefaultItem();
	}

}
