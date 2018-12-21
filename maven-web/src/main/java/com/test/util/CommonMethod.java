package com.test.util;

import com.test.util.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonMethod {

	public static String getArrayToStr(String array) {
		if(array == null) {
			return null;
		}
		array = array.substring(1, array.length()-1);
		array = array.replaceAll("\"", "");
		return array;
	}
	
    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    //获取2个集合交集
    public static List<Long> getIntersectionList(List<Long> list1, List<Long> list2) {
        List<Long> list = new ArrayList<>();
        if(list1 != null && list2 != null){
            list.addAll(CollectionUtils.intersection(list1, list2));
            return list;
        }else if(list1 == null && list2 == null){
            return null;
        }else if(list1 != null){
            return list1;
        }else{
            return list2;
        }
    }

    //获取新的编码
    public static String getNewCode(String oldCode) {
        if(StringUtils.isNotEmpty(oldCode)){
            String newCode = oldCode;
            if(oldCode.substring(oldCode.length()-1,oldCode.length()).matches("\\d")){
                String codeStr = oldCode.replaceAll("[^\\d]+","|");
                String[] codeArr = codeStr.split("\\|");
                String s = (Integer.parseInt(codeArr[codeArr.length-1])+1)+"";
                int len = codeArr[codeArr.length-1].length();
                newCode = oldCode.substring(0,oldCode.length() - len) + String.format("%0"+ (len) +"d", Integer.parseInt(s));
            }
            return newCode;
        }else {
            return "";
        }
    }

    // 取得随机数
    public static String getRandomNum(int num) {
        Random ran = new Random();
        String code = "0123456789";
        StringBuffer cc = new StringBuffer();
        for (int i = 0; i < num; i++) {
            cc.append(code.charAt(ran.nextInt(code.length())));
        }
        return cc.toString();
    }

    // 获得某一天的零点
    public static Date getSpecifiedDay(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, num);
        return cal.getTime();
    }

    // 获得某一天的零点
    public static String getSpecifiedDayStr(Date date, int num) {
        Date newdate = getSpecifiedDay(date, num);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("sdf.format(newdate)=" + sdf.format(newdate));
        return sdf.format(newdate);

    }

    // 根据日期取得全周日期
    public static List<Date> dateToWeek(Date mdate) {
        // int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        // Long fTime = mdate.getTime() - b * 24 * 3600000;
        Long fTime = mdate.getTime();
        for (int a = 0; a <= 6; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(fdate);
            System.out.println(fdate);
        }
        return list;
    }

    // 转换日期为UTC时间
    public static long tranDateToUTCTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

        return calendar.getTimeInMillis();
    }

    // 转换UTC时间为日期
    public static Date tranUTCTimeToDate(long timeInMillis) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.getTime();
    }

    // 由出生日期获得年龄
    public static int getAge(String strDate,boolean isSplit) {
        int age = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(isSplit==true){
                sdf=new SimpleDateFormat("yyyyMMdd");
            }
            Date birthDay = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

            cal.setTime(birthDay);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            age = yearNow - yearBirth;

            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth)
                        age--;
                } else {
                    age--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }

    public static String formatDate(String date) {
        String[] time = date.split("-");
        if (time[1].length() == 1) {
            time[1] = "0" + time[1];
        }
        if (time[2].length() == 1) {
            time[2] = "0" + time[1];
        }
        return time[0] + "-" + time[1] + "-" + time[2];
    }

    // 将Excel转换成可以保存的List
    public static String[][] tranExcelToList(MultipartFile file) throws Exception {
        String[][] upExcel = null;
        //一次只读500条
        InputStream in = null;
        Workbook wb = null;
        try{
            //获取文件流
            in = file.getInputStream();
            //防止修改文件扩展名
            try{
                wb = new HSSFWorkbook(in);
            }catch (Exception e){
                in = file.getInputStream();
                wb = new XSSFWorkbook(in);
            }
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                // i = 0 是标题栏
                for (int i = 1; i <= sheet.getPhysicalNumberOfRows() - 1; i++) {
                    Row row0 = sheet.getRow(0);
                    Row row = sheet.getRow(i);
                    if(row == null){
                        break;
                    }
                    if (upExcel == null) {
                        upExcel = new String[sheet.getPhysicalNumberOfRows() - 1][row0.getPhysicalNumberOfCells()];
                    }
                    for (int j = 0; j < row0.getPhysicalNumberOfCells(); j++) {
                        Cell cell = row.getCell(j);
                        String cellStr = ExcelUtil.getValue(cell);
                        upExcel[i - 1][j] = cellStr;
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导入失败");
        } finally {
            if (wb!=null) {
                try {
                    wb.close();
                } catch (IOException e) {
                }
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return upExcel;
    }

    /**
     * 将List转换成可以保存的Excel
     * @param list
     * @param heanders 字段名
     * @param beannames 表头名
     * @return
     * @throws Exception
     */
    public static File writeListToExcel(List<?> list,String path,String[] heanders,String[] beannames,Boolean isEntity) throws Exception{
        //创建文件
        File file = new File(path);
        file.createNewFile();
        try{
            ExcelUtil util = new ExcelUtil(beannames, heanders);
            Workbook workbook = util.doExportXLS(list, "sheet1", isEntity, true);
            OutputStream out = new FileOutputStream(path);
            workbook.write(out);// 写入File
            out.flush();
            workbook.close();
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    //下载图片
    public static String downloadPicture(String urlString) {
        String imageName = null;
        try {
            URL url = new URL(urlString);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            imageName = "/tmp/" + UUID.randomUUID().toString() + ".jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            dataInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageName;
    }

    //把list里面的数据 根据特定字段去重
    public static List<Object> removeRepeat(List<?> list,Object... args){
        Map<String,Object> map = new HashMap<>();
        try{
            Object[] arg1 = args;
            for(Object obj : list){
                String key = "";
                for(int i=0; i<arg1.length; i++){
                    Field field = obj.getClass().getField(arg1[i].toString());
                    key += field.get(obj).toString()+"-";
                }
                if(null == map.get(key)){
                    map.put(key,obj);
                }
            }
            List<Object> objectArrayList = new ArrayList<>();
            Collection<Object> c = map.values();
            objectArrayList.addAll(c);
            return objectArrayList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getSQL(Map<String, Object> condition, String prefix, Long storeId) {
		String type = (String)condition.get("type");
		String goodsLike = (String)condition.get("goodsLike");
		String prescription = (String)condition.get("prescription");
		Boolean isEphedrine = (Boolean)condition.get("isEphedrine");
		Boolean isNotStock = (Boolean)condition.get("isNotStock");
		Boolean isLimitSale = (Boolean)condition.get("isLimitSale");
		Boolean isNetLimitSale = (Boolean)condition.get("isNetLimitSale");
		Boolean isImport = (Boolean)condition.get("isImport");
		Boolean isInsurance = (Boolean)condition.get("isInsurance");
		@SuppressWarnings("unchecked")
		Map<String, String> goodsCategoryMap = (Map<String, String>)condition.get("goodsCategoryIds");
		
		String s = "";
		for(String key:goodsCategoryMap.keySet()) {
			String goodsCategoryIds = goodsCategoryMap.get(key);
			s += " and "+prefix+".goodsId in (select goodsId from b_goodsCategoryGoods where goodsCategoryId in ("+goodsCategoryIds+"))";
		}		    	
    	if(goodsLike != null 
    			||type != null
    			||prescription != null
    			||isEphedrine != null
    			||isNotStock != null
    			||isLimitSale != null
    			||isNetLimitSale != null
    			||isImport != null
    			||isInsurance != null) {
    		
    		s += " and "+prefix+".goodsId in (select id from b_goods where storeId="+storeId;
    		if(goodsLike != null) {
        		s += " and (code like '"+goodsLike+"%' or name like '"+goodsLike+"%' or pinyin like '"+goodsLike+"%') ";
    		}
    		if(type != null) {
                s += " and type = '"+type+"' ";
            }
            if(prescription != null) {
                s += " and prescription = '"+prescription+"' ";
            }
            if(isEphedrine != null) {
                s += " and isEphedrine = "+isEphedrine;
            }
            if(isNotStock != null) {
                s += " and isNotStock = "+isNotStock;
            }
            if(isLimitSale != null) {
                s += " and isLimitSale = "+isLimitSale;
            }
            if(isNetLimitSale != null) {
                s += " and isNetLimitSale = "+isNetLimitSale;
            }
            if(isImport != null) {
                s += " and isImport = "+isImport;
            }
            if(isInsurance != null) {
                s += " and isInsurance = "+isInsurance;
            }
    		s += ")";
    	}
		
		return s;
	}
    
    public static String getHQL(Map<String, Object> condition, String prefix) {
		String type = (String)condition.get("type");
		String goodsLike = (String)condition.get("goodsLike");
		String prescription = (String)condition.get("prescription");
		Boolean isEphedrine = (Boolean)condition.get("isEphedrine");
		Boolean isNotStock = (Boolean)condition.get("isNotStock");
		Boolean isLimitSale = (Boolean)condition.get("isLimitSale");
		Boolean isNetLimitSale = (Boolean)condition.get("isNetLimitSale");
		Boolean isImport = (Boolean)condition.get("isImport");
		Boolean isInsurance = (Boolean)condition.get("isInsurance");
		@SuppressWarnings("unchecked")
		Map<String, String> goodsCategoryMap = (Map<String, String>)condition.get("goodsCategoryIds");
		
		String s = "";
		for(String key:goodsCategoryMap.keySet()) {
			String goodsCategoryIds = goodsCategoryMap.get(key);
			s += " and "+prefix+".id in (select goods.id from GoodsCategoryGoods where goodsCategory.id in ("+goodsCategoryIds+"))";
		}		
        if(goodsLike != null) {
            s += " and ("+prefix+".code like '"+goodsLike+"%' or "+prefix+".name like '"+goodsLike+"%' or "+prefix+".pinyin like '"+goodsLike+"%') ";
        }
        if(type != null) {
            s += " and "+prefix+".type = '"+type+"' ";
        }
        if(prescription != null) {
            s += " and "+prefix+".prescription = '"+prescription+"' ";
        }
        if(isEphedrine != null) {
            s += " and "+prefix+".isEphedrine = "+isEphedrine;
        }
        if(isNotStock != null) {
            s += " and "+prefix+".isNotStock = "+isNotStock;
        }
        if(isLimitSale != null) {
            s += " and "+prefix+".isLimitSale = "+isLimitSale;
        }
        if(isNetLimitSale != null) {
            s += " and "+prefix+".isNetLimitSale = "+isNetLimitSale;
        }
        if(isImport != null) {
            s += " and "+prefix+".isImport = "+isImport;
        }
        if(isInsurance != null) {
            s += " and "+prefix+".isInsurance = "+isInsurance;
        }
       
		return s;
	}
    
    public static void main(String[] args) {
    }
}