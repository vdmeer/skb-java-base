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

package de.vandermeer.skb.base.utils.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Factory for SKB collections, mainly for transformers.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.12 build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.5
 */
public abstract class Skb_CollectionFactory {

	/**
	 * Returns a transformer that takes a collection and transforms it into a textual representation, for instance for debug output.
	 * @return transformer for textual representation of the collection
	 */
	public static final Skb_Transformer<Collection<?>, String> transformerCollectionToText(){
		return new Skb_Transformer<Collection<?>, String>(){
			@Override public String transform(Collection<?> coll){
				//String template="    <collection:{n | - <n>}; separator=\"\n\">";		//simple string didn't work in tests??? :(
				//ST ret=new ST(template);

				String collG = "collection(entries) ::= <<\n    <entries:{n | - <n>}; separator=\"\n\">\n>>";
				STGroup stg = new STGroupString(collG);
				ST ret = stg.getInstanceOf("collection");

				if(coll!=null){
					for(Object obj : coll){
						ret.add("entries", obj);
					}
				}
				return ret.render();
			}
		};
	}

	/**
	 * Returns a transformer that transforms a map into a textual representation, for instance for debug output.
	 * @return transformer for a map to text
	 */
	public static final Skb_Transformer<Map<?, ?>, String> transformerMapToText(){
		return new Skb_Transformer<Map<?, ?>, String>(){
			@Override public String transform(Map<?, ?> map){
				String collG = "map(tree) ::= <<\n    <tree.keys:{k | - <k> ==> [<tree.(k).(\"type\")> <tree.(k).(\"val\")>]}; separator=\"\n\">\n>>";
				STGroup stg = new STGroupString(collG);
				ST ret = stg.getInstanceOf("map");

				LinkedHashMap<String, LinkedHashMap<String, String>> tree = new LinkedHashMap<String, LinkedHashMap<String, String>>();
				@SuppressWarnings("unchecked")
				Set<String> keySet = (Set<String>)map.keySet();
				Iterator<String> keyIt = keySet.iterator();
				while(keyIt.hasNext()){
					LinkedHashMap<String, String> node = new LinkedHashMap<String, String>();
					String key = keyIt.next();

					Object v = map.get(key);
					if(v!=null){
						node.put("type", v.getClass().getSimpleName());//TODO add maybe TypeMap
						String val = v.toString();
						if(val.contains("\n")){
							node.put("val", "\n    "+val);
						}
						else{
							node.put("val", val);
						}
						tree.put(key, node);
					}
				}
				ret.add("tree", tree);
				return ret.render();
			}
		};
	}
}
