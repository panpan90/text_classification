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
    public  String  filterByModel(News news,String sexyModelPath,String adsModelPath)
    {
    	String filterReason=null;
    	String title=news.getTitle();
    	String content=news.getContent();
    	//根据色情模型进行判断
       // WekaSVM.predictOnlineSigle(sexyModelPath, GenerateSigleInstance.generateSigleInstance(news.getContent()));
    	//根据广告模型进行判断
       Double result=WekaSVM.predictOnlineSigle(adsModelPath, GenerateSigleInstance.generateSigleInstance(news.getContent()));
       if(result==1.0)
       {
    	   filterReason="根据广告模型被过滤";
       }
         return    filterReason; 	
    }
}
