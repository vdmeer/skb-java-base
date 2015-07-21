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

package de.vandermeer.skb.base.info.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;


/**
 * Tests for {@link STGroupValidator}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.7 build 150712 (12-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class Test_STGroupValidator {

	public final String stgFileEmpty = "de/vandermeer/skb/base/utils/test-empty.stg";

	public final String stgFileSimple = "de/vandermeer/skb/base/utils/test-simple.stg";

	@Test
	public void testSuccess(){
		STGroupFile stg = new STGroupFile(stgFileSimple);

		STGroupValidator stgv;
		Map<String, List<String>> map = new HashMap<>();

		map.put("noArg", new ArrayList<>());

		List<String> args = new ArrayList<>();
		args.add("one");
		map.put("oneArg", args);

		args = new ArrayList<>();
		args.add("one");
		args.add("two");
		map.put("twoArgs", args);

		stgv = new STGroupValidator(stg, map);
		assertTrue(stgv.isValid());
	}

	@Test
	public void testErrorS1(){
		STGroupFile stg = new STGroupFile(stgFileSimple);

		STGroupValidator stgv;
		Map<String, List<String>> map = new HashMap<>();

		map.put("noArg", new ArrayList<>());

		List<String> args = new ArrayList<>();
		args.add("one");
		map.put("oneArg", args);

		args = new ArrayList<>();
		args.add("one");
		args.add("three");
		map.put("twoArgs", args);

		stgv = new STGroupValidator(stg, map);
		assertFalse(stgv.isValid());
		assertEquals(1, stgv.getValidationErrors().size());
	}

	@Test
	public void testErrorS2(){
		STGroupFile stg = new STGroupFile(stgFileSimple);

		STGroupValidator stgv;
		Map<String, List<String>> map = new HashMap<>();

		map.put("noArg", new ArrayList<>());

		List<String> args = new ArrayList<>();
		args.add("one");
		map.put("oneArg", args);

		args = new ArrayList<>();
		args.add("three");
		args.add("four");
		map.put("twoArgs", args);

		stgv = new STGroupValidator(stg, map);
		assertFalse(stgv.isValid());
		assertEquals(2, stgv.getValidationErrors().size());
	}

	@Test
	public void testNull(){
		STGroupValidator stgv;

		stgv = new STGroupValidator(null, new HashMap<String, List<String>>());
		assertFalse(stgv.isValid());
		assertEquals(1, stgv.getValidationErrors().size());

		stgv = new STGroupValidator(new STGroup(), null);
		assertFalse(stgv.isValid());
		assertEquals(1, stgv.getValidationErrors().size());

		stgv = new STGroupValidator(null, null);
		assertFalse(stgv.isValid());
		assertEquals(2, stgv.getValidationErrors().size());
	}

	@Test
	public void testEmpty(){
		STGroupValidator stgv;

		stgv = new STGroupValidator(new STGroup(), new HashMap<String, List<String>>());
		assertTrue(stgv.isValid());
		assertEquals(0, stgv.getValidationErrors().size());
	}

	@Test
	public void testNoArgSTG(){
		Map<String, List<String>> chunks = new HashMap<String, List<String>>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		STGroupValidator stgv;

		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		chunks.put(null, null);
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		chunks.put("", null);
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		chunks.put(null, new ArrayList<String>());
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		chunks.put("noArg", null);
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(1, stgv.getValidationErrors().size());

		chunks.put("noArg", new ArrayList<String>());
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());
	}

	@Test
	public void test1ArgST(){
		Map<String, List<String>> chunks = new HashMap<String, List<String>>();
		ArrayList<String> ar = new ArrayList<String>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		STGroupValidator stgv;

		chunks.put("oneArg", new ArrayList<String>());
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		ar.add("one");
		chunks.put("oneArg", ar);
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		ar.add("two");
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(1, stgv.getValidationErrors().size());

		ar.add("three");
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(2, stgv.getValidationErrors().size());
	}

	@Test
	public void test2ArgST(){
		Map<String, List<String>> chunks = new HashMap<String, List<String>>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		STGroupValidator stgv;

		chunks.clear();
		chunks.put("noArg", new ArrayList<String>());
		chunks.put("oneArg", Arrays.asList(new String[]{"one"}));
		chunks.put("twoArgs", Arrays.asList(new String[]{"one", "two"}));
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		chunks.clear();
		chunks.put("noArg", new ArrayList<String>());
		chunks.put("oneArg", Arrays.asList(new String[]{"three"}));
		chunks.put("twoArgs", Arrays.asList(new String[]{"four", "five"}));
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(3, stgv.getValidationErrors().size());
	}

}
