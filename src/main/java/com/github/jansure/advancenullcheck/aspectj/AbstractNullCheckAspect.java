
package com.github.jansure.advancenullcheck.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.jansure.advancenullcheck.NullException;

@Aspect
public abstract class AbstractNullCheckAspect {

  @Pointcut("within(@com.github.jansure.advancenullcheck.annotation.NonNullable *)")
  public void classAnnotatedWithRejectNull() {}

  @Pointcut("args(*, ..)")
  public void atLeast1Argument() {}

  @Pointcut("execution(*.new(..))")
  public void constructor() {}

  @Pointcut("classAnnotatedWithRejectNull() && constructor() && atLeast1Argument() "
      + "&& !@annotation(com.github.jansure.advancenullcheck.annotation.Nullable)")
  public
      void constructorOfRejectNullAnnotatedClassWithoutAcceptNull() {}

  @Before("constructorOfRejectNullAnnotatedClassWithoutAcceptNull()")
  public void rejectNullForConsructors(JoinPoint jointPoint) {
    ConstructorSignature sig = (ConstructorSignature) jointPoint.getSignature();
    NullException.blockNulls(sig.getConstructor(), jointPoint.getArgs());
  }

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Pointcut("execution(public boolean equals(Object))")
  public void equalsMethod() {}

  @Pointcut("classAnnotatedWithRejectNull() && publicMethod() && !equalsMethod() && atLeast1Argument() "
      + "&& !@annotation(com.github.jansure.advancenullcheck.annotation.Nullable)")
  public
      void publicMethodOfRejectNullAnnotatedClassWithoutAcceptNull() {}

  @Before("publicMethodOfRejectNullAnnotatedClassWithoutAcceptNull()")
  public void rejectNullForPublicMethods(JoinPoint jointPoint) {
    MethodSignature sig = (MethodSignature) jointPoint.getSignature();
    NullException.blockNulls(sig.getMethod(), jointPoint.getArgs());
  }

}
