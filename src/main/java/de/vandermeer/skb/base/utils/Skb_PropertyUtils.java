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

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Methods to load properties.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.4 build 150619 (19-Jun-15) for Java 1.8
 */
public abstract class Skb_PropertyUtils {
	/** Logger instance */
	public static final Logger logger=LoggerFactory.getLogger(Skb_PropertyUtils.class);

	/**
	 * Load properties from the given file.
	 * @param file file with property information
	 * @param callee calling object, for reporting errors/logging only
	 * @return read properties
	 */
	public static final Properties loadProperties(String file, String callee){
		Properties ret = new Properties();
		try{
			ret.load(Skb_UrlUtils.getUrl(file).openStream());
		}
		catch (IOException e){
			logger.warn("{}: cannot load property file {}, IO exception\n-->{}", new Object[]{callee, file, e});
		}
		catch (Exception e){
			logger.warn("{}: cannot load property file {}, general exception\n-->{}", new Object[]{callee, file, e});
		}
		return ret;
	}
}
