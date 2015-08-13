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
 * An interpreter for the 'wait' shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.1 build 150814 (14-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class Ci_Wait extends AbstractCommandInterpreter {

	/** The argument for wait time. */
	public static final SkbShellArgument ARG_TIME = SkbShellFactory.newArgument(
			"time", false, SkbShellArgumentType.Integer, null, "wait time in milliseconds", null
	);

	/** The command for wait. */
	public static final SkbShellCommand WAIT = SkbShellFactory.newCommand(
			"wait", 
			ARG_TIME,
			SkbShellFactory.SIMPLE_COMMANDS, "shell waits for <time> milliseconds before accepting the next command", null
	);

	/**
	 * Returns an new 'wait' command interpreter.
	 */
	public Ci_Wait(){
		super(WAIT);
	}

	@Override
	public int interpretCommand(String command, LineParser lp, MessageMgr mm) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		if(!WAIT.getCommand().equals(command)){
			return -1;
		}

		try {
			Thread.sleep(new Integer(lp.setTokenPosition(1).getArgs()));
		}
		catch (InterruptedException e) {
			mm.report(MessageMgr.createErrorMessage("interrupted in wait {}", e.getMessage()));
		}
		return 0;
	}

}
