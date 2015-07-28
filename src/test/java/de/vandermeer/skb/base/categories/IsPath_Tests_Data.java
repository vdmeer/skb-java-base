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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Test data.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.6 (was in skb-composite before)
 */
public class IsPath_Tests_Data {

	/** Class for FQPN tuples using path/name combinations */
	public class FqpnObject{
		public Object path;
		public Object name;
		public String expected;

		public FqpnObject(Object path, Object name, String expected){
			this.path = path;
			this.name = name;
			this.expected = expected;
		}
	}

	/** Class for FQPN tuples using a string */
	public class FqpnString{
		public String fqpn;
		public boolean isvalid;
		public int expectedLevel;

		public FqpnString(String fqpn, boolean isvalid, int expectedLevel){
			this.fqpn = fqpn;
			this.isvalid = isvalid;
			this.expectedLevel = expectedLevel;
		}
	}

	public final String sepReplace="{sep}";

	public Map<Integer, FqpnObject> forBuild;

	public Map<Integer, FqpnString> forMisc;

	public IsPath_Tests_Data(){
		this.fillObjects();
		this.fillStrings();
	}

	/** Build the map with objects for tests */
	private void fillObjects(){
		int count=0;
		this.forBuild=new LinkedHashMap<Integer, FqpnObject>();

		this.forBuild.put(count++, new FqpnObject(null,    null,    ""));
		this.forBuild.put(count++, new FqpnObject("{sep}", null,    "{sep}"));
		this.forBuild.put(count++, new FqpnObject(null,    "{sep}", "{sep}"));
		this.forBuild.put(count++, new FqpnObject("{sep}", "{sep}", "{sep}"));

		this.forBuild.put(count++, new FqpnObject("{sep}{sep}", null,         "{sep}"));
		this.forBuild.put(count++, new FqpnObject(null,         "{sep}{sep}", "{sep}"));
		this.forBuild.put(count++, new FqpnObject("{sep}{sep}", "{sep}{sep}", "{sep}"));

		this.forBuild.put(count++, new FqpnObject("a",  null, "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(null,  "a", "{sep}a"));

		this.forBuild.put(count++, new FqpnObject("a{sep}b", null,      "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(null,      "a{sep}b", "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject("a",       "b",       "{sep}a{sep}b"));

		this.forBuild.put(count++, new FqpnObject("a{sep}b{sep}c", null,    "{sep}a{sep}b{sep}c"));
		this.forBuild.put(count++, new FqpnObject("a{sep}b",   "c",         "{sep}a{sep}b{sep}c"));
		this.forBuild.put(count++, new FqpnObject("a",     "b{sep}c",       "{sep}a{sep}b{sep}c"));
		this.forBuild.put(count++, new FqpnObject(null,    "a{sep}b{sep}c", "{sep}a{sep}b{sep}c"));


		this.forBuild.put(count++, new FqpnObject("a{sep}{sep}b{sep}{sep}c",                     null, "{sep}a{sep}b{sep}c"));
		this.forBuild.put(count++, new FqpnObject("a{sep}{sep}{sep}b{sep}{sep}{sep}c",           null, "{sep}a{sep}b{sep}c"));
		this.forBuild.put(count++, new FqpnObject("a{sep}{sep}{sep}{sep}b{sep}{sep}{sep}{sep}c", null, "{sep}a{sep}b{sep}c"));

		this.forBuild.put(count++, new FqpnObject(null, "a{sep}{sep}b{sep}{sep}c",                     "{sep}a{sep}b{sep}c"));
		this.forBuild.put(count++, new FqpnObject(null, "a{sep}{sep}{sep}b{sep}{sep}{sep}c",           "{sep}a{sep}b{sep}c"));
		this.forBuild.put(count++, new FqpnObject(null, "a{sep}{sep}{sep}{sep}b{sep}{sep}{sep}{sep}c", "{sep}a{sep}b{sep}c"));


		this.forBuild.put(count++, new FqpnObject("a{sep}b",           "{sep}c{sep}d",           "{sep}a{sep}b{sep}c{sep}d"));
		this.forBuild.put(count++, new FqpnObject("a{sep}b{sep}",      "{sep}{sep}c{sep}d",      "{sep}a{sep}b{sep}c{sep}d"));
		this.forBuild.put(count++, new FqpnObject("a{sep}b{sep}{sep}", "{sep}{sep}{sep}c{sep}d", "{sep}a{sep}b{sep}c{sep}d"));

		this.forBuild.put(count++, new FqpnObject("{sep}a{sep}b", "{sep}",        "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject("{sep}a{sep}b", "{sep}{sep}",   "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject("{sep}",        "{sep}a{sep}b", "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject("{sep}{sep}",   "{sep}a{sep}b", "{sep}a{sep}b"));



		LinkedList<String> list=new LinkedList<String>();

		this.forBuild.put(count++, new FqpnObject(list.clone(), null,         "{sep}"));
		this.forBuild.put(count++, new FqpnObject(null,         list.clone(), "{sep}"));
		this.forBuild.put(count++, new FqpnObject(list.clone(), list.clone(), "{sep}"));

		list.add("a");
		this.forBuild.put(count++, new FqpnObject(list.clone(), null,         "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(null,         list.clone(), "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(list.clone(), list.clone(), "{sep}a{sep}a"));

		list.add(null);
		this.forBuild.put(count++, new FqpnObject(list.clone(), null,         "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(null,         list.clone(), "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(list.clone(), list.clone(), "{sep}a{sep}a"));

		list.add("b");
		this.forBuild.put(count++, new FqpnObject(list.clone(), null,         "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(null,         list.clone(), "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(list.clone(), list.clone(), "{sep}a{sep}b{sep}a{sep}b"));

		list.add(null);
		this.forBuild.put(count++, new FqpnObject(list.clone(), null,         "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(null,         list.clone(), "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(list.clone(), list.clone(), "{sep}a{sep}b{sep}a{sep}b"));



		String[] arr=new String[]{};

		this.forBuild.put(count++, new FqpnObject(arr,  null, "{sep}"));
		this.forBuild.put(count++, new FqpnObject(null, arr,  "{sep}"));
		this.forBuild.put(count++, new FqpnObject(arr,  arr,  "{sep}"));

		arr=new String[]{"a"};
		this.forBuild.put(count++, new FqpnObject(arr,  null, "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(null, arr,  "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(arr,  arr,  "{sep}a{sep}a"));

		arr=new String[]{"a", null};
		this.forBuild.put(count++, new FqpnObject(arr,  null, "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(null, arr,  "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(arr,  arr,  "{sep}a{sep}a"));

		arr=new String[]{"a", null, "b"};
		this.forBuild.put(count++, new FqpnObject(arr,  null, "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(null, arr,  "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(arr,  arr,  "{sep}a{sep}b{sep}a{sep}b"));

		arr=new String[]{"a", null, "b", null};
		this.forBuild.put(count++, new FqpnObject(arr,  null, "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(null, arr,  "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(arr,  arr,  "{sep}a{sep}b{sep}a{sep}b"));



		arr=new String[]{};
		List<String> it;

		it=Arrays.asList(arr);
		this.forBuild.put(count++, new FqpnObject(it.iterator(), null,          "{sep}"));
		this.forBuild.put(count++, new FqpnObject(null,          it.iterator(), "{sep}"));
		this.forBuild.put(count++, new FqpnObject(it.iterator(), it.iterator(), "{sep}"));

		arr=new String[]{"a"};
		it=Arrays.asList(arr);
		this.forBuild.put(count++, new FqpnObject(it.iterator(), null,          "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(null,          it.iterator(), "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(it.iterator(), it.iterator(), "{sep}a{sep}a"));

		arr=new String[]{"a", null};
		it=Arrays.asList(arr);
		this.forBuild.put(count++, new FqpnObject(it.iterator(), null,          "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(null,          it.iterator(), "{sep}a"));
		this.forBuild.put(count++, new FqpnObject(it.iterator(), it.iterator(), "{sep}a{sep}a"));

		arr=new String[]{"a", null, "b"};
		it=Arrays.asList(arr);
		this.forBuild.put(count++, new FqpnObject(it.iterator(), null,          "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(null,          it.iterator(), "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(it.iterator(), it.iterator(), "{sep}a{sep}b{sep}a{sep}b"));

		arr=new String[]{"a", null, "b", null};
		it=Arrays.asList(arr);
		this.forBuild.put(count++, new FqpnObject(it.iterator(), null,          "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(null,          it.iterator(), "{sep}a{sep}b"));
		this.forBuild.put(count++, new FqpnObject(it.iterator(), it.iterator(), "{sep}a{sep}b{sep}a{sep}b"));

//		this.forBuild.put(count++, new FqpnObject(path, name, expectedFqpn));
	}

	private void fillStrings(){
		int count=0;
		this.forMisc=new LinkedHashMap<Integer, FqpnString>();

		//this.forMisc.put(count++, new FqpnString(fqpn, isvalid, expectedLevel));

		this.forMisc.put(count++, new FqpnString(null,         false, -1)); // must not be empty
		this.forMisc.put(count++, new FqpnString(new String(), false, -1));
		this.forMisc.put(count++, new FqpnString("",           false, -1));
		this.forMisc.put(count++, new FqpnString(" ",          false, -1));
		this.forMisc.put(count++, new FqpnString("	",         false, -1));
		this.forMisc.put(count++, new FqpnString("\n",         false, -1));
		this.forMisc.put(count++, new FqpnString("\r",         false, -1));
		this.forMisc.put(count++, new FqpnString("\r\n",       false, -1));
		this.forMisc.put(count++, new FqpnString(" 	\n\r\n",   false, -1));

		this.forMisc.put(count++, new FqpnString("a",            false, -1)); // must start with {sep}
		this.forMisc.put(count++, new FqpnString("a{sep}b" ,     false, -1));
		this.forMisc.put(count++, new FqpnString("a{sep}{sep}b", false, -1));

		this.forMisc.put(count++, new FqpnString("{sep}{sep}a",                   false, -1)); //must not contain {sep}{sep}
		this.forMisc.put(count++, new FqpnString("{sep}a{sep}{sep}",              false, -1));
		this.forMisc.put(count++, new FqpnString("{sep}{sep}a{sep}{sep}",         false, -1));
		this.forMisc.put(count++, new FqpnString("{sep}a{sep}b{sep}{sep}c{sep}c", false, -1));

		this.forMisc.put(count++, new FqpnString("{sep}a{sep}",       false, -1)); // must not end with {sep}
		this.forMisc.put(count++, new FqpnString("{sep}a{sep}b{sep}", false, -1));


		this.forMisc.put(count++, new FqpnString("{sep}", true, 0)); // special case, can be {sep} only

		this.forMisc.put(count++, new FqpnString("{sep}a",                   true, 1)); // number of valid FQPNs with their level
		this.forMisc.put(count++, new FqpnString("{sep}a{sep}b",             true, 2));
		this.forMisc.put(count++, new FqpnString("{sep}a{sep}b{sep}c",       true, 3));
		this.forMisc.put(count++, new FqpnString("{sep}a{sep}b{sep}c{sep}d", true, 4));
	}

}
