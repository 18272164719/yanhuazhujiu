package com.test.lock;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.bson.Document;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestScripts {

    public static void test(Long i){

    }

    public static void main(String[] args) throws IOException {
        /*InputStream in = TestScripts.class.getClassLoader().getResourceAsStream("redis.scripts");

        byte[] b = new byte[4096];

        StringBuffer buffer = new StringBuffer();
        for(int n;(n=in.read(b)) != -1;){
            buffer.append(new String(b,0,n));
        }
        System.out.println(buffer.toString());
        in.close();*/

        /*Jedis jedis = new Jedis("192.168.11.190",6379);
        System.out.println(jedis.setnx("test2","user1") == 1);
        String com.test = jedis.get("test2");
        System.out.println(com.test);*/

        /*Jedis jedis = RedisUtil.getJedis();
        System.out.println(jedis.setnx("test6","user1") == 1);*/
        /*Jedis jedis = null;
        for (int i = 0; i < 9; i++) {
            jedis = RedisUtil.getJedis();
            jedis.setnx("" + i, "" + i);
            System.out.println("第" + (i+1) + "个连接, 得到的值为" + jedis.get(""+i));
        }*/
        /*Long[] longs = new Long[]{1l,2l,3l,4l};
        List<Long> list = Arrays.asList(longs);
        for(final Long l : list){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    com.test(l);
                }
            }).start();
        }*/
        /*Jedis jedis = new Jedis("192.168.11.190",6379);
        *//*jedis.setnx("2018-11-06","2018-11-06");
        jedis.pexpire("2018-11-06",10);*//*
        jedis.expire("2018-11-06",10);
        long l = TimeUnit.MILLISECONDS.toMillis(1000);
        System.out.println(l);
        System.out.println(jedis.get("2018-11-06"));*/
        /*TimeUnit unit = TimeUnit.MILLISECONDS;
        long l = unit.toMillis(100);
        System.out.println(l);
        Jedis jedis = new Jedis("192.168.11.190",6379);
        jedis.setnx("","");
        jedis.pexpire("",10l);*/
        /*JedisPoolConfig config = new JedisPoolConfig();

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(config);
        connectionFactory.setHostName("192.168.11.190");
        connectionFactory.setPort(6379);
        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);*/
        /*Jedis jedis = new Jedis("192.168.11.190",6379);
        String set = jedis.set("99", "999", "NX", "EX", 100);
        System.out.println(set);*/

        /*ZooKeeper zk = new ZooKeeper("192.168.11.200:2181",50000,null);
        try {
            zk.setData("/CONTRACT/CONTRACTID4","contract".getBytes(),2);
            System.out.println("创建成功");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        /*try {
            System.out.println(zk.exists("/MYLOCK",false));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            zk.create("/locks",new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> subNodes = zk.getChildren("/MYLOCK",false);
            for(String s : subNodes){
                System.out.println(s);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        /*String a = "20180125";
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date parse = sim.parse(a);
            System.out.println(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        /*Mongo mongo = new Mongo("192.168.11.190",27017);
        DB db = mongo.getDB("file");
        System.out.println(db);
        System.out.println(db.getCollectionNames());

        DBCollection col = db.getCollection("col");
        BasicDBObject basic = new BasicDBObject();
        basic.put("admin","admin");
        WriteResult result = col.insert(basic);
        System.out.println(result);*/

        /*MongoClient mongoClient = new MongoClient("192.168.11.190",27017);
        List<ServerAddress> serverAddressList = mongoClient.getServerAddressList();
        MongoDatabase db = mongoClient.getDatabase("file");

        MongoCollection<Document> doc = db.getCollection("movie");
        doc.insertOne(new Document("name","张三李四").append("age", 20));*/
        /*BigDecimal a = new BigDecimal(105);

        Integer integer = Integer.valueOf(a.toString());
        System.out.println(integer);*/
        /*String a = "123";
        String b = "123";
        System.out.println(a == b);*/
        String[] a = {"a","b","c","d"};

        System.arraycopy(a,2,a,1,2);

        for(int i =0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }
}
