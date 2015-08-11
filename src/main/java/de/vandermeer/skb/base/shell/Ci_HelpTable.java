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

import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.V2_RenderedAsciiTable;
import de.vandermeer.asciitable.v2.core.V2_Width;
import de.vandermeer.asciitable.v2.core.V2_WidthByAbsolute;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.message.FormattingTupleWrapper;

/**
 * An interpreter for the 'help' shell command using an ASCII table for output.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class Ci_HelpTable extends Ci_Help {

	/** The theme for the table. */
	protected V2_E_TableThemes theme;

	/** Table width calculated. */
	protected V2_Width width;

	/**
	 * Returns an new 'help' command interpreter for table output with default theme {@link V2_E_TableThemes#UTF_DOUBLE_LIGHT} and 76 character width.
	 */
	public Ci_HelpTable(){
		this(null);
	}

	/**
	 * Returns an new 'help' command interpreter for table output.
	 * @param theme a theme for the table
	 */
	public Ci_HelpTable(V2_E_TableThemes theme){
		super();

		this.theme = (theme==null)?V2_E_TableThemes.PLAIN_7BIT_STRONG:theme;
		this.width = new V2_WidthByAbsolute().setWidth(76);
	}

	/**
	 * Sets the table with.
	 * @param width table width to be used
	 * @return self to allow for chaining
	 */
	public Ci_HelpTable setWidth(V2_Width width){
		if(width!=null){
			this.width = width;
		}
		return this;
	}

	@Override
	public int interpretCommand(String command, LineParser lp, SkbShell shell) {
		int ret = super.interpretCommand(command, lp, shell);
		if(ret!=0){
			return ret;
		}

		V2_AsciiTable at = null;
		at = new V2_AsciiTable(2);
		at.addRuleStrong();

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
			at.addRow(null, shell.getDisplayName() + "-" + shell.getDescription());
			at.addRule();

			//do the commands per category, starting with "__standard"
			for(String cat : cat2Cmd.keySet()){
				String catDescr = cat;
				if(defKey.equals(cat)){
					catDescr = "standard commands";
				}
				at.addRow(catDescr, new StrBuilder().appendWithSeparators(cat2Cmd.get(cat).keySet(), ", "));
				at.addRule();
			}
			at.addRow(null, "try: 'help <command>' for more details");
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

				at.addRow(ssc.getCommand(), new StrBuilder().appendWithSeparators(args.keySet(), ", "));
				at.addRow("", ssc.getDescription());

				for(SkbShellArgument ssa : args.values()){
					if(ssa.valueSet()!=null && ssa.addedHelp()!=null){
						at.addRow("", new FormattingTupleWrapper(" -- <{}> of type {} - {} - {} - value set {}", ssa.key(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp(), ArrayUtils.toString(ssa.valueSet())));
					}
					else if(ssa.valueSet()!=null && ssa.addedHelp()==null){
						at.addRow("", new FormattingTupleWrapper(" -- <{}> of type {} - {} - value set {}", ssa.key(), ssa.getType().name(), ssa.getDescription(), ArrayUtils.toString(ssa.valueSet())));
					}
					else if(ssa.valueSet()==null && ssa.addedHelp()!=null){
						at.addRow("", new FormattingTupleWrapper(" -- <{}> of type {} - {} - {}", ssa.key(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp()));
					}
					else{
						at.addRow("", new FormattingTupleWrapper(" -- <{}> of type {} - {}", ssa.key(), ssa.getType().name(), ssa.getDescription()));
					}
				}
				if(ssc.addedHelp()!=null){
					at.addRow("", ssc.addedHelp());
				}
			}
			else{
				Skb_Console.conInfo("");
				Skb_Console.conInfo("{}: no command {} found for help, try 'help' to see all available commands", new Object[]{shell.getPromptName(), toHelp});
			}
		}
		at.addRule();

		Skb_Console.conInfo("");
		V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer()
			.setTheme(this.theme.get())
			.setWidth(this.width)
		;
		V2_RenderedAsciiTable rat = rend.render(at);
		Skb_Console.conInfo(rat.toString());

		return 0;
	}
}
