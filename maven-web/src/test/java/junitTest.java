import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class junitTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test(){
        jdbcTemplate.execute("select * from t_sys_user");
    }
}
