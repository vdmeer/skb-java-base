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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * An abstract command interpreter implementation with all basic features.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public abstract class AbstractCommandInterpreter implements CommandInterpreter {

	/** Commands for the interpreter. */
	private final Map<String, SkbShellCommand> commands;

	/**
	 * Returns a new, empty interpreter.
	 */
	public AbstractCommandInterpreter(){
		this.commands = new TreeMap<>();
	}

	/**
	 * Returns a new interpreter set for a single command.
	 * @param command single command for the interpreter
	 */
	public AbstractCommandInterpreter(SkbShellCommand command){
		this();
		if(command!=null){
			this.commands.put(command.getCommand(), command);
		}
	}

	/**
	 * Returns a new interpreter set for an array of commands.
	 * @param commands array of commands for the interpreter, ignored if null and every member of the array that is null is ignored as well
	 */
	public AbstractCommandInterpreter(SkbShellCommand[] commands){
		this();
		if(commands!=null){
			for(SkbShellCommand command : commands){
				if(command!=null){
					this.commands.put(command.getCommand(), command);
				}
			}
		}
	}

	/**
	 * Returns a new interpreter set for a collection of commands.
	 * @param commands collection of commands for the interpreter, ignored if null and every member of the array that is null is ignored as well
	 */
	public AbstractCommandInterpreter(Collection<SkbShellCommand> commands){
		this();
		if(commands!=null){
			for(SkbShellCommand command : commands){
				if(command!=null){
					this.commands.put(command.getCommand(), command);
				}
			}
		}
	}

	@Override
	public Set<String> getCommandStrings() {
		return this.commands.keySet();
	}
	@Override
	public Map<String, SkbShellCommand> getCommands() {
		return this.commands;
	}

}
