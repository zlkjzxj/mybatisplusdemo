package com.sunny.mybatisplusdemo;

import com.sunny.mybatisplusdemo.entity.User;
import com.sunny.mybatisplusdemo.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @Description TODO
 * @Author sunny
 * @Date 2019-07-16 11:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {
    @Autowired
    private UserMapper userMapper;

    /*@Test
    public void select() {
        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(1, list.size());
    }*/

    @Test
    public void insert() {
        User user = new User();
        user.setRealName("sunny");
        user.setBirth(LocalDateTime.now());
        user.setDesc("perfect is shit");
        user.setDesc2("god is a girl");
        int rows = userMapper.insert(user);
        System.out.println("影响记录数：" + rows);

    }
}
