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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CommonToken;
import org.apache.commons.lang3.text.StrBuilder;
import org.junit.Test;

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Tests for Tests for {@link Skb_TextUtils}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.4 build 150827 (27-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-commons before)
 */
public class Test_Skb_TextUtils {

	private String nullValue="<null>";

	private String falseValue="<false>";

	@SuppressWarnings("unchecked")
	@Test public void test_Manyobjects2Strbuilder(){
		Skb_Transformer<Object, String> toText = Skb_Transformer.CHAIN(Skb_Antlr4Utils.ANTLR_TO_TEXT(), Skb_Renderable.OBJECT_TO_RENDERABLE_VALUE(), Skb_TextUtils.TO_STRING());

		Skb_Transformer<Object, StrBuilder> tf = Skb_TextUtils.MANYOBJECTS_TO_STRBUILDER("++", toText);
		List<Object> list = new ArrayList<>();

		assertEquals("", tf.transform((Iterator<Object>)null).toString());
		assertEquals("", tf.transform(list.iterator()).toString());

		CommonToken tk = new CommonToken(0);
		tk.setText("token1");
		list.add(tk);
		assertEquals("token1", tf.transform(list.iterator()).toString());

		list.add(3.1415);
		assertEquals("token1++3.1415", tf.transform(list.iterator()).toString());

		//TODO doesn't test HasValue/IsValue yet
	}

	@Test public void test_Object2Target_General(){
		//most other tests are done in Transformations
		Skb_Transformer<Object, String> toStr = Skb_ObjectUtils.OBJECT_TO_TARGET(String.class, nullValue, falseValue, true);

		//String = ok, <null> (can't be <false>)
		assertEquals("test string", toStr.transform("test string"));
		assertEquals(nullValue, toStr.transform(null));

		//try a set with null, then with a string, then with an object
		Set<Object>set = new HashSet<Object>();
		assertEquals(nullValue, toStr.transform(set));
		set.add(null);
		assertEquals(nullValue, toStr.transform(set));
		set.add("test string2");
		assertEquals("test string2", toStr.transform(set));
		set.clear();
		assertEquals(nullValue, toStr.transform(set));

//		set.add(SetStrategy.HASH_SET);//TODO from refactoring unto utils
//		assertEquals(falseValue, toStr.transform(set));//TODO from refactoring unto utils
	}

}
