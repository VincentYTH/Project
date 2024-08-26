package com.bootcamp.project.project_stock_web.infra;

public enum Scheme {
  HTTPS("https"),
  HTTP("http");

  private String value;

  private Scheme(String value){
    this.value = value;
  }

  public String lowercase(){
    return this.value.toLowerCase();
  }
}
