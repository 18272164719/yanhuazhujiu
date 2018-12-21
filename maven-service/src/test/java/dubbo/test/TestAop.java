package dubbo.test;

import com.test.service.IAopUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TestAop {

    @Resource
    private IAopUserService aopUserService;


    @Test
    public void testAop(){
        aopUserService.doAll();
    }
}
