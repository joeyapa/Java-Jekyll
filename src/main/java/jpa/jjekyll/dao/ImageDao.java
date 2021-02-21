package jpa.jjekyll.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jpa.jjekyll.common.ConstantCommon;
import jpa.jjekyll.model.PageModel;

public class ImageDao extends Sqlite3Dao {
	
	private static final Logger LOG = Logger.getLogger( ImageDao.class.getName() );
	
	private static ImageDao instance;
	
	static {
		getInstance().getConnection();
	}

	public static ImageDao getInstance() {
		ImageDao newinstance = instance;
		if( newinstance == null ) {
			synchronized(Sqlite3Dao.class) {
				newinstance = instance;
				if( newinstance == null ) {
					LOG.info("Generating ImageDao instance");
					instance = newinstance = new ImageDao();
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
	
	protected ImageDao() {
		super(ConstantCommon.ENV_DB_IMAGE_JDBC);	
	}
}
