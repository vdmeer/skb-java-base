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
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.info.STGroupValidator;
import de.vandermeer.skb.base.message.Message5WH;

/**
 * Renderer for a {@link Message5WH} object.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.1.2
 */
public class MessageRenderer {

	/** The ST group for the message */
	protected STGroup stg;

	/** The file name of the default STGroup file. */
	public static String DEFAULT_STG_FN = "de/vandermeer/skb/base/managers/5wh.stg";

	/** The default STGroup for messages read from {@link #DEFAULT_STG_FN}. */
	public static STGroup DEFAULT_STG = new STGroupFile(MessageRenderer.DEFAULT_STG_FN);

	/** The STGroup chunks for validation of an STGroup for a message. */
	public final static Map<String, Set<String>> STG_CHUNKS = new HashMap<String, Set<String>>(){
		private static final long serialVersionUID = 1L;{
			put("where", new HashSet<String>(){
				private static final long serialVersionUID = 1L;{
					add("location");
					add("line");
					add("column");
				}}
			);
			put("message5wh", new HashSet<String>(){
				private static final long serialVersionUID = 1L;{
					add("reporter");
					add("type");
					add("who");
					add("when");
					add("where");
					add("what");
					add("why");
					add("how");
				}}
			);
		}
	};

	/**
	 * Returns a new renderer with the default STGroup.
	 */
	public MessageRenderer(){
		this.stg = MessageRenderer.DEFAULT_STG;
		this.validateSTG();
	}

	/**
	 * Returns a new renderer with the given STGroup.
	 * @param stg given STGroup for the renderer
	 * @throws IllegalArgumentException if the given STGroup was null or not valid
	 */
	public MessageRenderer(STGroup stg){
		this.stg = stg;
		this.validateSTG();
	}

	/**
	 * Returns a new renderer with the STGroup read from the given file name (file system or as resource).
	 * @param filename file to read STGroup from
	 * @throws IllegalArgumentException if the given file name or STGroup was null or not valid
	 */
	public MessageRenderer(String filename){
		if(StringUtils.isBlank(filename)){
			throw new IllegalArgumentException("filename cannot be blank (null or empty)");
		}
		this.stg = new STGroupFile(filename);
		this.validateSTG();
	}

	/**
	 * Validates the local STGroup
	 * @throws IllegalArgumentException if the local STGroup was null or not valid
	 */
	protected void validateSTG(){
		if(this.stg==null){
			throw new IllegalArgumentException("stg is null");
		}

		STGroupValidator stgv = new STGroupValidator(this.stg, MessageRenderer.STG_CHUNKS);
		if(stgv.getValidationErrors().hasErrors()){
			throw new IllegalArgumentException(stgv.getValidationErrors().render());
		}
	}

	/**
	 * Renders a single message
	 * @param msg message to render
	 * @return string with the rendered message, empty if parameter was null
	 */
	public String render(Message5WH msg) {
		if(msg!=null){
			ST ret = this.stg.getInstanceOf("message5wh");
			if(msg.getWhereLocation()!=null){
				ST where = this.stg.getInstanceOf("where");
				where.add("location", msg.getWhereLocation());
				if(msg.getWhereLine()>0){
					where.add("line", msg.getWhereLine());
				}
				if(msg.getWhereColumn()>0){
					where.add("column", msg.getWhereColumn());
				}
				ret.add("where", where);
			}

			ret.add("reporter", msg.getReporter());

			if(msg.getType()!=null){
				Map<String, Boolean> typeMap = new HashMap<>();
				typeMap.put(msg.getType().toString(), true);
				ret.add("type", typeMap);
			}

			if(msg.getWhat()!=null && !StringUtils.isBlank(msg.getWhat().toString())){
				ret.add("what", msg.getWhat());
			}

			ret.add("who",  msg.getWho());
			ret.add("when", msg.getWhen());

			ret.add("why", msg.getWhy());
			ret.add("how", msg.getHow());

			return ret.render();
		}
		return "";
	}

	/**
	 * Renders a collection of messages.
	 * @param messages the collection of messages to render
	 * @return string with rendered messages
	 */
	public String render(Collection<Message5WH> messages){
		StrBuilder ret = new StrBuilder(50);
		for(Message5WH msg : messages){
			ret.appendln(this.render(msg));
		}
		return ret.toString();
	}
}
