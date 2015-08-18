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
 * 		<li>{@link de.vandermeer.skb.base.message.Message5WH_Renderer} - a renderer object that is used to render a message before printing or storing.</li>
 * 		<li>{@link de.vandermeer.skb.base.message.E_MessageType} - an enumerate for the supported message types, e.g. info, warning, error.</li>
 * 		<li>{@link de.vandermeer.skb.base.message.FormattingTupleWrapper} - a wrapper to create SLF4J formatting tuples and convert them into strings when needed.
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
 * 
 * <p>
 * 		In principal, a message can be rendered in two different ways: simply call its render() method or create a {@link de.vandermeer.skb.base.message.Message5WH_Renderer}
 * 		object and let it render the message.
 * </p>
 * 
 * 
 * <h4>Message render method</h4>
 * The render() method of the message creates a message renderer with the default (standard) template for rendering messages.
 * This standard template is pointed to by {@link de.vandermeer.skb.base.message.Message5WH_Renderer#DEFAULT_STG_FN}.
 * A default STGroup is also provided here {@link de.vandermeer.skb.base.message.Message5WH_Renderer#DEFAULT_STG}.
 * Rendering the message create above using the render() method will result in the following output (printed to standard out):
 * <pre>
	The Author: from Test_Examples at (noon) in the package API documentation showing a test message
	        ==&gt; as a demo
	        ==&gt; added to the package JavaDoc
 * </pre>
 * 
 * 
 * <h4>Message Renderer</h4>
 * 
 * To use the message renderer create a new renderer. Three constructors are provided:
 * <ul>
 * 		<li>No arguments - creates a renderer for the default (standard) template.</li>
 * 		<li>STGroup as argument - creates a renderer using the given STGroup.</li>
 * 		<li>String argument - tries to load an STGroup from a file and use it.</li>
 * </ul>
 * 
 * Any loaded STGroup will be validated against the required chunks. Those chunks define the expected methods and arguments the template must provide.
 * The chunks are defined in {@link de.vandermeer.skb.base.message.Message5WH_Renderer#STG_CHUNKS} as follows:
 * <ul>
 * 		<li>Method {@code message5wh} with arguments: {@code reporter}, {@code type}, {@code who}, {@code when}, {@code where}, {@code what}, {@code why}, {@code how}</li>
 * 		<li>Method {@code where} with arguments: {@code location}, {@code line}, {@code column}</li>
 * </ul>
 * 
 * All but one arguments in the template will be provided with simple objects. The exception is the {@code type} argument, which can have the following values:
 * <ul>
 * 		<li>{@code type.info} - if the type is {@link de.vandermeer.skb.base.message.E_MessageType#INFO}</li>
 * 		<li>{@code type.warning} - if the type is {@link de.vandermeer.skb.base.message.E_MessageType#WARNING}</li>
 * 		<li>{@code type.error} - if the type is {@link de.vandermeer.skb.base.message.E_MessageType#ERROR}</li>
 * </ul>
 * 
 * Once a renderer is created and the STGroup successfully loaded it can be used to render message object or collections of message objects.
 * Simply call the appropriate render method.
 * 
 * The following example loads a renderer using a defined template (in the src/test/resources of the package source) and uses that to render the message above:
 * <pre>{@code
	Message5WH_Renderer ren = new Message5WH_Renderer("de/vandermeer/skb/base/message/5wh-example.stg");
	String rendererd = ren.render(msg);
	System.out.println(rendererd);
 * }</pre>
 * 
 * With this template the same message will render as:
 * <pre>
	showing a test message in the package API documentation created by from Test_Examples as a demo added to the package JavaDoc 
	This has been reported:
	- by The Author
	- as info
	- at noon
 * </pre>
 * 
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 */
package de.vandermeer.skb.base.message;