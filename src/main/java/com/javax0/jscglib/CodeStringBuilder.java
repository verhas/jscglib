package com.javax0.jscglib;

import java.util.List;

public final class CodeStringBuilder {

	CodeStringBuilder() {
	}

	private static final int tabSize = 2;

	private final StringBuilder sb = new StringBuilder();
	private int tabs = 0;

	CodeStringBuilder append(String s) {
		if (s != null) {
			sb.append(s);
		}
		return this;
	}

	CodeStringBuilder append(String prefix, String s, String postfix) {
		if (s != null) {
			append(prefix);
			append(s);
			append(postfix);
		}
		return this;
	}

	private String sep = "";

	private void startList() {
		sep = "";
	}

	CodeStringBuilder space() {
		append(" ");
		return this;
	}

	private CodeStringBuilder space(int n) {
		for (int i = 0; i < n; i++) {
			space();
		}
		return this;
	}

	CodeStringBuilder begin() {
		append("{\n");
		tabs += tabSize;
		space(tabs);
		return this;
	}

	CodeStringBuilder end() {
		append("\n");
		tabs -= tabSize;
		space(tabs);
		append("}\n");
		space(tabs);
		return this;
	}

	private void separator() {
		append(sep);
		sep = ",";
	}

	CodeStringBuilder sc() {
		space(tabs);
		return append(";\n");
	}

	CodeStringBuilder nl() {
		space(tabs);
		return append("\n");
	}

	/**
	 * Same as {@link #appendList(List)} but also appends the prefix string
	 * before the concatenated list and the postfix after the string. No spaces
	 * or any other separators are inserted after the prefix and before the
	 * postfix.
	 * <p>
	 * Note that just like in case of {@link #appendList(List)} if the argument
	 * {@code objects} is null then nothing is appended.
	 * 
	 * @param prefix
	 * @param objects
	 * @param postfix
	 * @return
	 */
	CodeStringBuilder appendList(String prefix, List<?> objects, String postfix) {
		if (containsSomething(objects)) {
			append(prefix);
			appendList(objects);
			append(postfix);
		}
		return this;
	}

	CodeStringBuilder appendLines(List<String> strings) {
		if (containsSomething(strings)) {
			for (String string : strings) {
				append(string).nl();
			}
		}
		return this;
	}

	/**
	 * Appends a list of strings to the source code string. The list will become
	 * a single concatenated string, the elements are separated by comma and a
	 * space. For example the list <tt>[ "a", "b", "c" ]</tt> will become
	 * <tt>"a, b, c"</tt>.
	 * 
	 * @param objects
	 *            if the argument is null, then nothing is appended
	 * @return
	 */
	CodeStringBuilder appendList(List<?> objects) {
		startList();
		if (containsSomething(objects)) {
			for (Object object : objects) {
				separator();
				append(object.toString());
			}
		}
		return this;
	}

	private static boolean containsSomething(List<?> objects) {
		return objects != null && objects.size() > 0;
	}

	public String toString() {
		return sb.toString();
	}

}
