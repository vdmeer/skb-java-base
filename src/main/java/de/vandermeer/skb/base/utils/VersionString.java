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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * A class for parsing a version string with major, minor and patch elements.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.6
 */
public class VersionString {

	/** Major part of the version. */
	int major;

	/** Minor part of the version. */
	int minor;

	/** Patch part of the version. */
	int patch;

	/** Separator character for toString. */
	char separator = '.';

	/**
	 * Creates a new version object from a string.
	 * @param version version string in the format "a.b.c" or "a-b-c" with a, b and c being integers, for instance 0.0.2.
	 * 		The string will be split, all supported characters are: comma, dot, hyphen and blank; or combinations of them.
	 * @throws IllegalArgumentException if the version string is null or empty or in the wrong format
	 */
	public VersionString(String version){
		if(StringUtils.isBlank(version)){
			throw new IllegalArgumentException("version string null or empty");
		}
		String[] ar = StringUtils.split(version, "  - , .");
		if(ar==null || ar.length!=3){
			throw new IllegalArgumentException("version string of wrong format (split)");
		}
		if(!NumberUtils.isNumber(ar[0]) && !NumberUtils.isNumber(ar[1]) && !NumberUtils.isNumber(ar[2])){
			throw new IllegalArgumentException("some parts of the version string are not numbers");
		}
		this.major = NumberUtils.toInt(ar[0]);
		this.minor = NumberUtils.toInt(ar[1]);
		this.patch = NumberUtils.toInt(ar[2]);
	}

	/**
	 * Returns the major part of the version.
	 * @return major part
	 */
	public int getMajor(){
		return this.major;
	}

	/**
	 * Returns the minor part of the version.
	 * @return minor part
	 */
	public int getMinor(){
		return this.minor;
	}

	/**
	 * Returns the patch part of the version.
	 * @return patch part
	 */
	public int getPatch(){
		return this.patch;
	}

	public void setSeparator(char c){
		this.separator = c;
	}

	@Override
	public String toString(){
		return String.format("%d%c%d%c%d", this.major, this.separator, this.minor, this.separator, this.patch);
	}
}
