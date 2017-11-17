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
public class TestLongHexFormatterPadding
{

	final static Logger logger = LoggerFactory
			.getLogger(TestLongHexFormatterPadding.class);

	@Parameters(name = "{index}: format({0}, {1})")
	public static Collection<Object[]> data()
	{
		List<Object[]> data = new ArrayList<>();
		add(data);
		return data;
	}

	private static void add(List<Object[]> data)
	{
		add(data, 0);
		add(data, 1);
		add(data, 12);
		add(data, 123);
		add(data, 1234);
		add(data, 123456);
		add(data, 1234567);
		add(data, 12345678);
		add(data, 123456789);
		add(data, 1234567890);
		add(data, 12345678901L);
		add(data, 123456789012L);
		add(data, 1234567890123L);
		add(data, 12345678901234L);
		add(data, 123456789012345L);
		add(data, 1234567890123456L);
		add(data, 12345678901234567L);
		add(data, 123456789012345678L);
		add(data, 1234567890123456789L);
		add(data, Integer.MAX_VALUE);
		add(data, Integer.MIN_VALUE);
		add(data, Long.MAX_VALUE);
		add(data, Long.MIN_VALUE);
	}

	private static void add(List<Object[]> data, long number)
	{
		add(data, number, 1, 24);
	}

	private static void add(List<Object[]> data, long number, int minWidth,
			int maxWidth)
	{
		for (int width = minWidth; width <= maxWidth; width++) {
			data.add(new Object[] { number, width });
			data.add(new Object[] { -number, width });
		}
	}

	private long number;
	private int width;

	public TestLongHexFormatterPadding(long number, int width)
	{
		this.number = number;
		this.width = width;
	}

	@Test
	public void formatAndCheck()
	{
		LongHexFormatter formatter = new LongHexFormatter();
		formatter.setMinWidth(width);

		formatter.setPrintRadixIndicator(false);
		String ours = formatter.format(number);

		formatter.setPrintRadixIndicator(true);
		String oursRadix = formatter.format(number);

		formatter.setPrintRadixIndicator(false);
		formatter.setPadChar('0');
		formatter.setPadBeforeRadixIndicator(false);
		String ours0 = formatter.format(number);

		formatter.setPrintRadixIndicator(true);
		String oursRadix0 = formatter.format(number);

		String theirs = String.format(String.format("%%%dx", width), number);
		String theirs0 = String.format(String.format("%%0%dx", width), number);
		String theirsRadix = String.format(String.format("%%#%dx", width),
				number);
		String theirsRadix0 = String.format(String.format("%%#0%dx", width),
				number);
		logger.debug("ours:           '" + ours + "'");
		logger.debug("theirs:         '" + theirs + "'");
		logger.debug("ours0:          '" + ours0 + "'");
		logger.debug("theirs0:        '" + theirs0 + "'");
		logger.debug("ours radix:     '" + oursRadix + "'");
		logger.debug("theirs radix:   '" + theirsRadix + "'");
		logger.debug("ours radix 0:   '" + oursRadix0 + "'");
		logger.debug("theirs radix 0: '" + theirsRadix0 + "'");
		Assert.assertEquals(theirs, ours);
		Assert.assertEquals(theirs0, ours0);
		Assert.assertEquals(theirsRadix, oursRadix);
		Assert.assertEquals(theirsRadix0, oursRadix0);
	}

}
