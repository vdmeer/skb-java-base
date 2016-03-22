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
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.managers.MessageMgr;
import de.vandermeer.skb.interfaces.MessageConsole;
import de.vandermeer.skb.interfaces.categories.is.messagesets.IsInfoSetFT;

/**
 * An interpreter for the 'help' shell command using an STG for output.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.10
 */
public class Ci_HelpStg extends Ci_Help {

	/**
	 * Returns an new 'help' command interpreter for STG output.
	 * @param skbShell the calling shell
	 */
	public Ci_HelpStg(SkbShell skbShell){
		super(skbShell);
	}

	@Override
	public int interpretCommand(String command, LineParser lp, MessageMgr mm) {
		int ret = super.interpretCommand(command, lp, mm);
		if(ret!=0){
			return ret;
		}

		IsInfoSetFT info = IsInfoSetFT.create();

		String toHelp = lp.getArgs();
		if(toHelp==null){
			this.generalHelp(info);
		}
		else{
			this.specificHelp(info, toHelp);
		}
		info.addInfo("");
		MessageConsole.conInfo(info.render());

		return 0;
	}

	/**
	 * Processes general help with no specific command requested.
	 * @param info the info object to add help to
	 * @param toHelp the command to help with
	 */
	protected void specificHelp(IsInfoSetFT info, String toHelp){
		if(this.skbShell.getCommandMap().containsKey(toHelp)){
			//we have a command to show help for, collect all information and present help

			SkbShellCommand ssc = this.skbShell.getCommandMap().get(toHelp).getCommands().get(toHelp);
			TreeMap<String, SkbShellArgument> args = new TreeMap<>();
			if(ssc.getArguments()!=null){
				for(SkbShellArgument ssa : ssc.getArguments()){
					if(ssa.isOptional()){
						args.put("[" + ssa.getKey() + "]", ssa);
					}
					else{
						args.put("<" + ssa.getKey() + ">", ssa);
					}
				}
			}

			info.addInfo("{} {} -- {}", ssc.getCommand(), new StrBuilder().appendWithSeparators(args.keySet(), ", "), ssc.getDescription());
			for(SkbShellArgument ssa : args.values()){
				if(ssa.valueSet()!=null && ssa.addedHelp()!=null){
					info.addInfo(" -- <{}> of type {} - {} - {} - value set {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp(), ArrayUtils.toString(ssa.valueSet()));
				}
				else if(ssa.valueSet()!=null && ssa.addedHelp()==null){
					info.addInfo(" -- <{}> of type {} - {} - value set {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription(), ArrayUtils.toString(ssa.valueSet()));
				}
				else if(ssa.valueSet()==null && ssa.addedHelp()!=null){
					info.addInfo(" -- <{}> of type {} - {} - {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp());
				}
				else{
					info.addInfo(" -- <{}> of type {} - {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription());
				}
			}
			if(ssc.addedHelp()!=null){
				info.addInfo("{}", ssc.addedHelp());
			}
		}
		else{
			info.addInfo("");
			info.addInfo("{}: no command {} found for help, try 'help' to see all available commands", new Object[]{this.skbShell.getPromptName(), toHelp});
		}
	}


	/**
	 * Processes general help with no specific command requested.
	 * @param info the info object to add help to
	 */
	protected void generalHelp(IsInfoSetFT info){
		//collect all commands belonging to a particular category
		String defKey = "__standard";
		Map<String, TreeMap<String, SkbShellCommand>> cat2Cmd = new TreeMap<>();
		for(CommandInterpreter ci : this.skbShell.getCommandMap().values()){
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
		info.addInfo("");
		info.addInfo("{} {}", this.skbShell.getDisplayName(), this.skbShell.getDescription());
		info.addInfo("");

		//do the commands per category, starting with "__standard"
		for(String cat : cat2Cmd.keySet()){
			String catDescr = cat;
			if(defKey.equals(cat)){
				catDescr = "standard commands";
			}
			info.addInfo("- {}: {}", new Object[]{catDescr, cat2Cmd.get(cat).keySet()});
		}
		info.addInfo("  try: 'help <command>' for more details");
	}

}
