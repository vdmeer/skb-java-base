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

package de.vandermeer.skb.base.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.Skb_ToStringStyle;
import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.composite.coin.CC_Info;
import de.vandermeer.skb.base.composite.coin.CC_Warning;
import de.vandermeer.skb.base.console.Skb_Console;
import de.vandermeer.skb.base.info.STGroupValidator;
import de.vandermeer.skb.base.message.EMessageType;
import de.vandermeer.skb.base.message.Message5WH;
import de.vandermeer.skb.base.message.Message5WH_Builder;

/**
 * The SKB Report Manager.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.0-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-commons before)
 */
public class ReportManager {
	/** 
	 * Map that maintains the expected templates and their arguments for the report manager.
	 * Any STGroup handed over the the report manager will be tested against this map. Templates
	 * or template arguments that are missing can be reported, and the report manager will not invoke
	 * in any report activity if the provided STGroup does not provide for all required chunks.
	 * The set templates and their arguments are:
	 * <ul>
	 * 		<li>report ::= who, what, when, why, how, type, reporter</li>
	 * 		<li>where ::= location, line, column</li>
	 * 		<li>maxErrors ::= name, number</li>
	 * </ul>
	 */
	public static final Map<String, Set<String>> stgChunks = ReportManager.loadChunks();

	/**
	 * Returns a map set with all expected template names and their expected arguments.
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

	/** Counters for Info (0), Warnings (1) and Errors (2). */
	protected int[] counters;

	/** Active report types. */
	protected List<EMessageType> reportTypes;

	/** Report STGroup. */
	protected STGroup stg;

	/** Maintains the load status, for instance keeps errors or warnings that occurred during load of a string template group. */
	protected final CC_Error errorList;

	/** Maximum number of errors, default is 100. */
	protected int maxErrors;

	/** List of collected reports (errors, warnings, information) as rendered strings. */
	protected List<String> reports;

	/** Dummy constructor, only used for cloning. */
	private ReportManager(){
		this.errorList=new CC_Error();
	}

	/** Logger for info messages. */
	protected Logger infoLogger = Skb_Console.SKB_CONSOLE_OUT;

	/** Logger for error messages. */
	protected Logger errorLogger = Skb_Console.SKB_CONSOLE_ERR;

	/** Logger for warning messages. */
	protected Logger warningLogger = Skb_Console.SKB_CONSOLE_OUT;

	/**
	 * Creates a new report manager loading the string templates from a file and setting maximum errors to 100.
	 * @param filename file name for loading an STGroup (can be in resources or file system)
	 * @param reporter reporting object or application
	 */
	public ReportManager(Object filename, Object reporter){
		this(filename, 100, reporter);
	}

	/**
	 * Creates a new report manager Creates a new report manager loading the string templates from a file.
	 * @param filename file name for loading an STGroup (can be in resources or file system)
	 * @param maxErrors maximum errors to report on, if reached a special message will be reported
	 * @param reporter reporting object or application
	 */
	public ReportManager(Object filename, int maxErrors, Object reporter){
		this.errorList = new CC_Error();
		this.loadStgFromFile(filename, maxErrors, reporter);
	}

	/**
	 * Sets the logger for information messages.
	 * @param logger logger object, defaults to EMessageType.INFO logger.
	 * @return returns self to allow for chained calls
	 */
	public ReportManager setInfoLogger(Logger logger){
		if(logger!=null){
			this.infoLogger = logger;
		}
		return this;
	}

	/**
	 * Sets the logger for error messages.
	 * @param logger logger object, defaults to EMessageType.ERROR logger.
	 * @return returns self to allow for chained calls
	 */
	public ReportManager setErrorLogger(Logger logger){
		if(logger!=null){
			this.errorLogger = logger;
		}
		return this;
	}

	/**
	 * Sets the logger for warning messages.
	 * @param logger logger object, defaults to EMessageType.WARNING logger.
	 * @return returns self to allow for chained calls
	 */
	public ReportManager setWarningLogger(Logger logger){
		if(logger!=null){
			this.warningLogger = logger;
		}
		return this;
	}

	/**
	 * Reload an STG from file, overwrites previous loaded groups.
	 * @param filename file to read the STG from
	 * @param reporter callee (for error messages)
	 * @return list of errors, if size=0 everything was ok, if not list contains error descriptions
	 */
	public CC_Error loadStgFromFile(Object filename, Object reporter){
		return this.loadStgFromFile(filename, 100, reporter);
	}

