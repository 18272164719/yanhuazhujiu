package com.beauty.impl;

import com.beauty.Pay;
import com.beauty.Strategy;

@Pay(channelId = 2)
public class BankAService implements Strategy{

    @Override
    public String getSum(int channelId, Integer num) {
        return channelId*num+10+"";
    }
}
