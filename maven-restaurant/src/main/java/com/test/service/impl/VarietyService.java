package com.test.service.impl;

import com.shyl.common.framework.service.BaseService;
import com.test.dao.IVarietyDao;
import com.test.entity.Variety;
import com.test.service.IVarietyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(readOnly=true)
public class VarietyService extends BaseService<Variety,Long> implements IVarietyService{
    private IVarietyDao varietyDao;

    public IVarietyDao getVarietyDao() {
        return varietyDao;
    }

    @Resource
    public void setVarietyDao(IVarietyDao varietyDao) {
        this.varietyDao = varietyDao;
        super.setBaseDao(varietyDao);
    }

}
