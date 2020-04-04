/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Excel Utility
 * </p>
 *
 * @since 1.6
 * @create 2015. 3. 11.
 * @modified 2015. 3. 11.
 * @author jungsun
 *
 */
public class UtilExcel {

	/**
	 * <p>
	 * Excel 저장
	 * </p>
	 *
	 * @param data_excel
	 * @param save_file
	 * @return
	 * @throws EmpException
	 */
	public static byte[] saveExcel(DataExcelIf dataExcel, String title, int rows) throws EmpException {
		Workbook workbook = null;
		try {
			workbook = new SXSSFWorkbook();

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

			Sheet sheet = null;

			Object[] row_objects = dataExcel.getElements(dataExcel);
			int column_count = dataExcel.getColumnCount();
			int page = 0;
			int page_row = 0;
			int rownum = 0;
			for (int row = 0; row < row_objects.length; row++) {
				if (page_row == 0) {
					sheet = workbook.createSheet(UtilString.format("Page-{}", page + 1));
					rownum = 0;
					Row rrr = sheet.createRow(rownum++);
					for (int column = 0; column < column_count; column++) {
						sheet.setColumnWidth(column, dataExcel.getColumnWidth(column) * 34);
						Cell cell = rrr.createCell(column);
						cell.setCellStyle(titleStyle);
						cell.setCellValue(dataExcel.getColumnTitle(column));
					}
				}
				{
					Row rrr = sheet.createRow(rownum++);
					for (int column = 0; column < column_count; column++) {
						Cell cell = rrr.createCell(column);
						cell.setCellStyle(valueStyle);
						cell.setCellValue(dataExcel.getColumnText(row_objects[row], column));
					}
				}
				page_row++;
				if (rows <= page_row) {
					page++;
					page_row = 0;
				}
			}

			if (row_objects.length == 0) {
				sheet = workbook.createSheet(UtilString.format("Page-{}", page + 1));
				rownum = 0;
				Row rrr = sheet.createRow(rownum++);
				for (int column = 0; column < column_count; column++) {
					sheet.setColumnWidth(column, dataExcel.getColumnWidth(column) * 34);
					Cell cell = rrr.createCell(column);
					cell.setCellStyle(titleStyle);
					cell.setCellValue(dataExcel.getColumnTitle(column));
				}
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);

			bos.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static List<Object> toJson(File file, int sheetnum) throws EmpException {
		FileInputStream fis = null;
		Workbook workbook = null;
		try {
			fis = new FileInputStream(file);
			workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(sheetnum);

			List<Object> sheetObject = new ArrayList<Object>();
			for (int rownum = 0; rownum <= sheet.getLastRowNum(); rownum++) {
				Row row = sheet.getRow(rownum);

				List<Object> rowObject = new ArrayList<Object>();
				if (row != null) {
					for (int cellnum = 0; cellnum < row.getLastCellNum(); cellnum++) {
						Cell cell = row.getCell(cellnum);
						String cellObject = "";
						if (cell != null) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								cellObject = cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_NUMERIC:
								cellObject = String.valueOf((long) cell.getNumericCellValue());
								break;
							default:
								cellObject = String.valueOf(cell);
								break;
							}
						}
						rowObject.add(cellObject);
					}
				}
				sheetObject.add(rowObject);
			}
			return sheetObject;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static byte[] saveExcel(List<Object> json) throws EmpException {
		Workbook workbook = null;
		try {
			workbook = new SXSSFWorkbook();
			Sheet sheet = workbook.createSheet();

			for (int rownum = 0; rownum < json.size(); rownum++) {
				@SuppressWarnings("unchecked")
				List<Object> rowJson = (List<Object>) json.get(rownum);
				Row rowSheet = sheet.createRow(rownum);

				for (int cellnum = 0; cellnum < rowJson.size(); cellnum++) {
					Object value = rowJson.get(cellnum);
					rowSheet.createCell(cellnum).setCellValue(value == null ? "" : String.valueOf(value));
				}
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);

			bos.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
