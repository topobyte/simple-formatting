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

public class Formatting
{

	public static String format(String format, Object... args)
	{
		StringBuilder buffer = new StringBuilder();
		formatBuilder(buffer, format, args);
		return buffer.toString();
	}

	public static void formatBuilder(StringBuilder buffer, String format,
			Object... args)
	{
		List<IFormatter> formatters = new ArrayList<>();
		// TODO: parse format and add elements to formatters list
		Formatter formatter = new Formatter(formatters);
		formatter.formatBuilder(buffer, args);
	}

}
