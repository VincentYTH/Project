package com.bootcamp.project.project_yahoo_finance.infra;

import lombok.Getter;

@Getter
public class BusinessException extends Exception {
  
  private int code;

  public BusinessException(Syscode syscode){
    super(syscode.getDesc());
    this.code = syscode.getCode();
  }

  
}
