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
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Strategy for queues.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.8 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.5
 */
public enum QueueStrategy implements IsQueueStrategy {

	/** Default Queue, results in a LinkedList. */
	DEFAULT,

	/** ArrayBlockingQueue from java&#46;utils. */
	//ARRAY_BLOCKING_QUEUE, --> doesn work here because it uses capacity in constructor

	/** Concurrent linked queue from java&#46;utils. */
	CONCURRENT_LINKED_QUEUE,

	/** Blocking queue from java&#46;utils. */
	LINKED_BLOCKING_QUEUE,

	/** Linked list from java&#46;utils. */
	LINKED_LIST,

	/** Linked transfer queue from java&#46;utils. */
	LINKED_TRANSFER_QUEUE,

	/** Priority blocking queue from java&#46;utils. */
	PRIORITY_BLOCKING_QUEUE,

	/** Priority queue from java&#46;utils. */
	PRIORITY_QUEUE,

	/** Synchronous queue from java&#46;utils. */
	SYNCHRONOUS_QUEUE,


	/** ArrayDeque queue from java&#46;utils. */
	ARRAY_DEQUE,

	/** ConcurrentLinkedDeque queue from java&#46;utils. */
	CONCURRENT_LINKED_DEQUE,

	/** DelayQueue queue from java&#46;utils. */
	//DELAY_QUEUE, --> doesn't work cause type extends Delayed

	/** LinkedBlockingDeque queue from java&#46;utils. */
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
	public <T> Queue<T> get(Collection<T> collection) {
		Queue<T> ret;
		switch(this){
			case CONCURRENT_LINKED_QUEUE:
				ret = new ConcurrentLinkedQueue<T>();
				break;
			case LINKED_BLOCKING_QUEUE:
				ret = new LinkedBlockingQueue<T>();
				break;
			case LINKED_TRANSFER_QUEUE:
				ret = new LinkedTransferQueue<T>();
				break;
			case PRIORITY_BLOCKING_QUEUE:
				ret = new PriorityBlockingQueue<T>();
				break;
			case PRIORITY_QUEUE:
				ret = new PriorityQueue<T>();
				break;
			case SYNCHRONOUS_QUEUE:
				ret = new SynchronousQueue<T>();
				break;
			case LINKED_LIST:
			case DEFAULT:
			default:
				ret = new LinkedList<T>();
				break;
			case ARRAY_DEQUE:
				ret = new ArrayDeque<T>();
				break;
			case CONCURRENT_LINKED_DEQUE:
				ret = new ConcurrentLinkedDeque<T>();
				break;
			case LINKED_BLOCKING_DEQUE:
				ret = new LinkedBlockingDeque<T>();
				break;
		}
		if(collection!=null){
			ret.addAll(collection);
		}
		return ret;
	}

	@Override
	public <T> Queue<T> get(Class<T> T) {
		Queue<T> ret;
		switch(this){
			case CONCURRENT_LINKED_QUEUE:
				ret = new ConcurrentLinkedQueue<T>();
				break;
			case LINKED_BLOCKING_QUEUE:
				ret = new LinkedBlockingQueue<T>();
				break;
			case LINKED_TRANSFER_QUEUE:
				ret = new LinkedTransferQueue<T>();
				break;
			case PRIORITY_BLOCKING_QUEUE:
				ret = new PriorityBlockingQueue<T>();
				break;
			case PRIORITY_QUEUE:
				ret = new PriorityQueue<T>();
				break;
			case SYNCHRONOUS_QUEUE:
				ret = new SynchronousQueue<T>();
				break;
			case LINKED_LIST:
			case DEFAULT:
			default:
				ret = new LinkedList<T>();
				break;
			case ARRAY_DEQUE:
				ret = new ArrayDeque<T>();
				break;
			case CONCURRENT_LINKED_DEQUE:
				ret = new ConcurrentLinkedDeque<T>();
				break;
			case LINKED_BLOCKING_DEQUE:
				ret = new LinkedBlockingDeque<T>();
				break;
		}
		return ret;
	}

}
