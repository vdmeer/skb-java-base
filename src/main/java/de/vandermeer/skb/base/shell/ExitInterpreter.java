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

import org.apache.commons.lang3.StringUtils;

/**
 * An interpreter for the 'exit' shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.10 build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class ExitInterpreter extends AbstractCommandInterpreter {

	/**
	 * Returns an new 'exit' command interpreter.
	 */
	public ExitInterpreter(){
		super(
				new SkbShellCommand[]{
						SkbShellFactory.newCommand("exit", null, SkbShellFactory.STANDARD_COMMANDS, "exit the shell"),
						SkbShellFactory.newCommand("quit", null, SkbShellFactory.STANDARD_COMMANDS, "exit the shell"),
						SkbShellFactory.newCommand("bye",  null, SkbShellFactory.STANDARD_COMMANDS, "exit the shell")
				}
		);
	}

	@Override
	public int interpretCommand(String command, LineParser lp, SkbShell shell) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		switch(command){
			case "exit":
			case "quit":
			case "bye":
				return -2;
			default:
				return -3;
		}
	}

}