	/**
	 * Reload an STG from file, overwrites previous loaded groups.
	 * @param filename file to read the STG from
	 * @param maxErrors maximum errors allowed
	 * @param reporter callee (for error messages)
	 * @return list of errors, if size=0 everything was ok, if not list contains error descriptions
	 */
	public CC_Error loadStgFromFile(Object filename, int maxErrors, Object reporter){
		try{
			this.stg = new STGroupFile(filename.toString());
		}
		catch(Exception e){
			this.errorList.add(
					new Message5WH_Builder()
					.addWhat("could not load STGroup from resource/file <", filename, ">")
					.setReporter(reporter)
					.setWhere(this.getClass().getSimpleName())
					.addWhy(e.getMessage())
					.build()
			);
		}
		this.init(maxErrors, reporter);
		return this.errorList;
	}

	/**
	 * Creates a new report manager setting maximum errors to 100.
	 * @param stg STGroup for messages
	 * @param reporter reporting object or application
	 */
	public ReportManager(STGroup stg, Object reporter){
		this(stg, 100, reporter);
	}

	/**
	 * Creates a new report manager.
	 * @param stg STGroup for messages
	 * @param maxErrors maximum errors to report on, if reached a special message will be reported
	 * @param reporter reporting object or application
	 */
	public ReportManager(STGroup stg, int maxErrors, Object reporter){
		this.errorList = new CC_Error();

		this.stg = stg;
		this.init(maxErrors, reporter);
	}

	/**
	 * Sets the event types the manager should report.
	 * Event types provided will be reported. Event types not provided will not be reported (i.e. ignored).
	 * Null elements will be ignored. If the whole argument is null, no changes will be made (i.e. previous settings are maintained)
	 * Calling this method will override any previous settings.
	 * @param types event types to use in reporting
	 * @return returns self to allow for chained calls
	 */
	public ReportManager configureReporting(Iterable<EMessageType> types) {
		if(types!=null){
			this.reportTypes.clear();
			for(EMessageType t : types){
				if(t!=null){
					this.reportTypes.add(t);
				}
			}
		}
		return this;
	}

	/**
	 * Returns the current count for the given message type since its last initialisation or reset.
	 * @param type message type to report count for
	 * @return current count of messages reported
	 */
	public int getMessageCount(EMessageType type) {
		return this.counters[type.getNumber()];
	}

	/**
	 * Initialize the report manager.
	 * @param maxErrors maximum errors
	 * @param reporter reporting object or application
	 */
	protected final void init(int maxErrors, Object reporter){
		this.maxErrors = maxErrors;
		this.counters = new int[]{0,0,0};
		this.reportTypes = new ArrayList<EMessageType>(3);

		if(this.stg!=null){
			STGroupValidator stgv = new STGroupValidator(this.stg, ReportManager.stgChunks);
			if(!stgv.isValid()){
				for(Message5WH msg : stgv.getValidationErrors().getList()){
					this.errorList.add(new Message5WH_Builder().addWhat(msg.getWhat()).setReporter(reporter).setWhere(this.getClass().getSimpleName()).build());
				}
			}
			else{
				this.reportTypes.addAll(Arrays.asList(EMessageType.values()));	//only activate reporting if stg was ok
			}

//			Set<String> messages = Skb_STUtils.getMissingChunksErrorMessages(Skb_STUtils.getStgName(this.stg), Skb_STUtils.getMissingChunks(this.stg, ReportManager.stgChunks));
//			if(messages.size()>0){
//				for(String s : messages){
//					this.errorList.add(new Message5WH().addWhat(s).setReporter(reporter).setWhere(this.getClass().getSimpleName()));
//				}
//			}
//			else{
//				this.reportTypes.addAll(Arrays.asList(EMessageType.values()));	//only activate reporting if stg was ok
//			}
		}
	}

	/**
	 * Returns the list of errors, which contains all problems that might have occurred during initialization.
	 * @return error list
	 */
	public CC_Error getInitErrors(){
		return this.errorList.getCopy();
	}

	/**
	 * Returns is the report level is enabled or not.
	 * This method can help to reduce the computational resources needed to report.
	 * One can call this method before calling a report method,
	 * preventing any arguments to be created, thus saving execution time.
	 * @param message message type to check
	 * @return true if the message type is in the list of active message types of the report manager instance and if the associated logger is enabled, false otherwise
	 */
	public boolean isEnabledFor(EMessageType message) {
		if(!this.reportTypes.contains(message)){
			return false;
		}
		switch(message){
			case INFO:
				return this.infoLogger.isInfoEnabled();
			case WARNING:
				return this.warningLogger.isWarnEnabled();
			case ERROR:
				return this.errorLogger.isErrorEnabled();
		}
		return false;
	}

