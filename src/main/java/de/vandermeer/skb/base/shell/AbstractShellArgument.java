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

import org.apache.commons.lang3.ArrayUtils;

import de.vandermeer.skb.base.message.FormattingTupleWrapper;

/**
 * An abstract, default implementation of a shell argument, use the {@link SkbShellFactory} to create a new object.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.3 build 150819 (19-Aug-15) for Java 1.8
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

	/** The value set of the argument. */
	private final Object[] valueSet;

	/**
	 * Returns a new shell argument, use the {@link SkbShellFactory} to create a new object.
	 * @param argument the actual argument, cannot be blank
	 * @param isOptional flag for optional (true if optional, false if not)
	 * @param type the argument's type, cannot be null
	 * @param valueSet the argument's value set if specified, can be null
	 * @param description the command's description, cannot be null
	 * @param addedHelp a string additional to the description for help
	 * @throws IllegalArgumentException if argument, type, or description was null
	 */
	AbstractShellArgument(String argument, boolean isOptional, SkbShellArgumentType type, Object[] valueSet, String description, String addedHelp){
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
		this.valueSet= valueSet;
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

	@Override
	public String toString(){
		if(this.valueSet==null){
			FormattingTupleWrapper ftw = new FormattingTupleWrapper("{}:{}", new Object[]{this.key(), this.getType().name()});
			return ftw.toString();
		}
		else{
			FormattingTupleWrapper ftw = new FormattingTupleWrapper("{}:{}:{}", new Object[]{this.key(), this.getType().name(), ArrayUtils.toString(this.valueSet())});
			return ftw.toString();
		}
	}

	@Override
	public Object[] valueSet() {
		return this.valueSet;
	}

}
