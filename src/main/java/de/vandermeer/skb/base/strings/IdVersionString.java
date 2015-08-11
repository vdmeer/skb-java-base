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
 * Class handling a string as a combination of an identifier and a version part.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.8
 */
public class IdVersionString {

	/** Identifier part of the string. */
	protected String id;

	/* Version part of the string. */
	protected VersionString version;

	/**
	 * Returns a new id/version combined string.
	 * The format of the input must be id/version (the '/' as separator).
	 * Version must be of format a.b.c with a, b and c being integers.
	 * In the version, the characters dot, comma, hyphen or blank can be used as separators.
	 * The character '/' can only be used as separator of the identifier and the version part.
	 * @param idv input string
	 * @throws IllegalArgumentException if the input string was null, empty or of wrong format
	 */
	public IdVersionString(String idv){
		if(StringUtils.isBlank(idv)){
			throw new IllegalArgumentException("idv string null or empty");
		}
		String[] ar = StringUtils.split(idv, "/");
		if(ar==null || ar.length!=2){
			throw new IllegalArgumentException("idv string of wrong format (split)");
		}
		if(StringUtils.isBlank(ar[0])){
			throw new IllegalArgumentException("idv string with empty identifier part");
		}
		this.id = ar[0];
		this.version = new VersionString(ar[1]);
	}

	/**
	 * Returns a new id/version combined string.
	 * Version must be of format a.b.c with a, b and c being integers.
	 * In the version, the characters dot, comma, hyphen or blank can be used as separators.
	 * @param id identifier part of the string
	 * @param version version part of the string
	 * @throws IllegalArgumentException if the input string was null, empty or of wrong format
	 */
	public IdVersionString(String id, String version){
		if(StringUtils.isBlank(id)){
			throw new IllegalArgumentException("id part null or empty");
		}
		this.id = id;
		this.version = new VersionString(version);
	}

	/**
	 * A private empty constructor to create a NULL string.
	 */
	private IdVersionString(){
		
	}

	@Override
	public String toString(){
		return this.id + "/" + this.version;
	}

	/**
	 * Returns the identifier part of the string
	 * @return identifier part
	 */
	public String getID(){
		return this.id;
	}

	/**
	 * Returns the version part of the string.
	 * @return version part
	 */
	public VersionString getVersion(){
		return this.version;
	}

	/**
	 * Returns the string as a map using the key "id" for the identifier part and the key "version" for the version part.
	 * @return map representation of the string
	 */
	public Map<String, String> toMap(){
		Map<String, String> ret = new HashMap<>();
		ret.put("id", this.getID());
		ret.put("version", this.getVersion().toString());
		return ret;
	}

	/**
	 * Tests if the parameter is the same id and version.
	 * @param obj object to test
	 * @return true if the parameter is an IdVersionString or a string with the same identifier and the same version, false otherwise
	 */
	public boolean sameAs(Object obj){
		if(obj==null){
			return false;
		}
		if(obj instanceof String || obj instanceof IdVersionString){
			if(this.toString().equals(obj.toString())){
				return true;
			}
		}
		return false;
	}

	public static String NULL_STRING_RETURN = "NULL";

	/**
	 * A NULL IdVersionString for exceptional cases.
	 */
	public static IdVersionString NULL_STRING = new IdVersionString(){
		@Override
		public String toString(){
			return NULL_STRING_RETURN;
		}

		@Override
		public String getID(){
			return NULL_STRING_RETURN;
		}

		@Override
		public VersionString getVersion(){
			return null;
		}

		@Override
		public boolean sameAs(Object obj){
			if(obj instanceof IdVersionString && ((IdVersionString)obj).getID()==NULL_STRING_RETURN){
				return true;
			}
			return false;
		}
	};
}
