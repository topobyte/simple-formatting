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

public class IntFormatter implements IIntFormatter
{

	private int minWidth = 1;
	private char padChar = ' ';
	private boolean padBeforeMinus = true;

	@Override
	public Type getType()
	{
		return Type.INT;
	}

	@Override
	public String format(int n)
	{
		StringBuilder buffer = new StringBuilder();
		format(buffer, n);
		return buffer.toString();
	}

	@Override
	public void format(StringBuilder buffer, int n)
	{
		if (padBeforeMinus) {
			formatPadBeforeMinus(buffer, n);
		} else {
			formatPadAfterMinus(buffer, n);
		}
	}

	private void formatPadBeforeMinus(StringBuilder buffer, int n)
	{
		String s = Integer.toString(n);
		int len = s.length();
		if (len < minWidth) {
			int rem = minWidth - len;
			for (int i = 0; i < rem; i++) {
				buffer.append(padChar);
			}
		}
		buffer.append(s);
	}

	private void formatPadAfterMinus(StringBuilder buffer, int n)
	{
		boolean negative = n < 0;
		String s = Integer.toString(n);
		if (negative) {
			buffer.append('-');
		}
		int len = s.length();
		if (len < minWidth) {
			int rem = minWidth - len;
			for (int i = 0; i < rem; i++) {
				buffer.append(padChar);
			}
		}
		if (negative) {
			buffer.append(s, 1, len);
		} else {
			buffer.append(s);
		}
	}

	public int getMinWidth()
	{
		return minWidth;
	}

	public void setMinWidth(int minWidth)
	{
		this.minWidth = minWidth;
	}

	public char getPadChar()
	{
		return padChar;
	}

	public void setPadChar(char padChar)
	{
		this.padChar = padChar;
	}

	public boolean isPadBeforeMinus()
	{
		return padBeforeMinus;
	}

	public void setPadBeforeMinus(boolean padBeforeMinus)
	{
		this.padBeforeMinus = padBeforeMinus;
	}

}
