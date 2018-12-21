package com.test.Shiro;

import com.test.entity.User;
import com.test.service.IPermissionService;
import com.test.service.IUserService;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm{

    private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Resource
    private IUserService userService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private ShiroSessionDAO shiroSessionDAO;



    /**
     * 是先登录认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("2 ----------" + token.hashCode());
        String a = "admin";
        ShiroMyToken uptoken = (ShiroMyToken)token;
        uptoken.setRememberMe(true);
        //把user放进session里面
        Subject currentUser = SecurityUtils.getSubject();
        Session session = null;
        if(currentUser != null){
            session = currentUser.getSession();
        }
        if(session == null){
            throw new IncorrectCredentialsException("session为空");
        }else{
            String empId = uptoken.getUsername();
            String pwd = String.valueOf(uptoken.getPassword());
            //检查empId是否存在
            User user = userService.login(empId,pwd);
            if(user == null){
                user = userService.login(empId,"");
                if(user == null){
                    throw new UnknownAccountException("用户名不存在");
                }
                user.setErrTimes(user.getErrTimes()+1);
                if(user.getErrTimes() >= 5){
                    user.setIsLocked(1);
                }
                userService.update(user);
                throw new UnknownAccountException("用户名或密码错误");
            }else if(user.getIsDisabled() != null && user.getIsDisabled().equals(new Integer(1))){
                throw new LockedAccountException("您的帐号已被禁用");
            }else if(user.getIsLocked() != null && user.getIsLocked().equals(new Integer(1))){
                throw new DisabledAccountException("您的帐号已被锁定");
            }else{
                if(a == null || (a!=null && !a.contains(empId))){
                    String currentSessionId = user.getCurrentSessionId();
                    String path = shiroSessionDAO.getParentPath()+"/"+ currentSessionId;
                    Object obj = shiroSessionDAO.getZookeeperTemplate().getNode(path);
                    if(obj != null){
                        //shiroSessionDAO.getZookeeperTemplate().deleteNode(path);
                        //shiroSessionDAO.getActiveSessionsCache().remove(user.getCurrentSessionId());
                    /*DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
                    DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
                    sessionManager.getSessionDAO().delete(sessionManager.getSessionDAO().readSession((Serializable)user.getCurrentSessionId()));*/
                    /*Session oldSession = shiroSessionDAO.readSession((Serializable)user.getCurrentSessionId());
                    if(oldSession != null){
                        shiroSessionDAO.delete(oldSession);
                    }*/
                        Session oldSession = shiroSessionDAO.readSession((Serializable)user.getCurrentSessionId());
                        if(oldSession != null){
                            shiroSessionDAO.delete(oldSession);
                        }
                    }
                }

                /*if(!"admin".equals(user.getEmpId())){
                    Session oldSession = shiroSessionDAO.readSession((Serializable)user.getCurrentSessionId());
                    if(oldSession != null){
                        shiroSessionDAO.delete(oldSession);
                    }
                }*/

                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getEmpId() + "," + user.getName(), uptoken.getPassword(), this.getName());
                user.setErrTimes(Integer.valueOf(0));
                logger.info("sessionId : ------"+session.getId().toString());
                user.setCurrentSessionId(session.getId().toString());
                userService.update(user);
                logger.info("[" + user.getEmpId() + "] 登录成功！");
                session.setAttribute("currentUser",user);
                return authcInfo;
            }


        }
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        if(!SecurityUtils.getSubject().isAuthenticated()){
            this.doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }
        String principal = (String)super.getAvailablePrincipal(principals);
        String empId = principal.split(",")[0];
        User user = userService.login(empId,"");
        if(user != null){
            List<String> authorities = new ArrayList();
            List<String> roles = new ArrayList<>();
            List<com.test.entity.Resource> list = new ArrayList();
            if("admin".equals(user.getEmpId())){
                authorities.add("*");
            }else{
                list = permissionService.getPermissonByUser(user.getId());
            }
            for(com.test.entity.Resource resource : list){
                authorities.add(resource.getPermCode());
            }
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addRoles(roles);
            authorizationInfo.addStringPermissions(authorities);
            return authorizationInfo;
        }
        return null;
    }



}
