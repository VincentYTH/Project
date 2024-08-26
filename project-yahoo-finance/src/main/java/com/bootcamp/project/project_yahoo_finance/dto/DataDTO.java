package com.bootcamp.project.project_yahoo_finance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataDTO {
  private double bid;
  private double ask;
  private long bidSize;
  private long askSize;
  private double regularMarketChangePercent;
  private double regularMarketPrice;
  private double marketCap;
  private String shortName;
}
