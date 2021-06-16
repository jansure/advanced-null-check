package com.github.jansure.advancenullcheck;

import static com.google.inject.matcher.Matchers.any;

import com.google.inject.AbstractModule;

/**
 * 
 * {@link NonNullSpec} is a guide module designed for preventing null arguments
 * from method calls by AOP programming.
 */
public final class NonNullSpec extends AbstractModule {

  @Override
  protected void configure() {
    bindInterceptor(any(), any(), new NullException());
  }

}
