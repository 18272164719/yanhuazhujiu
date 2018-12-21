package com.test.dao.impl;

import com.shyl.common.framework.dao.BaseDao;
import com.test.dao.ITableDao;
import com.test.entity.TableVa;
import org.springframework.stereotype.Repository;

@Repository
public class TableDao extends BaseDao<TableVa,Long> implements ITableDao{
}
