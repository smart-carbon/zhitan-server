package com.dingzhuo.energy.basic.data.enerInfoManage.mapper;

import com.dingzhuo.energy.basic.data.enerInfoManage.domain.SysEnercoefficient;
import java.util.List;

/**
 * 能源折标系数Mapper接口
 * 
 * @author sys
 * @date 2020-02-18
 */
public interface SysEnercoefficientMapper 
{
    /**
     * 查询能源折标系数
     * 
     * @param ecid 能源折标系数ID
     * @return 能源折标系数
     */
    public SysEnercoefficient selectSysEnercoefficientById(Integer ecid);

    /**
     * 查询能源折标系数列表
     * 
     * @param sysEnercoefficient 能源折标系数
     * @return 能源折标系数集合
     */
    public List<SysEnercoefficient> selectSysEnercoefficientList(SysEnercoefficient sysEnercoefficient);

    /**
     * 新增能源折标系数
     * 
     * @param sysEnercoefficient 能源折标系数
     * @return 结果
     */
    public int insertSysEnercoefficient(SysEnercoefficient sysEnercoefficient);

    /**
     * 修改能源折标系数
     * 
     * @param sysEnercoefficient 能源折标系数
     * @return 结果
     */
    public int updateSysEnercoefficient(SysEnercoefficient sysEnercoefficient);

    /**
     * 删除能源折标系数
     * 
     * @param ecid 能源折标系数ID
     * @return 结果
     */
    public int deleteSysEnercoefficientById(Integer ecid);

    /**
     * 批量删除能源折标系数
     * 
     * @param ecids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysEnercoefficientByIds(Integer[] ecids);
}
