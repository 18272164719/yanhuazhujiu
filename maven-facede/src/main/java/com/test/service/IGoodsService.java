package com.test.service;

public interface IGoodsService {

    boolean updateGoods(Long id,int buyNum);

    int selectNum (Long id);
}
