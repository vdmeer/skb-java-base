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

import de.vandermeer.skb.base.categories.HasDescription;

/**
 * An argument type for the {@link SkbShell}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.8
 */
public enum SkbShellArgumentType implements HasDescription {

	/** A Boolean type. */
	Boolean ("a java.lang.Boolean"),

	/** A Char type. */
	Char ("a java.lang.Char"),

	/** A Double type. */
	Double ("a java.lang.Double"),

	/** An Integer type. */
	Integer ("a java.lang.Integer"),

	/** A String type. */
	String ("a java.lang.String"),
	;

	/** A description. */
	String description;

	/**
	 * Creates a new shell argument type.
	 * @param description type description
	 */
	SkbShellArgumentType(String description){
		this.description = description;
	}

	@Override
	public String getDescription(){
		return this.description;
	}
}
