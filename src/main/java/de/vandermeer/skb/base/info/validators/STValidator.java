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

import org.stringtemplate.v4.ST;

/**
 * An validator for an ST file.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.7 build 150712 (12-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class STValidator extends AbstractValidator {

	/** The local information object for the validator. */
	ST info;

	/** The expected chunks of the STG. */
	List<String> original;

	public STValidator(ST st, List<String> expectedArguments){
		if(st==null){
			this.errors.add("ST is null");
		}
		if(expectedArguments==null){
			this.errors.add("expectedArguments is null");
		}

		if(this.getValidationErrors().size()==0){
			this.validate(st, expectedArguments);
			if(this.isValid()){
				this.info = st;
				this.original = expectedArguments;
			}
		}
	}

	/**
	 * Validates the ST object and marks all missing arguments.
	 * @param st ST object for validation
	 * @param expectedArguments expected arguments to test for
	 */
	protected void validate(ST st, List<String> expectedArguments){
		Map<?,?> formalArgs = st.impl.formalArguments;
		if(formalArgs==null){
			for(String s : expectedArguments){
				this.errors.add("ST <{}> does not define argument <{}>", st.getName(), s);
			}
		}
		else{
			for(int i=0; i<expectedArguments.size(); i++){
				if(!formalArgs.containsKey(expectedArguments.get(i))){
					this.errors.add("ST <{}> does not define argument <{}>", st.getName(), expectedArguments.get(i));
				}
			}
		}
	}

	@Override
	public ST getInfo() {
		return this.info;
	}

	@Override
	public List<String> getOriginal(){
		return this.original;
	}

}
