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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.ST.AttributeList;

import de.vandermeer.skb.base.message.EMessageType;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.utils.Skb_STUtils;

/**
 * Tests for the 5WH message class.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.5 build 150623 (23-Jun-15) for Java 1.8
 */
public class Test_Message5WH {

	@Test public void testConstructur(){
		Message5WH m=new Message5WH();

		//everything else is NULL by default
		assertNull(m.getType());
		assertNull(m.getWhy());
		assertNull(m.getHow());
		assertNull(m.getWhat());
		assertNull(m.getReporter());
		assertNull(m.getWhen());
		assertNull(m.getWho());
		assertNull(m.getWhere());

		//initialCapacity should not be lower than 50
		assertTrue(50>=m.initialCapacity);

		//STGroup should not be null, must have a default initialisation
		assertNotNull(m.stg);
	}

	@Test public void testSTGroup(){
		Message5WH m=new Message5WH();

		//intial STGroup should not be null (test here to make sure this test works, also tested in constructor test)
		assertNotNull(m.stg);

		//these are the expected chunks for the STG
		Map<String, List<String>> expectedChunks=new HashMap<String, List<String>>(4);
		expectedChunks.put("where",      Arrays.asList(new String[]{"location", "line", "column"}));
		expectedChunks.put("message5wh", Arrays.asList(new String[]{"reporter", "type", "who", "when", "where", "what", "why", "how"}));

		//test for the expected chunks, returned list must be size 0
		Map<String, List<String>> actual=Skb_STUtils.getMissingChunks(m.stg, expectedChunks);
		assertTrue(actual.size()==0);

		//double check, error message list must be zero too ;) -- unnecessary test?
		Set<String> errmsg=Skb_STUtils.getMissingChunksErrorMessages(Skb_STUtils.getStgName(m.stg), actual);
		assertTrue(errmsg.size()==0);
	}

	@Test public void testST(){
		//this tests message.render(), so in turn it also tests message.asST()
		Message5WH m=new Message5WH();

		//initial message is all set to null and ST should be empty string (not null string)
		assertTrue(m.render()!=null);
		assertTrue("".equals(m.render()));
	}

	@Test public void testTypeSG(){
		Message5WH m=new Message5WH();

		//set null means null
		m.setType(null);
		assertTrue(m.getType()==null);

		// set error means error
		m.setType(EMessageType.ERROR);
		assertTrue(m.getType()==EMessageType.ERROR);

		//set warning means warning
		m.setType(EMessageType.WARNING);
		assertTrue(m.getType()==EMessageType.WARNING);

		//set message means message
		m.setType(EMessageType.INFO);
		assertTrue(m.getType()==EMessageType.INFO);

		//set null means null
		m.setType(null);
		assertTrue(m.getType()==null);
	}

	@Test public void testHowSG(){
		Message5WH m=new Message5WH();
		StrBuilder expected=new StrBuilder(m.initialCapacity);

		//test initial
		assertNull(m.getHow());

		//test to append null, nothing should change
		m.addHow((Object)null);
		assertEquals("", m.getHow().toString());

		//test to append null, nothing should change
		m.addHow((Object[])null);
		assertEquals("", m.getHow().toString());


		//test to append a string, capacity of of message.how should be same as message.initialCapacity
		m.addHow("st1");
		expected.append("st1");
		assertTrue(expected.equals(m.getHow()));
		assertEquals(m.initialCapacity, m.getHow().capacity());

		//append another string, just to check
		m.addHow("st2");
		expected.append("st2");
		assertTrue(expected.equals(m.getHow()));
		assertEquals(m.initialCapacity, m.getHow().capacity()); //two 3 character strings should not exceed intialCapacity

		//addHow(Object) is using StrBuilder.append(Object), so we don't need to check for more, do we?
	}

