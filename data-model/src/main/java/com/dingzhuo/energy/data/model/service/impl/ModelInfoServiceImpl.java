package com.dingzhuo.energy.data.model.service.impl;

import com.dingzhuo.energy.basic.data.meter.domain.MeterImplement;
import com.dingzhuo.energy.basic.data.meter.service.IMeterImplementService;
import com.dingzhuo.energy.data.model.domain.EnergyIndex;
import com.dingzhuo.energy.data.model.domain.ModelInfo;
import com.dingzhuo.energy.data.model.domain.vo.ModelNodeIndexInfor;
import com.dingzhuo.energy.data.model.domain.vo.PointDataVO;
import com.dingzhuo.energy.data.model.mapper.ModelInfoMapper;
import com.dingzhuo.energy.data.model.service.IEnergyIndexService;
import com.dingzhuo.energy.data.model.service.IModelInfoService;
import com.dingzhuo.energy.data.model.service.IModelNodeService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模型Service业务层处理
 *
 * @author fanxinfu
 * @date 2020-02-17
 */
@Service
public class ModelInfoServiceImpl implements IModelInfoService {
  @Autowired
  private ModelInfoMapper modelInfoMapper;

  @Autowired
  private final IModelNodeService modelNodeService;

  @Autowired
  private final IEnergyIndexService energyIndexService;

  @Autowired
  private final IMeterImplementService meterImplementService;

  public ModelInfoServiceImpl(IModelNodeService modelNodeService,
                              IEnergyIndexService energyIndexService,
                              IMeterImplementService meterImplementService) {
    this.modelNodeService = modelNodeService;
    this.energyIndexService = energyIndexService;
    this.meterImplementService = meterImplementService;
  }

  /**
   * 查询模型
   *
   * @param modelCode 模型ID
   * @return 模型
   */
  @Override
  public ModelInfo selectModelInfoById(String modelCode) {
    return modelInfoMapper.selectModelInfoById(modelCode);
  }

  /**
   * 查询模型列表
   *
   * @param modelInfo 模型
   * @return 模型
   */
  @Override
  public List<ModelInfo> selectModelInfoList(ModelInfo modelInfo) {
    return modelInfoMapper.selectModelInfoList(modelInfo);
  }

  /**
   * 新增模型
   *
   * @param modelInfo 模型
   * @return 结果
   */
  @Override
  public int insertModelInfo(ModelInfo modelInfo) {
    return modelInfoMapper.insertModelInfo(modelInfo);
  }

  /**
   * 修改模型
   *
   * @param modelInfo 模型
   * @return 结果
   */
  @Override
  public int updateModelInfo(ModelInfo modelInfo) {
    return modelInfoMapper.updateModelInfo(modelInfo);
  }

  /**
   * 批量删除模型
   *
   * @param modelCode 需要删除的模型ID
   * @return 结果
   */
  @Override
  public int deleteModelInfoByCode(String modelCode) {
    return modelInfoMapper.deleteModelInfoByCode(modelCode);
  }

  /**
   * 删除模型信息
   *
   * @param modelCode 模型ID
   * @return 结果
   */
  @Override
  public int deleteModelInfoById(String modelCode) {
    return modelInfoMapper.deleteModelInfoById(modelCode);
  }

  /**
   * 根据模型id查询对应点位信息
   *
   * @param modelId 查询模型id
   * @return
   */
  @Override
  public List<PointDataVO> listEnergyIndexByModelId(String modelId) {
    List<PointDataVO> voList = new ArrayList<>();
    // 根据id查询下级id与indexId
    List<ModelNodeIndexInfor> inforList = modelNodeService.listModelNodeIndexIdRelationInforByParentId(modelId);
    if (CollectionUtils.isEmpty(inforList)) {
      List<ModelNodeIndexInfor> indexInforList = modelNodeService.getModelNodeIndexIdRelationInforByNodeId(modelId);
      if (CollectionUtils.isEmpty(indexInforList)) {
        return voList;
      }
      inforList.addAll(indexInforList);
    }
    // 去除所有点位id信息
    List<String> indexIds = inforList.stream().map(ModelNodeIndexInfor::getIndexId).collect(Collectors.toList());
    List<EnergyIndex> energyIndexList = energyIndexService.getEnergyIndexByIds(indexIds);
    // 根据indexid查询对应计量器具信息
    List<String> meterIds = energyIndexList.stream().map(EnergyIndex::getMeterId).collect(Collectors.toList());
    Map<String, List<MeterImplement>> meterImplementMap = meterImplementService.listMeterImplementByIds(meterIds).stream()
            .filter(li -> StringUtils.isNotEmpty(li.getInstallactionLocation()))
            .collect(Collectors.groupingBy(MeterImplement::getId));
    for (EnergyIndex indexInfor : energyIndexList) {

      PointDataVO pointData = new PointDataVO();
      String indexName = indexInfor.getName();
      List<MeterImplement> meterImplements = meterImplementMap.get(indexInfor.getMeterId());
      if (CollectionUtils.isNotEmpty(meterImplements)) {
        MeterImplement infor = meterImplements.get(0);
        indexName = infor.getInstallactionLocation() + "_" + infor.getMeterName() + "_" + indexName;
      }
      pointData.setIndexName(indexName);
      pointData.setIndexId(indexInfor.getIndexId());
      voList.add(pointData);
    }
    return voList;
  }
}
