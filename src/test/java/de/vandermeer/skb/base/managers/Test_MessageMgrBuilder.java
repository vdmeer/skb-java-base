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

package de.vandermeer.skb.base.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.skb.base.info.STGroupValidator;
import de.vandermeer.skb.base.message.EMessageType;

/**
 * Tests for Tests for {@link MessageMgrBuilder}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.1 build 150814 (14-Aug-15) for Java 1.8
 * @since      v0.0.13
 */
public class Test_MessageMgrBuilder {

	Object retNull = mock(Object.class);
	Object retEmpty = mock(Object.class);


	@Rule public ExpectedException exception = ExpectedException.none();


	@Before
	public void setup(){
		when(retNull.toString()).thenReturn(null);
		when(retEmpty.toString()).thenReturn("");
	}


	@Test
	public void test_StgChunks(){
		Map<String, Set<String>> stgChunks = MessageMgrBuilder.loadChunks();

		assertTrue(stgChunks.containsKey("report"));
		assertTrue(stgChunks.get("report").contains("who"));
		assertTrue(stgChunks.get("report").contains("what"));
		assertTrue(stgChunks.get("report").contains("when"));
		assertTrue(stgChunks.get("report").contains("where"));
		assertTrue(stgChunks.get("report").contains("who"));
		assertTrue(stgChunks.get("report").contains("how"));
		assertTrue(stgChunks.get("report").contains("type"));
		assertTrue(stgChunks.get("report").contains("reporter"));

		assertTrue(stgChunks.containsKey("where"));
		assertTrue(stgChunks.get("where").contains("location"));
		assertTrue(stgChunks.get("where").contains("line"));
		assertTrue(stgChunks.get("where").contains("column"));

		assertTrue(stgChunks.containsKey("maxErrors"));
		assertTrue(stgChunks.get("maxErrors").contains("name"));
		assertTrue(stgChunks.get("maxErrors").contains("number"));
	}


	@Test
	public void test_DefaultSTG(){
		STGroup stg = new STGroupFile("de/vandermeer/skb/base/report-manager.stg");
		STGroupValidator stgv = new STGroupValidator(stg, MessageMgrBuilder.loadChunks());
		assertTrue(stgv.isValid());
	}


	@Test
	public void test_ConstructorException(){
		try {new MessageMgrBuilder(null);fail("no exception");} catch (IllegalArgumentException expected) {}
		try {new MessageMgrBuilder(null, false);fail("no exception");} catch (IllegalArgumentException expected) {}

		try {new MessageMgrBuilder("");fail("no exception");} catch (IllegalArgumentException expected) {}
		try {new MessageMgrBuilder("", false);fail("no exception");} catch (IllegalArgumentException expected) {}

		try {new MessageMgrBuilder(retNull);fail("no exception");} catch (IllegalArgumentException expected) {}
		try {new MessageMgrBuilder(retNull, false);fail("no exception");} catch (IllegalArgumentException expected) {}

		try {new MessageMgrBuilder(retEmpty);fail("no exception");} catch (IllegalArgumentException expected) {}
		try {new MessageMgrBuilder(retEmpty, false);fail("no exception");} catch (IllegalArgumentException expected) {}
	}


	@Test
	public void test_ConstructorOK(){
		MessageMgrBuilder mmb;

		mmb = new MessageMgrBuilder("@test");
		assertEquals("@test", mmb.appID);
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()==0);

