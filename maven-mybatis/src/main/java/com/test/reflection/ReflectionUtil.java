package com.test.reflection;

import com.test.entity.User;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionUtil {

    public static void setPropToEntityFromResultSet(ResultSet resultSet,Object entity) throws SQLException {
        Field[] fields = entity.getClass().getDeclaredFields();

        for(Field field : fields){
            if("String".equals(field.getType().getSimpleName())){
                setPropToBean(entity,field.getName(),resultSet.getString(field.getName()));
            }else if("Long".equals(field.getType().getSimpleName())){
                setPropToBean(entity,field.getName(),resultSet.getLong(field.getName()));
            }else if("Integer".equals(field.getType().getSimpleName())){
                setPropToBean(entity,field.getName(),resultSet.getInt(field.getName()));
            }
        }
    }


    public static void setPropToBean(Object bean,String propName,Object value){
        Field field = null;

        Class<?> clazz = bean.getClass();
        try {
            field = clazz.getDeclaredField(propName);
            field.setAccessible(true);
            field.set(bean,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user = new User();
        setPropToBean(user,"userName","admin");
        System.out.println(user);
    }
}
