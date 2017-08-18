package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGMemberLinkMapper;
import com.megalink.study.model.nochange.MemberLinkWithBLOBs;
import org.springframework.stereotype.Component;

@Component(value="MemberLinkMapper")
public interface MemberLinkMapper extends MBGMemberLinkMapper {
    void insertMemberLink(MemberLinkWithBLOBs memberLinkWithBLOBs);
}