package com.test.executor;

import com.test.config.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(MappedStatement ms,Object parameter);

}