	@Test public void testWhySG(){
		Message5WH m=new Message5WH();
		StrBuilder expected=new StrBuilder(m.initialCapacity);

		//test initial
		assertNull(m.getWhy());

		//test to append null, nothing should change
		m.addWhy((Object)null);
		assertEquals("", m.getWhy().toString());

				//test to append null, nothing should change
		m.addWhy((Object[])null);
		assertEquals("", m.getWhy().toString());

		//test to append a string, capacity of of message.how should be same as message.initialCapacity
		m.addWhy("st1");
		expected.append("st1");
		assertTrue(expected.equals(m.getWhy()));
		assertEquals(m.initialCapacity, m.getWhy().capacity());

		//append another string, just to check
		m.addWhy("st2");
		expected.append("st2");
		assertTrue(expected.equals(m.getWhy()));
		assertEquals(m.initialCapacity, m.getWhy().capacity()); //two 3 character strings should not exceed intialCapacity

		//addWhy(Object) is using StrBuilder.append(Object), so we don't need to check for more, do we?
	}

	@Test public void testWhatSG(){
		Message5WH m=new Message5WH();
		StrBuilder expected=new StrBuilder(m.initialCapacity);

		//test initial
		assertNull(m.getWhat());

		//test to append null, nothing should change
		m.addWhat((Object)null);
		assertEquals("", m.getWhat().toString());

		//test to append null, nothing should change
		m.addWhat((Object[])null);
		assertEquals("", m.getWhat().toString());

		//test to append a string, capacity of of message.how should be same as message.initialCapacity
		m.addWhat("st1");
		expected.append("st1");
		assertTrue(expected.equals(m.getWhat()));
		assertEquals(m.initialCapacity, m.getWhat().capacity());

		//append another string, just to check
		m.addWhat("st2");
		expected.append("st2");
		assertTrue(expected.equals(m.getWhat()));
		assertEquals(m.initialCapacity, m.getWhat().capacity()); //two 3 character strings should not exceed intialCapacity

		//addWhat(Object) is using StrBuilder.append(Object), so we don't need to check for more, do we?
	}

	@Test public void testReporterSG(){
		Message5WH m=new Message5WH();

		//set to a string
		m.setReporter("reporter");
		assertEquals("reporter", m.getReporter().toString());

		//no null check in set method, null is perfectly fine as a reporter (default for none)
		m.setReporter(null);
		assertEquals(null, m.getReporter());

		//set to an StrBuilder and test for object equality
		StrBuilder expected=new StrBuilder("strbuilder");
		m.setReporter(expected);
		assertTrue(expected.equals(m.getReporter()));
	}

	@Test public void testWhoSG(){
		Message5WH m=new Message5WH();

		//set to a string
		m.setWho("who");
		assertEquals("who", m.getWho().toString());

		//no null check in set method, null is perfectly fine as a reporter (default for none)
		m.setWho(null);
		assertEquals(null, m.getWho());

		//set to an StrBuilder and test for object equality
		StrBuilder expected=new StrBuilder("strbuilder");
		m.setWho(expected);
		assertTrue(expected.equals(m.getWho()));
	}

	@Test public void testWhenSG(){
		Message5WH m=new Message5WH();

		//set to a string
		m.setWhen("when");
		assertEquals("when", m.getWhen().toString());

		//no null check in set method, null is perfectly fine as a reporter (default for none)
		m.setWhen(null);
		assertEquals(null, m.getWhen());

		//set to an StrBuilder and test for object equality
		StrBuilder expected=new StrBuilder("strbuilder");
		m.setWhen(expected);
		assertTrue(expected.equals(m.getWhen()));
	}

