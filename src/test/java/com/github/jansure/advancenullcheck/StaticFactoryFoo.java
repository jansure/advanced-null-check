
package com.github.jansure.advancenullcheck;

import com.github.jansure.advancenullcheck.annotation.Nullable;
import com.github.jansure.advancenullcheck.annotation.NonNullable;

@NonNullable
public class StaticFactoryFoo {

  public static StaticFactoryFoo newInstance(String s) {
    return new StaticFactoryFoo();
  }

  @Nullable
  public static StaticFactoryFoo getInstance(String s) {
    return new StaticFactoryFoo();
  }

}
