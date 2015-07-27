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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.IOFileFilter;

import de.vandermeer.skb.base.composite.coin.CC_Error;
import de.vandermeer.skb.base.info.sources.DirectorySource;
import de.vandermeer.skb.base.info.sources.FileListSource;

/**
 * Walks a directory and loads files.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150727 (27-Jul-15) for Java 1.8
 * @since      v0.0.7
 */
public class CommonsDirectoryWalker extends DirectoryWalker<File> implements DirectoryLoader {

	/** The source for the walker, a root directory. */
	final DirectorySource source;

	/** Local list of errors collected during process, cleared for every new validation call. */
	final CC_Error errors = new CC_Error();

	/**
	 * Returns a new loader that uses a directory walker with directory and file filters.
	 * @param source the directory source to load from
	 * @param dirFilter directory filters the walker should use
	 * @param fileFilter file filters the walker should use
	 */
	public CommonsDirectoryWalker(DirectorySource source, IOFileFilter dirFilter, IOFileFilter fileFilter){
		super(dirFilter, fileFilter, -1);
		this.source = source;
		this.validateSource();
	}

	/**
	 * Returns a new loader that uses a directory walker with directory and file filters.
	 * @param root the directory source to load from
	 * @param dirFilter directory filters the walker should use
	 * @param fileFilter file filters the walker should use
	 */
	public CommonsDirectoryWalker(String root, IOFileFilter dirFilter, IOFileFilter fileFilter){
		this(new DirectorySource(root), dirFilter, fileFilter);
	}

	@Override
	public FileListSource load(){
		if(this.errors.size()==0){
			this.errors.clear();
			List<File> ret = new ArrayList<>();
			try {
				File f = this.source.asFile();
				walk(f, ret);
			}
			catch (IOException e) {
				this.errors.add("IOException while walking dir <{}> - {}",
						new Object[]{this.source.getSource(), e.getMessage()});
			}
			return new FileListSource(ret);
		}
		return null;
	}

	@Override
	public DirectorySource getSource(){
		return this.source;
	}

	@Override
	public CC_Error getLoadErrors(){
		return this.errors;
	}

	@Override
	protected void handleFile(File file, int depth, Collection<File> results) throws IOException {
		results.add(file);
	}
}
