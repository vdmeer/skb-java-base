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

/**
 * Tests for Tests for {@link MessageMgr}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 160304 (04-Mar-16) for Java 1.8
 * @since      v0.0.6 (was in skb-commons before)
 */
public class Test_MessageMgr {

}


//@Test public void testReportDirect(){
//MessageMgr arm = new MessageMgr("de/vandermeer/skb/base/report-manager.stg", "myTest");
//
//Message5WH msg = new Message5WH_Builder().setReporter("testReportDirect").setWho("junit").addWhat("a test message (ignore)").build();
//
//msg.changeType(EMessageType.ERROR);
//assertFalse(arm.report(null));
//assertEquals(0, arm.getMessageCount(EMessageType.ERROR));
//assertTrue(arm.report(msg));
//assertEquals(1, arm.getMessageCount(EMessageType.ERROR));
//assertTrue(arm.report(msg));
//assertEquals(2, arm.getMessageCount(EMessageType.ERROR));
//
//msg.changeType(EMessageType.WARNING);
//assertFalse(arm.report(null));
//assertEquals(0, arm.getMessageCount(EMessageType.WARNING));
//assertTrue(arm.report(msg));
//assertEquals(1, arm.getMessageCount(EMessageType.WARNING));
//assertTrue(arm.report(msg));
//assertEquals(2, arm.getMessageCount(EMessageType.WARNING));
//
//msg.changeType(EMessageType.INFO);
//assertFalse(arm.report(null));
//assertEquals(0, arm.getMessageCount(EMessageType.INFO));
//assertTrue(arm.report(msg));
//assertEquals(1, arm.getMessageCount(EMessageType.INFO));
//assertTrue(arm.report(msg));
//assertEquals(2, arm.getMessageCount(EMessageType.INFO));
//
//arm.resetMessageCount(EMessageType.ERROR);
//assertEquals(0, arm.getMessageCount(EMessageType.ERROR));
//arm.resetMessageCount(EMessageType.WARNING);
//assertEquals(0, arm.getMessageCount(EMessageType.WARNING));
//arm.resetMessageCount(EMessageType.INFO);
//assertEquals(0, arm.getMessageCount(EMessageType.INFO));
//}

//@Test public void testReportPublic(){
//MessageMgr arm = new MessageMgr("de/vandermeer/skb/base/report-manager.stg", "myTest");
//
//Message5WH msg = new Message5WH_Builder().setReporter("testReportDirect").setWho("junit").addWhat("a test message (ignore)").build();
//
//CC_Error err = new CC_Error().add(msg).add(msg);
//assertTrue(arm.report(err));
//assertEquals(2, arm.getMessageCount(EMessageType.ERROR));
//
//CC_Warning warn = new CC_Warning().add(msg).add(msg);
//assertTrue(arm.report(warn));
//assertEquals(2, arm.getMessageCount(EMessageType.WARNING));
//
//msg.changeType(null);
//assertFalse(arm.report(msg));
//
//msg.changeType(EMessageType.INFO);
//Object[] oarr = new Object[]{msg, msg, msg};
//assertTrue(arm.report(oarr));
//assertEquals(3, arm.getMessageCount(EMessageType.INFO));
//
//HashSet<Object> set = new HashSet<Object>();
//msg.changeType(EMessageType.WARNING);
//set.add(msg);
//assertTrue(arm.report(set));
//assertEquals(3, arm.getMessageCount(EMessageType.WARNING));
//
//arm.resetMessageCount(EMessageType.ERROR);
//assertEquals(0, arm.getMessageCount(EMessageType.ERROR));
//arm.resetMessageCount(EMessageType.WARNING);
//assertEquals(0, arm.getMessageCount(EMessageType.WARNING));
//arm.resetMessageCount(EMessageType.INFO);
//assertEquals(0, arm.getMessageCount(EMessageType.INFO));
//}