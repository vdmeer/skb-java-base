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

package de.vandermeer.skb.base.info.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A source object for an array of File objects.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class FileListSource extends AbstractSource {

	/** The set of files. */
	final List<File> files;

	/**
	 * Returns a new source for an array of File objects.
	 * @param files a list of files for the source
	 */
	public FileListSource(List<File> files){
		if(files==null){
			this.files = null;
			this.errors.add("constructor(List<File>) - files cannot be null");
		}
		else{
			this.files = files;
		}
	}

	@Override
	public List<File> getSource(){
		return this.files;
	}

	/**
	 * Returns file list as a array.
	 * @return an array of files
	 */
	public File[] getSourceAsArray(){
		return this.getSource().toArray(new File[0]);
	}

	/**
	 * Returns file list as an array of {@link FileSource} objects.
	 * @return an array of FileSource objects
	 */
	public FileSource[] getSourceAsFileSourceArray(){
		return this.getSourceAsFileSourceList().toArray(new FileSource[0]);
	}

	/**
	 * Returns file list as a list of {@link FileSource} objects.
	 * @return a list of FileSource objects
	 */
	public List<FileSource> getSourceAsFileSourceList(){
		List<FileSource> ret = new ArrayList<>();
		for(File f : this.getSource()){
			ret.add(new FileSource(f, false));
		}
		return ret;
	}

	/**
	 * Returns file list as a list of {@link FileSource} objects.
	 * @param setRoot a directory set-as-root for the file source
	 * @return a list of FileSource objects
	 */
	public List<FileSource> getSourceAsFileSourceList(String setRoot){
		List<FileSource> ret = new ArrayList<>();
		for(File f : this.getSource()){
			ret.add(new FileSource(f, setRoot));
		}
		return ret;
	}
}
