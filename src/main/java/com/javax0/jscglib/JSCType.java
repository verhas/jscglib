package com.javax0.jscglib;

/**
 * Lists the possible values for a {@link JSC} to denote what a {@link JSC} object describes. The values are
 * <ol>
 * <ul>CLASS the object describes a whole class</ul>
 * <ul>INTERFACE the object describes an interface</ul>
 * <ul>ENUM the object describes an enum</ul>
 * <ul>METHOD</ul>
 * <ul>ARGUMENT describes an argument to a method and to a constructor</ul>
 * <ul>COMMAND describes a command that can appear in a constructor or in a method</ul>
 * <ul>CONSTRUCTOT describes a constructor</ul>
 * </ol>
 * @author verhasp
 *
 */
public enum JSCType {
		CLASS, INTERFACE, ENUM, FIELD, METHOD, ARGUMENT, COMMAND, CONSTRUCTOR
}
