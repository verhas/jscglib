package com.javax0.jscglib;

import org.junit.Assert;
import org.junit.Test;

import static com.javax0.jscglib.JSCBuilder.*;

public class JSCTest {

	@Test
	public void given_AnEmptyClassWithPackageName_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib.test; class TestClass{ } ";
		String actual = klass("TestClass").inPackage("com.javax0.jscglib.test")
				.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnEmptyClassWithPackage_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib; class TestClass{ } ";
		String actual = klass("TestClass")
				.inPackage(JSCTest.class.getPackage()).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnEmptyClass_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "class TestClass{ } ";
		String actual = klass("TestClass").toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndAField_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib; class TestClass{ java.lang.Object name; } ";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.field(Object.class, "name").toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndANullField_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib; class TestClass{ java.lang.Object name = null; } ";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(field(Object.class, "name").initNull()).toString();
		assertEquals(expected, actual);
	}
	@Test
	public void given_AnClassWithPackageAndAnInitializedField_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib; class TestClass{ java.lang.Integer name = 53; } ";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(field(Integer.class, "name").initValue(53)).toString();
		assertEquals(expected, actual);
	}
	@Test
	public void given_AnClassWithPackageAndAMethod_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib; class TestClass{ "
				+ "java.lang.Object name(java.lang.Object arg){ "
				+ "System.out.println(\"hello\") ; } } ";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(method(Object.class, "name").argument(Object.class,
						"arg").command("System.out.println(\"hello\")"))
				.toString();
		assertEquals(expected, actual);
	}

	private void assertEquals(final String expected, final String actual) {
		Assert.assertEquals(expected, actual.replaceAll("[\\s\\n]+", " "));
	}
}
