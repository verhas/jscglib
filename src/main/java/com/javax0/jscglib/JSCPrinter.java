package com.javax0.jscglib;

import java.lang.reflect.Modifier;

public class JSCPrinter {
	public static String pprint(JSC code) {
		final String retval;
		switch (code.getJscType()) {
		case CLASS:
			retval = pprintClass(code);
			break;
		case METHOD:
			retval = pprintMethod(code);
			break;
		case FIELD:
			retval = pprintField(code);
			break;
		case ARGUMENT:
			retval = pprintArgument(code);
			break;
		default:
			throw new IllegalArgumentException("The type of the JSC object '"
					+ code.getJscType().name() + "' can not be printed");
		}
		return retval;
	}

	public static String pprintMethod(JSC code) {
		CodeStringBuilder sb = new CodeStringBuilder()
				.appendLines(code.getAnnotations())
				.append(null,Modifier.toString(code.getModifiers())," ")
				.append(code.getType()).space().append(code.getIdentifier())
				.append("(").appendList(code.getArguments()).append(")")
				.appendList("throws ", code.getExceptions(), null).begin();
		for (JSC command : code.getCommands()) {
			sb.append(command.getSimpleCommand()).sc();
		}
		sb.end();
		return sb.toString();
	}

	public static String pprintField(JSC code) {
		CodeStringBuilder sb = new CodeStringBuilder()
				.appendLines(code.getAnnotations())
				.append(Modifier.toString(code.getModifiers()))
				.append(code.getType())
				.space()
				.append(code.getIdentifier())
				.append(code.getExpression() == null ? "" : (" = " + code
						.getExpression()))//
				.sc();
		return sb.toString();
	}

	public static String pprintArgument(JSC code) {
		CodeStringBuilder sb = new CodeStringBuilder().append(code.getType())
				.space().append(code.getIdentifier());
		return sb.toString();
	}

	public static String pprintClass(JSC code) {
		CodeStringBuilder sb = new CodeStringBuilder().append("package ",
				code.getPackge(), ";\n");
		for (String importPackage : code.getImports()) {
			sb.append("import ").append(importPackage).sc();
		}
		sb.appendLines(code.getAnnotations())
				.append(Modifier.toString(code.getModifiers()))
				.append(" class ").append(code.getIdentifier())
				.append(" extends ", code.getParentClass(), null)
				.appendList(" implements ", code.getIntrfaces(), null).begin();
		//
		for (JSC jsc : code.getDeclaredBlocks()) {
			sb.append(jsc.toString());
		}
		//
		sb.end();
		return sb.toString();
	}
}
