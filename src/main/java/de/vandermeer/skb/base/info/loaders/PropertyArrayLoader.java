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

package de.vandermeer.skb.base.info.loaders;


/**
 * An file loader for a set of Java property files.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.7 build 150712 (12-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class PropertyArrayLoader {
//public class PropertyArrayLoader extends AbstractLoader implements FileListLoader {
//
//	/** The source file set for the loader. */
//	FileListSource source;
//
//	/** A directory loader for loading a file set from a directory. */
//	DirectoryLoader dl;
//
//	/**
//	 * Returns a new loader that uses a {@link DirectoryLoader} class to load a file set.
//	 * @param source
//	 */
//	public PropertyArrayLoader(DirectoryLoader dl){
//		this.dl = dl;
//	}
//
//	/**
//	 * Returns a new loader for a file source.
//	 * @param source
//	 */
//	public PropertyArrayLoader(FileListSource source){
//		this.source = source;
//	}
//
//	/**
//	 * Returns a new loader for a file name, creating a file source automatically.
//	 * @param fileName
//	 */
//	public PropertyArrayLoader(File[] files){
//		this.source = new FileListSource(files);
//	}
//
//	@Override
//	public Properties[] load() {
//		this.errors.clear();
//
//		if(this.dl!=null){
//			if(this.dl.validateSource()==false){
//				//DirectoryLoader set but its source does not validate, error and return null
//				this.errors.addAll(this.dl.getLastErrors());
//				return null;
//			}
//			else{
//				//DirectoryLoader set and source ok, so load
//				this.source = dl.load();
//			}
//		}
//		else{
//			//no DirectoryLoader set, check the set FileSource
//			if(this.validateSource()==false){
//				return null;
//			}
//		}
//
//		//now we have a valid source, do the property load
//		Properties[] ret = new Properties[this.source.getSource().length];
//		for(int i=0; i<this.source.getSource().length; i++){
//			ret[i] = Skb_PropertyUtils.loadProperties(this.source.getSource()[i].getAbsolutePath(), this.getClass().getName());
//		}
//		return ret;
//	}
//
//	@Override
//	public FileListSource getSource() {
//		return this.source;
//	}

}
