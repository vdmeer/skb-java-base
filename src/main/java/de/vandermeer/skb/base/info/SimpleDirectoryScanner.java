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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;

/**
 * Scans a directory and returns a complete list of files found.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.9-SNAPSHOT build 150729 (29-Jul-15) for Java 1.8
 * @since      v0.0.6
 */
public class SimpleDirectoryScanner extends AbstractLoader implements DirectoryLoader {

	/** The source for the scanner, a root directory. */
	final DirectorySource source;

	/** Number of directories scanned. */
	int scDir;

	/** Number of unreadable directories scanned. */
	int scDirUnreadable;

	/** Number of files found. */
	int scFiles;

	/** Number of unreadable files found. */
	int scFilesUnreadable;

	/** List of warnings collected during a scan. */
	final List<String> warnings;

	/** List of information collected during a scan. */
	final List<String> infos;

	/**
	 * Returns a new directory scanner for a given source.
	 * @param source the source for the scanner
	 */
	public SimpleDirectoryScanner(DirectorySource source){
		this.source = source;
		this.validateSource();

		this.warnings = new ArrayList<>();
		this.infos = new ArrayList<>();
	}

	/**
	 * Clears errors and warnings, automatically called for new scans.
	 */
	public void clear(){
		this.errors.clear();

		this.warnings.clear();
		this.infos.clear();

		this.scDir = 0;
		this.scDirUnreadable = 0;
		this.scFiles = 0;
		this.scFilesUnreadable = 0;
	}

	/**
	 * Returns a set of files found in the given directory.
	 * A scan will go recursively through all readable sub directories and collect all readable files.
	 * Hidden files and directories will be ignored (not scanned, found or counted)
	 * @return a set of readable files found in the directory, empty set of none found, errors and warnings are collected per scan
	 */
	protected List<File> getFiles(){
		this.clear();
		File f = this.source.asFile();
		List<File> ret = this.doScan(f);
		this.doInfo();
		return ret;
	}

	/**
	 * Generate a few strings with collected information about the scan.
	 */
	void doInfo(){
		this.infos.add("scanned directories:    " + this.scDir);
		this.infos.add("unreadable directories: " + this.scDirUnreadable);

		this.infos.add("found files:      " + this.scFiles);
		this.infos.add("unreadable files: " + this.scFilesUnreadable);
	}

	/**
	 * Does the actual scan of a directory.
	 * @param fDir starting directory
	 * @return set of found and readable files, empty set of none found
	 */
	protected List<File> doScan(File fDir){
		List<File> ret = new ArrayList<>();
		if(fDir!=null && fDir.exists() && !fDir.isHidden()){
			for(final File entry : fDir.listFiles()) {
				if(entry.isHidden()){
					continue;
				}
				if(!entry.isDirectory()) {
					this.scFiles++;
					if(entry.canRead()){
						ret.add(entry);
					}
					else{
						this.scFilesUnreadable++;
						this.warnings.add("found file <" + entry.getAbsolutePath() + "> but cannot read, ignore");
					}
				}
				else if(entry.isDirectory()){
					this.scDir++;
					if(entry.canRead()){
						ret.addAll(this.doScan(entry));
					}
					else{
						this.scDirUnreadable++;
						this.warnings.add("found directory <" + entry.getAbsolutePath() + "> but cannot read, ignore");
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Returns collected warnings from a scan.
	 * @return a list with warnings, empty list of none occurred
	 */
	public List<String> lastWarnings(){
		return this.warnings;
	}

	/**
	 * Returns collected information from a scan.
	 * @return a list with information, empty list of none occurred
	 */
	public List<String> lastInfos(){
		return this.infos;
	}

	@Override
	public String toString(){
		StrBuilder ret = new StrBuilder(100);

		ret.append("infos: ").append(this.infos.size());
		if(this.infos.size()>0){
			ret.appendNewLine().append("  - ");
			ret.appendWithSeparators(this.infos, "\n  - ");
		}
		ret.appendNewLine();

		ret.append("warnings: ").append(this.warnings.size());
		if(this.warnings.size()>0){
			ret.appendNewLine().append("  - ");
			ret.appendWithSeparators(this.warnings, "\n  - ");
		}
		ret.appendNewLine();

		ret.append("errors: ").append(this.infos.size());
		if(this.errors.size()>0){
			ret.appendNewLine().append("  - ");
			ret.append(this.errors.render());
			//TODO not nice format anymore
			//ret.appendWithSeparators(this.errors, "\n  - ");
		}
		ret.appendNewLine();

		return ret.toString();
	}

	@Override
	public DirectorySource getSource() {
		return this.source;
	}

	@Override
	public FileListSource load() {
		if(this.getSource().isValid()){
			return new FileListSource(this.getFiles());
		}
		return null;
	}
}