		mmb = new MessageMgrBuilder("@test", false);
		assertEquals("@test", mmb.appID);
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()==0);

		mmb = new MessageMgrBuilder("@test", true);
		assertEquals("@test", mmb.appID);
		assertTrue(mmb.stg!=null);
		assertTrue(mmb.buildErrors.size()==0);
	}


	@Test
	public void test_LoadStg(){
		MessageMgrBuilder mmb;

		//OK
		mmb = new MessageMgrBuilder("@test", false);
		mmb.loadStg("de/vandermeer/skb/base/report-manager.stg");
		assertTrue(mmb.stg!=null);
		assertTrue(mmb.buildErrors.size()==0);

		//ERROR - null
		mmb = new MessageMgrBuilder("@test", false);
		mmb.loadStg(null);
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()>0);

		//ERROR - blank
		mmb = new MessageMgrBuilder("@test", false);
		mmb.loadStg("");
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()>0);

		//ERROR - no file found
		mmb = new MessageMgrBuilder("@test", false);
		mmb.loadStg("xyz-manager.stg");
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()>0);

		//ERROR - wrong chunks
		mmb = new MessageMgrBuilder("@test", false);
		mmb.loadStg("de/vandermeer/skb/base/utils/test-simple.stg");
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()>0);
	}


	@Test
	public void test_SetStg(){
		MessageMgrBuilder mmb;

		//OK
		mmb = new MessageMgrBuilder("@test", false);
		mmb.setStg(new STGroupFile("de/vandermeer/skb/base/report-manager.stg"));
		assertTrue(mmb.stg!=null);
		assertTrue(mmb.buildErrors.size()==0);

		//ERROR - null
		mmb = new MessageMgrBuilder("@test", false);
		mmb.setStg(null);
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()>0);

		//ERROR - wrong chunks
		mmb = new MessageMgrBuilder("@test", false);
		mmb.setStg(new STGroupFile("de/vandermeer/skb/base/utils/test-simple.stg"));
		assertTrue(mmb.stg==null);
		assertTrue(mmb.buildErrors.size()>0);
	}


	@Test
	public void test_SetHandlerError(){
		MessageMgrBuilder mmb;

		mmb = new MessageMgrBuilder("@test", false);
		mmb.setHandler(null);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);

		mmb = new MessageMgrBuilder("@test", false);
		mmb.setHandler(null, 0);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);

		mmb = new MessageMgrBuilder("@test", false);
		mmb.setHandler(null, null);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);

		mmb = new MessageMgrBuilder("@test", false);
		mmb.setHandler(null, 0, null);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);
	}


	@Test
	public void test_SetHandlerOK(){
		MessageMgrBuilder mmb;

		//all defaults for info, 1 handler
		mmb = new MessageMgrBuilder("@test", false);
		mmb.setHandler(EMessageType.INFO);
		assertEquals(1, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(EMessageType.INFO));
		assertTrue(mmb.messageHandlers.get(EMessageType.INFO).useSkbConsole);
		assertEquals(-1, mmb.messageHandlers.get(EMessageType.INFO).maxCount);
		assertEquals(0, mmb.messageHandlers.get(EMessageType.INFO).count);
		assertEquals(EMessageType.INFO, mmb.messageHandlers.get(EMessageType.INFO).type);
		assertTrue(mmb.messageHandlers.get(EMessageType.INFO).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//maxCount with SkbConsole for error, 2 handlers
		mmb.setHandler(EMessageType.ERROR, 49);
		assertEquals(2, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(EMessageType.ERROR));
		assertTrue(mmb.messageHandlers.get(EMessageType.ERROR).useSkbConsole);
		assertEquals(49, mmb.messageHandlers.get(EMessageType.ERROR).maxCount);
		assertEquals(0, mmb.messageHandlers.get(EMessageType.ERROR).count);
		assertEquals(EMessageType.ERROR, mmb.messageHandlers.get(EMessageType.ERROR).type);
		assertTrue(mmb.messageHandlers.get(EMessageType.ERROR).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//null logger means all defaults for Warn, 3 handlers
		mmb.setHandler(EMessageType.WARNING, null);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(EMessageType.WARNING));
		assertTrue(mmb.messageHandlers.get(EMessageType.WARNING).useSkbConsole);
		assertEquals(-1, mmb.messageHandlers.get(EMessageType.WARNING).maxCount);
		assertEquals(0, mmb.messageHandlers.get(EMessageType.WARNING).count);
		assertEquals(EMessageType.WARNING, mmb.messageHandlers.get(EMessageType.WARNING).type);
		assertTrue(mmb.messageHandlers.get(EMessageType.WARNING).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//logger w/o max means max is -1 for INFO, 3 handlers and all INFO settings are for the new one
		Logger logger = LoggerFactory.getLogger("test");
		mmb.setHandler(EMessageType.INFO, logger);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(EMessageType.INFO));
		assertEquals(false, mmb.messageHandlers.get(EMessageType.INFO).useSkbConsole);
		assertEquals(-1, mmb.messageHandlers.get(EMessageType.INFO).maxCount);
		assertEquals(0, mmb.messageHandlers.get(EMessageType.INFO).count);
		assertEquals(EMessageType.INFO, mmb.messageHandlers.get(EMessageType.INFO).type);
		assertEquals(logger, mmb.messageHandlers.get(EMessageType.INFO).logger);
		assertTrue(mmb.buildErrors.size()==0);

		//logger null w/ max count means max and SkbConsole for WARN, 3 handlers and all WARN settings are for the new one
		mmb.setHandler(EMessageType.WARNING, 20, null);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(EMessageType.WARNING));
		assertTrue(mmb.messageHandlers.get(EMessageType.WARNING).useSkbConsole);
		assertEquals(20, mmb.messageHandlers.get(EMessageType.WARNING).maxCount);
		assertEquals(0, mmb.messageHandlers.get(EMessageType.WARNING).count);
		assertEquals(EMessageType.WARNING, mmb.messageHandlers.get(EMessageType.WARNING).type);
		assertTrue(mmb.messageHandlers.get(EMessageType.WARNING).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//logger with max count means all set for ERROR, 3 handlers and all ERROR settings for the new onemmb.setHandler(EMessageType.WARNING, 20, null);
		mmb.setHandler(EMessageType.ERROR, 71, logger);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(EMessageType.ERROR));
		assertEquals(false, mmb.messageHandlers.get(EMessageType.INFO).useSkbConsole);
		assertEquals(71, mmb.messageHandlers.get(EMessageType.ERROR).maxCount);
		assertEquals(0, mmb.messageHandlers.get(EMessageType.ERROR).count);
		assertEquals(EMessageType.ERROR, mmb.messageHandlers.get(EMessageType.ERROR).type);
		assertEquals(logger, mmb.messageHandlers.get(EMessageType.ERROR).logger);
		assertTrue(mmb.buildErrors.size()==0);
	}


	@Test
	public void test_BuildError(){
		MessageMgrBuilder mmb;
		MessageMgr mm;

		//no stg set
		mmb = new MessageMgrBuilder("@test", false);
		mm = mmb.build();
		assertTrue(mmb.buildErrors.size()>0);
		assertEquals(null, mm);

		//no handlers set
		mmb = new MessageMgrBuilder("@test", false);
		mmb.setStg(new STGroupFile("de/vandermeer/skb/base/report-manager.stg"));
		mm = mmb.build();
		assertTrue(mmb.buildErrors.size()>0);
		assertEquals(null, mm);
	}


	@Test
	public void test_BuildOK(){
		MessageMgrBuilder mmb;
		MessageMgr mm;

		//all good, 1 handler for INFO
		mmb = new MessageMgrBuilder("@test", false);
		mmb.setStg(new STGroupFile("de/vandermeer/skb/base/report-manager.stg"));
		mmb.setHandler(EMessageType.INFO);
		mm = mmb.build();
		assertEquals(1, mm.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()==0);
		assertTrue(mm!=null);
	}

}
