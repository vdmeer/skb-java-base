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

import de.vandermeer.skb.base.message.E_MessageType;

/**
 * Tests for Tests for {@link MessageMgrBuilder}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.5 build 160201 (01-Feb-16) for Java 1.8
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
		Map<String, Set<String>> stgChunks = MessageMgr.loadChunks();

		assertTrue(stgChunks.containsKey("max"));
		assertTrue(stgChunks.get("max").contains("name"));
		assertTrue(stgChunks.get("max").contains("number"));
		assertTrue(stgChunks.get("max").contains("type"));
	}


	@Test
	public void test_ConstructorException(){
		try {new MessageMgrBuilder(null);fail("no exception");} catch (IllegalArgumentException expected) {}
		try {new MessageMgrBuilder("");fail("no exception");} catch (IllegalArgumentException expected) {}
		try {new MessageMgrBuilder(retNull);fail("no exception");} catch (IllegalArgumentException expected) {}
		try {new MessageMgrBuilder(retEmpty);fail("no exception");} catch (IllegalArgumentException expected) {}
	}


	@Test
	public void test_ConstructorOK(){
		MessageMgrBuilder mmb;

		mmb = new MessageMgrBuilder("@test");
		assertEquals("@test", mmb.appID);
		assertTrue(mmb.buildErrors.size()==0);
	}


	@Test
	public void test_SetHandlerError(){
		MessageMgrBuilder mmb;

		mmb = new MessageMgrBuilder("@test");
		mmb.setHandler(null);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);

		mmb = new MessageMgrBuilder("@test");
		mmb.setHandler(null, 0);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);

		mmb = new MessageMgrBuilder("@test");
		mmb.setHandler(null, null);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);

		mmb = new MessageMgrBuilder("@test");
		mmb.setHandler(null, 0, null);
		assertEquals(0, mmb.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()>0);
	}


	@Test
	public void test_SetHandlerOK(){
		MessageMgrBuilder mmb;

		//all defaults for info, 1 handler
		mmb = new MessageMgrBuilder("@test");
		mmb.setHandler(E_MessageType.INFO);
		assertEquals(1, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(E_MessageType.INFO));
		assertTrue(mmb.messageHandlers.get(E_MessageType.INFO).useSkbConsole);
		assertEquals(-1, mmb.messageHandlers.get(E_MessageType.INFO).maxCount);
		assertEquals(0, mmb.messageHandlers.get(E_MessageType.INFO).count);
		assertEquals(E_MessageType.INFO, mmb.messageHandlers.get(E_MessageType.INFO).type);
		assertTrue(mmb.messageHandlers.get(E_MessageType.INFO).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//maxCount with SkbConsole for error, 2 handlers
		mmb.setHandler(E_MessageType.ERROR, 49);
		assertEquals(2, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(E_MessageType.ERROR));
		assertTrue(mmb.messageHandlers.get(E_MessageType.ERROR).useSkbConsole);
		assertEquals(49, mmb.messageHandlers.get(E_MessageType.ERROR).maxCount);
		assertEquals(0, mmb.messageHandlers.get(E_MessageType.ERROR).count);
		assertEquals(E_MessageType.ERROR, mmb.messageHandlers.get(E_MessageType.ERROR).type);
		assertTrue(mmb.messageHandlers.get(E_MessageType.ERROR).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//null logger means all defaults for Warn, 3 handlers
		mmb.setHandler(E_MessageType.WARNING, null);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(E_MessageType.WARNING));
		assertTrue(mmb.messageHandlers.get(E_MessageType.WARNING).useSkbConsole);
		assertEquals(-1, mmb.messageHandlers.get(E_MessageType.WARNING).maxCount);
		assertEquals(0, mmb.messageHandlers.get(E_MessageType.WARNING).count);
		assertEquals(E_MessageType.WARNING, mmb.messageHandlers.get(E_MessageType.WARNING).type);
		assertTrue(mmb.messageHandlers.get(E_MessageType.WARNING).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//logger w/o max means max is -1 for INFO, 3 handlers and all INFO settings are for the new one
		Logger logger = LoggerFactory.getLogger("test");
		mmb.setHandler(E_MessageType.INFO, logger);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(E_MessageType.INFO));
		assertEquals(false, mmb.messageHandlers.get(E_MessageType.INFO).useSkbConsole);
		assertEquals(-1, mmb.messageHandlers.get(E_MessageType.INFO).maxCount);
		assertEquals(0, mmb.messageHandlers.get(E_MessageType.INFO).count);
		assertEquals(E_MessageType.INFO, mmb.messageHandlers.get(E_MessageType.INFO).type);
		assertEquals(logger, mmb.messageHandlers.get(E_MessageType.INFO).logger);
		assertTrue(mmb.buildErrors.size()==0);

		//logger null w/ max count means max and SkbConsole for WARN, 3 handlers and all WARN settings are for the new one
		mmb.setHandler(E_MessageType.WARNING, 20, null);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(E_MessageType.WARNING));
		assertTrue(mmb.messageHandlers.get(E_MessageType.WARNING).useSkbConsole);
		assertEquals(20, mmb.messageHandlers.get(E_MessageType.WARNING).maxCount);
		assertEquals(0, mmb.messageHandlers.get(E_MessageType.WARNING).count);
		assertEquals(E_MessageType.WARNING, mmb.messageHandlers.get(E_MessageType.WARNING).type);
		assertTrue(mmb.messageHandlers.get(E_MessageType.WARNING).logger==null);
		assertTrue(mmb.buildErrors.size()==0);

		//logger with max count means all set for ERROR, 3 handlers and all ERROR settings for the new onemmb.setHandler(EMessageType.WARNING, 20, null);
		mmb.setHandler(E_MessageType.ERROR, 71, logger);
		assertEquals(3, mmb.messageHandlers.size());
		assertTrue(mmb.messageHandlers.containsKey(E_MessageType.ERROR));
		assertEquals(false, mmb.messageHandlers.get(E_MessageType.INFO).useSkbConsole);
		assertEquals(71, mmb.messageHandlers.get(E_MessageType.ERROR).maxCount);
		assertEquals(0, mmb.messageHandlers.get(E_MessageType.ERROR).count);
		assertEquals(E_MessageType.ERROR, mmb.messageHandlers.get(E_MessageType.ERROR).type);
		assertEquals(logger, mmb.messageHandlers.get(E_MessageType.ERROR).logger);
		assertTrue(mmb.buildErrors.size()==0);
	}


	@Test
	public void test_BuildError(){
		MessageMgrBuilder mmb;
		MessageMgr mm;

		//no stg set
		mmb = new MessageMgrBuilder("@test");
		mm = mmb.build();
		assertTrue(mmb.buildErrors.size()>0);
		assertEquals(null, mm);
	}


	@Test
	public void test_BuildOK(){
		MessageMgrBuilder mmb;
		MessageMgr mm;

		//all good, 1 handler for INFO
		mmb = new MessageMgrBuilder("@test");
		mmb.setHandler(E_MessageType.INFO);
		mm = mmb.build();
		assertEquals(1, mm.messageHandlers.size());
		assertTrue(mmb.buildErrors.size()==0);
		assertTrue(mm!=null);
	}

}
