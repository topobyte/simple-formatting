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
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestDoubleFormatter
{

	final static Logger logger = LoggerFactory
			.getLogger(TestDoubleFormatter.class);

	@Parameters(name = "{index}: format({0}, {1})")
	public static Collection<Object[]> data()
	{
		List<Object[]> data = new ArrayList<>();
		add(data, 0, 0, 5);
		add(data, 1, 0, 5);
		add(data, -1, 0, 5);
		add(data, Double.NaN, 0, 5);
		add(data, Double.NEGATIVE_INFINITY, 0, 5);
		add(data, Double.POSITIVE_INFINITY, 0, 5);
		add(data);
		return data;
	}

	private static void add(List<Object[]> data)
	{
		add(data, 1.2345678901234, 0, 13);
		add(data, 1.2, 0, 13);
		add(data, 12.345678901234, 0, 13);
		add(data, 123.45678901234, 0, 13);
		add(data, 1234.5678901234, 0, 13);
		add(data, 12345.678901234, 0, 13);
		add(data, 123456.78901234, 0, 13);
		add(data, 1234567.8901234, 0, 13);
		add(data, 12345678.901234, 0, 13);
		add(data, 123456789.01234, 0, 13);
		add(data, 1234567890.1234, 0, 13);
		add(data, 12345678901.234, 0, 13);
		add(data, 123456789012.34, 0, 13);
		add(data, 1234567890123.4, 0, 13);

		add(data, 12345678.901234, 0, 13);
		add(data, 12345678.9012345, 0, 13);
		add(data, 12345678.90123456, 0, 13);
		add(data, 12345678.901234567, 0, 13);
		add(data, 12345678.9012345678, 0, 13);
		add(data, 12345678.90123456789, 0, 13);
	}

	private static void add(List<Object[]> data, double number,
			int minFractionDigits, int maxFractionDigits)
	{
		for (int f = minFractionDigits; f <= maxFractionDigits; f++) {
			add(data, number, f);
			add(data, -number, f);
		}
	}

	private static void add(List<Object[]> data, double number, int fraction)
	{
		data.add(new Object[] { number, fraction });
	}

	private double number;
	private int fractionDigits;

	public TestDoubleFormatter(double number, int fractionDigits)
	{
		this.number = number;
		this.fractionDigits = fractionDigits;
	}

	@Test
	public void formatAndCheck()
	{
		DoubleFormatter formatter = new DoubleFormatter();
		formatter.setFractionDigits(fractionDigits);
		String ours = formatter.format(number);
		String theirs = String.format(String.format("%%.%df", fractionDigits),
				number);
		logger.debug("ours:   '" + ours + "'");
		logger.debug("theirs: '" + theirs + "'");
		Assert.assertEquals(theirs, ours);
	}

}
