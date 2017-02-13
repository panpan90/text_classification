package com.sohu.mrd.classification.web;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.classification.bean.JudgeInfo;
import com.sohu.mrd.classification.bean.News;
import com.sohu.mrd.classification.redis.RedisCrud;
import com.sohu.mrd.classification.service.JunkInformationProcessService;
/**
 * @author Jin Guopan
   @creation 2017年1月20日
 */
public class NewsClassifyServlet extends HttpServlet {
	private static Logger LOG =Logger.getLogger(NewsClassifyServlet.class);
	private static final  String REDIS_PREFIX="final2_sohu_com_classification_filter_cach_";
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    String url=request.getParameter("url");
		    String title=request.getParameter("title");
		    //判断缓存中是否存在
		    String cachResult=RedisCrud.get(REDIS_PREFIX+url);
		    if(cachResult==null)//缓存不存在
		    {
				    String content=request.getParameter("content");
				    String sort=request.getParameter("sort");
				    String imageCount=request.getParameter("imageCount");
				    String media=request.getParameter("media");
				    News news= new News();
				    news.setContent(content);
				    news.setImageCount(imageCount);
				    news.setMedia(media);
				    news.setUrl(url);
				    news.setSort(sort); 
				    news.setTitle(title);
				    LOG.info("接口获取数据  "+news);
				    long startTime=System.currentTimeMillis();
				    JunkInformationProcessService junkInformationProcessService = new JunkInformationProcessService();
				    JudgeInfo  judgeInfo= junkInformationProcessService.getJudgeInfo(news);
				    long endTime=System.currentTimeMillis();
				    long filterTime=endTime-startTime;
				    LOG.info("filterTime  "+filterTime);
				    if(filterTime > 6000)
				    {
				    	LOG.info("过滤时间超过6s " +filterTime +" url "+url);
				    }
				    	JSONObject result = new JSONObject();
					    result.put("status", judgeInfo.getStatus());
					    result.put("reason", judgeInfo.getReason());
					    result.put("url", url);
					    result.put("title", title);
					    write2Client(result.toJSONString(), response);
					    RedisCrud.set(REDIS_PREFIX+url, result.toJSONString());//把结果写入redis缓存
					    LOG.info("返回的值为 "+result+" url "+url+" title "+title);
		    }else{
		    	//返回缓存的结果
		    	 write2Client(cachResult, response);
		    	 LOG.info("从缓存中获取返回的值为 "+cachResult+" url "+url+" title "+title);
		    }
	}
	/**
	 * 向前端写入数据
	 * @throws IOException
	 */
	public void write2Client(String vlaue, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(vlaue);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
