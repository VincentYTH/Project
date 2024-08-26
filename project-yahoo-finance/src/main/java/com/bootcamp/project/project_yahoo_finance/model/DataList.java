package com.bootcamp.project.project_yahoo_finance.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataList {
  
  private long regularMarketUnix;

  private String symbol;

  private double bid;

  private double ask;

  private long bidSize;

  private long askSize;

  private double regularMarketChangePercent;

  private double regularMarketPrice;

  private String marketTime;

  private double marketCap;
  
  private String shortName;

}
