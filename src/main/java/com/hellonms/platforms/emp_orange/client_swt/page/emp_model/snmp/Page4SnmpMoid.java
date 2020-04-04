package com.hellonms.platforms.emp_orange.client_swt.page.emp_model.snmp;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.snmp.Panel4SnmpMoidDetail.Panel4SnmpMoidDetailListnerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.emp_model.snmp.Panel4SnmpMoidList.Panel4SnmpMoidListListenerIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_TYPE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.SNMP_TYPE;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_NODE;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_NODE.MIB_MACRO_TYPE;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_REPOSITORY;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_SYNTAX;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Page4SnmpMoid extends Page {

	private class Panel4SnmpListener implements Panel4SnmpMoidListListenerIf, Panel4SnmpMoidDetailListnerIf {

		@Override
		public void refresh() {
			listener.refresh();
		}

		@Override
		public void selected(MIB_NODE node) {
			displayDetail(node);
		}

		@Override
		public String addNe_info(MIB_NODE node) {
			EMP_MODEL emp_model = listener.getEmp_model();
			if (emp_model != null) {
				Page4SnmpMoid.this.addNe_info(emp_model, node);
				listener.refresh();
			}
			return null;
		}

		@Override
		public String addAll(MIB_NODE node) {
			EMP_MODEL emp_model = listener.getEmp_model();
			if (emp_model != null) {
				Page4SnmpMoid.this.addAll(emp_model, node);
				listener.refresh();
			}
			return null;
		}

		@Override
		public String addEnum(MIB_NODE node) {
			EMP_MODEL emp_model = listener.getEmp_model();
			if (emp_model != null) {
				Page4SnmpMoid.this.addEnum(emp_model, node);
				listener.refresh();
			}
			return null;
		}

		@Override
		public EMP_MODEL getEmp_model() {
			return listener.getEmp_model();
		}

		@Override
		public void showPage(String name) {
			listener.showPage(name);
		}

	}

	private Panel4SnmpMoidList panel4SnmpMoidList;

	private Composite dataPenel;

	private Panel4SnmpMoidDetail panel4SnmpMoidDetail;

	private Panel4SnmpListener childListener = new Panel4SnmpListener();

	private Panel4SnmpListenerIf listener;

	public Page4SnmpMoid(Composite parent, int style, Panel4SnmpListenerIf listener) {
		super(parent, style, "", "");
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panel4SnmpMoidList = new Panel4SnmpMoidList(getContentComposite(), SWT.NONE, childListener);
		FormData fd_panel4SnmpMoidList = new FormData();
		fd_panel4SnmpMoidList.top = new FormAttachment(0, 5);
		fd_panel4SnmpMoidList.bottom = new FormAttachment(100, -5);
		fd_panel4SnmpMoidList.left = new FormAttachment(0, 5);
		fd_panel4SnmpMoidList.right = new FormAttachment(50, -3);
		panel4SnmpMoidList.setLayoutData(fd_panel4SnmpMoidList);

		dataPenel = new Composite(getContentComposite(), SWT.NONE);
		dataPenel.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		FormData fd_dataPenel = new FormData();
		fd_dataPenel.top = new FormAttachment(panel4SnmpMoidList, 0, SWT.TOP);
		fd_dataPenel.bottom = new FormAttachment(panel4SnmpMoidList, 0, SWT.BOTTOM);
		fd_dataPenel.left = new FormAttachment(panel4SnmpMoidList, 5, SWT.RIGHT);
		fd_dataPenel.right = new FormAttachment(100, -5);
		dataPenel.setLayoutData(fd_dataPenel);

		dataPenel.setLayout(new StackLayout());

		panel4SnmpMoidDetail = new Panel4SnmpMoidDetail(dataPenel, SWT.NONE, childListener);
	}

	private void displayDetail(MIB_NODE node) {
		if (node != null) {
			panel4SnmpMoidDetail.displayDetail(node);
			((StackLayout) dataPenel.getLayout()).topControl = panel4SnmpMoidDetail;
		} else {
			((StackLayout) dataPenel.getLayout()).topControl = null;
		}

		dataPenel.layout();
	}

	public void refresh() {
		panel4SnmpMoidList.refresh();
		displayDetail(null);
	}

	private void addNe_info(EMP_MODEL emp_model, MIB_NODE node) {
		if (node.getMacroType() == MIB_MACRO_TYPE.OBJECT_IDENTIFIER) {
			EMP_MODEL_NE_INFO ne_info_def = emp_model.addNe_info();
			ne_info_def.setName(node.getName());
			for (MIB_NODE aaa : MIB_REPOSITORY.getInstance().getChilds(node)) {
				if (aaa.isScalar()) {
					SNMP_TYPE type_remote = getType_remote(aaa.getSyntax());
					EMP_MODEL_TYPE type_local = getType_local(aaa.getSyntax(), type_remote);
					EMP_MODEL_ENUM enum_def = getEnum(emp_model, aaa.getName(), aaa.getSyntax());

					EMP_MODEL_NE_INFO_FIELD ne_info_field_def = ne_info_def.addNe_info_field();
					ne_info_field_def.setName(aaa.getName());
					ne_info_field_def.setOid(UtilString.format("{}.0", aaa.getOidString()));
					ne_info_field_def.setType_remote(type_remote == null ? "" : type_remote.name());
					ne_info_field_def.setType_local(type_local);
					ne_info_field_def.setEnum_code(enum_def == null ? 0 : enum_def.getCode());
					ne_info_field_def.setIndex(false);
					ne_info_field_def.setRead(aaa.isRead());
					ne_info_field_def.setUpdate(aaa.isWrite());
				}
			}
		} else if (node.isTable()) {
			EMP_MODEL_NE_INFO ne_info_def = emp_model.addNe_info();
			ne_info_def.setName(node.getName());
			for (MIB_NODE entry : MIB_REPOSITORY.getInstance().getChilds(node)) {
				if (entry.isRow()) {
					StringBuilder index = new StringBuilder();
					for (int i = 0; i < entry.getIndexs().length; i++) {
						index.append(index.length() == 0 ? "" : ".").append("*");
					}

					for (MIB_NODE column : MIB_REPOSITORY.getInstance().getChilds(entry)) {
						if (column.isColumn()) {
							SNMP_TYPE type_remote = getType_remote(column.getSyntax());
							EMP_MODEL_TYPE type_local = getType_local(column.getSyntax(), type_remote);
							EMP_MODEL_ENUM enum_def = getEnum(emp_model, column.getName(), column.getSyntax());

							EMP_MODEL_NE_INFO_FIELD ne_info_field_def = ne_info_def.addNe_info_field();
							ne_info_field_def.setName(column.getName());
							ne_info_field_def.setOid(UtilString.format("{}.{}", column.getOidString(), index));
							ne_info_field_def.setType_remote(type_remote == null ? "" : type_remote.name());
							ne_info_field_def.setType_local(type_local);
							ne_info_field_def.setEnum_code(enum_def == null ? 0 : enum_def.getCode());
							ne_info_field_def.setIndex(column.isIndex());
							ne_info_field_def.setRead(column.isRead());
							ne_info_field_def.setUpdate(column.isWrite());
						}
					}
				}
			}
		}
		listener.showPage(EMP_MODEL.NE_INFO);
	}

	private void addEnum(EMP_MODEL emp_model, MIB_NODE node) {
		if (node.getMacroType() == MIB_MACRO_TYPE.OBJECT_IDENTIFIER) {
			EMP_MODEL_ENUM enum_def = emp_model.addEnum();
			enum_def.setName(node.getName());
			for (MIB_NODE aaa : MIB_REPOSITORY.getInstance().getChilds(node)) {
				if (aaa.getMacroType() == MIB_MACRO_TYPE.OBJECT_IDENTIFIER) {
					EMP_MODEL_ENUM_FIELD enum_field_def = enum_def.addEnum_field();
					enum_field_def.setName(aaa.getName());
					enum_field_def.setValue(aaa.getOidString());
				}
			}
		}
		listener.showPage(EMP_MODEL.ENUM);
	}

	private void addAll(EMP_MODEL emp_model, MIB_NODE node) {
		if (MIB_REPOSITORY.getInstance().isModel(node, EMP_MODEL_NE_INFO.class)) {
			addNe_info(emp_model, node);
		} else if (MIB_REPOSITORY.getInstance().isModel(node, EMP_MODEL_ENUM.class)) {
			addEnum(emp_model, node);
		}

		for (MIB_NODE child : MIB_REPOSITORY.getInstance().getChilds(node)) {
			addAll(emp_model, child);
		}
	}

	private Map<String, SNMP_TYPE> type_remote_map = new HashMap<String, SNMP_TYPE>();
	{
		type_remote_map.put("INTEGER", SNMP_TYPE.INTEGER_32);
		type_remote_map.put("BITS", SNMP_TYPE.OCTET_STRING);
		type_remote_map.put("OCTET STRING", SNMP_TYPE.OCTET_STRING);
		type_remote_map.put("OBJECT IDENTIFIER", SNMP_TYPE.OBJECT_IDENTIFIER);
		type_remote_map.put("IpAddress", SNMP_TYPE.IPADDRESS);
		type_remote_map.put("NetworkAddress", SNMP_TYPE.OCTET_STRING);
		type_remote_map.put("Counter32", SNMP_TYPE.COUNTER_32);
		type_remote_map.put("Counter", SNMP_TYPE.COUNTER_32);
		type_remote_map.put("Counter64", SNMP_TYPE.COUNTER_64);
		type_remote_map.put("Gauge", SNMP_TYPE.GAUGE_32);
		type_remote_map.put("TimeTicks", SNMP_TYPE.TIME_TICKS);
	}

	private Map<String, EMP_MODEL_TYPE> type_local_map = new HashMap<String, EMP_MODEL_TYPE>();
	{
		type_local_map.put("DateAndTime", EMP_MODEL_TYPE.DATE);
	}

	private SNMP_TYPE getType_remote(MIB_SYNTAX syntax) {
		SNMP_TYPE snmp_type = getType_remote(type_remote_map, syntax);
		if (snmp_type == null) {
			DialogMessage.openInfo(getShell(), "입력오류", UtilString.format("알 수 없는 타입 입니다. {}", syntax.getType()));
		}

		return snmp_type;
	}

	private SNMP_TYPE getType_remote(Map<String, SNMP_TYPE> type_remote_map, MIB_SYNTAX syntax) {
		SNMP_TYPE snmp_type = type_remote_map.get(syntax.getType());
		if (snmp_type == null) {
			MIB_SYNTAX syntax_def = MIB_REPOSITORY.getInstance().getSyntax(syntax.getType());
			if (syntax_def != null) {
				snmp_type = getType_remote(type_remote_map, syntax_def);
			} else {
				return null;
			}
		}
		return snmp_type;
	}

	private EMP_MODEL_TYPE getType_local(MIB_SYNTAX syntax, SNMP_TYPE type_remote) {
		if (type_remote == null) {
			return null;
		}
		EMP_MODEL_TYPE type_local = getType_local(type_local_map, syntax);
		if (type_local != null) {
			return type_local;
		}

		EMP_MODEL_TYPE[] type_locals = type_remote.getModel_types();
		return type_locals.length == 0 ? null : type_locals[0];
	}

	private EMP_MODEL_TYPE getType_local(Map<String, EMP_MODEL_TYPE> type_local_map, MIB_SYNTAX syntax) {
		EMP_MODEL_TYPE type_local = type_local_map.get(syntax.getType());
		return type_local;
	}

	private EMP_MODEL_ENUM getEnum(EMP_MODEL emp_model, String name, MIB_SYNTAX syntax) {
		if (syntax.isEnum()) {
			EMP_MODEL_ENUM enum_def = emp_model.addEnum();
			enum_def.setName(name);
			for (Entry<BigInteger, String> entry : syntax.getEnum_map().entrySet()) {
				EMP_MODEL_ENUM_FIELD enum_field_def = enum_def.addEnum_field();
				enum_field_def.setName(entry.getValue());
				enum_field_def.setValue(String.valueOf(entry.getKey()));
			}
			return enum_def;
		}
		return null;
	}

}
