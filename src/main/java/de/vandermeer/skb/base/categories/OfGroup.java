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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import de.vandermeer.skb.base.utils.collections.Skb_CollectionFilter;

/**
 * Category of objects that belong to multiple groups.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.9 build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface OfGroup extends CategoryOf {
	/**
	 * Returns the groups something belongs to.
	 * @return list of groups
	 */
	default List<IsGroup> getGroups() {
		return new ArrayList<IsGroup>();
	}

	/**
	 * Returns a predicate that evaluates to true if category is of given group
	 * @param group group to evaluate against
	 * @return true if category is of given group
	 */
	static Predicate<OfGroup> IN_GROUP(final IsGroup group){
		return new Predicate<OfGroup>(){
			@Override public boolean test(final OfGroup k){
				if(group==null || k==null || k.getGroups()==null){
					return false;
				}
				else{
					return k.getGroups().contains(group);
				}
			}
		};
	}

	/**
	 * Filter that uses the {@link OfGroup#IN_GROUP(IsGroup)} predicate to filter a list of categories by group
	 * @param group filter argument
	 * @param keys array of categories
	 * @return list of all categories that are of given type
	 */
	static Collection<OfGroup> GET_KEYS_FOR_GROUP(final IsGroup group, final OfGroup[] keys){
		return Skb_CollectionFilter.FILTER(OfGroup.IN_GROUP(group), keys);
	}

	/**
	 * Filter that uses the {@link OfGroup#IN_GROUP(IsGroup)} predicate to filter a list of categories by group
	 * @param group filter argument
	 * @param keys variable number of arrays containing categories
	 * @return list of all categories that are of given type
	 */
	static Collection<OfGroup> GET_KEYS_FOR_GROUP(final IsGroup group, final OfGroup[] ... keys){
		return Skb_CollectionFilter.FILTER(OfGroup.IN_GROUP(group), keys);
	}

}
