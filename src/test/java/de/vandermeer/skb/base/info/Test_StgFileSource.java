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

import static org.junit.Assert.*;

import org.junit.Test;

import de.vandermeer.skb.base.info.StgFileSource;

/**
 * Tests for {@link StgFileSource}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.7
 */
public class Test_StgFileSource {

	@Test
	public void testStg(){
		StgFileSource fsn;

		//valid file but no stg file extension is an error
		fsn = new StgFileSource("de/vandermeer/skb/base/info/loaders/test.properties");
		assertEquals(1, fsn.getInitError().getErrorMessages().size());
		assertFalse(fsn.isValid());

		//valid file and valid stg file with valid extension
		fsn = new StgFileSource("de/vandermeer/skb/base/utils/test-simple.stg");
		assertEquals(0, fsn.getInitError().getErrorMessages().size());
		assertTrue(fsn.isValid());
	}

}
