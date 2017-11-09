// Copyright 2017 Sebastian Kuerten
//
// This file is part of simple-formatting.
//
// simple-formatting is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// simple-formatting is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with simple-formatting. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.formatting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatParser
{

	final static Logger logger = LoggerFactory.getLogger(FormatParser.class);

	public static List<IFormatter> parse(String format)
	{
		FormatParser parser = new FormatParser(format);
		parser.parse();
		return parser.formatters;
	}

	private String format;

	private FormatParser(String format)
	{
		this.format = format;
	}

	private List<IFormatter> formatters = new ArrayList<>();

	private int pos = 0;
	private int numChars = 0;

	private void parse()
	{
		numChars = format.length();

		StringBuilder accu = new StringBuilder();

		while (pos < numChars) {
			char c = format.charAt(pos);
			if (c == '%') {
				appendAccu(accu);
				parseFormatter();
				continue;
			} else {
				accu.append(c);
			}
			pos++;
		}
		appendAccu(accu);
	}

	private void appendAccu(StringBuilder accu)
	{
		if (accu.length() == 0) {
			return;
		}
		String current = accu.toString();
		logger.debug("literal: \"" + current + "\"");
		formatters.add(new LiteralFormatter(current));
		accu.setLength(0);
	}

	// See this reference:
	// https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html
	//
	// This is the format for conversions:
	// %[argument_index$][flags][width][.precision]conversion

	private char[] flags = new char[] { '0', '#', '+', '(', ',', ' ' };
	private char[] conversions = new char[] { 's', 'd', 'f', 'b', 'x', 'X', '%',
			'n' };

	// TODO: use set with primitives here
	private Set<Character> flagLookup = new HashSet<>();
	{
		for (char c : flags) {
			flagLookup.add(c);
		}
	}

	// TODO: use set with primitives here
	private Set<Character> optionLookup = new HashSet<>();
	{
		for (char c : flags) {
			optionLookup.add(c);
		}
		for (char c = '0'; c <= '9'; c++) {
			optionLookup.add(c);
		}
		optionLookup.add('.');
	}

	// TODO: use set with primitives here
	private Set<Character> conversionLookup = new HashSet<>();
	{
		for (char c : conversions) {
			conversionLookup.add(c);
		}
	}

	private boolean isOption(char c)
	{
		return optionLookup.contains(c);
	}

	private boolean isFlag(char c)
	{
		return flagLookup.contains(c);
	}

	private boolean isConversion(char c)
	{
		return conversionLookup.contains(c);
	}

	private void parseFormatter()
	{
		logger.debug("parse formatter at: " + pos);
		pos++;
		StringBuilder options = new StringBuilder();
		while (pos < numChars) {
			char c = format.charAt(pos++);
			logger.debug("char: " + c);
			if (isConversion(c)) {
				conversion(c, options);
				break;
			} else if (isOption(c)) {
				options.append(c);
			}
		}
	}

	private void conversion(char c, StringBuilder options)
	{
		if (c == 's') {
			formatters.add(new StringFormatter());
		} else if (c == '%') {
			formatters.add(new LiteralFormatter("%"));
		} else if (c == 'n') {
			// TODO: this is not the _platform-specific_ line separator
			formatters.add(new LiteralFormatter("\n"));
		} else if (c == 'b') {
			formatters.add(new BooleanFormatter());
		} else if (c == 'd') {
			formatters.add(new LongFormatter());
		} else if (c == 'f') {
			PrecisionResult precision = precision(options);
			DoubleFormatter formatter = new DoubleFormatter();
			if (precision.isPresent) {
				formatter.setFractionDigits(precision.precision);
			}
			formatters.add(formatter);
		} else if (c == 'x') {
			LongHexFormatter formatter = new LongHexFormatter();
			if (hasFlag(options, '#')) {
				formatter.setPrintRadixIndicator(true);
			}
			formatter.setCase(Case.Lowercase);
			formatters.add(formatter);
		} else if (c == 'X') {
			LongHexFormatter formatter = new LongHexFormatter();
			if (hasFlag(options, '#')) {
				formatter.setPrintRadixIndicator(true);
			}
			formatter.setCase(Case.Uppercase);
			formatters.add(formatter);
		} else {
			throw new IllegalArgumentException("Unable to parse format");
		}
	}

	private boolean hasFlag(StringBuilder options, char c)
	{
		for (int i = 0; i < options.length(); i++) {
			if (options.charAt(i) == c) {
				return true;
			}
		}
		return false;
	}

	private class PrecisionResult
	{

		boolean isPresent = false;
		int precision = 0;

	}

	private PrecisionResult precision(StringBuilder options)
	{
		PrecisionResult result = new PrecisionResult();

		int dot = -1;
		for (int i = 0; i < options.length(); i++) {
			if (options.charAt(i) == '.') {
				dot = i;
				break;
			}
		}
		if (dot < 0) {
			return result;
		}

		int precision = 0;
		for (int i = dot + 1; i < options.length(); i++) {
			char c = options.charAt(i);
			if (!Character.isDigit(c)) {
				throw new IllegalArgumentException(
						"invalid precision character: '" + c + "'");
			}
			precision *= 10;
			precision += c - '0';
		}

		result.isPresent = true;
		result.precision = precision;
		return result;
	}

}
