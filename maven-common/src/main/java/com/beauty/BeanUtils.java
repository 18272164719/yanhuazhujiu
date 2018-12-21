package com.beauty;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.Set;

public class BeanUtils implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public BeanUtils(){

        Reflections reflections = new Reflections(this,new FieldAnnotationsScanner());

        Set<Field> fields = reflections.getFieldsAnnotatedWith(Resource.class);

        for(Field f : fields){
            try{
                //获取成员变量
                String simpleName = f.getType().getSimpleName();
                //spring管理的bean name首字母都是小写的
                String beanName = simpleName;
                Object bean = applicationContext.getBean(beanName);
                if(bean == null){
                    return;
                }
                f.setAccessible(true);
                f.set(this,bean);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
