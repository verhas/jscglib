package com.javax0.jscglib;

import static com.javax0.jscglib.JSCBuilder.argument;
import static com.javax0.jscglib.JSCBuilder.command;
import static com.javax0.jscglib.JSCBuilder.constructor;
import static com.javax0.jscglib.JSCBuilder.field;
import static com.javax0.jscglib.JSCBuilder.klass;
import static com.javax0.jscglib.JSCBuilder.method;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class JSCTest {

	@Rule
	public TestName name = new TestName();

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
	public void given_AnEmptyClassWithPackageName_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass").inPackage("com.javax0.jscglib.test")
				.toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AnEmptyClassWithPackage_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass")
				.inPackage(JSCTest.class.getPackage()).toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AnEmptyClass_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass").toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AClassWithPackageAndAField_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.field(Object.class, "name").toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AClassWithPackageAndANullField_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(field(Object.class, "name").initNull()).toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AClassWithPackageAndAnInitializedField_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(field(Integer.class, "name").initValue(53)).toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AClassWithPackageAndAMethod_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(method(Object.class, "name").argument(Object.class, "arg")
						.command("System.out.println(\"hello\")")).toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AClassWithPackageAndAMethodWithCommandBlock_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(method(Object.class, "name").argument(Object.class, "arg")
						.commandBlock("try{}finally{}")).toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AClassWithPackageAndAMethodStringReturnType_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass")
				.inPackage(this.getClass().getPackage())
				.add(method("void", "name").argument(Object.class, "arg")
						.command("System.out.println(\"hello\")")).toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AClassWithDefaultConstructor_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		String actual = klass("TestClass").constructor().toString();
		assertSourceIsOK(actual);
	}

	@Test
	public void given_AComplexClass_when_GeneratingCode_then_ReturnsProperSourceCode()
			throws Exception {
		JSC klass = klass("TestClass");
		String actual = klass
				.add(constructor(klass).argument(Long.class, "lng")
						.argument(Integer.class, "inti")
						.modifier(Modifier.PROTECTED)
						.command(command("this.lng = lng"))
						.command(command("that.lng = lng")))
				.add(method("myMethod")
						.returnType("String")
						.arguments(argument("int", "i"),
								argument("String", "s")).command("return s"))
				//
				.field("Object", "object")
				.field(Object.class, "object2")
				.add(field("object3").modifier(Modifier.FINAL).initValue("55")
						.returnType("Object")).toString();
		assertSourceIsOK(actual);
	}

	//
	// A U X M E T H O D S
	//
	private void assertSourceIsOK(final String actual) throws IOException {
		final String expected = loadJavaSource();
		if (expected == null) {
			saveGeneratedProgram(actual);
			Assert.fail(name.getMethodName() + " expected file is missing");
		}
		// remove all duplicated space
		Assert.assertEquals(despaced(expected), despaced(actual));
	}

	private void saveGeneratedProgram(String actual) throws IOException {
		File file = new File(getTargetFileName());
		file.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buf = actual.getBytes("utf-8");
		fos.write(buf, 0, buf.length);
		fos.close();
	}

	private String loadJavaSource() {
		try {
			String fileName = getResourceName();
			InputStream is = this.getClass().getResourceAsStream(fileName);
			byte[] buf = new byte[3000];
			int len = is.read(buf);
			is.close();
			return new String(buf, 0, len, "utf-8");
		} catch (Exception ie) {
			return null;
		}
	}

	private String getTargetFileName() {
		String testName = name.getMethodName();
		String fileName = "target/resources/" + testName + ".java";
		return fileName;
	}

	private String getResourceName() {
		String testName = name.getMethodName();
		return testName + ".java";
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
