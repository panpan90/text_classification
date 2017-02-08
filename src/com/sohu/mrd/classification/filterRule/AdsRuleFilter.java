package com.sohu.mrd.classification.filterRule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sohu.mrd.classification.bean.News;
import com.sohu.mrd.classification.utils.KillPuctuation;
import com.sohu.mrd.classification.utils.KillTag;
import com.sohu.mrd.classification.utils.MathKit;
import com.sohu.mrd.classification.utils.SegmentKit;
/**
 * @author Jin Guopan
 * @creation 2017年1月19日 广告过滤的规则
 */
public class AdsRuleFilter {
	private static final Logger LOG = Logger.getLogger(AdsRuleFilter.class);
	private static List<String> adsList = new ArrayList<String>();
	static{
		InputStream adsIs = AdsRuleFilter.class.getClassLoader()
				.getResourceAsStream("dic/ads.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(adsIs));
		String temp="";
		try {
			while((temp=br.readLine())!=null)
			{
				adsList.add(temp);
			}
		} catch (IOException e) {
			LOG.error("读取广告词汇异常 ",e);
		}
	}
	private AdsRuleFilter() {
	}

	private static class InstanceHolder {
		private static AdsRuleFilter instance = new AdsRuleFilter();
	}
	
	public static AdsRuleFilter getInstance()
	{
		return InstanceHolder.instance;
	}
	
	public String filterByAds(News news) {
		//TODO  通过关键字个数过滤关键字个数少于5被过滤
		//TODO  通过医疗词汇过滤标题
		String ruleFilterReason = null;
		String title = news.getTitle();
		String content = news.getContent();
		int imageCount = 0;
		String imageCountStr = news.getImageCount();
		if (imageCountStr != null && !imageCountStr.equals("")
				&& MathKit.isNumeric(imageCountStr)) {
			imageCount = Integer.valueOf(imageCountStr);
		}
		List<String> titleSegment = SegmentKit.segmentSentence(news.getTitle());
		for(int i=0;i<titleSegment.size();i++)
		{
			if(adsList.contains(titleSegment.get(i)))
			{
				ruleFilterReason="标题含有广告词汇被过滤，包含的广告词汇为 "+titleSegment.get(i);
				return ruleFilterReason;
			}
		}
		// 文章图片小于4，且文章长度小于100过滤
		if (imageCount < 4 && news.getContent().length() < 50) {
			ruleFilterReason = "文章图片小于4且正文小于50被过滤 ，文章图片个数为 " + imageCount
					+ " 文章正文长度为" + news.getContent().length();
			return ruleFilterReason;
		}
		// 文章没有媒体来源，被过滤
		if (news.getMedia() == null || news.getMedia().trim().equals("")) {
			ruleFilterReason = "文章没有媒体来源，被过滤  取得的媒体来源为 " + news.getMedia();
			return ruleFilterReason;
		}
		 //标题里包含**元，过滤
		if (news.getTitle().matches(".*\\d+元.*")) {
			ruleFilterReason = "标题包含价格被过滤,标题为  " + news.getTitle();
			return ruleFilterReason;
		}
		//过滤手机号，邮箱 ，qq
		String phoneFilter=filterByPhoneEtc(news);
		if(phoneFilter!=null)
		{
			ruleFilterReason=phoneFilter;
			return ruleFilterReason;
		}
		return ruleFilterReason;
	}
	/**
	 * 过滤手机号，邮箱，qq 
	 * @param news
	 */
	private String   filterByPhoneEtc(News news)
	{
		String content=news.getContent();
		//去除内容中的标签
		content=(KillTag.killTags(content));
		String filterReason = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("零", "0");
		map.put("一", "1");
		map.put("二", "2");
		map.put("三", "3");
		map.put("四", "4");
		map.put("五", "5");
		map.put("六", "6");
		map.put("七", "7");
		map.put("八", "8");
		map.put("九", "9");
		StringBuilder ch2NumberContentSb = new StringBuilder();
		// 把文章中中文数字转换为数字
		for (int i = 0; i < content.length(); i++) {
			String charStr = content.charAt(i) + "";
			if (map.containsKey(charStr.trim())) {
				String numberChar = map.get(charStr.trim());
				ch2NumberContentSb.append(numberChar);
			} else {
				ch2NumberContentSb.append(charStr);
			}
		}
		String ch2NumberContent = ch2NumberContentSb.toString();
		String splitRegu = "[,|。|，|!|！|?|？|、]";
		String[] ss = ch2NumberContent.split(splitRegu, -1); // 把文章切分为句子
		// qq 关键字
		String qqKeywordsReg = ".*[qq|QQ|qQ|Qq|扣扣|联系|地址|号码|咨询|详情|专家|热线|咨询|预约|致电|Q群|q群].*";
		// phone 关键字
		String phonekeywordsReg = ".*[热线|传真|预约|致电|电话|联系|地址|号码|咨询|拨打|手机|固话|详情|专家|热线|咨询].*";
		// 判断是否为固话的正则
		String phone_reg1 = ".*0\\d{2,3}-\\d{7,8}([^\\d]|.*).*"; //0351-8302224
		String phone_reg2 = ".*\\d{7,8}[^\\d].*"; //8302224
		String phone_reg3 = ".*0\\d{2,3}-\\d{3,4}-\\d{3,4}([^\\d]|.*).*";//86-10-66778899 
		// qq正则
		String qq_reg = ".*[1-9][0-9]{4,}.*";
		// email的正则表达式
		String email_reg = ".*(\\w)+(\\.\\w+)*[@|#](\\w)+((\\.\\w{2,3}){1,3}).*";
		// 判断是否为手机号的正则
		String mobile_reg = ".*((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}.*";
		for (int i = 0; i < ss.length; i++){
			if (ss[i].matches(mobile_reg)) // 判断是否为手机号码
			{
				filterReason = "通过手机号码被过滤，包含手机号码的句子是  " + ss[i];
				 return filterReason;
			}
			if (ss[i].matches(qqKeywordsReg)) // 包含qq关键字
			{
				if (ss[i].matches(qq_reg)){
					filterReason = "通过qq号码被过滤，包含qq号码的句子是  " + ss[i];
					return filterReason;
				}
			}
			if (ss[i].matches(phonekeywordsReg)) // 包含固话的关键字
			{
                if(ss[i].matches(phone_reg1) || ss[i].matches(phone_reg2) || ss[i].matches(phone_reg3))
                {
                	filterReason="通过固话被过滤，包含的电话号码的句子为  "+ss[i];
                	return filterReason;
                }
			}
			if(ss[i].matches(email_reg))
			{
				filterReason="通过email被过滤，包含的email的句子为   "+ss[i];
				return filterReason;
			}
		}
		 return filterReason;
	}
}
