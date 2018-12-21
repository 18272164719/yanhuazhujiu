package com.test.framework.argumentsResolver;

import com.alibaba.fastjson.JSON;
import com.shyl.common.entity.Entity;
import com.test.framework.annotation.RequestJson2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestJson2ArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJson2.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(HttpServletRequest.class);
        String parameterName = parameter.getParameterName();
        Class<?> fieldClazz = parameter.getParameterType();
        Iterator var8;
        Map.Entry entry;
        Map<String, String[]> parameterMap = webRequest.getParameterMap();

        if (!MultiValueMap.class.isAssignableFrom(fieldClazz)) {
            Map<String, String> result = new LinkedHashMap(parameterMap.size());
            var8 = parameterMap.entrySet().iterator();
            while(var8.hasNext()) {
                entry = (Map.Entry)var8.next();
                if (((String[])entry.getValue()).length > 0) {
                    result.put((String) entry.getKey(), ((String[])entry.getValue())[0]);
                }
            }
            return result;
        }else {
            MultiValueMap<String, String> result = new LinkedMultiValueMap(parameterMap.size());
            var8 = parameterMap.entrySet().iterator();

            while(var8.hasNext()) {
                entry = (Map.Entry)var8.next();
                String[] var10 = (String[])entry.getValue();
                int var11 = var10.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String value = var10[var12];
                    result.add((String)entry.getKey(), value);
                }
            }

            return result;
        }

        /*String fjs = request.getParameter(parameterName);

        if (StringUtils.isBlank(fjs)) {
            return null;
        } else {
            Map<String, Object> bodyMap = (Map) JSON.parseObject(fjs, Map.class);
            if (Entity.class.isAssignableFrom(fieldClazz)) {
                Object object = fieldClazz.newInstance();
                setObjectValue(object, bodyMap);
                return object;
            } else {
                Object obj = bodyMap.get(parameterName);
                if (obj == null) {
                    return null;
                } else {
                    if (fieldClazz == String.class) {
                        return obj.toString();
                    } else if (fieldClazz == Long.class) {
                        return Long.parseLong(obj.toString());
                    } else if (fieldClazz == Integer.class) {
                        return Integer.parseInt(obj.toString());
                    } else {
                        return fieldClazz == BigDecimal.class ? new BigDecimal(obj.toString()) : obj;
                    }
                }
            }
        }*/
    }

    private void setObjectValue(Object obj, Map<String, Object> params)
            throws SecurityException,
            IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
        if (null == obj) {
            return;
        }
        if (null == params) {
            return;
        }
        Class<?> clazz = obj.getClass();
        for (Iterator it = params.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            String key = entry.getKey();
            Object propertyValue = entry.getValue();
            if (null == propertyValue) {
                continue;
            }
            if (key.contains(".")) {
                String[] key0 = key.split("\\.");
                String pre = key0[0];

                Field f = clazz.getDeclaredField(pre);
                Class c = f.getType();
                if (Entity.class.isAssignableFrom(c)) {
                    try {
                        Object o = c.newInstance();
                        String rel = key0[1];
                        Field f1 = getSpecifiedField(c, rel);
                        if (f1 != null) {
                            f1.setAccessible(true);
                            setForType(f1, o, propertyValue);
                        }
                        f.setAccessible(true);
                        f.set(obj, o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                continue;
            }
            Field name = getSpecifiedField(clazz, key);
            if (name != null) {
                name.setAccessible(true);
                setForType(name, obj, propertyValue);
            }
        }

    }

    private Field getSpecifiedField(Class<?> clazz, String fieldName) {
        Field f = null;
        if (null == clazz) {
            return null;
        }
        try {
            f = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return getSpecifiedField(clazz.getSuperclass(), fieldName);
        }
        return f;
    }

    private void setForType(Field name, Object obj, Object propertyValue) {
        if (name != null) {
            name.setAccessible(true);
            Class clz = name.getType();
            try {
                if (clz == String.class) {
                    name.set(obj, propertyValue.toString());
                } else if (clz == Long.class) {
                    name.set(obj, Long.valueOf(String.valueOf(propertyValue)));
                } else if (clz == Integer.class) {
                    name.set(obj, Integer.valueOf(String.valueOf(propertyValue)));
                } else if (clz == BigDecimal.class) {
                    name.set(obj, new BigDecimal(propertyValue.toString()));
                } else if (Entity.class.isAssignableFrom(clz)) {
                    name.set(obj, propertyValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
