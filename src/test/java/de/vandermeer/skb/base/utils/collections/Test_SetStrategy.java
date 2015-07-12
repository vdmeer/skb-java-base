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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import de.vandermeer.skb.base.utils.collections.SetStrategy;

/**
 * Tests for set strategy.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150712 (12-Jul-15) for Java 1.8
 */
public class Test_SetStrategy {
	@Test public void test(){
		for(SetStrategy ss : SetStrategy.values()){
			this.testOp(ss);
		}
	}

	private void testOp(SetStrategy strategy){
		Set<String> stringsIn = new HashSet<String>();
		stringsIn.add("three");
		stringsIn.add("two");
		stringsIn.add("one");

		Set<Integer> intsIn = new HashSet<Integer>();
		intsIn.add(3);
		intsIn.add(2);
		intsIn.add(1);

		Set<String> strSimple = strategy.get(strSimple=null);
		Set<String> strMore = strategy.get(stringsIn);

		Set<Integer> intSimple = strategy.get(intSimple=null);
		Set<Integer> intMore = strategy.get(intsIn);

		assertNotNull(strMore);
		assertEquals(3, strMore.size());

		assertNotNull(intMore);
		assertEquals(3, intMore.size());

		switch(strategy){
			case DEFAULT:
			case HASH_SET:
				assertTrue(strSimple instanceof HashSet);
				assertTrue(strMore instanceof HashSet);
				assertTrue(intSimple instanceof HashSet);
				assertTrue(intMore instanceof HashSet);
				break;
			case LINKED_HASH_SET:
				assertTrue(strSimple instanceof LinkedHashSet);
				assertTrue(strMore instanceof LinkedHashSet);
				assertTrue(intSimple instanceof LinkedHashSet);
				assertTrue(intMore instanceof LinkedHashSet);
				break;
//			case TREE_SET:
//				assertTrue(strSimple instanceof TreeSet);
//				assertTrue(strMore instanceof TreeSet);
//				assertTrue(intSimple instanceof TreeSet);
//				assertTrue(intMore instanceof TreeSet);
//				break;
			default:
				assertTrue("test does not (yet) support this set type <"+strategy+">", false);
		}
	}
}
