package com.sohu.mrd.classification.filterRule;
import org.apache.log4j.Logger;

import com.sohu.mrd.classification.bean.FilterReasonBean;
import com.sohu.mrd.classification.bean.News;
import com.sohu.mrd.classification.utils.MathKit;
/**
 * @author Jin Guopan
 * @creation 2017年1月13日 关于规则过滤的service
 */
public class FilterRuleService {
	private static final Logger LOG =Logger.getLogger(FilterRuleService.class);
	public String FilterByRule(News news) {
		 String filterReason=null;
		 AdsRuleFilter   adsRuleFilter=AdsRuleFilter.getInstance();
		 SexyRuleFilter  sexyRuleFilter=SexyRuleFilter.getInstance();
		 SourceRuleSingletonFilter sourceRuleFilter=SourceRuleSingletonFilter.getInstance();
		 CmsFilterRuleSingleton    cmsRuleFilter=CmsFilterRuleSingleton.getInstance();
		 long  startTimeAds=System.currentTimeMillis();
		 String adsFilterReason=adsRuleFilter.filterByAds(news);
		 long  endTimeAds=System.currentTimeMillis();
		 //LOG.info("广告过滤时间  "+(endTimeAds-startTimeAds));
		 String  sexyFilterReason= sexyRuleFilter.filterBySexy(news);
		 String  sourceFilterReason=sourceRuleFilter.filterBlackSite(news.getUrl());
		 String  cmsFilterReason=cmsRuleFilter.filterBadSort(news.getSort());
		 if(adsFilterReason!=null)
		 {
			 filterReason=adsFilterReason;
			 return filterReason;
		 }else if(sexyFilterReason!=null)
		 {
			 filterReason=sexyFilterReason;
			 return filterReason;
		 }else if(sourceFilterReason!=null)
		 {
			 filterReason=sourceFilterReason;
		 }else if(cmsFilterReason!=null)
		 {
			 filterReason=cmsFilterReason;
		 }
		 return filterReason;
	}
}
