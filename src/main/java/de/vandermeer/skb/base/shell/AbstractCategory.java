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

package de.vandermeer.skb.base.shell;

/**
 * Abstract category implementation, use the {@link SkbShellFactory} to create a new object.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.10
 */
public class AbstractCategory implements SkbShellCommandCategory {

	/** The category, cannot be null. */
	private final String category;

	/** The command's description. */
	private final String description;

	/**
	 * Returns a new shell command category, use the {@link SkbShellFactory} to create a new object.
	 * @param category the actual category
	 * @param description the command's description
	 * @throws IllegalArgumentException if command or description was null
	 */
	AbstractCategory(String category, String description){
		if(category==null){
			throw new IllegalArgumentException("category cannot be null");
		}
		if(description==null){
			throw new IllegalArgumentException("description cannot be null");
		}
		this.category = category;
		this.description = description;
	}

	@Override
	public String getCategory() {
		return this.category;
	}

	@Override
	public String getDescription(){
		return this.description;
	}

	@Override
	public String toString(){
		return this.getCategory();
	}

}
