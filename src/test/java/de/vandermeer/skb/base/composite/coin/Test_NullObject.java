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

package de.vandermeer.skb.base.composite.coin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.vandermeer.skb.base.composite.Com_Coin;
import de.vandermeer.skb.base.composite.Com_CoinType;
import de.vandermeer.skb.base.composite.Com_Leaf;
import de.vandermeer.skb.base.composite.Com_Node;
import de.vandermeer.skb.base.composite.coin.NONode;
import de.vandermeer.skb.base.composite.coin.NONone;
import de.vandermeer.skb.base.composite.coin.NONull;
import de.vandermeer.skb.base.composite.coin.NOSuccess;

/**
 * Tests for {@link NONull} objects: {@link NONode}, {@link NONone}, {@link NOSuccess}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_NullObject {

	@Test public void testSuccess(){
		NOSuccess td1 = NOSuccess.get;

		assertTrue(td1 instanceof Com_Coin);
		assertTrue(td1 instanceof Com_Leaf);
		assertTrue(td1 instanceof Com_Node);

		assertEquals(Com_CoinType.NO_SUCCESS, td1.getType());
		assertEquals("Com_Coin(NOSuccess): IsType(Com_CoinType): NO_SUCCESS", td1.toString());

		NOSuccess td2 = td1.getCopy();
		assertEquals(td1, td2);
	}

	@Test public void testNone(){
		NONone td1 = NONone.get;

		assertTrue(td1 instanceof Com_Coin);
		assertTrue(td1 instanceof Com_Leaf);
		assertTrue(td1 instanceof Com_Node);

		assertEquals(Com_CoinType.NO_NONE, td1.getType());
		assertEquals("Com_Coin(NONone): IsType(Com_CoinType): NO_NONE", td1.toString());

		NONone td2 = td1.getCopy();
		assertEquals(td1, td2);
	}

	@Test public void testNode(){
		NONode td1 = NONode.get;

		assertTrue(td1 instanceof Com_Coin);
		assertTrue(td1 instanceof Com_Leaf);
		assertTrue(td1 instanceof Com_Node);

		assertEquals(Com_CoinType.NO_NODE, td1.getType());
		assertEquals("Com_Coin(NONode): IsType(Com_CoinType): NO_NODE", td1.toString());

		NONode td2 = td1.getCopy();
		assertEquals(td1, td2);
	}

	@Test public void testNull(){
		NONull td1 = NONull.get;

		assertTrue(td1 instanceof Com_Coin);
		assertTrue(td1 instanceof Com_Leaf);
		assertTrue(td1 instanceof Com_Node);

		assertEquals(Com_CoinType.NO_NULL, td1.getType());
		assertEquals("Com_Coin(NONull): IsType(Com_CoinType): NO_NULL", td1.toString());

		NONull td2 = td1.getCopy();
		assertEquals(td1, td2);
	}
}
