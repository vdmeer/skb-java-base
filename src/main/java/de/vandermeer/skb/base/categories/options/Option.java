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

package de.vandermeer.skb.base.categories.options;

import de.vandermeer.skb.base.Skb_Defaults;
import de.vandermeer.skb.base.categories.HasDescription;
import de.vandermeer.skb.base.categories.kvt.IsKey;
import de.vandermeer.skb.base.categories.kvt.IsValue;
import de.vandermeer.skb.base.categories.kvt.KeyValueType;
import de.vandermeer.skb.base.utils.Skb_ClassUtils;

/**
 * An option interface.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 150910 (10-Sep-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface Option<V> extends HasDescription, KeyValueType<String, V, String> {

	/**
	 * Returns a log representation of the option
	 * @param clazz class for logging
	 * @return a log representation of the option
	 */
	default String toLog(Class<?> clazz){
		return Skb_ClassUtils.kv(clazz, getKey()._value(), getValue()._value(), getDescription()).toString();
	}

	/**
	 * Creates a new option.
	 * @param key option key
	 * @param value option value
	 * @param type option type
	 * @param description option description
	 * @param <V> option value
	 * @return new option
	 */
	static <V> Option<V> create(final IsKey<String> key, final IsValue<V> value, final OptionType type, final String description){
		return new Option<V>(){
			@Override public IsKey<String> getKey() {return key;}
			@Override public OptionType getValueType() {return type;}
			@Override public IsValue<V> getValue() {return value;}
			@Override public String toString() {return toLog(Option.class);}
			@Override public Object getDescription() {return (description==null)?Skb_Defaults.DEFAULT_DESCRIPTION:description;}
		};
	}

	/**
	 * Creates a new option.
	 * @param key option key
	 * @param value option value
	 * @param description option description
	 * @param <V> option value
	 * @return new option
	 */
	static <V> Option<V> create(final String key, V value, String description){
		OptionType type = null;
		if(value.getClass().equals(Boolean.class)){
			type = OptionType.BOOLEAN;
		}
		else if(value.getClass().equals(Character.class)){
			type = OptionType.CHARACHTER;
		}
		else if(value.getClass().equals(String.class)){
			type = OptionType.STRING;
		}
		else if(value.getClass().equals(Double.class)){
			type = OptionType.DOUBLE;
		}
		else if(value.getClass().equals(Integer.class)){
			type = OptionType.INTEGER;
		}
		else if(new char[]{}.getClass().isAssignableFrom(value.getClass())){
			type = OptionType.CHARACTER_ARRAY;
		}
		else{
			throw new RuntimeException("unknown type for option");
		}
		return Option.create(IsKey.create(key), IsValue.create(value), type, description);
	}

	/**
	 * Creates a new option.
	 * @param key option key
	 * @param value option value
	 * @param <V> option value
	 * @return new option
	 */
	static <V> Option<V> create(final String key, V value){
		return Option.create(key, value, null);
	}

}
