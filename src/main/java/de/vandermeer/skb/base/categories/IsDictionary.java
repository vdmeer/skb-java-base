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
import java.util.List;

import de.vandermeer.skb.base.Skb_Pair;

/**
 * A dictionary between two languages.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.11-SNAPSHOT build 150805 (05-Aug-15) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public interface IsDictionary<SOURCE, TARGET> {

	/**
	 * Returns a translation from target to source, or null if none found.
	 * @param target object to be translated
	 * @return translation, null if none found
	 */
	SOURCE toSource(TARGET target);

	/**
	 * Returns a translation from source to target, or null if none found.
	 * @param source object to be translated
	 * @return translation, null if none found
	 */
	TARGET toTarget(SOURCE source);

	/**
	 * Returns all known translations for a given target.
	 * @param target object to be translated
	 * @return all known translations, empty list if none found
	 */
	Collection<SOURCE> toAllSources(TARGET target);

	/**
	 * Returns all known translations for a given source.
	 * @param source object to be translated
	 * @return all known translations, empty list if none found
	 */
	Collection<TARGET> toAllTargets(SOURCE source);

	/**
	 * Returns all pairs of target/source translation that contain a specified target.
	 * @param target filter object
	 * @return list of translation pairs that contain the filter in the target part
	 */
	Skb_Pair<SOURCE, TARGET> getPair4Target(TARGET target);

	/**
	 * Returns all pairs of target/source translation that contain a specified source.
	 * @param source filter object
	 * @return list of translation pairs that contain the filter in the source part
	 */
	Skb_Pair<SOURCE, TARGET> getPair4Source(SOURCE source);

	/**
	 * Returns a list of all translations of the dictionary.
	 * @return list of translations, empty list of there is no translation in the dictionary
	 */
	List<Skb_Pair<SOURCE, TARGET>> getTranslations();
}
