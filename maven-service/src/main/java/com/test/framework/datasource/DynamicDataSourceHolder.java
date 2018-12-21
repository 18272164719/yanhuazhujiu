package com.test.framework.datasource;



public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static String READ_DATA_SOURCE = "dataSource2";


    public static void setDataSourceType(String dataSourceType){
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType (){
        return (String)contextHolder.get();
    }

    public static void clearDataSourceType(){
        contextHolder.remove();
    }
}
