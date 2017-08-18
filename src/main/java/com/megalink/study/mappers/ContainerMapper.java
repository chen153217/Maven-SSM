package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGContainerMapper;
import com.megalink.study.model.nochange.ContainerWithBLOBs;
import org.springframework.stereotype.Component;

@Component(value="ContainerMapper")
public interface ContainerMapper extends MBGContainerMapper {
    void insertContainer(ContainerWithBLOBs containerWithBLOBs);
}