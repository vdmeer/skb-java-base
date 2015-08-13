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

package de.vandermeer.skb.base.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import de.vandermeer.asciitable.commons.ObjectToStringStyle;
import de.vandermeer.skb.base.composite.coin.Abstract_CC;
import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.composite.coin.CC_Info;
import de.vandermeer.skb.base.composite.coin.CC_Warning;
import de.vandermeer.skb.base.message.EMessageType;
import de.vandermeer.skb.base.message.FormattingTupleWrapper;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * Manages Info, Warning and Error messages supporting {@link Message5WH}, {@link CC_Info}, {@link CC_Warning}, and {@link CC_Error} objects as well as arrays and collections of them.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-commons before)
 */
public class MessageMgr {

	/** Map of collected messages (errors, warnings, information) as rendered strings. */
	protected final LinkedHashMap<EMessageType, String> messages;

	/** Maps of message handlers for message types. */
	protected final Map<EMessageType, MessageTypeHandler> messageHandlers;

	/** Flag to define the behavior for message collection. */
	protected final boolean doCollectMessages;

	/** Message output STG. */
	protected final STGroup stg;

	/** The identifier (or name) of the application using the message manager. */
	protected final Object appID;

	/**
	 * Creates a new information message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 * @return new information message
	 */
	public static Message5WH createInfoMessage(String what, Object ... obj){
		return new Message5WH_Builder().addWhat(new FormattingTupleWrapper(what, obj)).setType(EMessageType.INFO).build();
	}

	/**
	 * Creates a new warning message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 * @return new information message
	 */
	public static Message5WH createWarningMessage(String what, Object ... obj){
		return new Message5WH_Builder().addWhat(new FormattingTupleWrapper(what, obj)).setType(EMessageType.WARNING).build();
	}

	/**
	 * Creates a new error message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 * @return new information message
	 */
	public static Message5WH createErrorMessage(String what, Object ... obj){
		return new Message5WH_Builder().addWhat(new FormattingTupleWrapper(what, obj)).setType(EMessageType.ERROR).build();
	}

	/**
	 * Returns a new manager.
	 * @param appID the application identifier or name used in messages
	 * @param messageHandlers the message handlers for the manager
	 * @param doCollectMessages flag for collecting messages (true for do collect, false for not)
	 * @param stg the STG for messages
	 */
	MessageMgr(Object appID, Map<EMessageType, MessageTypeHandler> messageHandlers, boolean doCollectMessages, STGroup stg){
		this.messages = new LinkedHashMap<>();

		this.appID = appID;
		this.messageHandlers = new HashMap<>();
		this.messageHandlers.putAll(messageHandlers);
		this.doCollectMessages = doCollectMessages;
		this.stg = stg;
	}

	/**
	 * Returns the collected messages.
	 * @return empty if collection is disabled, a list of collected reports otherwise
	 */
	public Collection<String> getMessageCollection(){
		return this.messages.values();
	}

	/**
	 * Returns the collected messages.
	 * @return empty if collection is disabled, a list of collected reports otherwise
	 */
	public Map<EMessageType, String> getMessageMap(){
		return this.messages;
	}

	/**
	 * Returns true if the manager has errors reported, false otherwise
	 * @return true if errors have been reported, false otherwise
	 */
	public boolean hasErrors(){
		if(this.messageHandlers.containsKey(EMessageType.ERROR)){
			return (this.messageHandlers.get(EMessageType.ERROR).getCount()==0)?true:false;
		}
		return false;
	}

	/**
	 * Returns true if the manager has infos reported, false otherwise
	 * @return true if infos have been reported, false otherwise
	 */
	public boolean hasInfos(){
		if(this.messageHandlers.containsKey(EMessageType.INFO)){
			return (this.messageHandlers.get(EMessageType.INFO).getCount()==0)?true:false;
		}
		return false;
	}

	/**
	 * Returns the current count for the given message type since its last initialization or reset.
	 * @param type message type to report count for
	 * @return current count of messages, -1 if not in active list
	 */
	public int getMessageCount(EMessageType type) {
		if(this.messageHandlers.containsKey(type)){
			return this.messageHandlers.get(type).getCount();
		}
		return -1;
	}

	/**
	 * Returns is the report level is enabled or not.
	 * @param type message type to check
	 * @return true if the message type is in the list of active message types of the manager and if the associated logger is enabled, false otherwise
	 */
	public boolean isEnabledFor(EMessageType type) {
		if(!this.messageHandlers.containsKey(type)){
			return false;
		}
		return this.messageHandlers.get(type).isEnabled();
	}

