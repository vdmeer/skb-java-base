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

package de.vandermeer.skb.base.info.loaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import de.vandermeer.skb.base.info.sources.DirectorySource;
import de.vandermeer.skb.base.info.sources.FileListSource;
import de.vandermeer.skb.base.info.sources.FileSource;

/**
 * Tests for {@link CommonsDirectoryWalker}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class Test_SimpleDirectoryScanner {

	@Test
	public void testScanJavaSource(){
		SimpleDirectoryScanner scanner = new SimpleDirectoryScanner(new DirectorySource("src/main/java"));
		FileListSource files = scanner.load();
		int checkSize = 144;

		assertTrue(files.isValid());
		assertEquals(checkSize, files.getSource().size());

		File[] filesAr = files.getSourceAsArray();
		assertTrue(filesAr!=null);
		assertEquals(checkSize, filesAr.length);

		List<FileSource> fsList = files.getSourceAsFileSourceList();
		assertTrue(fsList!=null);
		assertEquals(checkSize, fsList.size());

		FileSource[] fsArray = files.getSourceAsFileSourceArray();
		assertTrue(fsArray!=null);
		assertEquals(checkSize, fsArray.length);
	}

	@Test
	public void testScan(){
		SimpleDirectoryScanner scanner = new SimpleDirectoryScanner(null);
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(0, scanner.lastInfos().size());
		assertEquals(1, scanner.getLoadErrors().size()) ;

		scanner = new SimpleDirectoryScanner(new DirectorySource(" "));
		assertEquals(1, scanner.getLoadErrors().size()) ;

		scanner = new SimpleDirectoryScanner(new DirectorySource("test/resources/for-scanner-tests"));
 		assertEquals(1, scanner.getLoadErrors().size());
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(0, scanner.lastInfos().size());

		scanner = new SimpleDirectoryScanner(new DirectorySource("src/test/resources/for-scanner-tests"));
		scanner.load();
		assertEquals(0, scanner.getLoadErrors().size());
		assertEquals(0, scanner.lastWarnings().size());
		assertEquals(4, scanner.lastInfos().size());
	}
}
