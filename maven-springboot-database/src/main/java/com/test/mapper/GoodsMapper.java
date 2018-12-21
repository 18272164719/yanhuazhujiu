package com.test.mapper;

import com.test.entity.Goods;

import java.util.List;

public interface GoodsMapper {

    Goods selectGoods (int id);

    List<Goods> listGoods();

    void buyGoods();
}
