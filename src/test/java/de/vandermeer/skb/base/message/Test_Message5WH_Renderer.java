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

package de.vandermeer.skb.base.message;

import org.junit.Test;

/**
 * Tests for {@link Test_Message5WH_Renderer}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.1 build 150814 (14-Aug-15) for Java 1.8
 */
public class Test_Message5WH_Renderer {

	@Test
	public void test_Constructor(){
		Message5WH_Renderer ren;

		ren = new Message5WH_Renderer();
		ren = new Message5WH_Renderer(Message5WH_Renderer.DEFAULT_STG);
		ren = new Message5WH_Renderer(Message5WH_Renderer.DEFAULT_STG_FN);

		ren.stg.show();
	}
}
