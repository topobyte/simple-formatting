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

public class TestDoubleFormatter
{

	final static Logger logger = LoggerFactory
			.getLogger(TestDoubleFormatter.class);

	@Test
	public void test()
	{
		formatAndCheck(1.2345678901234, 0);
		formatAndCheck(1.2345678901234, 1);
		formatAndCheck(1.2345678901234, 13);

		formatAndCheck(1.2, 0);
		formatAndCheck(1.2, 1);
		formatAndCheck(1.2, 14);

		formatAndCheck(12.345678901234, 0);
		formatAndCheck(12.345678901234, 1);
		formatAndCheck(12.345678901234, 13);

		formatAndCheck(123.45678901234, 0);
		formatAndCheck(123.45678901234, 1);
		formatAndCheck(123.45678901234, 13);

		formatAndCheck(1234.5678901234, 0);
		formatAndCheck(1234.5678901234, 1);
		formatAndCheck(1234.5678901234, 13);

		formatAndCheck(123456.78901234, 0);
		formatAndCheck(123456.78901234, 1);
		formatAndCheck(123456.78901234, 13);

		formatAndCheck(123456.78901234, 0);
		formatAndCheck(123456.78901234, 1);
		formatAndCheck(123456.78901234, 13);
	}

	private void formatAndCheck(double number, int fractionDigits)
	{
		DoubleFormatter formatter = new DoubleFormatter();
		formatter.setFractionDigits(fractionDigits);
		String ours = formatter.format(number);
		String theirs = String.format(String.format("%%.%df", fractionDigits),
				number);
		logger.debug("ours:   " + ours);
		logger.debug("theirs: " + theirs);
		Assert.assertEquals(theirs, ours);
	}

}
