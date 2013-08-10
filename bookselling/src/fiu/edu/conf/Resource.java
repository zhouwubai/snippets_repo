package fiu.edu.conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Resource {

	private static String pdpHome = null;

	private static String dbConf = null;

	static {
		pdpHome = System.getProperty("user.dir");

		if (pdpHome != null){
		dbConf = pdpHome + File.separator + "conf/db.properties";
			//servlet 启动产生不同的路径结果
//			dbConf = "/home/zhouwubai/U/workplace/PdpHome/conf/db.properties";
		}
		else{
			dbConf="db.properties";
		}
	}


	public static String getDBConf() {
		return dbConf;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// System.out.println(Resource.class.getResource("broker.properties"));
		BufferedReader bis = new BufferedReader(new FileReader(Resource.getDBConf()));
		String s=null;
		while((s=bis.readLine())!=null)
			System.out.println(s);
		
		// System.out.println(Resource.getBrokerConf().getPath());
		String fileName = pdpHome + "/" + "conf/broker.properties";
		System.out.println(fileName);
	}
	
}
