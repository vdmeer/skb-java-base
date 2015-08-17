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

package de.vandermeer.skb.base.console;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for console input/output.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 * @since      v0.0.5
 */
public abstract class Skb_Console {

	/**
	 * Console indicator.
	 * This flag indicates if the SKB components are running in a console (shell, with standard out/err access) or not.
	 * If the console is true, we are running in a shell environment, i.e. in an interactive mode.
	 * If not, then not, i.e. in a background mode.
	 * <br><br>
	 * 
	 * The logging and printing behavior of all components is then adapted to this setting.
	 * If in console mode, we can print information to standard out and errors to standard error plus do usual logging.
	 * If not in console more, than we should not print any information to standard out/err and do only usual logging.
	 * <br><br>
	 * 
	 * Note: this flag is static so it is the same for every object in the JVM. We do assume that different applications are
	 * executed in their own JVM, so that the setting does not collide with the applications intent.
	 * <br><br>
	 * 
	 * If in console mode, the SKB objects will use the {@link #SKB_CONSOLE_OUT} and {@link #SKB_CONSOLE_ERR} loggers for printing messages. This means
	 * one can still use the usual logging configuration (external) to decide what to do with printed messages. Usually one
	 * would direct {@link #SKB_CONSOLE_OUT} to an appender that prints to standard out and {@link #SKB_CONSOLE_ERR} to an appender that prints to standard error.
	 * <br><br>
	 * 
	 * The SKB components will print info, warn, and trace messages to {@link #SKB_CONSOLE_OUT} and error and debug messages to {@link #SKB_CONSOLE_ERR}.
	 */
	public static boolean USE_CONSOLE = false;

	/** SKB Logger used if CONSOLE is true to direct output for info, warn, and trace to standard out. See CONSOLE for behavior. */
	public static final Logger SKB_CONSOLE_OUT = LoggerFactory.getLogger("SKB_CONSOLE_OUT");

	/** SKB Logger used if CONSOLE is true to direct output for error and debug to standard out. See CONSOLE for behavior. */
	public static final Logger SKB_CONSOLE_ERR = LoggerFactory.getLogger("SKB_CONSOLE_ERR");

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level info.
	 * @param msg message to log
	 */
	public static void conInfo(String msg){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.info(msg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level info.
	 * @param msg message to log
	 * @param args arguments for the message
	 */
	public static void conInfo(String msg, Object[] args){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.info(msg, args);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level info.
	 * @param msg message to log
	 * @param arg single argument for the message
	 */
	public static void conInfo(String msg, Object arg){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.info(msg, arg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level warn.
	 * @param msg message to log
	 */
	public static void conWarn(String msg){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.warn(msg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level warn.
	 * @param msg message to log
	 * @param args arguments for the message
	 */
	public static void conWarn(String msg, Object[] args){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.warn(msg, args);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level warn.
	 * @param msg message to log
	 * @param arg single argument for the message
	 */
	public static void conWarn(String msg, Object arg){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.warn(msg, arg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level trace.
	 * @param msg message to log
	 */
	public static void conTrace(String msg){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.trace(msg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level trace.
	 * @param msg message to log
	 * @param args arguments for the message
	 */
	public static void conTrace(String msg, Object[] args){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.trace(msg, args);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_OUT} for log level trace.
	 * @param msg message to log
	 * @param arg single argument for the message
	 */
	public static void conTrace(String msg, Object arg){
		if(USE_CONSOLE){
			SKB_CONSOLE_OUT.trace(msg, arg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_ERR} for log level error.
	 * @param msg message to log
	 */
	public static void conError(String msg){
		if(USE_CONSOLE){
			SKB_CONSOLE_ERR.error(msg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_ERR} for log level error.
	 * @param msg message to log
	 * @param args arguments for the message
	 */
	public static void conError(String msg, Object[] args){
		if(USE_CONSOLE){
			SKB_CONSOLE_ERR.error(msg, args);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_ERR} for log level error.
	 * @param msg message to log
	 * @param arg single argument for the message
	 */
	public static void conError(String msg, Object arg){
		if(USE_CONSOLE){
			SKB_CONSOLE_ERR.error(msg, arg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_ERR} for log level debug.
	 * @param msg message to log
	 */
	public static void conDebug(String msg){
		if(USE_CONSOLE){
			SKB_CONSOLE_ERR.debug(msg);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_ERR} for log level debug.
	 * @param msg message to log
	 * @param args arguments for the message
	 */
	public static void conDebug(String msg, Object[] args){
		if(USE_CONSOLE){
			SKB_CONSOLE_ERR.debug(msg, args);
		}
	}

	/**
	 * Prints a message to {@link #SKB_CONSOLE_ERR} for log level debug.
	 * @param msg message to log
	 * @param arg single argument for the message
	 */
	public static void conDebug(String msg, Object arg){
		if(USE_CONSOLE){
			SKB_CONSOLE_ERR.debug(msg, arg);
		}
	}


	/**
	 * Returns the default encoding used at runtime.
	 * @return string with the encoding
	 */
	public static final String getDefaultEncoding(){
		byte [] byteArray = {'a'};
		InputStream inputStream = new ByteArrayInputStream(byteArray);
		InputStreamReader reader = new InputStreamReader(inputStream);
		return(reader.getEncoding());
	}

	/**
	 * Returns a new buffered reader for standard in, using UTF-8 encoding.
	 * @param appName application name for error messages
	 * @return new buffered reader for standard in using UTF-8 encoding, null if error occurred
	 */
	public static BufferedReader getStdIn(String appName){
		BufferedReader ret = null;
		try {
			ret = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		}
		catch(UnsupportedEncodingException ex) {
			Skb_Console.conError("{}: encoding exception opening SdtIn, expecting UTF-8 -> {}", new Object[]{appName, ex.getMessage()});
		}
		return ret;
	}

}
