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

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Interface for classes that support a render method.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public interface Skb_Renderable {

	/**
	 * Renders an object for output.
	 * @return rendered object
	 */
	public String render();

	/**
	 * A transformer that returns a String if a {@link Readable} object was provided.
	 * @return string of the transformation or null if object was not of type {@link Readable}
	 */
	public static Skb_Transformer<Object, String> OBJECT_TO_RENDERABLE_VALUE(){
		return new Skb_Transformer<Object, String>(){
			@Override public String transform(Object obj) {
				if(obj instanceof Skb_Renderable){
					return ((Skb_Renderable)obj).render();
				}
				return null;
			}
		};
	}

}
