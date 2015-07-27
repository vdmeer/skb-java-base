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
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.8
 */
public enum StandardShellCommands implements SkbShellCommand {
	/** Help command - help. */
	HELP("help", null, "help"),

	/** Abbreviated help command - h. */
	HELP_ABBREVIATED("h", null, "help"),

	/** Abbreviated help command - ?. */
	HELP_QMARK("?", null, "help"),

	/** Quit commands. */
	QUIT ("quit", null, "exit the shell"),

	/** Exit commands. */
	EXIT ("exit", null, "exit the shell"),

	/** Bye commands. */
	BYE ("bye", null, "exit the shell"),

	/** Wait commands. */
	WAIT ("wait", null, "shell waits for <n> milliseconds before accepting the next command"),

	;

	/** Shell commands. */
	String command;

	/** Arguments for the command. */
	SkbShellArgument[] arguments;

	/** A description. */
	String description;

	/**
	 * Creates a new standard shell command.
	 * @param command actual command
	 * @param arguments command arguments
	 * @param description a description of the command
	 */
	StandardShellCommands(String command, SkbShellArgument[] arguments, String description){
		this.command = command;
		this.arguments = arguments;
		this.description = description;
	}

	@Override
	public String getCommand() {
		return this.command;
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
