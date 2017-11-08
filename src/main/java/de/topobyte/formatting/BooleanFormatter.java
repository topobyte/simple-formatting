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

public class BooleanFormatter implements IBooleanFormatter
{

	@Override
	public Type getType()
	{
		return Type.BOOLEAN;
	}

	@Override
	public String format(boolean b)
	{
		StringBuilder buffer = new StringBuilder();
		format(buffer, b);
		return buffer.toString();
	}

	@Override
	public void format(StringBuilder buffer, boolean b)
	{
		buffer.append(b);
	}

}
