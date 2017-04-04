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

import java.util.Locale;

import org.stringtemplate.v4.AttributeRenderer;

/**
 * An ST string renderer {@link IdVersionString}.
 * Register this renderer for the class {@link IdVersionString}.
 * Formats are: "id" the id part of the string and all formats from {@link VersionStringRenderer}.
 * If any problem occurred, the returned string will be "###";
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.8
 */
public class IdVersionStringRenderer implements AttributeRenderer {

	@Override
	public String toString(Object o, String format, Locale locale) {
		if(o instanceof IdVersionString){
			if("id".equals(format)){
				return ((IdVersionString) o).getID();
			}
			if("version".equals(format) || "version-major".equals(format) || "version-minor".equals(format) || "version-patch".equals(format)){
				VersionStringRenderer vsr = new VersionStringRenderer();
				return vsr.toString(((IdVersionString) o).getVersion(), format, locale);
			}
		}

		return "###";
	}

}
