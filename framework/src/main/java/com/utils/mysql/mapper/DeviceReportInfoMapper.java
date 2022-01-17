package com.utils.mysql.mapper;

import com.utils.mysql.model.entity.DeviceReportInfo;
public interface DeviceReportInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceReportInfo record);

    int insertSelective(DeviceReportInfo record);

    DeviceReportInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceReportInfo record);

    int updateByPrimaryKey(DeviceReportInfo record);
}