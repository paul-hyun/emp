package com.hellonms.platforms.emp_orange.share.parser.snmp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_NODE.MIB_MACRO_TYPE;
import com.hellonms.platforms.emp_util.file.UtilFile;
import com.hellonms.platforms.emp_util.string.UtilString;

public class MIB_REPOSITORY {

	private static final MIB_REPOSITORY instance = new MIB_REPOSITORY();
	private static final String[] resources = { //
	"/com/hellonms/platforms/emp_orange/share/parser/snmp/RFC1155-SMI.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/RFC1157_SNMP.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/RFC-1212.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/RFC1213-MIB.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/RFC-1215.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/SNMPv2-SMI.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/SNMPv2-TC.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/SNMPv2-CONF.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/SNMPv2-MIB.mib", //
			"/com/hellonms/platforms/emp_orange/share/parser/snmp/HOST-RESOURCES-MIB.mib", //
	};
	static {
		try {
			for (String resource : resources) {
				InputStream in = MIB_REPOSITORY.class.getResourceAsStream(resource);
				instance.load_mib(in, resource);
				in.close();
			}
			instance.analysis();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MIB_REPOSITORY getInstance() {
		return instance;
	}

	private Map<String, MIB_MODULE_DEFINITIONS> module_definition_map = new LinkedHashMap<String, MIB_MODULE_DEFINITIONS>();

	private Map<String, MIB_SYNTAX> syntax_map = new LinkedHashMap<String, MIB_SYNTAX>();

	private TreeMap<Long[], MIB_NODE> oid_map = new TreeMap<Long[], MIB_NODE>(new Comparator<Long[]>() {
		@Override
		public int compare(Long[] o1, Long[] o2) {
			for (int i = 0; i < o1.length && i < o2.length; i++) {
				if (o1[i] < o2[i]) {
					return -1;
				} else if (o1[i] > o2[i]) {
					return 11;
				}
			}

			if (o1.length < o2.length) {
				return -1;
			} else if (o1.length > o2.length) {
				return 1;
			}

			return 0;
		}
	});

	private TreeMap<Long[], MIB_NODE[]> oid_child_map = new TreeMap<Long[], MIB_NODE[]>(new Comparator<Long[]>() {
		@Override
		public int compare(Long[] o1, Long[] o2) {
			for (int i = 0; i < o1.length && i < o2.length; i++) {
				if (o1[i] < o2[i]) {
					return -1;
				} else if (o1[i] > o2[i]) {
					return 11;
				}
			}

			if (o1.length < o2.length) {
				return -1;
			} else if (o1.length > o2.length) {
				return 1;
			}

			return 0;
		}
	});

	private MIB_REPOSITORY() {
		oid_map.put(MIB_NODE.ROOT.getOidArray(), MIB_NODE.ROOT);
	}

	public void load_mib(File file) throws EmpException {
		try {
			String data = UtilFile.readFile(file, "UTF-8");
			InputStream in = new ByteArrayInputStream(data.getBytes());
			load_mib(in, file.getAbsolutePath());
		} catch (EmpException e) {
			throw e;
		} catch (Throwable e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO, file.getName(), e.getMessage());
		}
	}

	public void load_mib(InputStream in, String name) throws EmpException {
		try {
			Parser4MIB parser = new Parser4MIB(in);
			parser.ReInit(in);
			MIB_MODULE_DEFINITIONS[] module_definitions = parser.mib();
			in.close();

			if (module_definitions.length == 0) {
				System.out.println(UtilString.format("{} : {}", name, module_definitions.length));
			}
			for (MIB_MODULE_DEFINITIONS module_definition : module_definitions) {
				module_definition_map.put(module_definition.getName(), module_definition);
			}
		} catch (Throwable e) {
			throw new EmpException(e, ERROR_CODE_ORANGE.FILE_IO, name, e.getMessage());
		}
	}

	public String analysis() {
		List<String> error = new ArrayList<String>();

		clearCache();
		analysis(error);
		clearCache();

		if (0 < error.size()) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < error.size(); i++) {
				stringBuilder.append("\n").append(error.get(i));
			}
			return stringBuilder.toString();
		} else {
			return null;
		}
	}

	private void clearCache() {
		oid_child_map.clear();
	}

	private void analysis(List<String> error) {
		for (MIB_MODULE_DEFINITIONS module_definition : module_definition_map.values().toArray(new MIB_MODULE_DEFINITIONS[0])) {
			module_definition.analysisBasic(error);
		}
		for (MIB_MODULE_DEFINITIONS module_definition : module_definition_map.values().toArray(new MIB_MODULE_DEFINITIONS[0])) {
			module_definition.analysisTree(this, error);
		}
		for (MIB_MODULE_DEFINITIONS module_definition : module_definition_map.values().toArray(new MIB_MODULE_DEFINITIONS[0])) {
			MIB_SYNTAX[] syntaxs = module_definition.getSyntaxs();
			for (MIB_SYNTAX syntax : syntaxs) {
				syntax_map.put(syntax.getNick_name(), syntax);
			}

			MIB_NODE[] nodes = module_definition.getNodes();
			for (MIB_NODE node : nodes) {
				if (node.getParent() == null && !UtilString.isEmpty(node.getParent_name()) && node.getParent_name().equals(MIB_NODE.ROOT.getName())) {
					node.setParent(MIB_NODE.ROOT);
					oid_map.put(node.getOidArray(), node);
				} else if (node.isRooted()) {
					oid_map.put(node.getOidArray(), node);
				} else if (node.getParent() == null && !UtilString.isEmpty(node.getParent_name())) {
					error.add(UtilString.format("{} - {} IMPORT error: {}", module_definition.getName(), node.getName(), node.getParent_name()));
				}
			}
		}
	}

	MIB_MODULE_DEFINITIONS getMODULE_DEFINITIONS(String name) {
		return module_definition_map.get(name);
	}

	public MIB_NODE getRoot() {
		return MIB_NODE.ROOT;
	}

	public MIB_NODE[] getChilds(MIB_NODE node) {
		Long[] key = node.getOidArray();
		MIB_NODE[] childs = oid_child_map.get(key);
		if (childs == null) {
			List<MIB_NODE> oid_list = new ArrayList<MIB_NODE>();
			for (MIB_NODE child : oid_map.values()) {
				MIB_NODE parent = child.getParent();
				if (parent != null) {
					if (node.equals(parent)) {
						oid_list.add(child);
					}
				}
			}
			childs = oid_list.toArray(new MIB_NODE[0]);
			oid_child_map.put(key, childs);
		}
		return childs;
	}

	public MIB_SYNTAX getSyntax(String name) {
		return syntax_map.get(name);
	}

	public boolean isModel(MIB_NODE oid, Class<?> clazz) {
		switch (oid.getMacroType()) {
		case OBJECT_IDENTIFIER:
			if (EMP_MODEL_NE_INFO.class == clazz) {
				MIB_NODE[] childs = getChilds(oid);
				for (MIB_NODE child : childs) {
					if (child.getMacroType() == MIB_MACRO_TYPE.OBJECT_TYPE && child.isScalar()) {
						return true;
					}
				}
			} else if (EMP_MODEL_ENUM.class == clazz) {
				MIB_NODE[] childs = getChilds(oid);
				if (childs.length == 0) {
					return false;
				}
				for (MIB_NODE child : childs) {
					if (child.getMacroType() != MIB_MACRO_TYPE.OBJECT_IDENTIFIER || getChilds(child).length != 0) {
						return false;
					}
				}
				return true;
			}
			return false;
		case OBJECT_TYPE:
			if (EMP_MODEL_NE_INFO.class == clazz) {
				return oid.isTable();
			} else if (EMP_MODEL_NE_INFO_FIELD.class == clazz) {
				return oid.isScalar() || oid.isColumn();
			}
		default:
			return false;
		}
	}

	public boolean hasModel(MIB_NODE oid) {
		MIB_NODE[] childs = getChilds(oid);

		switch (oid.getMacroType()) {
		case OBJECT_IDENTIFIER:
			for (MIB_NODE child : childs) {
				if (hasModel(child)) {
					return true;
				}
			}

			return false;
		case OBJECT_TYPE:
			if (oid.isTable()) {
				return true;
			} else if (oid.isScalar() || oid.isColumn()) {
				return true;
			}
		default:
			return false;
		}
	}

}
