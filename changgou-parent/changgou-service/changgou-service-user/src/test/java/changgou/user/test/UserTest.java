package changgou.user.test;

import com.changgou.user.dao.UserMapper;
import com.changgou.user.pojo.User;
import com.sun.glass.ui.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        User user = userMapper.selectByPrimaryKey(22);
        System.out.println(user);
    }
    @Test
    public User findOne(String username){
        User user = (User) redisTemplate.boundValueOps(username).get();//查询缓存
        if (redisTemplate.hasKey(username)){
            return user;
        }else {
            //没有key数据库查询
            User user1 = userMapper.selectOneByExample(username);
            System.out.println("第一次查询数据库");
            redisTemplate.boundValueOps(username).set(user1);//设置dao缓存中
            if (user1 == null){
                redisTemplate.expire(username,30, TimeUnit.SECONDS);//设置key的过期时间
            }
            return user1;
        }
    }
}
