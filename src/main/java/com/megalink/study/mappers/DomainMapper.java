package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGDomainMapper;
import com.megalink.study.model.nochange.DomainWithBLOBs;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value="DomainMapper")
public interface DomainMapper extends MBGDomainMapper {
    void insertDomain(DomainWithBLOBs domain);
    List<String> getDomainKey();
    List<String> getDomainName();
}