package com.bootcamp.project.project_yahoo_finance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryDataDTO {
  private String symbol;
  private String Date;
  private double open;
  private double close;
  private double high;
  private double low;
  private long volume;
}
