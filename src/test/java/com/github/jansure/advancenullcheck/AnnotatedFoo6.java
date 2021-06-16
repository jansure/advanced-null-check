
package com.github.jansure.advancenullcheck;

import java.util.Date;

import com.github.jansure.advancenullcheck.annotation.Nullable;
import com.github.jansure.advancenullcheck.annotation.NonNullable;

@NonNullable
@Nullable("bar2")
public class AnnotatedFoo6 {

  public AnnotatedFoo6(String s) {}

  @Nullable
  public AnnotatedFoo6(Float f) {}

  public void bar1(Integer i, Double d) {}

  public void bar2(Date d) {}

}
