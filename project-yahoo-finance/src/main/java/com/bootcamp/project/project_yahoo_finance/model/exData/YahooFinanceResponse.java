package com.bootcamp.project.project_yahoo_finance.model.exData;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YahooFinanceResponse {
  private QuoteResponse quoteResponse;

  @Getter
  @Setter
  public static class QuoteResponse{
    private List<Stock> result;
    private Object error;
  }

  @Getter
  @Setter
  public static class Stock{
  private String language;
  private String region;
  private String quoteType;
  private String typeDisp;
  private String quoteSourceName;
  private boolean triggerable;
  private String customPriceAlertConfidence;
  private boolean hasPrePostMarketData;
  private long firstTradeDateMilliseconds;
  private long priceHint;
  private double regularMarketChange;
  private long regularMarketTime;
  private double regularMarketDayHigh;
  private String regularMarketDayRange;
  private double regularMarketDayLow;
  private long regularMarketVolume;
  private double regularMarketPreviousClose;
  private double bid;
  private double ask;
  private long bidSize;
  private long askSize;
  private String fullExchangeName;
  private String financialCurrency;
  private double regularMarketOpen;
  private long averageDailyVolume3Month;
  private long averageDailyVolume10Day;
  private double fiftyTwoWeekLowChange;
  private double fiftyTwoWeekLowChangePercent;
  private String fiftyTwoWeekRange;
  private double fiftyTwoWeekHighChange;
  private double fiftyTwoWeekHighChangePercent;
  private double fiftyTwoWeekLow;
  private double fiftyTwoWeekHigh;
  private double fiftyTwoWeekChangePercent;
  private long earningsTimestamp;
  private long earningsTimestampStart;
  private long earningsTimestampEnd;
  private long earningsCallTimestampStart;
  private long earningsCallTimestampEnd;
  private boolean isEarningsDateEstimate;
  private double trailingAnnualDividendRate;
  private double trailingPE;
  private double dividendRate;
  private double trailingAnnualDividendYield;
  private double dividendYield;
  private double epsTrailingTwelveMonths;
  private double epsForward;
  private double epsCurrentYear;
  private double priceEpsCurrentYear;
  private long sharesOutstanding;
  private double bookValue;
  private double fiftyDayAverage;
  private double boofiftyDayAverageChangekValue;
  private double fiftyDayAverageChangePercent;
  private double twoHundredDayAverage;
  private double twoHundredDayAverageChange;
  private double twoHundredDayAverageChangePercent;
  private double marketCap;
  private double forwardPE;
  private double priceToBook;
  private long sourceInterval;
  private long exchangeDataDelayedBy;
  private String averageAnalystRating;
  private boolean tradeable;
  private boolean cryptoTradeable;
  private String currency;
  private String marketState;
  private double regularMarketChangePercent;
  private double regularMarketPrice;
  private String exchange;
  private String shortName;
  private String longName;
  private String messageBoardId;
  private String exchangeTimezoneName;
  private String exchangeTimezoneShortName;
  private long gmtOffSetMilliseconds;
  private String market;
  private boolean esgPopulated;
  private String symbol;
  }

}
