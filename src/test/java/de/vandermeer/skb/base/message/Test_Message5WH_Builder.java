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

package de.vandermeer.skb.base.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.text.StrBuilder;
import org.junit.Test;

/**
 * Tests for {@link Message5WH_Builder}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
 */
public class Test_Message5WH_Builder {

	@Test public void testConstructur(){
		Message5WH_Builder mb = new Message5WH_Builder();

		//everything else is NULL by default
		assertNull(mb.type);
		assertNull(mb.why);
		assertNull(mb.how);
		assertNull(mb.what);
		assertNull(mb.reporter);
		assertNull(mb.when);
		assertNull(mb.who);
		assertNull(mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//initialCapacity should not be lower than 50
		assertTrue(50 >= mb.initialCapacity);
	}

	@Test public void testBuilderMethods(){
		//test the set/add methods that return a message object, aka builder methods
		//this test only checks if the set/add methods returns a message object with that set/add being done correctly for valid values
		Message5WH_Builder mb;

		mb = new Message5WH_Builder().setReporter("repTest");
		assertEquals("repTest", mb.reporter);

		mb = new Message5WH_Builder().setType(E_MessageType.ERROR);
		assertTrue(mb.type==E_MessageType.ERROR);

		mb = new Message5WH_Builder().setWho("whoTest");
		assertEquals("whoTest", mb.who);

		mb = new Message5WH_Builder().setWhen("whenTest");
		assertEquals("whenTest", mb.when);

		//SkbMessage setWhere(Object, int, int)
		mb = new Message5WH_Builder().setWhere("whereTest", 10, 20);
		assertEquals("whereTest", mb.whereLocation);
		assertEquals(10, mb.whereLine);
		assertEquals(20, mb.whereColumn);

		//SkbMessage setWhere(Object, RecognitionException)
//		RecognitionException re=new RecognitionException();
//		re.line = 10;
//		re.charPositionInLine = 20;
//		mb = new Message5WH_Builder().setWhere("whereRE", re);
//		this.testWhereST(mb.where, "whereRE", "10", "20");

		//SkbMessage setWhere(Object, Token)
		CommonToken tk = new CommonToken(0);
		tk.setLine(10);
		tk.setCharPositionInLine(20);
		mb = new Message5WH_Builder().setWhere("whereToken", tk);
		assertEquals("whereToken", mb.whereLocation);
		assertEquals(10, mb.whereLine);
		assertEquals(20, mb.whereColumn);


		//SkbMessage setWhere(Object, Tree)
//		Tree tree = new CommonTree(tk);
//		mb = new Message5WH_Builder().setWhere("whereTree", tree);
//		this.testWhereST(mb.where, "whereTree", "10", "20");

		//SkbMessage setWhere(StackTraceElement)
		StackTraceElement ste = new StackTraceElement("myClass", "myMethod", "myFilename", 10);
		mb = new Message5WH_Builder().setWhere(ste);
		assertEquals("myMethod", mb.whereLocation);
		assertEquals(10, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//for what, how and why we do a .toString() equals only, exact equals tested elsewhere
		mb = new Message5WH_Builder().addWhat("whatTest");
		assertEquals("whatTest", mb.what.toString());

		mb = new Message5WH_Builder().addHow("howTest");
		assertEquals("howTest", mb.how.toString());

		mb = new Message5WH_Builder().addWhy("whyTest");
		assertEquals("whyTest", mb.why.toString());
	}

	@Test public void testTypeSet(){
		Message5WH_Builder mb = new Message5WH_Builder();

		//set null means null
		mb.setType(null);
		assertTrue(mb.type==null);

		// set error means error
		mb.setType(E_MessageType.ERROR);
		assertTrue(mb.type==E_MessageType.ERROR);

		//set warning means warning
		mb.setType(E_MessageType.WARNING);
		assertTrue(mb.type==E_MessageType.WARNING);

		//set message means message
		mb.setType(E_MessageType.INFO);
		assertTrue(mb.type==E_MessageType.INFO);

		//set null means null
		mb.setType(null);
		assertTrue(mb.type==null);
	}

	@Test public void testHowSet(){
		Message5WH_Builder mb = new Message5WH_Builder();
		StrBuilder expected = new StrBuilder(mb.initialCapacity);

		//test initial
		assertNull(mb.how);

		//test to append null, nothing should change
		mb.addHow((Object)null);
		assertEquals("", mb.how.toString());

		//test to append null, nothing should change
		mb.addHow((Object[])null);
		assertEquals("", mb.how.toString());


		//test to append a string, capacity of of message.how should be same as message.initialCapacity
		mb.addHow("st1");
		expected.append("st1");
		assertTrue(expected.equals(mb.how));
		assertEquals(mb.initialCapacity, mb.how.capacity());

		//append another string, just to check
		mb.addHow("st2");
		expected.append("st2");
		assertTrue(expected.equals(mb.how));
		assertEquals(mb.initialCapacity, mb.how.capacity()); //two 3 character strings should not exceed intialCapacity

		//addHow(Object) is using StrBuilder.append(Object), so we don't need to check for more, do we?
	}

	@Test public void testWhySet(){
		Message5WH_Builder mb = new Message5WH_Builder();
		StrBuilder expected = new StrBuilder(mb.initialCapacity);

		//test initial
		assertNull(mb.why);

		//test to append null, nothing should change
		mb.addWhy((Object)null);
		assertEquals("", mb.why.toString());

				//test to append null, nothing should change
		mb.addWhy((Object[])null);
		assertEquals("", mb.why.toString());

		//test to append a string, capacity of of message.how should be same as message.initialCapacity
		mb.addWhy("st1");
		expected.append("st1");
		assertTrue(expected.equals(mb.why));
		assertEquals(mb.initialCapacity, mb.why.capacity());

		//append another string, just to check
		mb.addWhy("st2");
		expected.append("st2");
		assertTrue(expected.equals(mb.why));
		assertEquals(mb.initialCapacity, mb.why.capacity()); //two 3 character strings should not exceed intialCapacity

		//addWhy(Object) is using StrBuilder.append(Object), so we don't need to check for more, do we?
	}

	@Test public void testWhatSet(){
		Message5WH_Builder mb = new Message5WH_Builder();
		StrBuilder expected = new StrBuilder(mb.initialCapacity);

		//test initial
		assertNull(mb.what);

		//test to append null, nothing should change
		mb.addWhat((Object)null);
		assertEquals("", mb.what.toString());

		//test to append null, nothing should change
		mb.addWhat((Object[])null);
		assertEquals("", mb.what.toString());

		//test to append a string, capacity of of message.how should be same as message.initialCapacity
		mb.addWhat("st1");
		expected.append("st1");
		assertTrue(expected.equals(mb.what));
		assertEquals(mb.initialCapacity, mb.what.capacity());

		//append another string, just to check
		mb.addWhat("st2");
		expected.append("st2");
		assertTrue(expected.equals(mb.what));
		assertEquals(mb.initialCapacity, mb.what.capacity()); //two 3 character strings should not exceed intialCapacity

		//addWhat(Object) is using StrBuilder.append(Object), so we don't need to check for more, do we?
	}

	@Test public void testReporterSet(){
		Message5WH_Builder mb = new Message5WH_Builder();

		//set to a string
		mb.setReporter("reporter");
		assertEquals("reporter", mb.reporter.toString());

		//no null check in set method, null is perfectly fine as a reporter (default for none)
		mb.setReporter(null);
		assertEquals(null, mb.reporter);

		//set to an StrBuilder and test for object equality
		StrBuilder expected = new StrBuilder("strbuilder");
		mb.setReporter(expected);
		assertTrue(expected.equals(mb.reporter));
	}

	@Test public void testWhoSet(){
		Message5WH_Builder mb = new Message5WH_Builder();

		//set to a string
		mb.setWho("who");
		assertEquals("who", mb.who.toString());

		//no null check in set method, null is perfectly fine as a reporter (default for none)
		mb.setWho(null);
		assertEquals(null, mb.who);

		//set to an StrBuilder and test for object equality
		StrBuilder expected = new StrBuilder("strbuilder");
		mb.setWho(expected);
		assertTrue(expected.equals(mb.who));
	}

	@Test public void testWhenSet(){
		Message5WH_Builder mb = new Message5WH_Builder();

		//set to a string
		mb.setWhen("when");
		assertEquals("when", mb.when.toString());

		//no null check in set method, null is perfectly fine as a reporter (default for none)
		mb.setWhen(null);
		assertEquals(null, mb.when);

		//set to an StrBuilder and test for object equality
		StrBuilder expected = new StrBuilder("strbuilder");
		mb.setWhen(expected);
		assertTrue(expected.equals(mb.when));
	}

	@Test public void testWhereSet_OII(){
		//test setWhere(Object, Integer, Integer)
		Message5WH_Builder mb = new Message5WH_Builder();

		//set to null should not change the default
		mb.setWhere(null, 0, 0);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
		mb.setWhere("loc1", 0, 0);
		assertEquals("loc1", mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
		mb.setWhere("loc2", 1, 0);
		assertEquals("loc2", mb.whereLocation);
		assertEquals(1, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
		mb.setWhere("loc3", 0, 1);
		assertEquals("loc3", mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(1, mb.whereColumn);

		//now test with different location (new ST created) and set line and column to a value greater than 0
		mb.setWhere("loc4", 1, 1);
		assertEquals("loc4", mb.whereLocation);
		assertEquals(1, mb.whereLine);
		assertEquals(1, mb.whereColumn);
	}

	@Test public void testWhereSet_Token(){
		//test setWhere(Object, Token)
		Message5WH_Builder mb = new Message5WH_Builder();
		CommonToken tk = new CommonToken(0);

		//set both to null should not change the default
		mb.setWhere(null, (Token)null);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//set one to null should not change the default
		mb.setWhere("loc", (Token)null);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//set the other one to null should not change the default
		mb.setWhere(null, tk);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
		mb.setWhere("loc1", tk);
		assertEquals("loc1", mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(-1, mb.whereColumn);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
		tk.setLine(1);
		mb.setWhere("loc2", tk);
		assertEquals("loc2", mb.whereLocation);
		assertEquals(1, mb.whereLine);
		assertEquals(-1, mb.whereColumn);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
		tk.setLine(0);
		tk.setCharPositionInLine(1);
		mb.setWhere("loc3", tk);
		assertEquals("loc3", mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(1, mb.whereColumn);

		//now test with different location (new ST created) and set line and column to a value greater than 0
		tk.setLine(1);
		mb.setWhere("loc4", tk);
		assertEquals("loc4", mb.whereLocation);
		assertEquals(1, mb.whereLine);
		assertEquals(1, mb.whereColumn);
	}

	@Test public void testWhereSet_STE(){
		//test setWhere(StackTraceElement)
		Message5WH_Builder mb;
		StackTraceElement ste = new StackTraceElement("", "", null, 0);

		//check with null first
		mb = new Message5WH_Builder().setWhere((StackTraceElement)null);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now check with an STE that doesn't have any values
		mb = new Message5WH_Builder().setWhere(ste);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now put in valid line only -- null
		ste = new StackTraceElement("", "", null, 1);
		mb = new Message5WH_Builder().setWhere(ste);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now put in valid class name only
		ste = new StackTraceElement("myClass", "", null, 0);
		mb = new Message5WH_Builder().setWhere(ste);
		assertEquals("myClass", mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now put in valid method name only
		ste = new StackTraceElement("", "myMethod", null, 0);
		mb = new Message5WH_Builder().setWhere(ste);
		assertEquals("myMethod", mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now put in valid class and method but not line
		ste = new StackTraceElement("myClass", "myMethod", null, 0);
		mb = new Message5WH_Builder().setWhere(ste);
		assertEquals("myMethod", mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);

		//now check for a complete STE
		ste = new StackTraceElement("myClass", "myMethod", null, 1);
		mb = new Message5WH_Builder().setWhere(ste);
		assertEquals("myMethod", mb.whereLocation);
		assertEquals(1, mb.whereLine);
		assertEquals(0, mb.whereColumn);
	}

	@Test public void testWhereSet_RE(){
		//test setWhere(Object, RecognitionException)
		Message5WH_Builder mb = new Message5WH_Builder();

		//set both to null should not change the default
		mb.setWhere(null, (RecognitionException)null);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);
//		assertTrue(mb.where==null);

		//set one to null should not change the default
		mb.setWhere("loc", (RecognitionException)null);
		assertEquals(null, mb.whereLocation);
		assertEquals(0, mb.whereLine);
		assertEquals(0, mb.whereColumn);
//		assertTrue(mb.where==null);

		//set the other one to null should not change the default
//		mb.setWhere(null, re);
//		assertTrue(mb.where==null);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
//		mb.setWhere("loc1", re);
//		this.testWhereST(mb.where, "loc1", null, null);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
//		re.line = 1;
//		mb.setWhere("loc2", re);
//		this.testWhereST(mb.where, "loc2", "1", null);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
//		re.line = 0;
//		re.charPositionInLine = 1;
//		mb.setWhere("loc3", re);
//		this.testWhereST(mb.where, "loc3", null, "1");

		//now test with different location (new ST created) and set line and column to a value greater than 0
//		re.line = 1;
//		mb.setWhere("loc4", re);
//		this.testWhereST(mb.where, "loc4", "1", "1");
	}

	@Test public void testWhereSet_Tree(){
		//test setWhere(Object, Tree)
//		Message5WH_Builder mb = new Message5WH_Builder();
//		Token tk = new CommonToken(0);
//		Tree tree = new CommonTree(tk);

//		//set both to null should not change the default
//		mb.setWhere(null, (Tree)null);
//		assertTrue(mb.where==null);

		//set one to null should not change the default
//		mb.setWhere("loc", (Tree)null);
//		assertTrue(mb.where==null);

		//set the other one to null should not change the default
//		mb.setWhere(null, tree);
//		assertTrue(mb.where==null);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
//		mb.setWhere("loc1", tree);
//		this.testWhereST(mb.where, "loc1", null, null);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
//		tk.setLine(1);
//		mb.setWhere("loc2", new CommonTree(tk));
//		this.testWhereST(mb.where, "loc2", "1", null);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
//		tk.setLine(0);
//		tk.setCharPositionInLine(1);
//		mb.setWhere("loc3", new CommonTree(tk));
//		this.testWhereST(mb.where, "loc3", null, "1");

		//now test with different location (new ST created) and set line and column to a value greater than 0
//		tk.setLine(1);
//		mb.setWhere("loc4", new CommonTree(tk));
//		this.testWhereST(mb.where, "loc4", "1", "1");
	}

//	/**
//	 * Tests a single attribute in an ST4.
//	 * The test generally fails if a) the ST is null or b) the attribute name is null.
//	 * If there is no general failure, then the value of the attribute will be tested against the expected value (and its type).
//	 * First test is for null, so if the expected value is null and the actual one is not, the test fails, and vice versa.
//	 * If the expected value is not null and all previous tests are successful, then the type of the expected value is used for further testing.
//	 * If the type is an instance of java.utils.List, the actual and the expected value will be converted to Object[] and those tested member by
//	 * member for equality (test fails for the first pair not being equal or is successful if all pairs are equal).
//	 * For all other cases, the test uses expectedValue.toString() for an equal test.
//	 * @param st template to be used for testing
//	 * @param attrName attribute name to look for
//	 * @param expectedValue expected value to test for
//	 */
//	private void testSTAttribute(ST st, String attrName, Object expectedValue){
//		assertNotNull(st);
//		assertNotNull(attrName);
//		Object o = st.getAttribute(attrName);
//		if(expectedValue==null){
//			assertNull(o);
//		}
//		else{
//			assertNotNull(o);
//			if(expectedValue instanceof List){
//				assertTrue(o instanceof AttributeList);
//
//				Object[] expected = ((List<?>)expectedValue).toArray();
//				Object[] actual = ((AttributeList)o).toArray();
//
//				assertEquals(expected.length, actual.length);
//				for(int i=0; i<expected.length; i++){
//					assertEquals(expected[i], actual[i]);
//				}
//			}
//			else{
//				//last chance toString compare
//				assertEquals(expectedValue, o.toString());
//			}
//		}
//	}

}
