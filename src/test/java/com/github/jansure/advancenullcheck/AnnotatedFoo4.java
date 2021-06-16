
package com.github.jansure.advancenullcheck;

import com.github.jansure.advancenullcheck.annotation.Nullable;
import com.github.jansure.advancenullcheck.annotation.NonNullable;

@NonNullable
@Nullable("AnnotatedFoo4")
public class AnnotatedFoo4 {

  public AnnotatedFoo4() {}

  public AnnotatedFoo4(String s) {}

  public void bar() {}

}
