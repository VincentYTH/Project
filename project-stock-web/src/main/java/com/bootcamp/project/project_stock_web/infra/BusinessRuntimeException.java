package com.bootcamp.project.project_stock_web.infra;

import lombok.Getter;

@Getter
public class BusinessRuntimeException extends RuntimeException {
  
  private int code;

  public BusinessRuntimeException(Syscode syscode){
    super(syscode.getDesc());
    this.code = syscode.getCode();
  }
}
