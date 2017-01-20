package com.sohu.mrd.classification.filterRule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.sohu.mrd.classification.bean.News;
import com.sohu.mrd.classification.utils.SegmentKit;
/**
 * @author Jin Guopan
   @creation 2017年1月19日
 */
public class SexyRuleFilter {
	private static final Logger LOG = Logger.getLogger(SexyRuleFilter.class);
	private static List<String> sexyList = new ArrayList<String>();
	static {
		InputStream sexyIs = SexyRuleFilter.class.getClassLoader()
				.getResourceAsStream("dic/top_level_sexy.txt");
		BufferedReader sexyBr = new BufferedReader(
				new InputStreamReader(sexyIs));
		String sexyTemp = "";
		try {
			while ((sexyTemp = sexyBr.readLine()) != null) {
				sexyList.add(sexyTemp);
			}
		} catch (IOException e) {
			LOG.error("读取色情关键字异常  ", e);
		}
	}
	private SexyRuleFilter(){}
	
	private static class InstanceHolder{
		private static SexyRuleFilter sexyRuleFilter = new SexyRuleFilter();
	}
	
	public static SexyRuleFilter getInstance()
	{
		return InstanceHolder.sexyRuleFilter;
	}
	
	public String  filterBySexy(News news)
	{
		String filterReason = null;
		String title=news.getTitle();
		String content=news.getContent();
		List<String> titleSegment = SegmentKit.segmentSentence(title);
		for (int i = 0; i < titleSegment.size(); i++) {
			if (sexyList.contains(titleSegment.get(i))) {
				filterReason = "通过标题色情词汇被过滤掉，标题出现的色情词为 "+titleSegment.get(i);
				return filterReason;
			}
		}
		List<String>  words=SegmentKit.article2List(content);
		StringBuilder sexySb=new StringBuilder();
		int countSexy=0;
		for(int i=0;i<words.size();i++)
		{
			if(sexyList.contains(words.get(i)))
			{
				countSexy++;
				sexySb.append(words.get(i));
				sexySb.append(",");
			}
		}
		if(countSexy>1)
		{
			filterReason="包括色情被过滤，包含色情词的个数为 "+countSexy+"包含的色情词为  "+sexySb.toString();
			return filterReason;
		}
		return filterReason;
	}
	
}
