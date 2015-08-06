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

package de.vandermeer.skb.base.categories;

import java.util.Comparator;

import de.vandermeer.skb.base.Skb_Defaults;
import de.vandermeer.skb.base.Skb_ToStringStyle;
import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Comes with some value category.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.11-SNAPSHOT build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface CategoryWithValue {

	/**
	 * Returns the value of an IS category.
	 * @return value of an IS category
	 */
	Object _value();

	default String toLog(Class<?> clazz){
		return Skb_ToStringStyle.parentKV(clazz, Skb_Defaults.DefaultImpl.class, this._value()).toString();
	}

	/**
	 * Standard comparator, useful for SortedLists etc
	 */
	Comparator<CategoryWithValue> comparator=new Comparator<CategoryWithValue>() {
		@Override public int compare(CategoryWithValue to1, CategoryWithValue to2){
			String s1 = (to1==null||to1._value()==null)?"":to1._value().toString();
			String s2 = (to2==null||to2._value()==null)?"":to2._value().toString();
			return s1.compareTo(s2);
		}
	};

	/**
	 * Returns a transformer that takes an object and returns its value as an object if the object is of type {@link CategoryWithValue}.
	 * @return object to object transformer
	 */
	static Skb_Transformer<Object, Object> CAT_TO_VALUE(){
		return new Skb_Transformer<Object, Object>(){
			@Override public Object transform(Object o){
				if(o instanceof CategoryWithValue){
					return (((CategoryWithValue)o)._value());
				}
				return null;
			}
		};
	}

	/**
	 * Returns the value a given object as object using the {@link CategoryWithValue#CAT_TO_VALUE()} transformer.
	 * @param in object
	 * @return value object or null
	 */
	static Object GET_VALUE(Object in){
		return CategoryWithValue.CAT_TO_VALUE().transform(in);
	}

	/**
	 * Returns a transformer that takes an object and returns its value as a string if the object is of type {@link CategoryWithValue}.
	 * @return object to string transformer
	 */
	static Skb_Transformer<Object, String> CAT_TO_VALUESTRING(){
		return new Skb_Transformer<Object, String>(){
			@Override public String transform(Object o){
				Object ret=CategoryWithValue.CAT_TO_VALUE().transform(o);
				if(ret!=null){
					return ret.toString();
				}
				return null;
			}
		};
	}

	/**
	 * Returns the value a given object as string using the {@link CategoryWithValue#CAT_TO_VALUESTRING()} transformer.
	 * @param in object
	 * @return value string or null
	 */
	static String GET_VALUESTRING(Object in){
		return CategoryWithValue.CAT_TO_VALUESTRING().transform(in);
	}

}
