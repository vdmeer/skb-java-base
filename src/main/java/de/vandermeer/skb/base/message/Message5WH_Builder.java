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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.base.utils.Skb_Antlr4Utils;

/**
 * Builder for a {@link Message5WH} object.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.7 (most tests where in the message tests before)
 */
public class Message5WH_Builder {

	/** The default STGroup definition for the 5WH message class. */
	public final static String messageSTGroup = 
			"where(location, line, column) ::= <<\n" +
			"<location;separator=\".\"><if(line&&column)> <line>:<column><elseif(!line&&!column)><elseif(!line)> -:<column><elseif(!column)> <line>:-<endif>\n"+
			">>\n\n" +
			"message5wh(reporter, type, who, when, where, what, why, how) ::= <<\n" +
			"<if(reporter)><reporter>: <endif><if(type)><type> <endif><if(who)><who> <endif><if(when)>at (<when>) <endif><if(where)>in <where> <endif><if(what)>\\>> <what><endif>" +
			"<if(why)> \n       ==> <why><endif>\n" +
			"<if(how)> \n       ==> <how><endif>\n" +
			">>\n"
	;

	/** The STGroup chunks for validation of an STGroup for a message. */
	public final static Map<String, Set<String>> stChunks = new HashMap<String, Set<String>>(){
		private static final long serialVersionUID = 1L;{
			put("where", new HashSet<String>(){
				private static final long serialVersionUID = 1L;{
					add("location");
					add("line");
					add("column");
				}}
			);
			put("message5wh", new HashSet<String>(){
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
		}
	};

	/** Initial capacity for StrBuilder members */
	protected int initialCapacity;

	/** Who is this message about? */
	protected Object who;

	/** What happened? */
	protected StrBuilder what;

	/** Where did it happen? */
	protected ST where;

	/** The ST group for the message */
	protected STGroup stg;

	/** When did take place/happen? */
	protected Object when;

	/** Why did it happen? */
	protected StrBuilder why;

	/** How did it happen? */
	protected StrBuilder how;

	/** Who reports it? */
	protected Object reporter;

	/** Type of message */
	protected EMessageType type;

	public Message5WH_Builder(){
		this.initialCapacity = 50;
		this.stg = new STGroupString(Message5WH_Builder.messageSTGroup);
	}

	/**
	 * Adds to the How? part of the message.
	 * If the argument is null, no change will be done.
	 * Otherwise if nothing was set to How? yet, the given argument will be used as initial value.
	 * If not, then the argument will be appended as is w/o any implicit separators.
	 * @param how additional information for How?
	 * @return self to allow chaining
	 */
	public Message5WH_Builder addHow(Object ...how){
		if(this.how==null){
			this.how = new StrBuilder(50);
		}
		this.how.appendAll(how);
		return this;
	}

	/**
	 * Adds to the What? part of the message.
	 * If the argument is null, no change will be done.
	 * Otherwise if nothing was set to What? yet, the given argument will be used as initial value.
	 * If not, then the argument will be appended as is w/o any implicit separators.
	 * @param what additional information for What?
	 * @return self to allow chaining
	 */
	public Message5WH_Builder addWhat(Object ...what){
		if(this.what==null){
			this.what = new StrBuilder(50);
		}
		this.what.appendAll(what);
		return this;
	}

	/**
	 * Adds to the Why? part of the message.
	 * If the argument is null, no change will be done.
	 * Otherwise if nothing was set to Why? yet, the given argument will be used as initial value.
	 * If not, then the argument will be appended as is w/o any implicit separators.
	 * @param why additional information for Why?
	 * @return self to allow chaining
	 */
	public Message5WH_Builder addWhy(Object ...why){
		if(this.why==null){
			this.why = new StrBuilder(50);
		}
		this.why.appendAll(why);
		return this;
	}

	/**
	 * Sets a new STGroup for the message.
	 * The STGroup contains the two templates used for rendering a message: <code>where</code> and <code>message5wh</code>.
	 * The default templates are defined in {@link Message5WH_Builder#messageSTGroup}.
	 * If the STGroup is not valid, the build message will not render and rendering might result in runtime errors.
	 * @param stg new STGroup
	 * @return self to allow chaining, but only sets the new STGroup if it contains the two required functions with all arguments
	 */
	public Message5WH_Builder setSTG(STGroup stg){
		if(stg!=null){
				this.stg = stg;
		}
		return this;
	}

	/**
	 * Sets the Reporter of the message.
	 * Since null is a valid value for this member of the message, no further check is applied to the argument.
	 * @param reporter new reporter
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setReporter(Object reporter){
		this.reporter = reporter;
		return this;
	}

	/**
	 * Sets the type of the message.
	 * Since null is a valid value for this member of the message, no further check is applied to the argument.
	 * @param type new type
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setType(EMessageType type){
		this.type = type;
		return this;
	}

	/**
	 * Sets the When? part of the message.
	 * Since null is a valid value for this part of the message, no further check is applied to the argument.
	 * @param when new When? part
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWhen(Object when){
		this.when = when;
		return this;
	}

	/**
	 * Sets the Where? part of the message.
	 * Nothing will be set if the first parameter is null.
	 * @param where location for Where?
	 * @param line line for Where?, ignored if &lt;1;
	 * @param column column for Where?, ignored if &lt;1
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWhere(Object where, int line, int column){
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
	 * Sets the Where? part of the message.
	 * Line and column information are taken from the recognition exception, if they are larger than 0.
	 * Nothing will be set if the two parameters are null.
	 * @param where location for Where?
	 * @param lineAndColumn source for the Where? part
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWhere(Object where, RecognitionException lineAndColumn){
		if(where!=null && lineAndColumn!=null){
			this.setWhere(where, Skb_Antlr4Utils.antlr2line(lineAndColumn), Skb_Antlr4Utils.antlr2column(lineAndColumn));
		}
		return this;
	}

	/**
	 * Sets the Where? part of the message.
	 * Line and column information are set to 0.
	 * @param where location for Where?
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWhere(Object where){
		return this.setWhere(where, 0, 0);
	}

	/**
	 * Sets the Where? part of the message.
	 * Line and column information are taken from the token, if they are larger than 0.
	 * Nothing will be set if the two parameters are null.
	 * @param where location for Where?
	 * @param lineAndColumn source for the Where? part
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWhere(Object where, Token lineAndColumn){
		if(where!=null && lineAndColumn!=null){
			this.setWhere(where, Skb_Antlr4Utils.antlr2line(lineAndColumn), Skb_Antlr4Utils.antlr2column(lineAndColumn));
		}
		return this;
	}

	/**
	 * Sets the Where? part of the message.
	 * Line and column information are taken from the tree, if they are larger than 0.
	 * Nothing will be set if the two parameters are null.
	 * @param where location for Where?
	 * @param lineAndColumn source for the Where? part
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWhere(Object where, ParserRuleContext lineAndColumn){
		if(where!=null && lineAndColumn!=null){
			this.setWhere(where, Skb_Antlr4Utils.antlr2line(lineAndColumn), Skb_Antlr4Utils.antlr2column(lineAndColumn));
		}
		return this;
	}

	/**
	 * Sets the Where? part of the message.
	 * Three values are taken from the stack trace: class name and method name for the location of Where?
	 * and line number for the the line. If the stack trace is null or class name and method are empty, nothing will be
	 * set. A line number of 0 will be ignored.
	 * @param ste source for the Where? part
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWhere(StackTraceElement ste){
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
	 * Sets the Who? part of the message.
	 * Since null is a valid value for this part of the message, no further check is applied to the argument.
	 * @param who new Who? part
	 * @return self to allow chaining
	 */
	public Message5WH_Builder setWho(Object who){
		this.who = who;
		return this;
	}

	/**
	 * Builds a message object with the set parameters.
	 * @return new message object
	 */
	public Message5WH build(){
		return new Message5WH(this.who, this.what, this.where, this.stg, this.when, this.why, this.how, this.reporter, this.type);
	}
}
