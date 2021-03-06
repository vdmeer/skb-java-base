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
 * An abstract help command interpreter.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.12
 */
public abstract class Ci_Help extends AbstractCommandInterpreter {

	/** The command for help. */
	public static final SkbShellCommand HELP = SkbShellFactory.newCommand("help", SkbShellFactory.SIMPLE_COMMANDS, "general help, use 'help <cmd> for help on a specific command", null);

	/** The command for h. */
	public static final SkbShellCommand HELP_H = SkbShellFactory.newCommand("h", SkbShellFactory.SIMPLE_COMMANDS, "general help, use 'help <cmd> for help on a specific command", null);

	/** The command for ?. */
	public static final SkbShellCommand HELP_QM = SkbShellFactory.newCommand("?", SkbShellFactory.SIMPLE_COMMANDS, "general help, use 'help <cmd> for help on a specific command", null);

	/** The calling shell, needed to parse lines. */
	protected final SkbShell skbShell;

	/**
	 * Returns an new 'help' command interpreter for STG output.
	 * @param skbShell the calling shell
	 */
	public Ci_Help(SkbShell skbShell){
		super(new SkbShellCommand[]{HELP, HELP_H, HELP_QM});
		this.skbShell = skbShell;
	}

	@Override
	public int interpretCommand(String command, LineParser lp, MessageMgr mm) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		if(!HELP.getCommand().equals(command) && !HELP_H.getCommand().equals(command) && !HELP_QM.getCommand().equals(command)){
			return -1;
		}
		return 0;
	}

}
