package com.just.sevice;

import com.just.mapper.UserMapper;
import com.just.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAcountId(user.getAccountId());
        long now = System.currentTimeMillis();
        if (dbUser == null) {
            //插入
            user.setGmtCreate(now);
            user.setGmtModified(now);
            userMapper.insert(user);
        }else {
            //更新
            dbUser.setGmtModified(now);
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            userMapper.update(dbUser);
        }
    }
}
