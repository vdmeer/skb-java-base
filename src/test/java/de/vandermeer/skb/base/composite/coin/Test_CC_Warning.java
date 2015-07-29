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
import de.vandermeer.skb.base.composite.coin.CC_Warning;
import de.vandermeer.skb.base.message.EMessageType;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * Tests for {@link CC_Warning}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_CC_Warning {

	@Test public void testInit(){
		CC_Warning warn = new CC_Warning();

		assertTrue(warn instanceof Com_Coin);
		assertTrue(warn instanceof Com_Leaf);
		assertTrue(warn instanceof Com_Node);

		assertEquals(Com_CoinType.SO_WARNING, warn.getType());
		assertEquals(0, warn.size());
	}

	@Test public void testAdd(){
		CC_Warning warn = new CC_Warning();

		Message5WH m = new Message5WH_Builder().setType(EMessageType.ERROR).build();

		warn.add(m);
		assertEquals(EMessageType.WARNING, warn.getList().get(0).getType());	//type automatically changed to warning

		warn = new CC_Warning(m);
		assertEquals(EMessageType.WARNING, warn.getList().get(0).getType());	//type automatically changed to warning

		m.changeType(EMessageType.WARNING);
		warn = new CC_Warning(m);
		assertEquals(EMessageType.WARNING, warn.getList().get(0).getType());	//type not changed

		assertEquals(1, warn.getList().size());		//size 1
		assertEquals(1, warn.size());				//size 1
		warn.add(m);
		assertEquals(2, warn.getList().size());		//size now 2
		assertEquals(2, warn.size());				//size now 2
	}
}
