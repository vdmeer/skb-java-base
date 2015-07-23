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
 * Standard commands for the {@link SkbShell}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.8
 */
public enum StandardShellCommands implements SkbShellCommand {
	/** Help commands. */
	HELP(new String[]{"help", "h", "?"}, null, "help"),

	/** Quit commands. */
	QUIT (new String[]{"quit", "exit", "bye"}, null, "exit the shell"),

	;

	/** Commands associated to this command. */
	String[] commands;

	/** Arguments for the command. */
	SkbShellArgument[] arguments;

	/** A description. */
	String description;

	StandardShellCommands(String[] commands, SkbShellArgument[] arguments, String description){
		this.commands = commands;
		this.arguments = arguments;
		this.description = description;
	}

	@Override
	public String[] getCommands() {
		return this.commands;
	}

	@Override
	public SkbShellArgument[] getArguments() {
		return this.arguments;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
