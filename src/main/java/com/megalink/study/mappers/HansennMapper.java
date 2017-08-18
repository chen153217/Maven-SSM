package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGHansennMapper;
import com.megalink.study.model.nochange.Hansenn;

import java.util.List;

public interface HansennMapper extends MBGHansennMapper {
    List<Hansenn> show();
}