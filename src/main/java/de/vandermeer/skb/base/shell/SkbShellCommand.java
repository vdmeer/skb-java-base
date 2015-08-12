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
 * A command for the {@link SkbShell}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.8
 */
public interface SkbShellCommand extends HasDescription {

	/**
	 * Returns the shell command.
	 * @return shell commands
	 */
	String getCommand();

	/**
	 * Returns a category the command belongs to, useful for grouping commands for example in help screens.
	 * @return the category of the command, can be null
	 */
	SkbShellCommandCategory getCategory();

	/**
	 * Returns the arguments of this command.
	 * @return command arguments
	 */
	SkbShellArgument[] getArguments();

	/**
	 * Returns additional text for an auto-generated help for the argument.
	 * @return additional help text, null if none set
	 */
	default String addedHelp(){
		return null;
	}

}
