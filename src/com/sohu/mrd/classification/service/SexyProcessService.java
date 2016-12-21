package com.sohu.mrd.classification.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sohu.mrd.classification.featureweight.TFIDF;
import com.sohu.mrd.classification.libsvm.Processor;
import com.sohu.mrd.classification.utils.FileKit;
import com.sohu.mrd.classification.utils.SegmentKit;

/**
 * @author Jin Guopan
 * @creation 2016年12月7日
 */
public class SexyProcessService {
	private static final Logger LOG = Logger
			.getLogger(SexyProcessService.class);
	private static List<String> sexyword = new ArrayList<String>();
	static {
		InputStream is = SexyProcessService.class.getClassLoader()
				.getResourceAsStream("dic/sexy_words.dic");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String tempWord = "";
		try {
			while ((tempWord = br.readLine()) != null) {
				sexyword.add(tempWord);
			}
		} catch (IOException e) {
			LOG.error("加载色情词汇失败 " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 判断该新闻是否是色情新闻
	 * 
	 * @param url
	 * @param title
	 * @param content
	 * @param content_length
	 * @param title_length
	 * @param media
	 * @param publish_time
	 * @param source
	 * @return
	 */
	public String judgeIsSexy(String title, String content) {
		String result = "0"; //表示不是色情文章
		List<String> titleSegs = SegmentKit.article2List(title);
		int titleSexyCount = 0;
		int contentSexyCount = 0;
		for (int i = 0; i < titleSegs.size(); i++) {
			if (sexyword.contains(titleSegs.get(i))) {
				titleSexyCount++;
			}
		}
		if (titleSexyCount >= 1) {
			result = "1";
		} else if (titleSexyCount == 0) {
			List<String> contentSegs = SegmentKit.article2List(content);
			for (int j = 0; j < contentSegs.size(); j++) {
				if (sexyword.contains(contentSegs.get(j))) {
					contentSexyCount++;
				}
			}
			if (contentSexyCount >= 3) {
				result = "1";
			}
		}
		if (result.equals("0")) {
			//LOG.info("生成预测文件");
			TFIDF tf_idf = new TFIDF();
			String predictData = tf_idf.generatePredictData(content);
			Processor predictProcessor = new Processor();
			result=predictProcessor.predict(predictData);
		}
		return result;
	}
	public static void main(String[] args) {
		SexyProcessService sexyProcessService = new SexyProcessService();
		List<String> list=FileKit.read2List("data/sexy_docId");
		for(int i=0;i<list.size();i++)
		{
			     String[] lines = list.get(i).split("\t", -1);
			     if(lines.length>=2)
			     {
			    	 String docId =lines[0];
				     String content=lines[1];
				     if(content!=null&&!content.trim().equals(""))
				     {
				    	 String result = sexyProcessService.judgeIsSexy("", content);
				    	 if(result.equals("0.0")||result.equals("0"))
				    	 {
				    		 LOG.info("docId "+docId+"\t"+"预测结果为 "+result);
				    	 }
				    	
				     } 
			     }
			     
		}
		
	}
}
