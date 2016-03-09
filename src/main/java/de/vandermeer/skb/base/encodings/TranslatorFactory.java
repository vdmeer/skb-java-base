/* Copyright 2015 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.skb.base.encodings;

/**
 * Factory for encoding translators.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160306 (06-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class TranslatorFactory {

	/**
	 * Enumerate for translation targets.
	 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
	 */
	public enum Target{
		Text2LaTeX, HTML2LaTeX, Text2HTML, HTML2AD, Text2AD
	}

	/**
	 * Returns a translator for the given target
	 * @param target translator target
	 * @return found translator, null if none found
	 */
	public static Translator getTranslator(Target target){
		if(target==null){
			return null;
		}
		switch(target){
			case Text2LaTeX:
				return new Text2Latex();
			case HTML2LaTeX:
				return new Html2Latex();
			case Text2HTML:
				return new Text2Html();
			case HTML2AD:
				return new Html2AsciiDoc();
			case Text2AD:
				return new Text2AsciiDoc();
			default:
				return null;
		}
	}
}