	/**
	 * Returns the load status of the manager.
	 * If the load was successful, the status will be success. In any other case the status will be a set of errors.
	 * @return success if loaded successfully, set of errors otherwise
	 */
	public boolean isLoaded() {
		return (this.errorList.size()==0)?true:false;
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
		boolean ret = false;
		if(this.stg==null){
			return ret;	//fatal error, no templates
		}

		if(message==null || !this.reportTypes.contains(message.getType()) || !this.isEnabledFor(message.getType())){
			return ret;	//no message or type not activated or logger not enabled
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

		this.counters[message.getType().getNumber()]++;
		switch(message.getType()){
			case INFO:
				this.infoLogger.info(template.render());
				//LoggerFactory.getLogger(message.getType().getLoggerName()).info(template.render());
				ret = true;
				break;
			case WARNING:
				this.warningLogger.warn(template.render());
				//LoggerFactory.getLogger(message.getType().getLoggerName()).warn(template.render());
				ret = true;
				break;
			case ERROR:
				this.errorLogger.error(template.render());
				//LoggerFactory.getLogger(message.getType().getLoggerName()).error(template.render());
				if(this.counters[message.getType().getNumber()]>this.maxErrors){
					ST error100=this.stg.getInstanceOf("maxError");
					error100.add("name", message.getReporter());
					error100.add("number", this.maxErrors);
					this.errorLogger.error(error100.render());
					//LoggerFactory.getLogger(message.getType().getLoggerName()).error(error100.render());
				}
				ret = true;
				break;
			default:
				break;
		}

		//finally collect the information, regardless of logger settings for report type
		if(this.reports!=null){
			this.reports.add(template.render());
		}
		return ret;
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
		if(message instanceof CC_Info){
			for(Message5WH w : ((CC_Info)message).getList()){
				ret = ret&this.report(w);
			}
		}
		if(message instanceof CC_Warning){
			for(Message5WH w : ((CC_Warning)message).getList()){
				ret = ret&this.report(w);
			}
		}
		else if(message instanceof CC_Error){
			for(Message5WH e : ((CC_Error)message).getList()){
				ret = ret&this.report(e);
			}
		}
		else if(message instanceof Object[]){
			for(Object o : (Object[])message){
				if(o instanceof Message5WH){
					ret = ret&this.report((Message5WH)o);
				}
			}
		}
		else if(message instanceof Iterable){
			for(Object o : (Iterable<?>)message){
				if(o instanceof Message5WH){
					ret = ret&this.report((Message5WH)o);
				}
			}
		}
		return ret;
	}

	/**
	 * Resets the count for the given message type to 0.
	 * @param type to reset count for
	 * @return returns self to allow for chained calls
	 */
	public ReportManager resetMessageCount(EMessageType type) {
		if(type!=null){
			this.counters[type.getNumber()] = 0;
		}
		return this;
	}

	/**
	 * Returns a deep copy of the ReportManager
	 * @return deep copy
	 */
	public ReportManager getCopy() {
		ReportManager ret = new ReportManager();
		ret.stg = this.stg;
		ret.counters = new int[]{this.counters[0], this.counters[1], this.counters[2]};
		ret.configureReporting(this.reportTypes);
		return ret;
	}

	@Override
	public String toString() {
		ToStringBuilder ret = new ToStringBuilder(this, Skb_ToStringStyle.TS_STYLE)
			.append("types       ", this.reportTypes)
			.append("counters    ", this.counters)
			.append("max errs    ", this.maxErrors)
			.append("------------------------------------")
			.append("errors #    ", this.errorList.size())
			.append("errors      ", this.errorList)
			.append("------------------------------------")
			.append("stg       ", this.stg, false)
			.append("stg       ", this.stg)
			.append("------------------------------------")
			;

		return ret.toString();
	}

	/**
	 * Returns a string representation of the current STG.
	 * @return string of the STG
	 */
	public String getStgAsString(){
		if(this.stg!=null){
			return this.stg.show();
		}
		return "";
	}

	/**
	 * Returns the collected reports.
	 * @return null if collection is disabled, a list of collected reports otherwise
	 */
	public List<String> getCollectedReports(){
		return this.reports;
	}

	/**
	 * Activates or deactivates the collection of reports.
	 * @param collect if true, all reports are collected in a local list, if false no collection will take place
	 * @return returns self to allow for chained calls 
	 */
	public ReportManager collectReports(boolean collect){
		if(collect==true){
			this.reports = new ArrayList<String>();
		}
		else{
			this.reports = null;
		}
		return this;
	}
}