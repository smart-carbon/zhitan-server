package com.dingzhuo.energy.data.model.service;

import com.dingzhuo.energy.data.model.domain.StateType;

import java.util.List;

/**
 * 系统状态维护Service接口
 * 
 * @author sys
 * @date 2020-03-18
 */
public interface IStateTypeService 
{
    /**
     * 查询系统状态维护
     * 
     * @param stateId 系统状态维护ID
     * @return 系统状态维护
     */
    public StateType selectStateTypeById(String stateId);

    /**
     * 查询系统状态维护列表
     * 
     * @param stateType 系统状态维护
     * @return 系统状态维护集合
     */
    public List<StateType> selectStateTypeList(StateType stateType);

    /**
     * 新增系统状态维护
     * 
     * @param stateType 系统状态维护
     * @return 结果
     */
    public int insertStateType(StateType stateType);

    /**
     * 修改系统状态维护
     * 
     * @param stateType 系统状态维护
     * @return 结果
     */
    public int updateStateType(StateType stateType);

    /**
     * 批量删除系统状态维护
     * 
     * @param stateIds 需要删除的系统状态维护ID
     * @return 结果
     */
    public int deleteStateTypeByIds(String[] stateIds);

    /**
     * 删除系统状态维护信息
     * 
     * @param stateId 系统状态维护ID
     * @return 结果
     */
    public int deleteStateTypeById(String stateId);
}
