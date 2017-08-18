package com.megalink.study.mappers.nochange;

import com.megalink.study.model.nochange.Group;
import com.megalink.study.model.nochange.GroupExample;
import com.megalink.study.model.nochange.GroupWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MBGGroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int countByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int deleteByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int insert(GroupWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int insertSelective(GroupWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    List<GroupWithBLOBs> selectByExampleWithBLOBs(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    List<Group> selectByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    GroupWithBLOBs selectByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") GroupWithBLOBs record, @Param("example") GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") GroupWithBLOBs record, @Param("example") GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Group record, @Param("example") GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GroupWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(GroupWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lcmgroup
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Group record);
}