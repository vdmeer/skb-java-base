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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for the directory scanner.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150712 (12-Jul-15) for Java 1.8
 * @since      v0.0.6
 */
public class Test_DirectoryScanner {

	@Test
	public void testScan(){
		DirectoryScanner scanner = new DirectoryScanner();
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(0, scanner.lastInfos().size());
		assertEquals(0, scanner.lastErrors().size());

		Set<File> files;

		files = scanner.getFiles(null);
		assertEquals(null, files);
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(0, scanner.lastInfos().size());
		assertEquals(1, scanner.lastErrors().size());

		files = scanner.getFiles("");
		assertEquals(null, files);
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(0, scanner.lastInfos().size());
		assertEquals(1, scanner.lastErrors().size());

		files = scanner.getFiles("test/resources/for-scanner-tests");
		assertEquals(null, files);
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(0, scanner.lastInfos().size());
		assertEquals(1, scanner.lastErrors().size());

		files = scanner.getFiles("src/test/resources/for-scanner-tests");
		assertEquals(4, files.size());
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(4, scanner.lastInfos().size());
		assertEquals(0, scanner.lastErrors().size());

//		System.err.println(scanner);
//		for(File f : files){
//			System.err.println(f.getAbsolutePath());
//		}
	}
}
