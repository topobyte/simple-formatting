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

public class Formatter
{

	private List<IFormatter> formatters = new ArrayList<>();

	public Formatter(List<IFormatter> formatters)
	{
		this.formatters = formatters;
	}

	public String format(Object... args)
	{
		StringBuilder buffer = new StringBuilder();
		format(buffer, args);
		return buffer.toString();
	}

	public void format(StringBuilder buffer, Object... args)
	{
		int idx = 0;

		for (IFormatter formatter : formatters) {
			Type type = formatter.getType();
			switch (type) {
			default:
				// do nothing
				break;
			case NONE: {
				IVoidFormatter f = (IVoidFormatter) formatter;
				f.format(buffer);
				break;
			}
			case STRING: {
				IStringFormatter f = (IStringFormatter) formatter;
				f.format(buffer, (String) args[idx++]);
				break;
			}
			case INT: {
				IIntFormatter f = (IIntFormatter) formatter;
				Object arg = args[idx++];
				if (arg instanceof Integer) {
					f.format(buffer, (int) arg);
				} else if (arg instanceof Short) {
					f.format(buffer, (short) arg);
				} else if (arg instanceof Byte) {
					f.format(buffer, (byte) arg);
				}
				break;
			}
			case LONG: {
				ILongFormatter f = (ILongFormatter) formatter;
				Object arg = args[idx++];
				if (arg instanceof Long) {
					f.format(buffer, (long) arg);
				} else if (arg instanceof Integer) {
					f.format(buffer, (int) arg);
				} else if (arg instanceof Short) {
					f.format(buffer, (short) arg);
				} else if (arg instanceof Byte) {
					f.format(buffer, (byte) arg);
				}
				break;
			}
			case DOUBLE: {
				IDoubleFormatter f = (IDoubleFormatter) formatter;
				f.format(buffer, (double) args[idx++]);
				break;
			}
			}
		}
	}

}
