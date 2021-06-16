package com.github.jansure.advancenullcheck;

import java.util.Map;

import com.github.jansure.advancenullcheck.annotation.NonNullable;

@NonNullable
public class Foo {

  public Foo(byte b, String s) {}

  public Foo(short s, String str) {}

  public Foo(int i, String s) {}

  public Foo(long l, String s) {}

  public Foo(float f, String s) {}

  public Foo(double d, String s) {}

  public Foo(boolean b, String s) {}

  public Foo(char c, String s) {}

  public Foo(Map<String, Integer> map) {}

  public Foo(String s) {}

  public Foo() {}

  private void bar(String s) {}

  public void barString(String s) {
    bar(null);
  }

  public void barInteger(Integer i) {}

  public void barDouble(Double d) {}

  public boolean equals(Integer i) {
    return false;
  }

  public boolean equals(Integer i, Double d) {
    return false;
  }

}
