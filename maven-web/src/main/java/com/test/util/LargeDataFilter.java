package com.test.util;

import com.alibaba.dubbo.rpc.*;


public class LargeDataFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println(123456);
        Result result = invoker.invoke(invocation);
        return result;
    }
}
