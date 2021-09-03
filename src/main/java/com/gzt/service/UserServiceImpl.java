package com.gzt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzt.mapper.UserMapper;
import com.gzt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Override
    public User findByPhone(String phone) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone",phone);
        return userMapper.selectOne(userQueryWrapper);
    }
}
