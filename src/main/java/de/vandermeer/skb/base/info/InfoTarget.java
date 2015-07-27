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

package de.vandermeer.skb.base.info;

import de.vandermeer.skb.base.composite.coin.CC_Error;

/**
 * An information target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public interface InfoTarget {

	/**
	 * Returns the actual target.
	 * For instance, if the target is a path in the file system this method returns the path.
	 * The result of this method can (will) be used as key in maps.
	 * @return object representation of the target
	 */
	Object getTarget();

	/**
	 * Returns any error that happened during initialization (and relate validation) of the target.
	 * @return a null string if the target is initialized successfully (and thus valid), a string with an error message otherwise
	 */
	CC_Error getInitError();

	/**
	 * Returns the valid flag of the target.
	 * @return true if the target is valid (can be used for a loader), false if not (loader should reject using the target).
	 * 		If the target is valid, then {@link #getInitError()} must return null, otherwise it must return an error string.
	 * 		If the target is valid, then {@link #getTarget()} must return a non-null target object, otherwise it must return null.
	 */
	default boolean isValid(){
		return (this.getInitError().size()==0)?true:false;
	}
}
