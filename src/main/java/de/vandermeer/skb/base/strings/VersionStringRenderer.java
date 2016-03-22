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
 * An ST string renderer {@link VersionString}.
 * Register this renderer for the class {@link VersionString}.
 * Formats are: "version" for the full version (same as original toString()), "version-major" for major part of version, "version-minor" for minor part of version, "version-patch" for patch part of version.
 * If any problem occurred, the returned string will be "###";
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160319 (19-Mar-16) for Java 1.8
 * @since      v0.0.8
 */
public class VersionStringRenderer implements AttributeRenderer {

	@Override
	public String toString(Object o, String format, Locale locale) {
		if(o instanceof VersionString){
			if("version".equals(format)){
				return o.toString();
			}
			if("version-major".equals(format)){
				return ((VersionString) o).getMajor().toString();
			}
			if("version-minor".equals(format)){
				return ((VersionString) o).getMinor().toString();
			}
			if("version-patch".equals(format)){
				return ((VersionString) o).getPatch().toString();
			}
		}
		return "###";
	}

}
