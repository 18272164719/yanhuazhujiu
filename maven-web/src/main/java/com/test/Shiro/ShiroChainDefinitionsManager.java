package com.test.Shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("shiroChainDefinitionsManager")
public class ShiroChainDefinitionsManager {
    @Resource(
            name = "&shiroFilter"
    )
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    public static final String PREMISSION_STRING = "authc, perms[\"{0}\"]";
    private Map<String, NamedFilterList> defaultFilterChains;

    public ShiroChainDefinitionsManager() {
    }

    public void initFilterChains(List<Map<String, String>> list) {
        AbstractShiroFilter shiroFilter = null;

        try {
            shiroFilter = (AbstractShiroFilter)this.shiroFilterFactoryBean.getObject();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
        this.defaultFilterChains = new LinkedHashMap(manager.getFilterChains());
        this.updateFilterChains(list);
    }

    public void updateFilterChains(List<Map<String, String>> list) {
        AbstractShiroFilter shiroFilter = null;

        try {
            shiroFilter = (AbstractShiroFilter)this.shiroFilterFactoryBean.getObject();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
        manager.getFilterChains().clear();
        this.shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        if (this.defaultFilterChains != null) {
            manager.getFilterChains().putAll(this.defaultFilterChains);
        }

        Iterator var5 = list.iterator();

        while(var5.hasNext()) {
            Map<String, String> m = (Map)var5.next();
            if (StringUtils.isNotEmpty((CharSequence)m.get("PERMCODE")) && StringUtils.isNotEmpty((CharSequence)m.get("URL"))) {
                manager.createChain((String)m.get("URL"), MessageFormat.format("authc, perms[\"{0}\"]", m.get("PERMCODE")));
            }
        }

        manager.createChain("/resources/**", "anon");
        manager.createChain("/guest/**", "anon");
        manager.createChain("/**", "authc");
        System.out.println("内存授权列表11");
        System.out.println("list.size = " + list.size());
        System.out.println(manager.getFilterChains());
        System.out.println("内存授权列表   end");
    }
}
