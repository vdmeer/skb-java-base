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
import java.net.URL;

/**
 * Some methods to deal with URLs.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.4-SNAPSHOT build 150618 (18-Jun-15) for Java 1.8
 */
public abstract class Skb_UrlUtils {

	/**
	 * Returns a URL for given file name to read a file from resource.
	 * @param filename name of resource file
	 * @return URL if found in class path, null otherwise
	 */
	public static final URL getUrlFromResource(String filename){
		URL url;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		url=loader.getResource(filename);
		if(url==null){
			loader = Skb_UrlUtils.class.getClassLoader();
			url = loader.getResource(filename);
		}
		return url;
	}

	/**
	 * Returns a URL.
	 * @param filename name of the file to test for
	 * @return a valid URL of file exists in file system or as resource (in class path), null otherwise
	 */
	public static final URL getUrl(Object filename){
		URL ret = null;
		if(filename==null){
			return ret;
		}
		File f = new File(filename.toString());
		if(f.exists()){
			try{
				ret = f.toURI().toURL();
			}
			catch(Exception ignore){}
		}
		else{
			ret = Skb_UrlUtils.getUrlFromResource(filename.toString());
		}
		return ret;
	}
}
