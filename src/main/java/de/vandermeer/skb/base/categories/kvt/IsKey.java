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

package de.vandermeer.skb.base.categories.kvt;

import de.vandermeer.skb.base.Skb_Defaults;
import de.vandermeer.skb.base.categories.CategoryIs;
import de.vandermeer.skb.base.categories.CategoryWithValue;
import de.vandermeer.skb.base.categories.HasDescription;

/**
 * Category of objects that represent a key.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.11-SNAPSHOT build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsKey<K> extends CategoryIs, CategoryWithValue, HasDescription {
	/**
	 * Returns the key
	 * @return key
	 */
	default K key(){
		return this._value();
	}

	@Override
	K _value();

	/**
	 * Returns a new generic {@link IsKey} object with a preset key (immutable).
	 * @param <K> type of the key value
	 * @param key the object's key
	 * @param description a description for the new key
	 * @return new {@link IsKey} object with a set key
	 */
	@SuppressWarnings("unchecked")
	static <K> IsKey<K> create(final K key, final String description){
		if(key!=null && key instanceof IsKey){
			return (IsKey<K>)key;
		}
		else{
			return new IsKey<K>(){
				@Override public K _value() {return key;}
				@Override public Object getDescription() {return (description==null)?Skb_Defaults.DEFAULT_DESCRIPTION:description;}
				@Override public String toString(){return toLog(IsKey.class);}
			};
		}
	}

	/**
	 * Returns a new generic {@link IsKey} object with a pre-set key and default description (immutable).
	 * @param <K> type of the key value
	 * @param key the object's key
	 * @return new {@link IsKey} object with a set key
	 */
	static <K> IsKey<K> create(K key){
		return IsKey.create(key, null);
	}

	/**
	 * Returns a new generic {@link IsKey} object with a default preset key (immutable).
	 * @param <K> type of the key value
	 * @return new {@link IsKey} object with a default key
	 */
	static <K> IsKey<K> create(){
		return IsKey.create(null);
	}
}
