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

package de.vandermeer.skb.base.info.validators;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import de.vandermeer.skb.base.info.ValidationOptions;

/**
 * An validator for a directory or path.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class DirectoryValidator extends AbstractValidator {

	/** The local information object for the validator. */
	File info;

	/** The original directory as string if used in a constructor. */
	String original;

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

	protected void validate(File dir, ValidationOptions option){
		if(!dir.exists()){
			this.errors.add("directory <{}> does not exist in file system", dir.getAbsolutePath());
		}
		else if(!dir.isDirectory()){
			this.errors.add("directory <{}> is not a directory", dir.getAbsolutePath());
		}
		else if(dir.isHidden()){
			this.errors.add("directory <{}> is a hidden directory", dir.getAbsolutePath());
		}
		else{
			switch(option){
				case AS_SOURCE:
					if(!dir.canRead()){
						this.errors.add("directory <{}> is not readable", dir.getAbsolutePath());
					}
					break;
				case AS_SOURCE_AND_TARGET:
					if(!dir.canRead()){
						this.errors.add("directory <{}> is not readable", dir.getAbsolutePath());
					}
					if(!dir.canWrite()){
						this.errors.add("directory <{}> is not writable", dir.getAbsolutePath());
					}
					break;
				case AS_TARGET:
					if(!dir.canWrite()){
						this.errors.add("directory <{}> is not writable", dir.getAbsolutePath());
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
