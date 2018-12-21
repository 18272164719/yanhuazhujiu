package com.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sunchenbin.mybatis.actable.utils.ClassTools;
import com.test.controller.User.LoginController;
import com.test.entity.Role;
import com.test.entity.TableVa;
import com.test.entity.User;
import com.test.entity.Variety;
import com.test.util.HttpClientUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import redis.clients.jedis.Jedis;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class testController {
    public enum PointType{
        //最后进价
        purchasePrice,
        //最高采购价
        maxPurchasePrice,
        //移动平均价
        movePrice
    }

    public static void  main (String[] args) throws Exception {
        /*String result = "{\"success\":true,\"msgcode\":\"\",\"msg\":\"\",\"data\":\"\"}";
        JSONObject json = JSONObject.parseObject(result);
        System.out.println(json.getString("success"));*/
        /*try{
            String url = "http://125.35.6.7/datasearch/face3/search.jsp?tableId=25&tableName=TABLE25&curstart=1";
            String returnMsg = HttpClientUtil.doPost(url,"".getBytes("UTF-8"),"application/x-www-form-urlencoded; charset=utf-8");

            System.out.println(returnMsg);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        /*List<Object> list = new ArrayList<>();
        for(Object obj : list){
            Map<Long,Object> ids = new HashMap<>();
            if(!ids.containsKey(obj.getId)){
                ids.put(obj.getId,obj);
            }
        }*/

        /*String a = "国药准字123";
        a = a.replace("国药准字","");
        System.out.println(a);

        String b = "";
        System.out.println(StringUtils.isEmpty(b));*/
        /*String a = "{\"ptdm\":\"0001\",\"ddmx\":[{\"shbh\":\"ZXZLH250250250\",\"gysbm\":\"A251\",\"mdbm\":\"KFWJ001\",\"ddrq\":\"2016-06-02\",\"ypmx\":[{\"ypbm\":\"10007\",\"scph\":\"11111\",\"scrq\":\"2017-09-13\",\"yxqz\":\"2019-09-13\",\"cgsli\":\"80\",\"cgdj\":\"222.00\",\"psdj\":\"222.00\",\"cgslv\":\"17\",\"pssl\":\"17\"},{\"ypbm\":\"10008\",\"scph\":\"22222\",\"scrq\":\"2017-09-13\",\"yxqz\":\"2019-09-13\",\"cgsli\":\"80\",\"cgdj\":\"250.00\",\"psdj\":\"250.00\",\"cgslv\":\"17\",\"pssl\":\"17\"}]},{\"shbh\":\"ZXZLH250250250\",\"gysbm\":\"A251\",\"mdbm\":\"KFWJ002\",\"ddrq\":\"2016-06-02\",\"ypmx\":[{\"ypbm\":\"10007\",\"scph\":\"11111\",\"scrq\":\"2017-09-13\",\"yxqz\":\"2019-09-13\",\"cgsli\":\"80\",\"cgdj\":\"222.00\",\"psdj\":\"222.00\",\"cgslv\":\"17\",\"pssl\":\"17\"},{\"ypbm\":\"10008\",\"scph\":\"22222\",\"scrq\":\"2017-09-13\",\"yxqz\":\"2019-09-13\",\"cgsli\":\"80\",\"cgdj\":\"250.00\",\"psdj\":\"250.00\",\"cgslv\":\"17\",\"pssl\":\"17\"}]}]}";
        JSONObject json = JSON.parseObject(a);
        System.out.println(json);*/
        /*String jsonStr = "{\"code\":\"123\",\"num\":1,\"segmentBD\":0,\"segmentList\":[],\"segmentMap\":{},\"statusName\":\"\"}";
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        TableVa tableVa = JSONObject.toJavaObject(jsonObject,TableVa.class);
        System.out.println(tableVa.getCode());*/
        /*TableVa t = new TableVa();
        t.setCode("123");
        t.setNum(1);
        String jsonStr1 = JSONObject.toJSONString(t);
        System.out.println(jsonStr1);*/
        /*Class clz = User.class;
        User user = (User)clz.newInstance();
        Role role = new Role();
        role.setCode("123");
        role.setName("456");
        Field field = clz.getDeclaredField("role");
        System.out.println(field);
        field.setAccessible(true);
        field.set(user,role);
        System.out.println(user.getRole().getCode());*/
        /*Class clz = User.class;
        Field field = clz.getDeclaredField("role");
        Class c = field.getType();
        System.out.println(c.getName());
        String a = "g.v";
        System.out.println(a.indexOf("."));*/
        /*Object a = "13";
        System.out.println(a instanceof String);*/
        /*Jedis jedis = new Jedis("192.168.11.190",6379);
        System.out.println(jedis.ping());*/
        /*User user = new User();
        Table tableName = user.getClass().getAnnotation(Table.class);
        System.out.println(tableName);*/
        /*Set<Class<?>> classes = ClassTools.getClasses("com.test.entity");
        System.out.println(classes);*/
        /*String a = "1,2,3,4,5,6,7,9,8";
        String[] b = a.split(",",2);
        for(String s : b){
            System.out.println(s);
        }*/
        /*User user = new User();
        BigDecimal dou = new BigDecimal(100).add(new BigDecimal(20)).divide(new BigDecimal(100));

        System.out.println(dou);*/
        /*Long a = 12l;
        System.out.println(a+"-");*/
        /*String hql = "select distinct t.Role from User t";
        String d = hql.substring(hql.indexOf("distinct"));

        System.out.println(d);*/

        /*Session session = super.getNewSession();
        String hql = "select count(distinct t.goods) from Stock t where not exists (select 1 from SalePrice s where t.goods.id=s.goods.id and t.storeExtend.storeId=s.storeId and t.storeExtend.deptId=s.deptId)";
        Query query = session.createQuery(hql);

        Long count = (Long)session.createQuery(hql).uniqueResult();
        System.out.println("条数"+count);
        System.out.println(" 条数--"+session.createQuery(hql).uniqueResult());*/
        /*Map<String,String>  map = new HashMap<>();
        map.put("1","a");
        map.put("2","b");
        map.put("3","c");
        System.out.println(map.values());
        map.clear();
        System.out.println(map.values());*/
        /*System.out.println(new BigDecimal("5").multiply(new BigDecimal("0")));*/
        /*DateUtil.dateToStr(new Date());
        System.out.println(DateUtil.dateToStr(new Date()).substring(0,10));*/

        /*String key = "t#code_S_EQ";
        System.out.println(key.substring(key.indexOf("#")+1,key.indexOf("_")));*/

        JSONObject js = new JSONObject();
        js.put("user",new User("13","123"));

        User user = (User) js.get("user");
        System.out.println(user.getEmpId());
    }

    protected String getCountHql(String hql, boolean isHql) {
        String e = hql.substring(hql.indexOf("from"));
        String c = "select count(*) " + e;
        if (isHql) {
            c = c.replaceAll("fetch", "");
            if(hql.contains("distinct")){
                String d = hql.substring(hql.indexOf("distinct"));
                String f = hql.substring(hql.indexOf("distinct"),hql.indexOf("from"));

                c = "select count(*) " + d + "group by " + f;
            }
        }

        return c;
    }
}
