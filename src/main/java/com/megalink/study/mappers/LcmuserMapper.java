package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGLcmuserMapper;
import com.megalink.study.model.nochange.LcmuserWithBLOBs;

import org.springframework.stereotype.Component;

import java.util.List;

@Component(value="LcmuserMapper")
public interface LcmuserMapper extends MBGLcmuserMapper {
    void insertUser(LcmuserWithBLOBs lcmuser);
    Long getSuperAdminId();
    List<String> getUsers();
}