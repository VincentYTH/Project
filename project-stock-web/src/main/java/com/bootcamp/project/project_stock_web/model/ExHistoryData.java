package com.bootcamp.project.project_stock_web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExHistoryData {
  private String symbol;
  private String Date;
  private double open;
  private double close;
  private double high;
  private double low;
  private long volume;
}
