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

package de.vandermeer.skb.base.info;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

import de.vandermeer.skb.base.info.PropertyFileLoader;

/**
 * Tests for {@link PropertyFileLoader}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.7
 */
public class Test_PropertyFileLoader {

	@Test
	public void testLoadFile(){
		String fn = "src/test/resources/de/vandermeer/skb/base/info/loaders/test.properties";
		PropertyFileLoader pfl = new PropertyFileLoader(fn);
		Properties p = pfl.load();
		assertEquals(0, pfl.getLoadErrors().size());
		this.assertProperties(p);
	}

	@Test
	public void testLoadResource(){
		String fn = "de/vandermeer/skb/base/info/loaders/test.properties";
		PropertyFileLoader pfl = new PropertyFileLoader(fn);
		Properties p = pfl.load();
		assertEquals(0, pfl.getLoadErrors().size());
		this.assertProperties(p);
	}

	public void assertProperties(Properties p){
		assertEquals(3, p.keySet().size());

		assertTrue(p.containsKey("test1"));
		assertTrue("1".equals(p.get("test1")));

		assertTrue(p.containsKey("test1.test2"));
		assertTrue("1 2".equals(p.get("test1.test2")));

		assertTrue(p.containsKey("test1.test2.test3"));
		assertTrue("1 2 3".equals(p.get("test1.test2.test3")));
	}

}