	@Test public void testWhereSG_OII(){
		//test setWhere(Object, Integer, Integer)
		Message5WH m=new Message5WH();

		//set to null should not change the default
		m.setWhere(null, 0, 0);
		assertTrue(m.getWhere()==null);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
		m.setWhere("loc1", 0, 0);
		this.testWhereST(m.getWhere(), "loc1", null, null);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
		m.setWhere("loc2", 1, 0);
		this.testWhereST(m.getWhere(), "loc2", "1", null);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
		m.setWhere("loc3", 0, 1);
		this.testWhereST(m.getWhere(), "loc3", null, "1");

		//now test with different location (new ST created) and set line and column to a value greater than 0
		m.setWhere("loc4", 1, 1);
		this.testWhereST(m.getWhere(), "loc4", "1", "1");
	}

	@Test public void testWhereSG_RE(){
		//test setWhere(Object, RecognitionException)
//		Message5WH m=new Message5WH();
//		RecognitionException re=new RecognitionException();

		//set both to null should not change the default
//		m.setWhere(null, (RecognitionException)null);
//		assertTrue(m.getWhere()==null);

		//set one to null should not change the default
//		m.setWhere("loc", (RecognitionException)null);
//		assertTrue(m.getWhere()==null);

		//set the other one to null should not change the default
//		m.setWhere(null, re);
//		assertTrue(m.getWhere()==null);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
//		m.setWhere("loc1", re);
//		this.testWhereST(m.getWhere(), "loc1", null, null);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
//		re.line=1;
//		m.setWhere("loc2", re);
//		this.testWhereST(m.getWhere(), "loc2", "1", null);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
//		re.line=0;
//		re.charPositionInLine=1;
//		m.setWhere("loc3", re);
//		this.testWhereST(m.getWhere(), "loc3", null, "1");

		//now test with different location (new ST created) and set line and column to a value greater than 0
//		re.line=1;
//		m.setWhere("loc4", re);
//		this.testWhereST(m.getWhere(), "loc4", "1", "1");
	}


	@Test public void testWhereSG_Token(){
		//test setWhere(Object, Token)
		Message5WH m=new Message5WH();
		CommonToken tk=new CommonToken(0);

		//set both to null should not change the default
		m.setWhere(null, (Token)null);
		assertTrue(m.getWhere()==null);

		//set one to null should not change the default
		m.setWhere("loc", (Token)null);
		assertTrue(m.getWhere()==null);

		//set the other one to null should not change the default
		m.setWhere(null, tk);
		assertTrue(m.getWhere()==null);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
		m.setWhere("loc1", tk);
		this.testWhereST(m.getWhere(), "loc1", null, null);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
		tk.setLine(1);
		m.setWhere("loc2", tk);
		this.testWhereST(m.getWhere(), "loc2", "1", null);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
		tk.setLine(0);
		tk.setCharPositionInLine(1);
		m.setWhere("loc3", tk);
		this.testWhereST(m.getWhere(), "loc3", null, "1");

		//now test with different location (new ST created) and set line and column to a value greater than 0
		tk.setLine(1);
		m.setWhere("loc4", tk);
		this.testWhereST(m.getWhere(), "loc4", "1", "1");
	}

	@Test public void testWhereSG_Tree(){
		//test setWhere(Object, Tree)
//		Message5WH m=new Message5WH();
//		Token tk=new CommonToken(0);
//		Tree tree=new CommonTree(tk);

		//set both to null should not change the default
//		m.setWhere(null, (Tree)null);
//		assertTrue(m.getWhere()==null);

		//set one to null should not change the default
//		m.setWhere("loc", (Tree)null);
//		assertTrue(m.getWhere()==null);

		//set the other one to null should not change the default
//		m.setWhere(null, tree);
//		assertTrue(m.getWhere()==null);

		//now test valid location with line/column being 0 (i.e. not set) -- must be ST with location but w/o line/column set
//		m.setWhere("loc1", tree);
//		this.testWhereST(m.getWhere(), "loc1", null, null);

		//now test with different location (new ST created) and set line to a value greater than 0 but not column
//		tk.setLine(1);
//		m.setWhere("loc2", new CommonTree(tk));
//		this.testWhereST(m.getWhere(), "loc2", "1", null);

		//now test with different location (new ST created) and set column to a value greater than 0 but not line
//		tk.setLine(0);
//		tk.setCharPositionInLine(1);
//		m.setWhere("loc3", new CommonTree(tk));
//		this.testWhereST(m.getWhere(), "loc3", null, "1");

		//now test with different location (new ST created) and set line and column to a value greater than 0
//		tk.setLine(1);
//		m.setWhere("loc4", new CommonTree(tk));
//		this.testWhereST(m.getWhere(), "loc4", "1", "1");
	}

