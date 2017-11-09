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

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFormatParserCompare
{

	final static Logger logger = LoggerFactory
			.getLogger(TestFormatParserCompare.class);

	private void formatAndCheck(String format, Object... args)
	{
		String ours = Formatting.format(format, args);
		String theirs = String.format(format, args);
		logger.debug("ours:   " + ours);
		logger.debug("theirs: " + theirs);
		Assert.assertEquals(theirs, ours);
	}

	@Test
	public void test()
	{
		formatAndCheck("foo: %d, bar: %s", 34, "yeah");
		formatAndCheck("foo: %f, bar: %s", -12.345, "yeah");
		formatAndCheck("foo: %b, bar: %b", true, false);
		formatAndCheck("foo: %x, bar: %#x", 45, 46);
		formatAndCheck("foo: %X, bar: %#X", 45, 46);
	}

}
