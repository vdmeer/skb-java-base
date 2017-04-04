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

import de.vandermeer.asciitable.AT_ColumnWidthCalculator;
import de.vandermeer.asciitable.AT_Renderer;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.asciithemes.TA_Grid;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.base.managers.MessageMgr;
import de.vandermeer.skb.interfaces.FormattingTupleWrapper;
import de.vandermeer.skb.interfaces.MessageConsole;

/**
 * An interpreter for the 'help' shell command using an ASCII table for output.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.10
 */
public class Ci_HelpTable extends Ci_Help {

	/** The grid for the table. */
	protected TA_Grid grid;

	/** Table width calculated. */
	protected AT_ColumnWidthCalculator cwc;

	/**
	 * Returns an new 'help' command interpreter for table output with default theme {@link V2_E_TableThemes#UTF_DOUBLE_LIGHT} and 76 character width.
	 * @param skbShell the calling shell
	 */
	public Ci_HelpTable(SkbShell skbShell){
		this(skbShell, null);
	}

	/**
	 * Returns an new 'help' command interpreter for table output.
	 * @param skbShell the calling shell
	 * @param grid a grid for the table
	 */
	public Ci_HelpTable(SkbShell skbShell, TA_Grid grid){
		super(skbShell);

		this.grid = (grid==null)?A7_Grids.minusBarPlus():grid;
		this.cwc = new CWC_FixedWidth().add(15).add(70);
//		this.width = new V2_WidthAbsoluteEven().setWidth(76);
	}

	/**
	 * Sets the table with.
	 * @param cwc table width calculator to be used
	 * @return self to allow for chaining
	 */
	public Ci_HelpTable setWidth(AT_ColumnWidthCalculator cwc){
		if(cwc!=null){
			this.cwc = cwc;
		}
		return this;
	}

	@Override
	public int interpretCommand(String command, LineParser lp, MessageMgr mm) {
		int ret = super.interpretCommand(command, lp, mm);
		if(ret!=0){
			return ret;
		}

		AsciiTable at = new AsciiTable();
		at.getContext().setGrid(this.grid);
		at.addStrongRule();

		String toHelp = lp.getArgs();
		if(toHelp==null){
			this.generalHelp(at);
		}
		else{
			this.specificHelp(at, toHelp);
		}
		at.addStrongRule();

		MessageConsole.conInfo("");
		AT_Renderer rend = AT_Renderer.create()
			.setCWC(this.cwc)
		;
		at.setRenderer(rend);
		MessageConsole.conInfo(at.render());
		return 0;
	}

	/**
	 * Processes general help with no specific command requested.
	 * @param at table to add help information to
	 * @param toHelp the command to help with
	 */
	protected void specificHelp(AsciiTable at, String toHelp){
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

			at.addRow(ssc.getCommand(), new StrBuilder().appendWithSeparators(args.keySet(), ", "));
			at.addRow("", ssc.getDescription());

			for(SkbShellArgument ssa : args.values()){
				if(ssa.valueSet()!=null && ssa.addedHelp()!=null){
					at.addRow("", FormattingTupleWrapper.create(" -- <{}> of type {} - {} - {} - value set {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp(), ArrayUtils.toString(ssa.valueSet())));
				}
				else if(ssa.valueSet()!=null && ssa.addedHelp()==null){
					at.addRow("", FormattingTupleWrapper.create(" -- <{}> of type {} - {} - value set {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription(), ArrayUtils.toString(ssa.valueSet())));
				}
				else if(ssa.valueSet()==null && ssa.addedHelp()!=null){
					at.addRow("", FormattingTupleWrapper.create(" -- <{}> of type {} - {} - {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp()));
				}
				else{
					at.addRow("", FormattingTupleWrapper.create(" -- <{}> of type {} - {}", ssa.getKey(), ssa.getType().name(), ssa.getDescription()));
				}
			}
			if(ssc.addedHelp()!=null){
				at.addRow("", ssc.addedHelp());
			}
		}
		else{
			MessageConsole.conInfo("");
			MessageConsole.conInfo("{}: no command {} found for help, try 'help' to see all available commands", new Object[]{this.skbShell.getPromptName(), toHelp});
		}
	}

	/**
	 * Processes general help with no specific command requested.
	 * @param at table to add help information to
	 */
	protected void generalHelp(AsciiTable at){
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
		at.addRow(null, this.skbShell.getDisplayName() + "-" + this.skbShell.getDescription());
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

}
