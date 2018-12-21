package com.beauty.impl;

import com.beauty.Pay;
import com.beauty.Strategy;

@Pay(channelId = 1)
public class BankService implements Strategy{


    @Override
    public String getSum(int channelId, Integer num) {
        return channelId*num+"";
    }
}
