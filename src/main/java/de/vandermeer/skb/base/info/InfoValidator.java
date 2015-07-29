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
 * An information validator.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public interface InfoValidator {

	/**
	 * Returns the information object that is validated.
	 * @return information object that is validated, should not be null or blank
	 */
	Object getInfo();

	/**
	 * Returns the original object used to create the validation object.
	 * For instance, when validating a file or directory the actual information object might be of type File while the original object (the one returned here) might be of type String.
	 * @return original validation object, null if not give not given to the validator
	 */
	Object getOriginal();

	/**
	 * Flag reporting on the validation.
	 * @return true if the validation was successful (error list is of size 0), false otherwise (error list is of size greater than 0)
	 */
	default boolean isValid(){
		return (this.getValidationErrors().size()==0)?true:false;
	}

	/**
	 * Returns collected errors from the last invocation of load or other methods.
	 * @return collected errors, should not be null but can be empty (meaning no errors).
	 */
	CC_Error getValidationErrors();
}
