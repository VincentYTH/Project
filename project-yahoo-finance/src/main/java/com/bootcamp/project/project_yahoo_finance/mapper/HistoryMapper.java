package com.bootcamp.project.project_yahoo_finance.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.bootcamp.project.project_yahoo_finance.dto.HistoryDataDTO;
import com.bootcamp.project.project_yahoo_finance.entity.HistoryStockEntity;

@Component
public class HistoryMapper {
  
  public List<HistoryDataDTO> mapToHistoryDataDTO(Map<String, Map<String, HistoryStockEntity>> historyMap){
    List<HistoryDataDTO> historyDataDTOs = new ArrayList<>();
    for (Map.Entry<String, Map<String, HistoryStockEntity>> outMap : historyMap.entrySet()){
      for (Map.Entry<String, HistoryStockEntity> inMap : outMap.getValue().entrySet()){
        HistoryStockEntity historyStockEntity = inMap.getValue();
        HistoryDataDTO historyDataDTO = new HistoryDataDTO();
        historyDataDTO.setClose(historyStockEntity.getClose());
        historyDataDTO.setHigh(historyStockEntity.getHigh());
        historyDataDTO.setLow(historyStockEntity.getLow());
        historyDataDTO.setOpen(historyStockEntity.getOpen());
        historyDataDTO.setSymbol(historyStockEntity.getSymbol());
        historyDataDTO.setVolume(historyStockEntity.getVolume());
        historyDataDTO.setDate(historyStockEntity.getDate().toString());
        historyDataDTOs.add(historyDataDTO);
      }
    }
    historyDataDTOs = historyDataDTOs.stream()
      .sorted(Comparator.comparing(HistoryDataDTO::getDate))
      .collect(Collectors.toList());
    return historyDataDTOs;
  }
}
