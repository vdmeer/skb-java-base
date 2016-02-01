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

import de.vandermeer.skb.base.composite.Com_Coin;
import de.vandermeer.skb.base.composite.Com_CoinType;
import de.vandermeer.skb.base.utils.Skb_ClassUtils;

/**
 * Null object for a successful result.
 * This null object can be used to express that the result of a request is 'success', as is done in many SKB types.
 * It is build as an Enumerate, so that only one instantiation exists at runtime. This implies that
 * this type should only be used to express a 'success' result, but not to identify a specific 'success' result.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public enum NOSuccess implements NullObject {
	/** The only instance for SkbTypeDefSuccess */
	get;

	@Override
	public Com_CoinType getType() {
		return Com_CoinType.NO_SUCCESS;
	}

	@Override
	public NOSuccess getCopy() {
		return this;
	}

	@Override
	public String toString() {
		return Skb_ClassUtils.parentKV(Com_Coin.class, this.getClass(), this.getType()).toString();
	}

}
