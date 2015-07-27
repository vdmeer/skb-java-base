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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.junit.Test;

import de.vandermeer.skb.base.utils.collections.ListStrategy;

/**
 * Tests for list strategy.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 */
public class Test_ListStrategy {

	@Test public void test(){
		for(ListStrategy ls:ListStrategy.values()){
			this.testOp(ls);
		}
	}

	private void testOp(ListStrategy strategy){
		List<String> stringsIn = Arrays.asList(new String[]{"one", "two", "three"});
		List<Integer> intsIn = Arrays.asList(new Integer[]{1, 2, 3});

		List<String> strSimple = strategy.get(strSimple=null);
		List<String> strMore = strategy.get(stringsIn);

		List<Integer> intSimple = strategy.get(intSimple=null);
		List<Integer> intMore = strategy.get(intsIn);

		assertNotNull(strMore);
		assertEquals(3, strMore.size());

		assertNotNull(intMore);
		assertEquals(3, intMore.size());

		switch(strategy){
			case DEFAULT:
			case ARRAY_LIST:
				assertTrue(strSimple instanceof ArrayList);
				assertTrue(strMore instanceof ArrayList);
				assertTrue(intSimple instanceof ArrayList);
				assertTrue(intMore instanceof ArrayList);
				break;
			case LINKED_LIST:
				assertTrue(strSimple instanceof LinkedList);
				assertTrue(strMore instanceof LinkedList);
				assertTrue(intSimple instanceof LinkedList);
				assertTrue(intMore instanceof LinkedList);
				break;
			case STACK:
				assertTrue(strSimple instanceof Stack);
				assertTrue(strMore instanceof Stack);
				assertTrue(intSimple instanceof Stack);
				assertTrue(intMore instanceof Stack);
				break;
			case VECTOR:
				assertTrue(strSimple instanceof Vector);
				assertTrue(strMore instanceof Vector);
				assertTrue(intSimple instanceof Vector);
				assertTrue(intMore instanceof Vector);
				break;
			default:
				assertTrue("test does not (yet) support this list type <"+strategy+">", false);
		}
	}
}
