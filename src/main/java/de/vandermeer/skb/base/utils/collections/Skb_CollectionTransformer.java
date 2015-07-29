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
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Transformation methods for collections.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.0-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.5
 */
public abstract class Skb_CollectionTransformer {

	/**
	 * Converts a collection into a set and transforms each element of the collection.
	 * @param <T1> type for the left site of the transformation (source)
	 * @param <T2> type for the right site of the transformation (target)
	 * @param <T3> type for the input collection
	 * @param input collection that should be converted
	 * @param transformer object that performs the transformation
	 * @param clazz type of the returned set
	 * @param strategy set strategy
	 * @return an empty set of type clazz or a set of type clazz with transformed objects from the input collection
	 */
	public static final <T1, T2, T3 extends T1> Set<T2> COLLECTION_TO_SET(final Collection<T3> input, final Skb_Transformer<T1, T2> transformer, Class<T2> clazz, SetStrategy strategy){
		Set<T2> ret = strategy.get(clazz);
		if(input!=null){
			for(T1 t1 : input){
				ret.add(transformer.transform(t1));
			}
		}
		
		return ret;
	}

	/**
	 * Converts a collection into a list and transforms each element of the collection.
	 * @param <T1> type for the left site of the transformation (source)
	 * @param <T2> type for the right site of the transformation (target)
	 * @param <T3> type for the input collection
	 * @param input collection that should be converted
	 * @param transformer object that performs the transformation
	 * @param clazz type of the returned list
	 * @param strategy list strategy
	 * @return an empty list of type clazz or a list of type clazz with transformed objects from the input collection
	 */
	public static final <T1, T2, T3 extends T1> List<T2> COLLECTION_TO_LIST(final Collection<T3> input, final Skb_Transformer<T1, T2> transformer, Class<T2> clazz, ListStrategy strategy){
		List<T2> ret = strategy.get(clazz);
		if(input!=null){
			for(T1 t1 : input){
				ret.add(transformer.transform(t1));
			}
		}
		
		return ret;
	}

	/**
	 * Converts a collection into a deque and transforms each element of the collection.
	 * @param <T1> type for the left site of the transformation (source)
	 * @param <T2> type for the right site of the transformation (target)
	 * @param <T3> type for the input collection
	 * @param input collection that should be converted
	 * @param transformer object that performs the transformation
	 * @param clazz type of the returned deque
	 * @param strategy deque strategy
	 * @return an empty deque of type clazz or a deque of type clazz with transformed objects from the input collection
	 */
	public static final <T1, T2, T3 extends T1> Deque<T2> COLLECTION_TO_DEQUE(Collection<T3> input, Skb_Transformer<T1, T2> transformer, Class<T2> clazz, DequeStrategy strategy) {
		Deque<T2> ret = strategy.get(clazz);
		if(input!=null){
			for(T1 t1 : input){
				ret.add(transformer.transform(t1));
			}
		}
		
		return ret;
	}

	/**
	 * Converts a collection into a sorted set and transforms each element of the collection.
	 * @param <T1> type for the left site of the transformation (source)
	 * @param <T2> type for the right site of the transformation (target)
	 * @param <T3> type for the input collection
	 * @param input collection that should be converted
	 * @param transformer object that performs the transformation
	 * @param clazz type of the returned sorted set
	 * @param strategy sorted set strategy
	 * @return an empty sorted set of type clazz or a sorted set of type clazz with transformed objects from the input collection
	 */
	public static final <T1, T2 extends Comparable<T2>, T3 extends T1> SortedSet<T2> COLLECTION_TO_SORTEDSET(final Collection<T3> input, final Skb_Transformer<T1, T2> transformer, Class<T2> clazz, SortedSetStrategy strategy){
		SortedSet<T2> ret = strategy.get(clazz);
		if(input!=null){
			for(T1 t1 : input){
				ret.add(transformer.transform(t1));
			}
		}
		
		return ret;
	}

	/**
	 * Converts a collection into a sorted set and transforms each element of the collection.
	 * @param <T1> type for the left site of the transformation (source)
	 * @param <T2> type for the right site of the transformation (target)
	 * @param <T3> type for the input collection

	 * @param input collection that should be converted
	 * @param transformer object that performs the transformation
	 * @param clazz type of the returned sorted set
	 * @param strategy sorted set strategy
	 * @param comparator a comparator function to be used by the sorted set
	 * @return an empty collection of type clazz or a collection of type clazz with transformed objects from the input collection
	 */

	public static final <T1, T2, T3 extends T1> SortedSet<T2> COLLECTION_TO_SORTEDSET(final Collection<T3> input, final Skb_Transformer<T1, T2> transformer, Class<T2> clazz, SortedSetStrategy strategy, Comparator<T2> comparator){
		SortedSet<T2> ret = strategy.get(clazz, comparator);
		if(input!=null){
			for(T1 t1 : input){
				ret.add(transformer.transform(t1));
			}
		}
		
		return ret;
	}

	/**
	 * Converts a collection into a queue and transforms each element of the collection.
	 * @param <T1> type for the left site of the transformation (source)
	 * @param <T2> type for the right site of the transformation (target)
	 * @param <T3> type for the input collection
	 * @param input collection that should be converted
	 * @param transformer object that performs the transformation
	 * @param clazz type of the returned queue
	 * @param strategy queue strategy
	 * @return an empty queue of type clazz or a queue of type clazz with transformed objects from the input collection
	 */
	public static final <T1, T2, T3 extends T1> Queue<T2> COLLECTION_TO_QUEUE(Collection<T3> input, Skb_Transformer<T1, T2> transformer, Class<T2> clazz, QueueStrategy strategy) {
		Queue<T2> ret = strategy.get(clazz);
		if(input!=null){
			for(T1 t1 : input){
				ret.add(transformer.transform(t1));
			}
		}
		
		return ret;
	}

	/**
	 * Transforms a collection into a textual representation, for instance for debug output
	 * @param coll input collection
	 * @return textual representation of the collection, empty string as default
	 */
	public final static String COLLECTION_TO_TEXT(Collection<?> coll){
		return Skb_CollectionFactory.transformerCollectionToText().transform(coll);
	}

	/**
	 * Transforms a map into a textual representation, for instance for debug output
	 * @param map input collection
	 * @return textual representation of the map, empty string as default
	 */
	public final static String MAP_TO_TEXT(Map<?, ?> map){
		return Skb_CollectionFactory.transformerMapToText().transform(map);
	}
}
