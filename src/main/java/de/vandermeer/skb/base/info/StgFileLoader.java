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

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * An file loader for STG property files.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 150910 (10-Sep-15) for Java 1.8
 * @since      v0.0.7
 */
public class StgFileLoader extends AbstractLoader implements FileLoader {

	/** The source file for the loader. */
	final StgFileSource source;

	/**
	 * Returns a new loader for a file source.
	 * @param source a file source to load an STG from
	 */
	public StgFileLoader(StgFileSource source){
		this.source = source;
	}

	/**
	 * Returns a new loader for a file name, creating a file source automatically.
	 * @param fileName a file name to load an STG from
	 */
	public StgFileLoader(String fileName){
		this.source = new StgFileSource(fileName);
	}

	@Override
	public STGroup load() {
		if(this.validateSource()){
			STGroupFile ret = new STGroupFile(this.source.getAbsoluteName());
			return ret;
		}
		return null;
	}

	@Override
	public FileSource getSource() {
		return this.source;
	}

}
