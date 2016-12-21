package com.sohu.mrd.classification.hive;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.classification.bean.NewsTag;
import com.sohu.mrd.classification.utils.FileKit;
import com.sohu.mrd.classification.utils.commonMethod;
/**
 * @author Jin Guopan
   @creation 2016年12月1日
 */
public class HiveService {
	public static final Logger LOG=Logger.getLogger(HiveService.class);
	public static final String SEXY_PATH="data/sexy_docId_sexyLever";
	public static final String  sexy_content="data/sexy_content";
	public  void   getnewsTag()
	{
		List<NewsTag> newsTags=new ArrayList<NewsTag>();
		String sql="select * from news.news_tag";
		Connection con=null;
		try {
			con=GetConnectionHive.getHiveConnection();
			Statement  sta=con.createStatement();
			ResultSet  rs=sta.executeQuery(sql);
			if(rs!=null)
			{
				int count = 0;
				while(rs.next())
				{
					NewsTag newsTag = new NewsTag();
					String docId=rs.getString(2);
					String quality_tag=rs.getString(7);
					JSONObject  jsonObject=JSON.parseObject(quality_tag);
					String sexyLever=jsonObject.getString("4");
					if(Integer.valueOf(sexyLever)>=2)
					{
						String content=commonMethod.getNewsByDocId(docId);
						FileKit.write2File(content, sexy_content);
						//LOG.info("content "+content);
						//writeDocIdLever(docId,sexyLever,SEXY_PATH);
					}
	 		    }
			}
		} catch (SQLException e) {
			LOG.error("从hive拉数据异常 "+e.getMessage());
		}
	}
	/**
	 * 向文件中写入docId 和 质量等级  level
	 * @param docId
	 * @param level
	 */
	public void  writeDocIdLever(String docId,String level,String path)
	{
		if(Integer.valueOf(level)>=2)
		{
			StringBuilder sb=new StringBuilder();
			sb.append(docId);
			sb.append("\t");
			sb.append(level);
			FileKit.write2File(sb.toString(), path);
		}
	}
	public static void main(String[] args) {
		HiveService hiveService=new HiveService();
		hiveService.getnewsTag();
	}
}
