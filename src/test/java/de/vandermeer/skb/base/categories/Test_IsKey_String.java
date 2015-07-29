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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.vandermeer.skb.base.Skb_Defaults;
import de.vandermeer.skb.base.Skb_Transformer;
import de.vandermeer.skb.base.categories.kvt.IsKey;
import de.vandermeer.skb.base.categories.kvt.IsKey_String;

/**
 * Tests for {@link IsKey}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class Test_IsKey_String {

	@Test public void testNewKey(){
		IsKey_String key;

		key = IsKey_String.create();
		assertNotNull(key);
		assertTrue(key instanceof IsKey_String);
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key.key());
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key._value());
		assertEquals(Skb_Defaults.DEFAULT_DESCRIPTION, key.getDescription());
		assertEquals("IsKey_String(DefaultImpl): " + Skb_Defaults.DEFAULT_VALUE, key.toString());

		key = IsKey_String.create(null);
		assertNotNull(key);
		assertTrue(key instanceof IsKey_String);
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key.key());
		assertEquals(Skb_Defaults.DEFAULT_VALUE, key._value());
		assertEquals(Skb_Defaults.DEFAULT_DESCRIPTION, key.getDescription());
		assertEquals("IsKey_String(DefaultImpl): " + Skb_Defaults.DEFAULT_VALUE, key.toString());

		key = IsKey_String.create("deadbeef");
		assertNotNull(key);
		assertTrue(key instanceof IsKey_String);
		assertEquals("deadbeef", key.key());
		assertEquals("deadbeef", key._value());
		assertEquals(Skb_Defaults.DEFAULT_DESCRIPTION, key.getDescription());
		assertEquals("IsKey_String(DefaultImpl): deadbeef", key.toString());
	}

	@Test public void test_Object2KeyValue(){
		Skb_Transformer<Object, String> tf=CategoryWithValue.CAT_TO_VALUESTRING();
		assertNull(tf.transform(null));
	}

	@Test public void test_key2Value(){
		assertNull(CategoryWithValue.GET_VALUESTRING(null));
	}
}
