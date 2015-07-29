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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.junit.Test;

import de.vandermeer.skb.base.info.CommonsDirectoryWalker;
import de.vandermeer.skb.base.info.FileListSource;
import de.vandermeer.skb.base.info.FileSource;

/**
 * Tests for {@link CommonsDirectoryWalker}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.0-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class Test_CommonsDirectoryWalker {

	@Test
	public void testWalkJavaSource(){

		IOFileFilter ff = new WildcardFileFilter(new String[]{
				"*.java"
		});
		CommonsDirectoryWalker ldw = new CommonsDirectoryWalker("src/main/java", DirectoryFileFilter.INSTANCE, ff);
		FileListSource files = ldw.load();
		int checkSize = 147;

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

}
