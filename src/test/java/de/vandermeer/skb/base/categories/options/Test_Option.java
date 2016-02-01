/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.skb.base.categories.options;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for {@link Option}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
<<<<<<< HEAD
 * @version    v0.1.8 build 160201 (01-Feb-16) for Java 1.8
=======
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
>>>>>>> dev
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_Option {

	@Test public void testString(){
		Option<String> opt = Option.create("option1", "TEST", "test description");
		assertEquals("option1", opt.getKey()._value());
		assertEquals("TEST", opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, TEST, test description]", opt.toString());

		opt = Option.create("option2", "TEST2", "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals("TEST2", opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, TEST2, test description2]", opt.toString());
	}

	@Test public void testChar(){
		Option<Character> opt = Option.create("option1", '#', "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Character('#'), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, #, test description]", opt.toString());

		opt = Option.create("option2", '@', "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Character('@'), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, @, test description2]", opt.toString());
	}

	@Test public void testInteger(){
		Option<Integer> opt = Option.create("option1", 99, "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Integer(99), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, 99, test description]", opt.toString());

		opt = Option.create("option2", -99, "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Integer(-99), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, -99, test description2]", opt.toString());
	}

	@Test public void testDouble(){
		Option<Double> opt = Option.create("option1", 3.1415, "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Double(3.1415), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, 3.1415, test description]", opt.toString());

		opt = Option.create("option2", -3.1415, "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Double(-3.1415), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, -3.1415, test description2]", opt.toString());
	}

	@Test public void testBoolean(){
		Option<Boolean> opt = Option.create("option1", true, "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Boolean(true), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, true, test description]", opt.toString());

		opt = Option.create("option2", false, "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Boolean(false), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, false, test description2]", opt.toString());
	}

}
