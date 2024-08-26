package com.bootcamp.project.project_yahoo_finance.infra;

import java.util.List;
import lombok.ToString;

@ToString
public class ApiResp<T> {

  private int code;
  private String message;
  private List<T> data;

  public int getCode(){
    return this.code;
  }

  public String getMessage(){
    return this.message;
  }

  public List<T> getData(){
    return this.data;
  }

  private ApiResp(ApiRespBuilder<T> builder){
    this.code = builder.code;
    this.message = builder.message;
    this.data = builder.data;
  }

  // static generic
  public static <U> ApiRespBuilder<U> builder(){
    return new ApiRespBuilder<>();
  }

  public static class ApiRespBuilder<T>{
    private int code;
    private String message;
    private List<T> data;

    public ApiRespBuilder<T> code(int code){
      this.code = code;
      return this;
    }

    public ApiRespBuilder<T> message(String message){
      if (message == null)
        throw new NullPointerException("message is marked non-null but is null");
      this.message = message;
      return this;
    }

    public ApiRespBuilder<T> error(ErrorCode errorCode){
      if (errorCode == null)
        throw new NullPointerException("message is marked non-null but is null");
      this.code = errorCode.getCode();
      this.message = errorCode.getDesc();
      return this;
    }

    public ApiRespBuilder<T> ok(){
      this.code = 000000;
      this.message = "Success.";
      return this;
    }

    // public ApiRespBuilder<T> badRequest(){
    //   this.code = 99;
    //   this.message = "Bad Request.";
    //   return this;
    // }

    public ApiRespBuilder<T> data(List<T> data){
      this.data = data;
      return this;
    }

    public ApiResp<T> build(){
      return new ApiResp<>(this);
    }

  }
}