	/**
	 * Reports a message.
	 * The report will use the logger associated with the type of the message. Most members of the message will be used
	 * as is, i.e. their toString method will be used for reporting. The exception is the 'where' member, which is processed
	 * specifically. If 'where' contains line/column numbers, they will be used. If its object is an ANTLR Recognition Exception
	 * or an ANTLR Token or an ANTLR Tree, the line/column numbers from this object will be used. For the actual where part, the obj
	 * member will be used first, next the class (clazz) and finally the file name (filename). 
	 * @param message to be reported
	 * @return true if the message was reported, false otherwise
	 */
	protected boolean report(Message5WH message) {
		if(message==null){
			return false; // no message to handle
		}
		EMessageType type = message.getType();
		if(!this.messageHandlers.containsKey(message.getType())){
			return false; // no handler means message type not managed
		}
		MessageTypeHandler handler = this.messageHandlers.get(message.getType());
		if(!handler.isEnabled()){
			return false; // handler found but it's not enabled
		}

		ST template = this.stg.getInstanceOf("report");
		template.add("type", message.getType());

		template.add("who", message.getWho());
		template.add("what", message.getWhat());
		template.add("when", message.getWhen());
		template.add("why", message.getWhy());
		template.add("how", message.getHow());
		template.add("reporter", message.getReporter());

		ST where = message.getWhere();
		if(where!=null){
			ST whereST = this.stg.getInstanceOf("where");
			whereST.add("location", where.getAttribute("location"));
			whereST.add("line", where.getAttribute("line"));
			whereST.add("column", where.getAttribute("column"));
			template.add("where", whereST);
		}

		handler.handleMessage(template, type, this.stg.getInstanceOf("maxError"), this.appID);

		//finally collect the information, regardless of logger settings for report type
		if(this.messages!=null){
			this.messages.put(type, template.render());
		}
		return true;
	}

	/**
	 * Reports a message.
	 * 
	 * <p>The report will use the logger associated with the type of the message. Most members of the message will be used
	 * as is, i.e. their toString method will be used for reporting. The exception is the 'where' member, which is processed
	 * specifically. If 'where' contains line/column numbers, they will be used. If its object is an ANTLR Recognition Exception
	 * or an ANTLR Token or an ANTLR Tree, the line/column numbers from this object will be used. For the actual where part, the obj
	 * member will be used first, next the class (clazz) and finally the file name (filename).</p>
	 * 
	 * <p> This method does understand any object that is an instance of {@link Message5WH} plus any array or Iterable that contains
	 * instances of {@link Message5WH}. Additionally, it can report on {@link CC_Error} and {@link CC_Warning} instances.
	 * All other objects will be silently ignored.</p>
	 * 
	 * @param message to be reported
	 * @return true if one or all message (for arrays, Iterable) have been reported, false otherwise
	 */
	public boolean report(Object message){
		if(message==null){
			return false;
		}
		else if(message instanceof Message5WH){
			return this.report((Message5WH)message);
		}

		boolean ret=true;	//default is true, set to false in case of problems
		if(message instanceof Abstract_CC){
			for(Message5WH w : ((Abstract_CC)message).getList()){
				ret = ret & this.report(w);
			}
		}
		else if(message instanceof Object[]){
			for(Object o : (Object[])message){
				if(o instanceof Message5WH){
					ret = ret & this.report((Message5WH)o);
				}
			}
		}
		else if(message instanceof Iterable){
			for(Object o : (Iterable<?>)message){
				if(o instanceof Message5WH){
					ret = ret & this.report((Message5WH)o);
				}
			}
		}
		return ret;
	}

	/**
	 * Resets the collected messages and all counters.
	 * @return returns self to allow for chained calls
	 */
	public MessageMgr clear() {
		for(MessageTypeHandler handler : this.messageHandlers.values()){
			handler.clear();
		}
		this.messages.clear();
		return this;
	}

	@Override
	public String toString() {
		ToStringBuilder ret = new ToStringBuilder(this, ObjectToStringStyle.getStyle())
//			.append("types       ", this.messageTypes)
//			.append("counters    ", this.counters)
//			.append("max errs    ", this.maxErrors)
//			.append("------------------------------------")
//			.append("errors #    ", this.errorList.size())
//			.append("errors      ", this.errorList)
//			.append("------------------------------------")
//			.append("stg       ", this.stg, false)
//			.append("stg       ", this.stg)
//			.append("------------------------------------")
			;

		return ret.toString();
	}

}