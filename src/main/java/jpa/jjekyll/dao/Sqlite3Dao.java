package jpa.jjekyll.dao;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;


public class Sqlite3Dao {
	
	private static final Logger LOG = Logger.getLogger( Sqlite3Dao.class.getName() );
	
	private String jdbc;
	
	private Connection conn;
	
	protected Connection getConnection(){
		return getConnection(this.jdbc);
	}
	
	protected Connection getConnection(String s) {
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

	protected InputStream getBlob(String table, String field, String id) throws SQLException {
		String sql = "SELECT " + field + " FROM " + table + " WHERE id = ?";
		ResultSet rs = null;
		InputStream in = null;
        PreparedStatement pstmt = null;
        pstmt = getFastConnection().prepareStatement(sql);
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        while( rs.next() ) {
        	in = rs.getBinaryStream(field);
        	break;
        }
        return in;
        
	}
	
	protected Connection getFastConnection() {
		return getConnection(this.jdbc);
	}
	
	protected Connection getFastConnection(String s) {		
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
	
	protected PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
	protected void closeConnection(){
		closeConnection(null,null);
	}
	
	/**
	 * 
	 * @param stmt
	 * @param rs
	 */
	protected void closeConnection(PreparedStatement stmt, ResultSet rs){
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
	protected void closeConnection(Statement stmt, ResultSet rs){
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
	
	protected Sqlite3Dao(String jdbc) {		
		LOG.fine("Creating sqlite3 dao.");
		this.jdbc = jdbc;
	}
	
}
