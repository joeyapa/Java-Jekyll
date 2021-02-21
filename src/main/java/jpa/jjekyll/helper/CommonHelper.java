package jpa.jjekyll.helper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Common helper with include the following functions
 * 1. StringHelper
 * 2. FileHelper
 * @author joeyapa
 */
public class CommonHelper {

	/**
	 * Load the file in the external resource first before loading internal file
	 * @param path String of the resource
	 * @return InputStream of the file, null if not found
	 */
	public static InputStream getFileAsInputStream(String path) {
		// 1. Null checking before proceeding
		if( path==null ) {
			return null;
		}
		
		// 2. Load the file from external and internal locations
		try {
			// 2.1 External file checking
			File file = new File(path);
			if( file.exists() ) {
				return new FileInputStream(file);
			}
			
			// 2.2 Internal resource checking
			InputStream in = CommonHelper.class.getResourceAsStream(path);
			if( in==null && !path.startsWith("/")) {				
				in = CommonHelper.class.getResourceAsStream("/"+path);
			}			
			return in;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Load the file and convert it to a string
	 * @param path
	 * @return String of the file, null if not found
	 */
	public static String getFileAsString(String path) {
		return getFileAsString(path, null);
	}
	
	public static String getFileAsString(String path, String defaultValue) {
		try {
			InputStream in = getFileAsInputStream(path);			
			return in==null ? defaultValue : new BufferedReader(new InputStreamReader(in))
					.lines().collect(Collectors.joining("\n"));
		} catch(Exception e) {
			e.printStackTrace();
			return defaultValue;
		}		
	}
	
	/**
	 * 
	 * @param path
	 * @param value
	 */
	public static void writeFile(String path, String value) {
	      try {
			FileWriter writer = new FileWriter(path);
			writer.write(value);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the file and convert if to byte[]
	 * @param path
	 * @return byte[] of the file, zero byte if not found
	 */
	public static byte[] getFileAsByte(String path) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {			
		    byte[] buffer = new byte[0xFFFF];
		    InputStream is = getFileAsInputStream(path);	    
		    for (int len = is.read(buffer); len != -1; len = is.read(buffer)) { 
		        os.write(buffer, 0, len);
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
	    return os.toByteArray();	    
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
