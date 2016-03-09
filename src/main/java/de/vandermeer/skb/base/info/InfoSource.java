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
 * A source of information.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.7
 */
public interface InfoSource {

	/**
	 * Returns the actual source.
	 * For instance, if the source is a path in the file system this method returns the path.
	 * The result of this method can (will) be used as key in maps.
	 * @return object representation of the source
	 */
	Object getSource();

	/**
	 * Returns a string representation of the source for messages.
	 * This representation should be configurable, for instance for a file source it might return the root directory or a set-root directory or the file base name or the file extension, depending on configuration.
	 * @return string representation of the source
	 */
	String asString();

	/**
	 * Returns any error that happened during initialization (and relate validation) of the source.
	 * @return a null string if the source is initialized successfully (and thus valid), a string with an error message otherwise
	 */
	CC_Error getInitError();

	/**
	 * Returns the valid flag of the source.
	 * @return true if the source is valid (can be used for a loader), false if not (loader should reject using the source).
	 * 		If the source is valid, then {@link #getInitError()} must return null, otherwise it must return an error string.
	 * 		If the source is valid, then {@link #getSource()} must return a non-null source object, otherwise it must return null.
	 */
	default boolean isValid(){
		return this.getInitError().size()==0;
	}

//	/**
//	 * Tests for source similarity.
//	 * @param obj object to test
//	 * @return true if the parameter is a ModelSource with the same values as this, false otherwise
//	 */
//	default boolean sameAs(Object obj){
//		if(obj==null){
//			return false;
//		}
//		if(obj instanceof InfoSource){
//			InfoSource is = (InfoSource) obj;
//			if(this.getClass().getName().equals(is.getClass().getName())){
//				if(this.getSource().equals(is.getSource())){
//					return true;
//				}
//			}
//		}
//		return false;
//	}

}
