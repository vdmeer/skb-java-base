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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.StrBuilder;

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Methods to manipulate arrays.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.0-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Skb_ArrayUtils {

	/**
	 * Returns a transformer that flips table (an array of arrays).
	 * For each cell in the table the row and column indexes are 'turned over', that is for instance the cell [1][2] becomes [2][1].
	 * Consider an input table of
	 * <pre>
	 * row 1: a1, b1, c1
	 * row 2: a2, b2, c2
	 * row 3: a3, b3, c3
	 * </pre>
	 * the transformer will return a table of
	 * <pre>
	 * row 1: a1, a2, a3
	 * row 2: b1, b2, b3
	 * row 3: c1, c2, c3
	 * </pre>
	 * A null table as input results in a null table in the output.
	 * @return transformer that flips an array
	 */
	public static final Skb_Transformer<String[][], String[][]> FLIP_ARRAY(){
		return new Skb_Transformer<String[][], String[][]>(){
			@Override public String[][] transform(String[][] ar){
				if(ar==null){
					return null;
				}
				String[][] ret=new String[ar[0].length][ar.length];

				for(int i=0; i<ar[0].length; i++){
					for(int k=0; k<ar.length; k++){
						ret[i][k] = ar[k][i];
					}
				}
				return ret;
			}
		};
	}

	/**
	 * Returns a transformer that normalizes string arrays.
	 * @param length number of columns in the transformed string array
	 * @return transformer for normalizing string arrays
	 */
	public static final Skb_Transformer<String[][], String[][]> NORMALISE_ARRAY(final int length){
		return new Skb_Transformer<String[][], String[][]>(){
			@Override public String[][] transform(String[][] ar){
				int width = 0;
				//get the length of the longest array, use that as width in normalization
				for(int row=0; row<ar.length; row++){ //TODO not null safe
					width = Math.max(width, ArrayUtils.getLength(ar[row]));
				}
				if(width==0){
					width = 1;
				}
				String[][] ret = new String[length][width];

				for(int row=0; row<ar.length; row++){ //not null safe
					if(ar[row]==null){
						for(int i=0; i<width; i++){
							ret[row][i] = null;
						}
					}
					else if(ar[row].length==0){
						for(int i=0; i<width; i++){
							ret[row][i] = "";
						}
					}
					else{
						for(int col=0; col<ar[row].length; col++){
							ret[row][col] = ar[row][col];
						}
						if(ar[row].length<width){
							for(int i=ar[row].length; i<width; i++){
								ret[row][i] = "";
							}
						}
					}
				}
				return ret;
			}
		};
	}

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
}
