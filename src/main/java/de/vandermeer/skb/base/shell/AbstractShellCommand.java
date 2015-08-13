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
import java.util.TreeMap;

import de.vandermeer.skb.base.message.FormattingTupleWrapper;

/**
 * An abstract, default implementation of a shell command, use the {@link SkbShellFactory} to create a new object.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.1 build 150814 (14-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class AbstractShellCommand implements SkbShellCommand {

	/** The command, cannot be null. */
	private final String command;

	/** The command category, can be null. */
	private final SkbShellCommandCategory category;

	/** The command's arguments, can be null. */
	private final SkbShellArgument[] arguments;

	/** The command's description. */
	private final String description;

	/** Additional help if any set. */
	private final String addedHelp;

	/**
	 * Returns a new shell command, use the {@link SkbShellFactory} to create a new object.
	 * @param command the actual command
	 * @param arguments the command's arguments, can be null
	 * @param category the command's category, can be null
	 * @param description the command's description
	 * @param addedHelp additional help, can be null
	 * @throws IllegalArgumentException if command or description was null
	 */
	AbstractShellCommand(String command, SkbShellArgument[] arguments, SkbShellCommandCategory category, String description, String addedHelp){
		if(command==null){
			throw new IllegalArgumentException("command cannot be null");
		}
		if(description==null){
			throw new IllegalArgumentException("description cannot be null");
		}
		this.command = command;
		this.arguments = arguments;
		this.category = category;
		this.description = description;
		this.addedHelp = addedHelp;
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
	public String getDescription(){
		return this.description;
	}

	@Override
	public SkbShellCommandCategory getCategory() {
		return this.category;
	}

	@Override
	public String addedHelp(){
		return this.addedHelp;
	}

	@Override
	public String toString(){
		Map<String, SkbShellArgument> args = new TreeMap<>();
		for(SkbShellArgument arg : this.getArguments()){
			args.put(arg.key(), arg);
		}
		FormattingTupleWrapper ftw = new FormattingTupleWrapper(
				"<{}> cat <{}> args {} descr <{}>",
				new Object[]{
						this.getCommand(), this.getCategory(), args.values(), this.getDescription()
				}
		);
		return ftw.toString();
	}

}
