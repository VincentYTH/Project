package com.bootcamp.project.project_yahoo_finance.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bootcamp.project.project_yahoo_finance.entity.HistoryStockEntity;
import com.bootcamp.project.project_yahoo_finance.entity.StockEntity;
import com.bootcamp.project.project_yahoo_finance.entity.StocksListEntity;
import com.bootcamp.project.project_yahoo_finance.infra.NotFoundException;
import com.bootcamp.project.project_yahoo_finance.infra.RedisHelper;
import com.bootcamp.project.project_yahoo_finance.mapper.StockMapper;
import com.bootcamp.project.project_yahoo_finance.model.DataList;
import com.bootcamp.project.project_yahoo_finance.model.SystemDate;
import com.bootcamp.project.project_yahoo_finance.repository.HistoryStockRepository;
import com.bootcamp.project.project_yahoo_finance.repository.StockRepository;
import com.bootcamp.project.project_yahoo_finance.repository.StocksListRepository;
import com.bootcamp.project.project_yahoo_finance.service.StockListService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class StockListServiceImpl implements StockListService {

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private StocksListRepository stocksListRepository;

  @Autowired
  private HistoryStockRepository historyStockRepository;

  @Autowired
  private RedisHelper redisHelper;

  @Autowired
  private StockMapper stockMapper;

  @Override
  public List<StocksListEntity> getStockList() throws JsonProcessingException {
    StocksListEntity[] stocksArr =
        redisHelper.get("STOCK-LIST", StocksListEntity[].class);
    if (stocksArr != null) {
      return Arrays.asList(stocksArr);
    }
    List<StocksListEntity> stocksListEntities = stocksListRepository.findAll();
    this.redisHelper.set("STOCK-LIST", stocksListEntities,
        Duration.ofHours(24L));
    return stocksListEntities;
  }

  @Override
  public SystemDate getSystemDate(String symbol)
      throws JsonProcessingException {
    SystemDate systemDateRedis =
        redisHelper.get("SYSDATE-".concat(symbol), SystemDate.class);
    if (systemDateRedis != null) {
      return systemDateRedis;
    } else {
      SystemDate systemDate = new SystemDate();
      Optional<List<StockEntity>> stockOptional =
          stockRepository.findBySymbol(symbol);
      if (!stockOptional.get().isEmpty()) {
        List<StockEntity> stockEntities = stockOptional.get();
        StockEntity targetStock = stockEntities.stream()
            .max(Comparator.comparing(StockEntity::getRegularMarketUnix))
            .orElse(null);
        systemDate.setSymbol(symbol);
        systemDate.setSystemDate(targetStock.getMarketTimeInNormalTimeStamp());
        this.redisHelper.set("SYSDATE-".concat(symbol), systemDate,
            Duration.ofHours(4));
        return systemDate;
      }
      throw new NotFoundException();
    }
  }

  @Override
  public List<DataList> get5MinsDataList() throws JsonProcessingException {
    Optional<List<StockEntity>> stockOptional =
        Optional.of(stockRepository.findAll());
    if (!stockOptional.get().isEmpty()) {
      List<StockEntity> stockEntities = stockOptional.get();
      StockEntity stockEntity = stockEntities.stream()
          .max(Comparator.comparing(StockEntity::getRegularMarketUnix))
          .orElse(null);
      DataList[] dataListRedis = redisHelper.get("5MIN-DATA", DataList[].class);
      if (dataListRedis != null) {
        DataList dataList = Arrays.asList(dataListRedis).stream()
            .max(Comparator.comparing(DataList::getRegularMarketUnix))
            .orElse(null);
        if (stockEntity.getRegularMarketUnix() > dataList
            .getRegularMarketUnix()) {
          this.redisHelper.set("5MIN-DATA",
              stockMapper.mapToAPIData(stockEntities), Duration.ofHours(12));
          return stockMapper.mapToAPIData(stockEntities);
        } else {
          return Arrays.asList(dataListRedis);
        }
      } else {
        this.redisHelper.set("5MIN-DATA",
            stockMapper.mapToAPIData(stockEntities), Duration.ofHours(12));
        return stockMapper.mapToAPIData(stockEntities);
      }
    }
    throw new NotFoundException();
  }

  @Override
  public DataList[] getDashboardData(){
    List<StockEntity> dataList = new ArrayList<>();
    Map<String, StockEntity> stockMap = new HashMap<>();
    List<StockEntity> stockEntities = stockRepository.findAll();
    for (StockEntity stock : stockEntities){
      if (!stockMap.containsKey(stock.getSymbol()) || 
          stock.getRegularMarketUnix() > stockMap.get(stock.getSymbol()).getRegularMarketUnix()){
        stockMap.put(stock.getSymbol(), stock);
      }
    }
    dataList.addAll(stockMap.values());
    return stockMapper.arrMapToAPIData(dataList);
  }

  @Override
  public Map<String, Map<String, HistoryStockEntity>> getDailyDataList() throws JsonProcessingException {
    return getHistoricalData("daily");
  }

  @Override
  public Map<String, Map<String, HistoryStockEntity>> getWeeklyDataList() throws JsonProcessingException {
    return getHistoricalData("weekly");
  }

  @Override
  public Map<String, Map<String, HistoryStockEntity>> getMonthlyDataList() throws JsonProcessingException {
    return getHistoricalData("monthly");
  }

  private Map<String, Map<String, HistoryStockEntity>> getHistoricalData(String timeFrame) throws JsonProcessingException {
    List<HistoryStockEntity> historyStockList = historyStockRepository.findAll();
    if (historyStockList.isEmpty()) {
      throw new NotFoundException();
    }
    Map<String, Map<String, HistoryStockEntity>> historyStockMap = new HashMap<>();
    for (HistoryStockEntity stock : historyStockList){
      String symbol = stock.getSymbol();
      if (!historyStockMap.containsKey(symbol)){
        historyStockMap.put(symbol, groupHistoryData(historyStockList, symbol, timeFrame));
      }
    }
    return historyStockMap;
  }

  private Map<String, HistoryStockEntity> groupHistoryData(List<HistoryStockEntity> stocks, String symbol, String timeFrame){
    Map<String, List<HistoryStockEntity>> groupedStocks = null;
    if ("daily".equals(timeFrame)){
      groupedStocks = groupDatesByDay(stocks, symbol);
    }
    if ("weekly".equals(timeFrame)){
      groupedStocks = groupDatesByWeek(stocks, symbol);
    }
    if ("monthly".equals(timeFrame)){
      groupedStocks = groupDatesByMonth(stocks, symbol);
    }
    if (groupedStocks == null){
      throw new NotFoundException();
    }
    Map<String, HistoryStockEntity> DataList = new HashMap<>();
    for (Map.Entry<String, List<HistoryStockEntity>> entry : groupedStocks.entrySet()) {
      String key = entry.getKey();
      List<HistoryStockEntity> stocksInPeriod = entry.getValue();
      HistoryStockEntity historyStocks = getReprsentativeStock(stocksInPeriod);
      DataList.put(key, historyStocks);
    }
    return DataList;
  }

  private Map<String, List<HistoryStockEntity>> groupDatesByDay(List<HistoryStockEntity> stocks, String symbol) {
    Map<String, List<HistoryStockEntity>> dailyStocks = new HashMap<>();
    for (HistoryStockEntity stock : stocks) {
      if (symbol.equals(stock.getSymbol())) {
        LocalDate date = stock.getDate().toLocalDate();
        int day = date.getDayOfMonth();
        int week = date.getMonthValue();
        int year = date.getYear();
        String dayKey = String.format("%04d%02d%02d", year, week, day);
        if (!dailyStocks.containsKey(dayKey)) {
          dailyStocks.put(dayKey, new ArrayList<>());
        }
        dailyStocks.get(dayKey).add(stock);
      }
    }
    return dailyStocks;
  }

  private Map<String, List<HistoryStockEntity>> groupDatesByWeek(List<HistoryStockEntity> stocks, String symbol) {
    Map<String, List<HistoryStockEntity>> weeklyStocks = new HashMap<>();
    for (HistoryStockEntity stock : stocks) {
      if (symbol.equals(stock.getSymbol())) {
        LocalDate date = stock.getDate().toLocalDate();
        int week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int year = date.getYear();
        String weekey = String.format("%04d%02d", year, week);
        if (!weeklyStocks.containsKey(weekey)) {
          weeklyStocks.put(weekey, new ArrayList<>());
        }
        weeklyStocks.get(weekey).add(stock);
      }
    }
    return weeklyStocks;
  }

  private Map<String, List<HistoryStockEntity>> groupDatesByMonth(List<HistoryStockEntity> stocks, String symbol) {
    Map<String, List<HistoryStockEntity>> monthlyStocks = new HashMap<>();
    for (HistoryStockEntity stock : stocks) {
      if (symbol.equals(stock.getSymbol())) {
        LocalDate date = stock.getDate().toLocalDate();
        int month = date.getMonthValue();
        int year = date.getYear();
        String monthKey = String.format("%04d%02d", year, month);
        if (!monthlyStocks.containsKey(monthKey)) {
          monthlyStocks.put(monthKey, new ArrayList<>());
        }
        monthlyStocks.get(monthKey).add(stock);
      }
    }
    return monthlyStocks;
  }

  private HistoryStockEntity getReprsentativeStock(List<HistoryStockEntity> stocks) {
    if (stocks.size() == 1){
      return stocks.get(0);
    }
    double maxHigh = Double.MIN_VALUE;
    double minLow = Double.MAX_VALUE;
    long totalVolume = 0;
    for (HistoryStockEntity stock : stocks) {
        maxHigh = Math.max(maxHigh, stock.getHigh());
        minLow = Math.min(minLow, stock.getLow());
        totalVolume += stock.getVolume();
    }
    HistoryStockEntity firstDayStock = stocks.get(0);
    HistoryStockEntity lastDayStock = stocks.get(stocks.size() - 1);
    return HistoryStockEntity.builder()
        .open(firstDayStock.getOpen())
        .close(lastDayStock.getClose())
        .high(maxHigh)
        .low(minLow)
        .volume(totalVolume)
        .symbol(firstDayStock.getSymbol())
        .Date(firstDayStock.getDate())
        .build();
  }

}
