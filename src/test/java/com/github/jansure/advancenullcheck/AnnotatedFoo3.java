
package com.github.jansure.advancenullcheck;

import java.util.Date;

import com.github.jansure.advancenullcheck.annotation.Nullable;

@Nullable({ "barInteger", "barDouble" })
public class AnnotatedFoo3 extends Foo {

  @Override
  public void barString(String s) {}

  @Override
  public void barInteger(Integer i) {}

  public void barInteger2(Integer i) {}

  @Override
  public void barDouble(Double d) {}

  public void barByte(Byte b) {}

  public void barFloat(Float f) {}

  public void barLong(Long l) {}

  public void barNumber(Number n) {}

  public void barDate(Date d) {}

}
