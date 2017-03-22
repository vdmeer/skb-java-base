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
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import de.vandermeer.skb.interfaces.messagesets.IsErrorSetFT;

/**
 * An abstract file info implementation that can be configured for use as source or target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.7
 */
public abstract class AbstractFileInfo {

	/** The underlying file object for the file name. */
	protected File file;

	/** The underlying URL object for the file name. */
	protected URL url;

	/** The full file name with name, dot and extension (without any prefix or path elements). */
	private String fullFileName;

	/** A path set-as-root for the file if a directory was explicitly given to a constructor. */
	private String setRootPath;

	/** Local list of errors collected during process, cleared for every new validation call. */
	protected final IsErrorSetFT errors = IsErrorSetFT.create();

	/**
	 * Options for an asString method
	 * @author Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
	 */
	public enum Options {
		/** As string returns the absolute path of the file source. */
		AS_STRING_ABSOLUTE_PATH,

		/** As string returns the absolute name of the file source. */
		AS_STRING_ABSOLUTE_NAME,

		/** As string returns the full file name of the file source. */
		AS_STRING_FULL_FILE_NAME,

		/** As string returns the base name of the file of the file source. */
		AS_STRING_BASE_FILE_NAME,

		/** As string returns the file extension of the file source. */
		AS_STRING__FILE_EXTENSION,

		/** As string returns the root path of the file source. */
		AS_STRING_ROOT_PATH,

		/** As string returns the set-root path of the file source. */
		AS_STRING_SET_ROOT_PATH,

		/** As string returns the set-root name of the file source. */
		AS_STRING_SET_ROOT_NAME,
	}

	/** Option for the return value of asString method. */
	protected Options asStringOpt = Options.AS_STRING_SET_ROOT_NAME;

	/**
	 * Creates a new file info object from an existing File object with optional validation.
	 * @param file existing file object
	 * @param doValidate true if internal validation is required, false otherwise (for instance if the file object comes from another loader).
	 * 		If validation is set to false and the input argument for file is not valid, the behavior of this object and anyone using it is unpredictable.
	 * 		If validation is set to false a null validation and a validation of the getURI method of the file is still run by the constructor.
	 */
	public AbstractFileInfo(File file, boolean doValidate){
		if(doValidate==true){
			this.init(file, null, null, null);
		}
		else if(file!=null){
			try{
				this.url = file.toURI().toURL();
				this.file = file;
				this.fullFileName = FilenameUtils.getName(file.getAbsolutePath());
			}
			catch (MalformedURLException e) {
				this.errors.addError("constructor(file, boolean) - malformed URL for file with name " + this.file.getAbsolutePath() + " and message: " + e.getMessage());
			}
		}
		else{
			this.errors.addError("constructor(file, boolean) - file cannot be null");
		}
	}

	/**
	 * Creates a new file info object from an existing File object with a set-as-root directory.
	 * The file parameter will not be tested except for null and problems creating a URL.
	 * @param file existing file object
	 * @param setRoot set-as-root directory
	 */
	public AbstractFileInfo(File file, String setRoot){
		if(file==null){
			this.errors.addError("constructor(file, setRoot) - file cannot be null");
		}
		else if(setRoot==null){
			this.errors.addError("constructor(file, setRoot) - setRoot cannot be null");
		}
		else{
			try{
				this.url = file.toURI().toURL();
				this.file = file;
				this.fullFileName = FilenameUtils.getName(file.getAbsolutePath());
				this.setRootPath = setRoot;
			}
			catch (MalformedURLException e) {
				this.errors.addError("constructor(file, boolean) - malformed URL for file with name " + this.file.getAbsolutePath() + " and message: " + e.getMessage());
			}
		}
	}

	/**
	 * Creates a new file info object from an existing File object.
	 * @param file existing file object
	 */
	public AbstractFileInfo(File file){
		if(file==null){
			this.errors.addError("constructor(file) - file cannot be null");
		}
		else{
			this.init(file, null, null, null);
		}
	}

