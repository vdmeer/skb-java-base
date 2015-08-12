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
 * An information writer.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.7
 */
public interface InfoWriter {

	/**
	 * Returns the target set for the writer, that is where the writer writes information to.
	 * @return target set for the writer
	 */
	InfoTarget getTarget();

	/**
	 * Writes information to a target and returns a specific object.
	 * Errors of the writing process will be collected, use {@link #getWriteErrors()} to retrieve them.
	 * @param content the content to be written
	 * @return true on success, false on error
	 * @throws IllegalArgumentException if the content was not of the right type, depending on the writer implementation
	 */
	boolean write(Object content);

	/**
	 * Returns collected errors from the last invocation of write or other methods.
	 * @return collected errors, should not be null but can be empty (meaning no errors).
	 */
	CC_Error getWriteErrors();

	/**
	 * Validates the writers target.
	 * @return true if the source is valid (not null and getSource not null), false otherwise
	 */
	default boolean validateTarget(){
		if(this.getTarget()==null){
			this.getWriteErrors().add("{} - target is null", "writer");
			return false;
		}
		if(!this.getTarget().isValid()){
			this.getWriteErrors().add("{} - invalid target <{}> - {}", new Object[]{"writer", this.getTarget().getTarget(), this.getTarget().getInitError().render()});
			return false;
		}
		return true;
	}

}