	@Test public void testWhereSG_STE(){
		//test setWhere(StackTraceElement)
		Message5WH m=new Message5WH();
		StackTraceElement ste=new StackTraceElement("", "", null, 0);

		//check with null first
		m=new Message5WH().setWhere((StackTraceElement)null);
		assertTrue(m.getWhere()==null);

		//now check with an STE that doesn't have any values
		m=new Message5WH().setWhere(ste);
		assertTrue(m.getWhere()==null);

		//now put in valid line only -- null
		ste=new StackTraceElement("", "", null, 1);
		m=new Message5WH().setWhere(ste);
		assertTrue(m.getWhere()==null);

		//now put in valid class name only
		ste=new StackTraceElement("myClass", "", null, 0);
		m=new Message5WH().setWhere(ste);
		this.testWhereST(m.getWhere(), "myClass", null, null);

		//now put in valid method name only
		ste=new StackTraceElement("", "myMethod", null, 0);
		m=new Message5WH().setWhere(ste);
		this.testWhereST(m.getWhere(), "myMethod", null, null);

		//now put in valid class and method but not line
		ste=new StackTraceElement("myClass", "myMethod", null, 0);
		m=new Message5WH().setWhere(ste);
		this.testWhereST(m.getWhere(), Arrays.asList(new Object[]{"myClass", "myMethod"}), null, null);

		//now check for a complete STE
		ste=new StackTraceElement("myClass", "myMethod", null, 1);
		m=new Message5WH().setWhere(ste);
		this.testWhereST(m.getWhere(), Arrays.asList(new Object[]{"myClass", "myMethod"}), "1", null);
	}

	@Test public void testBuilderMethods(){
		//test the set/add methods that return a message object, aka builder methods
		//this test only checks if the set/add methods returns a message object with that set/add being done correctly for valid values
		Message5WH m;

		m=new Message5WH().setReporter("repTest");
		assertEquals("repTest", m.getReporter());

		m=new Message5WH().setType(EMessageType.ERROR);
		assertTrue(m.getType()==EMessageType.ERROR);

		m=new Message5WH().setWho("whoTest");
		assertEquals("whoTest", m.getWho());

		m=new Message5WH().setWhen("whenTest");
		assertEquals("whenTest", m.getWhen());

		//SkbMessage setWhere(Object, int, int)
		m=new Message5WH().setWhere("whereTest", 10, 20);
		this.testWhereST(m.getWhere(), "whereTest", "10", "20");

		//SkbMessage setWhere(Object, RecognitionException)
//		RecognitionException re=new RecognitionException();
//		re.line=10;
//		re.charPositionInLine=20;
//		m=new Message5WH().setWhere("whereRE", re);
//		this.testWhereST(m.getWhere(), "whereRE", "10", "20");

		//SkbMessage setWhere(Object, Token)
		CommonToken tk=new CommonToken(0);
		tk.setLine(10);
		tk.setCharPositionInLine(20);
		m=new Message5WH().setWhere("whereToken", tk);
		this.testWhereST(m.getWhere(), "whereToken", "10", "20");

		//SkbMessage setWhere(Object, Tree)
//		Tree tree=new CommonTree(tk);
//		m=new Message5WH().setWhere("whereTree", tree);
//		this.testWhereST(m.getWhere(), "whereTree", "10", "20");

		//SkbMessage setWhere(StackTraceElement)
		StackTraceElement ste=new StackTraceElement("myClass", "myMethod", "myFilename", 10);
		m=new Message5WH().setWhere(ste);
		this.testWhereST(m.getWhere(), Arrays.asList(new Object[]{"myClass", "myMethod"}), "10", null);

		//for what, how and why we do a .toString() equals only, exact equals tested elsewhere
		m=new Message5WH().addWhat("whatTest");
		assertEquals("whatTest", m.getWhat().toString());

		m=new Message5WH().addHow("howTest");
		assertEquals("howTest", m.getHow().toString());

		m=new Message5WH().addWhy("whyTest");
		assertEquals("whyTest", m.getWhy().toString());
	}

