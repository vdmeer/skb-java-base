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

package de.vandermeer.skb.base.categories.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import de.vandermeer.asciitable.commons.ObjectToStringStyle;

/**
 * A list of options.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.8 build 160201 (01-Feb-16) for Java 1.8
 * @since      v0.0.6 (was in skb-categories before)
 */
public class OptionList {

	/** List of options */
	List<Option<?>> options;

	/** Creates a new OptionList with an empty list of options */
	public OptionList(){
		this.options = new ArrayList<>();
	}

	/**
	 * Creates a new OptionList with given input option (if not null)
	 * @param option initial option
	 */
	public OptionList(Option<?> option){
		this.options = new ArrayList<>();
		if(option!=null){
			options.add(option);
		}
	}

	/**
	 * Creates a new OptionList initialized with all non-null options from the collection
	 * @param options collection of options
	 */
	public OptionList(Collection<Option<?>> options){
		this.options = new ArrayList<>();
		if(options!=null){
			this.options.addAll(options);
		}
	}

	/**
	 * Creates a new OptionList initialised with all non-null options from the array
	 * @param options array of options
	 */
	public OptionList(Option<?>[] options){
		this.options = new ArrayList<>();
		if(options!=null){
			for(Option<?> opt:options){
				this.options.add(opt);
			}
		}
	}

	/**
	 * Adds an option to the list
	 * @param option option to be added
	 * @return self
	 */
	public OptionList addOption(Option<?> option){
		if(option!=null){
			this.options.add(option);
		}
		return this;
	}

	/**
	 * Adds options from an array to the list (all non-null options)
	 * @param options array of options to be added
	 * @return self
	 */
	public OptionList addOption(Option<?>[] options){
		if(options!=null){
			for(Option<?> opt:options){
				if(opt!=null){
					this.options.add(opt);
				}
			}
		}
		return this;
	}

	/**
	 * Adds options from a collection to the list (all non-null options)
	 * @param options collection of options to be added
	 * @return self
	 */
	public OptionList addOption(Collection<Option<?>> options){
		if(options!=null){
			for(Option<?> opt:options){
				if(opt!=null){
					this.options.add(opt);
				}
			}
		}
		return this;
	}

	/**
	 * Returns true if the list contains the option
	 * @param option option to test for
	 * @return true if the list contains the option, false otherwise
	 */
	public boolean hasOption(Option<?> option){
		if(option!=null){
			for(Option<?> opt : this.options){
				if(this.options.contains(option)){
					return true;
				}
				else if(opt.getKey()._value().equals(option.getKey()._value())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if the list contains an option whos getOption returns the given string
	 * @param option string to test
	 * @return true if the list contains an option that return the given string, false otherwise
	 */
	public boolean hasOption(String option){
		for(Option<?> opt : this.options){
			if(opt.getKey()._value().equals(option)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the option that uses the given string as option, null otherwise
	 * @param option option to test for
	 * @return the option that uses the given string as option, null otherwise
	 */
	public Option<?> getOption(String option){
		for(Option<?> opt : this.options){
			if(opt.getKey()._value().equals(option)){
				return opt;
			}
		}
		return null;
	}

	/**
	 * Returns the character value of the requested option or the default value
	 * @param option option to search for
	 * @param def default value to return
	 * @return character value of the option if found, default value otherwise
	 */
	public Character getOptValue(String option, Character def){
		Character ret = def;
		Option<?> opt = this.getOption(option);
		if(opt!=null && opt.getValueType()==OptionType.CHARACHTER){
			ret = (Character)opt.getValue()._value();
		}
		return ret;
	}

	/**
	 * Returns the character array value of the requested option or the default value
	 * @param option option to search for
	 * @param def default value to return
	 * @return character array value of the option if found, default value otherwise
	 */
	public char[] getOptValue(String option, char[] def){
		char[] ret = def;
		Option<?> opt = this.getOption(option);
		if(opt!=null && opt.getValueType()==OptionType.CHARACTER_ARRAY){
			ret = (char[])opt.getValue()._value();
		}
		return ret;
	}

	/**
	 * Returns the string value of the requested option or the default value
	 * @param option option to search for
	 * @param def default value to return
	 * @return string value of the option if found, default value otherwise
	 */
	public String getOptValue(String option, String def){
		String ret = def;
		Option<?> opt = this.getOption(option);
		if(opt!=null && opt.getValueType()==OptionType.STRING){
			ret = (String)opt.getValue()._value();
		}
		return ret;
	}

	/**
	 * Returns the boolean value of the requested option or the default value
	 * @param option option to search for
	 * @param def default value to return
	 * @return boolean value of the option if found, default value otherwise
	 */
	public Boolean getOptValue(String option, Boolean def){
		Boolean ret = def;
		Option<?> opt = this.getOption(option);
		if(opt!=null && opt.getValueType()==OptionType.BOOLEAN){
			ret = (Boolean)opt.getValue()._value();
		}
		return ret;
	}

	/**
	 * Returns the integer value of the requested option or the default value
	 * @param option option to search for
	 * @param def default value to return
	 * @return integer value of the option if found, default value otherwise
	 */
	public Integer getOptValue(String option, Integer def){
		Integer ret = def;
		Option<?> opt = this.getOption(option);
		if(opt!=null && opt.getValueType()==OptionType.INTEGER){
			ret = (Integer)opt.getValue()._value();
		}
		return ret;
	}

	/**
	 * Returns the double value of the requested option or the default value
	 * @param option option to search for
	 * @param def default value to return
	 * @return double value of the option if found, default value otherwise
	 */
	public Double getOptValue(String option, Double def){
		Double ret = def;
		Option<?> opt = this.getOption(option);
		if(opt!=null && opt.getValueType()==OptionType.DOUBLE){
			ret = (Double)opt.getValue()._value();
		}
		return ret;
	}

	@Override
	public String toString(){
		ToStringBuilder ret=new ToStringBuilder(this, ObjectToStringStyle.getStyle())
		.append("options   ", this.options, false)
		.append("options   ", this.options);
		return ret.toString();
	}

}
