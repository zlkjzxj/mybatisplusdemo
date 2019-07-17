package com.sunny.mybatisplusdemo.sys.service.impl;

import com.sunny.mybatisplusdemo.sys.entity.User;
import com.sunny.mybatisplusdemo.sys.mapper.UserMapper;
import com.sunny.mybatisplusdemo.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-07-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
