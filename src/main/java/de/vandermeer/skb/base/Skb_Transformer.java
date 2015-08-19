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

package de.vandermeer.skb.base;

import java.util.function.Function;

/**
 * Standard transformer interface.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.3 build 150819 (19-Aug-15) for Java 1.8
 * @since      v0.0.1
 */
public interface Skb_Transformer <FROM, TO> extends Function<FROM, TO> {

	/**
	 * Transforms from one representation to another.
	 * @param f input representation
	 * @return output representation or null if input was null or unexpected class
	 */
	TO transform(FROM f);

	@Override
	default TO apply(FROM f){
		return transform(f);
	}

	/**
	 * Chained transformer.
	 * This method allows a chain of transformers to be applied to a single transformation. The chain will be processed
	 * until a transformer returns a non-null result. The final result, of no transformer was applied successfully, is null.
	 * @param <T> type of the transformation target
	 * @param transformers chain of transformers to be used
	 * @return transformation or null if no transformer returned successfully
	 */
	@SuppressWarnings("unchecked")
	static <T extends Object> Skb_Transformer<Object, T> CHAIN(final Skb_Transformer<Object, T> ... transformers){
		return new Skb_Transformer<Object, T>(){
			@Override public T transform(Object o){
				T ret = null;
				for(Skb_Transformer<Object, T> tf:transformers){
					ret = tf.transform(o);
					if(ret!=null){
						return ret;
					}
				}
				return ret;
			}
		};
	}

}
