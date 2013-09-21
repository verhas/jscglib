package com.javax0.jscglib;

public class JSCBuilder {
	public static JSC klass(String name) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.CLASS);
		tool.identifier(name);
		return tool;
	}

	public static JSC field(Class<?> type, String name) {
		JSC tool = field(name);
		tool.returnType(type);
		return tool;
	}

	public static JSC field(String type, String name) {
		JSC tool = field(name);
		tool.returnType(type);
		return tool;
	}

	public static JSC field(String name) {
		JSC tool = new JSC();
		tool.identifier(name);
		tool.setJscType(JSCType.FIELD);
		return tool;
	}

	public static JSC constructor(JSC klass) {
		JSC tool = new JSC();
		tool.identifier(klass.getIdentifier());
		tool.setJscType(JSCType.CONSTRUCTOR);
		return tool;
	}

	public static JSC method(String type, String name) {
		JSC tool = method(name);
		tool.returnType(type);
		return tool;
	}

	public static JSC method(Class<?> type, String name) {
		JSC tool = method(name);
		tool.identifier(name);
		tool.returnType(type);
		return tool;
	}

	public static JSC method(String name) {
		JSC tool = new JSC();
		tool.identifier(name);
		tool.setJscType(JSCType.METHOD);
		return tool;
	}

	public static JSC argument(Class<?> type, String identifier) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.ARGUMENT);
		tool.identifier(identifier);
		tool.returnType(type);
		return tool;
	}

	public static JSC argument(String type, String identifier) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.ARGUMENT);
		tool.identifier(identifier);
		tool.returnType(type);
		return tool;
	}

	public static JSC command(String command) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.COMMAND);
		tool.simpleCommand = command;
		return tool;
	}
}
