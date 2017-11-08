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

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFormatter
{

	final static Logger logger = LoggerFactory.getLogger(TestFormatter.class);

	@Test
	public void test()
	{
		List<IFormatter> formatters = new ArrayList<>();
		formatters.add(new LiteralFormatter("foo: "));
		formatters.add(new StringFormatter());
		formatters.add(new LiteralFormatter(", bar: "));
		formatters.add(new DoubleFormatter());
		formatters.add(new LiteralFormatter(", cat: "));
		formatters.add(new LongFormatter());
		formatters.add(new LiteralFormatter(", really? "));
		formatters.add(new BooleanFormatter());

		Formatter formatter = new Formatter(formatters);

		String output = formatter.format("test", 1.2, 13, true);

		logger.debug("output: " + output);
		Assert.assertEquals("foo: test, bar: 1.200000, cat: 13, really? true",
				output);
	}

}
