package com.bootcamp.project.project_yahoo_finance.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.project.project_yahoo_finance.entity.HistoryStockEntity;
import com.bootcamp.project.project_yahoo_finance.entity.StockEntity;
import com.bootcamp.project.project_yahoo_finance.entity.StocksListEntity;
import com.bootcamp.project.project_yahoo_finance.infra.RedisHelper;
import com.bootcamp.project.project_yahoo_finance.mapper.StockMapper;
import com.bootcamp.project.project_yahoo_finance.model.exData.HistoryDataResponse;
import com.bootcamp.project.project_yahoo_finance.model.exData.YahooFinanceResponse;
import com.bootcamp.project.project_yahoo_finance.repository.HistoryStockRepository;
import com.bootcamp.project.project_yahoo_finance.repository.StockRepository;
import com.bootcamp.project.project_yahoo_finance.repository.StocksListRepository;

@Service
public class DataBaseService {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private StocksListRepository stocksListRepository;

  @Autowired
  private HistoryStockRepository historyStockRepository;

  @Autowired
  private StockMapper stockMapper;

  @Autowired
  private RedisHelper redisHelper;

  @Value(value = "${api.yahoo-finance.domain}")
  private String domain;

  @Value(value = "${api.yahoo-finance.cookie}")
  private String cookie;

  public void initialize() {
    initializeStocksList();
    fetchAndSaveHistoryData();
    clearRedis();
  }

  public void dailyUpdate() {
    clearRedis();
    dailyUpdateHistoryData();
  }

  public void fetchAndSaveStockData() { // 5Mins
    List<StocksListEntity> stocksList = stocksListRepository.findAll();
    for (StocksListEntity stock : stocksList) {
      YahooFinanceResponse response =
          callApiWithCookie(buildApiUrl(stock.getSymbol()));
      StockEntity stockEntity = stockMapper.mapToStockEntity(response);
      Optional<List<StockEntity>> stockEntities =
          stockRepository.findBySymbol(stock.getSymbol());
      if (!stockEntities.get().isEmpty()) {
        StockEntity maxStockEntity = stockEntities.get().stream()
            .max(Comparator.comparing(StockEntity::getRegularMarketUnix))
            .orElse(null);
        if (stockEntity.getRegularMarketUnix() > maxStockEntity
            .getRegularMarketUnix()) {
          stockRepository.save(stockEntity);
        }
      } else {
        stockRepository.save(stockEntity);
      }
    }
  }

  private void clearRedis() {
    redisHelper.deleteAll();
  }

  private void fetchAndSaveHistoryData() {
    List<HistoryStockEntity> historyStockEntities = historyStockRepository.findAll();
    if (historyStockEntities.isEmpty()){
      List<StocksListEntity> stockList = stocksListRepository.findAll();
      for (StocksListEntity stock : stockList) {
        HistoryDataResponse response = restTemplate.getForObject(
            buildHistoryApiUrl(stock.getSymbol(), 1420162200, 2000000000), // 2 Jan 2015 ~ today
            HistoryDataResponse.class);
        List<HistoryStockEntity> historyStockList = stockMapper.mapToStockEntity(response);
        historyStockRepository.saveAll(historyStockList);
      }
    }
  }

  private void dailyUpdateHistoryData() {
    long currentTime = Instant.now().getEpochSecond();
    List<StocksListEntity> stockList = stocksListRepository.findAll();
    for (StocksListEntity stock : stockList) {
      HistoryDataResponse response = restTemplate.getForObject(
          buildHistoryApiUrl(stock.getSymbol(), currentTime, 2000000000),
          HistoryDataResponse.class);
      List<HistoryStockEntity> historyStockList = stockMapper.mapToStockEntity(response);
      historyStockRepository.saveAll(historyStockList);
    }
  }

  private String buildApiUrl(String symbol) {
    return UriComponentsBuilder.newInstance().scheme("https").host(domain)
        .path("/v7/finance/quote").queryParam("symbols", symbol)
        .queryParam("crumb", "RCXxdw3tW3J").toUriString();
  }

  private String buildHistoryApiUrl(String symbol, long period1, long period2) {
    return UriComponentsBuilder.newInstance().scheme("https").host(domain)
        .path("/v8/finance/chart/").path(symbol).queryParam("period1", period1)
        .queryParam("period2", period2).queryParam("interval", "1d")
        .queryParam("events", "history").toUriString();
  }

  private YahooFinanceResponse callApiWithCookie(String url) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", cookie);
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<YahooFinanceResponse> response = restTemplate.exchange(url,
        HttpMethod.GET, entity, YahooFinanceResponse.class);
    return response.getBody();
  }

  private void initializeStocksList() {
    List<StocksListEntity> stocksList = stocksListRepository.findAll();
    if (stocksList.isEmpty()){
      stocksList =
        Arrays.asList(StocksListEntity.builder().symbol("0700.HK").build(),
            StocksListEntity.builder().symbol("2800.HK").build(),
            StocksListEntity.builder().symbol("2828.HK").build(),
            StocksListEntity.builder().symbol("9988.HK").build(),
            StocksListEntity.builder().symbol("0005.HK").build(),
            StocksListEntity.builder().symbol("3690.HK").build(),
            StocksListEntity.builder().symbol("0388.HK").build(),
            StocksListEntity.builder().symbol("1299.HK").build(),
            StocksListEntity.builder().symbol("1398.HK").build(),
            StocksListEntity.builder().symbol("2318.HK").build(),
            StocksListEntity.builder().symbol("0939.HK").build(),
            StocksListEntity.builder().symbol("1919.HK").build(),
            StocksListEntity.builder().symbol("0883.HK").build(),
            StocksListEntity.builder().symbol("1024.HK").build(),
            StocksListEntity.builder().symbol("1211.HK").build(),
            StocksListEntity.builder().symbol("0941.HK").build(),
            StocksListEntity.builder().symbol("0020.HK").build(),
            StocksListEntity.builder().symbol("9633.HK").build(),
            StocksListEntity.builder().symbol("3988.HK").build(),
            StocksListEntity.builder().symbol("1810.HK").build()
            );
      stocksListRepository.saveAll(stocksList);
    }
  }

}
