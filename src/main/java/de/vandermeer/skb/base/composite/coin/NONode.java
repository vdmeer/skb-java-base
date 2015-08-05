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

package de.vandermeer.skb.base.composite.coin;

import de.vandermeer.skb.base.Skb_ToStringStyle;
import de.vandermeer.skb.base.composite.Com_Coin;
import de.vandermeer.skb.base.composite.Com_CoinType;

/**
 * Null object to mark a node (in a tree or table).
 * This null object can be used to identify a node in a tree (or table), as is done in some of the composite SKB types.
 * It is build as an Enumerate, so that only one instantiation of exists at runtime. This implies that
 * this type should only be used to identify a node in a tree, but not to identify a specific instance or named node.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.10 build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public enum NONode implements NullObject {
	/** The only instance for SkbTypeDefaultNode */
	get;

	@Override
	public Com_CoinType getType() {
		return Com_CoinType.NO_NODE;
	}

	@Override
	public NONode getCopy() {
		return this;
	}

	@Override
	public String toString() {
		return Skb_ToStringStyle.parentKV(Com_Coin.class, this.getClass(), this.getType()).toString();
	}
}
