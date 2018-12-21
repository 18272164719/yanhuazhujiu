package com.test.service;

import com.shyl.common.framework.service.IBaseService;
import com.test.entity.TableVa;

import java.util.List;

public interface ITableService extends IBaseService<TableVa,Long>{

    void addByDetail(List<Long> ids,TableVa tableVa);
}
