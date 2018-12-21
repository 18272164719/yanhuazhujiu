package com.test.Shiro;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class InitService {

    @Resource
    private ShiroChainDefinitionsManager shiroChainDefinitionsManager;


    public InitService() {
    }

    @PostConstruct
    public void init() {
        //List<Map<String, String>> list = this.resourceService.getAllForShiro("");
        //System.out.println("list = " + list);
        this.shiroChainDefinitionsManager.initFilterChains(new ArrayList<>());
    }
}
