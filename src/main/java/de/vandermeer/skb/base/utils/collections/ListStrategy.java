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

package de.vandermeer.skb.base.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * Strategies for lists.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.5
 */
public enum ListStrategy implements IsListStrategy {
	/** Default list, results in an ArrayList. */
	DEFAULT,

	/** ArrayList from java&#46;util. */
	ARRAY_LIST,

	/** LinkedList from java&#46;util. */
	LINKED_LIST,

	/** Stack from java&#46;util. */
	STACK,

	/** Vector from java&#46;util. */
	VECTOR,
	;

	@Override
	public <T> List<T> get(Collection<T> collection) {
		List<T> ret;
		switch(this){
			case LINKED_LIST:
				ret = new LinkedList<T>();
				break;
			case STACK:
				ret = new Stack<T>();
				break;
			case VECTOR:
				ret = new Vector<T>();
				break;
			case ARRAY_LIST:
			case DEFAULT:
			default:
				ret = new ArrayList<T>();
				break;
		}
		if(collection!=null){
			ret.addAll(collection);
		}
		return ret;
	}

	@Override
	public <T> List<T> get(Class<T> T){
		List<T> ret;
		switch(this){
			case LINKED_LIST:
				ret = new LinkedList<T>();
				break;
			case STACK:
				ret = new Stack<T>();
				break;
			case VECTOR:
				ret = new Vector<T>();
				break;
			case ARRAY_LIST:
			case DEFAULT:
			default:
				ret = new ArrayList<T>();
				break;
		}
		return ret;
	}

	@Override
	public String toString() {
		return this.name();
	}

	@Override
	public boolean isList(){
		return true;
	}

	@Override
	public boolean isSet(){
		return false;
	}

	@Override
	public boolean isQueue(){
		return false;
	}

}
