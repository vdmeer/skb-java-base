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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import de.vandermeer.skb.base.managers.MessageMgr;

/**
 * An interpreter for the 'history' shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.1.2
 */
public class Ci_History extends AbstractCommandInterpreter {

	/** The command for history using 'history'. */
	public static final SkbShellCommand HISTORY = SkbShellFactory.newCommand("history", SkbShellFactory.SIMPLE_COMMANDS, "prints shell history or recalls a command", null);

	/** The command for history using '!' (exclamation mark). */
	public static final SkbShellCommand HISTORY_EM = SkbShellFactory.newCommand("!", SkbShellFactory.SIMPLE_COMMANDS, "prints shell history or recalls a command", null);

	/** Link to the calling shell to retrieve history from. */
	protected final SkbShell skbsh;

	/**
	 * Returns an new 'history' command interpreter.
	 *@param skbsh the calling shell to retrieve history information from
	 *@throws IllegalArgumentException if the parameter is null
	 */
	public Ci_History(SkbShell skbsh){
		super(new SkbShellCommand[]{HISTORY, HISTORY_EM});

		if(skbsh==null){
			throw new IllegalArgumentException("shell cannot be null");
		}
		this.skbsh = skbsh;
	}

	@Override
	public int interpretCommand(String command, LineParser lp, MessageMgr mm) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		if(!HISTORY.getCommand().equals(command) && !HISTORY_EM.getCommand().equals(command)){
			return -1;
		}

		List<String> history = this.skbsh.getCommandHistory();
		history.remove(history.size()-1);

		if(StringUtils.isBlank(lp.setTokenPosition(1).getArgs())){
			//no position requested, print history
			for(int i=0; i<this.skbsh.getCommandHistory().size(); i++){
				mm.report(MessageMgr.createInfoMessage("! {} {}", i, this.skbsh.getCommandHistory().get(i)));
			}
		}
		else{
			//position requested, try to get it and print it
			int position = NumberUtils.toInt(lp.setTokenPosition(1).getArgs(), -1);
			if(position!=-1){
				this.skbsh.parseLine(this.skbsh.getCommandHistory().get(position));
			}
			else{
				mm.report(MessageMgr.createErrorMessage("history, position <{}> not found", lp.setTokenPosition(1).getArgs()));
			}
		}


		return 0;
	}

}
