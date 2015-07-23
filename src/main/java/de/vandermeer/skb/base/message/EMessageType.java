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

/**
 * Types for {@link Message5WH}.
 * Supported types are information, warning and error. Each type has an associated unique number and a default name for a logger.
 * The logger name can be used in combination with logging frameworks (such as slf4j or log4j), which need to be configured appropriatetly.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8 build 150723 (23-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public enum EMessageType {
	/** An information (or informal) message */
	INFO (0, "SKBLoggerInfo"),

	/** A warning message, usually rather formal in format */
	WARNING (1, "SKBLoggerWarning"),

	/** An error message, usually rather formal in format */
	ERROR (2, "SKBLoggerError"),
	;

	/** The number of this instance */
	private int number;

	/** The logger name of this instance */
	private String loggerName;

	private EMessageType(int number, String loggerName){
		this.number = number;
		this.loggerName = loggerName;
	}

	/**
	 * Returns the logger name associated with the enumerate instance.
	 * The logger name is a string, uses "SKBLogger" as prefix and then a capitalised version of the 
	 * enumerate's name as a suffix. This makes the returned name unique. In association with logging frameworks,
	 * such as log4j or slf4j, it can be used to automatically retrieve a logger for a message of this type.
	 * @return logger name
	 */
	public String getLoggerName(){
		return this.loggerName;
	}

	/**
	 * Returns a unique number for this enumerate.
	 * @return unique number between 0 and numbers of enumerates in the class
	 */
	public int getNumber(){
		return this.number;
	}

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
