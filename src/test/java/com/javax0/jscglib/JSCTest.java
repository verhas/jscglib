package com.javax0.jscglib;

import static com.javax0.jscglib.JSCBuilder.field;
import static com.javax0.jscglib.JSCBuilder.klass;
import static com.javax0.jscglib.JSCBuilder.method;

import org.junit.Assert;
import org.junit.Test;

public class JSCTest {

	@Test
	public void given_AStringWithMultipleSpaces_when_CallingDespacer_then_ReturnsWithSingleSpaces() {
		final String input = "a  b  c";
		final String expected = "a b c";
		final String actual = despaced(input);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_AStringWithSpacesBetweenNonAlpha_when_CallingDespacer_then_ReturnsWithoutSpaces() {
		final String input = "} ;";
		final String expected = "};";
		final String actual = despaced(input);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_AStringWithSpacesBetweenNonAlphaRepeated_when_CallingDespacer_then_ReturnsWithoutSpaces() {
		final String input = "} ; {";
		final String expected = "};{";
		final String actual = despaced(input);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_AStringWithSpacesBetweenAlphaNonAlphaRepeated_when_CallingDespacer_then_ReturnsWithoutSpaces() {
		final String input = "A } A";
		final String expected = "A}A";
		final String actual = despaced(input);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_AStringWithSpacesBetweenAlphaNonAlpha_when_CallingDespacer_then_ReturnsWithoutSpaces() {
		final String input = "A }";
		final String expected = "A}";
		final String actual = despaced(input);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_AStringWithSpacesBetweenNonAlphaAlpha_when_CallingDespacer_then_ReturnsWithoutSpaces() {
		final String input = "} A";
		final String expected = "}A";
		final String actual = despaced(input);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_AStringWithSpacesBetweenNonAlphaAlphaRepeated_when_CallingDespacer_then_ReturnsWithoutSpaces() {
		final String input = "} A ; B ; C";
		final String expected = "}A;B;C";
		final String actual = despaced(input);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void given_AnEmptyClassWithPackageName_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib.test;class TestClass{}";
		String actual = klass("TestClass").inPackage("com.javax0.jscglib.test")
				.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnEmptyClassWithPackage_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib;class TestClass{}";
		String actual = klass("TestClass")
				.inPackage(JSCTest.class.getPackage()).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnEmptyClass_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "class TestClass{}";
		String actual = klass("TestClass").toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndAField_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib;class TestClass{java.lang.Object name;}";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.field(Object.class, "name").toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndANullField_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib;class TestClass{java.lang.Object name=null;}";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(field(Object.class, "name").initNull()).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndAnInitializedField_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib;class TestClass{java.lang.Integer name=53;}";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(field(Integer.class, "name").initValue(53)).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndAMethod_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib;class TestClass{"
				+ "java.lang.Object name(java.lang.Object arg){"
				+ "System.out.println(\"hello\");}}";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(method(Object.class, "name").argument(Object.class, "arg")
						.command("System.out.println(\"hello\")")).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndAMethodWithCommandBlock_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib;class TestClass{"
				+ "java.lang.Object name(java.lang.Object arg){"
				+ "try{}finally{}}}";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(method(Object.class, "name").argument(Object.class, "arg")
						.commandBlock("try{}finally{}")).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithPackageAndAMethodStringReturnType_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "package com.javax0.jscglib;class TestClass{"
				+ "void name(java.lang.Object arg){"
				+ "System.out.println(\"hello\");}}";
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(method("void", "name").argument(Object.class, "arg")
						.command("System.out.println(\"hello\")")).toString();
		assertEquals(expected, actual);
	}

	@Test
	public void given_AnClassWithDefaultConstructor_when_GeneratingCode_then_ReturnsProperSourceCode() {
		final String expected = "class TestClass{TestClass(){}}";
		String actual = klass("TestClass").constructor().toString();
		assertEquals(expected, actual);
	}

	private void assertEquals(final String expected, final String actual) {
		// remove all duplicated space
		Assert.assertEquals(despaced(expected), despaced(actual));
	}

	private String despaced(String s) {
		// remove all duplicated space
		String dspced = s.replaceAll("[\\s\\n]+", " ");
		String prev;
		do {
			prev = dspced;
			// remove spaces from the start
			dspced = dspced.replaceAll("^\\s+", "");
			// remove spaces from the end
			dspced = dspced.replaceAll("\\s+$", "");
			// remove spaces from between non-alpha tokens
			dspced = dspced.replaceAll("(\\W)\\s+(\\W)", "$1$2");
			dspced = dspced.replaceAll("(\\W)\\s+(\\w)", "$1$2");
			dspced = dspced.replaceAll("(\\w)\\s+(\\W)", "$1$2");
		} while (!prev.equals(dspced));
		return dspced;
	}
}
