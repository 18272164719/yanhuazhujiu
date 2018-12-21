package com.beauty;

/**
 * 存放上下文
 */
public class Context{

    public String reSum(int channelId) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        StrategyFactory strategyFactory = StrategyFactory.getInstance();
        Strategy strategy = strategyFactory.create(channelId);
        return strategy.getSum(channelId,10);
    }
}
