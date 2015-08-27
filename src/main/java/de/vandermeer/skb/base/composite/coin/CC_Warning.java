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

import de.vandermeer.skb.base.composite.Com_CoinType;
import de.vandermeer.skb.base.message.E_MessageType;
import de.vandermeer.skb.base.message.FormattingTupleWrapper;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * Special object for a warnings.
 * This special object can carry a list of warning messages. It allows a class or method to return or maintain detailed
 * descriptions about things that potentially went wrong, as compared to a boolean return or a single warning message or a thrown exception.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.4 build 150827 (27-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public final class CC_Warning extends Abstract_CC {
	/**
	 * Creates a new DefaultWarning w/o any warning messages.
	 */
	public CC_Warning(){
		super();
	}

	/**
	 * Adds a single warning message.
	 * @param add warning to be added, null is ignored
	 * @return self to allow chaining
	 */
	@Override
	public CC_Warning add(Message5WH add){
		if(add!=null){
			super.add(add.changeType(E_MessageType.WARNING));
		}
		return this;
	}

	/**
	 * Adds a new single warning using the SLF4J FormattingTuple wrapped in a message.
	 * @param what the what message with substitutions for objects
	 * @param obj the object for the substitution
	 * @return self to allow chaining
	 */
	public CC_Warning add(String what, Object ... obj){
		if(what!=null){
			super.add(new Message5WH_Builder().addWhat(new FormattingTupleWrapper(what, obj)).setType(E_MessageType.WARNING).build());
		}
		return this;
	}

	/**
	 * Adds another warning.
	 * @param add warning to be added, null is ignored
	 * @return self to allow chaining
	 */
	public CC_Warning add(CC_Warning add){
		if(add!=null){
			this.msglist.addAll(add.msglist);
		}
		return this;
	}

	@Override
	public Com_CoinType getType(){
		return Com_CoinType.SO_WARNING;
	}

	@Override
	public CC_Warning getCopy() {
		CC_Warning ret = new CC_Warning();
		ret.msglist.addAll(this.msglist);
		return ret;
	}

}
