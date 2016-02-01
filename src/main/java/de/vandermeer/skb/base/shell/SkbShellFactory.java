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

import java.util.HashSet;
import java.util.Set;

import de.vandermeer.skb.base.managers.MessageRenderer;

/**
 * Factory for Skb Shell artifacts.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
<<<<<<< HEAD
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
=======
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
>>>>>>> dev
 * @since      v0.0.10
 */
public class SkbShellFactory {

	/**
	 * Returns a new shell with the default identifier and the default STG and console activated.
	 * @return new shell
	 */
	public static SkbShell newShell(){
		return SkbShellFactory.newShell(null, null, true);
	}

	/**
	 * Returns a new shell with the default identifier and the given STG and console activated.
	 * @param renderer a renderer for help messages
	 * @return new shell
	 */
	public static SkbShell newShell(MessageRenderer renderer){
		return SkbShellFactory.newShell(null, renderer, true);
	}

	/**
	 * Returns a new shell with given STG and console flag.
	 * @param renderer a renderer for help messages
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen (except for errors on runShell() and some help commands)
	 * @return new shell
	 */
	public static SkbShell newShell(MessageRenderer renderer, boolean useConsole){
		return SkbShellFactory.newShell(null, renderer, useConsole);
	}

	/**
	 * Returns a new shell with a given identifier, standard STGroup and console activated.
	 * @param id new shell with identifier
	 * @return new shell
	 * 
	 */
	public static SkbShell newShell(String id){
		return SkbShellFactory.newShell(id, null, true);
	}

	/**
	 * Returns a new shell with given identifier and console flag with standard STGroup.
	 * @param id new shell with identifier, uses default if given STG is not valid
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen
	 * @return new shell
	 */
	public static SkbShell newShell(String id, boolean useConsole){
		return SkbShellFactory.newShell(id, null, useConsole);
	}

	/**
	 * Returns a new shell with a given identifier and STGroup plus console activated.
	 * @param id new shell with identifier
	 * @param renderer a renderer for help messages
	 * @return new shell
	 */
	public static SkbShell newShell(String id, MessageRenderer renderer){
		return SkbShellFactory.newShell(id, renderer, true);
	}

	/**
	 * Returns a new shell with given identifier and console flag.
	 * @param id new shell with identifier
	 * @param renderer a renderer for help messages
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen
	 * @return new shell
	 */
	public static SkbShell newShell(String id, MessageRenderer renderer, boolean useConsole){
		return new AbstractShell(id, renderer, useConsole);
	}

	/**
	 * Returns a new shell command, use the factory to create one.
	 * @param command the actual command
	 * @param arguments the command's arguments, can be null
	 * @param category the command's category, can be null
	 * @param description the command's description
	 * @param addedHelp additional help, can be null
	 * @return new shell command
	 * @throws IllegalArgumentException if command or description was null
	 */
	public static SkbShellCommand newCommand(String command, SkbShellArgument[] arguments, SkbShellCommandCategory category, String description, String addedHelp){
		return new AbstractShellCommand(command, arguments, category, description, addedHelp);
	}

	/**
	 * Returns a new shell command, use the factory to create one.
	 * @param command the actual command
	 * @param argument the command's argument, can be null
	 * @param category the command's category, can be null
	 * @param description the command's description
	 * @param addedHelp additional help, can be null
	 * @return new shell command
	 * @throws IllegalArgumentException if command or description was null
	 */
	public static SkbShellCommand newCommand(String command, SkbShellArgument argument, SkbShellCommandCategory category, String description, String addedHelp){
		if(argument==null){
			return new AbstractShellCommand(command, null, category, description, addedHelp);
		}
		return SkbShellFactory.newCommand(command, new SkbShellArgument[]{argument}, category, description, addedHelp);
	}

	/**
	 * Returns a new shell command without formal arguments, use the factory to create one.
	 * @param command the actual command
	 * @param category the command's category, can be null
	 * @param description the command's description
	 * @param addedHelp additional help, can be null
	 * @return new shell command
	 * @throws IllegalArgumentException if command or description was null
	 */
	public static SkbShellCommand newCommand(String command, SkbShellCommandCategory category, String description, String addedHelp){
		return new AbstractShellCommand(command, null, category, description, addedHelp);
	}

	/**
	 * Returns a new shell argument, use the factory to create one.
	 * @param argument the actual argument, cannot be blank
	 * @param isOptional flag for optional (true if optional, false if not)
	 * @param type the argument's type, cannot be null
	 * @param valueSet the argument's value set if specified, can be null
	 * @param description the command's description, cannot be null
	 * @param addedHelp a string additional to the description for help
	 * @return new shell argument
	 * @throws IllegalArgumentException if argument, type, or description was null
	 */
	public static SkbShellArgument newArgument(String argument, boolean isOptional, SkbShellArgumentType type, Object[] valueSet, String description, String addedHelp){
		return new AbstractShellArgument(argument, isOptional, type, valueSet, description, addedHelp);
	}

	/**
	 * Returns a new argument array.
	 * @param args input arguments, any argument being null will be ignored
	 * @return argument array, null if none provided
	 */
	public static SkbShellArgument[] newArgumentArray(SkbShellArgument ... args){
		if(args==null){
			return null;
		}
		Set<SkbShellArgument> ret = new HashSet<>();
		for(SkbShellArgument arg : args){
			if(arg!=null){
				ret.add(arg);
			}
		}
		return ret.toArray(new SkbShellArgument[args.length]);
	}

	/**
	 * Returns a new shell command category, use the {@link SkbShellFactory} to create a new object.
	 * @param category the actual category
	 * @param description the command's description
	 * @return new shell command category
	 * @throws IllegalArgumentException if command or description was null
	 */
	public static SkbShellCommandCategory newCategory(String category, String description){
		return new AbstractCategory(category, description);
	}

	/**
	 * A category for standard commands.
	 */
	public static SkbShellCommandCategory SIMPLE_COMMANDS = newCategory("Simple", "commands with 0 or 1 argument, no argument id required");

}
