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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.stringtemplate.v4.STGroup;

import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.composite.coin.CC_Info;
import de.vandermeer.skb.base.console.NonBlockingReader;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.STGroupValidator;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * An abstract shell implementation with all basic features, use the {@link SkbShellFactory} or a sub-class to create a new object.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.12-SNAPSHOT build 150811 (11-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class AbstractShell implements SkbShell {

	/** Local list of errors collected during process, cleared for every new line parsing. */
	protected final CC_Error errors = new CC_Error();

	/** Local list of infos collected during process, cleared for every new line parsing. */
	protected final CC_Info infos = new CC_Info();

	/** Mapping of a set of commands to an associated command interpreter. */
	protected final Map<String, CommandInterpreter> commandMap;

	/** Flag indicating if the shell is running. */
	protected boolean isRunning = true;

	/** The shell's exit status. */
	protected int exitStatus;

	/** A thread for the shell. */
	protected Thread thread;

	/** An object for notifications. */
	protected Object notify;

	/** The local String Template Group. */
	protected STGroup stg;

	/** The shell's identifier. */
	protected String id;

	/**
	 * Returns a new shell with the default identifier and the default STG and console activated.
	 */
	protected AbstractShell(){
		this(null, null, true);
	}

	/**
	 * Returns a new shell with the default identifier and the given STG and console activated.
	 * @param stg an STGroup for help messages, uses default if given STG is not valid
	 * @throws IllegalArgumentException if the STG did not validate
	 */
	protected AbstractShell(STGroup stg){
		this(null, stg, true);
	}

	/**
	 * Returns a new shell with given STG and console flag.
	 * @param stg an STGroup for help messages, uses default if given STG is not valid
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen (except for errors on runShell() and some help commands)
	 */
	protected AbstractShell(STGroup stg, boolean useConsole){
		this(null, stg, useConsole);
	}

	/**
	 * Returns a new shell with a given identifier, standard STGroup and console activated.
	 * @param id new shell with identifier
	 * @throws IllegalArgumentException if the STG did not validate
	 */
	protected AbstractShell(String id){
		this(id, null, true);
	}

	/**
	 * Returns a new shell with given identifier and console flag with standard STGroup.
	 * @param id new shell with identifier
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen
	 */
	protected AbstractShell(String id, boolean useConsole){
		this(id, null, useConsole);
	}

	/**
	 * Returns a new shell with a given identifier and STGroup plus console activated.
	 * @param id new shell with identifier
	 * @param stg an STGroup for help messages, uses default if given STG is not valid
	 * @throws IllegalArgumentException if the STG did not validate
	 */
	protected AbstractShell(String id, STGroup stg){
		this(id, stg, true);
	}

	/**
	 * Returns a new shell with given identifier and console flag.
	 * @param id new shell with identifier
	 * @param stg an STGroup for help messages, uses default if given STG is not valid
	 * @param useConsole flag to use (true) or not to use (false) console, of false then no output will happen
	 */
	protected AbstractShell(String id, STGroup stg, boolean useConsole){
		//activate console output
		Skb_Console.USE_CONSOLE = useConsole;
		this.commandMap = new HashMap<>();

		if(stg!=null){
			this.setSTGroup(stg);
		}
		if(this.stg==null){
			//fall back
			this.stg = AbstractShell.STG;
		}

		this.id = (id!=null)?id:"skbsh";
	}

	@Override
	public String getID(){
		return this.id;
	}

	@Override
	public CC_Error getLastErrors() {
		return this.errors;
	}

	@Override
	public CC_Info getLastInfos() {
		return this.infos;
	}

	@Override
	public Map<String, CommandInterpreter> getCommandMap() {
		return this.commandMap;
	}

	@Override
	public Set<String> getCommands() {
		Set<String> ret = new TreeSet<>();
		ret.addAll(this.commandMap.keySet());
		return ret;
	}

	@Override
	public Thread start(Object notify) {
		if(this.thread==null){
			this.notify = notify;
			this.thread = new Thread(){
				@Override
				public void run(){
					runShell();
				}
			};
			this.thread.start();
			return this.thread;
		}
		return null;
	}

	@Override
	public void stop() {
		if(this.thread!=null){
			this.notify = null;
			synchronized(this.thread){
				this.thread.interrupt();
			}
			this.thread = null;
		}
		this.isRunning = false;
	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public int getExitStatus() {
		return this.exitStatus;
	}

	@Override
	public int runShell(BufferedReader reader){
		BufferedReader sysin = reader;

		if(sysin==null){
			Skb_Console.conError("{}: could not load standard input device (stdin)", this.getPromptName());
			return -1;
		}

		this.isRunning = true;
		String in = "";

		while(isRunning()){
			this.exitStatus = -99;
			try{
				if(in!=null || "".equals(in) || "\n".equals(in)){
					if(Skb_Console.USE_CONSOLE==true){
						System.out.print(this.prompt());
					}
				}

				in = sysin.readLine();
				this.exitStatus = this.parseLine(in);

				if(this.exitStatus==-2){
					this.isRunning = false;
				}
			}
			catch(IOException ignore) {
				//TODO
				ignore.printStackTrace();
			}
			catch(Exception ex){
				//	TODO
				ex.printStackTrace();
			}
		}

		if(this.notify!=null){
			synchronized(this.notify){
				this.notify.notify();
			}
		}
		this.isRunning = false;
		return this.exitStatus;
	}

	@Override
	public int runShell(){
		BufferedReader sysin = NonBlockingReader.getNbReader(this.getID(), 1, 500, this);
//		BufferedReader sysin = Skb_Console.getStdIn(this.getID());
		return this.runShell(sysin);
	}

	@Override
	public boolean setSTGroup(STGroup stg) {
		if(stg==null){
			this.getLastErrors().add("{}: STG cannot be null", this.getPromptName());
			return false;
		}
		STGroupValidator stgv =new STGroupValidator(stg, Message5WH_Builder.stChunks);
		if(!stgv.isValid()){
			this.getLastErrors().add(stgv.getValidationErrors());
			return false;
		}
		this.stg = stg;
		return true;
	}
}
