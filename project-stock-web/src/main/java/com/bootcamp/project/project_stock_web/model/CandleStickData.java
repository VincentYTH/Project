package com.bootcamp.project.project_stock_web.model;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandleStickData {
  private String symbol;
  private Date Date;
  private double open;
  private double close;
  private double high;
  private double low;
  private long volume;
}
