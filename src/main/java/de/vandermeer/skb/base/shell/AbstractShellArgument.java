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
 * An abstract, default implementation of a shell argument, use the {@link SkbShellFactory} to create a new object.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.10 build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class AbstractShellArgument implements SkbShellArgument {

	/** The actual argument, cannot be null. */
	private final String argument;

	/** Optional flag, true if is optional, false otherwise (default). */
	private final boolean isOptional;

	/** The argument's type, cannot be null. */
	private final SkbShellArgumentType type;

	/** The argument's description, cannot be null. */
	private final String description;

	/** Additional help information for the argument, to be used after the description, can be null. */
	private final String addedHelp;

	/**
	 * Returns a new shell argument, use the {@link SkbShellFactory} to create a new object.
	 * @param argument the actual argument, cannot be blank
	 * @param isOptional flag for optional (true if optional, false if not)
	 * @param type the argument's type, cannot be null
	 * @param description the command's description. cannot be null
	 * @param addedHelp a string additional to the description for help
	 * @throws IllegalArgumentException if argument, type, or description was null
	 */
	AbstractShellArgument(String argument, boolean isOptional, SkbShellArgumentType type, String description, String addedHelp){
		if(argument==null){
			throw new IllegalArgumentException("argument cannot be null");
		}
		if(type==null){
			throw new IllegalArgumentException("type cannot be null");
		}
		if(description==null){
			throw new IllegalArgumentException("description cannot be null");
		}
		this.argument = argument;
		this.isOptional = isOptional;
		this.type = type;
		this.description = description;
		this.addedHelp = addedHelp;
	}

	@Override
	public String _value() {
		return this.argument;
	}

	@Override
	public SkbShellArgumentType getType() {
		return this.type;
	}

	@Override
	public String getDescription(){
		return this.description;
	}

	@Override
	public String addedHelp(){
		return this.addedHelp;
	}

	@Override
	public boolean isOptional() {
		return this.isOptional;
	}
}
