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

package de.vandermeer.skb.base.info.validators;

import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.STGroup;

import de.vandermeer.skb.base.utils.Skb_STUtils;

/**
 * An validator for an STGroup file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8-SNAPSHOT build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class STGroupValidator extends AbstractValidator {

	/** The local information object for the validator. */
	STGroup info;

	/** The expected chunks of the STG. */
	Map<String, List<String>> original;

	public STGroupValidator(STGroup stg, Map<String, List<String>> expectedChunks){
		if(stg==null){
			this.errors.add("stg is null");
		}
		if(expectedChunks==null){
			this.errors.add("expectedChunks is null");
		}

		if(this.getValidationErrors().size()==0){
			this.validate(stg, expectedChunks);
			if(this.isValid()){
				this.info = stg;
				this.original = expectedChunks;
			}
		}
	}

	protected void validate(STGroup stg, Map<String, List<String>> expectedChunks){
		for (String s : expectedChunks.keySet()){
			//null and "" are no valid templates, so we ignore them
			if(s!=null && !"".equals(s)){
				if(stg.isDefined(s)){
					STValidator stv = new STValidator(stg.getInstanceOf(s), expectedChunks.get(s));
					this.errors.add(stv.getValidationErrors());
//						this.errors.add("STG <{}> with {}", Skb_STUtils.getStgName(stg), errs.getMessage());
						//TODO better error message here!
				}
				else{
					this.errors.add("STGroup <{}> does not define mandatory template <{}>", Skb_STUtils.getStgName(stg), s);
				}
			}
		}
	}

	@Override
	public STGroup getInfo() {
		return this.info;
	}

	@Override
	public Map<String, List<String>> getOriginal(){
		return this.original;
	}

}
