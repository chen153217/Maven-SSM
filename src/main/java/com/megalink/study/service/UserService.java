package com.megalink.study.service;

import com.megalink.study.mappers.HansennMapper;
import com.megalink.study.mappers.UserMapper;
import com.megalink.study.model.nochange.Hansenn;
import com.megalink.study.model.nochange.User;
import com.megalink.study.model.nochange.UserExample;
import com.megalink.study.utils.Md5Utils;
import com.megalink.study.vo.BussinessException;
import com.megalink.study.vo.res.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenhansen on 2017/7/21.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HansennMapper hansennMapper;
    public void addUser(User user){
        userMapper.addUser(user);
        //userMapper.insertSelective(user);
    }
    public LoginRes login(String userName, String password){
        List<Hansenn> managerList=hansennMapper.show();
        managerList.get(0).getSex();
        if(password==null || "".equals(password)){
            throw new BussinessException("100001");
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);
        User user = userMapper.selectByExample(userExample).get(0);
        if(user!=null){
            if(Md5Utils.md5(Md5Utils.md5(password)+user.getSalt()).equals(user.getPassword())){
                LoginRes loginRes = new LoginRes();
                loginRes.setUserName(user.getUserName());
                return loginRes;
            }
        }
        return null;
    }
}
