package com.sohu.mrd.classification.hive;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * @author Jin Guopan
   @creation 2016年11月30日
      取得hive连接
      注：通过java远程访问hive时需要在Linux下启动服务；执行命令：
  hive --service hiveserver &
(执行完该命令后服务会启动，光标会一直处于闪烁状态，可以回车返回linux命令行)
 */
public class GetConnectionHive {
	public static Logger LOG=Logger.getLogger(GetConnectionHive.class);
	private static String user="";
	private static String password="";
	private static String url="";
	private static String driverName="";
	static{
		Properties hiveproperties=new Properties();
		InputStream  is=GetConnectionHive.class.getClassLoader().getResourceAsStream("hive.properties");
		try {
			hiveproperties.load(is);
			url=hiveproperties.getProperty("url");
			user=hiveproperties.getProperty("user");
			password=hiveproperties.getProperty("password");
			driverName=hiveproperties.getProperty("driverName");
		} catch (IOException e) {
			LOG.error("取得hive properties 出错 "+e.getMessage());
		}
	}
	public static Connection getHiveConnection()
	{
		Connection  con=null;
		try {
			Class.forName(driverName);
			LOG.info("加载hive驱动成功");
			con=DriverManager.getConnection(url, user, password);
			LOG.info("连接hive成功");
		} catch (Exception e) {
			LOG.error("连接hive失败"+e.getMessage());
		} 
		return con;
	}
	public static void main(String[] args) {
		GetConnectionHive.getHiveConnection();
	}
}
