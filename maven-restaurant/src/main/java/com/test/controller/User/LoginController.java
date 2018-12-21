package com.test.controller.User;

import com.shyl.common.web.controller.BaseController;
import com.test.entity.User;
import com.test.framework.annotation.RequestJson;
import com.test.framework.annotation.RequestJson2;
import com.test.util.Message;
import net.sf.json.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/home")
public class LoginController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Message login(@RequestJson2 String empId, @RequestJson2 String pwd, @RequestJson2 User user){
        Message message = new Message();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(empId, DigestUtils.md5Hex(pwd));
            Subject currentUser = SecurityUtils.getSubject();
            if(!currentUser.isAuthenticated()){
                currentUser.login(token);
            }
        }catch (IncorrectCredentialsException e){  // 密码不正确
            logger.error(String.format("incorrent credentials: ", empId), e);
            message.setSuccess(false);
            message.setMsg("密码不正确");
        }catch (UnknownAccountException e){         // 用户不存在
            logger.error(String.format("user has been authenticated: ", empId), e);
            message.setSuccess(false);
            message.setMsg("用户不存在");
        }catch (ConcurrentAccessException e){       // 用户重复登录
            logger.error(String.format("user has been authenticated: ", empId), e);
            message.setSuccess(false);
            message.setMsg("用户重复登录");
        }catch (AccountException e){                // 其他账户异常
            logger.error(String.format("account except: ", empId), e);
            message.setSuccess(false);
            message.setMsg("其他账户异常");
        }
        return message;
    }

    @RequestMapping("/success")
    public String index(){
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "login";
    }

    @Override
    protected void init(WebDataBinder webDataBinder) {

    }
}
