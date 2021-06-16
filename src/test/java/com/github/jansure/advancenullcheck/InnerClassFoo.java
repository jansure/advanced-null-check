
package com.github.jansure.advancenullcheck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.jansure.advancenullcheck.annotation.NonNullable;

@NonNullable
public class InnerClassFoo {

  public InnerClassFoo() {
    new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {}

    };
  }

}
