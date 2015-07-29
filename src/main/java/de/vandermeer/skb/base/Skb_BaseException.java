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

package de.vandermeer.skb.base;

/**
 * Default SKB exception.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public class Skb_BaseException extends RuntimeException {
	/** UUID */
	private static final long serialVersionUID = 1L;

	/** Standard message */
	private static String msg="unmanageable problem in SKB";

	/** Returns a new exception */
	public Skb_BaseException(){
		super();
	}

	/**
	 * Returns a new exception with given cause
	 * @param cause exception cause
	 */
	public Skb_BaseException(Throwable cause){
		super(Skb_BaseException.msg, cause);
	}

	/**
	 * Returns a new exception with given cause and message
	 * @param msg exception message
	 * @param cause exception cause
	 */
	public Skb_BaseException(String msg, String cause){
		super(msg, new Error(cause));
	}
}
