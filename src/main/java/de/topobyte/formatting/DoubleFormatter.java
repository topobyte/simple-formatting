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

public class DoubleFormatter implements IDoubleFormatter
{

	final static Logger logger = LoggerFactory.getLogger(DoubleFormatter.class);

	private int minWidth = 1;
	private int fractionDigits = 6;
	private char padChar = ' ';
	private boolean padBeforeMinus = true;

	@Override
	public Type getType()
	{
		return Type.DOUBLE;
	}

	@Override
	public String format(double d)
	{
		StringBuilder buffer = new StringBuilder();
		format(buffer, d);
		return buffer.toString();
	}

	@Override
	public void format(StringBuilder buffer, double d)
	{
		if (Double.isNaN(d)) {
			pad(buffer, minWidth - 3, ' ');
			buffer.append("NaN");
			return;
		} else if (d == Double.POSITIVE_INFINITY) {
			pad(buffer, minWidth - 8, ' ');
			buffer.append("Infinity");
			return;
		} else if (d == Double.NEGATIVE_INFINITY) {
			pad(buffer, minWidth - 9, ' ');
			buffer.append("-Infinity");
			return;
		}

		long integral = (long) d;

		boolean negative = d < 0;

		if (fractionDigits == 0) {
			if (negative) {
				double fraction = -d + integral;
				if (fraction >= 0.5) {
					integral -= 1;
				}
			} else {
				double fraction = d - integral;
				if (fraction >= 0.5) {
					integral += 1;
				}
			}
		}

		int lenIntegral;
		if (!negative) {
			String integralPart = Long.toString(integral);
			lenIntegral = integralPart.length();
			int used = lenIntegral + fractionDigits;
			if (fractionDigits > 0) {
				used += 1;
			}
			pad(buffer, minWidth - used, padChar);
			buffer.append(integralPart);
		} else {
			String integralPart = Long.toString(-integral);
			lenIntegral = integralPart.length();
			int used = lenIntegral + fractionDigits;
			used += 1; // account for minus sign
			if (fractionDigits > 0) {
				used += 1;
			}
			if (padBeforeMinus) {
				pad(buffer, minWidth - used, padChar);
			}
			buffer.append('-');
			if (!padBeforeMinus) {
				pad(buffer, minWidth - used, padChar);
			}
			buffer.append(integralPart);
		}
		int usedDigits = lenIntegral;

		if (fractionDigits > 0) {
			buffer.append(".");
			formatFractionDigits(buffer, d, usedDigits);
		}
	}

	private void pad(StringBuilder buffer, int n, char c)
	{
		for (int i = 0; i < n; i++) {
			buffer.append(c);
		}
	}

	private void formatFractionDigits(StringBuilder buffer, double d,
			int usedDigits)
	{
		int maxDigits = 16 - usedDigits;
		int validDigits = fractionDigits;
		int additionalDigits = 0;
		if (maxDigits < validDigits) {
			additionalDigits = validDigits - maxDigits;
			validDigits = maxDigits;
		}

		double number = d;
		long nintegral = (long) number;

		// TODO: use a list of primitive ints here
		List<Integer> ints = new ArrayList<>();

		logger.debug("format: " + d + " with " + fractionDigits + " digits");
		logger.debug(number + " " + nintegral);
		logger.debug(
				"used digits: " + usedDigits + " valid digits: " + validDigits);
		double x = (number - nintegral) * 10;
		if (x < 0) {
			x = -x;
		}
		for (int i = 0; i < validDigits; i++) {
			int integral = (int) (x);

			logger.debug(x + " " + integral);
			ints.add(integral);
			number = x;
			x = (number - integral) * 10;
		}

		logger.debug("digits: " + ints);
		logger.debug("remainder: " + x);
		if (x >= 5) {
			logger.debug("rounding up");
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

		for (int i = 0; i < additionalDigits; i++) {
			ints.add(0);
		}

		for (int i : ints) {
			buffer.append(i);
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

	public int getFractionDigits()
	{
		return fractionDigits;
	}

	public void setFractionDigits(int fractionDigits)
	{
		this.fractionDigits = fractionDigits;
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
