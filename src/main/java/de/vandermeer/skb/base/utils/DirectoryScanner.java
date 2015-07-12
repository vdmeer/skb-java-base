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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.text.StrBuilder;

/**
 * Scans a directory and returns a complete list of files found.
 * 
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.6 build 150712 (12-Jul-15) for Java 1.8
 * @since      v0.0.6
 */
public class DirectoryScanner {

	/** List of errors collected during a scan. */
	final List<String> errors;

	/** List of warnings collected during a scan. */
	final List<String> warnings;

	/** List of infos collected during a scan. */
	final List<String> infos;

	/** Number of directories scanned. */
	int scDir;

	/** Number of unreadable directories scanned. */
	int scDirUnreadable;

	/** Number of files found. */
	int scFiles;

	/** Number of unreadable files found. */
	int scFilesUnreadable;

	/**
	 * Returns a new directory scanner
	 */
	public DirectoryScanner(){
		this.errors = new ArrayList<>();
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
	 * @param directory name of the directory to scan
	 * @return a set of readable files found in the directory, empty set of none found, errors and warnings are collected per scan
	 */
	public Set<File> getFiles(String directory){
		this.clear();
		if(directory==null){
			this.errors.add("input directory was null");
			return null;
		}
		File fDir = null;
		try{
			fDir = new File(directory);
			if(!fDir.exists()){
				this.errors.add("input directory <" + directory + "> does not exist");
				return null;
			}
			if(fDir.isHidden()){
				this.errors.add("input directory <" + directory + "> is a hidden directory");
				return null;
			}
			if(!fDir.isDirectory()){
				this.errors.add("input directory <" + directory + "> is not a directory");
				return null;
			}
			if(!fDir.canRead()){
				this.errors.add("cannot read input directory <" + directory + ">");
				return null;
			}
		}
		catch(Exception ex){
			this.errors.add("problem with input directory <" + directory + "> with exception <" + ex.getMessage());
			return null;
		}

		Set<File> ret = this.doScan(fDir);
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
	Set<File> doScan(File fDir){
		Set<File> ret = new HashSet<>();
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
	 * Returns collected errors from a scan.
	 * @return a list with errors, empty list of none occurred
	 */
	public List<String> lastErrors(){
		return this.errors;
	}

	/**
	 * Returns collected infos from a scan.
	 * @return a list with infos, empty list of none occurred
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
			ret.appendWithSeparators(this.errors, "\n  - ");
		}
		ret.appendNewLine();

		return ret.toString();
	}
}
