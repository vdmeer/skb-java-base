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

import de.vandermeer.asciitable.commons.ObjectToStringStyle;
import de.vandermeer.skb.base.utils.Skb_Renderable;

/**
 * Standard SKB message.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 150910 (10-Sep-15) for Java 1.8
 * @since      v0.0.1
 */
public class Message5WH implements Skb_Renderable {

	/** Who is this message about? */
	private Object who;

	/** What happened? */
	private StrBuilder what;

	/** Where did it happen: location. */
	protected Object whereLocation;

	/** Where did it happen: line. */
	protected int whereLine;

	/** Where did it happen: column. */
	protected int whereColumn;

	/** When did take place/happen? */
	private Object when;

	/** Why did it happen? */
	private StrBuilder why;

	/** How did it happen? */
	private StrBuilder how;

	/** Who reports it? */
	private Object reporter;

	/** Type of message. */
	private E_MessageType type;

	Message5WH(Object who, StrBuilder what, Object whereLocation, int whereLine, int whereColumn, Object when, StrBuilder why, StrBuilder how, Object reporter, E_MessageType type){
		this.who = who;
		this.what = what;
		this.whereLocation = whereLocation;
		this.whereLine = whereLine;
		this.whereColumn = whereColumn;
		this.when = when;
		this.why = why;
		this.how = how;
		this.reporter = reporter;
		this.type = type;
	}

	@Override
	public String render(){
		StrBuilder ret = new StrBuilder(100);

		StrBuilder where = null;
		if(this.getWhereLocation()!=null){
			where = new StrBuilder(30);
			where.append(this.whereLocation);
			if(this.whereLine>0 && this.whereColumn>0){
				where.append(' ').append(this.whereLine).append(':').append(this.whereColumn).append(' ');
			}
			else if(this.whereLine<1 && this.whereColumn<1){
				where.append(' ');
			}
			else if(this.whereLine<1){
				where.append(" -:").append(this.whereColumn).append(' ');
			}
			else if(this.whereColumn<1){
				where.append(' ').append(this.whereLine).append(":- ");
			}
		}

		if(this.reporter!=null){
			ret.append(this.reporter).append(": ");
		}
		if(this.what!=null){
			ret.append(this.type.name().toLowerCase()).append(' ');
		}
		if(this.who!=null){
			ret.append(this.who).append(' ');
		}
		if(this.when!=null){
			ret.append("at (").append(this.when).append(") ");
		}

		if(where!=null){
			ret.append("in ").append(where);
		}

		if(this.type==E_MessageType.ERROR){
			ret.append("-> ");
		}
		if(this.what!=null){
			ret.append(this.what);
		}

		if(this.why!=null){
			ret.appendNewLine();
			ret.append("        ==> ").append(this.why);
		}
		if(this.how!=null){
			ret.appendNewLine();
			ret.append("        ==> ").append(this.how);
		}
		return ret.toString();
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
	public E_MessageType getType(){
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
	 * Returns the Where? location part of the message.
	 * @return Where? location part, null if no set
	 */
	public Object getWhereLocation(){
		return this.whereLocation;
	}

	/**
	 * Returns the Where? line part of the message.
	 * @return Where? line part, 0 if no set
	 */
	public int getWhereLine(){
		return this.whereLine;
	}

	/**
	 * Returns the Where? column part of the message.
	 * @return Where? column part, null if no set
	 */
	public int getWhereColumn(){
		return this.whereColumn;
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
	public Message5WH changeType(E_MessageType type){
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
		return new ToStringBuilder(this, ObjectToStringStyle.getStyle())
			.append("who       ", this.who, false)
			.append("who       ", this.who)
			.append("what      ", this.what)
			.append("whereLoc  ", this.whereLocation)
			.append("whereLine ", this.whereLine)
			.append("whereCol  ", this.whereColumn)
			.append("when      ", this.when, false)
			.append("when      ", this.when)
			.append("why       ", this.why)
			.append("how       ", this.how)
			.append("type      ", this.type)
			.append("reporter  ", this.reporter, false)
			.append("reporter  ", this.reporter)
			.toString();
	}

}
