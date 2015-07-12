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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

/**
 * Utilities for ST4 templates and template groups.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150712 (12-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Skb_STUtils {
	/**
	 * Returns a map of missing templates and/or missing arguments of templates.
	 * In detail, the given chunk list is tested against all templates of the template group and missing templates and/or
	 * missing arguments of a template are returned.
	 * @param stg template group to be tested
	 * @param expectedChunks expected arguments - map key is the expected template name and the associated list are the expected arguments
	 * @return empty map if nothing is missing, non-empty map otherwise. If the map is not empty, then a mapping of a string to null
	 *         indicates that the template did not exist at all while a mapping of a string to a list indicates that the template
	 *         did exist but the arguments noted in the list were missing.
	 */
	public static final Map<String, List<String>> getMissingChunks(STGroup stg, Map<String, List<String>> expectedChunks){
		if(stg==null || expectedChunks==null){
			return null;
		}

		Map<String, List<String>> ret=new LinkedHashMap<String, List<String>>();
		for (String s : expectedChunks.keySet()){
			if(s!=null && !"".equals(s)){	//null and "" are no valid templates, so we ignore them
				if(stg.isDefined(s)){
					List<String> missingArgs = Skb_STUtils.getMissingSTArguments(stg.getInstanceOf(s), expectedChunks.get(s));
					if(missingArgs!=null && missingArgs.size()>0){
						ret.put(s, missingArgs);
					}
				}
				else{
					ret.put(s, null);
				}
			}
		}
		return ret;
	}

	/**
	 * Returns a list of arguments missing in the given StringTemplate.
	 * In detail, the given argument list is tested against the formal arguments of the given StringTemplate. Any arguments not defined
	 * by the StringTemplate will be added to the returned list.
	 * @param st template to be tested
	 * @param expectedArguments list of expected arguments
	 * @return null if input parameters are null,
     *         empty list if all arguments are declared by the template,
     *         non-empty list if any expected argument is not declared in the template
	 */
	public static final List<String> getMissingSTArguments(ST st, List<String> expectedArguments){
		if(st==null){
			return null;
		}
		if(expectedArguments==null){
			return null;
		}

		List<String> ret;
		Map<?,?> formalArgs=st.impl.formalArguments;
		if(formalArgs==null){
			ret = new ArrayList<String>(expectedArguments);
		}
		else{
			ret = new ArrayList<String>();
			for(int i=0; i<expectedArguments.size(); i++){
				if(!formalArgs.containsKey(expectedArguments.get(i))){
					ret.add(expectedArguments.get(i));
				}
			}
		}
		return ret;
	}

	/**
	 * Returns a set of detailed error messages for missing chunks
	 * @param name name of the STGroup with missing templates (null is valid)
	 * @param missingChunks list of missing chunks
	 * @return a set of error messages detailing the problems, set is of size 0 if no missing chunks
	 */
	public static final Set<String> getMissingChunksErrorMessages(String name, Map<String, List<String>> missingChunks){
		LinkedHashSet<String> ret=new LinkedHashSet<String>();

		String msgST =  "template group<if(%1)> \\<<%1>><endif> does not specify mandatory string template \\<<%2>>";
		String msgArg = "template group<if(%1)> \\<<%1>><endif> with string template \\<<%2>> does not define argument \\<<%3>>";

		Set<String>keys = missingChunks.keySet();
		for(String missing : keys){
			List<String> missingArgs = missingChunks.get(missing);
			if(missingArgs==null || missingArgs.size()==0){
				//template is missing altogether
				ret.add(ST.format(msgST, name, missing));
			}
			else{
				// template exists but fails to specify an argument
				for(String arg : missingArgs){
					ret.add(ST.format(msgArg, name, missing, arg));
				}
			}
		}
		return ret;
	}

	/**
	 * Returns the name of the STGroup.
	 * Any file extension will be removed from the returned name.
	 * @param stg STGroup
	 * @return name of the group or null
	 */
	public static final String getStgName(STGroup stg){
		String ret = null;
		if(stg instanceof STGroupFile){
			ret = ((STGroupFile)stg).fileName;
		}
		else if(stg instanceof STGroupString){
			ret = ((STGroupString)stg).sourceName;
		}
		else if(stg instanceof STGroupDir){
			ret = ((STGroupDir)stg).groupDirName;
		}
		return StringUtils.substringBeforeLast(ret, ".");
	}
}
