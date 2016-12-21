package com.sohu.mrd.classification.utils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * @author Jin Guopan
   @creation 2016-11-21
 */
public class commonMethod {
	public static final Logger LOG = Logger.getLogger(commonMethod.class);
	/**
	 * 通过docId得到新闻
	 * @param docId
	 * @return
	 */
	public static String  getNewsByDocId(String docId)
	{
		String news="";
		String content="";
		String NEWSINTER="http://10.10.93.179:8080/news/newsprofile?act=getdata&docid=";
		String url=NEWSINTER+docId;
		news=HttpClientUtil.executeGet(url);
		try {
			if(null!=news&&!news.trim().equals(""))
			{
				JSONObject jsonObject=JSON.parseObject(news);
				JSONArray jsonArray=jsonObject.getJSONArray("data");
				String data=jsonArray.getString(0);
				JSONObject  dataJson=JSON.parseObject(data);
				content=dataJson.getString("co");
				content=KillTag.killTags(content);
			}
		} catch (Exception e) {
			LOG.error("解析json异常 "+e.getMessage());
			LOG.error("出现异常的 docId 为 "+docId);
			
		}
	
		
		return content;
	}
    public static void main(String[] args) {
		String docId = "001f56840c0d70b1-9a1dd0572a42a9b0";
		String news=getNewsByDocId(docId);
		JSONObject jsonObject=JSON.parseObject(news);
		JSONArray jsonArray=jsonObject.getJSONArray("data");
		String data=jsonArray.getString(0);
		JSONObject  dataJson=JSON.parseObject(data);
		String   content=dataJson.getString("co");
		System.out.println("data "+data);
		System.out.println("news "+news);
		System.out.println("content "+content);
	}
}
