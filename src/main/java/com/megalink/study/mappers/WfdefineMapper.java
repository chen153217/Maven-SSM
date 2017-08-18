package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGWfdefineMapper;
import com.megalink.study.model.nochange.WfdefineWithBLOBs;
import org.springframework.stereotype.Component;

@Component(value="WfdefineMapper")
public interface WfdefineMapper extends MBGWfdefineMapper {
    void insertWfdefine(WfdefineWithBLOBs wfdefineWithBLOBs);
}