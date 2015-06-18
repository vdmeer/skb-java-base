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

package de.vandermeer.skb.base.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.vandermeer.skb.base.utils.Skb_ClassUtils;

public class Test_Skb_ClassUtils {

	interface intA {}
	interface intB {}

	class clsA implements intA {}
	class clsB implements intB {}
	class clsAB implements intA, intB {}

	class clsCA extends clsA {}
	class clsDB extends clsB {}

	@Rule public ExpectedException exception = ExpectedException.none();

	@Test public void testIsSubClassOf(){
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(intA.class).test(new clsA()));
		assertFalse(Skb_ClassUtils.IS_SUBCLASS_OF(intA.class).test(new clsB()));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(intB.class).test(new clsB()));
		assertFalse(Skb_ClassUtils.IS_SUBCLASS_OF(intB.class).test(new clsA()));

		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(new clsA()).test(new clsCA()));
		assertFalse(Skb_ClassUtils.IS_SUBCLASS_OF(new clsB()).test(new clsCA()));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(new clsB()).test(new clsDB()));
		assertFalse(Skb_ClassUtils.IS_SUBCLASS_OF(new clsB()).test(new clsCA()));

		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(intA.class).test(new clsCA()));
		assertFalse(Skb_ClassUtils.IS_SUBCLASS_OF(intB.class).test(new clsCA()));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(intB.class).test(new clsDB()));
		assertFalse(Skb_ClassUtils.IS_SUBCLASS_OF(intA.class).test(new clsDB()));

		String s=new String();
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(String.class).test(s));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(String.class).test(String.class));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(s).test(String.class));
		assertTrue(Skb_ClassUtils.IS_SUBCLASS_OF(new String()).test(s));
	}

	@Test public void testIsSuperClassOf(){
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsA()).test(intA.class));
		assertFalse(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsB()).test(intA.class));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsB()).test(intB.class));
		assertFalse(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsA()).test(intB.class));

		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsCA()).test(new clsA()));
		assertFalse(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsCA()).test(new clsB()));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsDB()).test(new clsB()));
		assertFalse(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsCA()).test(new clsB()));

		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsCA()).test(intA.class));
		assertFalse(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsCA()).test(intB.class));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsDB()).test(intB.class));
		assertFalse(Skb_ClassUtils.IS_SUPERCLASS_OF(new clsDB()).test(intA.class));

		String s=new String();
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(String.class).test(s));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(String.class).test(String.class));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(s).test(String.class));
		assertTrue(Skb_ClassUtils.IS_SUPERCLASS_OF(new String()).test(s));
	}

	@Test public void testInstanceOf(){
		assertTrue(Skb_ClassUtils.INSTANCE_OF(intA.class).test(new clsA()));
		assertTrue(Skb_ClassUtils.INSTANCE_OF(intB.class).test(new clsB()));
		assertTrue(Skb_ClassUtils.INSTANCE_OF(intA.class).test(new clsCA()));
		assertTrue(Skb_ClassUtils.INSTANCE_OF(intB.class).test(new clsDB()));
		assertTrue(Skb_ClassUtils.INSTANCE_OF(new clsA()).test(new clsCA()));
		assertTrue(Skb_ClassUtils.INSTANCE_OF(new clsB()).test(new clsDB()));

		assertFalse(Skb_ClassUtils.INSTANCE_OF(new clsCA()).test(new clsA()));
		assertFalse(Skb_ClassUtils.INSTANCE_OF(new clsCA()).test(new clsB()));
		assertFalse(Skb_ClassUtils.INSTANCE_OF(new clsDB()).test(new clsB()));
		assertFalse(Skb_ClassUtils.INSTANCE_OF(new clsDB()).test(new clsA()));
	}
}
