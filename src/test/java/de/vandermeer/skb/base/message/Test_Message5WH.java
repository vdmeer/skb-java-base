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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for {@link Message5WH}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 */
public class Test_Message5WH {

	@Test public void testST(){
		//this tests message.render(), so in turn it also tests message.asST()
		Message5WH m = new Message5WH_Builder().build();

		//initial message is all set to null and ST should be empty string (not null string)
		assertTrue(m.render()!=null);
		assertTrue("".equals(m.render()));
	}

	@Test public void testTypeChange(){
		Message5WH m = new Message5WH_Builder().build();

		//set null means null
		m.changeType(null);
		assertTrue(m.getType()==null);

		// set error means error
		m.changeType(EMessageType.ERROR);
		assertTrue(m.getType()==EMessageType.ERROR);

		//set warning means warning
		m.changeType(EMessageType.WARNING);
		assertTrue(m.getType()==EMessageType.WARNING);

		//set message means message
		m.changeType(EMessageType.INFO);
		assertTrue(m.getType()==EMessageType.INFO);

		//set null means null
		m.changeType(null);
		assertTrue(m.getType()==null);
	}

}
