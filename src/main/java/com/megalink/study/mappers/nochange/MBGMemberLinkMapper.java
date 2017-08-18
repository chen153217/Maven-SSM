package com.megalink.study.mappers.nochange;

import com.megalink.study.model.nochange.MemberLink;
import com.megalink.study.model.nochange.MemberLinkExample;
import com.megalink.study.model.nochange.MemberLinkWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MBGMemberLinkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int countByExample(MemberLinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int deleteByExample(MemberLinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int insert(MemberLinkWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int insertSelective(MemberLinkWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    List<MemberLinkWithBLOBs> selectByExampleWithBLOBs(MemberLinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    List<MemberLink> selectByExample(MemberLinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    MemberLinkWithBLOBs selectByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MemberLinkWithBLOBs record, @Param("example") MemberLinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") MemberLinkWithBLOBs record, @Param("example") MemberLinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MemberLink record, @Param("example") MemberLinkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MemberLinkWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(MemberLinkWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table memberlink
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MemberLink record);
}