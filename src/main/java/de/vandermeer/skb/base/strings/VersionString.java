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

package de.vandermeer.skb.base.strings;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * A class for parsing a version string with major, minor and patch elements.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8
 * @since      v0.0.6
 */
public class VersionString {

	/** Major part of the version. */
	protected int major;

	/** Minor part of the version. */
	protected int minor;

	/** Patch part of the version. */
	protected int patch;

	/** Separator character for toString. */
	protected char separator = '.';

	/**
	 * Creates a new version object from a string.
	 * The default value for string to integer conversion is -1.
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
		this.major = NumberUtils.toInt(ar[0], -1);
		this.minor = NumberUtils.toInt(ar[1], -1);
		this.patch = NumberUtils.toInt(ar[2], -1);
	}

	/**
	 * Creates a new version string from integer values.
	 * @param major major part of the version
	 * @param minor minor part of the version
	 * @param patch patch part of the version
	 */
	public VersionString(int major, int minor, int patch){
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	/**
	 * Returns the major part of the version.
	 * @return major part
	 */
	public Integer getMajor(){
		return this.major;
	}

	/**
	 * Returns the minor part of the version.
	 * @return minor part
	 */
	public Integer getMinor(){
		return this.minor;
	}

	/**
	 * Returns the string as a map using the key "major" for the major part, the key "minor" for the minor part and the key "patch" for the patch part.
	 * @return map representation of the string
	 */
	public Map<String, Integer> toMap(){
		Map<String, Integer> ret = new HashMap<>();
		ret.put("major", this.major);
		ret.put("minor", this.minor);
		ret.put("patch", this.patch);
		return ret;
	}

	/**
	 * Returns the patch part of the version.
	 * @return patch part
	 */
	public Integer getPatch(){
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
