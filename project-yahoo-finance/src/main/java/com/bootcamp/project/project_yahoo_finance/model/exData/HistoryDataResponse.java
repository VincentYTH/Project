package com.bootcamp.project.project_yahoo_finance.model.exData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryDataResponse {

  @JsonProperty("chart")
  private Chart chart;

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Chart {

    @JsonProperty("result")
    private List<Result> results;

    @JsonProperty("error")
    private Object error;

  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Result {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("timestamp")
    private List<Long> timestamp;

    @JsonProperty("indicators")
    private Indicators indicators;

  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Meta {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("exchangeName")
    private String exchangeName;

    @JsonProperty("fullExchangeName")
    private String fullExchangeName;

    @JsonProperty("instrumentType")
    private String instrumentType;

    @JsonProperty("firstTradeDate")
    private int firstTradeDate;

    @JsonProperty("regularMarketTime")
    private int regularMarketTime;

    @JsonProperty("hasPrePostMarketData")
    private boolean hasPrePostMarketData;

    @JsonProperty("gmtoffset")
    private int gmtoffset;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("exchangeTimezoneName")
    private String exchangeTimezoneName;

    @JsonProperty("regularMarketPrice")
    private double regularMarketPrice;

    @JsonProperty("fiftyTwoWeekHigh")
    private double fiftyTwoWeekHigh;

    @JsonProperty("fiftyTwoWeekLow")
    private double fiftyTwoWeekLow;

    @JsonProperty("regularMarketDayHigh")
    private double regularMarketDayHigh;

    @JsonProperty("regularMarketDayLow")
    private double regularMarketDayLow;

    @JsonProperty("regularMarketVolume")
    private long regularMarketVolume;

    @JsonProperty("chartPreviousClose")
    private double chartPreviousClose;

    @JsonProperty("priceHint")
    private int priceHint;

    @JsonProperty("currentTradingPeriod")
    private CurrentTradingPeriod currentTradingPeriod;

    @JsonProperty("dataGranularity")
    private String dataGranularity;

    @JsonProperty("range")
    private String range;

    @JsonProperty("validRanges")
    private List<String> validRanges;

  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CurrentTradingPeriod {

    @JsonProperty("pre")
    private TradingPeriod pre;

    @JsonProperty("regular")
    private TradingPeriod regular;

    @JsonProperty("post")
    private TradingPeriod post;

  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TradingPeriod {

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("start")
    private int start;

    @JsonProperty("end")
    private int end;

    @JsonProperty("gmtoffset")
    private int gmtoffset;

  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Indicators {

    @JsonProperty("quote")
    private List<Quote> quotes;

    @JsonProperty("adjclose")
    private List<AdjClose> adjCloses;

  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Quote {

    @JsonProperty("low")
    private List<Double> low;

    @JsonProperty("high")
    private List<Double> high;

    @JsonProperty("open")
    private List<Double> open;

    @JsonProperty("close")
    private List<Double> close;

    @JsonProperty("volume")
    private List<Long> volume;

  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class AdjClose {

    @JsonProperty("adjclose")
    private List<Double> adjClose;

  }

}
