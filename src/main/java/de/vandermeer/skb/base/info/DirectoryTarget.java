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

import de.vandermeer.skb.base.composite.coin.CC_Error;

/**
 * A source object for a directory.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8
 * @since      v0.0.7
 */
public class DirectoryTarget extends AbstractDirectoryInfo implements InfoSource {

	/**
	 * Creates a new directory target object from a directory name.
	 * This constructor will try to locate the directory as resource first and in the file system next.
	 * @param directory name of the directory.
	 * 		Path information can be relative to any path in the class path.
	 */
	public DirectoryTarget(String directory){
		super(directory, InfoLocationOptions.TRY_CLASSPATH_THEN_FS);
	}

	/**
	 * Creates a new directory target from a directory name with options.
	 * @param directory name of the directory.
	 * 		Path information can be relative to any path in the class path.
	 * @param option an option on how to locate the directory
	 */
	public DirectoryTarget(String directory, InfoLocationOptions option){
		super(directory, option);
	}

	@Override
	public Object getSource() {
		return (this.file!=null)?this.file:this.url;
	}

	@Override
	public CC_Error getInitError() {
		return this.errors;
	}

	@Override
	protected ValidationOptions valOption() {
		return ValidationOptions.AS_TARGET;
	}

	@Override
	public String asString() {
		switch(this.asStringOpt){
			case AS_STRING_FULL_DIRECTORY_NAME:
				return this.getFullDirecoryName();
			case AS_STRING_SET_ROOT_PATH:
				return this.getSetRootPath();
		}
		return null;
	}

}
