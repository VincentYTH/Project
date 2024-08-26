package com.bootcamp.project.project_stock_web.infra;

public class NotFoundException extends BusinessRuntimeException {
  
  public NotFoundException(){
    super(Syscode.SYMBOL_NOT_FOUND);
  }
}
