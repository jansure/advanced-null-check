package com.github.jansure.advancenullcheck;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.NullPointerTester;
import com.google.inject.CreationException;
import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;

public class NullSpecTest {

  Foo foo;
  AnnotatedFoo1 annFoo1;

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    foo = new Foo();
    annFoo1 = NullSpec.of(AnnotatedFoo1.class);
  }

  @Test
  public void testNullCheckIsWorkingWithoutRejectNullAnnotation() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    NullSpec.of(NotAnnotatedFoo.class).bar(null);
  }

  @Test
  public void testPrivateConstructor() throws Exception {
    Constructor<NullSpec> c = NullSpec.class.getDeclaredConstructor();
    assertTrue(Modifier.isPrivate(c.getModifiers()));
    c.setAccessible(true);
    c.newInstance();
  }

  @Test
  public void testNullCheckWithNormalArgument() {
    NullSpec.of(Foo.class, 1);
  }

  @Test(expected = CreationException.class)
  public void testNullCheckWithNullArgument() {
    NullSpec.of(Foo.class, (Integer) null);
  }

  @Test(expected = CreationException.class)
  public void testNullCheckWithWrongArgumentNumber() {
    NullSpec.of(Foo.class, 1, 2);
  }

  @Test(expected = CreationException.class)
  public void testNullCheckWithWrongTypeArgument() {
    NullSpec.of(Foo.class, new Date());
  }

  @Test
  public void testNullCheckConstructor() {
    new NullSpec.Constructor<Foo>(Foo.class)
        .forType(new TypeLiteral<Map<String, Integer>>() {})
        .addArgument(new HashMap<String, Integer>()).create();
  }

  @Test(expected = CreationException.class)
  public void tesetNullObjectCanNotBind1() {
    new NullSpec.Constructor<Foo>(Foo.class)
        .forType(new TypeLiteral<Map<String, Integer>>() {}).addArgument(null)
        .create();
  }

  @Test(expected = CreationException.class)
  public void testNullObjectCanNotBind2() {
    NullSpec.of(Foo.class, (String) null);
  }

  @Test
  public void testNormalException() {
    try {
      foo.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage()
          .startsWith("Parameter<String> is not nullable"));
    }
  }

  @Test
  public void testEqulasWithNull() {
    foo.equals((Object) null);
  }

  @Test
  public void testOverloadedEqulasWithNull() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    foo.equals((Integer) null);
  }

  @Test
  public void testNotOverridedEquals() {
    NullSpec.of(AnnotatedFoo4.class, "").equals(null);
  }

  @Test
  public void testEqualsWithMoreThanOneArgument() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    NullSpec.of(Foo.class).equals(1, null);
  }

  @Test
  public void testNullCheckIsNotAffectPrivateMethods() {
    foo.barString("");
  }

  @Test
  public void testAllPublicMethodNPE() throws Exception {
    new NullPointerTester().ignore(
        foo.getClass().getMethod("equals", Object.class))
        .testAllPublicInstanceMethods(foo);
  }

  @Test
  public void testGlobalRejectNullExceptionMessage() {
    try {
      annFoo1.barString(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Oop!"));
    }
  }

  @Test
  public void testGlobalRejectNullIngore() {
    annFoo1.barInteger(null);
  }

  @Test
  public void testGlobalAccectNullIngore() {
    annFoo1.barDouble(null);
  }

  @Test
  public void testLocalAccectNullIngore() {
    annFoo1.barByte(null);
  }

  @Test
  public void testNormalRejectNull() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    annFoo1.barFloat(null);
  }

  @Test
  public void testLocalRejectNullIngore() {
    annFoo1.barLong(null);
  }

  @Test
  public void testLocalRejectNullExceptionMessage() {
    try {
      annFoo1.barNumber(null);
      fail();
    } catch (NullPointerException ex) {
      assertTrue(ex.getMessage().startsWith("Noop!"));
    }
  }

  @Test
  public void testLocalRejectNullOverride() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    annFoo1.barInteger2(null);
  }

  @Test
  public void testAcceptNullIsHigherThanRejectNull() {
    annFoo1.barDate(null);
  }

  @Test
  public void testRejectNullWithoutAcceptNullOnClass() {
    expectedEx.expect(NullPointerException.class);
    expectedEx.expectMessage("is not nullable");
    AnnotatedFoo2 annFoo2 = NullSpec.of(AnnotatedFoo2.class);
    annFoo2.barFloat(null);
  }

  @Test
  public void testOnlyAcceptNullOnClass() {
    AnnotatedFoo3 annFoo3 = NullSpec.of(AnnotatedFoo3.class);
    annFoo3.barInteger(null);
    annFoo3.barDouble(null);
  }

  @Test
  public void testByteConstructor() {
    foo = NullSpec.of(Foo.class, Byte.valueOf("0"), "");
  }

  @Test
  public void testShortConstructor() {
    foo = NullSpec.of(Foo.class, Short.valueOf("0"), "");
  }

  @Test
  public void testIntConstructor() {
    foo = NullSpec.of(Foo.class, Integer.valueOf("0"), "");
  }

  @Test
  public void testLongConstructor() {
    foo = NullSpec.of(Foo.class, Long.valueOf("0"), "");
  }

  @Test
  public void testFloatConstructor() {
    foo = NullSpec.of(Foo.class, Float.valueOf("0"), "");
  }

  @Test
  public void testDoubleConstructor() {
    foo = NullSpec.of(Foo.class, Double.valueOf("0"), "");
  }

  @Test
  public void testBooleanConstructor() {
    foo = NullSpec.of(Foo.class, Boolean.FALSE, "");
  }

  @Test
  public void testCharConstructor() {
    foo = NullSpec.of(Foo.class, Character.valueOf('a'), "");
  }

  @Test
  public void testNoAutoboxing_byte() {
    NullSpec.of(NoAutoboxingFoo.class, (byte) 1);
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Byte() {
    NullSpec.of(NoAutoboxingFoo.class, Byte.valueOf("0"));
  }

  @Test
  public void testNoAutoboxing_short() {
    NullSpec.of(NoAutoboxingFoo.class, (short) 1);
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Short() {
    NullSpec.of(NoAutoboxingFoo.class, Short.valueOf("0"));
  }

  @Test
  public void testNoAutoboxing_int() {
    NullSpec.of(NoAutoboxingFoo.class, (int) 1);
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Integer() {
    NullSpec.of(NoAutoboxingFoo.class, Integer.valueOf("0"));
  }

  @Test
  public void testNoAutoboxing_long() {
    NullSpec.of(NoAutoboxingFoo.class, (long) 1);
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Long() {
    NullSpec.of(NoAutoboxingFoo.class, Long.valueOf("0"));
  }

  @Test
  public void testNoAutoboxing_float() {
    NullSpec.of(NoAutoboxingFoo.class, (float) 1);
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Float() {
    NullSpec.of(NoAutoboxingFoo.class, Float.valueOf("0"));
  }

  @Test
  public void testNoAutoboxing_double() {
    NullSpec.of(NoAutoboxingFoo.class, (double) 1);
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Double() {
    NullSpec.of(NoAutoboxingFoo.class, Double.valueOf("0"));
  }

  @Test
  public void testNoAutoboxing_boolean() {
    NullSpec.of(NoAutoboxingFoo.class, false);
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Boolean() {
    NullSpec.of(NoAutoboxingFoo.class, Boolean.FALSE);
  }

  @Test
  public void testNoAutoboxing_char() {
    NullSpec.of(NoAutoboxingFoo.class, 'a');
  }

  @Test(expected = ProvisionException.class)
  public void testNoAutoboxing_Character() {
    NullSpec.of(NoAutoboxingFoo.class, Character.valueOf('a'));
  }

  @Test
  public void testNoPrimitive_byte() {
    NullSpec.of(NoPrimitiveFoo.class, (byte) 1);
  }

  @Test
  public void testNoPrimitive_short() {
    NullSpec.of(NoPrimitiveFoo.class, (short) 1);
  }

  @Test
  public void testNoPrimitive_int() {
    NullSpec.of(NoPrimitiveFoo.class, 1);
  }

  @Test
  public void testNoPrimitive_long() {
    NullSpec.of(NoPrimitiveFoo.class, 1L);
  }

  @Test
  public void testNoPrimitive_float() {
    NullSpec.of(NoPrimitiveFoo.class, 1f);
  }

  @Test
  public void testNoPrimitive_double() {
    NullSpec.of(NoPrimitiveFoo.class, 1d);
  }

  @Test
  public void testNoPrimitive_boolean() {
    NullSpec.of(NoPrimitiveFoo.class, false);
  }

  @Test
  public void testNoPrimitive_char() {
    NullSpec.of(NoPrimitiveFoo.class, 'a');
  }

}
