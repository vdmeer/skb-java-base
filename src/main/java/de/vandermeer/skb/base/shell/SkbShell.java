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
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.composite.coin.CC_Info;
import de.vandermeer.skb.base.console.HasPrompt;
import de.vandermeer.skb.base.console.Skb_Console;

/**
 * A shell with flexible adding of commands and auto-generation of help and other commands.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.12-SNAPSHOT build 150811 (11-Aug-15) for Java 1.8
 * @since      v0.0.8
 */
public interface SkbShell extends HasPrompt {

	/** Default STG for info and other messages, similar to Message5WH but w/o type. */
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
	 * Returns the STGroup file for the shell default is {@link #STG}.
	 * @return STGroup file
	 */
	default STGroup getSTGroup(){
		return SkbShell.STG;
	}

	/**
	 * Sets the STGroup.
	 * @param stg the new STGroup for the shell
	 * @return true on success (no errors), false on error (errors in the error list)
	 */
	boolean setSTGroup(STGroup stg);

	/**
	 * Clears the list of last messages (infos and errors).
	 */
	default void clearLastMessages(){
		this.getLastErrors().clear();
		this.getLastInfos().clear();
	}

	/**
	 * Adds a new command interpreter to the shell.
	 * @param interpreter new command interpreter
	 * @return true on success, false on error (interpreter null, some parameters not set, already set for same command)
	 */
	default boolean addCommandInterpreter(CommandInterpreter interpreter){
		if(interpreter==null || interpreter.getCommands()==null){
			return false;
		}
		for(String cmd : interpreter.getCommandStrings()){
			this.getCommandMap().put(cmd, interpreter);
		}
		return true;
	}

	/**
	 * Adds a new set command interpreters to the shell.
	 * Any interpreter that is null will not be set.
	 * Any interpreter with missing parameters will not be set.
	 * @param interpreters new command interpreters, null array will be ignored
	 */
	default void addCommandInterpreter(CommandInterpreter[] interpreters){
		if(interpreters==null){
			return;
		}
		for(CommandInterpreter ci : interpreters){
			if(ci!=null && ci.getCommands()!=null){
				for(String cmd : ci.getCommandStrings()){
					this.getCommandMap().put(cmd, ci);
				}
			}
		}
	}

	/**
	 * Adds a new collection of command interpreters to the shell.
	 * Any interpreter that is null will not be set.
	 * Any interpreter with missing parameters will not be set.
	 * @param interpreters new command interpreters, null collection will be ignored
	 */
	default void addCommandInterpreter(Collection<CommandInterpreter> interpreters){
		if(interpreters==null){
			return;
		}
		for(CommandInterpreter ci : interpreters){
			if(ci!=null && ci.getCommands()!=null){
				for(String cmd : ci.getCommandStrings()){
					this.getCommandMap().put(cmd, ci);
				}
			}
		}
	}

	/**
	 * Returns true if the shell has errors (last errors), false otherwise.
	 * @return true if errors are collected, false otherwise
	 */
	default boolean hasErrors(){
		return (this.getLastErrors().size()==0)?false:true;
	}

	/**
	 * Returns true if the shell has infos (last infos), false otherwise.
	 * @return true if infos are collected, false otherwise
	 */
	default boolean hasInfos(){
		return (this.getLastInfos().size()==0)?false:true;
	}

	/**
	 * Returns a list of collected errors from the last parsing process.
	 * @return collected errors, empty list if none occurred
	 */
	CC_Error getLastErrors();

	/**
	 * Returns a list of collected infos from the last parsing process.
	 * @return collected infos, empty list if none occurred
	 */
	CC_Info getLastInfos();

	/**
	 * Returns the description of the shell.
	 * @return shell description
	 */
	default String getDescription(){
		return "skb standard command shell";
	}

	/**
	 * Returns the display name of the shell.
	 * @return display name
	 */
	default String getDisplayName(){
		return "SKB Shell";
	}

	/**
	 * Returns the identifier of the shell.
	 * @return shell identifier.
	 */
	String getID();

	/**
	 * Returns the prompt name of the shell.
	 * @return shell short name
	 */
	default String getPromptName(){
		return "skbsh";
	}

	/**
	 * Returns the shell's mapping of commands to associated command interpreters.
	 * @return mapping of commands to an associated interpreters
	 */
	Map<String, CommandInterpreter> getCommandMap();

	/**
	 * Returns all commands set for the shell.
	 * @return all commands of the shell. The commands should be ordered.
	 */
	Set<String> getCommands();

	@Override
	default StrBuilder prompt(){
		StrBuilder ret = new StrBuilder(30);
		ret.append('[').append(this.getPromptName()).append("]> ");
		return ret;
	}

	/**
	 * Parses a command line string.
	 * If the string is blank or starts with a single line comment character (either '//' or '#'), no action is taken and 0 is returned.
	 * @param in command line string with a command
	 * @return
	 * 			-2 if a command was found and interpreted but leads to an exit command for the shell,
	 * 			-1 if a command not part of this interpreter,
	 * 			0 if a command was found and interpretation was successful and did not lead to an exit command (exit shell),
	 * 			greater than 0 otherwise (a command found, interpreted, but some errors occurred)
	 */
	default int parseLine(String in){
		if(StringUtils.isBlank(in) || in.startsWith("//") || in.startsWith("#")){
			return 0;
		}

		this.clearLastMessages();

		int ret = 0;
		LineParser lp = new LineParser(in);
		String command = lp.getToken();
		if(this.getCommandMap().containsKey(command)){
			ret = this.getCommandMap().get(command).interpretCommand(command, lp, this);
		}
		else{
			this.getLastErrors().add("{}: comand <{}> not found in shell, taken from line: <{}>", new Object[]{this.getPromptName(), command, in});
			ret = -1;
		}

		if(this.getLastErrors().size()>0){
			Skb_Console.conError("{}", this.getLastErrors().render());
		}
		if(this.getLastInfos().size()>0){
			Skb_Console.conInfo("{}", this.getLastInfos().render());
		}
		return ret;
	}

	/**
	 * Starts the shell as a new thread.
	 * @param notify an object to notify if the shell is terminated
	 * @return the created thread for the shell
	 */
	Thread start(Object notify);

	/**
	 * Stops the shell thread.
	 */
	void stop();

	/**
	 * tests if the shell is running.
	 * @return true if the shell is running (started), false otherwise.
	 */
	boolean isRunning();

	/**
	 * Returns the exit status of the shell.
	 * @return exit status, 0 on normal termination, non-0 otherwise
	 */
	int getExitStatus();

	/**
	 * Runs the shell.
	 * @return exit status:
	 * 			0 if the shell run through without errors,
	 * 			greater than 0 otherwise
	 */
	int runShell();

	/**
	 * Runs the shell with a particular reader.
	 * @param reader the reader for the shell
	 * @return exit status:
	 * 			0 if the shell run through without errors,
	 * 			greater than 0 otherwise
	 */
	int runShell(BufferedReader reader);
}
