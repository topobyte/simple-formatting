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
import java.util.List;

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

	private void parseFormatter()
	{
		logger.debug("parse formatter at: " + pos);
		pos++;
		while (pos < numChars) {
			char c = format.charAt(pos++);
			logger.debug("char: " + c);
			if (c == 's') {
				formatters.add(new StringFormatter());
				break;
			} else if (c == 'b') {
				formatters.add(new BooleanFormatter());
				break;
			} else if (c == 'd') {
				formatters.add(new LongFormatter());
				break;
			} else if (c == 'f') {
				DoubleFormatter formatter = new DoubleFormatter();
				formatter.setFractionDigits(6);
				formatters.add(formatter);
				break;
			} else if (c == 'x') {
				LongHexFormatter formatter = new LongHexFormatter();
				formatter.setCase(Case.Lowercase);
				formatters.add(formatter);
				break;
			} else if (c == 'X') {
				LongHexFormatter formatter = new LongHexFormatter();
				formatter.setCase(Case.Uppercase);
				formatters.add(formatter);
				break;
			} else {
				throw new IllegalArgumentException("Unable to parse format");
			}
		}
	}

}
