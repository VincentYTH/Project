package com.bootcamp.project.project_yahoo_finance.infra;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {

  // try to catch NotFoundException
  // if it is caught, call this java method, it passes the input parameter as well.
  // @ExceptionHandler(TooManyRequests.class)
  // public ErrorResponse tooManyRequestsException(TooManyRequests e) {
  //   return ErrorResponse.of(Syscode.TOO_MANY_REQUEST.getCode(), Syscode.TOO_MANY_REQUEST.getDesc());
  // }
  
  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Void> numberFormatExceptionHandler(NumberFormatException e) {
    return ApiResp.<Void>builder() //
    .error(ErrorCode.NFE) //
    .build();
  } 

  @ExceptionHandler(NullPointerException.class)
  public ErrorResponse nullPointerExceptionHandler(NullPointerException e) {
    return ErrorResponse.of(ErrorCode.NPE.getCode(), ErrorCode.NPE.getDesc());
  }

  @ExceptionHandler(ArithmeticException.class)
  public ErrorResponse arithmeticExceptionHandler(ArithmeticException e) {
    return ErrorResponse.of(ErrorCode.AE.getCode(), ErrorCode.AE.getDesc());
  }

  @ExceptionHandler(BusinessRuntimeException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponse businessRuntimeExceptionHandler(
      BusinessRuntimeException e) {
    return ErrorResponse.of(e.getCode(), e.getMessage());
  }

  // @ExceptionHandler(Exception.class)
  // @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  // public ErrorResponse unHandledExcption() {
  //   return new ErrorResponse(9999, "Other Unhandled Exception.");
  // }

  // Alternative
  // @ExceptionHandler({NumberFormatException.class,
  // NullPointerException.class, ArithmeticException.class}) // {} -> or
  // public ErrorResponse exceptionHandler(RuntimeException e){
  // if (e instanceof NumberFormatException)
  // return ErrorResponse.of(ErrorCode.NFE.getCode(), ErrorCode.NFE.getDesc());
  // if (e instanceof NullPointerException)
  // return ErrorResponse.of(ErrorCode.NPE.getCode(), ErrorCode.NPE.getDesc());
  // if (e instanceof ArithmeticException)
  // return ErrorResponse.of(ErrorCode.AE.getCode(), ErrorCode.AE.getDesc());
  // else
  // return ErrorResponse.of(999, "Unhandled Exception.");
  // }


  // @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
  // public ErrorResponse arrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException e){
  // return new ErrorResponse(102, "Array Index Out Of Bounds Exception.");
  // }

  // @ExceptionHandler(IndexOutOfBoundsException.class)
  // public ErrorResponse indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e){
  // return new ErrorResponse(103, "Index Out Of Bounds Exception.");
  // }

  // @ExceptionHandler(IllegalArgumentException.class)
  // public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e){
  // return new ErrorResponse(104, "Illegal Argument Exception.");
  // }

  // @ExceptionHandler(IllegalStateException.class)
  // public ErrorResponse illegalStateExceptionHandler(IllegalStateException e){
  // return new ErrorResponse(105, "Illegal State Exception.");
  // }

}
