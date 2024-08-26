package com.bootcamp.project.project_yahoo_finance.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
  private int code;
  private String message;

  public static ErrorResponse of(int code, String message){
    return new ErrorResponse(code, message);
  }
}
