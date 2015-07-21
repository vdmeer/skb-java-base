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

/**
 * Transformation methods for collections.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.7 build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Skb_CollectionUtils {

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
