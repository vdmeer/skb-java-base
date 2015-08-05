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
import java.util.Properties;

/**
 * An file loader for Java property files.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.10 build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.7
 */
public class PropertyFileLoader extends AbstractLoader implements FileLoader {

	/** The source file for the loader. */
	final FileSource source;

	/**
	 * Returns a new loader for a file source.
	 * @param source the file source to be used
	 */
	public PropertyFileLoader(FileSource source){
		this.source = source;
		if(!source.isValid()){
			this.errors.add("{}: problems creating file source - {}", new Object[]{"property file loader", this.source.getInitError()});
		}
	}

	/**
	 * Returns a new loader for a file name, creating a file source automatically.
	 * @param fileName a file name with all path elements (if required).
	 * 		The file name cannot be located (see {@link FileSource#FileSource(String)} the property loader is not valid.
	 */
	public PropertyFileLoader(String fileName){
		this.source = new FileSource(fileName);
		if(!source.isValid()){
			this.errors.add("{}: problems creating file source - {}", new Object[]{"property file loader", this.source.getInitError()});
		}
	}

	@Override
	public Properties load() {
		this.errors.clear();
		if(this.validateSource()==false){
			//no valid source
			return null;
		}

		Properties ret = new Properties();
		try{
			ret.load(source.asURL().openStream());
		}
		catch (IOException e){
			this.errors.add("{}: cannot load property file {}, IO exception\n-->{}", new Object[]{"pfl", this.source.getSource(), e.getMessage()});
			return null;
		}
		catch (Exception e){
			this.errors.add("{}: cannot load property file {}, general exception\n-->{}", new Object[]{"pfl", this.source.getSource(), e.getMessage()});
			return null;
		}
		return ret;
	}

	@Override
	public FileSource getSource() {
		return this.source;
	}

}
