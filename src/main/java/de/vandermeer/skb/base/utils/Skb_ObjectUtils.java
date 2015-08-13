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

package de.vandermeer.skb.base.utils;

import java.util.Collection;

import de.vandermeer.skb.base.Skb_Transformer;
import de.vandermeer.skb.base.utils.collections.Skb_CollectionUtils;

/**
 * Object converters.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.1 build 150814 (14-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Skb_ObjectUtils {

	/**
	 * Returns a transformer that takes a string and returns a boolean value, or null.
	 * The transformer returns true if the string is "true" or "on" and false if the string is "false" or "off" (all string are tested ignoring case).
	 * @return transformer that takes an object and returns a boolean
	 */
	public static final Skb_Transformer<String, Boolean> OBJECT_TO_BOOLEAN(){
		return new Skb_Transformer<String, Boolean>(){
			@Override public Boolean transform(String s){
				if(s!=null){
					if("true".equalsIgnoreCase(s) || "on".equalsIgnoreCase(s)){
						return new Boolean(true);
					}
					if("false".equalsIgnoreCase(s) || "off".equalsIgnoreCase(s)){
						return new Boolean(false);
					}
				}
				return null;
			}
		};
	}

	/**
	 * Type safe transformation from Object to target class, with optional special processing for Object[] and Collections.
	 * The conversion is done in the following sequence:
	 * <ul>
	 * 		<li>If value is null, the nullValue will be returned.</li>
	 * 		<li>If the requested class is an object array (clazz==Object[]), then the return is value (if value is an Object[])
	 *			or Collection.toArray() (if value is a Collection). In all other cases the process proceeds</li>
	 * 		<li>If collFist is set to true and value is a Collection, the first value of this collection will be used for the further process</li>
	 * 		<li>Now another null test returning nullValue if value is null</li>
	 * 		<li>Next test if the clazz is an instance of value.class. If true, value will be returned</li>
	 * 		<li>Next try for some standard type conversions for value, namely
	 * 				<ul>
	 * 					<li>If clazz is an instance of Boolean and value is "true" or "on" (case insensitive), then return Boolean(true)</li>
	 *					<li>If clazz is an instance of Boolean and value is "false" or "off" (case insensitive), then return Boolean(false)</li>
	 *					<li>If clazz is an instance of Integer then try to return Integer.valueOf(value.toString).</li>
	 *					<li>If clazz is an instance of Double then try to return Double.valueOf(value.toString).</li>
	 *					<li>If clazz is an instance of Long then try to return Long.valueOf(value.toString).</li>
	 * 				</ul>
	 * 		</li>
	 * 		<li>The last option is to return valueFalse to indicate that no test was successful</li>
	 * </ul>
	 * This method does suppress warnings for "unchecked" castings, because a casting from any concrete return type to T
	 * is unsafe. Because all actual castings follow an explicit type check, this suppression should have not negative impact
	 * (i.e. there are no ClassCastExceptions).
	 * @param <T> target class for the transformation
	 * @param clazz the requested type of the return value, required for initialisation
	 * @param nullValue the value to be used if a null test succeeds
	 * @param falseValue the value to be used in case no test succeeds
	 * @param collFirst if set true, collections will be processed and the first element returned, no special collection processing otherwise
	 * @return transformer that returns a value of type T if a conversion was successful, nullValue if null was tested successfully, falseValue in all other cases
	 */
	@SuppressWarnings("unchecked")
	public static final <T> Skb_Transformer<Object, T> OBJECT_TO_TARGET(final Class<T> clazz, final T nullValue, final T falseValue, final boolean collFirst){
		return new Skb_Transformer<Object, T>(){
			@Override public T transform(Object o){
				if(o==null){
					return nullValue;
				}

				if(clazz.isInstance(new Object[]{})){	//next check for Object[], because here we want collections unchanged
					if(o instanceof Object[]){
						return (T)o;
					}
					else if(o instanceof Collection){
						return (T)((Collection<?>)o).toArray();
					}
				}

				if(collFirst==true && (Skb_ClassUtils.INSTANCE_OF(Collection.class).test(o))){		//now, if collection use the first value
					o = Skb_CollectionUtils.GET_FIRST_ELEMENT((Collection<?>)o);
				}

				if(o==null){					//check value again, this maybe the one from the collection
					return nullValue;
				}

				if(clazz.isInstance(o)){		//if value is T, return caste to T
					return (T)o;
				}

				if(clazz.isInstance(new Boolean(true))){
					Object ret = Skb_ObjectUtils.OBJECT_TO_BOOLEAN().transform(o.toString());
					if(ret!=null){
						return (T) ret;
					}
				}
				if(clazz.isInstance(new Integer(0))){
					try{
						return (T)Integer.valueOf(o.toString());
					}
					catch(Exception ignore){}
				}
				if(clazz.isInstance(new Double(0))){
					try{
						return (T)Double.valueOf(o.toString());
					}
					catch(Exception ignore){}
				}
				if(clazz.isInstance(new Long(0))){
					try{
						return (T)Long.valueOf(o.toString());
					}
					catch(Exception ignore){}
				}

				return falseValue;				//no other option, return falseValue
			}
		};
	}

	/**
	 * Type safe casting or conversion from Object to target class, special processing for Object[] and Collections.
	 * This is a convenient method for {@link #CONVERT(Object, Class, Object, Object, boolean)} with both return values set to null and
	 * the last argument set to true (i.e. special processing of collections).
	 * @see #CONVERT(Object, Class, Object, Object, boolean)
	 * @param <T> type of the return object
	 * @param value the value that should be converted
	 * @param clazz the requested type of the return value, needed for initialisation
	 * @return a value of type T if a conversion was successful, nullValue if null was tested successfully, falseValue in all other cases
	 */
	public static final <T> T CONVERT(Object value, Class<T> clazz){
		return Skb_ObjectUtils.CONVERT(value, clazz, null, null, true);
	}

	/**
	 * Type safe casting or conversion from Object to target class, special processing for Object[] and Collections.
	 * This is a convenient method for {@link #CONVERT(Object, Class, Object, Object, boolean)} with the last 
	 * argument set to true (i.e. special processing of collections).
	 * @see #CONVERT(Object, Class, Object, Object, boolean)
	 * @param <T> type of the return object
	 * @param value the value that should be converted
	 * @param clazz the requested type of the return value, needed for initialisation
	 * @param nullValue the value to be used if a null test succeeds
	 * @param falseValue the value to be used in case no test succeeds
	 * @return a value of type T if a conversion was successful, nullValue if null was tested successfully, falseValue in all other cases
	 */
	public static final <T> T CONVERT(Object value, Class<T> clazz, T nullValue, T falseValue){
		return Skb_ObjectUtils.CONVERT(value, clazz, nullValue, falseValue, true);
	}

	/**
	 * Type safe casting or conversion from Object to target class, with optional special processing for Object[] and Collections.
	 * @see #OBJECT_TO_TARGET
	 * @param <T> type of the return object
	 * @param value the value that should be converted
	 * @param clazz the requested type of the return value, needed for initialisation
	 * @param nullValue the value to be used if a null test succeeds
	 * @param falseValue the value to be used in case no test succeeds
	 * @param collFirst if set true, collections will be processed and the first element returned, no special collection processing otherwise
	 * @return a value of type T if a conversion was successful, nullValue if null was tested successfully, falseValue in all other cases
	 */
	public static final <T> T CONVERT(Object value, Class<T> clazz, T nullValue, T falseValue, boolean collFirst){
		return Skb_ObjectUtils.OBJECT_TO_TARGET(clazz, nullValue, falseValue, collFirst).transform(value);
	}

}
