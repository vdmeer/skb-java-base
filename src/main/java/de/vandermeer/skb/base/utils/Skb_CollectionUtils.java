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

package de.vandermeer.skb.base.utils;

import java.util.Collection;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Transformation methods for collections.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.3-SNAPSHOT build 141210 (10-Dec-14) for Java 1.8
 */
public abstract class Skb_CollectionUtils {

	/**
	 * Returns a transformer that takes a collection and transforms it into a textual representation, for instance for debug output.
	 * @return transformer for textual representation of the collection
	 */
	public static final Skb_Transformer<Collection<?>, String> COLLECTION_TO_TEXT(){
		return new Skb_Transformer<Collection<?>, String>(){
			@Override public String transform(Collection<?> coll){
				//String template="    <collection:{n | - <n>}; separator=\"\n\">";		//simple string didn't work in tests??? :(
				String collG = "collection(entries) ::= <<\n    <entries:{n | - <n>}; separator=\"\n\">\n>>";
				STGroup stg = new STGroupString(collG);
				ST ret = stg.getInstanceOf("collection");
				if(coll!=null){
					for(Object obj:coll){
						ret.add("entries", obj);
					}
				}
				return ret.render();
			}
		};
	}

	/**
	 * Transforms a collection into a textual representation, for instance for debug output
	 * @param coll input collection
	 * @return textual representation of the collection, empty string as default
	 */
	public final static String TRANSFORM(Collection<?> coll){
		return Skb_CollectionUtils.COLLECTION_TO_TEXT().transform(coll);
	}

	/**
	 * Returns the first element of the given collection.
	 * @param coll input collection
	 * @return first element of the collection or null if not applicable
	 */
	public static Object GET_FIRST_ELEMENT(Collection<?> coll){
		Object ret = null;
		if(coll.size()>0){
			Object[] ar = new Object[coll.size()];
			ar = coll.toArray(ar);
			for(int i=0; i<ar.length; i++){
				if((ar[i]!=null)){
					ret = ar[i];
					break;
				}
			}
		}
		return ret;
	}
}
