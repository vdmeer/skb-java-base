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

package de.vandermeer.skb.base.composite;

import de.vandermeer.skb.base.categories.kvt.IsType;

/**
 * Types for special objects, including null objects.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.7 build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public enum Com_CoinType implements IsType<String> {
	/** Node, part of a tree with no attached value but children. */
	NO_NODE ("a node object marking the node of a tree"),

	/** None, means nothing, similar to a void return type. */
	NO_NONE ("a none object, as in nothing or void"),

	/** Null, similar to a &lt;null&gt; return type */
	NO_NULL ("a null object, similar to null"),

	/** Successful return */
	NO_SUCCESS ("a success object"),

	/** An warning occurred, details in the returned object */
	SO_WARNING ("a warning object with list of warnings"),

	/** An info occurred, details in the returned object */
	SO_INFO ("an information object with list of information"),

	/** An error occurred, details in the returned object */
	SO_ERROR ("an error object with list of errors"),

	/** Type not set, default type */
	UNSET ("type is unset, probably an error in initialisation"),
	;

	private String type;

	private String description;

	Com_CoinType(String description){
		this.description = description;
		this.type = this.name();
	}

	@Override
	public String toString(){
		return IsType.class.getSimpleName() + "(" + this.getClass().getSimpleName() + "): " + this.type();
	}

	@Override
	public Object getDescription() {
		return this.description;
	}

	@Override
	public String _value() {
		return this.type;
	}

	@Override
	public String type() {
		return this.type;
	}
}
