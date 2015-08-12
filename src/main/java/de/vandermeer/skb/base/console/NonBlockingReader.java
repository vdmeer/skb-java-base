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

package de.vandermeer.skb.base.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A non-blocking buffered reader.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.13-SNAPSHOT build 150812 (12-Aug-15) for Java 1.8
 * @since      v0.0.5
 */
public abstract class NonBlockingReader {

	/**
	 * Returns a new callable for reading strings from a reader with a set timeout of 200ms.
	 * @param reader input stream to read from
	 * @param emptyPrint a printout to realize on an empty readline string, for prompts, set null if not required
	 * @return null if input stream is null, results of read on input stream otherwise
	 */
	public static Callable<String> getCallWTimeout(BufferedReader reader, HasPrompt emptyPrint){
		return NonBlockingReader.getCallWTimeout(reader, 200, emptyPrint);
	}

	/**
	 * Returns a new callable for reading strings from a reader with a given timeout.
	 * @param reader input stream to read from
	 * @param timeout read timeout in milliseconds, very low numbers and 0 are accepted but might result in strange behavior
	 * @param emptyPrint a printout to realize on an empty readline string, for prompts, set null if not required
	 * @return null if input stream is null, results of read on input stream otherwise
	 */
	public static Callable<String> getCallWTimeout(BufferedReader reader, int timeout, HasPrompt emptyPrint){
		return new Callable<String>() {
			@Override
			public String call() throws IOException {
				String ret = "";
				while("".equals(ret)){
					try{
						while(!reader.ready()){
							Thread.sleep(timeout);
						}
						ret = reader.readLine();
						if("".equals(ret) && emptyPrint!=null){
							System.out.print(emptyPrint.prompt());
						}
					}
					catch (InterruptedException e) {
						return null;
					}
				}
				return ret;
			}
		};
	}

	/**
	 * Returns a new BufferedReader that uses tries and timeout for readline() for a UTF-8 StdIn.
	 * @param logID a string for logging in case StdIn cannot be opened with UTF-8 encoding
	 * @param tries number of tries for read calls, use one as default
	 * @param timeout milliseconds for read timeout
	 * @param emptyPrint a printout to realize on an empty readline string, for prompts, set null if not required
	 * @return new reader with parameterized readline() method
	 */
	public static BufferedReader getNbReader(String logID, int tries, int timeout, HasPrompt emptyPrint){
		return NonBlockingReader.getNbReader(Skb_Console.getStdIn(logID), tries, timeout, emptyPrint);
	}

	/**
	 * Returns a new BufferedReader that uses tries and timeout for readline().
	 * @param reader original reader to extend, use in combination with {@link Skb_Console#getStdIn(String)} for standard in
	 * @param tries number of tries for read calls, use one as default
	 * @param timeout milliseconds for read timeout
	 * @param emptyPrint a printout to realize on an empty readline string, for prompts, set null if not required
	 * @return new reader with parameterized readline() method
	 */
	public static BufferedReader getNbReader(BufferedReader reader, int tries, int timeout, HasPrompt emptyPrint){
		if(reader==null){
			return null;
		}
		return new BufferedReader(reader){
			ExecutorService ex = Executors.newSingleThreadExecutor();
			@Override
			public String readLine(){
				String input = null;
				try {
					for(int i=0; i<tries; i++) {
						Future<String> result = ex.submit(NonBlockingReader.getCallWTimeout(reader, emptyPrint));
						try {
							input = result.get(timeout, TimeUnit.MILLISECONDS);
							break;
						}
						catch(ExecutionException ignore) {}
						catch(TimeoutException e) {
							result.cancel(true);
						}
						catch(InterruptedException ignore) {
							ex.shutdownNow();
						}
					}
				}
				finally {
					//ex.shutdownNow();
				}
				return input;
			}
		};
	}

}
