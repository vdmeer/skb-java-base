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

import java.util.Map;

/**
 * Interface for Map strategies.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.5
 */
public interface IsMapStrategy extends IsMCStrategy {

	/**
	 * Returns a new map for the used strategy (enumerate) for the given class T as map &lt;String, T&gt;.
	 * @param <T> type for the map values
	 * @param T class to be used for initializing the map
	 * @return new map for the given class
	 */
	<T> Map<String, T> get(Class<?> T);

	/**
	 * Returns a new map view for the given map of type &lt;String, T&gt;.
	 * @param <T> type for the map values
	 * @param map input map
	 * @return new map view
	 */
	<T> Map<String, T> get(Map<String, T> map);

}
