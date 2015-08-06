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

package de.vandermeer.skb.base.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for Tests for {@link Skb_ObjectUtils}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.11-SNAPSHOT build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-commons before)
 */
public class Test_Skb_ObjectUtils {

	private String nullValue = "<null>";

	private String falseValue = "<false>";

	@Test public void testConvert(){
		//String = ok, <null> (can't be <false>)
		assertEquals("test string", Skb_ObjectUtils.CONVERT("test string", String.class, nullValue, falseValue));
		assertEquals(nullValue, Skb_ObjectUtils.CONVERT(null, String.class, nullValue, falseValue));

		//try a set with null, then with a string, then with an object
		Set<Object>set = new HashSet<Object>();
		assertEquals(nullValue, Skb_ObjectUtils.CONVERT(set, String.class, nullValue, falseValue));
		set.add(null);
		assertEquals(nullValue, Skb_ObjectUtils.CONVERT(set, String.class, nullValue, falseValue));
		set.add("test string2");
		assertEquals("test string2", Skb_ObjectUtils.CONVERT(set, String.class, nullValue, falseValue));
		set.clear();
		set.add(0);//no string should result in false
		assertEquals(falseValue, Skb_ObjectUtils.CONVERT(set, String.class, nullValue, falseValue));
	}

	@Test public void testConvertObjectarr(){
		//test for nullValue
		assertNull(Skb_ObjectUtils.CONVERT(null, Object[].class, null, null));
		assertNotNull(Skb_ObjectUtils.CONVERT(null, Object[].class, new Object[]{}, null));
		assertTrue(Skb_ObjectUtils.CONVERT(null, Object[].class, new Object[]{}, null).length==0);

		//test for falseValue
		assertNull(Skb_ObjectUtils.CONVERT("", Object[].class, new Object[]{}, null));

		//test for valid object[]
		Object[] arr=new Object[]{null, "two", new Integer(100), '/'};
		assertEquals(arr.hashCode(), Skb_ObjectUtils.CONVERT(arr, Object[].class, null, null).hashCode());
	}

	@Test public void testConvertBoolean(){
		//test with nullValue returns
		assertNull(Skb_ObjectUtils.CONVERT(null, Boolean.class, null, null));
		assertFalse(Skb_ObjectUtils.CONVERT(null, Boolean.class, false, null));
		assertTrue(Skb_ObjectUtils.CONVERT(null, Boolean.class, true, null));

		//falseValue returns
		assertNull(Skb_ObjectUtils.CONVERT("", Boolean.class, false, null));
		assertFalse(Skb_ObjectUtils.CONVERT("", Boolean.class, true, false));
		assertTrue(Skb_ObjectUtils.CONVERT("", Boolean.class, false, true));

		//some good native values
		assertTrue(Skb_ObjectUtils.CONVERT(Boolean.TRUE, Boolean.class, null, null));
		assertFalse(Skb_ObjectUtils.CONVERT(Boolean.FALSE, Boolean.class, null, null));

		Set<Object>set = new HashSet<Object>();
		set.add(null);
		assertFalse(Skb_ObjectUtils.CONVERT(set, Boolean.class, false, true));
		set.add(Boolean.TRUE);
		assertEquals(Boolean.TRUE, Skb_ObjectUtils.CONVERT(set, Boolean.class, false, false));

		//some values that come from String (it uses BooleanUtils.toBooleanObject)
		assertTrue(Skb_ObjectUtils.CONVERT("true", Boolean.class, false, false));
		assertTrue(Skb_ObjectUtils.CONVERT("True", Boolean.class, false, false));
		assertTrue(Skb_ObjectUtils.CONVERT("on", Boolean.class, false, false));
		assertTrue(Skb_ObjectUtils.CONVERT("ON", Boolean.class, false, false));

		assertFalse(Skb_ObjectUtils.CONVERT("false", Boolean.class, true, true));
		assertFalse(Skb_ObjectUtils.CONVERT("False", Boolean.class, true, true));
		assertFalse(Skb_ObjectUtils.CONVERT("off", Boolean.class, true, true));
		assertFalse(Skb_ObjectUtils.CONVERT("Off", Boolean.class, true, true));

		assertFalse(Skb_ObjectUtils.CONVERT("bla", Boolean.class, true, false));
		assertFalse(Skb_ObjectUtils.CONVERT("foo", Boolean.class, true, false));
	}

