package com.bootcamp.project.project_stock_web.controller.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.bootcamp.project.project_stock_web.controller.StockPriceOperation;
import com.bootcamp.project.project_stock_web.infra.NotFoundException;
import com.bootcamp.project.project_stock_web.model.StockPrice;
import com.bootcamp.project.project_stock_web.model.ExData;
import com.bootcamp.project.project_stock_web.model.ExHistoryData;

@RestController
public class StockPriceController implements StockPriceOperation {

  @Autowired
  private RestTemplate restTemplate;

  private final String instance = "http://localhost:9003";
  // private final String instance = "http://ec2-43-198-87-173.ap-east-1.compute.amazonaws.com:9003";
  private final String fiveMinData = "/get_data/5min";
  private final String dailyData = "/get_data/daily";
  private final String weeklyData = "/get_data/weekly";
  private final String monthlyData = "/get_data/monthly";

  @Override
  public List<StockPrice> getFiveMinuteData(String symbol) {
    ExData[] response = restTemplate.getForObject(instance.concat(fiveMinData), ExData[].class);
    List<StockPrice> stockPriceList = new ArrayList<>();
    if (response != null) {
      for (int i = 0; i < response.length; i++) {
        if (symbol.equals(response[i].getSymbol())) {
          StockPrice stockPrice = new StockPrice(response[i].getMarketTime(),
              response[i].getRegularMarketPrice());
          stockPriceList.add(stockPrice);
        }
      }
      return stockPriceList;
    }
    throw new NotFoundException();
  }

  @Override
  public List<StockPrice> getMovingAverage(String symbol, int period) {
    ExData[] response = restTemplate.getForObject(instance.concat(fiveMinData), ExData[].class);
    List<StockPrice> stockPriceList = new ArrayList<>();
    if (response != null) {
      for (int i = 0; i < response.length; i++) {
        if (symbol.equals(response[i].getSymbol())) {
          StockPrice stockPrice = new StockPrice(response[i].getMarketTime(),
              response[i].getRegularMarketPrice());
          stockPriceList.add(stockPrice);
        }
      }
      List<StockPrice> maList = new ArrayList<>();
      for (int i = 0; i < stockPriceList.size(); i++) {
        if (i >= period - 1) {
          double sum = 0;
          for (int j = i - period + 1; j <= i; j++) {
            sum += stockPriceList.get(j).getPrice();
          }
          StockPrice averagedStockPrice =
              new StockPrice(response[i].getMarketTime(), Double.valueOf(sum / period));
          maList.add(averagedStockPrice);
        } else {
          maList.add(null);
        }
      }
      return maList;
    }
    throw new NotFoundException();
  }

  @Override
  public double getAsk(String symbol){
    ExData[] response = restTemplate.getForObject(instance.concat(fiveMinData), ExData[].class);
    List<ExData> ExDataList = new ArrayList<>();
    if (response != null) {
      for (int i = 0; i < response.length; i++) {
        if (symbol.equals(response[i].getSymbol())) {
          ExData exData = new ExData();
          exData.setRegularMarketUnix(response[i].getRegularMarketUnix());
          exData.setAsk(response[i].getAsk());
          ExDataList.add(exData);
        }
      }
      ExData target = ExDataList.stream()
        .max(Comparator.comparing(ExData::getRegularMarketUnix))
        .orElse(null);
      return target.getAsk();
    }
    return 0.00;
  }

  @Override
  public double getBid(String symbol){
    ExData[] response = restTemplate.getForObject(instance.concat(fiveMinData), ExData[].class);
    List<ExData> ExDataList = new ArrayList<>();
    if (response != null) {
      for (int i = 0; i < response.length; i++) {
        if (symbol.equals(response[i].getSymbol())) {
          ExData exData = new ExData();
          exData.setRegularMarketUnix(response[i].getRegularMarketUnix());
          exData.setBid(response[i].getBid());
          ExDataList.add(exData);
        }
      }
      ExData target = ExDataList.stream()
        .max(Comparator.comparing(ExData::getRegularMarketUnix))
        .orElse(null);
      return target.getBid();
    }
    return 0.00;
  }

  @Override
  public List<ExHistoryData> gethistoryDataList(String symbol, String type){
    ExHistoryData[] response = new ExHistoryData[]{};
    if (type.equals("daily")){
      response = restTemplate.getForObject(instance.concat(dailyData), ExHistoryData[].class);
    }
    if (type.equals("weekly")){
      response = restTemplate.getForObject(instance.concat(weeklyData), ExHistoryData[].class);
    }
    if (type.equals("monthly")){
      response = restTemplate.getForObject(instance.concat(monthlyData), ExHistoryData[].class);
    }

    if (response == null){
      throw new NotFoundException();
    }
    List<ExHistoryData> dataList = new ArrayList<>();
    for (int i = 0; i < response.length; i++){
      if (symbol.equals(response[i].getSymbol())){
        ExHistoryData data = new ExHistoryData();
        data.setClose(response[i].getClose());
        data.setDate(response[i].getDate());
        data.setHigh(response[i].getHigh());
        data.setLow(response[i].getLow());
        data.setOpen(response[i].getOpen());
        data.setSymbol(symbol);
        data.setVolume(response[i].getVolume());
        dataList.add(data);
      }
    }
    return dataList;
  }

}
