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

package de.vandermeer.skb.base.shell;

import org.junit.Test;

import de.vandermeer.asciitable.CWC_FixedWidth;

/**
 * Tests for Tests for {@link SkbShell}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.10-SNAPSHOT build 170331 (31-Mar-17) for Java 1.8
 * @since      v0.0.8
 */
public class Test_EclRun_Shell {

	@Test
	public void testMe(){

		if("true".equals(System.getProperty("EclRun"))){
			SkbShell skbsh = SkbShellFactory.newShell();
			skbsh.addCommandInterpreter(new Ci_Exit());
			skbsh.addCommandInterpreter(new Ci_HelpTable(skbsh).setWidth(new CWC_FixedWidth().add(10).add(66)));
//			skbsh.addCommandInterpreter(new Ci_HelpStg(skbsh));
			skbsh.addCommandInterpreter(new Ci_Wait());
			skbsh.addCommandInterpreter(new Ci_ScRun(skbsh));
			skbsh.addCommandInterpreter(new Ci_History(skbsh));
			skbsh.runShell();
System.err.println("#");
//			skbsh.runShell();
System.err.println("##");
//			Object notify = new Object();
//			Thread th = skbsh.start(notify);
//			Skb_ThreadUtils.sleep(1);
//			synchronized(notify){
//				try {
//					while(skbsh.isRunning()){
//						notify.wait(2000);
//						System.err.println("@");
//						skbsh.stop();
//						break;
//					}
//				}
//				catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
System.err.println("###");
System.err.println(skbsh.isRunning());

		}
	}

}
