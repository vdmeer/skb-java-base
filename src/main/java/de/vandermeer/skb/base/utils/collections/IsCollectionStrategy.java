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

import java.util.Collection;

/**
 * Root interface for collection strategies.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.9-SNAPSHOT build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.5
 */
public interface IsCollectionStrategy extends IsMCStrategy {

	/**
	 * Test if the collection is a list.
	 * @return true if list, false otherwise
	 */
	boolean isList();

	/**
	 * Test if the collection is a set.
	 * @return true if set, false otherwise
	 */
	boolean isSet();

	/**
	 * Test if the collection is a queue.
	 * @return true if queue, false otherwise
	 */
	boolean isQueue();

	/**
	 * Returns a new collection for the given collection.
	 * @param <T> type of the collection
	 * @param collection input collection
	 * @return new collection
	 */
	<T> Collection<T> get(Collection<T> collection);

	/**
	 * Returns a new collection of requested type.
	 * @param <T> type of the collection
	 * @param T class for the collection type, used for initialization
	 * @return new collection
	 */
	<T> Collection<T> get(Class<T> T);

}
