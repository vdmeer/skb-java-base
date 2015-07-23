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

package de.vandermeer.skb.base;

import org.apache.commons.lang3.StringUtils;

/**
 * Default SKB assertions.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.6
 */
public class Skb_Assertions {

	/**
	 * Asserts that a value is not null and throws an {@link IllegalArgumentException} if it is null.
	 * @param para parameter to test
	 * @param message a message in case the parameter is null, will be used for throwing the exception
	 * @param<P> generic type of the parameter to check
	 */
	public static <P> void argumentNotNull(P para, String message) {
		if(para==null){
			throw new IllegalArgumentException(message);
		}
	}

	public static void stringNotEMpty(String s, String message) {
		if(StringUtils.isEmpty(s)){
			throw new IllegalArgumentException(message);
		}
	}
}

