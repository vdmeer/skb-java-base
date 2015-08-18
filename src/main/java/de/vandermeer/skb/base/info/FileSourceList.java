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
import java.util.ArrayList;
import java.util.List;

/**
 * A source object for an array of File objects.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.7
 */
public class FileSourceList extends AbstractSource {

	/** The set of files. */
	final List<FileSource> source;

	/**
	 * Returns a new source for an array of {@link FileSource} objects.
	 * @param fileSources a list of files for the source
	 */
	public FileSourceList(List<FileSource> fileSources){
		if(fileSources==null){
			this.source = null;
			this.errors.add("constructor(List<FileSource>) - fileSources cannot be null");
		}
		else{
			this.source = fileSources;
		}
	}

	@Override
	public List<FileSource> getSource(){
		return this.source;
	}

	/**
	 * Returns file list as a array.
	 * @return an array of files
	 */
	public FileSource[] getSourceAsArray(){
		return this.getSource().toArray(new FileSource[0]);
	}

	/**
	 * Returns file list as an array of File objects.
	 * @return an array of FileSource objects
	 */
	public File[] getSourceAsFileArray(){
		return this.getSourceAsFileSourceList().toArray(new File[0]);
	}

	/**
	 * Returns file list as a list of File objects.
	 * @return a list of FileSource objects
	 */
	public List<File> getSourceAsFileSourceList(){
		List<File> ret = new ArrayList<>();
		for(FileSource fs : this.getSource()){
			ret.add(fs.asFile());
		}
		return ret;
	}

}