	@Test public void testConvertInteger(){
		//test with nullValue returns
		assertNull(Skb_ObjectUtils.CONVERT(null, Integer.class, null, null));
		assertEquals(new Integer(-1), Skb_ObjectUtils.CONVERT(null, Integer.class, -1, null));
		assertEquals(new Integer(0), Skb_ObjectUtils.CONVERT(null, Integer.class, 0, null));

		//falseValue returns
		assertNull(Skb_ObjectUtils.CONVERT("", Integer.class, 0, null));
		assertEquals(new Integer(0), Skb_ObjectUtils.CONVERT("", Integer.class, -1, 0));
		assertEquals(new Integer(-1), Skb_ObjectUtils.CONVERT("", Integer.class, 0, -1));

		//some values that come from String
		assertEquals(new Integer(1), Skb_ObjectUtils.CONVERT("1", Integer.class, -1, -2));
		assertEquals(new Integer(10), Skb_ObjectUtils.CONVERT("10", Integer.class, -1, -2));

		assertEquals(new Integer(-2), Skb_ObjectUtils.CONVERT("xxx", Integer.class, -1, -2));
		assertEquals(new Integer(-2), Skb_ObjectUtils.CONVERT("1x", Integer.class, -1, -2));
	}

	@Test public void testConvertDouble(){
		//test with nullValue returns
		assertNull(Skb_ObjectUtils.CONVERT(null, Double.class, null, null));
		assertEquals(new Double(-1.0), Skb_ObjectUtils.CONVERT(null, Double.class, -1.0, null));
		assertEquals(new Double(0.0), Skb_ObjectUtils.CONVERT(null, Double.class, 0.0, null));

		//falseValue returns
		assertNull(Skb_ObjectUtils.CONVERT("", Double.class, 0.0, null));
		assertEquals(new Double(0.0), Skb_ObjectUtils.CONVERT("", Double.class, -1.0, 0.0));
		assertEquals(new Double(-1.0), Skb_ObjectUtils.CONVERT("", Double.class, 0.0, -1.0));

//		//some values that come from String
		assertEquals(new Double(1.1), Skb_ObjectUtils.CONVERT("1.1", Double.class, -1.0, -2.0));
		assertEquals(new Double(10.2), Skb_ObjectUtils.CONVERT("10.2", Double.class, -1.0, -2.0));

		assertEquals(new Double(-2.0), Skb_ObjectUtils.CONVERT("xxx", Double.class, -1.0, -2.0));
		assertEquals(new Double(-2.0), Skb_ObjectUtils.CONVERT("1x", Double.class, -1.0, -2.0));
	}

	@Test public void testConvertLong(){
		//test with nullValue returns
		assertNull(Skb_ObjectUtils.CONVERT(null, Long.class, null, null));
		assertEquals(new Long(-1), Skb_ObjectUtils.CONVERT(null, Long.class, new Long(-1), null));
		assertEquals(new Long(0), Skb_ObjectUtils.CONVERT(null, Long.class, new Long(0), null));

		//falseValue returns
		assertNull(Skb_ObjectUtils.CONVERT("", Long.class, new Long(0), null));
		assertEquals(new Long(0), Skb_ObjectUtils.CONVERT("", Long.class, new Long(-1), new Long(0)));
		assertEquals(new Long(-1), Skb_ObjectUtils.CONVERT("", Long.class, new Long(0), new Long(-1)));

		//some values that come from String
		assertEquals(new Long(1), Skb_ObjectUtils.CONVERT("1", Long.class, new Long(-1), new Long(-2)));
		assertEquals(new Long(10), Skb_ObjectUtils.CONVERT("10", Long.class, new Long(-1), new Long(-2)));

		assertEquals(new Long(-2), Skb_ObjectUtils.CONVERT("xxx", Long.class, new Long(-1), new Long(-2)));
		assertEquals(new Long(-2), Skb_ObjectUtils.CONVERT("1x", Long.class, new Long(-1), new Long(-2)));
	}

}
