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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Tests for examples of message objects.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.7 (was in the message tests before)
 */
public class Test_Examples {

	@Test public void testJavadocExample(){
		Message5WH msg = new Message5WH_Builder()
				.setWho("from "+this.getClass().getSimpleName())
				.addWhat("showing a test message")
				.setWhen("noon")
				.setWhere("the class API documentation", 0, 0)
				.addWhy("as a demo")
				.addHow("added to the class JavaDoc")
				.setReporter("The Author")
				.setType(EMessageType.INFO)
				.build()
		;
		assertNotNull(msg);
//		System.err.println(msg.render());
//		System.err.println(msg);
	}
}
