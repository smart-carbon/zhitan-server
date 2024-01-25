package com.dingzhuo.energy.project.energyShareAnalysis.service.impl;

import com.dingzhuo.energy.data.model.domain.vo.ModelNodeIndexInfor;
import com.dingzhuo.energy.data.model.service.IEnergyIndexService;
import com.dingzhuo.energy.data.model.service.IModelNodeService;
import com.dingzhuo.energy.dataservice.domain.DataItem;
import com.dingzhuo.energy.project.common.CommonConst;
import com.dingzhuo.energy.project.dataEntry.service.IDataItemService;
import com.dingzhuo.energy.project.energyShareAnalysis.domain.dto.EnergyAnalysisDTO;
import com.dingzhuo.energy.project.energyShareAnalysis.domain.vo.EnergyAnalysisVO;
import com.dingzhuo.energy.project.energyShareAnalysis.service.IEnergyShareAnalysisService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 能耗占比分析  接口层实现层
 */
@Service
@AllArgsConstructor
public class EnergyShareAnalysisServiceImpl implements IEnergyShareAnalysisService {


    private final IDataItemService dataItemService;

    private final IModelNodeService modelNodeService;

    private final IEnergyIndexService energyIndexService;


    /**
     * 获取电占比统计信息
     *
     * @param dto 查询参数
     * @return 结果
     */
    @Override
    public List<EnergyAnalysisVO> listEnergyShareAnalysis(EnergyAnalysisDTO dto) {

        List<ModelNodeIndexInfor> nodeInforList = modelNodeService.getModelNodeIndexIdRelationInforByCode(dto.getModelCode());
        if (CollectionUtils.isEmpty(nodeInforList)) {
            return new ArrayList<>();
        }
        // 能源单位
        String unit = "electric".equals(dto.getEnergyType()) ? "KWh" : "m³";

        List<String> indexIds = nodeInforList.stream()
                .map(ModelNodeIndexInfor::getIndexId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        // 按照点位进行分组
        Map<String, List<ModelNodeIndexInfor>> nodeIndexMap = nodeInforList.stream().collect(
                Collectors.groupingBy(ModelNodeIndexInfor::getName));
        // 获取查询时间
        Date beginTime = dto.getBeginTime();
        Date endTime = dto.getEndTime();
        // 查询对应indexIds，找到对应dataItem信息
        List<DataItem> dataItemList = dataItemService.getDataItemHourInforByIndexIds(beginTime, endTime, dto.getTimeType(), indexIds);
        // 求和
        BigDecimal sum = BigDecimal.valueOf(dataItemList.stream().filter(li -> indexIds.contains(li.getIndexId()))
                .mapToDouble(DataItem::getValue).sum()).setScale(CommonConst.DIGIT_2, RoundingMode.HALF_UP);
        //  倍率
        BigDecimal multiple = BigDecimal.valueOf(CommonConst.DIGIT_100);
        AtomicReference<BigDecimal> totalRatio = new AtomicReference<>(BigDecimal.ZERO);

        List<EnergyAnalysisVO> analysisVOList = new ArrayList<>(nodeIndexMap.size());
        nodeIndexMap.forEach((key, value) -> {
            EnergyAnalysisVO analysisVO = new EnergyAnalysisVO();

            analysisVO.setUnit(unit);
            analysisVO.setSumValue(sum);
            analysisVO.setEnergyUnitName(key);

            List<String> nodeIndexIds = value.stream().map(ModelNodeIndexInfor::getIndexId).collect(Collectors.toList());
            BigDecimal currentValue = BigDecimal.valueOf(dataItemList.stream()
                            .filter(li -> nodeIndexIds.contains(li.getIndexId()))
                            .mapToDouble(DataItem::getValue).sum())
                    .setScale(CommonConst.DIGIT_2, RoundingMode.HALF_UP);

            analysisVO.setValue(currentValue);
            //  同比值
            BigDecimal ratio = BigDecimal.ZERO;
            if (sum.compareTo(BigDecimal.ZERO) != 0) {
                ratio = currentValue.divide(sum, CommonConst.DIGIT_2, RoundingMode.HALF_UP);
            }
            // 超过1则用1剪掉
            if (totalRatio.get().add(ratio).compareTo(BigDecimal.ONE) > 0) {
                ratio = BigDecimal.ONE.subtract(totalRatio.get());
            }

            analysisVO.setRatio(ratio.multiply(multiple));
            analysisVOList.add(analysisVO);
            totalRatio.set(totalRatio.get().add(ratio));
        });

        return analysisVOList;
    }
}