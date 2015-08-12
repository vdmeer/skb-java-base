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

/**
 * Category of objects that represent an identifier.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsID extends CategoryIs, CategoryWithValue, HasDescription {
	/**
	 * Returns the ID.
	 * @return ID
	 */
	default Object id(){
		return this._value();
	}

	static IsID create(final Object value, final  Object description){
		return new IsID(){
			@Override public Object _value() {return value;}
			@Override public Object getDescription() {return description;}
			@Override public String toString() {return IsID.class.getSimpleName() + "[" + value + "]";}
		};
	}

	static IsID create(final Object value){
		return IsID.create(value, null);
	}

}
