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

package de.vandermeer.skb.base.info;

/**
 * Options to locate information.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public enum InfoLocationOptions {
	/** Try to locate as resource first, then in the file system (for a file). */
	TRY_RESOURCE_THEN_FS,

	/** Try to locate in the file system first, then as resource (for a file). */
	TRY_FS_THEN_RESOURCE,

	/** Try to locate using class path as root, then in the file system (for directory). */
	TRY_CLASSPATH_THEN_FS,

	/** Try to locate in the file system first, then using class path as root (for directory). */
	TRY_FS_THEN_CLASSPATH,

	/** Try to locate in the file system only. */
	FILESYSTEM_ONLY,

	/** Try to locate as resource only (for file). */
	RESOURCE_ONLY,

	/** Try only in class path (for directory). */
	CLASSPATH_ONLY,
	;
}
