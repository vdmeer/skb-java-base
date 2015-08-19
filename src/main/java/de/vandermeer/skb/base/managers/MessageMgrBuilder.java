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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.message.E_MessageType;

/**
 * Builds a {@link MessageMgr} object with all settings.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.13
 */
public class MessageMgrBuilder {

	/** Errors of the builder. */
	protected final CC_Error buildErrors;

	/** Maps of message handlers for message types. */
	protected final Map<E_MessageType, MessageTypeHandler> messageHandlers;

	/** Flag to define the behavior for message collection. */
	protected boolean doCollectMessages;

	/** The identifier (or name) of the application using the message manager. */
	protected final Object appID;

	/**
	 * Creates a new builder with given application identifier (name).
	 * @param appID identifier (or name) of the application using the manager
	 * @throws IllegalArgumentException if application identifier was null or its toString() is blank
	 */
	public MessageMgrBuilder(Object appID){
		if(appID==null){
			throw new IllegalArgumentException("appID cannot be null");
		}
		if(StringUtils.isBlank(appID.toString())){
			throw new IllegalArgumentException("appID cannot be blank");
		}

		this.appID = appID;
		this.messageHandlers = new HashMap<>();
		this.buildErrors = new CC_Error();
	}

	/**
	 * Returns errors the builder collected.
	 * @return builder's errors
	 */
	public CC_Error getBuildErrors(){
		return this.buildErrors;
	}

	/**
	 * Activate a message type and sets a type handler with max count set to 100 using {@link Skb_Console}.
	 * An existing handler will be overwritten.
	 * @param type message type to be activated, nothing will be set if null
	 * @return self to allow for chaining
	 */
	public MessageMgrBuilder setHandler(E_MessageType type){
		if(type!=null){
			this.messageHandlers.put(type, new MessageTypeHandler(type));
		}
		else{
			this.buildErrors.add("{}: cannot add handler for empty type", this.getClass().getSimpleName());
		}
		return this;
	}

	/**
	 * Activate a message type and sets a type handler with max count using {@link Skb_Console}.
	 * An existing handler will be overwritten.
	 * @param type message type to be activated, nothing will be set if null
	 * @param maxCount max count for the handler, -1 to ignore or greater than 0 to be used
	 * @return self to allow for chaining
	 */
	public MessageMgrBuilder setHandler(E_MessageType type, int maxCount){
		if(type!=null){
			this.messageHandlers.put(type, new MessageTypeHandler(type, maxCount));
		}
		else{
			this.buildErrors.add("{}: cannot add handler for empty type", this.getClass().getSimpleName());
		}
		return this;
	}

	/**
	 * Activate a message type and sets a type handler with max count set to 100 using specific logger.
	 * An existing handler will be overwritten.
	 * @param type message type to be activated, nothing will be set if null
	 * @param logger the logger to be used for the handler, if null {@link Skb_Console} will be used
	 * @return self to allow for chaining
	 */
	public MessageMgrBuilder setHandler(E_MessageType type, Logger logger){
		if(type!=null){
			this.messageHandlers.put(type, new MessageTypeHandler(type, logger));
		}
		else{
			this.buildErrors.add("{}: cannot add handler for empty type", this.getClass().getSimpleName());
		}
		return this;
	}

	/**
	 * Activate a message type and sets a type handler with max count using specific logger.
	 * An existing handler will be overwritten.
	 * @param type message type to be activated, nothing will be set if null
	 * @param maxCount max count for the handler, -1 to ignore or greater than 0 to be used
	 * @param logger the logger to be used for the handler, if null {@link Skb_Console} will be used
	 * @return self to allow for chaining
	 */
	public MessageMgrBuilder setHandler(E_MessageType type, int maxCount, Logger logger){
		if(type!=null){
			this.messageHandlers.put(type, new MessageTypeHandler(type, maxCount, logger));
		}
		else{
			this.buildErrors.add("{}: cannot add handler for empty type", this.getClass().getSimpleName());
		}
		return this;
	}

	/**
	 * Enables collection of messages for the manager.
	 * @return self to allow for chaining
	 */
	public MessageMgrBuilder enableMessageCollection(){
		this.doCollectMessages = true;
		return this;
	}

	/**
	 * Builds a {@link MessageMgr}.
	 * @return an new manager if all required parameters are set, null if not with errors being logged
	 */
	public MessageMgr build(){
		if(this.messageHandlers.size()==0){
			this.buildErrors.add("no message handlers set");
			return null;
		}
		return new MessageMgr(this.appID, this.messageHandlers, this.doCollectMessages);
	}

}
