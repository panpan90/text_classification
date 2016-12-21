package com.sohu.mrd.classification.preProcess;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.classification.utils.FileKit;
import com.sohu.mrd.classification.utils.HttpClientUtil;
import com.sohu.mrd.classification.utils.commonMethod;
/**
 * @author Jin Guopan
   @creation 2016年11月30日
     产生语料
 */
public class GenerateNegativeCorpus {
	public static final Logger LOG = Logger.getLogger(GenerateNegativeCorpus.class);
	// 频道id
	public static final String CHANNELID_CAR = "10000"; // 汽车
	public static final String CHANNELID_CURRENT_POLITICS = "20000"; // 时政
	public static final String CHANNELID_EDUCATION = "30000"; // 教育
	public static final String CHANNELID_ENTERTAINMENT = "40000"; // 娱乐
	public static final String CHANNELID_FINANCE = "50000"; // 财政
	public static final String CHANNELID_GAME = "60000"; // 游戏
	public static final String CHANNELID_SCIENCE = "70000"; // 科学
	public static final String CHANNELID_MILITARY = "80000"; // 军事
	public static final String CHANNELID_SOCIETY = "90000"; // 社会
	public static final String CHANNELID_SPORTS = "100000"; // 足球
	public static final String CHANNELID_HOUSING = "110000"; // 房产
	public static final String CHANNELID_CULTURE = "120000"; // 文化
	public static final String CHANNELID_LIFE = "130000"; // 生活
	private static final String NEGATIVEURL = "http://service1.mrd.sohuno.com/chservice?type=chlistnews&page=1&pageSize=100&ch=";
	public static void main(String[] args) {
		writeData(CHANNELID_CAR);
		writeData(CHANNELID_CULTURE);
		writeData(CHANNELID_CURRENT_POLITICS);
		writeData(CHANNELID_EDUCATION);
		writeData(CHANNELID_FINANCE);
		writeData(CHANNELID_GAME);
		writeData(CHANNELID_LIFE);
		writeData(CHANNELID_HOUSING);
		writeData(CHANNELID_MILITARY);
		writeData(CHANNELID_SCIENCE);
		writeData(CHANNELID_SOCIETY);
		writeData(CHANNELID_SPORTS);
		writeData(CHANNELID_ENTERTAINMENT);
	}
	public static void   writeData(String category)
	{
		try {
			String jsonDocids=HttpClientUtil.executeGet(NEGATIVEURL+category);
			JSONObject  jsonObject=JSON.parseObject(jsonDocids);
	    	JSONArray  jsonArray=jsonObject.getJSONArray("data");
	    	String path="ordernaryYuliao/"+category;
	    	for(int i=0;i<jsonArray.size();i++)
	    	{
	    		String docId=jsonArray.getString(i);
	    		String news=commonMethod.getNewsByDocId(docId);
	    		news=news.replaceAll("(\r\n|\r|\n|\n\r)", " ");
	    		StringBuilder sb=new StringBuilder();
	    		sb.append(docId);
	    		sb.append("\t");
	    		sb.append(news);
	    		FileKit.write2File(sb.toString(), path);
	    	}
		} catch (Exception e) {
			LOG.error("提取语料 json 数据转换异常 "+e.getMessage());
		}
	}
}
