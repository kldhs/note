package com.utils.mysql.service;

import com.utils.mysql.model.entity.DeviceReportInfo;
import org.springframework.stereotype.Service;

/**
 * @author xs
 * @date 2022/1/17 9:31
 */
@Service
public interface DeviceReportInfoService {
    void insertIntoDeviceReportInfo(DeviceReportInfo deviceReportInfo);

    Integer updateByPrimaryKey(DeviceReportInfo deviceReportInfo);
}
