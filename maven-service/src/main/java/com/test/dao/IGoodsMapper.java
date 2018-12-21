package com.test.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IGoodsMapper {

    Map<String,Object> selectGoodsById(Long id);

    Integer updateGoods(@Param("id") Long id, @Param("bus") int bus, @Param("version")int version);

    Integer updateGoodsName(@Param("name") String name, @Param("bus") int bus);

    int selectNum(Long id);

    int updateGoodsById (@Param("id")Long id,@Param("bus")int bus);
}
