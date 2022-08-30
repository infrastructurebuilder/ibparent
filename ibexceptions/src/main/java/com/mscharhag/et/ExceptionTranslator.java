package com.mscharhag.et;

public interface ExceptionTranslator {

  void translate(TryBlock tryBlock);

  @Deprecated
  default void withTranslation(TryBlock tryBlock) {
    translate(tryBlock);
  }

  <T> T returns(ReturningTryBlock<T> invokable);

  @Deprecated
  default <T> T withReturningTranslation(ReturningTryBlock<T> invokable) {
    return returns(invokable);
  }

  ExceptionTranslatorConfigurer newConfiguration();

}
