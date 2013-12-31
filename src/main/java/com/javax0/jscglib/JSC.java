package com.javax0.jscglib;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Basic builder to create a class source code. An instance of a JSC class
 * describes a class or a part of the class. The {@code jscType} filed stores
 * the information about what the instance describes. It can be {@link JSCType}.
 * 
 * The methods in this class implement a kind of builder patters always
 * returning {@code this}.
 * 
 * @author Peter Verhas
 * 
 */
public class JSC {
	// TODO static and dynamic block building is missing
	private JSCType jscType;

	public JSCType getJscType() {
		return jscType;
	}

	/**
	 * Set the type of the JSC object to define what is describes. This method
	 * is used by the static methods implemented in {@link JSCBuilder}
	 * 
	 * @param jscType
	 */
	public void setJscType(final JSCType jscType) {
		this.jscType = jscType;
	}

	private String packge;

	/**
	 * Define which package the class is in.
	 * 
	 * @param packge
	 * @return
	 */
	public JSC inPackage(final String packge) {
		this.packge = packge;
		return this;
	}

	/**
	 * Define which package the class is in.
	 * 
	 * @param packge
	 * @return
	 */
	public JSC inPackage(final Package packge) {
		this.packge = packge.getName();
		return this;
	}

	private int modifiers = 0;

	/**
	 * Aux method with plural form calls the single version.
	 * 
	 * @param modifiers
	 * @return
	 */
	public JSC modifiers(final int... modifiers) {
		return modifier(modifiers);
	}

	/**
	 * Defines the modifiers of the class, field, method or constructor.
	 * 
	 * @param modifiers
	 *            use the reflection class {@link java.lang.reflect.Modifier}
	 *            for the constants. You can combine the arguments using the
	 *            bitwise OR operator, or you can list them as separate
	 *            argument, whichever result readable code.
	 * @return
	 */
	public JSC modifier(final int... modifiers) {
		for (final int modifier : modifiers) {
			this.modifiers |= modifier;
		}
		return this;
	}

	private String parentClass = null;

	/**
	 * Defines the parent class, that the actual class extends.
	 * 
	 * @param parent
	 * @return
	 */
	public JSC parent(final Class<?> parent) {
		parentClass = parent.getCanonicalName();
		return this;
	}

	/**
	 * Defines the parent class, that the actual class extends.
	 * 
	 * @param packge
	 *            the package where the parent class is in
	 * @param className
	 *            the name of the parent class without the package name
	 * @return
	 */
	public JSC parent(final Package packge, final String className) {
		parentClass = packge.toString() + "." + className;
		return this;
	}

	/**
	 * Defines the parent class, that the actual class extends.
	 * 
	 * @param packge
	 *            the fully qualified name of the parent class' package
	 * @param className
	 *            the name of the parent class without the package name
	 * @return
	 */
	public JSC parent(final String packge, final String className) {
		parentClass = packge + "." + className;
		return this;
	}

	/**
	 * 
	 * Defines the parent class, that the actual class extends.
	 * 
	 * @param className
	 *            the fully qualified class name including the package name
	 * @return
	 */
	public JSC parent(final String className) {
		parentClass = className;
		return this;
	}

	private List<String> intrfaces = new LinkedList<>();

	/**
	 * Defines the interfaces that the class implements.
	 * 
	 * @param intrfaces
	 *            the fully qualified names of the interfaces that are listed
	 *            following the keyword {@code implements}
	 * @return
	 */
	public JSC interfaces(final String... intrfaces) {
		this.intrfaces = Arrays.asList(intrfaces);
		return this;
	}

	/**
	 * Defines the interfaces that the class implements.
	 * 
	 * @param intrfaces
	 * @return
	 */
	public JSC interfaces(final Class<?>... intrfaces) {
		for (final Class<?> intrface : intrfaces) {
			this.intrfaces.add(intrface.getCanonicalName());
		}
		return this;
	}

	private final List<JSC> declaredBlocks = new LinkedList<>();

	/**
	 * Add some block to the class, like a method, a construtor aor just
	 * anything that can be in a class.
	 * 
	 * @param jsc
	 * @return
	 */
	public JSC add(final JSC jsc) {
		declaredBlocks.add(jsc);
		return this;
	}

	private String type;

	/**
	 * Define the return type of a method or a field.
	 * 
	 * @param type
	 * @return
	 */
	public JSC returnType(final String type) {
		this.type = type;
		return this;
	}

	/**
	 * Define the return type of a method or a field.
	 * 
	 * @param type
	 * @return
	 */
	public JSC returnType(final Class<?> type) {
		this.type = type.getCanonicalName();
		return this;
	}

	private String identifier;

	/**
	 * Define the identifier of the actual jsc. This is the name of a field, the
	 * name of a class etc. Many calls implicitly set this.
	 * 
	 * @param identifier
	 * @return
	 */
	public JSC identifier(final String identifier) {
		this.identifier = identifier;
		return this;
	}

	private final List<JSC> arguments = new LinkedList<>();

	/**
	 * Define the arguments of a constructor or a method. Chaining this method
	 * has the same effect as calling the plural form of this method with many
	 * arguments.
	 * 
	 * @param argument
	 * @return
	 */
	public JSC argument(final JSC argument) {
		return arguments(argument);
	}