	/**
	 * Creates a new file info object from a file name.
	 * This constructor will try to locate the file as resource first and in the file system next.
	 * @param fileName name of the file.
	 * 		The file name must include a path that is accessible.
	 * 		The file name must include path information if it is not in the root path.
	 * 		Path information can be relative to any path in the class path.
	 */
	public AbstractFileInfo(String fileName){
		this(fileName, InfoLocationOptions.TRY_RESOURCE_THEN_FS);
	}

	/**
	 * Creates a new file info object from a file name with options.
	 * @param fileName name of the file.
	 * 		The file name must include a path that is accessible.
	 * 		The file name must include path information if it is not in the root path.
	 * 		Path information can be relative to any path in the class path.
	 * @param option an option on how to locate the file
	 */
	public AbstractFileInfo(String fileName, InfoLocationOptions option){
		if(fileName==null){
			this.errors.addError("constructor(fileName) - fileName cannot be null");
		}
		else if(StringUtils.isBlank(fileName)){
			this.errors.addError("constructor(fileName) - fileName cannot be blank");
		}
		else if(option==null){
			this.errors.addError("constructor(fileName) - option cannot be blank");
		}
		else{
			this.init(null, null, fileName, option);
		}
	}

	/**
	 * Creates a new file info object from a path and a file name.
	 * This constructor will try to locate the file as resource first and in the file system next.
	 * @param directory a directory in which the file can be found. The directory can be absolute or relative to any directory in the class path.
	 * @param fileName the file name to locate
	 */
	public AbstractFileInfo(String directory, String fileName){
		this(directory, fileName, InfoLocationOptions.TRY_RESOURCE_THEN_FS);
	}

	/**
	 * Creates a new file info object from a path and a file name with options.
	 * @param directory directory a directory in which the file can be found. The directory can be absolute or relative to any directory in the class path.
	 * @param fileName the file name to locate
	 * @param option an option on how to locate the file
	 */
	public AbstractFileInfo(String directory, String fileName, InfoLocationOptions option){
		if(directory==null){
			this.errors.addError("constructor(directory, fileName) - directory cannot be null");
		}
		else if(StringUtils.isBlank(directory)){
			this.errors.addError("constructor(directory, fileName) - directory cannot be blank");
		}
		else if(fileName==null){
			this.errors.addError("constructor(directory, fileName) - fileName cannot be null");
		}
		else if(StringUtils.isBlank(fileName)){
			this.errors.addError("constructor(directory, fileName) - fileName cannot be blank");
		}
		else if(option==null){
			this.errors.addError("constructor(directory, fileName) - option cannot be blank");
		}
		else{
			this.init(null, directory, fileName, option);
		}
	}

	/**
	 * Resets all local members to null, except for the error.
	 */
	protected void reset(){
		this.url = null;
		this.file = null;
		this.fullFileName = null;
	}

