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
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Strategies for sorted sets.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 150910 (10-Sep-15) for Java 1.8
 * @since      v0.0.5
 */
public enum SortedSetStrategy implements IsSortedSetStrategy {
	/** Default set, results in a TreeSet */
	DEFAULT,

	/** TreeSet from java&#46;util. */
	TREE_SET,

	/** Concurrent SKip List Set from java&#46;util. */
	CONCURRENT_SKIP_LIST_SET,
	;

	@Override
	public <T extends Comparable<T>> SortedSet<T> get(Collection<T> collection) {
		switch(this){
			case CONCURRENT_SKIP_LIST_SET:
				return new ConcurrentSkipListSet<T>(collection);
			case TREE_SET:
			case DEFAULT:
			default:
				if(collection==null){
					return new TreeSet<T>();
				}
				TreeSet<T> ret = new TreeSet<T>();
				ret.addAll(collection);
				return ret;
		}
	}

	@Override
	public <T> SortedSet<T> get(Collection<T> collection, Comparator<T> comparator) {
		SortedSet<T> ret;
		switch(this){
			case CONCURRENT_SKIP_LIST_SET:
				ret = new ConcurrentSkipListSet<T>(comparator);
				ret.addAll(collection);
				return ret;
			case TREE_SET:
			case DEFAULT:
			default:
				if(collection==null){
					return new TreeSet<T>(comparator);
				}
				ret = new TreeSet<T>();
				ret.addAll(collection);
				return ret;
		}
	}

	@Override
	public <T extends Comparable<T>> SortedSet<T> get(Class<T> T){
		SortedSet<T> ret;
		switch(this){
			case CONCURRENT_SKIP_LIST_SET:
				ret = new ConcurrentSkipListSet<T>();
			case DEFAULT:
			default:
			case TREE_SET:
				ret = new TreeSet<T>();
				break;
		}
		return ret;
	}

	@Override
	public <T> SortedSet<T> get(Class<T> T, Comparator<T> comparator) {
		SortedSet<T> ret;
		switch(this){
			case CONCURRENT_SKIP_LIST_SET:
				ret = new ConcurrentSkipListSet<T>(comparator);
			case DEFAULT:
			default:
			case TREE_SET:
				ret = new TreeSet<T>(comparator);
				break;
		}
		return ret;
	}

	@Override
	public String toString() {
		return this.name();
	}

	@Override
	public boolean isList(){
		return false;
	}

	@Override
	public boolean isSet(){
		return true;
	}

	@Override
	public boolean isQueue(){
		return false;
	}

}
