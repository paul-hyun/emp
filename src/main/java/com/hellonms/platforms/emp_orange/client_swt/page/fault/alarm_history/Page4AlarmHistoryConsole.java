package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history;

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.action.ActionIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.ModelClient4NetworkTreeListenerIf;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.COLOR_ORANGE;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;

/**
 * <p>
 * Page4AlarmHistoryConsole
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4AlarmHistoryConsole extends Page implements ModelClient4NetworkTreeListenerIf {

	public class Page4AlarmHistoryConsoleAction implements ActionIf {

		protected SEVERITY severity;

		public Page4AlarmHistoryConsoleAction(SEVERITY severity) {
			this.severity = severity;
		}

		@Override
		public int getStyle() {
			return SWT.CHECK;
		}

		@Override
		public String getTooltip() {
			return severity.toString();
		}

		@Override
		public String getText() {
			return severity.toString();
		}

		@Override
		public Image getImage() {
			switch (severity) {
			case COMMUNICATION_FAIL:
				return ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF_FILTER_ON);
			case CRITICAL:
				return ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CRITICAL_FILTER_ON);
			case MAJOR:
				return ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MAJOR_FILTER_ON);
			case MINOR:
				return ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MINOR_FILTER_ON);
			case CLEAR:
				return ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CLEAR_FILTER_ON);
			case INFO:
				return ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_INFO_FILTER_ON);
			default:
				return null;
			}
		}

		@Override
		public void initAction(ToolItem item) {
			item.setSelection(true);
			switch (severity) {
			case COMMUNICATION_FAIL:
				item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF_FILTER_ON));
				break;
			case CRITICAL:
				item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CRITICAL_FILTER_ON));
				break;
			case MAJOR:
				item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MAJOR_FILTER_ON));
				break;
			case MINOR:
				item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MINOR_FILTER_ON));
				break;
			case CLEAR:
				item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CLEAR_FILTER_ON));
				break;
			case INFO:
				item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_INFO_FILTER_ON));
				break;
			default:
			}
		}

		@Override
		public void widgetSelected(ToolItem item) {
			DataTable4AlarmHistoryConsole dataTable = (DataTable4AlarmHistoryConsole) panelTable.getDataTable();
			switch (severity) {
			case COMMUNICATION_FAIL:
				if (item.getSelection()) {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF_FILTER_ON));
				} else {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF_FILTER_OFF));
				}
				dataTable.setCfFilter(item.getSelection());
				break;
			case CRITICAL:
				if (item.getSelection()) {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CRITICAL_FILTER_ON));
				} else {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CRITICAL_FILTER_OFF));
				}
				dataTable.setCriticalFilter(item.getSelection());
				break;
			case MAJOR:
				if (item.getSelection()) {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MAJOR_FILTER_ON));
				} else {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MAJOR_FILTER_OFF));
				}
				dataTable.setMajorFilter(item.getSelection());
				break;
			case MINOR:
				if (item.getSelection()) {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MINOR_FILTER_ON));
				} else {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MINOR_FILTER_OFF));
				}
				dataTable.setMinorFilter(item.getSelection());
				break;
			case CLEAR:
				if (item.getSelection()) {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CLEAR_FILTER_ON));
				} else {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CLEAR_FILTER_OFF));
				}
				dataTable.setClearFilter(item.getSelection());
				break;
			case INFO:
				if (item.getSelection()) {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_INFO_FILTER_ON));
				} else {
					item.setImage(ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_INFO_FILTER_OFF));
				}
				dataTable.setInfoFilter(item.getSelection());
				break;
			default:
			}
		}

	}

	public class Page4AlarmHistoryConsoleClear implements ActionIf {

		@Override
		public int getStyle() {
			return SWT.PUSH;
		}

		@Override
		public String getTooltip() {
			return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CLEAR);
		}

		@Override
		public String getText() {
			return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CLEAR);
		}

		@Override
		public Image getImage() {
			return ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CONSOLE_RESET);
		}

		@Override
		public void initAction(ToolItem item) {
		}

		@Override
		public void widgetSelected(ToolItem item) {
			DataTable4AlarmHistoryConsole dataTable = (DataTable4AlarmHistoryConsole) panelTable.getDataTable();
			dataTable.clear();
		}

	}

	protected PanelTableIf panelTable;

	protected ActionIf[] actions;

	public Page4AlarmHistoryConsole(Composite parent, int style) {
		super(parent, style, "", "");

		ModelClient4NetworkTree.getInstance().addModelClient4NetworkTreeListener(Page4AlarmHistoryConsole.this);
		addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				ModelClient4NetworkTree.getInstance().removeModelClient4NetworkTreeListener(Page4AlarmHistoryConsole.this);
			}
		});

		createGUI();
	}

	protected void createGUI() {
		getContentComposite().setBackgroundMode(SWT.INHERIT_DEFAULT);
		getContentComposite().setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));
		getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.ALARM_HISTORY_CONSOLE, this, SWT.BORDER | SWT.FULL_SELECTION, 0, null);
		panelTable.setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.ALARM_HISTORY_CONSOLE));
		panelTable.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(org.eclipse.jface.viewers.DoubleClickEvent event) {

			}
		});
		panelTable.setBackground(ThemeFactory.getColor(COLOR_ORANGE.CONSOLE_TABLE_BG));
		panelTable.setForeground(ThemeFactory.getColor(COLOR_ORANGE.CONSOLE_TABLE_FG));

		refresh();
	}

	@Override
	public void display(boolean progressBar) {
	}

	@Override
	public ActionIf[] getActions() {
		if (actions == null) {
			actions = new ActionIf[] { //
			new Page4AlarmHistoryConsoleAction(SEVERITY.COMMUNICATION_FAIL), //
					new Page4AlarmHistoryConsoleAction(SEVERITY.CRITICAL), //
					new Page4AlarmHistoryConsoleAction(SEVERITY.MAJOR), //
					new Page4AlarmHistoryConsoleAction(SEVERITY.MINOR), //
					new Page4AlarmHistoryConsoleClear(), //
			};
		}
		return actions;
	}

	@Override
	public void refresh() {
		if (panelTable != null) {
			panelTable.setDatas((Object) ModelClient4NetworkTree.getInstance().getAlarmHistories());
		}
	}

}
