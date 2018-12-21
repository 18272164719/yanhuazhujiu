package com.test.controller;

import com.alibaba.dubbo.rpc.RpcContext;
import com.test.entity.User;
import com.test.framework.annotation.CurrentUser;
import com.test.service.IUserService;
import com.test.util.BaseController;
import com.test.util.Message;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Controller
@RequestMapping("/home")
public class UserController extends BaseController{

    @Resource
    private IUserService userService;
    @Resource
    private CuratorFramework curatorFramework;

    private static final CountDownLatch latch = new CountDownLatch(1);

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     *
     * @return
     */
    @RequestMapping("")
    public String home(){
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public Message index (String empId, String pwd){
        Message message = new Message();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(empId, pwd);
            Subject currentUser = SecurityUtils.getSubject();
            if(!currentUser.isAuthenticated()){
                logger.info("1 ------"+ token.hashCode());
                currentUser.login(token);
                /*if(!currentUser.hasRole("admin")){
                    logger.info("权限不足");
                }else{
                    logger.info("有admin权限");
                }*/
                message.setSuccess(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }
        return message;
    }

    /**
     *
     * @return
     */
    @RequestMapping("/page")
    public String page(){
        return "page";
    }

    @RequestMapping("/listByName")
    @ResponseBody
    public List<User> getByName(Long id){
        try {
            Stat stat = curatorFramework.checkExists().forPath("/CONTRACT/"+id);
            if(stat != null){
                curatorFramework.setData().forPath("/CONTRACT/"+id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userService.getByName("cs002");
    }

    @RequestMapping("/listAll")
    @ResponseBody
    public List<User> list (HttpServletRequest request ,@CurrentUser User user){
        HttpSession session = request.getSession();
        logger.info("session -------"+session.toString());
        Long data1 = System.currentTimeMillis();
        Map<String,Future> futureMap = new HashMap<>();
        userService.listByAll("dataSource1");
        futureMap.put("Future0", RpcContext.getContext().getFuture());
        List<User> userList = new ArrayList<>();
        try {
            userList = ((Future<List<User>>)futureMap.get("Future0")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Long data2 = System.currentTimeMillis();
        System.out.println((data2.longValue()-data1.longValue()));
        return userList;
    }

    @RequestMapping("/listAll2")
    @ResponseBody
    public Message listAll2 (@CurrentUser User user,final Long id){
        Message msg = new Message();
        //List<User> userList = userService.listByAll2("dataSource3");
        /*List<User> list = userService.listByAll("dataSource1");*/
        final Object obj = id;
        try {
            Stat stat = curatorFramework.checkExists().forPath("/CONTRACT/"+id);
            if(stat == null){
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/CONTRACT/"+id);
            }
            curatorFramework.getData().usingWatcher(
                    new CuratorWatcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            synchronized (obj){
                                obj.notifyAll();
                            }
                        }
                    }
            ).forPath("/CONTRACT/"+id);
            /**
             * 监听数据节点的变化情况
             */
            /*final NodeCache nodeCache = new NodeCache(curatorFramework, "/CONTRACT/"+id, false);
            nodeCache.start(true);
            nodeCache.getListenable().addListener(
                    new NodeCacheListener() {
                        @Override
                        public void nodeChanged() throws Exception {
                            synchronized (obj){
                                obj.notifyAll();
                            }
                        }
                    }
            );*/
            System.out.println("线程在等待");
            synchronized (obj){
                obj.wait(30000);
            }
            System.out.println("线程被唤醒");
            //System.out.println("返回的数据"+list.size());
            msg.setSuccess(true);
            msg.setMsgcode("线程成功被唤醒");

            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            msg.setSuccess(false);
        }finally {

        }
        return msg;
    }

    @RequestMapping(value = "/add" , method = RequestMethod.GET)
    public String add (@CurrentUser User user){
        return "add";
    }

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public Message add (User user1,@CurrentUser User user){
        Message message = new Message();
        try{
            userService.save(user.getProjectCode(),user1);
        }catch (Exception e){
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }
        return message;
    }


    @RequestMapping("/success")
    public String success(){
        logger.info("进来了");
        return "index";
    }

    /**
     * 用户登出
     */
    @RequestMapping("/logout")
    public String logout(@CurrentUser User user){
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }

    @RequestMapping("/findById")
    @ResponseBody
    public Message findById (){
        Message message = new Message();
        User user = userService.getById(1L);
        message.setData(user);
        return message;
    }


    @Override
    protected void init(WebDataBinder var1) {

    }
}
