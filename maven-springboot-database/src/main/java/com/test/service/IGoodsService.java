package com.test.service;

import com.test.entity.Goods;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IGoodsService {

    Goods selectGoods (int id);

    List<Goods> listGoods();

    void buyGoods();
}
