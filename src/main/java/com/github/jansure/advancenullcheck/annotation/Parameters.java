
package com.github.jansure.advancenullcheck.annotation;

import com.github.jansure.advancenullcheck.NullSpec;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 *
 * {@link Parameters} can be only used in {@link NonNullable} annotation. It
 * describes the detail actions while any null argument is found by
 * {@link NullSpec}.
 *
 */
@Retention(RUNTIME)
@Target({ ANNOTATION_TYPE, METHOD })
public @interface Parameters {

  /**
   * The target Class of arguments.
   *
   * @return any Class
   */
  Class<?> type();

  /**
   * The exception message is used while a NullPointerException is raised on
   * null arguments of target Class.
   *
   * @return a String
   */
  String message() default "";

  /**
   * Option to suppress the NullPointerException of target Class.
   *
   * @return true if the NullPointerException of target Class is ignored, false
   *         otherwise
   */
  boolean ignore() default false;

}
