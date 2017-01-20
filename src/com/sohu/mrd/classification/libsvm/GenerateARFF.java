package com.sohu.mrd.classification.libsvm;

import java.util.List;

/**
 * @author Jin Guopan
   @creation 2017年1月9日
   arff 格式
@relation weather 
@attribute outlook {sunny, overcast, rainy} 
@attribute temperature real 
@attribute humidity real 
@attribute windy {TRUE, FALSE} 
@attribute class {yes, no} 
@data 
sunny,85,85,FALSE,no 
sunny,80,90,TRUE,no 
 */
public class GenerateARFF {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("@relation train");
		sb.append("\n");
		for(int i=0;i<8145;i++)
		{
			sb.append("@attribute feature"+i+" real");
			sb.append("\n");
		}
		sb.append("@attribute class "+"{1,0}");
		sb.append("\n");
		sb.append("@data");
		sb.append("\n");
		List<String> list=FileKit.read2List("data/feature_weight");
		for(int i=0;i<list.size();i++)
		{
			int count =0;
			String line=list.get(i);
			String[] ss=line.split(" ", -1);
			String category="";
			if(ss.length>0)
			{
				category=ss[0];
			}
			for(int j=1;j<ss.length;j++)
			{
				String word=ss[j];
				String[] words=word.split(":");
				if(words.length>=2)
				{
					String feature =words[1];
					sb.append(feature);
					sb.append(",");
				}
			}
			sb.append(category);
			sb.append("\n");
		}
		FileKit.write2File(sb.toString(), "data/train.arff");
	}
}
