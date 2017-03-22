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

package de.vandermeer.skb.base.info;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.interfaces.messagesets.IsErrorSetFT;

/**
 * An abstract directory info implementation that can be configured for use as source or target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.8
 */
public abstract class AbstractDirectoryInfo {

	/** The underlying file object for the directory. */
	protected File file;

	/** The underlying URL object for the directory. */
	protected URL url;

	/** The full directory name with full path. */
	private String fullDirectoryName;

	/** A path set-as-root. */
	private String setRootPath;

	/** Local list of errors collected during process, cleared for every new validation call. */
	protected final IsErrorSetFT errors = IsErrorSetFT.create();

	/**
	 * Options for an asString method
	 * @author Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
	 */
	public enum Options {
		/** As string returns the full directory name of the directory source. */
		AS_STRING_FULL_DIRECTORY_NAME,

		/** As string returns the set-root path of the directory source. */
		AS_STRING_SET_ROOT_PATH,
	}

	/** Option for the return value of asString method. */
	protected Options asStringOpt = Options.AS_STRING_SET_ROOT_PATH;

	/**
	 * Creates a new directory info object from a directory name.
	 * This constructor will try to locate the directory in the class path first and in the file system next.
	 * @param directory name of the directory.
	 * 		Path information can be relative to any path in the class path.
	 */
	public AbstractDirectoryInfo(String directory){
		this(directory, InfoLocationOptions.TRY_CLASSPATH_THEN_FS);
	}

	/**
	 * Creates a new directory info object from a directory name with options.
	 * @param directory name of the directory.
	 * 		Path information can be relative to any path in the class path.
	 * @param option an option on how to locate the directory
	 */
	public AbstractDirectoryInfo(String directory, InfoLocationOptions option){
		if(directory==null){
			this.errors.addError("constructor(directory) - directory cannot be null");
		}
		else if(StringUtils.isBlank(directory)){
			this.errors.addError("constructor(directory) - directory cannot be blank");
		}
		else if(option==null){
			this.errors.addError("constructor(directory) - option cannot be blank");
		}
		else{
			this.init(directory, option);
		}
	}

	/**
	 * Resets all local members to null, except for the error.
	 */
	protected void reset(){
		this.url = null;
		this.file = null;
		this.fullDirectoryName = null;
		this.setRootPath = null;
	}

	/**
	 * Initialize the directory info object with any parameters presented by the constructors.
	 * @param directory a directory name
	 * @param option an option on how to locate the directory
	 */
	protected void init(String directory, InfoLocationOptions option){
		if(this.valOption()==null){
			this.errors.addError("constructor(init) - no validation option set");
			return;
		}

		try{
			if(directory!=null){
				switch(option){
					case FILESYSTEM_ONLY:
						if(this.tryFS(directory)==false){
							if(this.tryCP(directory)==false){
								this.errors.addError("constructor(init) - could not find directory <" + directory + ">, tried file system");
							}
						}
						break;
					case CLASSPATH_ONLY:
						if(this.tryCP(directory)==false){
							if(this.tryCP(directory)==false){
								this.errors.addError("constructor(init) - could not find directory <" + directory + ">>, tried using class path");
							}
						}
						break;
					case TRY_FS_THEN_CLASSPATH:
						if(this.tryFS(directory)==false){
							this.errors.clearErrorMessages();;
							if(this.tryCP(directory)==false){
								this.errors.addError("constructor(init) - could not find directory <" + directory + ">, tried file system then using class path");
							}
						}
						break;
					case TRY_CLASSPATH_THEN_FS:
						if(this.tryCP(directory)==false){
							this.errors.clearErrorMessages();;
							if(this.tryFS(directory)==false){
								this.errors.addError("constructor(init) - could not find directory <" + directory + ">, tried using class path then file system");
							}
						}
						break;
					default:
						this.errors.addError("constructor(init) - unknown location option <" + option + "> for directories");
				}
			}
		}
		catch(Exception ex){
			this.errors.addError("init() - catched unpredicted exception: " + ex.getMessage());
		}
	}

	/**
	 * Try to locate a directory in the file system.
	 * @param directory the directory to locate
	 * @return true if the directory was found, false otherwise
	 */
	protected final boolean tryFS(String directory){
		String path = directory;
		File file = new File(path);
		if(this.testDirectory(file)==true){
			//found in file system
			try{
				this.url = file.toURI().toURL();
				this.file = file;
				this.fullDirectoryName = FilenameUtils.getPath(file.getAbsolutePath());
				this.setRootPath = directory;
				return true;
			}
			catch (MalformedURLException e) {
				this.errors.addError("init() - malformed URL for file with name " + this.file.getAbsolutePath() + " and message: " + e.getMessage());
			}
		}
		return false;
	}

	/**
	 * Try to locate a directory using any directory given in the class path as set-root.
	 * @param directory the directory to locate
	 * @return true if the directory was found, false otherwise
	 */
	protected final boolean tryCP(String directory){

		String[] cp = StringUtils.split(System.getProperty("java.class.path"), File.pathSeparatorChar);
		for(String s : cp){
			if(!StringUtils.endsWith(s, ".jar") && !StringUtils.startsWith(s, "/")){
				String path = s + File.separator + directory;
				File file = new File(path);
				if(this.testDirectory(file)==true){
					//found using class path
					try{
						this.url = file.toURI().toURL();
						this.file = file;
						this.fullDirectoryName = FilenameUtils.getPath(file.getAbsolutePath());
						this.setRootPath = directory;
						return true;
					}
					catch (MalformedURLException e) {
						this.errors.addError("init() - malformed URL for file with name " + this.file.getAbsolutePath() + " and message: " + e.getMessage());
					}
				}
			}
		}
		return false;
	}

	/**
	 * Test a directory for several conditions (does exist, is not hidden etc).
	 * @param directory file object to test
	 * @return true if object points to an existing, non-hidden, readable/writable (according to options) directory, false otherwise
	 */
	protected final boolean testDirectory(File directory){
		DirectoryValidator dv = new DirectoryValidator(directory, this.valOption());
		this.errors.addAllErrors(dv.getValidationErrors());
		return !dv.getValidationErrors().hasErrors();
	}

	/**
	 * Returns the directory info object name as File object
	 * @return file object, null if none was created during initialization (no directory found)
	 */
	public File asFile(){
		return this.file;
	}

	/**
	 * Returns the directory info object name as URL object.
	 * @return URL object if the directory info object is valid, null otherwise
	 */
	public URL asURL(){
		return this.url;
	}

	/**
	 * Returns the full directory name.
	 * @return full directory name if the file info object is valid, null otherwise
	 */
	public String getFullDirecoryName(){
		return this.fullDirectoryName;
	}

	/**
	 * Returns a path set-as-root to the directory.
	 * @return the relative path if input directory was not absolute, null otherwise
	 */
	public String getSetRootPath(){
		if(this.isValid()){
			return this.setRootPath;
		}
		return null;
	}

	/**
	 * Flag reporting on the validation.
	 * @return true if the validation was successful (error list is of size 0), false otherwise (error list is of size greater than 0)
	 */
	public boolean isValid(){
		return !this.errors.hasErrors();
	}

	/**
	 * Returns the validation option which makes this abstract implementation a info object or target or both.
	 * @return validation option, if not set (null) will create init errors
	 */
	protected abstract ValidationOptions valOption();

	/**
	 * Sets the option for an asString method
	 * @param option new option, only set if not null
	 */
	public void setAsStringOption(Options option){
		if(option!=null){
			this.asStringOpt = option;
		}
	}

}
