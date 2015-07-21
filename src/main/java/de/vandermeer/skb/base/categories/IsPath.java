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

package de.vandermeer.skb.base.categories;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.Skb_Defaults;
import de.vandermeer.skb.base.Skb_Transformer;
import de.vandermeer.skb.base.utils.Skb_TextUtils;
import de.vandermeer.skb.base.utils.collections.Skb_CollectionFilter;

/**
 * Category of objects that represent a path.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8-SNAPSHOT build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsPath extends CategoryIs, CategoryWithValue, HasDescription {
	/**
	 * Returns the path
	 * @return path
	 */
	default String path(){
		return this._value();
	}

	@Override
	String _value();

	/**
	 * Returns a new generic {@link IsPath} object with a path (immutable).
	 * @param path the object's path
	 * @param description a description for the new path
	 * @return new {@link IsPath} object with a set path
	 */
	static IsPath create(final Object path, final String description){
		if(path!=null && path instanceof IsPath){
			return (IsPath)path;
		}
		else{
			return new IsPath(){
				@Override public String _value() {return (path==null)?Skb_Defaults.DEFAULT_VALUE:path.toString();}
				@Override public Object getDescription() {return (description==null)?Skb_Defaults.DEFAULT_DESCRIPTION:description;}
				@Override public String toString(){return toLog(IsPath.class);}
			};
		}
	}

	/**
	 * Returns a new generic {@link IsPath} object with defined path and a default description (immutable).
	 * @param path the object's path
	 * @return new {@link IsPath} object with a default description
	 */
	static IsPath create(Object path){
		return IsPath.create(path, null);
	}

	/**
	 * Returns a new generic {@link IsPath} object with a default preset path (immutable).
	 * @return new {@link IsPath} object with a default path
	 */
	static IsPath create(){
		return IsPath.create(null);
	}

	/**
	 * Returns a predicate that validates a path value.
	 * The path value is evaluated as FQPN string in terms of:
	 * <ul>
	 * 		<li>is of length one and contains only the separator</li>
	 * 		<li> or fulfils all of the following criteria
	 * 			<ul> 
	 * 				<li>is not empty (null, "" or only white spaces</li>
	 * 				<li>does start with the separator, e.g. '/'</li>
	 * 				<li>does not contain any sequence of separators, e.g. '//'</li>
	 * 				<li>does not end with the separator</li>
	 * 			</ul>
	 * 		</li>
	 * </ul>
	 * @param separator path separator
	 * @return predicate that validates a path value with return of -1 if FQPN not valid, 0 if equal to separator, &gt;0 otherwise
	 */
	static Predicate<Object> IS_VALID_PATH_VALUE(final String separator){
		return new Predicate<Object>(){
			@Override public boolean test(final Object fqpn){
				String path = null;
				try{
					if(fqpn instanceof IsPath){
						path = ((IsPath)fqpn).path().toString();
					}
					else{
						path = fqpn.toString();
					}
				}
				catch(Exception ignore){}

				if(path==null){
					return false;
				}
				if(path.isEmpty()){
					return false;
				}
				if(path.length()==1 && path.startsWith(separator)){
					return true;
				}
				if(path.startsWith(separator)){ 				// must start with separator
					if(!path.contains(separator+separator)){ 	// must not contain e.g. '//'
						if(!path.endsWith(separator)){ 			// must not end with separator
							return true;
						}
					}
				}
				return false;
			}
		};
	}

	/**
	 * Returns a predicate that evaluates if a given path is a sub path using a specified path separator.
	 * Sub path here means that the path given to the predicate as a string is a child of the given FQPN.
	 * @param separator path separator
	 * @param fqpn original path as FQPN
	 * @return predicate that tests for sub paths
	 */
	static Predicate<String> IS_SUB_PATH_OF(final String separator, final Object fqpn){
		return new Predicate<String>(){
			Skb_Transformer<Object, Integer> transformer=IsPath.PATH_TO_LEVELS(separator);
			@Override public boolean test(final String s){
				String path = null;
				try{
					if(fqpn instanceof IsPath){
						path = ((IsPath)fqpn).path().toString();
					}
					else{
						path = fqpn.toString();
					}
				}
				catch(Exception ignore){}

				if(path==null){
					return false;
				}
				int level = this.transformer.transform(fqpn);
				if(s.equals(path)){
					return false;
				}
				if(s.startsWith(path)){
					int curLevel = this.transformer.transform(s);
					if(level<=curLevel){
						return true;
					}
				}
				return false;
			}
		};
	}

	/**
	 * Returns a transformer that calculates the levels in a path.
	 * The level is calculated for a given FQPN as follows:
	 * <ul>
	 * 		<li>-1 if the FQPN is not valid</li>
	 * 		<li>0 if the FQPN is valid, of size one and contains (only) a single separator</li>
	 * 		<li>&gt;0 if the FQPN is valid (number is effectively the count of separators in the FQPN)
	 * </ul>
	 * @param separator path separator to be used
	 * @return transformer for path-level calculations
	 */
	static Skb_Transformer<Object, Integer> PATH_TO_LEVELS(final String separator){
		return new Skb_Transformer<Object, Integer>(){
			final private Predicate<Object> isValidPath=IsPath.IS_VALID_PATH_VALUE(separator);
			@Override public Integer transform(Object fqpn){
				Integer ret = -1;
				String path = null;
				try{
					if(fqpn instanceof IsPath){
						path = ((IsPath)fqpn).path().toString();
					}
					else{
						path = fqpn.toString();
					}
				}
				catch(Exception ignore){}

				if(this.isValidPath.test(fqpn)){
					if(path.length()==1 && path.startsWith(separator)){	//fqpn == sep means level=0
						ret = 0;
					}
					else{
						ret = StringUtils.countMatches(path, separator);
					}
				}
				return ret;
			}
		};
	}

	/**
	 * Returns a collection with the names of all children of a given path from a given collection of paths
	 * @param separator path separator to use
	 * @param fqpn name of the path
	 * @param coll all paths that are children of the given path
	 * @return collection of paths that are sub paths of the given fqpn
	 */
	static Collection<String> GET_SUB_PATHS(String separator, Object fqpn, Collection<String> coll){
		return Skb_CollectionFilter.FILTER(IsPath.IS_SUB_PATH_OF(separator, fqpn), coll);
	}

	/**
	 * Returns a transformer that takes a path (as StrBuilder) and returns a list of all path contained.
	 * For instance, if the FQPN built for <code>path</code> and <code>name</code> is "/a/b/c/d", then the returned array will be
	 * <code>[a, a/b, a/b/c, a/b/c/d]</code> (assuming that the separator is '/').
	 * The resulting array will have a root node added if autoRoot is activated.
	 * @param separator path separator
	 * @param autoRootArray add root node if true, add nothing if false
	 * @return a transformer that takes a path and returns a list of all contained paths, or an empty list
	 */
	static Skb_Transformer<StrBuilder, List<String>> PATH_TO_ARRAY_OF_PATHS(final String separator, final Boolean autoRootArray){
		return new Skb_Transformer<StrBuilder, List<String>>(){
			final private Predicate<Object> isValidPath=IsPath.IS_VALID_PATH_VALUE(separator);
			@Override public List<String> transform(StrBuilder fqpn){
				List<String> ret = new LinkedList<String>();
				if(this.isValidPath.test(fqpn)){
					if(autoRootArray==true){
						ret.add(separator);	//add root
					}

					String[] elements = StringUtils.split(fqpn.toString(), separator);
					StrBuilder add = new StrBuilder(25);
					for(int i=0; i<elements.length; i++){
						add.clear();
						for(int k=0; k<=i; k++){
							add.append(separator).append(elements[k]);
						}
						ret.add(add.toString());
					}
				}
				return ret;
			}
		};
	}

	/**
	 * Returns a transformer that removes all excessive elements (such as double separators).
	 * @param separator path separator
	 * @return a transformer that returns a FQPN that does not contain excessive separators, but can be invalid otherwise
	 */
	static Skb_Transformer<StrBuilder, StrBuilder> CLEAN_PATH(final String separator){
		return new Skb_Transformer<StrBuilder, StrBuilder>(){
			@Override public StrBuilder transform(StrBuilder in){
				try{
					if(in.size()>0){
						String[] ar = StringUtils.split(in.toString(), separator);
						in.clear();
						in.appendWithSeparators(ar, separator);
					}
				}
				catch(Exception ignore){}
				return in;
			}
		};
	}

	/**
	 * Returns a transformer that adds the separator in front of a path (if path does not start already with separator)
	 * @param separator path separator
	 * @return a transformer that returns an FQPN that starts with a separator, but can be invalid otherwise
	 */
	static Skb_Transformer<StrBuilder, StrBuilder> ADD_ROOT(final String separator){
		return new Skb_Transformer<StrBuilder, StrBuilder>(){
			@Override public StrBuilder transform(StrBuilder fqpn){
				try{
					if(fqpn.indexOf(separator)!=0){
						fqpn.insert(0, separator);
					}
				}
				catch(Exception ignore){}
				return fqpn;
			}
		};
	}

	/**
	 * Returns a transformer that combines a pair of path information into a single path. If the A in the pair is null, then null will be returned.
	 * In all other cases a path is returned.
	 * Path and name are processed as follows:
	 * <ul>
	 * 		<li>if it is null, nothing happens</li>
	 * 		<li>if it is an instance of Object[], all elements of the array are joined using the separator</li>
	 * 		<li>if it is an instance of Iterator, all elements the iterator returns are joined using the separator</li>
	 * 		<li>if it is an instance of Iterable, all elements the iterable returns are joined using the separator</li>
	 * 		<li>in all other case the object's toString() method is used as path/name components</li>
	 * </ul>
	 * The returned FQPN can be valid or invalid, depending on the doClean and setRoot flags.
	 * If both flags are set true, the returned FQPN will be a valid one. If either of them is set to false, the FQPN might
	 * be valid depending on path/name components.
	 * @param <A> left type of pair
	 * @param <B> right type of pair
	 * @param separator path separator
	 * @param doClean clean the final path (if true)
	 * @param setRoot set a root separator on the final path (if true)
	 * @return transformer that combines objects to a path
	 */
	static <A, B> Skb_Transformer<Pair<A, B>, StrBuilder> JOIN_PATH_ELEMENTS(final String separator, final Boolean doClean, final Boolean setRoot){
		return new Skb_Transformer<Pair<A, B>, StrBuilder>(){
			@SuppressWarnings("unchecked")
			Skb_Transformer<Object, StrBuilder> tr = Skb_TextUtils.MANYOBJECTS_TO_STRBUILDER(separator, Skb_Transformer.CHAIN(CategoryWithValue.CAT_TO_VALUESTRING(), Skb_TextUtils.TO_STRING()));
			Skb_Transformer<StrBuilder, StrBuilder> clean = IsPath.CLEAN_PATH(separator);
			Skb_Transformer<StrBuilder, StrBuilder> asRoot = IsPath.ADD_ROOT(separator);
			@Override public StrBuilder transform(Pair<A, B> pair){
				StrBuilder ret = new StrBuilder(20);
				if(pair.a==null && pair.b==null){
					return ret;
				}
				ret = tr.transform(pair.a);
				if(!ret.endsWith(separator)){
					ret.append(separator);
				}
				ret.append(tr.transform(pair.b));

				if(doClean==true){
					ret = this.clean.transform(ret);
				}
				if(setRoot==true){
					ret = this.asRoot.transform(ret);
				}

				return ret;
			}
		};
	}

	/**
	 * Checks if the FQPN is less or equal to a maximum depth
	 * @param fqpn FQPN for testing
	 * @param maxDepth maximum depth to test for, set to -1 for no test
	 * @param separator path separator
	 * @return FQPN if maximum depth is -1 or if level of FQPN is less or equal to given maximum depth, null otherwise (e.g. maximum depth &lt; -2, FQPN not valid)
	 */
	static StrBuilder forMaxDepth(StrBuilder fqpn, int maxDepth, String separator){
		Skb_Transformer<Object, Integer> transformer = IsPath.PATH_TO_LEVELS(separator);
		if(maxDepth==-1 || transformer.transform(fqpn)<=maxDepth){
			return fqpn;
		}
		return new StrBuilder();
	}
}
