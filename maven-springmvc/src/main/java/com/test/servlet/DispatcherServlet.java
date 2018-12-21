package com.test.servlet;

import com.test.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 手写springmvc   requestParam 使用策略模式  responseBody 注解
 */
public class DispatcherServlet extends HttpServlet{

    private static final List<String> classNames = new ArrayList<>();

    private static final Map<String,Object> iocMap = new ConcurrentHashMap<>();

    private static final Map<String,Object> handlerMappingMap = new ConcurrentHashMap<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、类全限定名，反射  得到实例  保存起来   扫描所有的controller和service 注解的类
        scanPackages("com.test");
        //2、controller对象 - 》 method //spring容器注册了实例   将对象放到map里面去
        getInstance();
        //3、处理对象之间的依赖关系ioc   将controller的autowired注解注入进去
        ioc();
        //4、Url 与method 隐射关系 //handlermapping  处理请求里面的url  找到对应的方法
        handlerMapping();


    }

    private void scanPackages(String packagesName) {
        //扫描包  当然这里也可以用Reflections 进行 扫描
        URL url = this.getClass().getClassLoader().getResource("/"+packagesName.replaceAll("\\.","/"));
        String fileStr = url.getFile();
        File file = new File(fileStr);

        String[] filesStr = file.list();
        for(String path : filesStr){

            File filePath = new File(fileStr+path);
            //判断该文件是不是文件夹  是就递归
            if(filePath.isDirectory()){
                scanPackages(packagesName+"."+path);
            }else{
                classNames.add(packagesName+"."+filePath.getName());
            }
        }

    }

    private void getInstance() {
        for(String className : classNames){
            String cn = className.replace(".class","");

            try {
                Class<?> clazz = Class.forName(cn);

                if(clazz.isAnnotationPresent(MyController.class)){
                    MyRequestMapping annotation = clazz.getAnnotation(MyRequestMapping.class);
                    String classUrl = annotation.value();
                    iocMap.put(classUrl,clazz.newInstance());
                }else if(clazz.isAnnotationPresent(MyService.class)){
                    String beaName = Util.toLowerFirstWord(clazz.getSimpleName());
                    iocMap.put(beaName,clazz.newInstance());
                }else{
                    continue;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void ioc(){
        for(Map.Entry<String,Object> entry : iocMap.entrySet()){
            Object obj = entry.getValue();
            Class<?> clazz = obj.getClass();

            if(clazz.isAnnotationPresent(MyController.class)){
                Field[] fields = clazz.getDeclaredFields();

                for(Field field : fields){
                    if(field.isAnnotationPresent(MyAutowired.class)){
                        String filedName = field.getName();
                        Object bean = iocMap.get(filedName);
                        if(bean == null){
                            continue;
                        }
                        field.setAccessible(true);
                        try {
                            field.set(obj,bean);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void handlerMapping(){
        for(Map.Entry<String,Object> entry : iocMap.entrySet()){
            Object obj = entry.getValue();

            Class<?> clazz = obj.getClass();

            if(clazz.isAnnotationPresent(MyController.class)){
                MyRequestMapping requestMapping = clazz.getAnnotation(MyRequestMapping.class);
                //类的url
                String classUrl = requestMapping.value();

                Method[] methods = clazz.getMethods();

                for(Method method : methods){
                    if(method.isAnnotationPresent(MyRequestMapping.class)){
                        MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
                        //方法的url
                        String methodUrl = annotation.value();

                        handlerMappingMap.put(classUrl+methodUrl,method);

                    }
                }
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI(); //maven-springmvc/home/query
        //去掉项目名称
        String requestUrl = url.replace(req.getContextPath(),"");

        Object controlelr = iocMap.get("/"+requestUrl.split("/")[1]);

        Method method = (Method) handlerMappingMap.get(requestUrl);

        Object[] args = hand(req,resp,method);
        try {
            method.invoke(controlelr,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private static Object[] hand(HttpServletRequest req, HttpServletResponse resp, Method method) {
        //获取所有的参数
        Class<?>[] parameterTypes = method.getParameterTypes();

        Object[] args = new Object[parameterTypes.length];

        int args_i = 0;
        int index = 0;
        for(Class<?> clazz : parameterTypes){
            if(ServletRequest.class.isAssignableFrom(clazz)){
                args[args_i++] = req;
            }
            if(ServletResponse.class.isAssignableFrom(clazz)){
                args[args_i++] = resp;
            }
            Annotation[] parameterAnnotations = method.getParameterAnnotations()[index];
            if(parameterAnnotations.length > 0){
                for(Annotation annotation : parameterAnnotations){
                    if(MyRequestParam.class.isAssignableFrom(annotation.getClass())){
                        MyRequestParam myRequestParam = (MyRequestParam)annotation;
                        //找到注解里面的nage和age
                        args[args_i++] = req.getParameter(myRequestParam.value());
                    }
                }
            }
            index++;
        }
        return args;
    }


}
