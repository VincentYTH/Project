package com.bootcamp.project.project_yahoo_finance.infra;

public class NotFoundException extends BusinessRuntimeException {
  
  public NotFoundException(){
    super(Syscode.SYMBOL_NOT_FOUND);
  }
}
