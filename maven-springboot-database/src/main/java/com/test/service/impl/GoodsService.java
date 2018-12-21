package com.test.service.impl;

import com.test.entity.Goods;
import com.test.mapper.GoodsMapper;
import com.test.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsService implements IGoodsService{

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Goods selectGoods(int id) {
        return goodsMapper.selectGoods(id);
    }

    @Override
    public List<Goods> listGoods() {
        return goodsMapper.listGoods();
    }

    @Override
    public void buyGoods() {
        System.out.println("进入到service层了---------------");
        goodsMapper.buyGoods();
    }
}