	/**
	 * Initialize the file info object with any parameters presented by the constructors.
	 * @param file a file with all information
	 * @param directory a directory to locate a file in
	 * @param fileName a file name with or without path information
	 * @param option an option on how to locate the file (not used if parameter file is set)
	 */
	protected void init(File file, String directory, String fileName, InfoLocationOptions option){
		if(this.valOption()==null){
			this.errors.addError("constructor(init) - no validation option set");
			return;
		}

		try{
			if(file!=null){
				if(this.testFile(file)==true){
					this.url = file.toURI().toURL();
					this.file = file;
					this.fullFileName = FilenameUtils.getName(file.getAbsolutePath());
				}
			}
			else if((directory!=null && fileName!=null) || fileName!=null){
				switch(option){
					case FILESYSTEM_ONLY:
						if(this.tryFS(directory, fileName)==false){
							if(this.tryResource(directory, fileName)==false){
								if(directory!=null){
									this.errors.addError("constructor(init) - could not find anything for directory <" + directory + "> and fileName <" + fileName + ">, tried file system");
								}
								else{
									this.errors.addError("constructor(init) - could not find anything for fileName <" + fileName + ">, tried file system");
								}
							}
						}
						break;
					case RESOURCE_ONLY:
						if(this.tryResource(directory, fileName)==false){
							if(this.tryResource(directory, fileName)==false){
								if(directory!=null){
									this.errors.addError("constructor(init) - could not find anything for directory <" + directory + "> and fileName <" + fileName + ">, tried as resource");
								}
								else{
									this.errors.addError("constructor(init) - could not find anything for fileName <" + fileName + ">, tried as resource");
								}
							}
						}
						break;
					case TRY_FS_THEN_RESOURCE:
						if(this.tryFS(directory, fileName)==false){
							this.errors.clearErrorMessages();;
							if(this.tryResource(directory, fileName)==false){
								if(directory!=null){
									this.errors.addError("constructor(init) - could not find anything for directory <" + directory + "> and fileName <" + fileName + ">, tried file system then as resource");
								}
								else{
									this.errors.addError("constructor(init) - could not find anything for fileName <" + fileName + ">, tried file system then as resource");
								}
							}
						}
						break;
					case TRY_RESOURCE_THEN_FS:
						if(this.tryResource(directory, fileName)==false){
							this.errors.clearErrorMessages();;
							if(this.tryFS(directory, fileName)==false){
								if(directory!=null){
									this.errors.addError("constructor(init) - could not find anything for directory <" + directory + "> and fileName <" + fileName + ">, tried as resource then file system");
								}
								else{
									this.errors.addError("constructor(init) - could not find anything for fileName <" + fileName + ">, tried as resource then file system");
								}
							}
						}
						break;
					default:
						this.errors.addError("constructor(init) - unknown location option <" + option + "> for files");
				}
			}
			else{
				this.errors.addError("init() - unresolvable problems with input paramters, implementation problem(!)");
			}
		}
//		catch (MalformedURLException e) {
//			this.errors.addError("init() - malformed URL for file with name " + this.file.getAbsolutePath() + " and message: " + e.getMessage());
//		}
		catch(Exception ex){
			this.errors.addError("init() - catched unpredicted exception: " + ex.getMessage());
		}
	}

	/**
	 * Try to locate a file in the file system.
	 * @param directory a directory to locate the file in
	 * @param fileName a file name with optional path information
	 * @return true if the file was found, false otherwise
	 */
	protected final boolean tryFS(String directory, String fileName){
		String path = directory + "/" + fileName;
		if(directory==null){
			path = fileName;
		}
		File file = new File(path);
		if(this.testFile(file)==true){
			//found in file system
			try{
				this.url = file.toURI().toURL();
				this.file = file;
				this.fullFileName = FilenameUtils.getName(file.getAbsolutePath());
				if(directory!=null){
					this.setRootPath = directory;
				}
				return true;
			}
			catch (MalformedURLException e) {
				this.errors.addError("init() - malformed URL for file with name " + this.file.getAbsolutePath() + " and message: " + e.getMessage());
			}
		}
		return false;
	}

	/**
	 * Try to locate a file as resource.
	 * @param directory a directory to locate the file in
	 * @param fileName a file name with optional path information
	 * @return true if the file was found, false otherwise
	 */
	protected final boolean tryResource(String directory, String fileName){
		String path = directory + "/" + fileName;
		if(directory==null){
			path = fileName;
		}

		URL url;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		url = loader.getResource(path);
		if(url==null){
			loader = AbstractFileInfo.class.getClassLoader();
			url = loader.getResource(path);
		}
		if(url==null){
			this.errors.addError("could not get Resource URL");
			return false;
		}

		this.url = url;
		this.fullFileName = FilenameUtils.getName(fileName);

		if(directory!=null){
			this.setRootPath = directory;
		}

		return true;
	}

	/**
	 * Test a file for several conditions (does exist, is not hidden etc).
	 * @param file file object to test
	 * @return true if object points to an existing, non-hidden, readable/writable (according to options) file, false otherwise
	 */
	protected final boolean testFile(File file){
		FileValidator fv = new FileValidator(file, this.valOption());
		this.errors.addAllErrors(fv.getValidationErrors());
		return !fv.getValidationErrors().hasErrors();
	}

