package com.example.gaia.errors;

import java.util.Optional;

public class ApplicationError extends Throwable {
  public String cause;
  public Integer code;
  public Optional<StackTraceElement[]> stackTraceElements;

  public ApplicationError(String cause, Integer code, Optional<StackTraceElement[]> stackTraceElements) {
    this.cause = cause;
    this.code = code;
    this.stackTraceElements = stackTraceElements;
  }
}
