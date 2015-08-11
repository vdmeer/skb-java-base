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

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.DirectoryLoader;
import de.vandermeer.skb.base.info.FileSourceList;
import de.vandermeer.skb.base.info.FileSource;
import de.vandermeer.skb.base.info.StringFileLoader;

/**
 * An interpreter for the 'run' shell command.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.12-SNAPSHOT build 150811 (11-Aug-15) for Java 1.8
 * @since      v0.0.10
 */
public class Ci_ScRun extends AbstractCommandInterpreter {

	/** Last script run by the shell. */
	protected String lastScript;

	/** Flag for printing progress during script execution. */
	protected boolean printProgress = true;

	/** Extension for script files. */
	public static String SCRIPT_FILE_EXTENSION = "ssc";

	/** The argument for script file names for run. */
	public static final SkbShellArgument ARG_SCRIPT_RUN = SkbShellFactory.newArgument(
			"script", false, SkbShellArgumentType.String, null, "name (filename) of a script", "script files use the extension " + SCRIPT_FILE_EXTENSION + ", filename can be w/o extension"
	);

	/** The argument for script file names for info. */
	public static final SkbShellArgument ARG_SCRIPT_INFO= SkbShellFactory.newArgument(
			"script", false, SkbShellArgumentType.String, null, "name (filename) of a script", "the information provided is taken from a line in the script that starts with //**"
	);

	/** The argument for directory names. */
	public static final SkbShellArgument ARG_DIRECTORY = SkbShellFactory.newArgument(
			"directory", false, SkbShellArgumentType.String, null, "directory to search in, can be in file system or class path", null
	);

	/** The command for running scripts. */
	public static final SkbShellCommand SC_RUN = SkbShellFactory.newCommand(
			"scrun",
			ARG_SCRIPT_RUN,
			SkbShellFactory.SIMPLE_COMMANDS, "runs a <script> with shell commands", null
	);

	/** The command for info on scripts. */
	public static final SkbShellCommand SC_INFO = SkbShellFactory.newCommand(
			"scinfo",
			ARG_SCRIPT_INFO,
			SkbShellFactory.SIMPLE_COMMANDS, "provides information about <script> file if available", null
	);

	/** The command for ls on scripts. */
	public static final SkbShellCommand SC_LS = SkbShellFactory.newCommand(
			"scls",
			ARG_DIRECTORY,
			SkbShellFactory.SIMPLE_COMMANDS, "lists available script files", null
	);

	/**
	 * Returns an new 'run' command interpreter which will print progress information.
	 */
	public Ci_ScRun(){
		this(true);
	}

	/**
	 * Returns an new 'run' command interpreter.
	 * @param printProgress flag for printing progress when executing commands from a file, true for yes, false for no
	 */
	public Ci_ScRun(boolean printProgress){
		super(new SkbShellCommand[]{SC_RUN, SC_INFO, SC_LS,});
		this.printProgress = printProgress;
	}

	@Override
	public int interpretCommand(String command, LineParser lp, SkbShell shell) {
		if(StringUtils.isBlank(command) || lp==null){
			return -3;
		}
		if(!SC_RUN.getCommand().equals(command) && !SC_INFO.getCommand().equals(command) && !SC_LS.getCommand().equals(command)){
			return -1;
		}

		if(SC_RUN.getCommand().equals(command)){
			String fileName = this.getFileName(lp);
			String content = this.getContent(fileName, shell);
			if(content==null){
				return 1;
			}

			Skb_Console.conInfo("");
			Skb_Console.conInfo("{}: running file {}", new Object[]{shell.getPromptName(), fileName});
			for(String s : StringUtils.split(content, '\n')){
				if(this.printProgress==true && Skb_Console.USE_CONSOLE==true){
					System.out.print(".");
				}
				shell.parseLine(s);
			}
			this.lastScript = fileName;

			//clear messages, they all should have been out already
			shell.clearLastMessages();
		}
		else if(SC_LS.getCommand().equals(command)){
			String directory = lp.getArgs();
			IOFileFilter fileFilter = new WildcardFileFilter(new String[]{
						"*.ssc"
			});
			DirectoryLoader dl = new CommonsDirectoryWalker(directory, DirectoryFileFilter.INSTANCE, fileFilter);
			if(dl.getLoadErrors().size()>0){
				shell.getLastErrors().add(dl.getLoadErrors());
				return 1;
			}
			FileSourceList fsl = dl.load();
			if(dl.getLoadErrors().size()>0){
				shell.getLastErrors().add(dl.getLoadErrors());
				return 1;
			}
			for(FileSource fs : fsl.getSource()){
				//TODO need to adapt to new source return
				Skb_Console.conInfo("{}: script file - dir <{}> file <{}>", new Object[]{shell.getPromptName(), directory, fs.getSetRootName()});
			}
		}
		else if(SC_INFO.getCommand().equals(command)){
			String fileName = this.getFileName(lp);
			String content = this.getContent(fileName, shell);
			if(content==null){
				return 1;
			}
			String[] lines = StringUtils.split(content, "\n");

			String info = null;
			for(String s : lines){
				if(s.startsWith("//**")){
					info = StringUtils.substringAfter(s, "//**");
					break;
				}
			}
			if(info!=null){
				Skb_Console.conInfo("{}: script {} - info: {}", new Object[]{shell.getPromptName(), fileName, info});
			}
		}
		return 0;
	}

	/**
	 * Returns a file name taken as argument from the line parser with fixed extension
	 * @param lp line parser
	 * @return file name, null if none found
	 */
	protected String getFileName(LineParser lp){
		String fileName = lp.getArgs();
		if(fileName==null){
			fileName = this.lastScript;
		}
		else if(!fileName.endsWith("." + SCRIPT_FILE_EXTENSION)){
			fileName += "." + SCRIPT_FILE_EXTENSION;
		}
		return fileName;
	}

	/**
	 * Returns the content of a string read from a file.
	 * @param fileName name of file to read from
	 * @param shell a shell for error messages
	 * @return null if no content could be found (error), content in string otherwise
	 */
	protected String getContent(String fileName, SkbShell shell){
		StringFileLoader sfl = new StringFileLoader(fileName);
		if(sfl.getLoadErrors().size()>0){
			shell.getLastErrors().add(sfl.getLoadErrors());
			return null;
		}

		String content = sfl.load();
		if(sfl.getLoadErrors().size()>0){
			shell.getLastErrors().add(sfl.getLoadErrors());
			return null;
		}
		if(content==null){
			shell.getLastErrors().add("run: unexpected problem with run script, content was null");
			return null;
		}
		return content;
	}
}
