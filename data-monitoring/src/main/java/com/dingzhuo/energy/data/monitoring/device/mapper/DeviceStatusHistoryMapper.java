package com.dingzhuo.energy.data.monitoring.device.mapper;

import com.dingzhuo.energy.data.monitoring.device.domain.DeviceStatusHistory;
import java.util.List;

/**
 * 设备启停历史监测Mapper接口
 * 
 * @author sys
 * @date 2020-03-25
 */
public interface DeviceStatusHistoryMapper 
{
    /**
     * 查询设备启停历史监测
     * 
     * @param id 设备启停历史监测ID
     * @return 设备启停历史监测
     */
    DeviceStatusHistory selectDeviceStatusHistoryById(String id);

    /**
     * 查询设备启停历史监测列表
     * 
     * @param deviceStatusHistory 设备启停历史监测
     * @return 设备启停历史监测集合
     */
    List<DeviceStatusHistory> selectDeviceStatusHistoryList(DeviceStatusHistory deviceStatusHistory);

    /**
     * 新增设备启停历史监测
     * 
     * @param deviceStatusHistory 设备启停历史监测
     * @return 结果
     */
    int insertDeviceStatusHistory(DeviceStatusHistory deviceStatusHistory);

    /**
     * 修改设备启停历史监测
     * 
     * @param deviceStatusHistory 设备启停历史监测
     * @return 结果
     */
    int updateDeviceStatusHistory(DeviceStatusHistory deviceStatusHistory);

    /**
     * 删除设备启停历史监测
     * 
     * @param id 设备启停历史监测ID
     * @return 结果
     */
    int deleteDeviceStatusHistoryById(String id);

    /**
     * 批量删除设备启停历史监测
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDeviceStatusHistoryByIds(String[] ids);

    /**
     * 查询设备启停历史监测列表（新加）
     *
     * @param deviceStatusHistory 设备启停历史监测
     * @return 设备启停历史监测集合
     */
    List<DeviceStatusHistory> selectDeviceStatusHistoryListNew(DeviceStatusHistory deviceStatusHistory);

  void saveHistoryStatus(DeviceStatusHistory history);
}
