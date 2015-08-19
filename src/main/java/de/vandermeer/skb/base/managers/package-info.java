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
 * Classes that manage other classes or artifacts, for instance a message manager.
 * 
 * 
 * 
 * <h3>Message Renderer {@link de.vandermeer.skb.base.managers.MessageRenderer}</h3>
 * 
 * To use the message renderer create a new renderer. Three constructors are provided:
 * <ul>
 * 		<li>No arguments - creates a renderer for the default (standard) template.</li>
 * 		<li>STGroup as argument - creates a renderer using the given STGroup.</li>
 * 		<li>String argument - tries to load an STGroup from a file and use it.</li>
 * </ul>
 * 
 * This standard template is pointed to by {@link de.vandermeer.skb.base.managers.MessageRenderer#DEFAULT_STG_FN}.
 * A default STGroup is also provided here {@link de.vandermeer.skb.base.managers.MessageRenderer#DEFAULT_STG}.
 * 
 * Any loaded STGroup will be validated against the required chunks. Those chunks define the expected methods and arguments the template must provide.
 * The chunks are defined in {@link de.vandermeer.skb.base.managers.MessageRenderer#STG_CHUNKS} as follows:
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
 * Let's create a message first:
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
 * The following example loads a renderer using a defined template (in the src/test/resources of the package source) and uses that to render the message above:
 * <pre>{@code
	Message5WH_Renderer ren = new Message5WH_Renderer("de/vandermeer/skb/base/message/5wh-example.stg");
	String rendered = ren.render(msg);
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
 * 
 * 
 * 
 * 
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150817 (17-Aug-15) for Java 1.8
 */
package de.vandermeer.skb.base.managers;