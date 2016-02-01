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
import de.vandermeer.skb.base.categories.OfGroup;


/**
 * Category of objects that represent a property key.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
<<<<<<< HEAD
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
=======
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
>>>>>>> dev
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsPropertyKey extends IsKey_String, OfGroup {

	/**
	 * Returns a new generic {@link IsPropertyKey} object with a preset property key (immutable).
	 * @param key the object's key
	 * @param description a description for the new property key
	 * @return new {@link IsPropertyKey} object with a set key
	 */
	static IsPropertyKey create(final Object key, final String description){
		if(key!=null && key instanceof IsPropertyKey){
			return (IsPropertyKey)key;
		}
		else{
			return new IsPropertyKey(){
				@Override public String _value() {return (key==null)?Skb_Defaults.DEFAULT_VALUE:key.toString();}
				@Override public Object getDescription() {return (description==null)?Skb_Defaults.DEFAULT_DESCRIPTION:description;}
				@Override public String toString(){return toLog(IsPropertyKey.class);}
			};
		}
	}

	/**
	 * Returns a new generic {@link IsPropertyKey} object with set key and a default description (immutable).
	 * @param key the object's key
	 * @return new {@link IsPropertyKey} object with a default description
	 */
	static IsPropertyKey create(Object key){
		return IsPropertyKey.create(key, null);
	}

	/**
	 * Returns a new generic {@link IsPropertyKey} object with a default preset property key (immutable).
	 * @return new {@link IsPropertyKey} object with a default key
	 */
	static IsPropertyKey create(){
		return IsPropertyKey.create(null);
	}

}
