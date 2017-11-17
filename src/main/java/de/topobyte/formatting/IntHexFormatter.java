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

public class IntHexFormatter implements IIntFormatter
{

	private Case c = Case.Lowercase;
	private boolean printRadixIndicator = false;

	private int minWidth = 1;
	private char padChar = ' ';
	private boolean padBeforeRadixIndicator = true;

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
		String hex = IntegerFormatting.intToHexString(n, c);
		int len = hex.length();
		if (printRadixIndicator) {
			len += 2;
		}
		if (padBeforeRadixIndicator && len < minWidth) {
			pad(buffer, len);
		}
		if (printRadixIndicator) {
			buffer.append(c == Case.Uppercase ? "0X" : "0x");
		}
		if (!padBeforeRadixIndicator && len < minWidth) {
			pad(buffer, len);
		}
		buffer.append(hex);
	}

	private void pad(StringBuilder buffer, int len)
	{
		int rem = minWidth - len;
		for (int i = 0; i < rem; i++) {
			buffer.append(padChar);
		}
	}

	public Case getCase()
	{
		return c;
	}

	public void setCase(Case c)
	{
		this.c = c;
	}

	public boolean isPrintRadixIndicator()
	{
		return printRadixIndicator;
	}

	public void setPrintRadixIndicator(boolean printRadixIndicator)
	{
		this.printRadixIndicator = printRadixIndicator;
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

	public boolean isPadBeforeRadixIndicator()
	{
		return padBeforeRadixIndicator;
	}

	public void setPadBeforeRadixIndicator(boolean padBeforeRadixIndicator)
	{
		this.padBeforeRadixIndicator = padBeforeRadixIndicator;
	}

}
