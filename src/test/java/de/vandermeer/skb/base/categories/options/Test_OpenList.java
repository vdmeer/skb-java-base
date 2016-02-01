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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.vandermeer.skb.base.categories.kvt.IsKey;
import de.vandermeer.skb.base.categories.kvt.IsType;
import de.vandermeer.skb.base.categories.kvt.IsValue;

/**
 * Tests for {@link OptionList}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_OpenList {

	@Test public void testConstructor(){
		OptionList ol = new OptionList();
		assertNotNull(ol.options);
		assertEquals(0, ol.options.size());

		ol = new OptionList(TestOptions.OPT1_STRING);
		assertNotNull(ol.options);
		assertEquals(1, ol.options.size());

		ol = new OptionList(TestOptions.values());
		assertNotNull(ol.options);
		assertEquals(5, ol.options.size());

		List<Option<?>> list = new ArrayList<>();
		list.add(Test_OpenList.OPTION(false));
		list.add(Test_OpenList.OPTION('#'));
		ol = new OptionList(list);
		assertNotNull(ol.options);
		assertEquals(2, ol.options.size());
	}

	@Test public void testHasOption(){
		OptionList ol = new OptionList();

		assertFalse(ol.hasOption(""));
		assertFalse(ol.hasOption((String)null));

		assertFalse(ol.hasOption(TestOptions.OPT1_STRING));
		assertFalse(ol.hasOption((Option<?>)null));

		ol.addOption(TestOptions.values());
		ol.addOption(Test_OpenList.OPTION(false));
		ol.addOption(Test_OpenList.OPTION('#'));
		ol.addOption(Test_OpenList.OPTION("string string"));
		ol.addOption(Test_OpenList.OPTION(2013));
		ol.addOption(Test_OpenList.OPTION(3.1415));

		assertTrue(ol.hasOption(TestOptions.OPT1_STRING));
		assertTrue(ol.hasOption(TestOptions.OPT1_STRING.name()));
		assertTrue(ol.hasOption(TestOptions.OPT1_STRING.getKey()._value()));

		assertTrue(ol.hasOption("CHAR_OPTION"));
		assertTrue(ol.hasOption("STRING_OPTION"));
		assertTrue(ol.hasOption("DOUBLE_OPTION"));
		assertTrue(ol.hasOption("BOOLEAN_OPTION"));
		assertTrue(ol.hasOption("INT_OPTION"));
	}

	@Test public void testGetOption(){
		OptionList ol = new OptionList();

		assertNull(ol.getOption(""));
		assertNull(ol.getOption((String)null));

		ol.addOption(TestOptions.values());
		ol.addOption(Test_OpenList.OPTION(false));
		ol.addOption(Test_OpenList.OPTION('#'));
		ol.addOption(Test_OpenList.OPTION("string string"));
		ol.addOption(Test_OpenList.OPTION(2013));
		ol.addOption(Test_OpenList.OPTION(3.1415));

		assertEquals(TestOptions.OPT1_STRING, ol.getOption(TestOptions.OPT1_STRING.name()));
		assertEquals(TestOptions.OPT1_STRING, ol.getOption(TestOptions.OPT1_STRING.getKey()._value()));

		assertEquals("CHAR_OPTION", ol.getOption("CHAR_OPTION").getKey()._value());
		assertEquals("STRING_OPTION", ol.getOption("STRING_OPTION").getKey()._value());
		assertEquals("DOUBLE_OPTION", ol.getOption("DOUBLE_OPTION").getKey()._value());
		assertEquals("BOOLEAN_OPTION", ol.getOption("BOOLEAN_OPTION").getKey()._value());
		assertEquals("INT_OPTION", ol.getOption("INT_OPTION").getKey()._value());
	}

	@Test public void testGetOptValue(){
		OptionList ol = new OptionList(TestOptions.values());
		ol.addOption(TestOptions.values());
		ol.addOption(Test_OpenList.OPTION(false));
		ol.addOption(Test_OpenList.OPTION('#'));
		ol.addOption(Test_OpenList.OPTION("string string"));
		ol.addOption(Test_OpenList.OPTION(2013));
		ol.addOption(Test_OpenList.OPTION(3.1415));

		assertEquals(new Boolean(false), ol.getOptValue("BOOLEAN_OPTION", true));
		assertEquals(new Boolean(true), ol.getOptValue("INT_OPTION", true));

		assertEquals(new Integer(2013), ol.getOptValue("INT_OPTION", 0));
		assertEquals(new Integer(0), ol.getOptValue("BOOLEAN_OPTION", 0));
		assertEquals(new Integer(-10), ol.getOptValue("STRING_OPTION", -10));

		assertEquals(new String("string string"), ol.getOptValue("STRING_OPTION", (String)null));
		assertEquals(null, ol.getOptValue("BOOLEAN_OPTION", (String)null));
		assertEquals(new String(""), ol.getOptValue("CHAR_OPTION", ""));
	
		assertEquals(new Double(3.1415), ol.getOptValue("DOUBLE_OPTION", 0.0));
		assertEquals(new Double(0.0), ol.getOptValue("BOOLEAN_OPTION", 0.0));
		assertEquals(new Double(9.9), ol.getOptValue("STRING_OPTION", 9.9));

		assertEquals(new Character('#'), ol.getOptValue("CHAR_OPTION", ' '));
		assertEquals(new Character(' '), ol.getOptValue("BOOLEAN_OPTION", ' '));
		assertEquals(new Character('@'), ol.getOptValue("STRING_OPTION", '@'));
	}

	public enum TestOptions implements Option<String> {
		OPT1_STRING			("opt one"),
		OPT2_INT			("opt two"),
		OPT3_CHAR			("opt three"),
		OPT4_BOOL			("opt four"),
		OPT5_DOUBLE			("opt five"),
		;
		Option<String> option;
		TestOptions(String description){this.option=Option.create(this.name(), this.name(), description);}
		@Override public IsKey<String> getKey() {return option.getKey();}
		@Override public IsValue<String> getValue() {return option.getValue();}
		@Override public Object getDescription() {return this.option.getDescription();}
		@Override public IsType<String> getValueType() {return this.option.getValueType();}
	}

	public static Option<Character> OPTION(char value){
		return Option.create("CHAR_OPTION", value, "a character option");
	}

	public static Option<Integer> OPTION(int value){
		return Option.create("INT_OPTION", value, "an integer option");
	}

	public static Option<String> OPTION(String value){
		return Option.create("STRING_OPTION", value, "a string option");
	}

	public static Option<Double> OPTION(Double value){
		return Option.create("DOUBLE_OPTION", value, "a double option");
	}

	public static Option<Boolean> OPTION(Boolean value){
		return Option.create("BOOLEAN_OPTION", value, "a boolean option");
	}

}
