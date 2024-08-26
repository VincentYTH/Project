package com.bootcamp.project.project_yahoo_finance.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.bootcamp.project.project_yahoo_finance.dto.HistoryDataDTO;
import com.bootcamp.project.project_yahoo_finance.dto.SystemDateDTO;
import com.bootcamp.project.project_yahoo_finance.entity.StocksListEntity;
import com.bootcamp.project.project_yahoo_finance.model.DataList;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockListOperation {
  
  @GetMapping(value = "/stocklist_get")
  List<StocksListEntity> getStockList() throws JsonProcessingException; 

  @GetMapping(value = "/system_date/{symbol}")
  SystemDateDTO getSystemDate(@PathVariable String symbol) throws JsonProcessingException;

  @GetMapping(value = "/get_data/5min")
  List<DataList> get5MinsDataList() throws JsonProcessingException;

  @GetMapping(value = "/get_data/daily")
  List<HistoryDataDTO> getDailyDataList() throws JsonProcessingException;

  @GetMapping(value = "/get_data/weekly")
  List<HistoryDataDTO> getWeeklyDataList() throws JsonProcessingException;

  @GetMapping(value = "/get_data/monthly")
  List<HistoryDataDTO> getMonthlyDataList() throws JsonProcessingException;

  @GetMapping(value = "/get_data/dashboard")
  DataList[] getDashboardData();
}
