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

import java.util.function.Predicate;

import de.vandermeer.skb.base.Skb_BaseException;

/**
 * Class testing methods.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.11-SNAPSHOT build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Skb_ClassUtils {

	/**
	 * Returns a predicate that evaluates to true if a given object (subClass in evaluate) is assignable from superClass.
	 * @param superClass super class to test for
	 * @return a predicate that evaluates the super class of an object
	 */
	public final static Predicate<Object> IS_SUBCLASS_OF(final Object superClass){
		return new Predicate<Object>(){
			@Override public boolean test(Object subClass){
				if(superClass==null || subClass==null){
					return false;
				}
				if((superClass instanceof Class) && (subClass instanceof Class)){
					return ((Class<?>)superClass).isAssignableFrom((Class<?>)subClass);
				}
				else if((superClass instanceof Class) && !(subClass instanceof Class)){
					return ((Class<?>)superClass).isAssignableFrom(subClass.getClass());
				}
				else if(!(superClass instanceof Class) && (subClass instanceof Class)){
					return superClass.getClass().isAssignableFrom((Class<?>)subClass);
				}
				else{
					return superClass.getClass().isAssignableFrom(subClass.getClass());
				}
			}
		};
	}

	/**
	 * Returns a predicate that evaluates to true if a given object (superClass in evaluate) is assignable from subClass.
	 * @param subClass sub class to test for
	 * @return a predicate that evaluates the sub class of an object
	 */
	public final static Predicate<Object> IS_SUPERCLASS_OF(final Object subClass){
		return new Predicate<Object>(){
			@Override public boolean test(Object superClass){
				if(superClass==null || subClass==null){
					return false;
				}
				if((superClass instanceof Class) && (subClass instanceof Class)){
					return ((Class<?>)superClass).isAssignableFrom((Class<?>)subClass);
				}
				else if((superClass instanceof Class) && !(subClass instanceof Class)){
					return ((Class<?>)superClass).isAssignableFrom(subClass.getClass());
				}
				else if(!(superClass instanceof Class) && (subClass instanceof Class)){
					return superClass.getClass().isAssignableFrom((Class<?>)subClass);
				}
				else{
					return superClass.getClass().isAssignableFrom(subClass.getClass());
				}
			}
		};
	}

	/**
	 * Returns a predicate that evaluates to true if a given object is an instance of a class.
	 * @param lhs test class
	 * @return true if object is an instance of class, false otherwise
	 */
	public final static Predicate<Object> INSTANCE_OF(final Object lhs) throws Skb_BaseException {
		return new Predicate<Object>(){
			@Override public boolean test(Object rhs){
				if(lhs==null || rhs==null){
					return false;
				}
				if((lhs instanceof Class) && (rhs instanceof Class)){
					//return ((Class<?>)lhs).isInstance((Class<?>)rhs);
					throw new Skb_BaseException("wrong arguments", "cannot check instance of class with class");
				}
				else if((lhs instanceof Class) && !(rhs instanceof Class)){
					return ((Class<?>)lhs).isInstance(rhs);
				}
				else if(!(lhs instanceof Class) && (rhs instanceof Class)){
					//return lhs.getClass().isInstance((Class<?>)rhs);
					throw new Skb_BaseException("wrong arguments", "cannot check instance of object with class");
				}
				else{
					return lhs.getClass().isInstance(rhs);
				}
			}
		};
	}
}
