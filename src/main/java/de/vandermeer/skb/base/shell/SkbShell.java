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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.composite.coin.CC_Info;
import de.vandermeer.skb.base.info.STGroupValidator;
import de.vandermeer.skb.base.message.Message5WH_Builder;
import de.vandermeer.skb.base.utils.PromptOnEmpty;
import de.vandermeer.skb.base.utils.Skb_ConsoleUtils;

/**
 * An abstract shell.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.0-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.8
 */
public abstract class SkbShell implements PromptOnEmpty {

	/** Default shell identifier. */
	public static String DEFAULT_ID = "skbsh";

	/** Client runtime identifier. */
	protected final String id;

	/** Indicator if the read line loop should be maintained. */
	protected boolean inLoop = true;

	/** Standard shell commands. */
	private final Map<String, SkbShellCommand> standardShellCommands;

	/** Additional shell commands. */
	private final Map<String, SkbShellCommand> addedShellCommands;

	/** Local list of errors collected during process, cleared for every new line parsing. */
	protected final CC_Error errors = new CC_Error();

	/** Local list of infos collected during process, cleared for every new line parsing. */
	protected final CC_Info infos = new CC_Info();

	/** An STG for info and other messages, similar to Message5WH but w/o type. */
	protected STGroup stg = new STGroupString(
			"where(location, line, column) ::= <<\n" +
			"<location;separator=\".\"><if(line&&column)> <line>:<column><elseif(!line&&!column)><elseif(!line)> -:<column><elseif(!column)> <line>:-<endif>\n"+
			">>\n\n" +
			"message5wh(reporter, type, who, when, where, what, why, how) ::= <<\n" +
			"<if(reporter)><reporter>: <endif><if(who)><who> <endif><if(when)>at (<when>) <endif><if(where)>in <where> <endif><if(what)>\\>> <what><endif>" +
			"<if(why)> \n       ==> <why><endif>\n" +
			"<if(how)> \n       ==> <how><endif>\n" +
			">>\n"
	);

	/** An STG for info and other messages, similar to Message5WH but w/o type. */
	protected STGroupValidator stgv;

	/**
	 * Returns a new shell with the default identifier and the default STG.
	 */
	public SkbShell(){
		this(DEFAULT_ID, null, true);
	}

	/**
	 * Returns a new shell with the default identifier and the given STG.
	 * @param stg an STGroup for help messages
	 * @throws IllegalArgumentException if the STG did not validate
	 */
	public SkbShell(STGroup stg){
		this(DEFAULT_ID, stg, true);
	}

	/**
	 * Returns a new shell with a given identifier.
	 * @param id new shell with identifier
	 * @throws IllegalArgumentException if the STG did not validate
	 */
	public SkbShell(String id){
		this(id, null, true);
	}

	/**
	 * Returns a new shell with a given identifier.
	 * @param id new shell with identifier
	 * @param stg an STGroup for help messages
	 * @throws IllegalArgumentException if the STG did not validate
	 */
	public SkbShell(String id, STGroup stg){
		this(id, stg, true);
	}

	/**
	 * Returns a new shell with given identifier and console flag.
	 * @param id new shell with identifier
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen
	 */
	public SkbShell(String id, boolean useConsole){
		this(id, null, useConsole);
	}

	/**
	 * Returns a new shell with given STG and console flag.
	 * @param stg an STGroup for help messages
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen (except for errors on runShell() and some help commands)
	 */
	public SkbShell(STGroup stg, boolean useConsole){
		this(DEFAULT_ID, stg, useConsole);
	}

	/**
	 * Returns a new shell with given identifier and STG.
	 * @param id new shell with identifier
	 * @param stg an STGroup for help messages
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen
	 */
	public SkbShell(String id, STGroup stg, boolean useConsole){
		//activate console output
		Skb_ConsoleUtils.USE_CONSOLE = useConsole;

		if(stg==null){
			stg = this.stg;
		}
		this.stgv = new STGroupValidator(stg, Message5WH_Builder.stChunks);
		if(!this.stgv.isValid()){
			throw new IllegalArgumentException("invalid STG");
		}


		this.id = id;

		this.standardShellCommands = new HashMap<>();
		for(SkbShellCommand cmd : StandardShellCommands.values()){
			this.standardShellCommands.put(cmd.getCommand(), cmd);
		}
		this.addedShellCommands = new HashMap<>();
	}

