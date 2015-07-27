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
 * Standard arguments for the {@link SkbShell}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.8
 */
public enum StandardShellArguments implements SkbShellArgument {
	id (SkbShellArgumentType.String, "an identifier"),
	;

	/** Type of the argument. */
	SkbShellArgumentType type;

	/** A description. */
	String description;

	/**
	 * Returns a new standard shell argument
	 * @param type argument type
	 * @param description argument description
	 */
	StandardShellArguments(SkbShellArgumentType type, String description){
		this.type = type;
		this.description = description;
	}

	@Override
	public String getDescription(){
		return this.description;
	}

	@Override
	public String _value() {
		return this.name();
	}

	@Override
	public SkbShellArgumentType getType() {
		return this.type;
	}

}
