package com.bootcamp.project.project_yahoo_finance.mapper;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.bootcamp.project.project_yahoo_finance.entity.HistoryStockEntity;
import com.bootcamp.project.project_yahoo_finance.entity.StockEntity;
import com.bootcamp.project.project_yahoo_finance.model.DataList;
import com.bootcamp.project.project_yahoo_finance.model.exData.HistoryDataResponse;
import com.bootcamp.project.project_yahoo_finance.model.exData.YahooFinanceResponse;

@Component
public class StockMapper {

  public StockEntity mapToStockEntity(YahooFinanceResponse response) {
    YahooFinanceResponse.Stock quote = response.getQuoteResponse().getResult().get(0);
    StockEntity stockEntity = new StockEntity();
    stockEntity.setShortName(quote.getShortName());
    stockEntity.setMarketCap(quote.getMarketCap());
    stockEntity.setAsk(quote.getAsk());
    stockEntity.setAskSize(quote.getAskSize());
    stockEntity.setBid(quote.getBid());
    stockEntity.setBidSize(quote.getBidSize());
    stockEntity.setRegularMarketChangePercent(quote.getRegularMarketChangePercent());
    stockEntity.setRegularMarketPrice(quote.getRegularMarketPrice());
    stockEntity.setSymbol(quote.getSymbol());
    stockEntity.setType("5 Minutes");
    stockEntity.setRegularMarketUnix(quote.getRegularMarketTime());
    stockEntity.setApiDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());

    Instant instant = Instant.ofEpochSecond(quote.getRegularMarketTime());
    String marketTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Hong_Kong")).toString();
    stockEntity.setMarketTimeInNormalTimeStamp(marketTime);

    return stockEntity;
  }

  public List<HistoryStockEntity> mapToStockEntity(HistoryDataResponse response) {
    String symbol = response.getChart().getResults().get(0).getMeta().getSymbol();
    List<Long> timeStamps = response.getChart().getResults().get(0).getTimestamp();
    HistoryDataResponse.Quote quotes = response.getChart().getResults().get(0).getIndicators().getQuotes().get(0);
    List<HistoryStockEntity> historyStockList = new ArrayList<>();
    for (int i = 0; i < quotes.getOpen().size(); i++){
      HistoryStockEntity historyStockEntity = new HistoryStockEntity();
      historyStockEntity.setSymbol(symbol);
      historyStockEntity.setType("1 Day");
      if(quotes.getOpen().get(i) != null){
        historyStockEntity.setOpen(Math.round(quotes.getOpen().get(i) * 100.0 / 100.0));
      } else {
        historyStockEntity.setOpen(Math.round(quotes.getOpen().get(i-1) * 100.0 / 100.0));
      }
      if(quotes.getClose().get(i) != null){
        historyStockEntity.setClose(Math.round(quotes.getClose().get(i) * 100.0) / 100.0);
      } else {
        historyStockEntity.setClose(Math.round(quotes.getClose().get(i-1) * 100.0) / 100.0);
      }
      if(quotes.getHigh().get(i) != null){
        historyStockEntity.setHigh(Math.round(quotes.getHigh().get(i) * 100.0) / 100.0);
      } else {
        historyStockEntity.setHigh(Math.round(quotes.getHigh().get(i-1) * 100.0) / 100.0);
      }
      if(quotes.getLow().get(i) != null){
        historyStockEntity.setLow(Math.round(quotes.getLow().get(i) * 100.0) / 100.0);
      } else {
        historyStockEntity.setLow(Math.round(quotes.getLow().get(i-1) * 100.0) / 100.0);
      }
      if(quotes.getVolume().get(i) != null){
        historyStockEntity.setVolume(quotes.getVolume().get(i));
      } else {
        historyStockEntity.setVolume(quotes.getVolume().get(i-1));
      }
      Instant instant = Instant.ofEpochSecond(timeStamps.get(i));
      Date sqlDate = Date.valueOf(LocalDate.ofInstant(instant, ZoneId.of("Asia/Hong_Kong")));
      historyStockEntity.setDate(sqlDate);

      historyStockList.add(historyStockEntity);
    }
    return historyStockList;
  }

  public List<DataList> mapToAPIData(List<StockEntity> stockEntities) {
    List<DataList> dataList = new ArrayList<>();
    for (StockEntity stockEntity : stockEntities){
      DataList data = new DataList();
      data.setMarketCap(stockEntity.getMarketCap());
      data.setShortName(stockEntity.getShortName());
      data.setAsk(stockEntity.getAsk());
      data.setAskSize(stockEntity.getAskSize());
      data.setBid(stockEntity.getBid());
      data.setBidSize(stockEntity.getBidSize());
      data.setSymbol(stockEntity.getSymbol());
      data.setRegularMarketUnix(stockEntity.getRegularMarketUnix());
      data.setRegularMarketChangePercent(stockEntity.getRegularMarketChangePercent());
      data.setRegularMarketPrice(stockEntity.getRegularMarketPrice());
      Instant instant = Instant.ofEpochSecond(stockEntity.getRegularMarketUnix());
      String marketTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Hong_Kong")).toString();
      data.setMarketTime(marketTime);
      dataList.add(data);
    }
    return dataList;
  }

  public DataList[] arrMapToAPIData(List<StockEntity> stockEntities) {
    DataList[] dataList = new DataList[stockEntities.size()];
    for (int i = 0; i < stockEntities.size(); i++){
      DataList data = new DataList();
      data.setMarketCap(stockEntities.get(i).getMarketCap());
      data.setShortName(stockEntities.get(i).getShortName());
      data.setAsk(stockEntities.get(i).getAsk());
      data.setAskSize(stockEntities.get(i).getAskSize());
      data.setBid(stockEntities.get(i).getBid());
      data.setBidSize(stockEntities.get(i).getBidSize());
      data.setSymbol(stockEntities.get(i).getSymbol());
      data.setRegularMarketUnix(stockEntities.get(i).getRegularMarketUnix());
      data.setRegularMarketChangePercent(stockEntities.get(i).getRegularMarketChangePercent());
      data.setRegularMarketPrice(stockEntities.get(i).getRegularMarketPrice());
      Instant instant = Instant.ofEpochSecond(stockEntities.get(i).getRegularMarketUnix());
      String marketTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Hong_Kong")).toString();
      data.setMarketTime(marketTime);
      dataList[i] = data;
    }
    return dataList;
  }
}

