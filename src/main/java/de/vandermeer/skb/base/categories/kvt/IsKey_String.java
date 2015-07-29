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
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsKey_String extends CategoryIs, CategoryWithValue, HasDescription {
	/**
	 * Returns the key
	 * @return key
	 */
	default String key(){
		return this._value();
	}

	@Override
	String _value();

	/**
	 * Returns a new generic {@link IsKey_String} object with a preset key (immutable).
	 * @param key the object's key
	 * @param description a description for the new key
	 * @return new {@link IsKey_String} object with a set key
	 */
	static IsKey_String create(final Object key, final String description){
		if(key!=null && key instanceof IsKey_String){
			return (IsKey_String)key;
		}
		else{
			return new IsKey_String(){
				@Override public String _value() {return (key==null)?Skb_Defaults.DEFAULT_VALUE:key.toString();}
				@Override public Object getDescription() {return (description==null)?Skb_Defaults.DEFAULT_DESCRIPTION:description;}
				@Override public String toString(){return toLog(IsKey_String.class);}
			};
		}
	}

	/**
	 * Returns a new generic {@link IsKey_String} object with a pre-set key and default description (immutable).
	 * @param key the object's key
	 * @return new {@link IsKey_String} object with a set key
	 */
	static IsKey_String create(Object key){
		return IsKey_String.create(key, null);
	}

	/**
	 * Returns a new generic {@link IsKey_String} object with a default preset key (immutable).
	 * @return new {@link IsKey_String} object with a default key
	 */
	static IsKey_String create(){
		return IsKey_String.create(null);
	}
}
