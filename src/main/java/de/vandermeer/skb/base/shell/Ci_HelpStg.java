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
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.base.composite.coin.CC_Info;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.managers.MessageMgr;

/**
 * An interpreter for the 'help' shell command using an STG for output.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class Ci_HelpStg extends Ci_Help {

	/** Default STG for help messages. */
	STGroup STG = new STGroupString(
			"where(location, line, column) ::= <<\n" +
			"<location;separator=\".\"><if(line&&column)> <line>:<column><elseif(!line&&!column)><elseif(!line)> -:<column><elseif(!column)> <line>:-<endif>\n"+
			">>\n\n" +
			"message5wh(reporter, type, who, when, where, what, why, how) ::= <<\n" +
			"<if(reporter)><reporter>: <endif><if(who)><who> <endif><if(when)>at (<when>) <endif><if(where)>in <where> <endif><if(what)>\\>> <what><endif>" +
			"<if(why)> \n       ==> <why><endif>\n" +
			"<if(how)> \n       ==> <how><endif>\n" +
			">>\n"
	);

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

		CC_Info info = new CC_Info();
		info.setSTG(this.STG);

		String toHelp = lp.getArgs();
		if(toHelp==null){
			this.generalHelp(info);
		}
		else{
			this.specificHelp(info, toHelp);
		}
		info.add("");
		Skb_Console.conInfo(info.render());

		return 0;
	}

	/**
	 * Processes general help with no specific command requested.
	 * @param info the info object to add help to
	 * @param toHelp the command to help with
	 */
	protected void specificHelp(CC_Info info, String toHelp){
		if(this.skbShell.getCommandMap().containsKey(toHelp)){
			//we have a command to show help for, collect all information and present help

			SkbShellCommand ssc = this.skbShell.getCommandMap().get(toHelp).getCommands().get(toHelp);
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
			info.add("{}: no command {} found for help, try 'help' to see all available commands", new Object[]{this.skbShell.getPromptName(), toHelp});
		}
	}


	/**
	 * Processes general help with no specific command requested.
	 * @param info the info object to add help to
	 */
	protected void generalHelp(CC_Info info){
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
		info.add("");
		info.add("{} {}", this.skbShell.getDisplayName(), this.skbShell.getDescription());
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

}
