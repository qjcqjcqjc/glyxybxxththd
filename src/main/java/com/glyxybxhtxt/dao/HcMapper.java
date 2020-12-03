package com.glyxybxhtxt.dao;

import com.glyxybxhtxt.dataObject.Hc;

import java.util.List;

public interface HcMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hc record);

    int insertSelective(Hc record);

    Hc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hc record);

    int updateByPrimaryKey(Hc record);
    List<Hc> selall();
}