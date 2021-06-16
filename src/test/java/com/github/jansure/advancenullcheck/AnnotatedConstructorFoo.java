package com.github.jansure.advancenullcheck;

import java.util.Date;

import com.github.jansure.advancenullcheck.annotation.Nullable;
import com.github.jansure.advancenullcheck.annotation.Parameters;
import com.github.jansure.advancenullcheck.annotation.NonNullable;

@NonNullable({ @Parameters(type = String.class, message = "Wow!"),
    @Parameters(type = Integer.class, ignore = true) })
public class AnnotatedConstructorFoo {

  public AnnotatedConstructorFoo(String s) {}

  public AnnotatedConstructorFoo(Integer s) {}

  @NonNullable(@Parameters(type = Double.class, message = "Yay!"))
  public AnnotatedConstructorFoo(Double d) {}

  @NonNullable(@Parameters(type = Float.class, ignore = true))
  public AnnotatedConstructorFoo(Float f) {}

  @Nullable
  @NonNullable
  public AnnotatedConstructorFoo(Date d) {}

}
