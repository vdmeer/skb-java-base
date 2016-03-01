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

import org.antlr.v4.runtime.CommonToken;
import org.junit.Test;

/**
 * Tests for Tests for {@link Skb_AntlrUtils}.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.9-SNAPSHOT build 160301 (01-Mar-16) for Java 1.8
 * @since      v0.0.6 (was in skb-commons before)
 */
public class Test_Skb_AntlrUtils {

	@Test public void test_Antlr2Line(){
		CommonToken tk = new CommonToken(0);
		assertEquals(new Integer(-1), Skb_Antlr4Utils.ANTLR_TO_LINE().transform((CommonToken)null));
		assertEquals(new Integer(0),  Skb_Antlr4Utils.ANTLR_TO_LINE().transform(tk));

		tk.setLine(0);
		assertEquals(new Integer(0),  Skb_Antlr4Utils.ANTLR_TO_LINE().transform(tk));

		tk.setLine(20);
		assertEquals(new Integer(20), Skb_Antlr4Utils.ANTLR_TO_LINE().transform(tk));
	}

	@Test public void test_Antlr2Column(){
		CommonToken tk = new CommonToken(0);
		assertEquals(new Integer(-1),  Skb_Antlr4Utils.ANTLR_TO_COLUMN().transform((CommonToken)null));
		assertEquals(new Integer(-1), Skb_Antlr4Utils.ANTLR_TO_COLUMN().transform(tk));

		tk.setCharPositionInLine(0);
		assertEquals(new Integer(0), Skb_Antlr4Utils.ANTLR_TO_COLUMN().transform(tk));

		tk.setCharPositionInLine(20);
		assertEquals(new Integer(20), Skb_Antlr4Utils.ANTLR_TO_COLUMN().transform(tk));
	}

	@Test public void testAntlr2Line(){
		CommonToken tk = new CommonToken(0);
		assertEquals(new Integer(0), Skb_Antlr4Utils.antlr2line(tk));	//default line is 0

		tk.setLine(99);
		assertEquals(new Integer(99), Skb_Antlr4Utils.antlr2line(tk));

		tk.setLine(88);
		assertEquals(new Integer(88), Skb_Antlr4Utils.antlr2line(tk));
	
		tk.setLine(0);
		assertEquals(new Integer(0), Skb_Antlr4Utils.antlr2line(tk));
	}

	@Test public void testAntlr2Column(){
		CommonToken tk = new CommonToken(0);
		assertEquals(new Integer(-1), Skb_Antlr4Utils.antlr2column(tk));	//default column is not set, so -1

		tk.setCharPositionInLine(99);
		assertEquals(new Integer(99), Skb_Antlr4Utils.antlr2column(tk));

		tk.setCharPositionInLine(88);
		assertEquals(new Integer(88), Skb_Antlr4Utils.antlr2column(tk));
	
		tk.setCharPositionInLine(0);
		assertEquals(new Integer(0), Skb_Antlr4Utils.antlr2column(tk));
	}

}
