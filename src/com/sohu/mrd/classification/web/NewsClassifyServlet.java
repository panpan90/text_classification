package com.sohu.mrd.classification.web;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sohu.mrd.classification.bean.News;
/**
 * @author Jin Guopan
   @creation 2017年1月20日
 */
public class NewsClassifyServlet extends HttpServlet {
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
	}
}
