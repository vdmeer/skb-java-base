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

package de.vandermeer.skb.base.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * File and directory utilities.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6
 */
public abstract class Skb_FileUtils {

	/**
	 * A simple filter for directories.
	 */
	public final static FileFilter DIRECTORIES_ONLY = new FileFilter(){
		public boolean accept(File f){
			if(f.exists() && f.isDirectory()){
				return true;
			}
			return false;
		}
	};

	/**
	 * A simple filter for files.
	 */
	public final static FileFilter FILES_ONLY = new FileFilter(){
		public boolean accept(File f){
			if(f.exists() && f.isFile()){
				return true;
			}
			return false;
		}
	};

	/**
	 * Read an entire file and return the contents as string.
	 * @param file input file
	 * @return string with entire file contents, empty on error
	 */
	public final static String readFile(File file){
		String ret = "";
		Scanner s = null;
		try{
			s = new Scanner(file).useDelimiter("\\Z");
			ret = s.next();
			s.close();
		}
		catch(FileNotFoundException ignore){
//			if(s!=null){
//				s.close();
//			}
		}
		return ret;
	}
}
