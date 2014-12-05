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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.utils.Skb_STUtils;

/**
 * Tests for STUtils.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 140626 (26-Jun-14) with Java 1.8
 */
public class Skb_STUtils_Tests {
	public final String stgFileEmpty ="de/vandermeer/skb/base/utils/test-empty.stg";
	public final String stgFileSimple="de/vandermeer/skb/base/utils/test-simple.stg";

	@Test public void testGetStgName(){
		assertNull(Skb_STUtils.getStgName(null));

		STGroup stg=new STGroupFile(this.stgFileEmpty);
		assertNotNull(stg);
		assertEquals(StringUtils.substringBeforeLast(this.stgFileEmpty, "."), Skb_STUtils.getStgName(stg));
	}

	@Test public void testGetMissingSTArguments(){
		//null and empty ST
		assertNull(Skb_STUtils.getMissingSTArguments(null, null));
		assertNull(Skb_STUtils.getMissingSTArguments(new ST(""), null));
		assertNull(Skb_STUtils.getMissingSTArguments(null, new ArrayList<String>()));

		//empty but not-null ST and empty chunk list
		assertNotNull(Skb_STUtils.getMissingSTArguments(new ST(""), new ArrayList<String>()));
		assertEquals(0, Skb_STUtils.getMissingSTArguments(new ST(""), new ArrayList<String>()).size());


		//some vars for further testing
		ArrayList<String> chunks=new ArrayList<String>();
		STGroup stg=new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		ST st;


		//test an empty template
		chunks.clear();
		st=stg.getInstanceOf("noArg");
		assertNotNull(st);
		assertEquals(0, Skb_STUtils.getMissingSTArguments(st, chunks).size());
		
		chunks.add("one");
		assertEquals(1, Skb_STUtils.getMissingSTArguments(st, chunks).size());
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("one"));

		chunks.add("two");
		assertEquals(2, Skb_STUtils.getMissingSTArguments(st, chunks).size());
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("one"));
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("two"));


		//test a template with 1 argument
		chunks.clear();
		st=stg.getInstanceOf("oneArg");
		assertNotNull(st);
		assertEquals(0, Skb_STUtils.getMissingSTArguments(st, chunks).size());

		chunks.add("one");
		assertEquals(0, Skb_STUtils.getMissingSTArguments(st, chunks).size());

		chunks.add("two");
		assertEquals(1, Skb_STUtils.getMissingSTArguments(st, chunks).size());
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("two"));


		//test a template with 2 arguments
		chunks.clear();
		st=stg.getInstanceOf("twoArgs");
		assertNotNull(st);

		assertEquals(0, Skb_STUtils.getMissingSTArguments(st, chunks).size());

		chunks.add("one");
		assertEquals(0, Skb_STUtils.getMissingSTArguments(st, chunks).size());

		chunks.add("two");
		assertEquals(0, Skb_STUtils.getMissingSTArguments(st, chunks).size());

		chunks.add("three");
		assertEquals(1, Skb_STUtils.getMissingSTArguments(st, chunks).size());
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("three"));

		chunks.add("four");
		assertEquals(2, Skb_STUtils.getMissingSTArguments(st, chunks).size());
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("three"));
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("four"));

		chunks.add("five");
		assertEquals(3, Skb_STUtils.getMissingSTArguments(st, chunks).size());
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("three"));
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("four"));
		assertTrue(Skb_STUtils.getMissingSTArguments(st, chunks).contains("five"));
	}



	//TODO not sure when a template has a leading '/' ...
	@Test public void testGetMissingChunks(){
		//null
		assertNull(Skb_STUtils.getMissingChunks(null, null));
		assertNull(Skb_STUtils.getMissingChunks(new STGroup(), null));
		assertNull(Skb_STUtils.getMissingChunks(null, new HashMap<String, List<String>>()));

		//empty but not-null ST and empty chunk list
		assertNotNull(Skb_STUtils.getMissingChunks(new STGroup(), new HashMap<String, List<String>>()));
		assertEquals(0, Skb_STUtils.getMissingChunks(new STGroup(), new HashMap<String, List<String>>()).size());


		//some vars for further testing
		Map<String, List<String>> chunks=new HashMap<String, List<String>>();
		ArrayList<String> ar=new ArrayList<String>();
		STGroup stg=new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);

		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());

		chunks.put(null, null);
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());
		chunks.put("", null);
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());
		chunks.put(null, new ArrayList<String>());
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());
		chunks.put("noArg", null);
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());
		chunks.put("noArg", new ArrayList<String>());
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());

		chunks.put("oneArg", null);
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());
		ar.add("one");
		chunks.put("oneArg", ar);
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());
		ar.add("two");
		assertEquals(1, Skb_STUtils.getMissingChunks(stg, chunks).size());
		assertEquals(1, Skb_STUtils.getMissingChunks(stg, chunks).get("oneArg").size());
		assertTrue(Skb_STUtils.getMissingChunks(stg, chunks).get("oneArg").contains("two"));
		ar.add("three");
		assertEquals(1, Skb_STUtils.getMissingChunks(stg, chunks).size());
		assertEquals(2, Skb_STUtils.getMissingChunks(stg, chunks).get("oneArg").size());
		assertTrue(Skb_STUtils.getMissingChunks(stg, chunks).get("oneArg").contains("two"));
		assertTrue(Skb_STUtils.getMissingChunks(stg, chunks).get("oneArg").contains("three"));


		chunks.clear();
		chunks.put("noArg", null);
		chunks.put("oneArg", Arrays.asList(new String[]{"one"}));
		chunks.put("twoArgs", Arrays.asList(new String[]{"one", "two"}));
		assertEquals(0, Skb_STUtils.getMissingChunks(stg, chunks).size());

		chunks.clear();
		chunks.put("noArg", null);
		chunks.put("oneArg", Arrays.asList(new String[]{"three"}));
		chunks.put("twoArgs", Arrays.asList(new String[]{"four", "five"}));
		assertEquals(2, Skb_STUtils.getMissingChunks(stg, chunks).size());
		assertEquals(1, Skb_STUtils.getMissingChunks(stg, chunks).get("oneArg").size());
		assertTrue(Skb_STUtils.getMissingChunks(stg, chunks).get("oneArg").contains("three"));
		assertEquals(2, Skb_STUtils.getMissingChunks(stg, chunks).get("twoArgs").size());
		assertTrue(Skb_STUtils.getMissingChunks(stg, chunks).get("twoArgs").contains("four"));
		assertTrue(Skb_STUtils.getMissingChunks(stg, chunks).get("twoArgs").contains("five"));
	}
}
