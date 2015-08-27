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

package de.vandermeer.skb.base.utils.collections;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import de.vandermeer.skb.base.utils.collections.MapStrategy;

/**
 * Tests for {@link MapStrategy}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.4 build 150827 (27-Aug-15) for Java 1.8
 */
public class Test_MapStrategy {

	@Test public void test(){
		this.testOp(MapStrategy.HASH_MAP);
		this.testOp(MapStrategy.LINKED_HASH_MAP);
		this.testOp(MapStrategy.TREE_MAP);

	}

	private void testOp(MapStrategy m){
		Map<String, String> strings = m.get(String.class);
		Map<String, Integer> ints = m.get(Integer.class);

		switch(m){
			case HASH_MAP:
				assertTrue(strings instanceof HashMap);
				assertTrue(ints instanceof HashMap);
				break;
			case LINKED_HASH_MAP:
				assertTrue(strings instanceof LinkedHashMap);
				assertTrue(ints instanceof LinkedHashMap);
				break;
			case TREE_MAP:
				assertTrue(strings instanceof TreeMap);
				assertTrue(ints instanceof TreeMap);
				break;
			default:
				assertTrue("test does not (yet) support this map type", false);
		}
	}

}
