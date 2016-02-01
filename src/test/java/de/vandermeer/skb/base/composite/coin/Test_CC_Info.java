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
import de.vandermeer.skb.base.message.E_MessageType;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * Tests for {@link CC_Info}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_CC_Info {

	@Test public void testInit(){
		CC_Info info = new CC_Info();

		assertTrue(info instanceof Com_Coin);
		assertTrue(info instanceof Com_Leaf);
		assertTrue(info instanceof Com_Node);

		assertEquals(Com_CoinType.SO_INFO, info.getType());
		assertEquals(0, info.size());
	}

	@Test public void testAdd(){
		CC_Info info = new CC_Info();

		Message5WH m = new Message5WH_Builder().setType(E_MessageType.ERROR).build();

		info.add(m);
		assertEquals(E_MessageType.INFO, info.getList().get(0).getType());	//type automatically changed to warning

		assertEquals(1, info.getList().size());		//size 1
		assertEquals(1, info.size());				//size 1
		info.add(m);
		assertEquals(2, info.getList().size());		//size now 2
		assertEquals(2, info.size());				//size now 2
	}

}
