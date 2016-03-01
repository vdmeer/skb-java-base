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

import org.slf4j.Logger;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.message.E_MessageType;

/**
 * Handles a message type for the message manager.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.9 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.13
 */
class MessageTypeHandler {

	/** Count of messages for this type. */
	protected int count;

	/** The maximum count of messages allows, defaults to -1 for being ignored. */
	protected int maxCount;

	/** Flag to use SkbConsole for output. */
	protected boolean useSkbConsole;

	/** The logger to be used for output, only used if SkbConsole is not used/ */
	protected Logger logger;

	/** The message type the handler handles. */
	protected final E_MessageType type;

	/**
	 * Returns a new handler with max count set to -1 using {@link Skb_Console}.
	 */
	MessageTypeHandler(E_MessageType type){
		this(type, 0);
	}

	/**
	 * Returns a new handler with given max count using {@link Skb_Console}.
	 * @param maxCount given maximum count, only used of greater than 0, set to -1 otherwise
	 */
	MessageTypeHandler(E_MessageType type, int maxCount){
		this.type = type;

		this.maxCount = (maxCount>0)?maxCount:-1;
		this.useSkbConsole = true;
		this.count = 0;
	}

	/**
	 * Returns a new handler with given max count using given logger.
	 * @param maxCount maxCount given maximum count, only used of greater than 0, set to -1 otherwise
	 * @param logger used as logger for the message type if not null, handler will use {@link Skb_Console} otherwise
	 */
	MessageTypeHandler(E_MessageType type, int maxCount, Logger logger){
		this(type, maxCount);
		if(logger!=null){
			this.useSkbConsole = false;
			this.logger = logger;
		}
	}

	/**
	 * Returns a new handler with max count set to -1 using given logger.
	 * @param logger used as logger for the message type if not null, handler will use {@link Skb_Console} otherwise
	 */
	MessageTypeHandler(E_MessageType type, Logger logger){
		this(type, 0, logger);
	}

	/**
	 * Returns the count of messages of the type the handler handles
	 * @return message count
	 */
	public int getCount(){
		return this.count;
	}

	/**
	 * Resets the handler.
	 */
	public void clear(){
		this.count = 0;
	}

	public boolean isEnabled(){
		if(this.useSkbConsole==true){
			return Skb_Console.USE_CONSOLE;
		}
		switch(this.type){
			case INFO:
				return this.logger.isInfoEnabled();
			case WARNING:
				return this.logger.isWarnEnabled();
			case ERROR:
				return this.logger.isErrorEnabled();
		}
		return false;
	}

	/**
	 * Handles the message.
	 * @param message the message to be handled
	 * @param type message type
	 * @param appID application identifier for max message count
	 */
	public void handleMessage(String message, E_MessageType type, ST max100, Object appID){
		this.count++;

		boolean doMax = false;
		if(this.maxCount!=-1 && this.count>this.maxCount){
			max100.add("name", appID);
			max100.add("number", this.maxCount);
			doMax = true;
		}

		if(this.useSkbConsole==true){
			switch(type){
				case ERROR:
					Skb_Console.conError(message);
					if(doMax==true){
						Skb_Console.conError(max100.render());
					}
					break;
				case INFO:
					Skb_Console.conInfo(message);
					if(doMax==true){
						Skb_Console.conError(max100.render());
					}
					break;
				case WARNING:
					Skb_Console.conWarn(message);
					if(doMax==true){
						Skb_Console.conError(max100.render());
					}
					break;
			}
		}
		else{
			switch(type){
				case ERROR:
					this.logger.error(message);
					if(doMax==true){
						this.logger.error(max100.render());
					}
					break;
				case INFO:
					this.logger.info(message);
					if(doMax==true){
						this.logger.error(max100.render());
					}
					break;
				case WARNING:
					this.logger.warn(message);
					if(doMax==true){
						this.logger.error(max100.render());
					}
					break;
			}
		}
	}

}
