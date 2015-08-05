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
import java.util.Comparator;
import java.util.SortedSet;

/**
 * Interface for Sorted Set strategies.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.10 build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.5
 */
public interface IsSortedSetStrategy extends IsMCStrategy {

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
	 * @param <T> type of the set and the comparable
	 * @param collection input collection
	 * @return new collection
	 */
	<T extends Comparable<T>> SortedSet<T> get(Collection<T> collection);

	/**
	 * Returns a new collection for the given collection.
	 * @param <T> type of the set and the collection
	 * @param collection input collection
	 * @param comparator comparator for objects
	 * @return new collection
	 */
	<T> SortedSet<T> get(Collection<T> collection, Comparator<T> comparator);

	/**
	 * Returns a new collection of requested type.
	 * @param <T> type of the set and the comparable
	 * @param T class for the type
	 * @return new collection
	 */
	<T extends Comparable<T>> SortedSet<T> get(Class<T> T);

	/**
	 * Returns a new collection of requested type.
	 * @param <T> type for collection
	 * @param T class for the type
	 * @param comparator comparator for objects
	 * @return new collection
	 */
	<T> SortedSet<T> get(Class<T> T, Comparator<T> comparator);
}
