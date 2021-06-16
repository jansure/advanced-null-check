package com.github.jansure.advancenullcheck;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.github.jansure.advancenullcheck.annotation.Nullable;
import com.github.jansure.advancenullcheck.annotation.Parameters;
import com.github.jansure.advancenullcheck.annotation.NonNullable;

/**
 * 
 * {@link NullException} is a guide MethodInterceptor which is designed to raise a
 * NullPointerException if any null argument is found during a method call.
 */
public final class NullException implements MethodInterceptor {

  private static final int REGULAR_ERROR = -1;
  private static final int NO_ERROR = Integer.MIN_VALUE;
  private static final Parameters[] emptyArgAnnotAry = new Parameters[0];

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    blockNulls(invocation.getMethod(), invocation.getArguments());
    return invocation.proceed();
  }

  /**
   * Throws NullPointerException if any null argument of given Constructor is
   * detected.
   * 
   * @param ct
   *          any Constructor
   * @param args
   *          arguments of the Constructor
   * @throws NullPointerException
   *           if any null argument is detected
   *
   */
  public static void blockNulls(Constructor<?> ct, Object[] args) {
    if (args.length == 0)
      return;

    NonNullable methodRN = ct.getAnnotation(NonNullable.class);
    if (methodRN != null) {
      preventNulls(ct, ct.getParameterTypes(), args, methodRN.value());
      return;
    }

    Class<?> klass = ct.getDeclaringClass();
    Nullable classAN = klass.getAnnotation(Nullable.class);
    NonNullable classRN = klass.getAnnotation(NonNullable.class);
    if (classAN == null
        || notFoundIn(classAN.value(),
            ct.getName().substring(ct.getName().lastIndexOf('.') + 1)))
      preventNulls(ct, ct.getParameterTypes(), args, classRN == null
          ? emptyArgAnnotAry : classRN.value());
  }

  /**
   * Throws NullPointerException if any null argument of given Method is
   * detected.
   * 
   * @param m
   *          any Method
   * @param args
   *          arguments of the Method
   * @throws NullPointerException
   *           if any null argument is detected
   */
  public static void blockNulls(Method m, Object[] args) {
    if (args.length == 0)
      return;

    // Object#equals is always ignored
    if (m.getName().equals("equals") && args.length == 1) {
      if (Object.class.equals(m.getParameterTypes()[0]))
        return;
    }

    Nullable methodAN = m.getAnnotation(Nullable.class);
    if (methodAN != null)
      return;

    NonNullable methodRN = m.getAnnotation(NonNullable.class);
    if (methodRN != null) {
      preventNulls(m, m.getParameterTypes(), args, methodRN.value());
      return;
    }

    Class<?> klass = m.getDeclaringClass();
    Nullable classAN = klass.getAnnotation(Nullable.class);
    NonNullable classRN = klass.getAnnotation(NonNullable.class);
    if (classAN == null || notFoundIn(classAN.value(), m.getName()))
      preventNulls(m, m.getParameterTypes(), args, classRN == null
          ? emptyArgAnnotAry : classRN.value());
  }

  private static void preventNulls(Member m, Class<?>[] argTypes,
      Object[] args, Parameters[] parameters) {
    for (int i = 0; i < argTypes.length; i++) {
      Object arg = args[i];
      Class<?> type = argTypes[i];
      int errorType = checkErrorType(parameters, arg, type);
      if (errorType == NO_ERROR)
        continue;
      else if (errorType == REGULAR_ERROR)
        throw new NullPointerException("Parameter<" + type.getSimpleName()
            + "> is not nullable" + buildSuffix(m, argTypes));
      else
        throw new NullPointerException(parameters[errorType].message()
            + buildSuffix(m, argTypes));
    }
  }

  private static String buildSuffix(Member m, Class<?>[] argTypes) {
    Class<?> klass = m.getDeclaringClass();

    ClassPool pool = ClassPool.getDefault();
    int lineNum = 1;
    CtConstructor constructor;
    CtMethod method;
    try {
      CtClass cc = pool.get(klass.getName());
      CtClass[] ctClasses = new CtClass[argTypes.length];
      for (int i = 0; i < ctClasses.length; i++) {
        ctClasses[i] = pool.get(argTypes[i].getName());
      }
      if (m instanceof Constructor) {
        constructor = cc.getDeclaredConstructor(ctClasses);
        lineNum = constructor.getMethodInfo().getLineNumber(0);
      } else {
        method = cc.getDeclaredMethod(m.getName(), ctClasses);
        lineNum = method.getMethodInfo().getLineNumber(0) - 1;
      }
    } catch (NotFoundException e) {}

    if (m instanceof Constructor)
      return "\n\tat " + klass.getName() + "." + klass.getSimpleName() + "("
          + klass.getSimpleName() + ".java:" + lineNum + ")";
    else
      return "\n\tat " + klass.getName() + "." + m.getName() + "("
          + klass.getSimpleName() + ".java:" + lineNum + ")";
  }

  private static boolean notFoundIn(String[] nullables, String methodName) {
    for (String name : nullables) {
      if (methodName.equals(name))
        return false;
    }
    return true;
  }

  private static int checkErrorType(Parameters[] parameters, Object arg,
                                    Class<?> klass) {
    if (arg != null)
      return NO_ERROR;

    for (int i = 0; i < parameters.length; i++) {
      Parameters a = parameters[i];
      if (a.type().equals(klass)) {
        if (a.ignore())
          return NO_ERROR;
        else
          return i;
      }
    }

    return REGULAR_ERROR;
  }

}
