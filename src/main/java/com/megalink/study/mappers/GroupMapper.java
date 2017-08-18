package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGGroupMapper;
import com.megalink.study.model.nochange.Group;
import com.megalink.study.model.nochange.GroupWithBLOBs;
import org.springframework.stereotype.Component;

@Component(value="GroupMapper")
public interface GroupMapper extends MBGGroupMapper {
    void insertGroup(GroupWithBLOBs group);
}