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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.composite.coin.CC_Info;
import de.vandermeer.skb.base.console.Skb_Console;

/**
 * An interpreter for the 'help' shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.11-SNAPSHOT build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class HelpInterpreter extends AbstractCommandInterpreter {

	/**
	 * Returns an new 'help' command interpreter.
	 */
	public HelpInterpreter(){
		super(
				new SkbShellCommand[]{
						SkbShellFactory.newCommand("help", null, SkbShellFactory.STANDARD_COMMANDS, "general help, use 'help <cmd> for help on a specific command", null),
						SkbShellFactory.newCommand("h",    null, SkbShellFactory.STANDARD_COMMANDS, "general help, use 'help <cmd> for help on a specific command", null),
						SkbShellFactory.newCommand("?",    null, SkbShellFactory.STANDARD_COMMANDS, "general help, use 'help <cmd> for help on a specific command", null)
				}
		);
	}

	@Override
	public int interpretCommand(String command, LineParser lp, SkbShell shell) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		if(!"help".equals(command) && !"h".equals(command) && !"?".equals(command)){
			return -3;
		}

		CC_Info info = new CC_Info();
		info.setSTG(shell.getSTGroup());

		String toHelp = lp.getArgs();
		if(toHelp==null){
			//collect all commands belonging to a particular category
			String defKey = "__standard";
			Map<String, TreeMap<String, SkbShellCommand>> cat2Cmd = new TreeMap<>();
			for(CommandInterpreter ci : shell.getCommandMap().values()){
				for(SkbShellCommand ssc : ci.getCommands().values()){
					String cat = defKey;
					if(ssc.getCategory()!=null){
						cat = ssc.getCategory().getCategory();
					}
					if(!cat2Cmd.containsKey(cat)){
						cat2Cmd.put(cat, new TreeMap<>());
					}
					cat2Cmd.get(cat).put(ssc.getCommand(), ssc);
				}
			}

			//no argument, means general help
			info.add("");
			info.add("{} {}", shell.getDisplayName(), shell.getDescription());
			info.add("");

			//do the commands per category, starting with "__standard"
			for(String cat : cat2Cmd.keySet()){
				String catDescr = cat;
				if(defKey.equals(cat)){
					catDescr = "standard commands";
				}
				info.add("- {}: {}", new Object[]{catDescr, cat2Cmd.get(cat).keySet()});
			}
			info.add("  try: 'help <command>' for more details");
		}
		else{
			if(shell.getCommandMap().containsKey(toHelp)){
				//we have a command to show help for, collect all information and present help

				SkbShellCommand ssc = shell.getCommandMap().get(toHelp).getCommands().get(toHelp);
				TreeMap<String, SkbShellArgument> args = new TreeMap<>();
				if(ssc.getArguments()!=null){
					for(SkbShellArgument ssa : ssc.getArguments()){
						if(ssa.isOptional()){
							args.put("[" + ssa.key() + "]", ssa);
						}
						else{
							args.put("<" + ssa.key() + ">", ssa);
						}
					}
				}

				info.add("{} {} -- {}", ssc.getCommand(), new StrBuilder().appendWithSeparators(args.keySet(), ", "), ssc.getDescription());
				for(SkbShellArgument ssa : args.values()){
					if(ssa.valueSet()!=null && ssa.addedHelp()!=null){
						info.add(" -- <{}> of type {} - {} - {} - value set {}", ssa.key(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp(), ArrayUtils.toString(ssa.valueSet()));
					}
					else if(ssa.valueSet()!=null && ssa.addedHelp()==null){
						info.add(" -- <{}> of type {} - {} - value set {}", ssa.key(), ssa.getType().name(), ssa.getDescription(), ArrayUtils.toString(ssa.valueSet()));
					}
					else if(ssa.valueSet()==null && ssa.addedHelp()!=null){
						info.add(" -- <{}> of type {} - {} - {}", ssa.key(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp());
					}
					else{
						info.add(" -- <{}> of type {} - {}", ssa.key(), ssa.getType().name(), ssa.getDescription());
					}
				}
				if(ssc.addedHelp()!=null){
					info.add("{}", ssc.addedHelp());
				}
			}
			else{
				info.add("");
				info.add("{}: no command {} found for help, try 'help' to see all available commands", new Object[]{shell.getID(), toHelp});
			}
		}
		info.add("");
		Skb_Console.conInfo(info.render());

		return 0;
	}
}
