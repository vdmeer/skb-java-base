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

/**
 * Classes for a simple 5WH message object, for details on 5WH see <a target="_new" href="https://en.wikipedia.org/wiki/Five_Ws">Wikipedia</a>.
 * 
 * This message object contains information following the 5WH news style (see also <a target="_new" href="https://en.wikipedia.org/wiki/Five_Ws">Wikipedia</a>): 
 * <ul>
 * 		<li>Who is it about?</li>
 * 		<li>What happened?</li>
 * 		<li>When did it take place?</li>
 * 		<li>Where did it take place?</li>
 * 		<li>Why did it happen?</li>
 * 		<li>How did it happen?</li>
 * </ul>
 * 
 * The 'where' part of a message is further divided into
 * <ul>
 * 		<li>Location - the location of the where part, e.g. a file in which an error was detected. The location is mandatory to use the where part.</li>
 * 		<li>Line - a line in the location, e.g. a line in a file with an error. The line is optional, but must be present if column (see below) is used.</li>
 * 		<li>Column - a column in the location, e.g. a column in a line in a file with an error. The column is optional and can be used along with line (see above).</li>
 * </ul>
 * 
 * In addition to that, the object also provides for information on
 * <ul>
 * 		<li>Reporter - the object actually reporting the message, e.g. a shell or a compiler</li>
 * 		<li>Message type - a type, e.g. information, warning, error</li>
 * </ul>
 * 
 * The package provides a set of classes for creating and processing messages:
 * <ul>
 * 		<li>{@link de.vandermeer.skb.base.message.Message5WH} - the actual message object with all the parts introduced above.</li>
 * 		<li>{@link de.vandermeer.skb.base.message.Message5WH_Builder} - a builder object that is used to create a message object.</li>
 * 		<li>{@link de.vandermeer.skb.base.managers.MessageRenderer} - a renderer object that is used to render a message before printing or storing.</li>
 * 		<li>{@link de.vandermeer.skb.base.message.E_MessageType} - an enumerate for the supported message types, e.g. info, warning, error.</li>
 * 		<li>{@link de.vandermeer.skb.interfaces.FormattingTupleWrapper} - a wrapper to create SLF4J formatting tuples and convert them into strings when needed.
 * 			The wrapper creates a new formatting tuple and uses its getMessage() method in toString().
 * 		</li>
 * </ul>
 * 
 * 
 * <h3>Creating a message</h3>
 * 
 * Use the builder to create a message. The following example shows a simple information message being created:
 * <pre>{@code
	Message5WH msg = new Message5WH_Builder()
		.setWho("from " + this.getClass().getSimpleName())
		.addWhat("showing a test message")
		.setWhen(null)
		.setWhere("the package API documentation", 0, 0)
		.addWhy("as a demo")
		.addHow("added to the package JavaDoc")
		.setReporter("The Author")
		.setType(EMessageType.INFO)
		.build()
	;
 * }</pre>
 * 
 * <p>
 * 		The builder provides methods to set the who, when, and where parts of the message as well as the reporter and the type.
 * 		For the what, why, and how parts the builder provides methods to add to existing information.
 * 		The where part of a message as a special case is supported with several methods to be set.
 * </p>
 * 
 * Once all information is set, the builder can be instructed to build an actual message using the build() method.
 * 
 * 
 * <h3>Rendering a message</h3>
 * <p>
 * 		The message class provides a {@code render()} method to render a message.
 * 		Internally, this method creates a StrBuilder, fills it conditional with all information of the message, and returns the string representation of that builder.
 * </p>
 * 
 * Rendering the message create above using the render() method will result in the following output (printed to standard out):
 * <pre>
	The Author: from Test_Examples at (noon) in the package API documentation showing a test message
	        ==&gt; as a demo
	        ==&gt; added to the package JavaDoc

 * </pre>
 * 
 * 
 * <h3>Changing the render output</h3>
 * <p>
 * 		Changing the render output requires to create a new class inheriting from the {@link de.vandermeer.skb.base.message.Message5WH} class and overwriting the {@code render()} method.
 * 		Alternatively, one can implement a message renderer with any required functionality.
 * 		The package 'managers' does exactly that: implement a message renderer that uses string templates and provides many options to render single messages, collections of messages and some classes that use message objects.
 * </p>
 * 
 * 
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.2.0 build 170404 (04-Apr-17) for Java 1.8
 */
package de.vandermeer.skb.base.message;