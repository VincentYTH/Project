package com.bootcamp.project.project_yahoo_finance.service;

import java.util.List;
import java.util.Map;
import com.bootcamp.project.project_yahoo_finance.entity.HistoryStockEntity;
import com.bootcamp.project.project_yahoo_finance.entity.StocksListEntity;
import com.bootcamp.project.project_yahoo_finance.model.DataList;
import com.bootcamp.project.project_yahoo_finance.model.SystemDate;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockListService {

  List<StocksListEntity> getStockList() throws JsonProcessingException;

  SystemDate getSystemDate(String symbol) throws JsonProcessingException;

  List<DataList> get5MinsDataList() throws JsonProcessingException;

  Map<String, Map<String, HistoryStockEntity>> getDailyDataList() throws JsonProcessingException;

  Map<String, Map<String, HistoryStockEntity>> getWeeklyDataList() throws JsonProcessingException;

  Map<String, Map<String, HistoryStockEntity>> getMonthlyDataList() throws JsonProcessingException;

  DataList[] getDashboardData();
}
