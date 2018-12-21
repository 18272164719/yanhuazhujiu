package com.test.service;

import com.test.dao.IGoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;

@Service
public class GoodsService implements IGoodsService{

    @Resource
    private IGoodsMapper goodsMapper;

    @Override
    public boolean updateGoods(Long id,int buyNum) {

        Map<String,Object> map = goodsMapper.selectGoodsById(id);

        int num = (int)map.get("num");
        if(num < buyNum){
            return false;
        }

        int i = goodsMapper.updateGoodsById(id,buyNum);

        return i>0 ? true : false;

        //基于乐观锁来实现 对库存的扣减
        /*if(goodsMapper.updateGoods(id, buyNum, (int)map.get("version")) >0){
            return true;
        }

        waitForLock();
        return updateGoods(id,buyNum);*/
    }

    private void waitForLock() {
        try {
            Thread.sleep(new Random().nextInt(10)+1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int selectNum(Long id) {
        return goodsMapper.selectNum(id);
    }
}