	/**
	 * Tests the attributes of a string template for Where.
	 * The test calls testSTAttribute() using the attribute names "location", "line" and "column".
	 * @param st template to be used for testing
	 * @param expectedLocation expected location value (and type)
	 * @param expectedLine expected line value (and type)
	 * @param expectedColumn expected column value (and type)
	 */
	private void testWhereST(ST st, Object expectedLocation, Object expectedLine, Object expectedColumn){
		this.testSTAttribute(st, "location", expectedLocation);
		this.testSTAttribute(st, "line", expectedLine);
		this.testSTAttribute(st, "column", expectedColumn);
	}

	/**
	 * Tests a single attribute in an ST4.
	 * The test generally fails if a) the ST is null or b) the attribute name is null.
	 * If there is no general failure, then the value of the attribute will be tested against the expected value (and its type).
	 * First test is for null, so if the expected value is null and the actual one is not, the test fails, and vice versa.
	 * If the expected value is not null and all previous tests are successful, then the type of the expected value is used for further testing.
	 * If the type is an instance of java.utils.List, the actual and the expected value will be converted to Object[] and those tested member by
	 * member for equality (test fails for the first pair not being equal or is successful if all pairs are equal).
	 * For all other cases, the test uses expectedValue.toString() for an equal test.
	 * @param st template to be used for testing
	 * @param attrName attribute name to look for
	 * @param expectedValue expected value to test for
	 */
	private void testSTAttribute(ST st, String attrName, Object expectedValue){
		assertNotNull(st);
		assertNotNull(attrName);
		Object o=st.getAttribute(attrName);
		if(expectedValue==null){
			assertNull(o);
		}
		else{
			assertNotNull(o);
			if(expectedValue instanceof List){
				assertTrue(o instanceof AttributeList);

				Object[] expected=((List<?>)expectedValue).toArray();
				Object[] actual=((AttributeList)o).toArray();

				assertEquals(expected.length, actual.length);
				for(int i=0;i<expected.length;i++){
					assertEquals(expected[i], actual[i]);
				}
			}
			else{
				//last chance toString compare
				assertEquals(expectedValue, o.toString());
			}
		}
	}

	@Test public void testMessageEnum(){
		//check the number (increasing), toString (as name().tolower()) and name of the logger for each of the enumerates
		EMessageType[] values=EMessageType.values();
		for(int i=0;i<values.length;i++){
			assertTrue(values[i].getNumber()==i);
			assertEquals(values[i].name().toLowerCase(), values[i].toString());
			String loggerAdd=StringUtils.capitalize(values[i].name().toLowerCase());
			assertEquals("SKBLogger"+loggerAdd, values[i].getLoggerName());
		}
	}

	@Test public void testJavadocExample(){
		Message5WH msg=new Message5WH()
				.setWho("from "+this.getClass().getSimpleName())
				.addWhat("showing a test message")
				.setWhen("noon")
				.setWhere("the class API documentation", 0, 0)
				.addWhy("as a demo")
				.addHow("added to the class JavaDoc")
				.setReporter("The Author")
				.setType(EMessageType.INFO)
		;
		assertNotNull(msg);
//		System.err.println(msg.render());
//		System.err.println(msg);
	}
}
