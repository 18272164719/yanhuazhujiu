package com.test.springbootdatabase;

import com.test.entity.Goods;
import com.test.service.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDatabaseApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IGoodsService goodsService;

    private CountDownLatch ctl = new CountDownLatch(1);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(40, 50, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());



    @Test
    public void main() {
        for (int i = 0; i < 300; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"进来了");
                    buy();
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctl.countDown();
    }


    private void buy (){
        System.out.println(Thread.currentThread().getName()+"进来了buy 前面");
        try {
            ctl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            System.out.println(Thread.currentThread().getName()+"进来了buy   后面");
            goodsService.buyGoods();
            //jdbcTemplate.execute("update goods set num = num -1 where id = 1 and num > 0");
            System.out.println("SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("FAILUE");
        }
    }

    @Test
    public void excute (){
        for(int i = 0 ; i< 100 ;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("进来了");
                    jdbcTemplate.execute("update goods set num = num -1 where id = 1 and num > 0");
                    System.out.println("出来了");
                }
            }).start();
        }
    }


}