	/**
	 * Returns the identifier of the shell.
	 * @return shell identifier.
	 */
	public String getID() {
		return this.id;
	}

	/**
	 * Adds a shell command to the additional list.
	 * @param cmd new shell command
	 */
	public final void addShellCommand(SkbShellCommand cmd){
		if(cmd!=null){
			this.addedShellCommands.put(cmd.getCommand(), cmd);
		}
	}

	@Override
	public StrBuilder prompt(){
		StrBuilder ret = new StrBuilder(30);
		ret
			.append('[')
			.append(this.getShortName())
			.append("]> ");
		;
		return ret;
	}

	/**
	 * Prints a help screen for the command shell, for all shell commands.
	 * @param cpl command parser with arguments
	 */
	private final void _commandHelp(SkbShellLineParser cpl){
		CC_Info info = new CC_Info();
		info.setSTG(this.stgv.getInfo());

		String toHelp = cpl.getArgs();
		if(toHelp==null){
			info.add("");
			info.add("{} {}", this.getDisplayName(), this.getDescription());
			info.add("");
			TreeSet<String> sc = new TreeSet<String>();
			sc.addAll(this.standardShellCommands.keySet());
			sc.addAll(this.addedShellCommands.keySet());

			info.add("- shell commands: {}", sc);
			info.add("");
			if(this.commandHelp()!=null){
				info.add("  {}", this.commandHelp());
				info.add("");
			}
			info.add("  try: 'help <command>' for more details");
		}
		else if(this.standardShellCommands.containsKey(toHelp)){
			info.add("");
			this._doCmdHelp(info, toHelp, this.standardShellCommands);
		}
		else if(this.addedShellCommands.containsKey(toHelp)){
			info.add("");
			this._doCmdHelp(info, toHelp, this.addedShellCommands);
		}
		else{
			info.add("");
			info.add("{}", this.commandHelp(toHelp));
		}
		Skb_ConsoleUtils.conInfo(info.render());
	}

	/**
	 * Do help for a particular command
	 * @param info the info object to add help to
	 * @param toHelp the command to do the help for
	 * @param cmds list of shell commands to select help from
	 */
	private void _doCmdHelp(CC_Info info, String toHelp, Map<String, SkbShellCommand> cmds){
		SkbShellCommand sc = cmds.get(toHelp);
		TreeMap<String, SkbShellArgument> args = new TreeMap<>();
		if(sc.getArguments()!=null){
			for(SkbShellArgument ssa : sc.getArguments()){
				args.put(ssa.key(), ssa);
			}
		}

		info.add("{} {} -- {}", sc.getCommand(), args.keySet(), sc.getDescription());
		for(SkbShellArgument ssa : args.values()){
			if(ssa.addedHelp()!=null){
				info.add(" -- <{}> of type {} - {} - {}", ssa.key(), ssa.getType().name(), ssa.getDescription(), ssa.addedHelp());
			}
			else{
				info.add(" -- <{}> of type {} - {}", ssa.key(), ssa.getType().name(), ssa.getDescription());
			}
		}
		if(sc.addedHelp()!=null){
			info.add("{}", sc.addedHelp());
		}
	}

	/**
	 * Returns general help information on all commands, other than the standard and added shell commands.
	 * @return help information, can be null
	 */
	abstract protected StrBuilder commandHelp();

	/**
	 * Returns help information on specific commands, other than the standard and added shell commands.
	 * @param command command for which help information is requested
	 * @return help information
	 */
	abstract protected StrBuilder commandHelp(String command);

	/**
	 * Process an added shell command.
	 * @param command command to process
	 * @param cpl command parser
	 * @return false if the commands means to exit the shell (loop), true otherwise
	 */
	abstract protected boolean processAddedShellCommand(String command, SkbShellLineParser cpl);

	/**
	 * Process shell specific command other than the standard or added shell commands.
	 * @param command command to process
	 * @param cpl command parser
	 * @return false if the commands means to exit the shell (loop), true otherwise
	 */
	abstract protected boolean processCommand(String command, SkbShellLineParser cpl);

	/**
	 * Starts parsing a command line, if it is not empty and does not start with a comment string ("//" or "#").
	 * @param in command line string
	 * @return true if the input line is null or is empty or starts with a comment, the result of the parsing otherwise
	 */
	public final boolean parseLine(String in){
		if(in==null){
			return true;
		}
		if("".equals(in) || in.startsWith("//") || in.startsWith("#")){
			return true;
		}
		return this.parse(new SkbShellLineParser(in));
	}

