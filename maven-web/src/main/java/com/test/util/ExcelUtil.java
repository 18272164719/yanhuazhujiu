package com.test.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private String dataFormat = "m/d/yy h:mm";
    private Map<Workbook, CellStyle> dateStyleMaps = new HashMap();
    private String[] heanders;
    private String[] beannames;
    private Map<String, Boolean> lineMap;
    private Map<String, Map<Object, Object>> formatMap = new HashMap();

    public ExcelUtil() {
    }

    public ExcelUtil(String[] heanders, String[] beannames) {
        this.heanders = heanders;
        this.beannames = beannames;
    }

    public ExcelUtil(String[] heanders, String[] beannames, Map<String, Boolean> lineMap) {
        this.heanders = heanders;
        this.beannames = beannames;
        this.lineMap = lineMap;
    }

    public ExcelUtil(String[] heanders, String[] beannames, Map<String, Boolean> lineMap, Map<String, Map<Object, Object>> formatMap) {
        this.heanders = heanders;
        this.beannames = beannames;
        this.lineMap = lineMap;
        this.formatMap = formatMap;
    }

    public Workbook doExportXLS(List dateList, String sheetname, boolean isEntity, boolean isXLS) throws IOException {
        Workbook wb = null;
        if (isXLS) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }

        if (dateList.size() > 32767) {
            this.createXLSEntityBulk((Workbook)wb, dateList);
        } else {
            Sheet sheet = ((Workbook)wb).createSheet(sheetname);
            this.createXLSHeader((Workbook)wb, sheet);
            if (isEntity) {
                this.createXLSEntity((Workbook)wb, sheet, dateList);
            } else {
                this.createXLS((Workbook)wb, sheet, dateList);
            }
        }

        this.dateStyleMaps.clear();
        return (Workbook)wb;
    }

    public Workbook doExportXLS(List dateList, String sheetname, boolean isEntity, boolean isXLS, File file) throws IOException, InvalidFormatException {
        Workbook wb = null;
        if (isXLS) {
            wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file)));
        } else {
            wb = new XSSFWorkbook(file);
        }

        if (dateList.size() > 32767) {
            this.createXLSEntityBulk((Workbook)wb, dateList);
        } else {
            Sheet sheet = ((Workbook)wb).createSheet(sheetname);
            this.createXLSHeader((Workbook)wb, sheet);
            if (isEntity) {
                this.createXLSEntity((Workbook)wb, sheet, dateList);
            } else {
                this.createXLS((Workbook)wb, sheet, dateList);
            }
        }

        this.dateStyleMaps.clear();
        return (Workbook)wb;
    }

    private void createXLSHeader(Workbook wb, Sheet sheet) {
        for(int i = 0; i < this.heanders.length; ++i) {
            this.setStringValue(wb, sheet, 0, (short)i, this.heanders[i]);
        }

    }

    private void createXLS(Workbook wb, Sheet sheet, List<Map<String, Object>> dateList) {
        for(int i = 1; i <= dateList.size(); ++i) {
            Map<String, Object> object = (Map)dateList.get(i - 1);

            for(int j = 0; j < this.beannames.length; ++j) {
                if (StringUtils.isEmpty(this.beannames[j])) {
                    this.doSetCell(wb, sheet, (short)i, (short)j, "");
                } else {
                    Object value = object.get(this.beannames[j]);
                    Map<Object, Object> format = (Map)this.formatMap.get(this.beannames[j]);
                    if (value != null && format != null) {
                        value = format.get(value);
                    }

                    this.doSetCell(wb, sheet, (short)i, (short)j, value);
                }

                this.doCellLine(wb, sheet, (short)i, (short)j);
            }
        }

    }

    private void doCellLine(Workbook wb, Sheet sheet, int rowNum, int colNum) {
        if (this.lineMap != null && this.lineMap.get(this.beannames[colNum]) != null && ((Boolean)this.lineMap.get(this.beannames[colNum])).booleanValue()) {
            Cell cell = this.getMyCell(sheet, rowNum, colNum);
            CellStyle style = wb.createCellStyle();
            style.setBorderBottom((short)1);
            style.setBottomBorderColor((short)8);
            style.setBorderLeft((short)1);
            style.setLeftBorderColor((short)8);
            style.setBorderRight((short)1);
            style.setRightBorderColor((short)8);
            style.setBorderTop((short)1);
            style.setTopBorderColor((short)8);
            cell.setCellStyle(style);
        }

    }

    private void createXLSEntity(Workbook wb, Sheet sheet, List<Object> dateList) {
        for(int i = 1; i <= dateList.size(); ++i) {
            Object bean = dateList.get(i - 1);

            for(int j = 0; j < this.beannames.length; ++j) {
                BeanWrapper bw = new BeanWrapperImpl(bean);
                if (StringUtils.isEmpty(this.beannames[j])) {
                    this.doSetCell(wb, sheet, (short)i, (short)j, "");
                } else {
                    Object value = bw.getPropertyValue(this.beannames[j]);
                    Map<Object, Object> format = (Map)this.formatMap.get(this.beannames[j]);
                    if (value != null && format != null) {
                        value = format.get(value);
                    }

                    this.doSetCell(wb, sheet, (short)i, (short)j, value);
                }

                this.doCellLine(wb, sheet, (short)i, (short)j);
            }
        }

    }

    private Workbook createXLSEntityBulk(Workbook wb, List<Object> dateList) {
        int sublistIndex = 0;
        int perSheetMaxSize = 32767;

        for(int sheetindex = 1; sublistIndex < dateList.size(); ++sheetindex) {
            List<Object> subList = dateList.subList(sublistIndex, dateList.size());
            Sheet sheet = wb.createSheet("" + sheetindex);
            this.createXLSHeader(wb, sheet);
            long row = 1L;

            for(int i = 1; i <= subList.size(); ++i) {
                ++sublistIndex;
                Object bean = subList.get(i - 1);

                for(int j = 0; j < this.beannames.length; ++j) {
                    BeanWrapper bw = new BeanWrapperImpl(bean);
                    Object value = bw.getPropertyValue(this.beannames[j]);
                    Map<Object, Object> format = (Map)this.formatMap.get(this.beannames[j]);
                    if (value != null && format != null) {
                        format.get(value);
                    }

                    this.doSetCell(wb, sheet, (short)i, (short)j, bw.getPropertyValue(this.beannames[j]));
                }

                ++row;
                if (row > (long)perSheetMaxSize) {
                    break;
                }
            }
        }

        this.dateStyleMaps.clear();
        return wb;
    }

    private void doSetCell(Workbook wb, Sheet sheet, int rowNum, int colNum, Object value) {
        if (value != null) {
            if (value instanceof Number) {
                this.setDoubleValue(sheet, rowNum, colNum, Double.valueOf(value.toString()));
            } else if (value instanceof String) {
                this.setStringValue(wb, sheet, rowNum, colNum, value.toString());
            } else if (value instanceof Date) {
                CellStyle dateStyle = null;
                if (this.dateStyleMaps.containsKey(wb)) {
                    dateStyle = (CellStyle)this.dateStyleMaps.get(wb);
                } else {
                    dateStyle = wb.createCellStyle();
                    this.dateStyleMaps.put(wb, dateStyle);
                }

                this.setDateValue(sheet, dateStyle, rowNum, colNum, (Date)value);
            }
        }

    }

    private void setDoubleValue(Sheet sheet, int rowNum, int colNum, Double value) {
        Cell cell = this.getMyCell(sheet, rowNum, colNum);
        cell.setCellType(0);
        cell.setCellValue(value.doubleValue());
    }

    private void setDateValue(Sheet sheet, CellStyle dateStyle, int rowNum, int colNum, Date value) {
        Cell cell = this.getMyCell(sheet, rowNum, colNum);
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(this.dataFormat));
        cell.setCellStyle(dateStyle);
        cell.setCellValue(value);
    }

    private void setStringValue(Workbook wb, Sheet sheet, int rowNum, int colNum, String value) {
        Cell cell = this.getMyCell(sheet, rowNum, colNum);
        if (rowNum == 0) {
            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBoldweight((short)700);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        cell.setCellType(1);
        cell.setCellValue(value);
    }

    private Cell getMyCell(Sheet sheet, int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (null == row) {
            row = sheet.createRow(rowNum);
        }

        Cell cell = row.getCell((short)colNum);
        if (null == cell) {
            cell = row.createCell((short)colNum);
        }

        return cell;
    }

    public static String getValue(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        } else {
            switch(cell.getCellType()) {
                case 0:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = format.format(date);
                    } else {
                        BigDecimal big = new BigDecimal(cell.getNumericCellValue());
                        value = big.toString();
                        if (null != value && !"".equals(value.trim())) {
                            String[] item = value.split("[.]");
                            if (1 < item.length && "0".equals(item[1])) {
                                value = item[0];
                            }
                        }
                    }
                    break;
                case 1:
                    value = cell.getStringCellValue().toString();
                    break;
                case 2:
                    value = String.valueOf(cell.getNumericCellValue());
                    if (value.equals("NaN")) {
                        value = cell.getStringCellValue().toString();
                    }
                    break;
                case 3:
                    value = "";
                    break;
                case 4:
                    value = " " + cell.getBooleanCellValue();
                    break;
                case 5:
                    value = "";
                    break;
                default:
                    value = cell.getStringCellValue().toString();
            }

            if ("null".endsWith(value.trim())) {
                value = "";
            }

            return value;
        }
    }
}
