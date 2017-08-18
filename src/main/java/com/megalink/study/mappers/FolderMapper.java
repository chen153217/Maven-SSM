package com.megalink.study.mappers;

import com.megalink.study.mappers.nochange.MBGFolderMapper;
import com.megalink.study.model.nochange.FolderWithBLOBs;
import org.springframework.stereotype.Component;

@Component(value="FolderMapper")
public interface FolderMapper extends MBGFolderMapper {
    void insertFolder(FolderWithBLOBs folderWithBLOBs);
}