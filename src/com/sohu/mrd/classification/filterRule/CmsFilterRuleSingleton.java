package com.sohu.mrd.classification.filterRule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
/**
   @author Jin Guopan
   @creation 2017年1月13日
     通过CMS垃圾源被过滤掉
 */
public class CmsFilterRuleSingleton {
    private static final Logger LOG = Logger.getLogger(CmsFilterRuleSingleton.class);
    private  static List<String>   blackSortList = new ArrayList<String>();
     static{
    	InputStream  is = CmsFilterRuleSingleton.class.getClassLoader().getResourceAsStream("filter_dic/black_sort");
    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
    	String temp="";
    	try {
    		while((temp=br.readLine())!=null)
    		{
    			blackSortList.add(temp.trim());
    		}
		} catch (IOException e){
			LOG.error("读取black_sort名单错误 ",e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				LOG.error("关闭 black_sort 流异常 ",e);
			}
		}
    }
     private CmsFilterRuleSingleton()
     { 
     }
     // 单例提供类
     private static  class InstanceHolder{
    	 private static final  CmsFilterRuleSingleton instance =  new CmsFilterRuleSingleton();
     }
     public static  CmsFilterRuleSingleton getInstance()
     {
    	 return InstanceHolder.instance;
     }
     //对含有badSort的东西进行过滤
     public  String   filterBadSort(String sort)
     {
    	 String filterReason=null;
    	 if(blackSortList.contains(sort.trim()))
    	 {
    		 filterReason="通过sort字段过滤，被过滤的sort字段为   "+sort.trim();
    	 }
    	 return filterReason;
     }
} 
