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
 * Category of objects that represent a value.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsValue<V> extends CategoryIs, CategoryWithValue, HasDescription {
	/**
	 * Returns the value of something.
	 * @return value
	 */
	default V value(){
		return this._value();
	}

	@Override
	V _value();

	@SuppressWarnings("unchecked")
	static <V> IsValue<V> create(final V value, final String description){
		if(value!=null && value instanceof IsValue){
			return (IsValue<V>)value;
		}
		else{
			return new IsValue<V>(){
				@Override public V _value() {return value;}
				@Override public Object getDescription() {return (description==null)?Skb_Defaults.DEFAULT_DESCRIPTION:description;}
				@Override public String toString(){return toLog(IsKey.class);}
			};
		}
	}

	/**
	 * Creates a new value object.
	 * @param value the actual value
	 * @param <V> value type
	 * @return a new value object
	 */
	static <V> IsValue<V> create(V value){
		return IsValue.create(value, null);
	}

	/**
	 * Creates a new value object.
	 * @param <V> value type
	 * @return a new value object
	 */
	static <V> IsValue<V> create(){
		return IsValue.create(null);
	}

}
