package com.test.framework.aspect;

import com.test.framework.datasource.DynamicDataSource;

public class DSParams {
    private DynamicDataSource dataSource;

    public DynamicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DynamicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
