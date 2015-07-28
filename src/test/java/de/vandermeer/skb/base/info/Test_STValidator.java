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

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.info.STValidator;


/**
 * Tests for {@link STValidator}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class Test_STValidator {

	public final String stgFileEmpty = "de/vandermeer/skb/base/utils/test-empty.stg";

	public final String stgFileSimple = "de/vandermeer/skb/base/utils/test-simple.stg";

	@Test
	public void testSuccess(){
		STGroupFile stg = new STGroupFile(stgFileSimple);

		STValidator stv;
		Set<String> list = new HashSet<String>();

		list.clear();
		stv = new STValidator(stg.getInstanceOf("noArg"), list);
		assertTrue(stv.isValid());

		list.clear();
		list.add("one");
		stv = new STValidator(stg.getInstanceOf("oneArg"), list);
		assertTrue(stv.isValid());

		list.clear();
		list.add("one");
		list.add("two");
		stv = new STValidator(stg.getInstanceOf("twoArgs"), list);
		assertTrue(stv.isValid());
	}

	@Test
	public void testError(){
		STGroupFile stg = new STGroupFile(stgFileSimple);

		STValidator stv;
		Set<String> list = new HashSet<String>();

		list.clear();
		list.add("one");
		list.add("three");
		stv = new STValidator(stg.getInstanceOf("twoArgs"), list);
		assertFalse(stv.isValid());
	}

	@Test
	public void testNull(){
		STValidator stv;

		stv = new STValidator(null, new HashSet<String>());
		assertFalse(stv.isValid());
		assertEquals(1, stv.getValidationErrors().size());

		stv = new STValidator(new ST(""), null);
		assertFalse(stv.isValid());
		assertEquals(1, stv.getValidationErrors().size());

		stv = new STValidator(null, null);
		assertFalse(stv.isValid());
		assertEquals(2, stv.getValidationErrors().size());
	}

	@Test
	public void testEmpty(){
		STValidator stv;

		stv = new STValidator(new ST(""), new HashSet<String>());
		assertTrue(stv.isValid());
		assertEquals(0, stv.getValidationErrors().size());
	}

	@Test
	public void testNoArgST(){
		STValidator stv;
		Set<String> chunks = new HashSet<String>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		ST st;

		chunks.clear();
		st = stg.getInstanceOf("noArg");
		assertNotNull(st);
		stv = new STValidator(st, chunks);
		assertEquals(0, stv.getValidationErrors().size());

		chunks.add("one");
		stv = new STValidator(st, chunks);
		assertEquals(1, stv.getValidationErrors().size());
		chunks.add("two");
		stv = new STValidator(st, chunks);
		assertEquals(2, stv.getValidationErrors().size());
	}

	@Test
	public void test1ArgST(){
		STValidator stv;
		Set<String> chunks = new HashSet<String>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		ST st;

		chunks.clear();
		st = stg.getInstanceOf("oneArg");
		assertNotNull(st);
		stv = new STValidator(st, chunks);
		assertEquals(0, stv.getValidationErrors().size());

		chunks.add("one");
		stv = new STValidator(st, chunks);
		assertEquals(0, stv.getValidationErrors().size());

		chunks.add("two");
		stv = new STValidator(st, chunks);
		assertEquals(1, stv.getValidationErrors().size());
	}

	@Test
	public void test2ArgST(){
		STValidator stv;
		Set<String> chunks = new HashSet<String>();
		STGroup stg = new STGroupFile(this.stgFileSimple);
		assertNotNull(stg);
		ST st;

		chunks.clear();
		st = stg.getInstanceOf("twoArgs");
		assertNotNull(st);
		stv = new STValidator(st, chunks);
		assertEquals(0, stv.getValidationErrors().size());

		chunks.add("one");
		stv = new STValidator(st, chunks);
		assertEquals(0, stv.getValidationErrors().size());

		chunks.add("two");
		stv = new STValidator(st, chunks);
		assertEquals(0, stv.getValidationErrors().size());

		chunks.add("three");
		stv = new STValidator(st, chunks);
		assertEquals(1, stv.getValidationErrors().size());

		chunks.add("four");
		stv = new STValidator(st, chunks);
		assertEquals(2, stv.getValidationErrors().size());

		chunks.add("five");
		stv = new STValidator(st, chunks);
		assertEquals(3, stv.getValidationErrors().size());
	}
}
