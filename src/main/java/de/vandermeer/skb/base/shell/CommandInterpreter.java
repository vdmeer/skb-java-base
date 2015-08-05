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

import java.util.Map;
import java.util.Set;

/**
 * An interpreter for a shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.10 build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public interface CommandInterpreter {

	/**
	 * Interprets the given command.
	 * @param command command for interpretation
	 * @param lp a parser with the whole command line
	 * @param shell the original calling shell
	 * @return
	 * 			-3 if any of the input parameters was null, blank or not correct
	 * 			-2 if command was found and interpreted but leads to an exit command for the shell,
	 * 			-1 if command not part of this interpreter,
	 * 			0 if command was found and interpretation was successful and did not lead to an exit command (exit shell),
	 * 			greater than 0 otherwise (command found, interpreted, but some errors occurred)
	 */
	int interpretCommand(String command, LineParser lp, SkbShell shell);

	/**
	 * Returns the set of commands this interpreter does handle.
	 * @return set of commands of the command interpreter
	 */
	Set<String> getCommandStrings();

	/**
	 * Returns the mapping of commands to {@link SkbShellCommand} this interpreter does handle.
	 * @return the mapping, cannot be null
	 */
	Map<String, SkbShellCommand> getCommands();
}
