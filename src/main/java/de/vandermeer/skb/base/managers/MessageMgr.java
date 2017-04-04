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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.message.E_MessageType;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;
import de.vandermeer.skb.interfaces.FormattingTupleWrapper;
import de.vandermeer.skb.interfaces.categories.has.HasToStringStyle;
import de.vandermeer.skb.interfaces.messagesets.HasErrorSet;
import de.vandermeer.skb.interfaces.messagesets.HasInfoSet;
import de.vandermeer.skb.interfaces.messagesets.HasWarningSet;
import de.vandermeer.skb.interfaces.messagesets.IsErrorSet;
import de.vandermeer.skb.interfaces.messagesets.IsInfoSet;
import de.vandermeer.skb.interfaces.messagesets.IsMessageSet;
import de.vandermeer.skb.interfaces.messagesets.IsWarningSet;
import de.vandermeer.skb.interfaces.render.DoesRender;

/**
 * Manages Info, Warning and Error messages supporting {@link Message5WH}, {@link IsInfoSet}, {@link IsWarningSet}, {@link IsErrorSet}, and {@link DoesRender} objects as well as arrays and collections of them.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 170404 (04-Apr-17) for Java 1.8
 * @since      v0.0.6 (was in skb-commons before)
 */
public class MessageMgr implements HasToStringStyle {

