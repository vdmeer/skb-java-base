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

package de.vandermeer.skb.base.shell;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * A line parser for the {@link SkbShell}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.8
 */
public class LineParser {

	/** Original line to parse. */
	protected String line;

	/** Current position of the parser in the token stream. */
	protected int tokenPosition=1;

	/**
	 * Returns a new command parser for a given command line.
	 * @param line command line
	 */
	public LineParser(String line){
		this.line = StringUtils.trim(line);
	}

	/**
	 * Sets the parsers position in the token stream.
	 * @param pos new position
	 * @return self to allow for chaining
	 */
	public LineParser setTokenPosition(int pos){
		this.tokenPosition = pos;
		return this;
	}

	/**
	 * Returns the original command line.
	 * @return original command line
	 */
	public String getLine(){
		return this.line;
	}

	/**
	 * Returns an array of all tokens being parsed.
	 * @return token array
	 */
	public String getToken(){
		if(this.tokenPosition>0){
			String[] ar = StringUtils.split(this.line, null, this.tokenPosition+1);
			if(ar!=null && ar.length>(this.tokenPosition-1)){
				return StringUtils.trim(ar[this.tokenPosition-1]);
			}
		}
		return null;
	}

	/**
	 * Returns an string of all tokens that can be considered being command arguments, using the current token position.
	 * @return argument string
	 */
	public String getArgs(){
		int count = (this.tokenPosition==0)?1:this.tokenPosition+1;
		String[] ar = StringUtils.split(this.line, null, count);
		if(ar!=null && ar.length>(this.tokenPosition)){
			return StringUtils.trim(ar[this.tokenPosition]);
		}
		return null;
	}

	/**
	 * Returns a list of all tokens that can be considered being command arguments, using the current token position.
	 * @return argument list
	 */
	public ArrayList<String> getArgList(){
		ArrayList<String> ret = new ArrayList<String>();
		String[] ar = StringUtils.split(this.getArgs());
		if(ar!=null){
			for(String s : ar){
				ret.add(StringUtils.trim(s));
			}
		}
		return ret;
	}

	/**
	 * Returns a map of all tokens that can be considered being command arguments, using the current token position.
	 * @return argument map, using ':' as key/value separator (and naturally white spaces as separator between pairs)
	 */
	public Map<String, String> getArgMap(){
		Map<String, String> ret = new LinkedHashMap<String, String>();
		String[] ar = StringUtils.split(this.getArgs(), ',');
		if(ar!=null){
			for(String s : ar){
				String[] kv = StringUtils.split(s, ":", 2);
				if(kv!=null && kv.length==2){
					ret.put(StringUtils.trim(kv[0]), StringUtils.trim(kv[1]));
				}
				else{
					//TODO error log?
				}
			}
		}
		return ret;
	}

	/**
	 * Returns an argument map fitting the given value key set (using defined types).
	 * @param arguments input arguments to test arguments against
	 * @return argument map with correct value types
	 */
	public Map<SkbShellArgument, Object> getArgMap(SkbShellArgument[] arguments){
		Map<SkbShellArgument, Object> ret = new LinkedHashMap<SkbShellArgument, Object>();
		if(arguments!=null){
			for(Entry<String, String> entry : this.getArgMap().entrySet()){
				for(SkbShellArgument ssa : arguments){
					if(ssa.key().equals(entry.getKey())){
						switch(ssa.getType()){
							case Boolean:
								ret.put(ssa, Boolean.valueOf(entry.getValue()));
								break;
							case Double:
								ret.put(ssa, Double.valueOf(entry.getValue()));
								break;
							case Integer:
								ret.put(ssa, Integer.valueOf(entry.getValue()));
								break;
							case String:
								ret.put(ssa, entry.getValue());
								break;
							default:
								System.err.println("parser.getArgMap --> argument type not yet supported: " + ssa.getType());
								break;
						}
					}
				}
			}
		}
		return ret;
	}

}
