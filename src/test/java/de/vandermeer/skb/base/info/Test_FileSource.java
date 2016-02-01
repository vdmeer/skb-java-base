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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import de.vandermeer.skb.base.info.FileSource;
import de.vandermeer.skb.base.info.InfoLocationOptions;
import de.vandermeer.skb.base.info.ValidationOptions;

/**
 * Tests for {@link FileSource}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
<<<<<<< HEAD
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
=======
 * @version    v0.1.6 build 160201 (01-Feb-16) for Java 1.8
>>>>>>> dev
 * @since      v0.0.7
 */
public class Test_FileSource {

	@Test
	public void testValOption(){
		FileSource fsn = new FileSource((File)null);
		assertEquals(ValidationOptions.AS_SOURCE, fsn.valOption());
	}

	@Test
	public void testConstructorSuccessCase_File(){
		FileSource fsn;

		fsn = new FileSource(new File("src/test/resources/de/vandermeer/skb/base/info/loaders/test.properties"));
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource(new File("src/test/resources/de/vandermeer/skb/base/info/loaders", "test.properties"));
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());
	}

	@Test
	public void testConstructorSuccessCase_DirFn(){
		FileSource fsn;

		fsn = new FileSource("de/vandermeer/skb/base/info/loaders/", "test.properties");
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("de/vandermeer/skb/base/info/loaders/", "test.properties", InfoLocationOptions.RESOURCE_ONLY);
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("de/vandermeer/skb/base/info/loaders", "test.properties");
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("de/vandermeer/skb/base/info/loaders", "test.properties", InfoLocationOptions.RESOURCE_ONLY);
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("src/test/resources/de/vandermeer/skb/base/info/loaders/", "test.properties");
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("src/test/resources/de/vandermeer/skb/base/info/loaders/", "test.properties", InfoLocationOptions.FILESYSTEM_ONLY);
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("src/test/resources/de/vandermeer/skb/base/info/loaders", "test.properties");
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("src/test/resources/de/vandermeer/skb/base/info/loaders", "test.properties", InfoLocationOptions.FILESYSTEM_ONLY);
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());
	}

	@Test
	public void testConstructorSuccessCase_Fn(){
		FileSource fsn;

		fsn = new FileSource("de/vandermeer/skb/base/info/loaders/test.properties");
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("de/vandermeer/skb/base/info/loaders/test.properties", InfoLocationOptions.RESOURCE_ONLY);
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("src/test/resources/de/vandermeer/skb/base/info/loaders/test.properties");
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());

		fsn = new FileSource("src/test/resources/de/vandermeer/skb/base/info/loaders/test.properties", InfoLocationOptions.FILESYSTEM_ONLY);
		assertEquals(0, fsn.getInitError().size());
		assertTrue(fsn.isValid());
	}

	@Test
	public void testConstructorErrorCases_Fn(){
		FileSource fsn;

		//fileName null means error
		fsn = new FileSource((String)null);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//blank fileName means error
		fsn = new FileSource("");
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//nothing on FS nor RES is error
		fsn = new FileSource("src/main/test.test");
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//nothing on FS is error
		fsn = new FileSource("src/main/test.test", InfoLocationOptions.FILESYSTEM_ONLY);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//nothing on RES is error
		fsn = new FileSource("src/main/test.test", InfoLocationOptions.RESOURCE_ONLY);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//valid as RES only, but requested as FS
		fsn = new FileSource("de/vandermeer/skb/base/info/loaders/test.properties", InfoLocationOptions.FILESYSTEM_ONLY);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//valid as FS only, but requested as RES
		fsn = new FileSource("src/test/resources/de/vandermeer/skb/base/info/loaders/test.properties", InfoLocationOptions.RESOURCE_ONLY);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());
	}

	@Test
	public void testConstructorErrorCases_DirFn(){
		FileSource fsn;

		//directory null means error
		fsn = new FileSource((String)null, "test");
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//fileName null means error
		fsn = new FileSource("test", (String)null);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//blank directory means error
		fsn = new FileSource("", "test");
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//blank fileName means error
		fsn = new FileSource("test", "");
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//nothing on FS nor RES is error
		fsn = new FileSource("src/main/", "test.test");
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//nothing on FS is error
		fsn = new FileSource("src/main/", "test.test", InfoLocationOptions.FILESYSTEM_ONLY);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//nothing on RES is error
		fsn = new FileSource("src/main/", "test.test", InfoLocationOptions.RESOURCE_ONLY);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());
	}

	@Test
	public void testConstructorErrorCases_File(){
		FileSource fsn;

		//null file means error
		fsn = new FileSource((File)null);
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//non accessible file means error
		fsn = new FileSource(new File("src/main/test.test"));
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());

		//non accessible file means error
		fsn = new FileSource(new File("src/main/", "test.test"));
		assertTrue(fsn.getInitError().size()!=0);
		assertFalse(fsn.isValid());
	}

//	@Test
//	public void testSetRoot(){
//		FileSource fsn;
//
//		fsn = new FileSource("de/vandermeer/skb/base/info/loaders/test.properties");
//		assertTrue(fsn.getInitError().size()==0);
//		System.err.println(fsn.getOriginalFilename());
//	}

}
