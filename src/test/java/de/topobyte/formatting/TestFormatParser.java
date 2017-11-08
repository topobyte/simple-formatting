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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFormatParser
{

	final static Logger logger = LoggerFactory
			.getLogger(TestFormatParser.class);

	@Test
	public void test1()
	{
		List<IFormatter> formatters = FormatParser.parse("foo: %d, bar: %s");
		for (IFormatter formatter : formatters) {
			logger.debug(formatter.getClass().getSimpleName());
		}
	}

	@Test
	public void test2()
	{
		String result = Formatting.format("foo: %d, bar: %s", 34, "yeah");
		logger.debug("result: " + result);
		Assert.assertEquals("foo: 34, bar: yeah", result);
	}

	@Test
	public void test3()
	{
		String result = Formatting.format("foo: %f, bar: %s", -12.345, "yeah");
		logger.debug("result: " + result);
		Assert.assertEquals("foo: -12.345000, bar: yeah", result);
	}

}
