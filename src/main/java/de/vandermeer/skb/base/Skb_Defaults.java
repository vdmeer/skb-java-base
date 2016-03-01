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

package de.vandermeer.skb.base;

/**
 * Some default definitions for the SKB.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.9-SNAPSHOT build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Skb_Defaults {

	/** A default value. */
	public static final String DEFAULT_VALUE = "##default value##";

	/** A default description. */
	public static final String DEFAULT_DESCRIPTION = "##default description##";

	/** Class needed for some category classes. */
	public class DefaultImpl {}

	/**
	 * An array of SKB jars, used to optimize for speed when searching in all known jars in ExecS.
	 */
	public static final String[] jarFilter = new String[]{
			"asciitable",
			"execs",
			"skb-base",
			"skb-categories",
			"skb-commons",
			"skb-configuration",
			"skb-examples",
			"skb-tribe",
			"svg2vector",
	};

	/**
	 * The main package for all SKB definitions, used to optimize for speed when searching in all known jars in ExecS.
	 */
	public static final String pkgFilter = "de.vandermeer.skb.";

}
