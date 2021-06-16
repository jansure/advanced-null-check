package com.github.jansure.advancenullcheck;

import java.util.Date;

import com.github.jansure.advancenullcheck.annotation.Nullable;
import com.github.jansure.advancenullcheck.annotation.Parameters;
import com.github.jansure.advancenullcheck.annotation.NonNullable;

@NonNullable({ @Parameters(type = String.class, message = "Oop! at $TYPE"),
    @Parameters(type = Integer.class, ignore = true) })
public class AnnotatedFoo2 extends Foo {

  @Override
  public void barString(String s) {}

  @Override
  public void barInteger(Integer i) {}

  @NonNullable
  public void barInteger2(Integer i) {}

  @Override
  public void barDouble(Double d) {}

  @Nullable
  public void barByte(Byte b) {}

  public void barFloat(Float f) {}

  @NonNullable(@Parameters(type = Long.class, ignore = true))
  public void barLong(Long l) {}

  @NonNullable(@Parameters(type = Number.class, message = "Noop!"))
  public void barNumber(Number n) {}

  @NonNullable
  @Nullable
  public void barDate(Date d) {}

}
