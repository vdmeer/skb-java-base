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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Strategies for maps.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.4 build 150827 (27-Aug-15) for Java 1.8
 * @since      v0.0.5
 */
public enum MapStrategy implements IsMapStrategy {
	/** Default map, results in an HashMap */
	DEFAULT,

	/** HashMap from java&#46;util. */
	HASH_MAP,

	/** Hashtable from java&#46;util. */
	HASH_TABLE,

	/** LinkedHashMap from java&#46;util. */
	LINKED_HASH_MAP,

	/** IdentityHashMap from java&#46;util. */
	IDENTITY_HASH_MAP,

	/** ConcurrentHashMap from java&#46;util. */
	CONCURRENT_HASH_MAP,

	/** WeakHashMap from java&#46;util. */
	WEAK_HASH_MAP,

	/** TreeMap from java&#46;util. */
	TREE_MAP,
	;

	/**
	 * Returns a new map for the used strategy (enum) for the given class T as map &lt;String, T&gt;.
	 * @param <T> class to be used for initialising the map
	 * @return new map for the given class
	 */
	@Override
	public <T> Map<String, T> get(Class<?> T) {
		switch(this){
			case LINKED_HASH_MAP:
				return new LinkedHashMap<String, T>();
			case TREE_MAP:
				return new TreeMap<String, T>();
			case HASH_TABLE:
				return new Hashtable<String, T>();
			case IDENTITY_HASH_MAP:
				return new IdentityHashMap<String, T>();
			case WEAK_HASH_MAP:
				return new WeakHashMap<String, T>();
			case CONCURRENT_HASH_MAP:
				return new ConcurrentHashMap<String, T>();
			case HASH_MAP:
			case DEFAULT:
			default:
				return new HashMap<String, T>();
		}
	}

	/**
	 * Returns a new map view for the given map of type &lt;String, T&gt;.
	 * @param <T> type for the map values
	 * @param map input map
	 * @return new map view
	 */
	@Override
	public <T> Map<String, T> get(Map<String, T> map){
		Map<String, T> ret;
		switch(this){
			case LINKED_HASH_MAP:
				ret = new LinkedHashMap<String, T>(map);
				break;
			case TREE_MAP:
				ret = new TreeMap<String, T>(map);
				break;
			case HASH_TABLE:
				ret = new Hashtable<String, T>(map);
				break;
			case IDENTITY_HASH_MAP:
				ret = new IdentityHashMap<String, T>(map);
				break;
			case WEAK_HASH_MAP:
				ret = new WeakHashMap<String, T>(map);
				break;
			case CONCURRENT_HASH_MAP:
				ret = new ConcurrentHashMap<String, T>(map);
				break;
			case HASH_MAP:
			case DEFAULT:
			default:
				ret = new HashMap<String, T>(map);
				break;
		}
		return ret;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
