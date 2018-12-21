package com.test;

import com.test.entity.User;
import com.test.mongodb.IMongoDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import sun.applet.resources.MsgAppletViewer;
import sun.nio.cs.US_ASCII;

import java.util.List;
import java.util.Map;

public class TestMongoDB {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        IMongoDao mongoDao = (IMongoDao)context.getBean("mongoDao");

        User user = new User();
        user.setId(1L);
        user.setEmpId("admin");
        user.setName("小小");

        mongoDao.save(user,"user");

        JSONObject js = (JSONObject) mongoDao.findById(1L, String.class, "user");
        System.out.println("user ----------------"+ js);

        List<Map> list = mongoDao.findAll(Map.class,"user");

        System.out.println("list---------------------------------"+list.size());
        System.out.println("list---------------------------------"+list);


    }
}
