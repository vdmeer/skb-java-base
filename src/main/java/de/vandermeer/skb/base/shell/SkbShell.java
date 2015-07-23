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
import java.util.TreeSet;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.utils.Skb_ConsoleUtils;

/**
 * An abstract shell class.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.8
 */
public abstract class SkbShell {

	public static String DEFAULT_ID = "skbsh";

	/** Client runtime identifier. */
	protected final String id;

	/** Indicator if the read line loop should be maintained. */
	protected boolean inLoop = true;

	/** Shell short name. */
	protected String shortName = "skbsh";

	/** Shell display name. */
	protected String displayName = "SKB Shell ";

	/** Shell description. */
	protected String description = "skb standard command shell";

	/** Standard shell commands. */
	private final Map<String, SkbShellCommand> standardShellCommands;

	/** Additional shell commands. */
	private final Map<String, SkbShellCommand> addedShellCommands;

	/**
	 * Returns a new shell with the default identifier.
	 */
	public SkbShell(){
		this(DEFAULT_ID);
	}

	/**
	 * Returns a new shell with a given identifier.
	 * @param id new shell with identifier
	 */
	public SkbShell(String id){
		//activate console output
		Skb_ConsoleUtils.USE_CONSOLE = true;

		this.id = id;

		this.standardShellCommands = new HashMap<>();
		for(SkbShellCommand cmd : StandardShellCommands.values()){
			this._addShellCommand(cmd);
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
	 * Adds a shell command to the standard list.
	 * @param cmd new shell command
	 */
	private final void _addShellCommand(SkbShellCommand cmd){
		if(cmd!=null){
			for(String c : cmd.getCommands()){
				this.standardShellCommands.put(c, cmd);
			}
		}
	}

	/**
	 * Adds a shell command to the additional list.
	 * @param cmd new shell command
	 */
	public final void addShellCommand(SkbShellCommand cmd){
		if(cmd!=null){
			for(String c : cmd.getCommands()){
				this.addedShellCommands.put(c, cmd);
			}
		}
	}

	/**
	 * Returns a prompt for the shell.
	 * @return prompt with connection information, simply saying shell otherwise
	 */
	protected StrBuilder prompt(){
		StrBuilder ret = new StrBuilder(30);
		ret
			.append('[')
			.append(this.shortName)
			.append("]> ");
		;
		return ret;
	}

	/**
	 * Prints a help screen for the command shell, for all shell commands.
	 * @param cpl command parser with arguments
	 */
	private final void _commandHelp(SkbShellLineParser cpl){
		StrBuilder help = new StrBuilder(100);
		help.appendNewLine();
		help.append(this.displayName).appendln(" command shell");

		String toHelp = cpl.getArgs();
		if(toHelp==null){
			TreeSet<String> sc = new TreeSet<String>();
			sc.addAll(this.standardShellCommands.keySet());
			sc.addAll(this.addedShellCommands.keySet());

			help.append("- Shell commands: ");
			help.appendWithSeparators(sc, ", ");
			help.appendNewLine();
			help.append(this.commandHelp());
			help.appendNewLine();
			help.appendln("try: 'help <command>' for more details");
		}
		else if(this.standardShellCommands.containsKey(toHelp)){
			SkbShellCommand sc = this.standardShellCommands.get(toHelp);
			help.appendWithSeparators(sc.getCommands(), " | ");
			help.append(' ').appendWithSeparators(sc.getArguments(), ", ").append(' ');
			help.append("-- ").append(sc.getDescription());
		}
		else if(this.addedShellCommands.containsKey(toHelp)){
			SkbShellCommand sc = this.addedShellCommands.get(toHelp);
			help.appendWithSeparators(sc.getCommands(), " | ");
			help.append(' ').appendWithSeparators(sc.getArguments(), ", ").append(' ');
			help.append("-- ").append(sc.getDescription());
		}
		else{
			help.append(this.commandHelp(toHelp));
		}
		Skb_ConsoleUtils.conInfo("{}", help);
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
	protected final boolean parseLine(String in){
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
	protected final boolean parse(SkbShellLineParser cpl){
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
		switch(command){
			case "bye":
			case "quit":
			case "exit":
				ret = false;
				break;
			case "wait":
				ArrayList<String> args = cpl.getArgList();
				if(args.size()>0){
					try {
						Thread.sleep(Integer.parseInt(cpl.getArgList().get(0)));
					}
					catch (NumberFormatException e) {
						Skb_ConsoleUtils.conError("{}: problem with number in wait {}", new Object[]{this.getID(), e.getMessage()});
					}
					catch (InterruptedException e) {
						Skb_ConsoleUtils.conError("{}: interrupted in wait {}", new Object[]{getID(), e.getMessage()});
					}
				}
				else{
					Skb_ConsoleUtils.conInfo("{}: no time given for wait", getID());
				}
				break;
			case "?":
			case "h":
			case "help":
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
		String printEmpty = (Skb_ConsoleUtils.USE_CONSOLE==true)?this.prompt().toString():null;

		BufferedReader sysin = Skb_ConsoleUtils.getNbReader(Skb_ConsoleUtils.getStdIn(this.getID()), 1, 500, printEmpty);
		if(sysin==null){
			Skb_ConsoleUtils.conInfo("{}: could not load sysin", this.shortName);
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
}
