package jpa.jjekyll.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class HelperCommon {

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream getFileAsInputStream(String path) {
		try {
			return HelperCommon.class.getResourceAsStream(path);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileAsString(String path) {
		try {
			return new BufferedReader(new InputStreamReader(getFileAsInputStream(path)))
					  .lines().collect(Collectors.joining("\n"));
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s!=null && !"".equalsIgnoreCase(s) && s.length()<=0;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}
}
