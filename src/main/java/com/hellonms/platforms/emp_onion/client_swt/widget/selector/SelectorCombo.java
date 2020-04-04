package com.hellonms.platforms.emp_onion.client_swt.widget.selector;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;

/**
 * <p>
 * SelectorCombo
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorCombo extends ComboViewer implements ControlInputIf {

	private DataComboIf dataCombo;

	private PanelInputListenerIf listener;

	private boolean fireSelectionChanged = true;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public SelectorCombo(Composite parent, int style) {
		super(parent, style);

		addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				SelectorCombo.this.selectionChanged(event);
			}
		});
	}

	public SelectorCombo(Composite parent, int style, DataComboIf dataCombo) {
		this(parent, style);

		setDataCombo(dataCombo);
	}

	public SelectorCombo(CCombo combo, DataComboIf dataCombo) {
		super(combo);

		addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				SelectorCombo.this.selectionChanged(event);
			}
		});

		setDataCombo(dataCombo);
	}

	private void selectionChanged(SelectionChangedEvent event) {
		if (listener != null) {
			listener.completeChanged();
		}
	}

	public DataComboIf getDataCombo() {
		return dataCombo;
	}

	/**
	 * 모델을 설정합니다.
	 * 
	 * @param model
	 *            콤보 모델
	 */
	public void setDataCombo(DataComboIf dataCombo) {
		if (this.dataCombo != null) {
			this.dataCombo.removeSelector(this);
		}
		this.dataCombo = dataCombo;

		setContentProvider(this.dataCombo);
		setLabelProvider(this.dataCombo);
		setInput(this.dataCombo.getInput());
		this.dataCombo.addSelector(this);

		setSelectedItem(dataCombo.getDefaultItem());
	}

	/**
	 * 선택된 항목을 반환합니다.
	 * 
	 * @return 선택된 항목
	 */
	public Object getSelectedItem() {
		StructuredSelection selection = (StructuredSelection) super.getSelection();
		return selection.isEmpty() ? null : selection.getFirstElement();
	}

	/**
	 * 선택된 항목이 선택되도록 설정합니다.
	 * 
	 * @param element
	 *            선택된 항목
	 */
	public void setSelectedItem(Object element) {
		if (element != null) {
			SelectorCombo.super.setSelectionToWidget(new StructuredSelection(element), false);
		}
	}

	@Override
	public Control getControl() {
		return super.getControl();
	}

	@Override
	public boolean isComplete() {
		return !super.getSelection().isEmpty();
	}

	@Override
	public Object getValue() {
		return getSelectedItem();
	}

	@Override
	public void setValue(Object value) {
		setSelectedItem(value);
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.listener = listener;
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

	/**
	 * 콤보 데이터 설정
	 * 
	 * @param datas
	 */
	public void setDatas(Object... datas) {
		dataCombo.setDatas(datas);
	}

	/**
	 * @return 기본 항목
	 */
	public Object getDefaultItem() {
		return dataCombo.getDefaultItem();
	}

	/**
	 * @param datas
	 * @return 업데이터 필요여부
	 */
	public boolean isNeedUpdate(Object... datas) {
		return dataCombo.isNeedUpdate(datas);
	}

	/**
	 * @param element
	 * @return 찾은 항목
	 */
	public Object findElement(Object element) {
		return dataCombo.findItem(element);
	}

}
