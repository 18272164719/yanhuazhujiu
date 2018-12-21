package com.test.lock;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        ZooKeeper zk = new ZooKeeper("192.168.11.200:2181",50000,null);
        try {
            zk.setData("/CONTRACT/CONTRACTID4","contract".getBytes(),2);
            System.out.println("创建成功");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    }
}
