package com.sohu.mrd.classification.libsvm;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sohu.mrd.classification.utils.SegmentKit;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
/**
 * @author Jin Guopan
   @creation 2017年2月4日
     产生一条数据的Instance
 */
public class GenerateSigleInstance {
	private static Logger LOG = Logger.getLogger(GenerateSigleInstance.class);
	private static LinkedHashMap<String, Double> feature= new LinkedHashMap<String, Double>(); 
	static{
		InputStream is=GenerateSigleInstance.class.getClassLoader().getResourceAsStream("feature_select");
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		String  temp="";
		try {
			while((temp=br.readLine())!=null)
			{
				feature.put(temp, 0.0);
			}
		} catch (IOException e) {
			LOG.info("读取特征文件出错",e);
		}finally{
		    try {
				is.close();
			} catch (IOException e) {
				LOG.info("关闭文件异常",e);
			}
		}
	}
	public static Instance  generateSigleInstance(String content)
	{
		List<String> words=SegmentKit.article2List(content);
		for(int i=0;i<words.size();i++)
		{
			if(feature.containsKey(words.get(i)))
			{
				feature.put(words.get(i), feature.get(words.get(i))+1.0);
			}
		}
		Instance instance=generateInstanceByFeatureMapping();
		return instance;
		
	}
	private static Instance  generateInstanceByFeatureMapping()
	{
		Instance instance=null;
		StringBuilder sb = new StringBuilder();
		sb.append("@relation test");
		sb.append("\n");
		for(int i=0;i<feature.size();i++)
		{
			sb.append("@attribute feature"+i+" real");
			sb.append("\n");
		}
		sb.append("@attribute class "+"{1,0}");
		sb.append("\n");
		sb.append("@data");
		sb.append("\n");
		Collection<Double> values=feature.values();
		Iterator<Double>  it=values.iterator();
		while(it.hasNext())
		{
			Double  featureValue=it.next();
			sb.append(featureValue);
			sb.append(",");
		}
		sb.append("1");
		sb.append("\n");
		String arff=sb.toString();
		sb.delete(0, sb.length());
		//对feature的value重新进行初始化
		//feature 进行初始化
		Set<String>  clearSet=feature.keySet();
		Iterator<String> clearIt=clearSet.iterator();
		while(clearIt.hasNext())
		{
			String key=clearIt.next();
			feature.put(key, 0.0);
		}
		ArffLoader arffLoader = new ArffLoader();
		try {
			arffLoader.setSource(new ByteArrayInputStream(arff.getBytes()));
			Instances instances=arffLoader.getDataSet();
			instances.setClassIndex(instances.numAttributes()-1);
			instance=instances.instance(0);
			return instance;
		} catch (IOException e) {
			LOG.info("str 转换为 stream 异常",e);
		}
        return null;		
	}
	public static void main(String[] args) {
		Instance instance=generateSigleInstance("我是一天测试新闻");
		if(instance!=null)
		{
			System.out.println(instance.toString());
			System.out.println(instance.classValue());
			System.out.println(instance.classIndex());
			System.out.println(instance.numAttributes());
			System.out.println(instance.numValues());
		}
	}
}
