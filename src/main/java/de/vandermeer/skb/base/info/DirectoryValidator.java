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

import org.apache.commons.lang3.StringUtils;

/**
 * An validator for a directory or path.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.7
 */
public class DirectoryValidator extends AbstractValidator {

	/** The local information object for the validator. */
	File info;

	/** The original directory as string if used in a constructor. */
	String original;

	/**
	 * Returns a new directory validator.
	 * @param directory the directory to validate
	 * @param option validation options
	 */
	public DirectoryValidator(String directory, ValidationOptions option){
		if(StringUtils.isBlank(directory)){
			this.errors.add("directory is null or blank");
		}
		else{
			File f = new File(directory);
			this.validate(f, option);
			if(this.isValid()){
				this.info = f;
				this.original = directory;
			}
		}
	}

	/**
	 * Returns a new directory validator.
	 * @param directory the directory to validate
	 * @param option validation options
	 */
	public DirectoryValidator(File directory, ValidationOptions option){
		if(directory==null){
			this.errors.add("directory is null");
		}
		else{
			this.validate(directory, option);
			if(this.isValid()){
				this.info = directory;
				this.original = null;
			}
		}
	}

	/**
	 * Does the actual validation
	 * @param directory the directory to validate
	 * @param option validation options
	 */
	protected void validate(File directory, ValidationOptions option){
		if(!directory.exists()){
			this.errors.add("directory <{}> does not exist in file system", directory.getAbsolutePath());
		}
		else if(!directory.isDirectory()){
			this.errors.add("directory <{}> is not a directory", directory.getAbsolutePath());
		}
		else if(directory.isHidden()){
			this.errors.add("directory <{}> is a hidden directory", directory.getAbsolutePath());
		}
		else{
			switch(option){
				case AS_SOURCE:
					if(!directory.canRead()){
						this.errors.add("directory <{}> is not readable", directory.getAbsolutePath());
					}
					break;
				case AS_SOURCE_AND_TARGET:
					if(!directory.canRead()){
						this.errors.add("directory <{}> is not readable", directory.getAbsolutePath());
					}
					if(!directory.canWrite()){
						this.errors.add("directory <{}> is not writable", directory.getAbsolutePath());
					}
					break;
				case AS_TARGET:
					if(!directory.canWrite()){
						this.errors.add("directory <{}> is not writable", directory.getAbsolutePath());
					}
					break;
			}
		}
	}

	@Override
	public File getInfo() {
		return this.info;
	}

	@Override
	public String getOriginal(){
		return this.original;
	}

}
