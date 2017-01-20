package com.sohu.mrd.classification.libsvm;
import org.apache.log4j.Logger;
import com.sohu.mrd.classification.bean.News;
import com.sohu.mrd.classification.utils.SegmentKit;
/**
 * @author Jin Guopan
   @creation 2017年1月20日
      模型实时判断
 */
public class ModelRealTimePredictService {
    private static  final Logger LOG = Logger.getLogger(ModelRealTimePredictService.class);
    private ModelRealTimePredictService(){}
    private static class InstanceHolder{
    	private static ModelRealTimePredictService  modelRealTimePredictService = new ModelRealTimePredictService();
    }
    public static ModelRealTimePredictService  getInstance()
    {
    	 return InstanceHolder.modelRealTimePredictService;
    }
    public  String  filterByModel(News news)
    {
    	String filterReason=null;
    	String title=news.getTitle();
    	String content=news.getContent();
    	//根据模型进行判断
        	
    	
         return    filterReason; 	
    }
}
