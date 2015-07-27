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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.vandermeer.skb.base.composite.Com_Coin;
import de.vandermeer.skb.base.composite.coin.Abstract_CC;
import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.composite.coin.CC_Warning;
import de.vandermeer.skb.base.utils.Skb_ClassUtils;

/**
 * Tests for Predicates.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
@RunWith(JUnit4.class)
public class Test_Predicates {

	@Test public void testIsSubClassOf(){
		CC_Error serr=new CC_Error();
		CC_Warning swarn=new CC_Warning();
		assertFalse(Skb_ClassUtils.IS_SUBCLASS_OF(serr).test(swarn));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(Com_Coin.class).test(serr));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(Com_Coin.class).test(swarn));

		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(Abstract_CC.class).test(serr));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(Abstract_CC.class).test(swarn));
	}

	@Test public void testIsSuperClassOf(){
		CC_Error serr=new CC_Error();
		CC_Warning swarn=new CC_Warning();
		assertFalse(Skb_ClassUtils.IS_SUPERCLASS_OF(serr).test(swarn));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(serr).test(Com_Coin.class));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(swarn).test(Com_Coin.class));

		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(serr).test(Abstract_CC.class));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(swarn).test(Abstract_CC.class));
	}
}
