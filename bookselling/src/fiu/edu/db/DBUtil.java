package fiu.edu.db;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fiu.edu.conf.Resource;

public class DBUtil {

	public static Log log = LogFactory.getLog(DBUtil.class);

	/** store the information of db */
	public static Properties dbProp = new Properties();


	/** initialize the information when the class is loaded */
	static {
		InputStream dbin = null;
		try {
			dbin = new BufferedInputStream(new FileInputStream(Resource.getDBConf()));
			dbProp.load(dbin);

			log.info(getDBDriver());

		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				dbin.close();
			} catch (Throwable ignore) {
			}
		}
	}

	/**
	 * get the url of db
	 * 
	 * @return
	 */
	public static String getDBUrl() {
		return dbProp.getProperty("url");
	}

	/**
	 * get the user of db
	 * 
	 * @return
	 */
	public static String getDBUser() {
		return dbProp.getProperty("user");
	}

	/**
	 * get the password of db
	 * 
	 * @return
	 */
	public static String getDBPasswd() {
		return dbProp.getProperty("password");
	}

	/**
	 * get the driver of db
	 * 
	 * @return
	 */
	public static String getDBDriver() {
		return dbProp.getProperty("driver");
	}



	/**
	 * get a db connection
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Connection conn = null;
		String url = getDBUrl();
		String driver = getDBDriver();
		String user = getDBUser();
		String pwd = getDBPasswd();

		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, pwd);

		return conn;
	}
	

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conn = DBUtil.getConnection();
		if(conn != null)
			System.out.println("success");
		conn.close();
	}
	
	
}
