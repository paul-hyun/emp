package com.hellonms.platforms.emp_orange.share.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultDocument;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_util.string.UtilString;

public class UTIL_EMP_MODEL {

	public static Map<String, Set<String>> field_map = new LinkedHashMap<String, Set<String>>();
	static {
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("code");
			field_set.add("name");
			field_map.put(EMP_MODEL.ENUM, field_set);
		}
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("name");
			field_set.add("value");
			field_map.put(EMP_MODEL.ENUM_FIELD, field_set);
		}
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("code");
			field_set.add("probable_cause");
			field_set.add("specific_problem");
			field_set.add("alarm");
			field_set.add("audit_alarm");
			field_map.put(EMP_MODEL.EVENT, field_set);
		}
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("code");
			field_set.add("name");
			field_set.add("display_name");
			field_set.add("display_enable");
			field_set.add("protocol");
			field_set.add("monitoring");
			field_set.add("filter_enable");
			field_set.add("fault_enable");
			field_set.add("audit_alarm");
			field_set.add("stat_enable");
			field_set.add("stat_type");
			field_map.put(EMP_MODEL.NE_INFO, field_set);
		}
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("name");
			field_set.add("display_name");
			field_set.add("display_enable");
			field_set.add("unit");
			field_set.add("virtual_enable");
			field_set.add("oid");
			field_set.add("type_remote");
			field_set.add("type_local");
			field_set.add("enum_code");
			field_set.add("index");
			field_set.add("read");
			field_set.add("update");
			field_set.add("stat_label");
			field_set.add("stat_enable");
			field_set.add("chart_default");
			field_set.add("stat_save");
			field_set.add("stat_aggr");
			field_set.add("thr_enable");
			field_set.add("thr_event_code");
			field_set.add("thr_type");
			field_set.add("thr_critical_min");
			field_set.add("thr_critical_max");
			field_set.add("thr_major_min");
			field_set.add("thr_major_max");
			field_set.add("thr_minor_min");
			field_set.add("thr_minor_max");
			field_map.put(EMP_MODEL.NE_INFO_FIELD, field_set);
		}
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("code");
			field_set.add("manufacturer");
			field_set.add("oui");
			field_set.add("product_class");
			field_set.add("ne_oid");
			field_set.add("ne_icon");
			field_map.put(EMP_MODEL.NE, field_set);
		}
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("ne_info_code");
			field_map.put(EMP_MODEL.NE_INFO_CODE, field_set);
		}
		{
			LinkedHashSet<String> field_set = new LinkedHashSet<String>();
			field_set.add("code");
			field_set.add("name");
			field_set.add("oid");
			field_set.add("script");
			field_map.put("SCRIPT", field_set);
		}
	}

	public static void export_excel(byte[] data, File file) throws EmpException {
		FileOutputStream fos = null;
		Workbook workbook = null;
		try {
			ByteArrayInputStream input_stream = new ByteArrayInputStream(data);
			SAXReader reader = new SAXReader();
			Document document = reader.read(input_stream);

			workbook = new XSSFWorkbook();

			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

			CellStyle valueStyle = workbook.createCellStyle();
			valueStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			valueStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			valueStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			valueStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			valueStyle.setWrapText(true);

			Sheet sheet_enum_list = workbook.createSheet(UtilString.format("{}", EMP_MODEL.ENUM_LIST));
			LinkedHashSet<String> key_set = new LinkedHashSet<String>();
			key_set.add("define");
			key_set.addAll(field_map.get(EMP_MODEL.ENUM));
			key_set.addAll(field_map.get(EMP_MODEL.ENUM_FIELD));
			export_sheet(document.getRootElement().element(EMP_MODEL.ENUM_LIST), sheet_enum_list, titleStyle, valueStyle, EMP_MODEL.ENUM, EMP_MODEL.ENUM_FIELD, key_set.toArray(new String[0]));

			Sheet sheet_event_list = workbook.createSheet(UtilString.format("{}", EMP_MODEL.EVENT_LIST));
			key_set.clear();
			key_set.add("define");
			key_set.addAll(field_map.get(EMP_MODEL.EVENT));
			export_sheet(document.getRootElement().element(EMP_MODEL.EVENT_LIST), sheet_event_list, titleStyle, valueStyle, EMP_MODEL.EVENT, null, key_set.toArray(new String[0]));

			Sheet sheet_ne_info_list = workbook.createSheet(UtilString.format("{}", EMP_MODEL.NE_INFO_LIST));
			key_set.clear();
			key_set.add("define");
			key_set.addAll(field_map.get(EMP_MODEL.NE_INFO));
			key_set.addAll(field_map.get(EMP_MODEL.NE_INFO_FIELD));
			export_sheet(document.getRootElement().element(EMP_MODEL.NE_INFO_LIST), sheet_ne_info_list, titleStyle, valueStyle, EMP_MODEL.NE_INFO, EMP_MODEL.NE_INFO_FIELD, key_set.toArray(new String[0]));

			Sheet sheet_ne_list = workbook.createSheet(UtilString.format("{}", EMP_MODEL.NE_LIST));
			key_set.clear();
			key_set.add("define");
			key_set.addAll(field_map.get(EMP_MODEL.NE));
			key_set.addAll(field_map.get(EMP_MODEL.NE_INFO_CODE));
			export_sheet(document.getRootElement().element(EMP_MODEL.NE_LIST), sheet_ne_list, titleStyle, valueStyle, EMP_MODEL.NE, EMP_MODEL.NE_INFO_CODE, key_set.toArray(new String[0]));

			Sheet sheetscript = workbook.createSheet(UtilString.format("{}", "SCRIPT"));
			key_set.clear();
			key_set.add("define");
			key_set.addAll(field_map.get("SCRIPT"));
			export_script(document.getRootElement().element(EMP_MODEL.NE_INFO_LIST), sheetscript, titleStyle, valueStyle, EMP_MODEL.NE_INFO, EMP_MODEL.NE_INFO_FIELD, key_set.toArray(new String[0]));

			fos = new FileOutputStream(file);
			workbook.write(fos);
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void export_sheet(Element element_list, Sheet sheet, CellStyle titleStyle, CellStyle valueStyle, String name_1, String name_2, String[] keys) {
		int rownum = 0;
		int column = 0;
		Row row = null;
		Cell cell = null;

		row = sheet.createRow(rownum++);
		column = 0;

		for (String key : keys) {
			cell = row.createCell(column++);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(key);
		}

		Object[] object_1s = element_list.elements(name_1).toArray();
		for (Object object_1 : object_1s) {
			Element element_1 = (Element) object_1;

			row = sheet.createRow(rownum++);
			column = 0;

			for (String key : keys) {
				cell = row.createCell(column++);
				cell.setCellStyle(valueStyle);
				if (key.equals(keys[0])) {
					cell.setCellValue(element_1.getName());
				} else {
					cell.setCellValue(element_1.attributeValue(key, ""));
				}
			}

			if (name_2 != null) {
				Object[] object_2s = element_1.elements(name_2).toArray();
				for (Object object_2 : object_2s) {
					Element element_2 = (Element) object_2;

					row = sheet.createRow(rownum++);
					column = 0;

					for (String key : keys) {
						cell = row.createCell(column++);
						cell.setCellStyle(valueStyle);
						if (key.equals(keys[0])) {
							cell.setCellValue(element_2.getName());
						} else {
							cell.setCellValue(element_2.attributeValue(key, ""));
						}
					}
				}
			}
		}
	}

	private static void export_script(Element element_list, Sheet sheet, CellStyle titleStyle, CellStyle valueStyle, String name_1, String name_2, String[] keys) {
		int rownum = 0;
		int column = 0;
		Row row = null;
		Cell cell = null;

		row = sheet.createRow(rownum++);
		column = 0;

		for (String key : keys) {
			cell = row.createCell(column++);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(key);
		}

		Object[] object_1s = element_list.elements(name_1).toArray();
		for (Object object_1 : object_1s) {
			Element element_1 = (Element) object_1;

			for (Object script_object : element_1.elements("filter_script")) {
				Element script_filter = (Element) script_object;
				row = sheet.createRow(rownum++);
				column = 0;

				for (String key : keys) {
					cell = row.createCell(column++);
					cell.setCellStyle(valueStyle);
					if (key.equals(keys[0])) {
						cell.setCellValue(script_filter.getName());
					} else if (key.equals("script")) {
						cell.setCellValue(script_filter.getText().trim());
					} else if (script_filter.attribute(key) != null) {
						cell.setCellValue(script_filter.attributeValue(key, ""));
					} else {
						cell.setCellValue(element_1.attributeValue(key, ""));
					}
				}
			}

			for (Object script_object : element_1.elements("event_script")) {
				Element script_event = (Element) script_object;
				row = sheet.createRow(rownum++);
				column = 0;

				for (String key : keys) {
					cell = row.createCell(column++);
					cell.setCellStyle(valueStyle);
					if (key.equals(keys[0])) {
						cell.setCellValue(script_event.getName());
					} else if (key.equals("script")) {
						cell.setCellValue(script_event.getText().trim());
					} else if (script_event.attribute(key) != null) {
						cell.setCellValue(script_event.attributeValue(key, ""));
					} else {
						cell.setCellValue(element_1.attributeValue(key, ""));
					}
				}
			}

			for (Object script_object : element_1.elements("notification")) {
				Element script_event = (Element) script_object;
				row = sheet.createRow(rownum++);
				column = 0;

				for (String key : keys) {
					cell = row.createCell(column++);
					cell.setCellStyle(valueStyle);
					if (key.equals(keys[0])) {
						cell.setCellValue(script_event.getName());
					} else if (key.equals("script")) {
						cell.setCellValue(script_event.getText().trim());
					} else if (script_event.attribute(key) != null) {
						cell.setCellValue(script_event.attributeValue(key, ""));
					} else {
						cell.setCellValue(element_1.attributeValue(key, ""));
					}
				}
			}

			if (name_2 != null) {
				Object[] object_2s = element_1.elements(name_2).toArray();
				for (Object object_2 : object_2s) {
					Element element_2 = (Element) object_2;

					for (Object script_object : element_2.elements("field_script")) {
						Element script_event = (Element) script_object;
						row = sheet.createRow(rownum++);
						column = 0;

						for (String key : keys) {
							cell = row.createCell(column++);
							cell.setCellStyle(valueStyle);
							if (key.equals(keys[0])) {
								cell.setCellValue(script_event.getName());
							} else if (key.equals("script")) {
								cell.setCellValue(script_event.getText().trim());
							} else if (script_event.attribute(key) != null) {
								cell.setCellValue(script_event.attributeValue(key, ""));
							} else if (element_2.attribute(key) != null) {
								cell.setCellValue(element_2.attributeValue(key, ""));
							} else {
								cell.setCellValue(element_1.attributeValue(key, ""));
							}
						}
					}
				}
			}
		}
	}

	public static byte[] import_excel(File file) throws EmpException {
		DefaultDocument document = new DefaultDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement(EMP_MODEL.ROOT);
		root.addElement(EMP_MODEL.ENUM_LIST);
		root.addElement(EMP_MODEL.EVENT_LIST);
		root.addElement(EMP_MODEL.NE_INFO_LIST);
		root.addElement(EMP_MODEL.NE_LIST);

		FileOutputStream fos = null;
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Element element_list = document.getRootElement().element(sheet.getSheetName());
				if (element_list != null) {
					import_sheet(sheet, element_list);
				} else if ("SCRIPT".equals(sheet.getSheetName())) {
					import_script(sheet, document.getRootElement().element(EMP_MODEL.NE_INFO_LIST));
				}
			}

			return document.asXML().getBytes();
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void import_sheet(Sheet sheet, Element element_list) {
		int rowcount = sheet.getLastRowNum() + 1;
		if (rowcount < 1) {
			return;
		}
		int columncount = 0;
		Row row = null;
		Cell cell = null;

		List<String> key_list = new ArrayList<String>();
		row = sheet.getRow(0);
		columncount = row.getLastCellNum() + 1;
		for (int column = 0; column < columncount; column++) {
			cell = row.getCell(column);
			key_list.add(cell == null ? "" : cell.getStringCellValue().trim());
		}

		Element element_1 = null;
		Element element_2 = null;
		Element element_cursor = null;
		Set<String> field_set = null;

		for (int rownum = 1; rownum < rowcount; rownum++) {
			row = sheet.getRow(rownum);
			columncount = row.getLastCellNum();
			cell = row.getCell(0);

			if (cell != null) {
				String name = cell.getStringCellValue().trim();
				if (name.equals(EMP_MODEL.ENUM) || name.equals(EMP_MODEL.EVENT) || name.equals(EMP_MODEL.NE_INFO) || name.equals(EMP_MODEL.NE)) {
					element_1 = element_list.addElement(name);
					element_cursor = element_1;
					field_set = field_map.get(name);
				} else if (element_1 != null && (name.equals(EMP_MODEL.ENUM_FIELD) || name.equals(EMP_MODEL.NE_INFO_FIELD) || name.equals(EMP_MODEL.NE_INFO_CODE))) {
					element_2 = element_1.addElement(name);
					element_cursor = element_2;
					field_set = field_map.get(name);
				} else {
					element_1 = null;
					element_2 = null;
					element_cursor = null;
				}

				for (int column = 1; element_cursor != null && field_set != null && column < columncount; column++) {
					cell = row.getCell(column);
					String key = key_list.get(column);
					if (cell != null && field_set.contains(key)) {
						String value = cell.getStringCellValue().trim();
						if (!UtilString.isEmpty(value)) {
							element_cursor.addAttribute(key, value);
						}
					}
				}
			}
		}
	}

	private static void import_script(Sheet sheet, Element element_list) {
		int rowcount = sheet.getLastRowNum() + 1;
		if (rowcount < 1) {
			return;
		}
		int columncount = 0;
		Row row = null;
		Cell cell = null;

		List<String> key_list = new ArrayList<String>();
		row = sheet.getRow(0);
		columncount = row.getLastCellNum() + 1;
		for (int column = 0; column < columncount; column++) {
			cell = row.getCell(column);
			key_list.add(cell == null ? "" : cell.getStringCellValue().trim());
		}

		for (int rownum = 1; rownum < rowcount; rownum++) {
			row = sheet.getRow(rownum);
			columncount = row.getLastCellNum();
			Cell cell_def = row.getCell(0);
			Cell cell_code = row.getCell(1);
			Cell cell_name = row.getCell(2);
			Cell cell_oid = row.getCell(3);
			Cell cell_script = row.getCell(4);

			Element element_info = null;
			if (cell_def != null && cell_code != null) {
				String code = cell_code.getStringCellValue().trim();
				for (Object object : element_list.elements(EMP_MODEL.NE_INFO)) {
					Element aaa = (Element) object;
					if (aaa.attributeValue("code", "").equals(code)) {
						element_info = aaa;
						break;
					}
				}
			}

			if (cell_def != null && element_info != null && cell_script != null) {
				String def = cell_def.getStringCellValue().trim();
				String script = cell_script.getStringCellValue().trim();
				if (def.equals("filter_script")) {
					element_info.addElement(def).addCDATA(script);
				} else if (def.equals("event_script")) {
					element_info.addElement(def).addCDATA(script);
				} else if (def.equals("notification") && cell_oid != null) {
					element_info.addElement(def).addAttribute("oid", cell_oid.getStringCellValue().trim()).addCDATA(script);
				} else if (def.equals("field_script") && cell_name != null) {
					Element element_info_field = null;
					String name = cell_name.getStringCellValue().trim();
					for (Object object : element_info.elements(EMP_MODEL.NE_INFO_FIELD)) {
						Element aaa = (Element) object;
						if (aaa.attributeValue("name", "").equals(name)) {
							element_info_field = aaa;
							break;
						}
					}
					if (element_info_field != null) {
						element_info_field.addElement(def).addCDATA(script);
					}
				}
			}
		}
	}

}
