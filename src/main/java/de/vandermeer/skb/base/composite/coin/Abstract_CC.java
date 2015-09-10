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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.composite.Com_Coin;
import de.vandermeer.skb.base.composite.Com_CoinType;
import de.vandermeer.skb.base.message.Message5WH;

/**
 * Special object for information.
 * This special object can carry a list of info messages. It allows a class or method to return or maintain detailed
 * descriptions about things that have been noticeable, as compared to a boolean return or a single message or a thrown exception.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 150910 (10-Sep-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public abstract class Abstract_CC implements Com_Coin {

	/**
	 * Local list of information messages.
	 * This list is used to chain information messages. This can be useful when several chained method calls return an information message or when
	 * a single method call wants to express a number of information messages.
	 */
	protected List<Message5WH> msglist;

	/**
	 * Creates a new object w/o any information.
	 */
	public Abstract_CC(){
		this.msglist = new ArrayList<Message5WH>();
	}

	/**
	 * Creates a new object with a list of information.
	 * @param list error list
	 */
	public Abstract_CC(List<Message5WH> list){
		this.msglist = new ArrayList<Message5WH>(list.size());
		for(Message5WH msg : list){
			this.add(msg);
		}
	}

	/**
	 * Creates a new object with a single information.
	 * @param info error for initialization
	 */
	public Abstract_CC(Message5WH info){
		this.msglist = new ArrayList<Message5WH>(1);
		this.add(info);
	}

	/**
	 * Adds a single information to the information list.
	 * @param add error to be added, null is ignored
	 * @return self to allow chaining
	 */
	public Abstract_CC add(Message5WH add){
		if(add!=null){
			this.msglist.add(add);
		}
		return this;
	}

	/**
	 * Adds another information.
	 * @param add information to be added, null is ignored
	 * @return self to allow chaining
	 */
	public Abstract_CC add(Abstract_CC add){
		if(add!=null){
			this.msglist.addAll(add.msglist);
		}
		return this;
	}

	@Override
	public Com_CoinType getType(){
		return Com_CoinType.UNSET;
	}

	@Override
	public abstract Abstract_CC getCopy();

	@Override
	public String toString() {
		StrBuilder ret = new StrBuilder(200);

		ret.append(Com_Coin.class.getSimpleName())
			.append('(')
			.append(this.getClass().getSimpleName())
			.append("): ")
			.append(this.getType())
			.append("; with messages [\n")
		;

		for(Message5WH entry : this.msglist){
			ret.append(entry.toString()).append('\n');
		}

		ret.append("]\n");
		return ret.toString();
	}

	/**
	 * Returns a copy of the list of maintained errors.
	 * @return copy of the list of errors
	 */
	public List<Message5WH> getList() {
		return new ArrayList<Message5WH>(this.msglist);
	}

	/**
	 * Returns the count of errors.
	 * @return count of errors
	 */
	public int size(){
		return this.msglist.size();
	}

	/**
	 * Renders the list of errors.
	 * @return string with rendered errors
	 */
	public String render(){
		StrBuilder ret = new StrBuilder(50);
		for(Message5WH msg : this.msglist){
			ret.appendln(msg.render());
		}
		return ret.toString();
	}

	/**
	 * Clears the error list.
	 */
	public void clear(){
		this.msglist.clear();
	}

}
