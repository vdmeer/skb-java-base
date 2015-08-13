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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.STGroupValidator;
import de.vandermeer.skb.base.message.EMessageType;

/**
 * Builds a {@link MessageMgr} object with all settings.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.1 build 150814 (14-Aug-15) for Java 1.8
 * @since      v0.0.13
 */
public class MessageMgrBuilder {

	/**
	 * Returns a map set with all expected template names and their expected arguments.
	 * 
	 * The map that maintains the expected templates and their arguments for the manager.
	 * Any STGroup handed over the the manager will be tested against this map.
	 * Templates or template arguments that are missing can be reported, and the report manager will not invoke in any report activity if the provided STGroup does not provide for all required chunks.
	 * The set templates and their arguments are:
	 * <ul>
	 * 		<li>report ::= who, what, when, why, how, type, reporter</li>
	 * 		<li>where ::= location, line, column</li>
	 * 		<li>maxErrors ::= name, number</li>
	 * </ul>
	 * 
	 * @return template map
	 */
	public final static Map<String, Set<String>> loadChunks(){
		return new HashMap<String, Set<String>>(){
			private static final long serialVersionUID = 1L;{
				put("report", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("who"); add("what"); add("when"); add("where"); add("why"); add("how"); add("type"); add("reporter");
					}}
				);
				put("where", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("location"); add("line"); add("column");
					}}
				);
				put("maxErrors", new HashSet<String>(){
					private static final long serialVersionUID = 1L;{
						add("name"); add("number");
					}}
				);
			}
		};
	}

	/** Errors of the builder. */
	protected final CC_Error buildErrors;

	/** Maps of message handlers for message types. */
	protected final Map<EMessageType, MessageTypeHandler> messageHandlers;

	/** Flag to define the behavior for message collection. */
	protected boolean doCollectMessages;

	/** Message output STG. */
	protected STGroup stg;

	/** The identifier (or name) of the application using the message manager. */
	protected final Object appID;

	/**
	 * Creates a new builder with given application identifier (name).
	 * @param appID identifier (or name) of the application using the manager
	 * @throws IllegalArgumentException if the parameter was null or its toString() is blank
	 */
	public MessageMgrBuilder(Object appID){
		this(appID, false);
	}

	/**
	 * Creates a new builder with given application identifier (name).
	 * @param appID identifier (or name) of the application using the manager
	 * @param useDefaultSTG flag for using the default STG (will be used if true, not used if false)
	 * @throws IllegalArgumentException if application identifier was null or its toString() is blank
	 */
	public MessageMgrBuilder(Object appID, boolean useDefaultSTG){
		if(appID==null){
			throw new IllegalArgumentException("appID cannot be null");
		}
		if(StringUtils.isBlank(appID.toString())){
			throw new IllegalArgumentException("appID cannot be blank");
		}
		if(useDefaultSTG==true){
			this.loadStg("de/vandermeer/skb/base/report-manager.stg");
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
	public MessageMgrBuilder setHandler(EMessageType type){
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
	public MessageMgrBuilder setHandler(EMessageType type, int maxCount){
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
	public MessageMgrBuilder setHandler(EMessageType type, Logger logger){
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
	public MessageMgrBuilder setHandler(EMessageType type, int maxCount, Logger logger){
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
	 * Sets the STG overwriting an existing one.
	 * @param stg new STG to set, not set if not valid
	 * @return self to allow for chaining
	 */
	public MessageMgrBuilder setStg(STGroup stg){
		if(stg==null){
			this.buildErrors.add("{}: could not set STG, parameter was null", this.getClass().getSimpleName());
			return this;
		}
		this.stg = stg;
		this.validateSTG();
		return this;
	}

	/**
	 * Loads an STG from a given filename (from file system or from resource).
	 * @param filename name of the STG file, errors are logged if load was not successful or STG was not valid
	 * @return self to allow for chaining
	 */
	public MessageMgrBuilder loadStg(Object filename){
		try{
			this.stg = new STGroupFile(filename.toString());
			this.validateSTG();
		}
		catch(Exception e){
			this.buildErrors.add("{}: could not load STG from file/resource for filename <{}>", this.getClass().getSimpleName(), filename);
		}
		return this;
	}

	/**
	 * Validates the STG and resets it if not valid.
	 * @return true if an STG is loaded and valid, false otherwise (including STG being null)
	 */
	public boolean validateSTG(){
		if(this.stg==null){
			return false;
		}

		STGroupValidator stgv = new STGroupValidator(this.stg, MessageMgrBuilder.loadChunks());
		if(!stgv.isValid()){
			this.buildErrors.add(stgv.getValidationErrors());
			this.stg = null;
			return false;
		}
		return true;
	}

	/**
	 * Builds a {@link MessageMgr}.
	 * @return an new manager if all required parameters are set, null if not with errors being logged
	 */
	public MessageMgr build(){
		if(this.stg==null){
			this.buildErrors.add("stg cannot be null");
			return null;
		}
		if(this.messageHandlers.size()==0){
			this.buildErrors.add("no message handlers set");
			return null;
		}
		return new MessageMgr(this.appID, this.messageHandlers, this.doCollectMessages, this.stg);
	}

}
