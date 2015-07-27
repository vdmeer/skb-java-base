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

package de.vandermeer.skb.base.message;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import de.vandermeer.skb.base.Skb_Renderable;
import de.vandermeer.skb.base.Skb_ToStringStyle;
import de.vandermeer.skb.base.info.validators.STGroupValidator;

/**
 * Standard SKB message.
 * This message class contains information following the 5WH news style (see also <a target="_new" href="https://en.wikipedia.org/wiki/Five_Ws">Wikipedia</a>): 
 * <ul>
 * 		<li>Who is it about?</li>
 * 		<li>What happened?</li>
 * 		<li>When did it take place?</li>
 * 		<li>Where did it take place?</li>
 * 		<li>Why did it happen?</li>
 * 		<li>How did it happen?</li>
 * </ul>
 * In addition to that, the class also provides for information on the reporter (the object reporting the message) and
 * the message type (i.e. an error, a warning or an information).
 * 
 * <p>
 * The class comprises methods to build a message (add*, set*), methods to access a message (get*) and some additional
 * methods to process a complete message (asST, render, toString). Additionally, it carries an enumerate {@link EMessageType} for typing messages.
 * Message properties that have a "set" method (reporter, type, when, where, who) can only be set once and are immutable afterwards.
 * For message properties that have an "add" method (how, what, why) information can be appended unlimited, but once added it cannot be removed.
 * </p>
 * 
 * <p>
 * The simplest way to create a message is to use the the builder methods. The following example creates a new message and sets all of its
 * properties:
 * <pre>
Message5WH msg = new Message5WH_Builder()
	.setWho("from " + this.getClass().getSimpleName())
	.addWhat("showing a test message")
	.setWhen(null)
	.setWhere("the class API documentation", 0, 0)
	.addWhy("as a demo")
	.addHow("added to the class JavaDoc")
	.setReporter("The Author")
	.setType(EMessageType.INFO)
	.build()
;
 * </pre>
 *This message will print to the following lines:
 * <pre>
The Author: info from Message5WHTests in the class API documentation &gt;&gt; showing a test message 
       ==&gt; as a demo 
       ==&gt; added to the class JavaDoc
 * </pre>
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public class Message5WH implements Skb_Renderable {

	/** Who is this message about? */
	private Object who;

	/** What happened? */
	private StrBuilder what;

	/** Where did it happen? */
	private ST where;

	/** The ST group for the message. */
	private STGroup stg;

	/** When did take place/happen? */
	private Object when;

	/** Why did it happen? */
	private StrBuilder why;

	/** How did it happen? */
	private StrBuilder how;

	/** Who reports it? */
	private Object reporter;

	/** Type of message. */
	private EMessageType type;

	Message5WH(Object who, StrBuilder what, ST where, STGroup stg, Object when, StrBuilder why, StrBuilder how, Object reporter, EMessageType type){
		this.who = who;
		this.what = what;
		this.where = where;
		this.stg = stg;
		this.when = when;
		this.why = why;
		this.how = how;
		this.reporter = reporter;
		this.type = type;
	}

	/**
	 * Renders the message for output.
	 * @return returns a rendered version of the message as string
	 */
	@Override
	public String render(){
		ST ret = this.stg.getInstanceOf("message5wh");
		ret.add("reporter", this.reporter);
		ret.add("type",     this.type);
		ret.add("who",      this.who);
		ret.add("when",     this.when);
		ret.add("where",    this.where);
		ret.add("what",     this.what);
		ret.add("why",      this.why);
		ret.add("how",      this.how);

		return ret.render();
	}

	/**
	 * Sets the STGroup for the message based on a validator.
	 * @param stgv the validator, which must have checked the STGroup against the required chunks defined in {@link Message5WH_Builder#stChunks}.
	 * 		If other chunks where used to validate the STG file, rendering this message might result in runtime errors
	 * @return true if STG was set, false otherwise
	 */
	public boolean setSTG(STGroupValidator stgv){
		if(stgv!=null && stgv.isValid()){
			this.stg = stgv.getInfo();
			return true;
		}
		return false;
	}

	/**
	 * Sets the reporter.
	 * @param reporter new reporter
	 * @return true on success (reporter was not null), false otherwise
	 */
	public boolean setReporter(Object reporter){
		if(reporter!=null){
			this.reporter = reporter;
			return true;
		}
		return false;
	}

	/**
	 * Returns the How? part of the message.
	 * @return How? part, null if no set
	 */
	public StrBuilder getHow(){
		return this.how;
	}

	/**
	 * Returns the reporter of the message
	 * @return message reporter, null if not set
	 */
	public Object getReporter(){
		return this.reporter;
	}

	/**
	 * Returns the type of the message.
	 * @return message type, null if not set
	 */
	public EMessageType getType(){
		return this.type;
	}

	/**
	 * Returns the What? part of the message.
	 * @return What? part, null if no set
	 */
	public StrBuilder getWhat(){
		return this.what;
	}

	/**
	 * Returns the When? part of the message.
	 * @return When? part, null if no set
	 */
	public Object getWhen(){
		return this.when;
	}

	/**
	 * Returns the Where? part of the message.
	 * @return Where? part, null if no set
	 */
	public ST getWhere(){
		return this.where;
	}

	/**
	 * Returns the Who? part of the message.
	 * @return Who? part, null if no set
	 */
	public Object getWho(){
		return this.who;
	}

	/**
	 * Returns the Why? part of the message.
	 * @return Why? part, null if no set
	 */
	public StrBuilder getWhy(){
		return this.why;
	}

	/**
	 * Changes the type of the message.
	 * Since null is a valid value for this member of the message, no further check is applied to the argument.
	 * @param type new type
	 * @return self to allow chaining
	 */
	public Message5WH changeType(EMessageType type){
		this.type = type;
		return this;
	}

	/**
	 * Returns a string representation of the message for debugging purpose, but not the rendered output.
	 * To render the message, i.e. to get the message as a text for logging or console output, please use {@link #render()}.
	 * @return (debug) string representation of the message
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, Skb_ToStringStyle.TS_STYLE)
			.append("who     ", this.who, false)
			.append("who     ", this.who)
			.append("what    ", this.what)
			.append("where   ", this.where)
			.append("when    ", this.when, false)
			.append("when    ", this.when)
			.append("why     ", this.why)
			.append("how     ", this.how)
			.append("type    ", this.type)
			.append("reporter", this.reporter, false)
			.append("reporter", this.reporter)
			.toString();
	}
}
