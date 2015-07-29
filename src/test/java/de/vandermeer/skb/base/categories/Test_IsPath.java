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

package de.vandermeer.skb.base.categories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import de.vandermeer.skb.base.Skb_Defaults;
import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Tests for {@link IsPath}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.0-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_IsPath {

	Test_IsPath_Data ptd;

	public Test_IsPath(){
		this.ptd = new Test_IsPath_Data();
	}

	@Test public void testNewPath(){
		IsPath key;

		key = IsPath.create();
		assertNotNull(key);
		assertTrue(key instanceof IsPath);
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key.path());
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key._value());
		assertEquals(Skb_Defaults.DEFAULT_DESCRIPTION, key.getDescription());
		assertEquals("IsPath(DefaultImpl): " + Skb_Defaults.DEFAULT_VALUE, key.toString());

		key = IsPath.create(null);
		assertNotNull(key);
		assertTrue(key instanceof IsPath);
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key.path());
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key._value());
		assertEquals(Skb_Defaults.DEFAULT_DESCRIPTION, key.getDescription());
		assertEquals("IsPath(DefaultImpl): " + Skb_Defaults.DEFAULT_VALUE, key.toString());

		key = IsPath.create("deadbeef");
		assertNotNull(key);
		assertTrue(key instanceof IsPath);
		assertEquals("deadbeef", key.path());
		assertEquals("deadbeef", key._value());
		assertEquals(Skb_Defaults.DEFAULT_DESCRIPTION, key.getDescription());
		assertEquals("IsPath(DefaultImpl): deadbeef", key.toString());
	}

	@Test public void test_Object2PathValue(){
		Skb_Transformer<Object, String> tf = CategoryWithValue.CAT_TO_VALUESTRING();
		assertNull(tf.transform(null));
	}

	@Test public void test_path2Value(){
		assertNull(CategoryWithValue.GET_VALUESTRING(null));
	}

	@Test public void testIsValidPathValue(){
		Predicate<Object> slash = IsPath.IS_VALID_PATH_VALUE("/");
		Predicate<Object> hash = IsPath.IS_VALID_PATH_VALUE("#");

		assertFalse(slash.test(null));
		assertFalse(hash.test(null));

		for(Integer key : this.ptd.forMisc.keySet()){
			StrBuilder st = new StrBuilder(StringUtils.replace(this.ptd.forMisc.get(key).fqpn, this.ptd.sepReplace, "/"));
			boolean isvalid = this.ptd.forMisc.get(key).isvalid;
			assertEquals(key + " is a problem", isvalid, slash.test(st));
		}

		for(Integer key : this.ptd.forMisc.keySet()){
			StrBuilder st = new StrBuilder(StringUtils.replace(this.ptd.forMisc.get(key).fqpn, this.ptd.sepReplace, "#"));
			boolean isvalid = this.ptd.forMisc.get(key).isvalid;
			assertEquals(key + " is a problem", isvalid, hash.test(st));
		}
	}

	@Test public void testPath2Levels(){
		Test_IsPath_Data ptd = new Test_IsPath_Data();
		Skb_Transformer<Object, Integer> slash = IsPath.PATH_TO_LEVELS("/");
		Skb_Transformer<Object, Integer> hash = IsPath.PATH_TO_LEVELS("#");

		for(Integer key : ptd.forMisc.keySet()){
			StrBuilder st = new StrBuilder(StringUtils.replace(ptd.forMisc.get(key).fqpn, ptd.sepReplace, "/"));
			Integer expectedLevel = ptd.forMisc.get(key).expectedLevel;
			assertEquals(key + " is a problem", expectedLevel, slash.transform(st));
		}

		for(Integer key : ptd.forMisc.keySet()){
			StrBuilder st = new StrBuilder(StringUtils.replace(ptd.forMisc.get(key).fqpn, ptd.sepReplace, "#"));
			Integer expectedLevel = ptd.forMisc.get(key).expectedLevel;
			assertEquals(key + " is a problem", expectedLevel, hash.transform(st));
		}
	}

	@Test public void testGetSubPaths(){
		Map<String, String> map = new HashMap<String, String>();

		map.put("/", null);
		map.put("/a", "level1");
		map.put("/a/b", "level2");
		map.put("/a/b/c", "level3");
		map.put("/a/b/c/d", "level4");
		map.put("/a/b/c/e", "level4");
		map.put("/a/b/y", "level3");
		map.put("/a/b/y/w", "level4");
		map.put("/a/b/y/z", "level4");

		assertEquals(8, IsPath.GET_SUB_PATHS("/", "/", map.keySet()).size());
		assertEquals(7, IsPath.GET_SUB_PATHS("/", "/a", map.keySet()).size());
		assertEquals(6, IsPath.GET_SUB_PATHS("/", "/a/b", map.keySet()).size());

		assertEquals(2, IsPath.GET_SUB_PATHS("/", "/a/b/c", map.keySet()).size());
		assertEquals(0, IsPath.GET_SUB_PATHS("/", "/a/b/c/d", map.keySet()).size());
		assertEquals(0, IsPath.GET_SUB_PATHS("/", "/a/b/c/e", map.keySet()).size());

		assertEquals(2, IsPath.GET_SUB_PATHS("/", "/a/b/y", map.keySet()).size());
		assertEquals(0, IsPath.GET_SUB_PATHS("/", "/a/b/w", map.keySet()).size());
		assertEquals(0, IsPath.GET_SUB_PATHS("/", "/a/b/z", map.keySet()).size());

		Collection<String> ch;
		ch = IsPath.GET_SUB_PATHS("/", "/a/b/c", map.keySet());
		assertTrue(ch.contains("/a/b/c/d"));
		assertTrue(ch.contains("/a/b/c/e"));

		ch = IsPath.GET_SUB_PATHS("/", "/a/b/y", map.keySet());
		assertTrue(ch.contains("/a/b/y/w"));
		assertTrue(ch.contains("/a/b/y/z"));
	}

	@Test public void testPathToArray(){
		Skb_Transformer<StrBuilder, List<String>> tr = IsPath.PATH_TO_ARRAY_OF_PATHS("/", true);
		List<String> list;

		assertEquals(0, tr.transform(new StrBuilder((String)null)).size());	//null not valid
		assertEquals(0, tr.transform(new StrBuilder("a")).size());			//"a" not valid not valid


		list = tr.transform(new StrBuilder("/"));
		assertEquals(1, list.size());	//special case, '/' == level 0
		assertEquals("/", list.get(0));

		list = tr.transform(new StrBuilder("/a"));
		assertEquals(2, list.size());
		assertEquals("/", list.get(0));
		assertEquals("/a", list.get(1));

		list = tr.transform(new StrBuilder("/a/b"));
		assertEquals(3, list.size());
		assertEquals("/", list.get(0));
		assertEquals("/a", list.get(1));
		assertEquals("/a/b", list.get(2));

		list = tr.transform(new StrBuilder("/a/b/c"));
		assertEquals(4, list.size());
		assertEquals("/", list.get(0));
		assertEquals("/a", list.get(1));
		assertEquals("/a/b", list.get(2));
		assertEquals("/a/b/c", list.get(3));
	}

	@Test public void testJoinPathElements(){
		String separator = "/";
		Skb_Transformer<Pair<Object, Object>, StrBuilder> join = IsPath.JOIN_PATH_ELEMENTS(separator, true, true);
		for(Integer key:this.ptd.forBuild.keySet()){
			Object path = this.ptd.forBuild.get(key).path;
			Object name = this.ptd.forBuild.get(key).name;

			path = (path instanceof String)?StringUtils.replace(path.toString(), this.ptd.sepReplace, separator):path;
			name = (name instanceof String)?StringUtils.replace(name.toString(), this.ptd.sepReplace, separator):name;

			String expected = StringUtils.replace(this.ptd.forBuild.get(key).expected, this.ptd.sepReplace, separator);
			assertEquals(key + " was not equal", expected, join.transform(new Pair<Object, Object>(path, name)).toString());
		}

		//test with different separator
		separator = "#";
		join = IsPath.JOIN_PATH_ELEMENTS(separator, true, true);
		for(Integer key : this.ptd.forBuild.keySet()){
			Object p = this.ptd.forBuild.get(key).path;
			Object n = this.ptd.forBuild.get(key).name;
			//but only if path/name == null 0r instanceof(String), can't test generically for []/iterator/iterable (cause it implies separator changes)
			if((p==null || p instanceof String)&&(n==null || n instanceof String)){
				String path = (p==null)?null:StringUtils.replace(p.toString(), this.ptd.sepReplace, separator);
				String name = (n==null)?null:StringUtils.replace(n.toString(), this.ptd.sepReplace, separator);

				String expected=StringUtils.replace(this.ptd.forBuild.get(key).expected, this.ptd.sepReplace, separator);

				StrBuilder act = join.transform(new Pair<Object, Object>(path, name));
				String actual = (act!=null)?act.toString():null;
				assertEquals(key + " was not equal", expected, actual);
			}
		}
	}

	@Test public void testForMaxDepth(){
		String separator = "/";
		Skb_Transformer<Pair<Object, Object>, StrBuilder> join = IsPath.JOIN_PATH_ELEMENTS(separator, true, true);

		assertEquals(0, IsPath.forMaxDepth(join.transform(new Pair<Object, Object>(null, '/')), -2, separator).size());	//to not <-1
		assertEquals(0, IsPath.forMaxDepth(join.transform(new Pair<Object, Object>(null, null)), 1, separator).size());	//no valid FQPN

		assertEquals(0, IsPath.forMaxDepth(join.transform(new Pair<Object, Object>("/l1/l2", "l3")), 0, separator).size());
		assertEquals(0, IsPath.forMaxDepth(join.transform(new Pair<Object, Object>("/l1/l2", "l3")), 1, separator).size());
		assertEquals(0, IsPath.forMaxDepth(join.transform(new Pair<Object, Object>("/l1/l2", "l3")), 2, separator).size());

		assertEquals(new StrBuilder("/l1/l2/l3"), IsPath.forMaxDepth(join.transform(new Pair<Object, Object>("/l1/l2", "l3")), 3, separator));
		assertEquals(new StrBuilder("/l1/l2/l3"), IsPath.forMaxDepth(join.transform(new Pair<Object, Object>("/l1/l2", "l3")), 4, separator));
		assertEquals(new StrBuilder("/l1/l2/l3"), IsPath.forMaxDepth(join.transform(new Pair<Object, Object>("/l1/l2", "l3")), -1, separator));
	}


	@Test public void testMe(){
		Map<String, String> map = new HashMap<String, String>();

		map.put("/", null);
		map.put("/a", "level1");
		map.put("/a/b", "level2");
		map.put("/a/b/c", "level3");
		map.put("/a/b/c/d", "level4");
		map.put("/a/b/c/e", "level4");
		map.put("/a/b/y", "level3");
		map.put("/a/b/y/w", "level4");
		map.put("/a/b/y/z", "level4");

		StopWatch sw = new StopWatch();

		sw.reset();
		sw.start();
		Collection<String> actual = IsPath.GET_SUB_PATHS("/", "/a/b", map.keySet());
		sw.stop();
		System.err.println(sw);

		System.err.println(new TreeSet<String>(actual));
	}
}
