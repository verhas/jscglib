package com.javax0.jscglib;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JSC {

	private JSCType jscType;

	public JSCType getJscType() {
		return jscType;
	}

	public void setJscType(final JSCType jscType) {
		this.jscType = jscType;
	}

	private String packge;

	public JSC inPackage(final String packge) {
		this.packge = packge;
		return this;
	}

	public JSC inPackage(final Package packge) {
		this.packge = packge.getName();
		return this;
	}

	private int modifiers = 0;

	public JSC modifiers(final int... modifiers) {
		return modifier(modifiers);
	}

	public JSC modifier(final int... modifiers) {
		for (final int modifier : modifiers) {
			this.modifiers |= modifier;
		}
		return this;
	}

	private String parentClass = null;

	public JSC parent(final Class<?> parent) {
		parentClass = parent.getCanonicalName();
		return this;
	}

	public JSC parent(final Package packge, final String className) {
		parentClass = packge.toString() + "." + className;
		return this;
	}

	public JSC parent(final String packge, final String className) {
		parentClass = packge + "." + className;
		return this;
	}

	public JSC parent(final String className) {
		parentClass = className;
		return this;
	}

	private List<String> intrfaces = new LinkedList<>();

	public JSC interfaces(final String... intrfaces) {
		this.intrfaces = Arrays.asList(intrfaces);
		return this;
	}

	public JSC interfaces(final Class<?>... intrfaces) {
		for (final Class<?> intrface : intrfaces) {
			this.intrfaces.add(intrface.getCanonicalName());
		}
		return this;
	}

	private final List<JSC> declaredBlocks = new LinkedList<>();

	public JSC add(final JSC jsc) {
		declaredBlocks.add(jsc);
		return this;
	}

	private String type;

	public JSC returnType(final String type) {
		this.type = type;
		return this;
	}

	public JSC returnType(final Class<?> type) {
		this.type = type.getCanonicalName();
		return this;
	}

	private String identifier;

	public JSC identifier(final String identifier) {
		this.identifier = identifier;
		return this;
	}

	private final List<JSC> arguments = new LinkedList<>();

	public JSC argument(final JSC argument) {
		return arguments(argument);
	}

	public JSC arguments(final JSC... arguments) {
		this.arguments.addAll(Arrays.asList(arguments));
		return this;
	}

	private final List<String> imports = new LinkedList<>();

	public JSC imports(String... imports) {
		this.imports.addAll(Arrays.asList(imports));
		return this;
	}

	private boolean blockCommand = false;

	public boolean isBlockCommand() {
		return blockCommand;
	}

	public JSC commandBlock(String command) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.COMMAND);
		tool.simpleCommand = command;
		tool.blockCommand = true;
		return command(tool);
	}

	private final List<JSC> commands = new LinkedList<>();

	public JSC command(String command) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.COMMAND);
		tool.simpleCommand = command;
		return command(tool);
	}

	public JSC command(final JSC command) {
		return commands(command);
	}

	public JSC commands(final JSC... commands) {
		this.commands.addAll(Arrays.asList(commands));
		return this;
	}

	private final List<JSC> elseCommands = new LinkedList<>();

	public JSC elseCommands(final JSC... elseCommands) {
		this.elseCommands.addAll(Arrays.asList(elseCommands));
		return this;
	}

	private final List<JSC> catchCommands = new LinkedList<>();

	public JSC catchCommands(final JSC... catchCommands) {
		this.catchCommands.addAll(Arrays.asList(catchCommands));
		return this;
	}

	private final List<JSC> finallyCommands = new LinkedList<>();

	public JSC finallyCommands(final JSC... finallyCommands) {
		this.finallyCommands.addAll(Arrays.asList(finallyCommands));
		return this;
	}

	private final List<String> annotations = new LinkedList<>();

	public JSC annotation(String annotation) {
		return annotations(annotation);
	}

	public JSC annotations(String... annotations) {
		this.annotations.addAll(Arrays.asList(annotations));
		return this;
	}

	private final List<String> exceptions = new LinkedList<>();

	public JSC exception(String exception) {
		return exceptions(exception);
	}

	public JSC exceptions(String... exceptions) {
		this.exceptions.addAll(Arrays.asList(exceptions));
		return this;
	}

	private String expression;

	private JSC setExpression(String expression) {
		this.expression = expression;
		return this;
	}

	public JSC initValue(Object expression) {
		return setExpression(expression.toString());
	}

	public JSC initNull() {
		return setExpression("null");
	}

	public JSC condition(String expression) {
		return setExpression(expression);
	}

	private String simpleCommand;

	public JSC constructor(JSC constructor) {
		return constructors(constructor);
	}

	public JSC constructors(JSC... constructors) {
		this.declaredBlocks.addAll(Arrays.asList(constructors));
		return this;
	}

	public JSC field(Class<?> type, String name) {
		JSC tool = new JSC();
		tool.identifier(name);
		tool.returnType(type);
		tool.setJscType(JSCType.FIELD);
		return add(tool);
	}

	public JSC field(String type, String name) {
		JSC tool = new JSC();
		tool.identifier(name);
		tool.returnType(type);
		tool.setJscType(JSCType.FIELD);
		return add(tool);
	}

	public JSC field(String name) {
		JSC tool = new JSC();
		tool.identifier(name);
		tool.setJscType(JSCType.FIELD);
		return add(tool);
	}

	public JSC constructor() {
		JSC tool = new JSC();
		tool.setJscType(JSCType.CONSTRUCTOR);
		tool.identifier(this.getIdentifier());
		return constructor(tool);
	}

	public JSC argument(Class<?> type, String identifier) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.ARGUMENT);
		tool.identifier(identifier);
		tool.returnType(type);
		return argument(tool);
	}

	public JSC argument(String type, String identifier) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.ARGUMENT);
		tool.identifier(identifier);
		tool.returnType(type);
		return argument(tool);
	}

	public String toString() {
		return JSCPrinter.pprint(this);
	}

	protected String getPackge() {
		return packge;
	}

	protected int getModifiers() {
		return modifiers;
	}

	protected String getParentClass() {
		return parentClass;
	}

	protected List<String> getIntrfaces() {
		return intrfaces;
	}

	protected List<JSC> getDeclaredBlocks() {
		return declaredBlocks;
	}

	protected String getType() {
		return type;
	}

	protected String getIdentifier() {
		return identifier;
	}

	protected List<JSC> getArguments() {
		return arguments;
	}

	protected List<JSC> getCommands() {
		return commands;
	}

	protected List<JSC> getElseCommands() {
		return elseCommands;
	}

	protected List<JSC> getCatchCommands() {
		return catchCommands;
	}

	protected List<JSC> getFinallyCommands() {
		return finallyCommands;
	}

	protected List<String> getExceptions() {
		return exceptions;
	}

	protected List<String> getImports() {
		return imports;
	}

	protected List<String> getAnnotations() {
		return annotations;
	}

	protected String getExpression() {
		return expression;
	}

	protected String getSimpleCommand() {
		return simpleCommand;
	}

}
