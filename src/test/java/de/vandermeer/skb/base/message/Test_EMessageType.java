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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for {@link EMessageType}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.7 (was in the message tests before)
 */
public class Test_EMessageType {

	@Test public void testMessageEnum(){
		//check the number (increasing), toString (as name().tolower()) and name of the logger for each of the enumerates
		EMessageType[] values = EMessageType.values();
		for(int i=0; i<values.length; i++){
			assertTrue(values[i].getNumber()==i);
			assertEquals(values[i].name().toLowerCase(), values[i].toString());
//			String loggerAdd = StringUtils.capitalize(values[i].name().toLowerCase());
//			assertEquals("SKBLogger" + loggerAdd, values[i].getLoggerName());
		}
	}

}
