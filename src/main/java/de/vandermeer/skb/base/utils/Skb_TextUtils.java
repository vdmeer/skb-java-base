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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.WordUtils;

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Text converters.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 140626 (26-Jun-14) with Java 1.8
 */
public abstract class Skb_TextUtils {

	/**
	 * Returns a transformer that takes a 2 dimensional array and transforms it into a textual representation, for instance for debug output.
	 * @param <T> type of the input array
	 * @return transformer for textual representation of the 2 dimensional array
	 */
	public static final <T> Skb_Transformer<T[][], StrBuilder> ARRAY_TO_TEXT(){
		return new Skb_Transformer<T[][], StrBuilder>(){
			@Override public StrBuilder transform(T[][] ar){
				StrBuilder ret = new StrBuilder(50);
				for(int row=0; row<ar.length; row++){ //TODO not null save
					if(ar[row]==null){
						ret.append("[").append(row).appendln("]: null");
					}
					else if(ar[row].length==0){
						ret.append("[").append(row).appendln("]: 0");
					}
					else{
						for(int col=0; col<ar[row].length; col++){
							ret.append("[").append(row).append("][").append(col).append("]: ");
							if(ar[row][col]==null){
								ret.appendln("null");
							}
							else if("".equals(ar[row][col])){
								ret.appendln("0");
							}
							else{
								ret.appendln(ar[row][col]);
							}
						}
					}
				}
				return ret;
			}
		};
	}

//	/**
//	 * Returns a transformer that takes a collection and transforms it into a textual representation, for instance for debug output.
//	 * @return transformer for textual representation of the collection
//	 */
//	public static final Transformer<Collection<?>, String> COLLECTION_TO_TEXT(){
//		return new Transformer<Collection<?>, String>(){
//			@Override public String transform(Collection<?> coll){
//				//String template="    <collection:{n | - <n>}; separator=\"\n\">";		//simple string didn't work in tests??? :(
//				String collG="collection(entries) ::= <<\n    <entries:{n | - <n>}; separator=\"\n\">\n>>";
//				STGroup stg=new STGroupString(collG);
//				ST ret=stg.getInstanceOf("collection");
//				if(coll!=null){
//					for(Object obj:coll){
//						ret.add("entries", obj);
//					}
//				}
//				return ret.render();
//			}
//		};
//	}

	/**
	 * Returns a transformer that takes a map and returns a string representation of its contents.
	 * @return map to string transformer
	 */
	public static final Skb_Transformer<Map<String, ?>, String> MAP_TO_TEXT(){
		return new Skb_Transformer<Map<String, ?>, String>(){
			@Override public String transform(Map<String, ?> map){
				StrBuilder ret = new StrBuilder(map.size()*20);
				TreeSet<String> keys = new TreeSet<String>(map.keySet());
				for(String leaf : keys){
//					int level=StringUtils.countMatches(leaf, builder.getSeparatorAsString());
//					ret.append('-');
//					for(int i=0;i<=level;i++){
//						ret.append("-");
//					}
//					ret.append("> ").append(leaf);
					ret.append("--> ").append(leaf);
					ret.append(" ::= ");
					ret.append(map.get(leaf));
					ret.append('\n');
				}
				return ret.toString();
			}
		};
	}

	/**
	 * Returns a transformer that takes an iterator and returns a String Builder.
	 * @param <T> common target type
	 * @param separator string or character to use as separator between elements of the iterator
	 * @param toText a transformer used to transform an object to text
	 * @return transformer that returns a String Builder that concatenates all elements from the iterator using the given separator
	 */
	public static final <T extends Object> Skb_Transformer<Object, StrBuilder> MANYOBJECTS_TO_STRBUILDER(final Object separator, final Skb_Transformer<Object, T> toText){
		return new Skb_Transformer<Object, StrBuilder>(){
			@Override public StrBuilder transform(Object obj) {
				StrBuilder ret = new StrBuilder(50);
				if(obj!=null && toText!=null){
					if(obj instanceof Iterator){
						while(((Iterator<?>)obj).hasNext()){
							ret.appendSeparator(separator.toString());
							ret.append(toText.transform(((Iterator<?>)obj).next()));
						}
					}
					else if(obj instanceof Iterable){
						for(Object o : (Iterable<?>)obj){
							ret.appendSeparator(separator.toString());
							ret.append(toText.transform(o));
						}
					}
					else if(obj instanceof Object[]){
						for(int i=0; i<((Object[])obj).length; i++){
							ret.appendSeparator(separator.toString());
							ret.append(toText.transform(((Object[])obj)[i]));
						}
					}
					else{
						ret.appendSeparator(separator.toString());
						ret.append(toText.transform(obj));
					}
				}
				return ret;
			}
		};
	}

	/**
	 * Returns a transformer that takes an object and returns a string.
	 * @return transformer from object to string, the transformation returns the object itself it it was a string, the object's toString method if it is not null, an empty string otherwise */
	public static final Skb_Transformer<Object, String> TO_STRING(){
		return new Skb_Transformer<Object, String>(){
			@Override public String transform(Object obj) {
				if(obj instanceof String){
					return (String)obj;
				}
				if(obj!=null){
					return obj.toString();
				}
				return "";
			}
		};
	}

	/**
	 * Returns a transformer that takes an object and returns a string array with wrapped lines of max length.
	 * For the input object, null and empty are allowed.
	 * The wrapping is done using StringUtils and WordUtils so that words are not broken into characters.
	 * @param length maximum length of a line
	 * @return transformer from object to String[], the transformation returns null if object was null or toString() returns null, empty array if empty string, array with lines of wrappings otherwise
	 */
	public static final Skb_Transformer<Object, String[]> WRAP_LINES(final int length){
		return new Skb_Transformer<Object, String[]>(){
			@Override public String[] transform(Object obj) {
				if(obj==null || obj.toString()==null){
					return null;
				}
				if("".equals(obj)){
					return new String[]{};
				}
				return StringUtils.split(WordUtils.wrap(obj.toString(), length, "@@@", true), "@@@");
			}
		};
	}
}
