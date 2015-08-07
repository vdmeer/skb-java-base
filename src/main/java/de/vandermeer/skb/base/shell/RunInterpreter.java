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

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.StringFileLoader;

/**
 * An interpreter for the 'run' shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.11-SNAPSHOT build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class RunInterpreter extends AbstractCommandInterpreter {

	/** Last script run by the shell. */
	protected String lastScript;

	protected boolean printProgress = true;

	/**
	 * Returns an new 'run' command interpreter which will print progress information.
	 */
	public RunInterpreter(){
		this(true);
	}

	/**
	 * Returns an new 'run' command interpreter.
	 * @param printProgress flag for printing progress when executing commands from a file, true for yes, false for no
	 */
	public RunInterpreter(boolean printProgress){
		super(
				SkbShellFactory.newCommand("run", 
						SkbShellFactory.newArgumentArray(
								SkbShellFactory.newArgument("script", false, SkbShellArgumentType.String, null, "name (filename) of a script", null)
						),
						SkbShellFactory.STANDARD_COMMANDS, "runs a <script> with shell commands", null
				)
		);
		this.printProgress = printProgress;
	}

	@Override
	public int interpretCommand(String command, LineParser lp, SkbShell shell) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		if(command.equals("run")){
			String fileName = lp.getArgs();
			if(fileName==null){
				fileName = this.lastScript;
			}
			StringFileLoader sfl = new StringFileLoader(fileName);
			if(sfl.getLoadErrors().size()>0){
				shell.getLastErrors().add(sfl.getLoadErrors());
				return 1;
			}

			String content = sfl.load();
			if(sfl.getLoadErrors().size()>0){
				shell.getLastErrors().add(sfl.getLoadErrors());
				return 2;
			}
			if(content==null){
				shell.getLastErrors().add("run: unexpected problem with run script, content was null");
				return 3;
			}

			if(Skb_Console.USE_CONSOLE==true){
				Skb_Console.conInfo("{}: running file {}", new Object[]{shell.getPromptName(), fileName});
			}
			for(String s : StringUtils.split(content, '\n')){
				if(this.printProgress==true && Skb_Console.USE_CONSOLE==true){
					System.out.print(".");
				}
				shell.parseLine(s.trim());
			}
			this.lastScript = fileName;

			return 0;
		}
		return -1;
	}

}
