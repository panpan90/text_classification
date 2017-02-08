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
import com.sohu.mrd.classification.service.JunkInformationProcessService;
/**
 * @author Jin Guopan
   @creation 2017年1月20日
 */
public class NewsClassifyServlet extends HttpServlet {
	private static Logger LOG =Logger.getLogger(NewsClassifyServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		   this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    String title=request.getParameter("title");
		    String url=request.getParameter("url");
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
		    JunkInformationProcessService junkInformationProcessService = new JunkInformationProcessService();
		    JudgeInfo  judgeInfo= junkInformationProcessService.getJudgeInfo(news);
		    JSONObject result = new JSONObject();
		    result.put("status", judgeInfo.getStatus());
		    result.put("reason", judgeInfo.getReason());
		    write2Client(result.toJSONString(), response);
		    LOG.info("返回的值为 "+result+" url "+url+" title "+title);
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
