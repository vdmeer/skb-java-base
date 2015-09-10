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

package de.vandermeer.skb.base.categories.kvt;

/**
 * A key for a switch.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 150910 (10-Sep-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public enum SwitchKey  implements IsKey_String {
	/** Enable a call switch */
	ENABLED		("enabled", "enable a scope or a set of scopes"),
	/** Disable a call switch */
	DISABLE		("disable", "disable a scoep or a set of scopes"),
	;

	/** Key */
	String key;

	/** Key description */
	String description;

	SwitchKey(String key, String description){
		this.key = key;
		this.description = description;
	}

	@Override
	public Object getDescription() {
		return this.description;
	}

	@Override
	public String _value() {
		return this.key;
	}

	//TODO
	@Override
	public String toString(){
		return SwitchKey.class.getSimpleName() + "(" + this.getClass().getSimpleName() + "):" + this.key();
	}

}
