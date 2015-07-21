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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import de.vandermeer.skb.base.utils.collections.Skb_CollectionTransformer;

/**
 * Tests for strategy to string transformations.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8-SNAPSHOT build 150721 (21-Jul-15) for Java 1.8
 */
public class Test_ToString {

	@Test public void testList(){
		List<String> l = new ArrayList<String>();
		l.add("one");
		l.add("two");
		l.add("three");

		String s = Skb_CollectionTransformer.COLLECTION_TO_TEXT(l);

		System.out.println(s);
	}

	@Test public void testMap(){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("eins", "one");
		m.put("zwei", "two");
		m.put("drei", "three");

		String s = Skb_CollectionTransformer.MAP_TO_TEXT(m);
		System.out.println(s);

		m.clear();
		m.put("one", Arrays.asList(new String[]{"1", "2", "3"}));
		s=Skb_CollectionTransformer.MAP_TO_TEXT(m);
		System.out.println(s);
	}
}
