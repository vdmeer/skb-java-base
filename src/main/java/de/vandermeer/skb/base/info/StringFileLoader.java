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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * An file loader for reading a file into a string.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.10
 */
public class StringFileLoader extends AbstractLoader implements FileLoader {

	/** The source file for the loader. */
	final FileSource source;

	/**
	 * Returns a new loader for a file source.
	 * @param source the file source to be used
	 */
	public StringFileLoader(FileSource source){
		this.source = source;
		if(!source.isValid()){
			this.errors.addError("{}: problems creating file source - {}", new Object[]{"string file loader", this.source.getInitError().render()});
		}
	}

	/**
	 * Returns a new loader for a file name, creating a file source automatically.
	 * @param fileName a file name with all path elements (if required).
	 */
	public StringFileLoader(String fileName){
		this.source = new FileSource(fileName);
		if(!source.isValid()){
			this.errors.addError("{}: problems creating file source - {}", new Object[]{"string file loader", this.source.getInitError().render()});
		}
	}

	@Override
	public String load() {
		String ret = null;
		this.errors.clearErrorMessages();;
		if(this.validateSource()==false){
			//no valid source
			return ret;
		}

		Scanner s = null;
		try{
			s = new Scanner(this.source.asURL().openStream()).useDelimiter("\\Z");
			ret = s.next();
			s.close();
		}
		catch(FileNotFoundException ex){
			this.errors.addError("{}: unexpected file not found exception - {}", new Object[]{"string file loader", ex.getMessage()});
			return ret;
		} catch (IOException exio) {
			this.errors.addError("{}: unexpected IO not found exception - {}", new Object[]{"string file loader", exio.getMessage()});
			return ret;
		}

		return ret;
	}

	@Override
	public FileSource getSource() {
		return this.source;
	}

}
