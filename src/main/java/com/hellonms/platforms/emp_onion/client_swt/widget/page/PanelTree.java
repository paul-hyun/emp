package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeIf;

/**
 * <p>
 * PanelTree
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelTree extends TreeViewer implements PanelTreeIf {

	protected DataTreeIf dataTree;

	protected boolean fireSelectionChanged = true;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public PanelTree(Composite parent, int style) {
		super(parent, style);

		getTree().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent event) {
				dispose();
			}
		});
	}

	public PanelTree(Composite parent, int style, DataTreeIf dataTree) {
		this(parent, style);

		setDataTree(dataTree);
	}

	public DataTreeIf getDataTree() {
		return dataTree;
	}

	/**
	 * 모델을 설정합니다.
	 * 
	 * @param model
	 *            트리 모델
	 */
	public void setDataTree(DataTreeIf dataTree) {
		if (this.dataTree != null) {
			this.dataTree.removePanelTree(this);
		}
		this.dataTree = dataTree;

		setContentProvider(this.dataTree);
		setLabelProvider(this.dataTree);
		setInput(this.dataTree.getInput());
		this.dataTree.addPanelTree(this);
	}

	/**
	 * 배경색을 설정합니다.
	 * 
	 * @param color
	 *            배경색
	 */
	public void setBackground(Color color) {
		getTree().setBackground(color);
	}

	/**
	 * 전경색을 설정합니다.
	 * 
	 * @param color
	 *            전경색
	 */
	public void setForeground(Color color) {
		getTree().setForeground(color);
	}

	/**
	 * 모델에서 트리 뷰어를 제거합니다.
	 */
	public void dispose() {
		if (this.dataTree != null) {
			this.dataTree.removePanelTree(this);
		}
	}

	@Override
	public Display getDisplay() {
		return getTree().getDisplay();
	}

	@Override
	public void refresh() {
		ISelection selection = super.getSelection();
		Object[] elements = super.getExpandedElements();

		super.refresh();

		super.setExpandedElements(elements);
		super.setSelectionToWidget(selection, false);
	}

	@Override
	public void setSelection(ISelection selection) {
		if (dataTree != null) {
			List<Object> elementList = new ArrayList<Object>();
			getExpandedElements(((StructuredSelection) selection).getFirstElement(), elementList);
			super.setExpandedElements(elementList.toArray());
		}
		super.setSelection(selection);
	}

	protected void getExpandedElements(Object object, List<Object> elementList) {
		elementList.add(object);

		Object parent = dataTree.getParent(object);
		if (parent != null) {
			getExpandedElements(parent, elementList);
		}
	}

	public boolean isFireSelectionChanged() {
		return fireSelectionChanged;
	}

	public void setFireSelectionChanged(boolean fireSelectionChanged) {
		this.fireSelectionChanged = fireSelectionChanged;
	}

	@Override
	protected void fireSelectionChanged(SelectionChangedEvent event) {
		if (fireSelectionChanged) {
			super.fireSelectionChanged(event);
		}
	}

	@Override
	public void setDatas(Object... datas) {
		if (dataTree != null) {
			dataTree.setDatas(datas);
		}
	}

}