	/**
	 * Returns the file info object name as File object
	 * @return file object, null if none was created during initialization (then the file info object is only accessible as resource from URL)
	 */
	public File asFile(){
		return this.file;
	}

	/**
	 * Returns the file info object name as URL object.
	 * @return URL object if the file info object is valid, null otherwise
	 */
	public URL asURL(){
		return this.url;
	}

	/**
	 * Returns the file extension of the file name, without the leading dot. 
	 * @return file extension if the file info object is valid, null otherwise
	 */
	public String getFileExtension(){
		if(this.isValid()){
			return FilenameUtils.getExtension(this.fullFileName);
		}
		return null;
	}

	/**
	 * Returns the base name of the file name of the object.
	 * @return base name if the file info object is valid, null otherwise
	 */
	public String getBaseFileName(){
		if(this.isValid()){
			return FilenameUtils.getBaseName(this.fullFileName);
		}
		return null;
	}

	/**
	 * Returns the full file name (base name plus dot plus extension).
	 * @return full file name if the file info object is valid, null otherwise
	 */
	public String getFullFileName(){
		return this.fullFileName;
	}

	/**
	 * Returns the absolute path of the file, without the file name
	 * @return absolute path if the file info object is valid, null otherwise
	 */
	public String getAbsolutePath(){
		if(this.isValid()){
			if(this.asFile()!=null){
				return FilenameUtils.getFullPathNoEndSeparator(this.file.getAbsolutePath());
			}
			else{
				return FilenameUtils.getFullPathNoEndSeparator(this.url.getPath());
			}
		}
		return null;
	}

	/**
	 * Returns the absolute name of the file with all path information and the file (with extension).
	 * @return the absolute name of the file with all part elements if the file info object is valid, null otherwise
	 */
	public String getAbsoluteName(){
		if(this.isValid()){
			if(this.asFile()!=null){
				return this.file.getAbsolutePath();
			}
			else{
				return this.url.getPath();
			}
		}
		return null;
	}

	/**
	 * Returns a path set-as-root to the file if a directory was explicitly given to one of the constructors.
	 * @return the relative path if the file info object is valid and a relative path was given to a constructor as directory, null otherwise
	 */
	public String getSetRootPath(){
		if(this.isValid()){
			return this.setRootPath;
		}
		return null;
	}

	/**
	 * Returns the path and file elements for the set-as-root path of the file if a directory was explicitly given to one of the constructors.
	 * @return set-as-root-file name if the file info object is valid and a relative path was given to a constructor as directory, null otherwise
	 */
	public String getSetRootName(){
		if(this.isValid() && this.setRootPath!=null){
			return this.setRootPath + "/" + this.fullFileName;
		}
		return null;
	}

	/**
	 * Returns the root path which is either the absolute path or (if any directory is set and {@link #getSetRootPath()} returns not null) the path to the set-as-root path
	 * @return absolute or prefix path for the set-as-root path if the file info object is valid and a relative path was given to a constructor as directory, null otherwise
	 */
	public String getRootPath(){
		if(this.isValid() && this.setRootPath!=null){
			return StringUtils.substringBefore(this.getAbsolutePath(), this.setRootPath);
		}
		return null;
	}

	@Override
	public String toString(){
		if(this.isValid()){
			FormattingTuple ret = MessageFormatter.arrayFormat("file[{}]\nurl [{}]\nfull / base / extension [{}, {}, {}]\nabsPath[{}]\nabsName[{}]\nsetRootPath[{}]\nsetRootName[{}]\nrootPath[{}]", new Object[]{
					this.asFile(), this.asURL(), this.getFullFileName(), this.getBaseFileName(), this.getFileExtension(), this.getAbsolutePath(), this.getAbsoluteName(), this.getSetRootPath(), this.getSetRootName(), this.getRootPath()
			});
			return ret.getMessage();
		}
		return "not valid";
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
