package com.test.service.impl;

import com.shyl.common.framework.service.BaseService;
import com.test.dao.ITableDao;
import com.test.dao.IVarietyDao;
import com.test.entity.TableVa;
import com.test.entity.Variety;
import com.test.service.ITableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly=true)
public class TableService extends BaseService<TableVa,Long> implements ITableService{
    @Resource
    private IVarietyDao varietyDao;

    private ITableDao tableDao;

    public ITableDao getTableDao() {
        return tableDao;
    }
    @Resource
    public void setTableDao(ITableDao tableDao) {
        this.tableDao = tableDao;
        //super.setBaseDao(tableDao);
    }

    @Override
    @Transactional
    public void addByDetail(List<Long> ids, TableVa tableVa) {
        TableVa newTable = tableDao.getById(tableVa.getId());
        for(Long id : ids){
            newTable.getVarietys().add(varietyDao.getById(id));
        }
        newTable.setStatus(TableVa.Status.use);
        newTable.setNum(tableVa.getNum());
        newTable.setSum(tableVa.getSum());
        tableDao.update(newTable);
    }
}
