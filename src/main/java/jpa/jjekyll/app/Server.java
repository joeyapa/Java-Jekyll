package jpa.jjekyll.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpServer;

import jpa.jjekyll.helper.CommonHelper;

/**
 * Server main start-up to trigger the http server
 * @author joeyapa
 */
@SuppressWarnings("restriction")
public class Server {

	private static final Logger LOG = Logger.getLogger( Server.class.getName() );
	
	private static boolean isShuttingDown = false;
	
	private HttpServer httpServer;
	
	
	
	/**
	 * Server main arguments
	 * 
	 * 0: configuration file path or 0 for no configuration  
	 * 1: port number
	 * 2: context path
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 80;
		String contextpath = "/";
		Server server = new Server();		
		// 1. Arguments
		if( args!=null && args.length > 0 ) {			
			switch (args.length) {
				case 1: 
					port = Integer.valueOf(args[0]);
					break;
				case 2: 
					port = Integer.valueOf(args[0]);
					contextpath = args[1];
					break;
				default:
					port = Integer.valueOf(args[0]);
					contextpath = args[1];
					LOG.warning("Excess configuration parameters.");
					break;
			}			
		}	
		// 2. Start the server
		server.start(port, contextpath);		
	}
	
	/**
	 * Starting threaded request
	 */
	public void start(int port, String contextpath) {
		try {
			LOG.info("Server start on port " + port + " at [" + contextpath +"]");
			CommonHelper.writeFile("jjekyll.cmd", "jjekyll=starting");
			
			httpServer = HttpServer.create(new InetSocketAddress(port), 0);
			httpServer.createContext(contextpath, new ServerHandler());
			httpServer.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
			httpServer.start();
			
			CommonHelper.writeFile("jjekyll.cmd", "jjekyll=started");
			
			String cmd = CommonHelper.getFileAsString("jjekyll.cmd", "");
			while( !"jjekyll=shutdown".equals(cmd) ) {
				Thread.sleep(5000);
				cmd = CommonHelper.getFileAsString("jjekyll.cmd", "");
				switch( cmd ) {
					case "jjekyll=off":
						shutdown();
						break;
				}
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 */
	public void shutdown() {
		CommonHelper.writeFile("jjekyll.cmd", "jjekyll=shuttingdown");
		isShuttingDown = true;
		httpServer.stop(0);
		CommonHelper.writeFile("jjekyll.cmd", "jjekyll=shutdown");
	}
	
	/**
	 * 
	 */
	public void schema() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isShuttingDown() {
		return isShuttingDown;
	}
}
