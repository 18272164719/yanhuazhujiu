package com.test.framework.aspect;

import com.test.annotation.ProjectCodeFlag;
import com.test.framework.datasource.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DataSourceAspect {

    private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    public void before (JoinPoint joinPoint){
        /*Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();*/

        Object[] args = joinPoint.getArgs();
        Object obj = args[0].toString();

        logger.info("前置切点 ---------"+args[0].toString());
        if(obj != null){
            DynamicDataSourceHolder.setDataSourceType(obj.toString());
        }else{
            DynamicDataSourceHolder.setDataSourceType(DynamicDataSourceHolder.READ_DATA_SOURCE);
        }
    }

    public Object intercept(ProceedingJoinPoint joinPoint)throws Throwable {
        logger.info("环绕通知方法 参数为："+Arrays.toString(joinPoint.getArgs()));
        Object result = null;
        try{
            long start = System.currentTimeMillis();
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = null;
            methodSignature = (MethodSignature)signature;
            Method method = methodSignature.getMethod();

            Annotation[][] an = method.getParameterAnnotations();
            int index = -1;
            for(int i = 0 ; i< an.length ; i++){
                Annotation[] var5 = an[i];
                int var6 = var5.length;
                for(int j = 0; j < var6 ; j++){
                    Annotation annotation = var5[j];
                    logger.info(ProjectCodeFlag.class.isAssignableFrom(annotation.getClass())+"");
                    logger.info("注解一"+ProjectCodeFlag.class);
                    logger.info("注解二"+annotation.annotationType());
                    if(ProjectCodeFlag.class.isAssignableFrom(annotation.annotationType())){
                        index = i;
                    }
                }
            }
            Object[] args = joinPoint.getArgs();
            Object obj = null;
            if(index != -1){
                obj = args[index].toString();
            }
            if(obj != null){
                DynamicDataSourceHolder.setDataSourceType(obj.toString());
            }else{
                DynamicDataSourceHolder.setDataSourceType(DynamicDataSourceHolder.READ_DATA_SOURCE);
            }
            //有返回参数 则需返回值
            result =  joinPoint.proceed();
            long end = System.currentTimeMillis();
            System.out.println("总共执行时长" + (end - start) + " 毫秒");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
