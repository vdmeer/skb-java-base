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

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Strategy for Deques.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.7 build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.5
 */
public enum DequeStrategy implements IsDequeStrategy {
	/** Default Deque, results in a LinkedList */
	DEFAULT,

	/** Linked list from java&#46;util. */
	LINKED_LIST,

	/** Array deque from java&#46;util. */
	ARRAY_DEQUE,

	/** Concurrent linked deque from java&#46;util. */
	CONCURRENT_LINKED_DEQUE,

	/** Linked blocking deque from java&#46;util. */
	LINKED_BLOCKING_DEQUE,
	;

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public boolean isSet() {
		return false;
	}

	@Override
	public boolean isQueue() {
		return true;
	}

	@Override
	public <T> Deque<T> get(Collection<T> collection) {
		Deque<T> ret;
		switch(this){
			case ARRAY_DEQUE:
				ret = new ArrayDeque<T>();
				break;
			case CONCURRENT_LINKED_DEQUE:
				ret = new ConcurrentLinkedDeque<T>();
				break;
			case LINKED_BLOCKING_DEQUE:
				ret = new LinkedBlockingDeque<T>();
				break;
			case LINKED_LIST:
			case DEFAULT:
			default:
				ret = new LinkedList<T>();
				break;
		}
		if(collection!=null){
			ret.addAll(collection);
		}
		return ret;
	}

	@Override
	public <T> Deque<T> get(Class<T> T) {
		Deque<T> ret;
		switch(this){
			case ARRAY_DEQUE:
				ret = new ArrayDeque<T>();
				break;
			case CONCURRENT_LINKED_DEQUE:
				ret = new ConcurrentLinkedDeque<T>();
				break;
			case LINKED_BLOCKING_DEQUE:
				ret = new LinkedBlockingDeque<T>();
				break;
			case LINKED_LIST:
			case DEFAULT:
			default:
				ret = new LinkedList<T>();
				break;
		}
		return ret;
	}
}
