package com.bootcamp.project.project_yahoo_finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TSTOCK_QUOTE_YAHOO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private double bid;
  private double ask;
  private long bidSize;
  private long askSize;
  private double regularMarketChangePercent;
  private double regularMarketPrice;
  private String symbol;
  private String type;
  private String apiDateTime;
  private String marketTimeInNormalTimeStamp;
  private long regularMarketUnix;
  private double marketCap;
  private String shortName;

}
