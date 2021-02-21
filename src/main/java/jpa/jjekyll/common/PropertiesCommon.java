package jpa.jjekyll.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import jpa.jjekyll.helper.CommonHelper;

public class PropertiesCommon implements Serializable {
	private static final long serialVersionUID = 4775175870632273296L;

	private static final Logger LOG = Logger.getLogger( PropertiesCommon.class.getName() );
	
	private Properties prop;

	/**
	 * 
	 * @param path
	 */
	public PropertiesCommon(String path) {
		InputStream in = CommonHelper.getFileAsInputStream(path);
		if( in != null ) {
			try {
				prop.load(in);				
			} catch (IOException e) {
				LOG.warning("Property file " + path + " is not found.");
			}
		}
		else {
			LOG.warning("Property file " + path + " is not found.");
		}		
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return get(key, null);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String get(String key, String defaultValue) {
		String value = prop.getProperty(key);
		if( CommonHelper.isNotEmpty(value)  ) {
			return value;
		}
		return defaultValue;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean exist(String key) {
		return get(key)!=null;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String[] split(String key) {
		return split(key, ",");
	}
	
	/**
	 * 
	 * @param key
	 * @param delimeter
	 * @return
	 */
	public String[] split(String key, String regex) {
		String value = get(key);
		if( value!=null ) {
			return value.split(regex);
		}
		return new String[0];
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public List<String> list(String key) {
		return list(key, ",");
	}
	
	/**
	 * 
	 * @param key
	 * @param regex
	 * @return
	 */
	public List<String> list(String key, String regex) {
		return Arrays.asList(split(key, regex));
	}
}
