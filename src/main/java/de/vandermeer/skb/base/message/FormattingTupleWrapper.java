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
package de.vandermeer.skb.base.message;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * Wraps a formatting tuple object from SLF4J to be used as the what? part of a message.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8-SNAPSHOT build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class FormattingTupleWrapper {

	/** The local formatting tuple object. */
	FormattingTuple tuple;

	/**
	 * Returns a new wrapper with a single message.
	 * @param msg message for the wrapper
	 */
	public FormattingTupleWrapper(String msg){
		this.tuple = MessageFormatter.arrayFormat(msg, new Object[0]);
	}

	/**
	 * Returns a new wrapper with a message and objects for the message.
	 * @param msg message for the wrapper
	 * @param obj objects for the message
	 */
	public FormattingTupleWrapper(String msg, Object ... obj){
		this.tuple = MessageFormatter.arrayFormat(msg, obj);
	}

	@Override
	public String toString(){
		return this.tuple.getMessage();
	}
}