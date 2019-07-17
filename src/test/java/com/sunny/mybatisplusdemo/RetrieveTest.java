package com.sunny.mybatisplusdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunny.mybatisplusdemo.entity.User;
import com.sunny.mybatisplusdemo.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

/**
 * @Description TODO
 * @Author sunny
 * @Date 2019-07-16 14:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RetrieveTest {

    @Autowired
    private UserMapper userMapper;

//    @Test
//    public void select() {
//        List<User> list = userMapper.selectList(null);
//        Assert.assertEquals(1, list.size());
//    }

    @Test
    public void selectByIds() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<User> list = userMapper.selectBatchIds(ids);

        List<User> list1 = new ArrayList<>();
        list.forEach(user -> list1.add(user));
        list1.forEach(System.out::println);
    }

    @Test
    public void queryWrapper() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name", "y").between("age", 20, 40).isNotNull("birth");
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void queryWrapper1() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.apply("date_format(birth,'%Y-%m-%d')={0}", "2019-07-16")
                .inSql("id", "select id from user where id<3");
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void queryWrapper2() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.likeRight("name", "w")
                .and(qw -> qw.lt("age", 40).or().isNotNull("birth"));
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void queryWrapper3() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.likeRight("name", "w")
                .or(qw -> qw.lt("age", 40).gt("age", 20).isNotNull("birth"));
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void queryWrapper4() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.nested(wq -> wq.lt("age", 20).or().isNotNull("birth"))
                .likeRight("name", "w");
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void queryWrapper5() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.select("id", "name");
        userQueryWrapper.select(User.class, info -> !info.getColumn().equals("birth"));
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    /**
     * condition 模糊查询的时候传多个字段要判断是否为空
     */
    public void queryWrapper6() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        String name = "y";
        String birth = "";
        /*if (StringUtils.isNotEmpty(name)) {
            userQueryWrapper.like("name",name);
        }
        if (StringUtils.isNotEmpty(birth)) {
            userQueryWrapper.like("birth",birth);
        }*/
        userQueryWrapper.like(StringUtils.isNotEmpty(name), "name", name)
                .like(StringUtils.isNotEmpty(birth), "birth", birth);
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    /**
     * 传实体类
     */
    public void queryWrapper7() {
        User whereUser = new User();
        whereUser.setRealName("sunny");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(whereUser);
        List<User> list = userMapper.selectList(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    /**
     */
    public void queryWrapper8() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List<Map<String, Object>> list = userMapper.selectMaps(userQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    /**
     * lambda 构造器
     */
    public void queryWrapper9() {
        //三种构造方法
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        LambdaQueryWrapper<User> lambdaQueryWrapper1 = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> lambdaQueryWrapper2 = Wrappers.lambdaQuery();
        lambdaQueryWrapper.like(User::getRealName, "w")
                .and(qw -> qw.lt(User::getAge, 40).isNotNull(User::getBirth));
        List<Map<String, Object>> list = userMapper.selectMaps(lambdaQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    /**
     * lambda 自定义sql
     */
    public void queryWrapper10() {
        //三种构造方法
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        LambdaQueryWrapper<User> lambdaQueryWrapper1 = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> lambdaQueryWrapper2 = Wrappers.lambdaQuery();
        lambdaQueryWrapper.like(User::getRealName, "w")
                .and(qw -> qw.lt(User::getAge, 40).isNotNull(User::getBirth));
        List<User> list = userMapper.getAll(lambdaQueryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    /**
     * lambda 分页
     */
    public void queryWrapper11() {
        //三种构造方法
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getRealName, "w")
                .and(qw -> qw.lt(User::getAge, 40).isNotNull(User::getBirth));
        Page<User> userPage = new Page<>(1, 2, false);//是否查询总数
        IPage<User> page = userMapper.selectPage(userPage, lambdaQueryWrapper);
        System.out.println("总页数：" + page.getPages());
        System.out.println("总记录数：" + page.getTotal());
    }

    @Test
    /**
     * lambda 分页
     */
    public void queryWrapper12() {
        //三种构造方法
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getRealName, "w")
                .and(qw -> qw.lt(User::getAge, 40).isNotNull(User::getBirth));
        Page<User> userPage = new Page<>(1, 2, false);//是否查询总数
        IPage<User> page = userMapper.selectUserPage(userPage, lambdaQueryWrapper);
        System.out.println("总页数：" + page.getPages());
        System.out.println("总记录数：" + page.getTotal());
    }
}
