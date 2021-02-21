package jpa.jjekyll.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import jpa.jjekyll.helper.CommonHelper;

/**
 * 
 * @author joeyapa
 */
@SuppressWarnings("restriction")
public class ServerHandler implements HttpHandler {
	private static final Logger LOG = Logger.getLogger( ServerHandler.class.getName() );
	
	private static Map<String,String> uriContent;
	private static Map<String,Long> uriExpiry;
	
	private ServerConfig config;
	
	static {		
		uriContent = new HashMap<String,String>();
		uriExpiry = new HashMap<String,Long>();
	}
	
	/**
	 * 	Server handler init
	 */
	public ServerHandler(){		
		this.config = new ServerConfig();		
		this.initLogger();

	}

	/**
	 *	Http exchange handler
	 */
	@Override
	public void handle(HttpExchange t) throws IOException {
		int code = 200;
		byte[] response = null;
		
		
		String path = t.getRequestURI().getPath();
		path = path.length()>1 ? path.startsWith("/") ? path.substring(1) : path : "";
		
		if( path!=null && path.endsWith(".ico") ) {
			response = CommonHelper.getFileAsByte(path);
			t.sendResponseHeaders(code, response.length);
			OutputStream os = t.getResponseBody();
			os.write(response);
			os.close();
			
			return;
		}
		
		
		response = config.getPageHome().getBytes();
		boolean isPost = "POST".equalsIgnoreCase(t.getRequestMethod());
				
		
		
		String content = isPost ? "{\"state\":\"200\",\"method\":\"POST\"}"  : this.getStaticContent(path);
		
		String[] uri = path.split("/");
		Map<String,String> query = this.queryToMap(t.getRequestURI().getQuery());					
		
		if( isPost ) {
			String sb = getString(t.getRequestBody());
			if( sb!=null && !sb.isEmpty() ) {
				query.putAll(this.queryToMap(sb));
			}			
		}		

		LOG.fine("Requesting URI [" + path + "], Query " + query.keySet());		
		 
		if( content==null ) {
			code = 404;
			content = config.getPage404();
		}		
		else if( uri!=null && uri.length>0 ) {
			
		}
				
		response = content.getBytes();
		t.sendResponseHeaders(code, response.length);
		OutputStream os = t.getResponseBody();
		os.write(response);
		os.close();
	}
	
	/**
	 * Convert the query request to hash map 
	 * 
	 * @param query
	 * @return
	 */
	private Map<String,String> queryToMap(String query){
		Map<String,String> map = new HashMap<String,String>();
		if( query!=null && !query.isEmpty()) {
			String[] pairs = query.split("&");
			for(String pair : pairs) {
				if( pair!=null && !pair.isEmpty() ) {
					String[] keyvalue = pair.split("=");
					if( keyvalue.length == 2 ) {
						map.put(keyvalue[0], keyvalue[1]);
					}					
				}
				
			}
		}
		return map;
	}	
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private String getStaticContent(String path) {
		String content = "{\"state\":\"200\",\"method\":\"GET\"}";
		
		// 1. Check if it refers to the default domain path 
		if( path==null || path.trim().isEmpty() ) {
			return config.getPageHome();
		}
		
		// 2. Grant exception to path that starts with dynamic URI
		if( !config.getDynamicURI().isEmpty() ) {
			for(String uri : config.getDynamicURI()) {
				if( path.startsWith(uri) ) {
					return content;
				}
			}
		}
		
		// 3. Is the URI restricted to the list of process or static URI
		if( config.isStrictURI()
			&& !config.getProcessURI().contains(path)
			&& !config.getStaticURI().contains(path) ) {
				return null;
		}
		
		/* 4. Is the URI marked with expire, then wait until the refresh cycle 
		if( config.isMarkedURI() 
			&& markedURI.containsKey(path) 
			&& markedURI.get(path) > new Date().getTime()  ) {
				return null;
		} */
		
		// 5. Is the URI cached
		if( config.isCacheStaticURI() ) {
			/*content = staticURIContent.get(path);
			if( content == null ) {
				//content = CommonHelper.getFileAsString(path);				
			}
			if( content != null ) {
				//staticURIContent.put(path, content);
			}
			*/								
		}
		else {
			content = CommonHelper.getFileAsString(path);
		}
		
		// 6. Flag marked null content
		if( config.isMarkedURI() && content==null ) {
			//markedURI.put(path, new Date().getTime() + markedURIExpiry);
		}
					
		return content;	 
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 */
	private String getString(InputStream is) {
		return new BufferedReader(new InputStreamReader(is)).lines().parallel().collect(Collectors.joining("\n"));		
	}
	
	/**
	 * 
	 * 	@param html
	 * 	@return
	 */
	private String replaceConfiguration(String html) {
		// fixed configuration
		html = html.replace("{{editor.tpl.css}}", "");
		
		// query the database
		return html;
	}
	
	/**
	 * 	Initialize loggers
	 */
	private void initLogger(){
		Handler loggerhandler = null;
		
		// 1.
		try {
			switch( config.getLoggerType() ) {
				case "CONSOLE": 
					loggerhandler = new ConsoleHandler(); 
					break;
				case "FILE": 
					loggerhandler = new FileHandler( config.getLoggerFilePath() ); 
					break;
				default:
					loggerhandler = new ConsoleHandler();
					break;
			}	
		} catch(Exception e) {
			loggerhandler = new ConsoleHandler();
			e.printStackTrace();
		}		
		 
		// 2.
		switch( config.getLoggerLevel() ) {
			case "WARNING":
				loggerhandler.setLevel(Level.WARNING);
				LOG.setLevel(Level.WARNING); 
				break;
			case "INFO": 
				loggerhandler.setLevel(Level.INFO);
				LOG.setLevel(Level.INFO); 
				break;
			case "FINE": 
				loggerhandler.setLevel(Level.FINE);
				LOG.setLevel(Level.FINE); 
				break;
			case "FINEST": 
				loggerhandler.setLevel(Level.FINEST);
				LOG.setLevel(Level.FINEST); 
				break;			
			default: 
				loggerhandler.setLevel(Level.ALL);
				LOG.setLevel(Level.ALL); 
			break;
		}		
		LOG.addHandler(loggerhandler);
		LOG.setUseParentHandlers(false);
	}
}
