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
import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.message.EMessageType;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * Tests for SOWarning.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_SOError {

	@Test public void testInit(){
		CC_Error err = new CC_Error();

		assertTrue(err instanceof Com_Coin);
		assertTrue(err instanceof Com_Leaf);
		assertTrue(err instanceof Com_Node);

		assertEquals(Com_CoinType.SO_ERROR, err.getType());
		assertEquals(0, err.size());
	}

	@Test public void testAdd(){
		CC_Error err = new CC_Error();

		Message5WH m = new Message5WH_Builder().setType(EMessageType.WARNING).build();

		err.add(m);
		assertEquals(EMessageType.ERROR, err.getList().get(0).getType());	//type automatically changed to error

		err = new CC_Error(m);
		assertEquals(EMessageType.ERROR, err.getList().get(0).getType());	//type automatically changed to error

		m.changeType(EMessageType.ERROR);
		err = new CC_Error(m);
		assertEquals(EMessageType.ERROR, err.getList().get(0).getType());	//type not changed

		assertEquals(1, err.getList().size());		//size 1
		assertEquals(1, err.size());				//size 1
		err.add(m);
		assertEquals(2, err.getList().size());		//size now 2
		assertEquals(2, err.size());				//size now 2
	}
}
