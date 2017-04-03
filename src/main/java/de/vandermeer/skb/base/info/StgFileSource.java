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

/**
 * File source for an STG file, the same as {@link FileSource} plus extension check for "stg" extension.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.7
 */
public class StgFileSource extends FileSource {

	/**
	 * Creates a new file source from an existing File object with optional validation.
	 * @param file existing file object
	 * @param doValidate true if internal validation is required, false otherwise (for instance if the file object comes from another loader).
	 * 		If validation is set to false and the input argument for file is not valid, the behavior of this object and anyone using it is unpredictable.
	 * 		If validation is set to false a null validation and a validation of the getURI method of the file is still run by the constructor.
	 */
	public StgFileSource(File file, boolean doValidate){
		super(file, doValidate);
	}

	/**
	 * Creates a new file source from an existing File object.
	 * @param file existing file object
	 */
	public StgFileSource(File file){
		super(file);
	}

	/**
	 * Creates a new file source from a file name.
	 * This constructor will try to locate the file as resource first and in the file system next.
	 * @param fileName name of the file.
	 * 		The file name must include a path that is accessible.
	 * 		The file name must include path information if it is not in the root path.
	 * 		Path information can be relative to any path in the class path.
	 */
	public StgFileSource(String fileName){
		super(fileName);
	}

	/**
	 * Creates a new file source from a file name with options.
	 * @param fileName name of the file.
	 * 		The file name must include a path that is accessible.
	 * 		The file name must include path information if it is not in the root path.
	 * 		Path information can be relative to any path in the class path.
	 * @param option an option on how to locate the file
	 */
	public StgFileSource(String fileName, InfoLocationOptions option){
		super(fileName, option);
	}

	/**
	 * Creates a new file source from a path and a file name.
	 * This constructor will try to locate the file as resource first and in the file system next.
	 * @param directory a directory in which the file can be found. The directory can be absolute or relative to any directory in the class path.
	 * @param fileName the file name to locate
	 */
	public StgFileSource(String directory, String fileName){
		super(directory, fileName);
	}

	/**
	 * Creates a new file source from a path and a file name with options.
	 * @param directory directory a directory in which the file can be found. The directory can be absolute or relative to any directory in the class path.
	 * @param fileName the file name to locate
	 * @param option an option on how to locate the file
	 */
	public StgFileSource(String directory, String fileName, InfoLocationOptions option){
		super(directory, fileName, option);
	}

	/**
	 * Initialize the file source with any parameters presented by the constructors.
	 * @param file a file with all information
	 * @param directory a directory to locate a file in
	 * @param fileName a file name with or without path information
	 * @param option an option on how to locate the file (not used if parameter file is set)
	 */
	protected void init(File file, String directory, String fileName, InfoLocationOptions option){
		super.init(file, directory, fileName, option);
		if(!this.errors.hasErrors()){
			if(!"stg".equals(this.getFileExtension())){
				this.errors.addError("file name must have '.stg' extension");
				this.reset();
			}
			else{
			}
		}
	}

}
