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

package de.vandermeer.skb.base.info;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.info.STGroupValidator;


/**
 * Tests for {@link STGroupValidator}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.12-SNAPSHOT build 150811 (11-Aug-15) for Java 1.8
 * @since      v0.0.7
 */
public class Test_STGroupValidator {

	public final String stgFileEmpty = "de/vandermeer/skb/base/utils/test-empty.stg";

	public final String stgFileSimple = "de/vandermeer/skb/base/utils/test-simple.stg";

	@Test
	public void testSuccess(){
		STGroupFile stg = new STGroupFile(stgFileSimple);

		STGroupValidator stgv;
		Map<String, Set<String>> map = new HashMap<>();

		map.put("noArg", new HashSet<>());

		Set<String> args = new HashSet<>();
		args.add("one");
		map.put("oneArg", args);

		args = new HashSet<>();
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
		Map<String, Set<String>> map = new HashMap<>();

		map.put("noArg", new HashSet<>());

		Set<String> args = new HashSet<>();
		args.add("one");
		map.put("oneArg", args);

		args = new HashSet<>();
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
		Map<String, Set<String>> map = new HashMap<>();

		map.put("noArg", new HashSet<>());

		Set<String> args = new HashSet<>();
		args.add("one");
		map.put("oneArg", args);

		args = new HashSet<>();
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

		stgv = new STGroupValidator(null, new HashMap<String, Set<String>>());
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

		stgv = new STGroupValidator(new STGroup(), new HashMap<String, Set<String>>());
		assertTrue(stgv.isValid());
		assertEquals(0, stgv.getValidationErrors().size());
	}

	@Test
	public void testNoArgSTG(){
		Map<String, Set<String>> chunks = new HashMap<String, Set<String>>();
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

		chunks.put(null, new HashSet<String>());
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		chunks.put("noArg", null);
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(1, stgv.getValidationErrors().size());

		chunks.put("noArg", new HashSet<String>());
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());
	}

	@Test
	public void test1ArgST(){
		Map<String, Set<String>> chunks = new HashMap<String, Set<String>>();
		Set<String> ar = new HashSet<String>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		STGroupValidator stgv;

		chunks.put("oneArg", new HashSet<String>());
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
		STGroup stg = new STGroupFile(this.stgFileSimple);
		Map<String, Set<String>> chunks;
		assertNotNull(stg);
		STGroupValidator stgv;

		chunks = new HashMap<String, Set<String>>(){
			private static final long serialVersionUID = 1L;{
				put("noArg", new HashSet<String>());
				put("oneArg", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("one");
					}}
				);
				put("twoArgs", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("one"); add("one");
					}}
				);
			}
		};
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(0, stgv.getValidationErrors().size());

		chunks = new HashMap<String, Set<String>>(){
			private static final long serialVersionUID = 1L;{
				put("noArg", new HashSet<String>());
				put("oneArg", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("three");
					}}
				);
				put("twoArgs", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("four"); add("five");
					}}
				);
			}
		};
		chunks.put("noArg", new HashSet<String>());
		stgv = new STGroupValidator(stg, chunks);
		assertEquals(3, stgv.getValidationErrors().size());
	}

}
