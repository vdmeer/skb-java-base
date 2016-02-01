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

import de.vandermeer.skb.base.categories.HasDescription;
import de.vandermeer.skb.base.categories.kvt.IsKey;

/**
 * An argument for a command for the {@link SkbShell}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
<<<<<<< HEAD
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
=======
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
>>>>>>> dev
 * @since      v0.0.8
 */
public interface SkbShellArgument extends IsKey<String>, HasDescription {

	/**
	 * Returns the type of a shell argument.
	 * @return shell argument type
	 */
	SkbShellArgumentType getType();

	/**
	 * Returns additional text for an auto-generated help for the argument.
	 * @return additional help text, null if none set
	 */
	default String addedHelp(){
		return null;
	}

	/**
	 * Optional flag for the argument.
	 * @return true if the argument is optional, false otherwise (default)
	 */
	boolean isOptional();

	/**
	 * Returns an allowed set of values of same type as {@link #getType()} refers to
	 * @return value set, null if non specified (which means any value is allowed)
	 */
	Object[] valueSet();

}
