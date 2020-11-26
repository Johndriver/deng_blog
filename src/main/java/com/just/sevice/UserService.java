package com.just.sevice;

import com.just.mapper.UserMapper;
import com.just.model.User;
import com.just.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        long now = System.currentTimeMillis();
        if (users.size()==0) {
            //插入
            user.setGmtCreate(now);
            user.setGmtModified(now);
            userMapper.insert(user);
        }else {
            //更新
            User dbUser=users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(now);
            updateUser.setToken(user.getToken());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, userExample);
        }
    }
}
