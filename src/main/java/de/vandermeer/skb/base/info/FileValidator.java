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
 * An validator for a file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.3 build 150819 (19-Aug-15) for Java 1.8
 * @since      v0.0.7
 */
public class FileValidator extends AbstractValidator {

	/** The local information object for the validator. */
	File info;

	/** The original directory as string if used in a constructor. */
	String original;

	/**
	 * Returns a new file validator.
	 * @param file name of the file to validate
	 * @param option validation options
	 */
	public FileValidator(String file, ValidationOptions option){
		if(StringUtils.isBlank(file)){
			this.errors.add("file is null or blank");
		}
		else{
			File f = new File(file);
			this.validate(f, option);
			if(this.isValid()){
				this.info = f;
				this.original = file;
			}
		}
	}

	/**
	 * Returns a new file validator.
	 * @param file file to validate
	 * @param option validation options
	 */
	public FileValidator(File file, ValidationOptions option){
		if(file==null){
			this.errors.add("file is null");
		}
		else{
			this.validate(file, option);
			if(this.isValid()){
				this.info = file;
				this.original = null;
			}
		}
	}

	/**
	 * Does the actual validation.
	 * @param file file to validate
	 * @param option validation options
	 */
	protected void validate(File file, ValidationOptions option){
		if(!file.exists()){
			this.errors.add("file <{}> does not exist in file system", file.getAbsolutePath());
		}
		else if(!file.isFile()){
			this.errors.add("file <{}> is not a file", file.getAbsolutePath());
		}
		if(file.isHidden()){
			this.errors.add("file <{}> is a hidden file", file.getAbsolutePath());
		}
		else{
			switch(option){
				case AS_SOURCE:
					if(!file.canRead()){
						this.errors.add("file <{}> is not readable", file.getAbsolutePath());
					}
					break;
				case AS_SOURCE_AND_TARGET:
					if(!file.canRead()){
						this.errors.add("file <{}> is not readable", file.getAbsolutePath());
					}
					if(!file.canWrite()){
						this.errors.add("file <{}> is not writable", file.getAbsolutePath());
					}
					break;
				case AS_TARGET:
					if(!file.canWrite()){
						this.errors.add("file <{}> is not writable", file.getAbsolutePath());
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
