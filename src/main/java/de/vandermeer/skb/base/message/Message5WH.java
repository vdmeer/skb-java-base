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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.base.Skb_Renderable;
import de.vandermeer.skb.base.Skb_ToStringStyle;
import de.vandermeer.skb.base.utils.Skb_Antlr4Utils;
import de.vandermeer.skb.base.utils.Skb_STUtils;

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
Message5WH msg=new Message5WH()
	.setWho("from "+this.getClass().getSimpleName())
	.addWhat("showing a test message")
	.setWhen(null)
	.setWhere("the class API documentation", 0, 0)
	.addWhy("as a demo")
	.addHow("added to the class JavaDoc")
	.setReporter("The Author")
	.setType(EMessageType.INFO)
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
 * @version    v0.0.6 build 150712 (12-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public class Message5WH implements Skb_Renderable {

	/** The default STGroup definition for the 5WH message class. */
	public final static String messageSTGroup=
			"where(location, line, column) ::= <<\n" +
			"<location;separator=\".\"><if(line&&column)> <line>:<column><elseif(!line&&!column)><elseif(!line)> -:<column><elseif(!column)> <line>:-<endif>\n"+
			">>\n\n" +
			"message5wh(reporter, type, who, when, where, what, why, how) ::= <<\n" +
			"<if(reporter)><reporter>: <endif><if(type)><type> <endif><if(who)><who> <endif><if(when)>at (<when>) <endif><if(where)>in <where> <endif><if(what)>\\>> <what><endif>" +
			"<if(why)> \n       ==> <why><endif>\n" +
			"<if(how)> \n       ==> <how><endif>\n" +
			">>\n";

	public final static Map<String, List<String>> stChunks = new HashMap<String, List<String>>(){
			private static final long serialVersionUID = 1L;{
				put("where", new ArrayList<String>(){
					private static final long serialVersionUID = 1L;{
						add("location");
						add("line");
						add("column");
					}}
				);
				put("message5wh", new ArrayList<String>(){
					private static final long serialVersionUID = 1L;{
						add("reporter");
						add("type");
						add("who");
						add("when");
						add("where");
						add("what");
						add("why");
						add("how");
					}}
				);
			}};
	;

	/** Initial capacity for StrBuilder members */
	protected int initialCapacity;

	/** Who is this message about? */
	private Object who;

	/** What happened? */
	private StrBuilder what;

	/** Where did it happen? */
	private ST where;

	/** The ST group for the message */
	STGroup stg;

	/** When did take place/happen? */
	private Object when;

	/** Why did it happen? */
	private StrBuilder why;

	/** How did it happen? */
	private StrBuilder how;

	/** Who reports it? */
	private Object reporter;

	/** Type of message */
	private EMessageType type;

	/**
	 * Simple constructor setting/loading defaults for the object.
	 * It does not set any implicit defaults for any part of the message (5WH) nor the reporter or the message type.
	 * All of those members are initialised with the standard object default "null".
	 * This initial capacity for all StrBuilder parts of the message (what, why and how) is set to 50 (characters).
	 */
	public Message5WH(){
		this.initialCapacity = 50;
		this.stg = new STGroupString(Message5WH.messageSTGroup);
	}

	/**
	 * Builder method: adds to the How? part of the message.
	 * If the argument is null, no change will be done.
	 * Otherwise if nothing was set to How? yet, the given argument will be used as initial value.
	 * If not, then the argument will be appended as is w/o any implicit separators.
	 * @param how additional information for How?
	 * @return this to allow concatenation
	 */
	public Message5WH addHow(Object ...how){
		if(this.how==null){
			this.how = new StrBuilder(50);
		}
		this.how.appendAll(how);
		return this;
	}

	/**
	 * Builder method: adds to the What? part of the message.
	 * If the argument is null, no change will be done.
	 * Otherwise if nothing was set to What? yet, the given argument will be used as initial value.
	 * If not, then the argument will be appended as is w/o any implicit separators.
	 * @param what additional information for What?
	 * @return this to allow concatenation
	 */
	public Message5WH addWhat(Object ...what){
		if(this.what==null){
			this.what = new StrBuilder(50);
		}
		this.what.appendAll(what);
		return this;
	}

	/**
	 * Builder method: adds to the Why? part of the message.
	 * If the argument is null, no change will be done.
	 * Otherwise if nothing was set to Why? yet, the given argument will be used as initial value.
	 * If not, then the argument will be appended as is w/o any implicit separators.
	 * @param why additional information for Why?
	 * @return this to allow concatenation
	 */
	public Message5WH addWhy(Object ...why){
		if(this.why==null){
			this.why = new StrBuilder(50);
		}
		this.why.appendAll(why);
		return this;
	}

	/**
	 * Sets a new STGroup for the message.
	 * The STGroup contains the two templates used for rendering a message: <code>where</code> and <code>message5wh</code>. The default templates are
	 * defined in {@link Message5WH#messageSTGroup}. 
	 * @param stg new STGroup
	 * @return returns always self, but only sets the new STGroup if it contains the two required functions with all arguments
	 */
	public Message5WH setSTG(STGroup stg){
		if(stg!=null){
			Map<String, List<String>> actual=Skb_STUtils.getMissingChunks(stg, Message5WH.stChunks);
			if(actual.size()==0){
				this.stg = stg;
			}
		}
		return this;
	}

	/**
	 * Tests the given STGroup.
	 * The test basically looks into the STGroup for the two required templates <code>where</code> and <code>message5wh</code> and their required arguments.
	 * The required arguments are provided by {@link Message5WH#stChunks}.
	 * @param stg STGroup to test
	 * @return null if input group was null, a set size 0 of everything was ok, a set size &gt; 0 with detailed error messages otherwise
	 */
	public Set<String> testSTG(STGroup stg){
		if(stg!=null){
			Map<String, List<String>> actual=Skb_STUtils.getMissingChunks(stg, Message5WH.stChunks);
			Set<String> errors = Skb_STUtils.getMissingChunksErrorMessages(Skb_STUtils.getStgName(stg), actual);
			if(errors.size()>0){
				return errors;
			}
			else{
				this.stg = stg;
				return new HashSet<String>();
			}
		}
		return null;
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
	 * Builder method: sets the Reporter of the message.
	 * Since null is a valid value for this member of the message, no further check is applied to the argument.
	 * @param reporter new reporter
	 * @return this to allow concatenation
	 */
	public Message5WH setReporter(Object reporter){
		this.reporter = reporter;
		return this;
	}

	/**
	 * Builder method: sets the type of the message.
	 * Since null is a valid value for this member of the message, no further check is applied to the argument.
	 * @param type new type
	 * @return this to allow concatenation
	 */
	public Message5WH setType(EMessageType type){
		this.type = type;
		return this;
	}

	/**
	 * Builder method: sets the When? part of the message.
	 * Since null is a valid value for this part of the message, no further check is applied to the argument.
	 * @param when new When? part
	 * @return this to allow concatenation
	 */
	public Message5WH setWhen(Object when){
		this.when = when;
		return this;
	}

	/**
	 * Builder method: sets the Where? part of the message.
	 * Nothing will be set if the first parameter is null.
	 * @param where location for Where?
	 * @param line line for Where?, ignored if &lt;1;
	 * @param column column for Where?, ignored if &lt;1
	 * @return this to allow concatenation
	 */
	public Message5WH setWhere(Object where, int line, int column){
		if(where!=null){
			this.where = this.stg.getInstanceOf("where");
			this.where.add("location", where);
			if(line>0){
				this.where.add("line", line);
			}
			if(column>0){
				this.where.add("column", column);
			}
		}
		return this;
	}

	/**
	 * Builder method: sets the Where? part of the message.
	 * Line and column information are taken from the recognition exception, if they are larger than 0.
	 * Nothing will be set if the two parameters are null.
	 * @param where location for Where?
	 * @param lineAndColumn source for the Where? part
	 * @return this to allow concatenation
	 */
	public Message5WH setWhere(Object where, RecognitionException lineAndColumn){
		if(where!=null && lineAndColumn!=null){
			this.setWhere(where, Skb_Antlr4Utils.antlr2line(lineAndColumn), Skb_Antlr4Utils.antlr2column(lineAndColumn));
		}
		return this;
	}

	public Message5WH setWhere(Object where){
		return this.setWhere(where, 0, 0);
	}

	/**
	 * Builder method: sets the Where? part of the message.
	 * Line and column information are taken from the token, if they are larger than 0.
	 * Nothing will be set if the two parameters are null.
	 * @param where location for Where?
	 * @param lineAndColumn source for the Where? part
	 * @return this to allow concatenation
	 */
	public Message5WH setWhere(Object where, Token lineAndColumn){
		if(where!=null && lineAndColumn!=null){
			this.setWhere(where, Skb_Antlr4Utils.antlr2line(lineAndColumn), Skb_Antlr4Utils.antlr2column(lineAndColumn));
		}
		return this;
	}

	/**
	 * Builder method: sets the Where? part of the message.
	 * Line and column information are taken from the tree, if they are larger than 0.
	 * Nothing will be set if the two parameters are null.
	 * @param where location for Where?
	 * @param lineAndColumn source for the Where? part
	 * @return this to allow concatenation
	 */
	public Message5WH setWhere(Object where, ParserRuleContext lineAndColumn){
		if(where!=null && lineAndColumn!=null){
			this.setWhere(where, Skb_Antlr4Utils.antlr2line(lineAndColumn), Skb_Antlr4Utils.antlr2column(lineAndColumn));
		}
		return this;
	}

	/**
	 * Builder method: sets the Where? part of the message.
	 * Three values are taken from the stack trace: class name and method name for the location of Where?
	 * and line number for the the line. If the stack trace is null or class name and method are empty, nothing will be
	 * set. A line number of 0 will be ignored.
	 * @param ste source for the Where? part
	 * @return this to allow concatenation
	 */
	public Message5WH setWhere(StackTraceElement ste){
		if(ste!=null){
			String cn = ste.getClassName();
			String mn = ste.getMethodName();
			int line = ste.getLineNumber();
			//StackTraceElement requires Class and Method set to !null but allows for "", we cope with null but not "" in ST4
			if(!"".equals(cn) || !"".equals(mn)){
				this.where = this.stg.getInstanceOf("where");
				if(!"".equals(cn)){
					this.where.add("location", ste.getClassName());
				}
				if(!"".equals(mn)){
					this.where.add("location", ste.getMethodName());
				}
				if(line>0){
					this.where.add("line", ste.getLineNumber());
				}
				//no column information in a stack trace
			}
		}
		return this;
	}

	/**
	 * Builder method: sets the Who? part of the message.
	 * Since null is a valid value for this part of the message, no further check is applied to the argument.
	 * @param who new Who? part
	 * @return this to allow concatenation
	 */
	public Message5WH setWho(Object who){
		this.who = who;
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
