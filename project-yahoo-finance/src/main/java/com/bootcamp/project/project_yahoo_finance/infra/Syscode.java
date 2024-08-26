package com.bootcamp.project.project_yahoo_finance.infra;

import lombok.Getter;

@Getter
public enum Syscode {
  OK(000000, "success."),
  SYMBOL_NOT_FOUND(1, "Symbol Not found."),
  INVALID_INPUT(2, "Invalid input."),
  TOO_MANY_REQUEST(3, "Too many request.");

  private int code;
  private String desc;

  private Syscode(int code, String desc){
    this.code = code;
    this.desc = desc;
  }
}
