package com.sohu.mrd.classification.service;
import org.apache.log4j.Logger;
import com.sohu.mrd.classification.bean.JudgeInfo;
import com.sohu.mrd.classification.bean.News;
import com.sohu.mrd.classification.filterRule.FilterRuleService;
import com.sohu.mrd.classification.libsvm.ModelRealTimePredictService;
/**
   @author Jin Guopan
   @creation 2017年1月20日
 */
public class JunkInformationProcessService {
	private static final Logger LOG = Logger.getLogger(JunkInformationProcessService.class);
	public JudgeInfo  getJudgeInfo(News news)
	{
		JudgeInfo judgeInfo = new JudgeInfo();
		judgeInfo.setStatus("good");
		judgeInfo.setReason("good news");
		FilterRuleService  filterRuleService = new FilterRuleService();
		String ruleFilterReason=filterRuleService.FilterByRule(news);
		if(ruleFilterReason!=null) //根据规则
		{
			judgeInfo.setStatus("bad");
			judgeInfo.setReason(ruleFilterReason);
			return judgeInfo;
		}
		//ModelRealTimePredictService  modelRealTimePredictService=ModelRealTimePredictService.getInstance();
		//String  modelFilterReason=modelRealTimePredictService.filterByModel(news,"sexyModelPath","adsModelPath");
//		if(modelFilterReason!=null) //根据模型
//		{
//			judgeInfo.setReason("bad");
//			judgeInfo.setReason(modelFilterReason);
//			return judgeInfo;
//		}
		return judgeInfo;
	}
}
