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
		buffer.append(hex);
	}

	public Case getCase()
	{
		return c;
	}

	public void setCase(Case c)
	{
		this.c = c;
	}

}
