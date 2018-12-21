package com.test.dao.impl;

import com.shyl.common.framework.dao.BaseDao;
import com.test.dao.IVarietyDao;
import com.test.entity.Variety;
import org.springframework.stereotype.Repository;

@Repository
public class VarietyDao extends BaseDao<Variety,Long> implements IVarietyDao{

}
