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

import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * An file information writer writing strings into a file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.10 build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.9
 */
public class StringFileWriter extends AbstractWriter {

	/** The target file for the writer. */
	final FileTarget target;

	/**
	 * Returns a new writer for a file target.
	 * @param target the file target to be used
	 */
	public StringFileWriter(FileTarget target){
		this.target = target;
		if(!target.isValid()){
			this.errors.add("{}: problems creating file target - {}", new Object[]{"string file writer", this.target.getInitError()});
		}
	}

	/**
	 * Returns a new writer for a file name, creating a file target automatically.
	 * @param fileName a file name with all path elements (if required).
	 * 		The file name cannot be located (see {@link FileTarget#FileTarget(String)} the writer is not valid.
	 */
	public StringFileWriter(String fileName){
		this.target = new FileTarget(fileName);
		if(!target.isValid()){
			this.errors.add("{}: problems creating file target - {}", new Object[]{"string file writer", this.target.getInitError()});
		}
	}

	@Override
	public boolean write(Object content) {
		this.errors.clear();
		if(this.validateTarget()==false){
			//no valid target
			return false;
		}

		if(content==null){
			throw new IllegalArgumentException("content was null");
		}
		if(!(content instanceof String)){
			throw new IllegalArgumentException("content was not of type String");
		}

		try {
			FileUtils.writeStringToFile(this.target.asFile(), content.toString());
		}
		catch (IOException e) {
			this.errors.add("{}: IO exception writing to file w/writeStringToFile()", "string file writer");
			return false;
		}

		return true;
	}

	@Override
	public FileTarget getTarget() {
		return this.target;
	}

}
