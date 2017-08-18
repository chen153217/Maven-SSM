package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGPermissionMapper;
import com.megalink.study.model.nochange.Permission;
import org.springframework.stereotype.Component;

@Component(value="PermissionMapper")
public interface PermissionMapper extends MBGPermissionMapper {
    void insertPermission(Permission permission);
}