
package com.github.jansure.advancenullcheck;

public class NoAutoboxingFoo {

  NoAutoboxingFoo(byte b) {}

  NoAutoboxingFoo(Byte b) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(short s) {}

  NoAutoboxingFoo(Short s) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(int i) {}

  NoAutoboxingFoo(Integer i) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(long l) {}

  NoAutoboxingFoo(Long l) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(float f) {}

  NoAutoboxingFoo(Float f) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(double d) {}

  NoAutoboxingFoo(Double d) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(boolean b) {}

  NoAutoboxingFoo(Boolean b) {
    throw new IllegalArgumentException();
  }

  NoAutoboxingFoo(char c) {}

  NoAutoboxingFoo(Character c) {
    throw new IllegalArgumentException();
  }

}
