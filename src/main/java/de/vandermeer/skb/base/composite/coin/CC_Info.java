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

import java.util.List;

import de.vandermeer.skb.base.composite.Com_CoinType;
import de.vandermeer.skb.base.message.EMessageType;
import de.vandermeer.skb.base.message.FormattingTupleWrapper;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * Special object for information.
 * This special object can carry a list of info messages. It allows a class or method to return or maintain detailed
 * descriptions about things that have been noticeable, as compared to a boolean return or a single info message or a thrown exception.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8-SNAPSHOT build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public final class CC_Info extends Abstract_CC {
	/**
	 * Creates a new DefaultInfo w/o any information messages.
	 */
	public CC_Info(){
		super();
	}

	/**
	 * Creates a new DefaultInfo with a list of information.
	 * @param list info list
	 */
	public CC_Info(List<Message5WH> list){
		super(list);
	}

	/**
	 * Creates a new DefaultInfo with a single information.
	 * @param info info for initialization
	 */
	public CC_Info(Message5WH info){
		super(info);
	}

	/**
	 * Adds a single info to the information list.
	 * @param add error to be added, null is ignored
	 * @return self to allow chaining
	 */
	@Override
	public CC_Info add(Message5WH add){
		if(add!=null){
			super.add(add.changeType(EMessageType.INFO));
		}
		return this;
	}

	/**
	 * Adds a new single info using the SLF4J FormattingTuple wrapped in a message.
	 * @param what the what message with substitutions for objects
	 * @param obj the object for the substitution
	 * @return self to allow chaining
	 */
	public CC_Info add(String what, Object ... obj){
		if(what!=null){
			super.add(new Message5WH_Builder().addWhat(new FormattingTupleWrapper(what, obj)).setType(EMessageType.INFO).build());
		}
		return this;
	}

	/**
	 * Adds another information.
	 * @param add info to be added, null is ignored
	 * @return self to allow chaining
	 */
	public CC_Info add(CC_Info add){
		if(add!=null){
			this.msglist.addAll(add.msglist);
		}
		return this;
	}

	@Override
	public Com_CoinType getType(){
		return Com_CoinType.SO_INFO;
	}

	@Override
	public CC_Info getCopy() {
		return new CC_Info(this.msglist);
	}
}
