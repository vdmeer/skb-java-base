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
 * An information source loader.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.7
 */
public interface InfoLoader {

	/**
	 * Returns the source set for the loader, that is where the loader loads information from.
	 * @return source set for the loader
	 */
	InfoSource getSource();

	/**
	 * Loads information from a source and returns a specific object.
	 * Errors of the loading process will be collected, use {@link #getLoadErrors()} to retrieve them.
	 * @return an object with the information loaded
	 */
	Object load();

	/**
	 * Returns collected errors from the last invocation of load or other methods.
	 * @return collected errors, should not be null but can be empty (meaning no errors).
	 */
	CC_Error getLoadErrors();

	/**
	 * Validates the loaders source.
	 * @return true if the source is valid (not null and getSource not null), false otherwise
	 */
	default boolean validateSource(){
		if(this.getSource()==null){
			this.getLoadErrors().add("{} - source is null", "loader");
			return false;
		}
		if(!this.getSource().isValid()){
			this.getLoadErrors().add("{} - invalid source <{}> - {}", new Object[]{"loader", this.getSource().getSource(), this.getSource().getInitError().render()});
			return false;
		}
		return true;
	}

}
