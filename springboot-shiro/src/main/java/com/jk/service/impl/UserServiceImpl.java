package com.jk.service.impl;

import com.jk.mapper.UsersMapper;
import com.jk.model.Users;
import com.jk.service.inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceInter {
    @Autowired
    private UsersMapper usersMapper;


    public Users queryUserName(String username) {
        return usersMapper.queryUserName(username);
    }
}
