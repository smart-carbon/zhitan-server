package com.dingzhuo.energy.basic.data.workforce.mapper;

import com.dingzhuo.energy.basic.data.workforce.domain.RosteringShift;
import com.dingzhuo.energy.project.system.domain.SysDept;

import java.util.List;

/**
 * 排班管理设置Mapper接口
 * 
 * @author liuli
 * @date 2020-05-12
 */
public interface RosteringShiftMapper 
{
    public List<SysDept> treeList();
    /**
     * 查询排班管理设置
     * 
     * @param id 排班管理设置ID
     * @return 排班管理设置
     */
    public RosteringShift selectRosteringShiftById(String id);

    /**
     * 查询排班管理设置列表
     * 
     * @param rosteringShift 排班管理设置
     * @return 排班管理设置集合
     */
    public List<RosteringShift> selectRosteringShiftList(RosteringShift rosteringShift);

    /**
     * 新增排班管理设置
     * 
     * @param rosteringShift 排班管理设置
     * @return 结果
     */
    public int insertRosteringShift(RosteringShift rosteringShift);

    /**
     * 修改排班管理设置
     * 
     * @param rosteringShift 排班管理设置
     * @return 结果
     */
    public int updateRosteringShift(RosteringShift rosteringShift);

    /**
     * 删除排班管理设置
     * 
     * @param id 排班管理设置ID
     * @return 结果
     */
    public int deleteRosteringShiftById(String id);

    /**
     * 批量删除排班管理设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRosteringShiftByIds(String[] ids);
}