	/**
	 * Parses for all main shell commands: connect, disconnect, bye, quit, exit, shutdown, ?, h, and help.
	 * @param cpl command parser with arguments
	 * @return false if the command leads to exit or shutdown, true otherwise
	 */
	private final boolean parse(SkbShellLineParser cpl){
		this.errors.clear();
		this.infos.clear();

		boolean ret = true;
		String command = cpl.getToken();
		if(this.standardShellCommands.containsKey(command)){
			ret = this._processStandardShellCommand(command, cpl);
		}
		else if(this.addedShellCommands.containsKey(command)){
			ret = this.processAddedShellCommand(command, cpl);
		}
		else{
			ret = this.processCommand(command, cpl);
		}
		return ret;
	}

	/**
	 * Processes a standard shell command.
	 * @param command command to process
	 * @param cpl parser with further arguments
	 * @return false if the commands means to exit the shell (loop), true otherwise
	 */
	private final boolean _processStandardShellCommand(String command, SkbShellLineParser cpl){
		boolean ret = true;

		StandardShellCommands cmd = null;
		for(StandardShellCommands sc : StandardShellCommands.values()){
			if(command.equals(sc.getCommand())){
				cmd = sc;
			}
			if(cmd!=null){
				break;
			}
		}

		switch(cmd){
			case BYE:
			case QUIT:
			case EXIT:
				ret = false;
				break;
			case WAIT:
				ArrayList<String> args = cpl.getArgList();
				if(args.size()>0){
					try {
						Thread.sleep(Integer.parseInt(args.get(0)));
					}
					catch (NumberFormatException e) {
						this.errors.add("{}: problem with number in wait {}", new Object[]{this.getID(), e.getMessage()});
					}
					catch (InterruptedException e) {
						this.errors.add("{}: interrupted in wait {}", new Object[]{getID(), e.getMessage()});
					}
				}
				else{
					this.errors.add("{}: no time given for wait", getID());
				}
				break;
			case HELP:
			case HELP_ABBREVIATED:
			case HELP_QMARK:
				this._commandHelp(cpl);
				break;

			default:
				//TODO
		}
		return ret;
	}

	/**
	 * Runs the shell.
	 * @return termination status, 0 on success, -1 on error
	 */
	public int runShell(){
		BufferedReader sysin = Skb_ConsoleUtils.getNbReader(Skb_ConsoleUtils.getStdIn(this.getID()), 1, 500, this);
		if(sysin==null){
			Skb_ConsoleUtils.conError("{}: could not load standard input device (stdin)", this.getShortName());
			return -1;
		}

		String in = "";
		while(this.inLoop){
			try{
				if(in!=null || "".equals(in) || "\n".equals(in)){
					if(Skb_ConsoleUtils.USE_CONSOLE==true){
						System.out.print(this.prompt());
					}
				}
				in = sysin.readLine();
				this.inLoop = this.parseLine(in);
			}
			catch(IOException ignore) {
				ignore.printStackTrace();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * Returns the display name of the shell.
	 * @return display name
	 */
	public String getDisplayName(){
		return "SKB Shell";
	}

	/**
	 * Returns the short name of the shell.
	 * @return shell short name
	 */
	public String getShortName(){
		return "skbsh";
	}

	/**
	 * Returns the description of the shell.
	 * @return shell description
	 */
	public String getDescription(){
		return "skb standard command shell";
	}

	/**
	 * Returns true if the shell has errors (last errors), false otherwise
	 * @return true if errors are collected, false otherwise
	 */
	public boolean hasErrors(){
		return (this.errors.size()==0)?false:true;
	}

	/**
	 * Returns true if the shell has infos (last infos), false otherwise
	 * @return true if infos are collected, false otherwise
	 */
	public boolean hasInfos(){
		return (this.infos.size()==0)?false:true;
	}

	/**
	 * Returns a list of collected errors from the last parsing process.
	 * @return collected errors, empty list if none occurred
	 */
	public CC_Error getLastErrors(){
		return this.errors;
	}

	/**
	 * Returns a list of collected infos from the last parsing process.
	 * @return collected infos, empty list if none occurred
	 */
	public CC_Info getLastInfos(){
		return this.infos;
	}

}
