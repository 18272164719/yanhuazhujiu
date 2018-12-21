package com.test.session;

import java.util.List;

/**
 * 暴露给外部的接口，实现增删查改
 */
public interface SqlSession {

    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement, Object parameter);

    <T> T getMapper(Class<T> var1);
}
