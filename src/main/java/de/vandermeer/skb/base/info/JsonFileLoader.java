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

/**
 * An file loader for JSON property files.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.3 build 150819 (19-Aug-15) for Java 1.8
 * @since      v0.0.7
 */
public class JsonFileLoader extends AbstractLoader implements FileLoader {

	/** The source file for the loader. */
	final FileSource source;

	/**
	 * Returns a new loader for a file source.
	 * @param source the file source to be used
	 */
	public JsonFileLoader(FileSource source){
		this.source = source;
		if(!source.isValid()){
			this.errors.add("{}: problems creating file source - {}", new Object[]{"json file loader", this.source.getInitError().render()});
		}
	}

	/**
	 * Returns a new loader for a file name, creating a file source automatically.
	 * @param fileName a file name with all path elements (if required).
	 */
	public JsonFileLoader(String fileName){
		this.source = new FileSource(fileName);
		if(!source.isValid()){
			this.errors.add("{}: problems creating file source - {}", new Object[]{"json file loader", this.source.getInitError().render()});
		}
	}

	@Override
	public Object load() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileSource getSource() {
		return this.source;
	}

}
