package com.sohu.mrd.classification.libsvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sohu.mrd.classification.utils.SegmentKit;

/**
 * @author Jin Guopan
   @creation 2017年2月7日
 */
public class GenerateArff {
	private static Logger LOG = Logger.getLogger(GenerateArff.class);
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
	public static void generateArff(List<String> contents,String className,String arffPath)
	{  
		LinkedHashMap<String, Double> pureFeature=feature;
		StringBuilder sb = new StringBuilder();
		sb.append("@relation train");
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
		for(int i=0;i<contents.size();i++)
		{
			String content=contents.get(i);
			List<String> words=SegmentKit.article2List(content);
			for(int k=0;k<words.size();k++)
			{
				if(feature.containsKey(words.get(k)))
				{
					feature.put(words.get(k), feature.get(words.get(k))+1.0);
				}
			}
			Collection<Double> values=feature.values();
			Iterator<Double> it=values.iterator();
			while(it.hasNext())
			{
				sb.append(it.next());
				sb.append(",");
			}
			sb.append(className);
			sb.append("\n");
			//feature 进行初始化
			Set<String>  clearSet=feature.keySet();
			Iterator<String> clearIt=clearSet.iterator();
			while(clearIt.hasNext())
			{
				String key=clearIt.next();
				feature.put(key, 0.0);
			}
		}
		FileKit.write2File(sb.toString(), arffPath, true);
	}
	
}
