package com.sohu.mrd.classification.web;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.classification.service.SexyProcessService;

/**
 * @author Jin Guopan
 * @creation 2016年12月7日 {"sort":"","url":"
 *           http://news.youth.cn/yl/201611/t20161116_8851373
 *           .htm","title":"超尴尬！素颜出镜的陈坤
 *           ，竟被指撞脸宋小宝","bread":[],"content":"<image_0></image_0><br>
 *           网友微博截图<br>
 *           <image_1></image_1><br>
 *           陈坤<br>
 *           近日，有网友在微博中晒出一张照片，配文称:宋小宝太像陈坤了。乍一看去，照片中坐在民族风的床单上，皮肤黝黑，穿着休闲的人是宋小宝无疑，
 *           但仔细辨认，却发现原来是素颜的陈坤。这个乌龙让网友哭笑不得。<br>
 *           对此，网友纷纷评论：这是陈坤的颜值被黑的最惨的一次。哈哈哈，陈坤看到之后，内心是崩溃的。一打眼还真的以为是宋小宝。<br>
 *           ","content_length":233,"title_length":20,"media":"中国青年网","
 *           image_count":2,"summary":"","image_string":"
 *           http://news.youth.cn/yl/201611/W020161116342413460529.jpg
 *           http://news
 *           .youth.cn/yl/201611/W020161116342414191815.jpg","publish_time
 *           ":"1479259697000","from":" k.sohu.com
 *           ","source":"中国青年网","page_title
 *           ":"","is_force_rec":0,"cmsid":"-1","token
 *           ":"3bd1ed5f-c3ee-4252-88a3-014aed9f5f0c", "st":1,"old_title":""}
 */
public class SexyServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String json = request.getParameter("json"); // 接受前端json数据
		JSONObject result = new JSONObject();
		result.put("status", "success");
		if(json.trim().equals("")||null==json)
		{
			result.put("status", "fail");
			result.put("msg", "json is null");
		}else{
			JSONObject 	jsonObject=JSON.parseObject(json);
			String url=jsonObject.getString("url");
			String source=jsonObject.getString("source");
			String content=jsonObject.getString("content");
			String content_length=jsonObject.getString("content_length");
			String title_length=jsonObject.getString("title_length");
			String media=jsonObject.getString("media");
			String publish_time=jsonObject.getString("publish_time");
			String title=jsonObject.getString("title");
			SexyProcessService  sexyProcessService = new SexyProcessService();
			sexyProcessService.judgeIsSexy(title, content);
		}
	}
	/**
	 * 向浏览器写入数据
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