	/**
	 * Define one or more arguments for a constructor or a method.
	 * 
	 * @param arguments
	 * @return
	 */
	public JSC arguments(final JSC... arguments) {
		this.arguments.addAll(Arrays.asList(arguments));
		return this;
	}

	private final List<String> imports = new LinkedList<>();

	/**
	 * Add imports to the class. The arguments have to be the fully qualified
	 * class names.
	 * 
	 * @param imports
	 * @return
	 */
	public JSC imports(String... imports) {
		this.imports.addAll(Arrays.asList(imports));
		return this;
	}

	private boolean blockCommand = false;

	boolean isBlockCommand() {
		return blockCommand;
	}

	/**
	 * Create a block command. Block commands are enclosed between '{' and '}'
	 * and do not need ';' after them. Using this method the caller can define a
	 * block command, like an 'if', 'do', 'when'.
	 * 
	 * @param command
	 *            the string of the command containing the opening and closing
	 *            '{' and '}' characters.
	 * @return
	 */
	public JSC commandBlock(String command) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.COMMAND);
		tool.simpleCommand = command;
		tool.blockCommand = true;
		return command(tool);
	}

	private final List<JSC> commands = new LinkedList<>();

	/**
	 * Add a command to the actual constructor or method or other block.
	 * 
	 * @param command
	 *            the string of the command without the closing ';'
	 * @return
	 */
	public JSC command(String command) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.COMMAND);
		tool.simpleCommand = command;
		return command(tool);
	}

	/**
	 * Add a block as a command. This is the method to call to create a block
	 * command for example.
	 * 
	 * @param command
	 * @return
	 */
	public JSC command(final JSC command) {
		return commands(command);
	}

	/**
	 * Add one or more commands.
	 * 
	 * @param commands
	 *            array of comamnds. The commands do not include the closing ';'
	 * @return
	 */
	public JSC commands(final JSC... commands) {
		this.commands.addAll(Arrays.asList(commands));
		return this;
	}

	private final List<String> annotations = new LinkedList<>();

	/**
	 * Give annotation to the element.
	 * 
	 * @param annotation
	 * @return
	 */
	public JSC annotation(String annotation) {
		return annotations(annotation);
	}

	/**
	 * Give one or more annotation to the element.
	 * 
	 * @param annotations
	 * @return
	 */
	public JSC annotations(String... annotations) {
		this.annotations.addAll(Arrays.asList(annotations));
		return this;
	}

	private final List<String> exceptions = new LinkedList<>();

	/**
	 * Add an exception to the method or constructor.
	 * 
	 * @param exception
	 * @return
	 */
	public JSC exception(String exception) {
		return exceptions(exception);
	}

	public JSC exceptions(String... exceptions) {
		this.exceptions.addAll(Arrays.asList(exceptions));
		return this;
	}

	public JSC exceptions(Class<?>[] throwables) {
		for (Class<?> throwable : throwables) {
			this.exceptions.add(throwable.getName());
		}
		return this;
	}

	private String expression;

	private JSC setExpression(String expression) {
		this.expression = expression;
		return this;
	}

	/**
	 * Set the expression that initializes a field.
	 * 
	 * @param expression
	 * @return
	 */
	public JSC initValue(Object expression) {
		return setExpression(expression.toString());
	}

	/**
	 * Set the expression to be "null" to initialize a field.
	 * 
	 * @return
	 */
	public JSC initNull() {
		return setExpression("null");
	}

	String simpleCommand;

	/**
	 * Add a "default" construtor to the class.
	 * 
	 * @return
	 */
	public JSC constructor() {
		JSC tool = new JSC();
		tool.setJscType(JSCType.CONSTRUCTOR);
		tool.identifier(this.getIdentifier());
		return add(tool);
	}

	/**
	 * Add a field to a class.
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	public JSC field(Class<?> type, String name) {
		JSC tool = new JSC();
		tool.identifier(name);
		tool.returnType(type);
		tool.setJscType(JSCType.FIELD);
		return add(tool);
	}

	/**
	 * Sting variant to add a field to a class.
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	public JSC field(String type, String name) {
		JSC tool = new JSC();
		tool.identifier(name);
		tool.returnType(type);
		tool.setJscType(JSCType.FIELD);
		return add(tool);
	}

	/**
	 * Specify a parameter for a method or for a constructor.
	 * 
	 * @param type
	 * @param identifier
	 * @return
	 */
	public JSC argument(Class<?> type, String identifier) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.ARGUMENT);
		tool.identifier(identifier);
		tool.returnType(type);
		return argument(tool);
	}

	/**
	 * String version of the argument definition method.
	 * 
	 * @param type
	 * @param identifier
	 * @return
	 */
	public JSC argument(String type, String identifier) {
		JSC tool = new JSC();
		tool.setJscType(JSCType.ARGUMENT);
		tool.identifier(identifier);
		tool.returnType(type);
		return argument(tool);
	}

	/**
	 * Convert the generated structure to string and thus ready to be passed to
	 * the compiler.
	 */
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
