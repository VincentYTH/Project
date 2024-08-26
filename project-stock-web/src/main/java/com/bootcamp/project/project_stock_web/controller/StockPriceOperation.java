package com.bootcamp.project.project_stock_web.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.bootcamp.project.project_stock_web.model.ExHistoryData;
import com.bootcamp.project.project_stock_web.model.StockPrice;

public interface StockPriceOperation {
  
  @GetMapping(value = "/five-minute/{symbol}")
  List<StockPrice> getFiveMinuteData(@PathVariable String symbol);
  
  @GetMapping(value = "/five-minute/ma/{symbol}/{period}")
  List<StockPrice> getMovingAverage(@PathVariable String symbol, @PathVariable int period);

  @GetMapping(value = "/five-minute/{symbol}/ask")
  double getAsk(@PathVariable String symbol);

  @GetMapping(value = "/five-minute/{symbol}/bid")
  double getBid(@PathVariable String symbol);

  @GetMapping(value = "/history/{symbol}/{type}")
  List<ExHistoryData> gethistoryDataList(@PathVariable String symbol, @PathVariable String type);
}
