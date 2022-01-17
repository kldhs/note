package com.utils.mysql.service.impl;

import com.utils.mysql.mapper.DeviceReportInfoMapper;
import com.utils.mysql.model.entity.DeviceReportInfo;
import com.utils.mysql.service.DeviceReportInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xs
 * @date 2022/1/17 9:33
 */

@Service
public class DeviceReportInfoServiceImpl implements DeviceReportInfoService {
    @Resource
    DeviceReportInfoMapper deviceReportInfoMapper;

    public void insertIntoDeviceReportInfo(DeviceReportInfo deviceReportInfo){
        deviceReportInfoMapper.insertSelective(deviceReportInfo);
    }
}
