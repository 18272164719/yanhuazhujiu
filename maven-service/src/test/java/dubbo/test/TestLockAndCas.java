package dubbo.test;

import com.test.dao.IGoodsMapper;
import com.test.service.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.swing.plaf.PanelUI;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TestLockAndCas {

    @Resource
    private IGoodsService goodsService;
    @Resource
    private IGoodsMapper goodsMapper;

    @Test
    public void testMapper(){
        Map<String, Object> map = goodsMapper.selectGoodsById(id);
        System.out.println(map);
    }

    private static final int user_Num = 1000;

    private static final int buyNum = 3;

    private static final Long id = 1l;

    private static int sucesssPerson = 0;

    private static int saleOut_num = 0;

    private CountDownLatch count = new CountDownLatch(user_Num);

    private CyclicBarrier cyc = new CyclicBarrier(user_Num);


    @Test
    public void buyGoods() throws InterruptedException {
        long start = System.currentTimeMillis();
        for(int i = 0 ;i <user_Num;i++){
            new Thread(new UserRequest(id,buyNum)).start();
            if(i == user_Num){
                Thread.sleep(1000);
            }
            count.countDown();
        }
        try {
            cyc.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("运行时间："+(end-start));
        System.out.println("成功购买手机的人数"+sucesssPerson);
        System.out.println("成功卖出的手机数量"+saleOut_num);
        System.out.println("库存剩余的手机个数为："+goodsService.selectNum(id));
    }

    public class UserRequest implements Runnable{

        private Long id;
        private int buyNum;

        public UserRequest(Long id,int buyNum){
            this.id = id;
            this.buyNum = buyNum;
        }

        @Override
        public void run() {
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = goodsService.updateGoods(id, buyNum);
            if(b){
                synchronized (count){
                    sucesssPerson++;
                    saleOut_num += buyNum;
                }
            }
            try {
                cyc.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
