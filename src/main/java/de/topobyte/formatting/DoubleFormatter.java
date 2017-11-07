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

public class DoubleFormatter
{

	final static Logger logger = LoggerFactory.getLogger(DoubleFormatter.class);

	private int fractionDigits = 6;

	public String format(double d)
	{
		StringBuilder buffer = new StringBuilder();
		format(buffer, d);
		return buffer.toString();
	}

	public void format(StringBuilder buffer, double d)
	{
		long integral = (long) d;

		buffer.append(integral);

		if (fractionDigits > 0) {
			buffer.append(".");
			formatFractionDigits(buffer, d);
		}
	}

	private void formatFractionDigits(StringBuilder buffer, double d)
	{
		double number = d;
		long nintegral = (long) number;

		List<Integer> ints = new ArrayList<>();

		logger.debug("format: " + d + " with " + fractionDigits + " digits");
		logger.debug(number + " " + nintegral);
		double x = (number - nintegral) * 10;
		for (int i = 0; i < fractionDigits; i++) {
			int integral = (int) (x);

			logger.debug(String.format("%.30f %d", x, integral));
			ints.add(integral);
			number = x;
			x = (number - integral) * 10;
		}

		if (x > 5) {
			for (int i = ints.size() - 1; i >= 0; i--) {
				int oldI = ints.get(i);
				int newI = oldI + 1;
				if (newI < 10) {
					ints.set(i, newI);
					break;
				} else {
					ints.set(i, 0);
				}
			}
		}

		for (Integer i : ints) {
			buffer.append(i);
		}
	}

	public int getFractionDigits()
	{
		return fractionDigits;
	}

	public void setFractionDigits(int fractionDigits)
	{
		this.fractionDigits = fractionDigits;
	}

}
