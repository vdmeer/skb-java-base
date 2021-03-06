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

import de.vandermeer.skb.base.managers.MessageMgr;

/**
 * An interpreter for the 'exit' shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.10
 */
public class Ci_Exit extends AbstractCommandInterpreter {

	/** The command for exit. */
	public static final SkbShellCommand EXIT = SkbShellFactory.newCommand("exit", SkbShellFactory.SIMPLE_COMMANDS, "exit the shell", null);

	/** The command for quit. */
	public static final SkbShellCommand QUIT = SkbShellFactory.newCommand("quit", SkbShellFactory.SIMPLE_COMMANDS, "exit the shell", null);

	/** The command for bye. */
	public static final SkbShellCommand BYE = SkbShellFactory.newCommand("bye",  SkbShellFactory.SIMPLE_COMMANDS, "exit the shell", null);

	/**
	 * Returns an new 'exit' command interpreter.
	 */
	public Ci_Exit(){
		super(new SkbShellCommand[]{EXIT, QUIT, BYE});
	}

	@Override
	public int interpretCommand(String command, LineParser lp, MessageMgr mm) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		if(!EXIT.getCommand().equals(command) && !QUIT.getCommand().equals(command) && !BYE.getCommand().equals(command)){
			return -1;
		}

		return -2;
	}

}
