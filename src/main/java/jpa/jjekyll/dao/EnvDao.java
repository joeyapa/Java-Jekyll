package jpa.jjekyll.dao;

import java.util.logging.Logger;

import jpa.jjekyll.app.ServerHandler;
import jpa.jjekyll.common.ConstantCommon;
import jpa.jjekyll.common.PropertiesCommon;

public class EnvDao extends PropertiesCommon {
	
	private static final Logger LOG = Logger.getLogger( ServerHandler.class.getName() );
	
	private static final long serialVersionUID = -2916760853135620505L;

	private static EnvDao instance;

	/**
	 * 	Initialize environment properties
	 */
	private EnvDao() {
		super(ConstantCommon.ENV_PROPERTIES_FILEPATH);
	}
	
	/**
	 * 
	 * @return
	 */
	public static EnvDao getInstance() {
		EnvDao newinstance = instance;
		if( newinstance == null ) {
			synchronized(EnvDao.class) {
				newinstance = instance;
				if( newinstance == null ) {
					LOG.info("Generating EnvDao instance");
					instance = newinstance = new EnvDao();
				}
			}
		}
		return newinstance;
	}
	
}
