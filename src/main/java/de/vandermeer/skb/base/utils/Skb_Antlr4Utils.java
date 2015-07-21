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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.base.Skb_Transformer;

/**
 * Methods to query ANTLR4 objects.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.8-SNAPSHOT build 150721 (21-Jul-15) for Java 1.8
 * @since      v0.0.1
 */
public abstract class Skb_Antlr4Utils {

	/**
	 * Returns a transformer that takes an object and returns an Integer with column information for ANTLR classes
	 * @return transformer that returns column information for ANTLR classes RecognitionException, Token, ParserRuleContext and TerminalNode or -1 as default
	 */
	public static final Skb_Transformer<Object, Integer> ANTLR_TO_COLUMN(){
		return new Skb_Transformer<Object, Integer>(){
			@Override public Integer transform(Object o){
				Integer ret = -1;
				if(o==null){
					ret = -1;
				}
				else if(o instanceof RecognitionException){
					try{
						ret = ((RecognitionException)o).getOffendingToken().getCharPositionInLine();
					}
					catch(Exception ignore){}
				}
				else if(o instanceof Token){
					ret = ((Token)o).getCharPositionInLine();
				}
				else if(o instanceof ParserRuleContext){
					try{
						ret = ((ParserRuleContext)o).getStart().getCharPositionInLine();
					}
					catch(Exception ignore){}
				}
				else if(o instanceof TerminalNode){
					try{
						ret = ((TerminalNode)o).getSymbol().getCharPositionInLine();
					}
					catch(Exception ignore){}
				}
				return ret;
			}
		};
	}

	/**
	 * Returns the column information of an ANTLR object (RecognitionException, Token, ParserRuleContext, TerminalNode)
	 * @param o input object
	 * @return column information, -1 as default
	 */
	public final static Integer antlr2column(Object o){
		return Skb_Antlr4Utils.ANTLR_TO_COLUMN().transform(o);
	}

	/**
	 * Returns a transformer that takes an object and returns an Integer with line information for ANTLR classes
	 * @return transformer that returns line information for ANTLR classes RecognitionException, Token, ParserRuleContext and TerminalNode or -1 as default
	 */
	public static final Skb_Transformer<Object, Integer> ANTLR_TO_LINE(){
		return new Skb_Transformer<Object, Integer>(){
			@Override public Integer transform(Object o){
				Integer ret = -1;
				if(o==null){
					ret = -1;
				}
				else if(o instanceof RecognitionException){
					try{
						ret = ((RecognitionException)o).getOffendingToken().getLine();
					}
					catch(Exception ignore){}
				}
				else if(o instanceof Token){
					ret = ((Token)o).getLine();
				}
				else if(o instanceof ParserRuleContext){
					try{
						ret = ((ParserRuleContext)o).getStart().getLine();
					}
					catch(Exception ignore){}
				}
				else if(o instanceof TerminalNode){
					try{
						ret = ((TerminalNode)o).getSymbol().getLine();
					}
					catch(Exception ignore){}
				}
				return ret;
			}
		};
	}

	/**
	 * Returns the line information of an ANTLR object (RecognitionException, Token, ParserRuleContext, TerminalNode)
	 * @param o input object
	 * @return line information, -1 as default
	 */
	public final static Integer antlr2line(Object o){
		return Skb_Antlr4Utils.ANTLR_TO_LINE().transform(o);
	}

	/**
	 * Returns a transformer that takes an ANTLR object and returns a textual representation of it
	 * @return transformer that returns a null or text for ANTLR classes (RecognitionException, Token, ParserRuleContext, Terminal Node) and for StringTemplates (ST)
	 */
	public static final Skb_Transformer<Object, String> ANTLR_TO_TEXT(){
		return new Skb_Transformer<Object, String>(){
			@Override public String transform(Object o){
				String ret = null;
				if(o instanceof Token){
					try{
						ret = ((Token)o).getText();
					}
					catch(Exception ignore){}
					ret = (ret==null)?"":ret;
				}
				else if(o instanceof RecognitionException){
					ret = ((RecognitionException)o).toString();
					ret = (ret==null)?"":ret;
				}
				else if(o instanceof ParserRuleContext){
					ret = ((ParserRuleContext)o).getText();
					ret = (ret==null)?"":ret;
				}
				else if(o instanceof ParseTree){
					try{
						ret = ((ParseTree)o).getText();
					}
					catch(Exception ignore){}
					ret = (ret==null)?"":ret;
				}
				else if(o instanceof TerminalNode){
					try{
						ret = ((TerminalNode)o).getText();
					}
					catch(Exception ignore){}
					ret = (ret==null)?"":ret;
				}
				else if(o instanceof ST){
					ret = ((ST)o).render();
				}
				return ret;
			}
		};
	}
}
