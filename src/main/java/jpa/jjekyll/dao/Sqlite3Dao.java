package jpa.jjekyll.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jpa.jjekyll.app.ServerHandler;
import jpa.jjekyll.model.PageModel;

public class Sqlite3Dao {
	
	private static Sqlite3Dao instance;
	
	private static final Logger LOG = Logger.getLogger( ServerHandler.class.getName() );
	
	private String jdbc = "default.db";
	
	private Connection conn;
	
	static {
		getInstance().getConnection();
	}

	public static Sqlite3Dao getInstance() {
		Sqlite3Dao newinstance = instance;
		if( newinstance == null ) {
			synchronized(Sqlite3Dao.class) {
				newinstance = instance;
				if( newinstance == null ) {
					LOG.info("Generating Sqlite3Dao instance");
					instance = newinstance = new Sqlite3Dao();
				}
			}
		}
		return newinstance;
	}

	public boolean insertPage(PageModel model) {
		return true;
	}
	
	public boolean updatePage(PageModel model) {
		return true;
	}
	
	public List<PageModel> listPages() {
		List<PageModel> list = new ArrayList<PageModel>();
		return list;
	}

	private Connection getConnection(){
		return getConnection(this.jdbc);
	}
	
	private Connection getConnection(String s) {
		try {
			if( conn == null || conn.isClosed() ) {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:" + s);
				LOG.info("Successfully create a connection.");
			}
		} catch( Exception e) {
			LOG.severe(e.getMessage());
		}
		return conn;
	}
	
	private Connection getFastConnection(){
		return getConnection(this.jdbc);
	}
	
	private Connection getFastConnection(String s) {		
		try {
			if( conn == null || conn.isClosed() ) {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(s);
				LOG.info("Successfully create a connection.");
				conn.prepareStatement("PRAGMA cache_size=10000").execute();
				conn.prepareStatement("PRAGMA temp_store = MEMORY").execute();
				conn.prepareStatement("PRAGMA synchronous = OFF").execute();
				conn.prepareStatement("PRAGMA journal_mode = MEMORY").execute();
			}
		} catch( Exception e) {
			LOG.severe(e.getMessage());
		}
		return conn;
	}
	
	private PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
	private void closeConnection(){
		closeConnection(null,null);
	}
	
	private void closeConnection(PreparedStatement stmt, ResultSet rs){
		try {
			if( stmt!=null ) {			
				stmt.close();			
			}
			if( rs!=null ) {
				rs.close();
			}
			if( conn!=null ) {
				conn.close();
			}
		} catch (SQLException e) {		
			LOG.severe(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param stmt
	 * @param rs
	 */
	private void closeConnection(Statement stmt, ResultSet rs){
		try {
			if( stmt!=null ) {			
				stmt.close();			
			}
			if( rs!=null ) {
				rs.close();
			}
			if( conn!=null ) {
				conn.close();
			}
		} catch (SQLException e) {		
			LOG.severe(e.getMessage());
		}
	}
	
	private Sqlite3Dao() {
		LOG.fine("Creating sqlite3 dao single instance.");
	}
	
}