	/**
	 * Returns a map set with all expected template names and their expected arguments.
	 * 
	 * The map that maintains the expected templates and their arguments for the manager.
	 * Any STGroup handed over the the manager will be tested against this map.
	 * Templates or template arguments that are missing can be reported, and the report manager will not invoke in any report activity if the provided STGroup does not provide for all required chunks.
	 * The set templates and their arguments are:
	 * <ul>
	 * 		<li>max ::= name, number, type</li>
	 * </ul>
	 * 
	 * @return template map
	 */
	public final static Map<String, Set<String>> loadChunks(){
		return new HashMap<String, Set<String>>(){
			private static final long serialVersionUID = 1L;{
				put("max", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("name"); add("number"); add("type");
					}}
				);
			}
		};
	}

	/** Map of collected messages (errors, warnings, information) as rendered strings. */
	protected final LinkedHashMap<String, E_MessageType> messages;

	/** Maps of message handlers for message types. */
	protected final Map<E_MessageType, MessageTypeHandler> messageHandlers;

	/** Flag to define the behavior for message collection. */
	protected final boolean doCollectMessages;

	/** Message renderer. */
	protected MessageRenderer renderer = null;

	/** STGroup for max100 messages. */
	protected final STGroup max100stg = new STGroupFile("de/vandermeer/skb/base/managers/msg-manager.stg");

	/** The identifier (or name) of the application using the message manager. */
	protected final Object appID;

	/**
	 * Creates a new information message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 * @return new information message
	 */
	public static Message5WH createInfoMessage(String what, Object ... obj){
		return new Message5WH_Builder().addWhat(FormattingTupleWrapper.create(what, obj)).setType(E_MessageType.INFO).build();
	}

	/**
	 * Creates a new warning message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 * @return new information message
	 */
	public static Message5WH createWarningMessage(String what, Object ... obj){
		return new Message5WH_Builder().addWhat(FormattingTupleWrapper.create(what, obj)).setType(E_MessageType.WARNING).build();
	}

	/**
	 * Creates a new error message.
	 * @param what the what part of the message (what has happened)
	 * @param obj objects to add to the message
	 * @return new information message
	 */
	public static Message5WH createErrorMessage(String what, Object ... obj){
		return new Message5WH_Builder().addWhat(FormattingTupleWrapper.create(what, obj)).setType(E_MessageType.ERROR).build();
	}

	/**
	 * Returns a new manager.
	 * @param appID the application identifier or name used in messages
	 * @param messageHandlers the message handlers for the manager
	 * @param doCollectMessages flag for collecting messages (true for do collect, false for not)
	 */
	MessageMgr(Object appID, Map<E_MessageType, MessageTypeHandler> messageHandlers, boolean doCollectMessages){
		this.messages = new LinkedHashMap<>();

		this.appID = appID;
		this.messageHandlers = new HashMap<>();
		this.messageHandlers.putAll(messageHandlers);
		this.doCollectMessages = doCollectMessages;

		this.renderer = new MessageRenderer();
	}

	/**
	 * Sets an renderer for the object, which then will be used to render all messages.
	 * @param renderer new renderer
	 * @return self to allow chaining
	 */
	public MessageMgr setRenderer(MessageRenderer renderer){
		if(renderer!=null){
			this.renderer = renderer;
		}
		return this;
	}

	/**
	 * Returns the collected messages.
	 * @return empty if collection is disabled, a list of collected reports otherwise
	 */
	public Collection<String> getMessageCollection(){
		return this.messages.keySet();
	}

	/**
	 * Returns the collected messages.
	 * @return empty if collection is disabled, a list of collected reports otherwise
	 */
	public Map<String, E_MessageType> getMessageMap(){
		return this.messages;
	}

	/**
	 * Returns true if the manager has errors reported, false otherwise
	 * @return true if errors have been reported, false otherwise
	 */
	public boolean hasErrors(){
		if(this.messageHandlers.containsKey(E_MessageType.ERROR)){
			return (this.messageHandlers.get(E_MessageType.ERROR).getCount()==0)?false:true;
		}
		return false;
	}

	/**
	 * Returns true if the manager has warnings reported, false otherwise
	 * @return true if warnings have been reported, false otherwise
	 */
	public boolean hasWarnings(){
		if(this.messageHandlers.containsKey(E_MessageType.WARNING)){
			return (this.messageHandlers.get(E_MessageType.WARNING).getCount()==0)?false:true;
		}
		return false;
	}

	/**
	 * Returns true if the manager has infos reported, false otherwise
	 * @return true if infos have been reported, false otherwise
	 */
	public boolean hasInfos(){
		if(this.messageHandlers.containsKey(E_MessageType.INFO)){
			return (this.messageHandlers.get(E_MessageType.INFO).getCount()==0)?false:true;
		}
		return false;
	}

	/**
	 * Returns the current count for the given message type since its last initialization or reset.
	 * @param type message type to report count for
	 * @return current count of messages, -1 if not in active list
	 */
	public int getMessageCount(E_MessageType type) {
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
	public boolean isEnabledFor(E_MessageType type) {
		if(!this.messageHandlers.containsKey(type)){
			return false;
		}
		return this.messageHandlers.get(type).isEnabled();
	}

	/**
	 * Reports a message.
	 * @param message to be reported
	 * @return true if the message was reported, false otherwise
	 */
	protected boolean report(Message5WH message) {
		if(message==null){
			return false;
		}

		E_MessageType type = message.getType();
		if(!this.messageHandlers.containsKey(message.getType())){
			return false;
		}
		MessageTypeHandler handler = this.messageHandlers.get(message.getType());

		String template = this.renderer.render(message);
		handler.handleMessage(template, type, this.max100stg.getInstanceOf("max"), this.appID);
		this.messages.put(template, type);
		return true;
	}

	/**
	 * Reports a message.
	 * @param messageSet message to be reported
	 * @return true if the message was reported, false otherwise
	 */
	protected boolean report(IsMessageSet messageSet){
		if(messageSet==null){
			return false;
		}

		E_MessageType type = null;
		if(messageSet.isErrorSet()){
			type = E_MessageType.ERROR;
		}
		else if(messageSet.isWarningSet()){
			type = E_MessageType.WARNING;
		}
		else if(messageSet.isInfoSet()){
			type = E_MessageType.INFO;
		}
		else{
			return false;
		}
		if(!this.messageHandlers.containsKey(type)){
			return false;
		}
		MessageTypeHandler handler = this.messageHandlers.get(type);

		String template = messageSet.render();
		handler.handleMessage(template, type, this.max100stg.getInstanceOf("max"), this.appID);
		this.messages.put(template, type);
		return true;
	}

	/**
	 * Reports a message trying all known classes (message formats).
	 * @param message message to be reported
	 * @return true if the message was reported, false otherwise
	 */
	protected boolean reportKnownClasses(Object message){
		boolean ret = true;
		if(message instanceof Message5WH){
			ret = ret & this.report((Message5WH)message);
		}
		else if(message instanceof IsMessageSet){
			ret = ret & this.report((IsMessageSet)message);
		}
		else if(message instanceof HasErrorSet){
			ret = ret & this.report(((HasErrorSet<?>)message).getErrorSet());
		}
		else if(message instanceof HasWarningSet){
			ret = ret & this.report(((HasWarningSet<?>)message).getWarningSet());
		}
		else if(message instanceof HasInfoSet){
			ret = ret & this.report(((HasInfoSet<?>)message).getInfoSet());
		}
		return ret;
	}

	/**
	 * Reports a message.
	 * 
	 * This methods understands the message formats {@link Message5WH}, {@link IsMessageSet}, {@link HasErrorSet}, {@link HasWarningSet}, and {@link HasInfoSet}.
	 * It also does process iterators, iterables, and arrays of any type to process the message formats mentioned above.
	 * 
	 * All objects not understood will be silently ignored.
	 * 
	 * @param obj message object or object containing messages for reporting
	 * @return true if one or all message have been reported, false otherwise
	 */
	public boolean report(Object obj){
		if(obj==null){
			return false;
		}

		boolean ret = true;
		if(obj instanceof Iterable){
			for(Object o : (Iterable<?>)obj){
				ret = ret & this.reportKnownClasses(o);
			}
		}
		else if(obj instanceof Iterator){
			while(((Iterator<?>)obj).hasNext()){
				ret = ret & this.reportKnownClasses(((Iterator<?>)obj).next());
			}
		}
		else if(obj instanceof Object[]){
			for(Object o : (Object[])obj){
				ret = ret & this.reportKnownClasses(o);
			}
		}
		else{
			ret = ret & this.reportKnownClasses(obj);
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
		ToStringBuilder ret = new ToStringBuilder(this, this.getStyle())
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
