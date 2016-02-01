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

/**
 * Class handling a string as a combination of an identifier, a version and a value part.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
<<<<<<< HEAD
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
=======
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
>>>>>>> dev
 * @since      v0.0.8
 */
public class IdVersionValueString {

	/** Identifier and version part of the string. */
	protected IdVersionString id;

	/** Value part of the string. */
	protected String value;

	/**
	 * Returns a new id/version/value combined string.
	 * The format of the input must be id/version/value (the '/' as separator).
	 * Version must be of format a.b.c with a, b and c being integers.
	 * In the version, the characters dot, comma, hyphen or blank can be used as separators.
	 * The character '/' can only be used as separator of the identifier and the version part.
	 * @param idv input string
	 * @throws IllegalArgumentException if the input string was null, empty or of wrong format
	 */
	public IdVersionValueString(String idv){
		if(StringUtils.isBlank(idv)){
			throw new IllegalArgumentException("idv string null or empty");
		}
		String[] ar = StringUtils.split(idv, "/");
		if(ar==null || ar.length!=3){
			throw new IllegalArgumentException("idv string of wrong format (split)");
		}
		this.id = new IdVersionString(ar[0], ar[1]);

		if(StringUtils.isBlank(ar[2])){
			throw new IllegalArgumentException("idv string with empty value part");
		}
		this.value = ar[2];
	}

	/**
	 * Returns the identifier part of the string
	 * @return identifier part
	 */
	public String getID(){
		return this.id.getID();
	}

	/**
	 * Returns the string as a map using the key "id" for the identifier part, the key "version" for the version part and the key "value" for the value part.
	 * @return map representation of the string
	 */
	public Map<String, String> toMap(){
		Map<String, String> ret = new HashMap<>();
		ret.put("id", this.getID());
		ret.put("version", this.getVersion().toString());
		ret.put("value", this.getValue());
		return ret;
	}

	/**
	 * Returns the version part of the string.
	 * @return version part
	 */
	public VersionString getVersion(){
		return this.id.getVersion();
	}

	/**
	 * Returns the value part of the string.
	 * @return value part
	 */
	public String getValue(){
		return this.value;
	}

}
