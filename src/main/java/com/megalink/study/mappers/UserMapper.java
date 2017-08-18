package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGUserMapper;
import com.megalink.study.model.nochange.User;

public interface UserMapper extends MBGUserMapper {
    void addUser(User user);
}