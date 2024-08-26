package com.bootcamp.project.project_stock_web.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockPrice {
  private String timestamp;
  private double price;

  public StockPrice(int year, int month, int day, int hour, int minute,
      double price) {
    this.timestamp = LocalDateTime.of(year, month, month, hour, minute, minute)
        .atZone(ZoneId.of("Asia/Hong_Kong"))
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    this.price = price;
  }

  public StockPrice(String timeStamp, double price) {
    this.timestamp =
        LocalDateTime.parse(timeStamp).atZone(ZoneId.of("Asia/Hong_Kong"))
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    this.price = price;
  }
}
