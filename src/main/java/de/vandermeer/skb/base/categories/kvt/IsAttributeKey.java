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


/**
 * Category of objects that represent an attribute key.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsAttributeKey extends IsKey_String {

	/**
	 * Returns a new generic {@link IsAttributeKey} object with a preset attribute key (immutable).
	 * @param key the object's key
	 * @param description a description for the new attribute key
	 * @return new {@link IsAttributeKey} object with a set key
	 */
	static IsAttributeKey create(final Object key, final String description){
		if(key!=null && key instanceof IsAttributeKey){
			return (IsAttributeKey)key;
		}
		else{
			return new IsAttributeKey(){
				@Override public String _value() {return (key==null)?Skb_Defaults.DEFAULT_VALUE:key.toString();}
				@Override public Object getDescription() {return (description==null)?Skb_Defaults.DEFAULT_DESCRIPTION:description;}
				@Override public String toString(){return toLog(IsAttributeKey.class);}
			};
		}
	}

	/**
	 * Returns a new generic {@link IsAttributeKey} object with a preset attribute key and default description (immutable).
	 * @param key the object's key
	 * @return new {@link IsAttributeKey} object with a set key
	 */
	static IsAttributeKey create(Object key){
		return IsAttributeKey.create(key, null);
	}

	/**
	 * Returns a new generic {@link IsAttributeKey} object with a default preset attribute key (immutable).
	 * @return new {@link IsAttributeKey} object with a default key
	 */
	static IsAttributeKey create(){
		return IsAttributeKey.create(null);
	}
}
