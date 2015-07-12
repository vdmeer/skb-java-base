package de.vandermeer.skb.base.categories.options;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.vandermeer.skb.base.categories.options.Option;

public class Test_Option {

	@Test public void testString(){
		Option<String> opt=Option.create("option1", "TEST", "test description");
		assertEquals("option1", opt.getKey()._value());
		assertEquals("TEST", opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, TEST, test description]", opt.toString());

		opt=Option.create("option2", "TEST2", "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals("TEST2", opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, TEST2, test description2]", opt.toString());
	}

	@Test public void testChar(){
		Option<Character> opt=Option.create("option1", '#', "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Character('#'), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, #, test description]", opt.toString());

		opt=Option.create("option2", '@', "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Character('@'), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, @, test description2]", opt.toString());
	}

	@Test public void testInteger(){
		Option<Integer> opt=Option.create("option1", 99, "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Integer(99), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, 99, test description]", opt.toString());

		opt=Option.create("option2", -99, "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Integer(-99), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, -99, test description2]", opt.toString());
	}

	@Test public void testDouble(){
		Option<Double> opt=Option.create("option1", 3.1415, "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Double(3.1415), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, 3.1415, test description]", opt.toString());

		opt=Option.create("option2", -3.1415, "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Double(-3.1415), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, -3.1415, test description2]", opt.toString());
	}

	@Test public void testBoolean(){
		Option<Boolean> opt=Option.create("option1", true, "test description");
		assertEquals("option1", opt.getKey().key());
		assertEquals(new Boolean(true), opt.getValue()._value());
		assertEquals("test description", opt.getDescription());
		assertEquals("Option[option1, true, test description]", opt.toString());

		opt=Option.create("option2", false, "test description2");
		assertEquals("option2", opt.getKey().key());
		assertEquals(new Boolean(false), opt.getValue()._value());
		assertEquals("test description2", opt.getDescription());
		assertEquals("Option[option2, false, test description2]", opt.toString());
	}
}
