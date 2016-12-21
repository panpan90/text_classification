package com.sohu.mrd.classification.featureweight;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.log4j.Logger;

import com.sohu.mrd.classification.preProcess.WordBag;
import com.sohu.mrd.classification.utils.FileKit;
/**
 * @author Jin Guopan
   @creation 2016年12月6日
     使用词频作为特征，产生特征：权重文件
 */
public class TFIDF {
	public static final Logger LOG=Logger.getLogger(TFIDF.class);
	public static final String  TRAIN_FEATURE_WEIGHT_PATH="svm_data/train_feature_weight_path";
	public static final String  FEATURE_PATH="data/feature_select";
	public static final String  TEST_FEATURE_WEIGHT_PATH="svm_data/test_feature_weight";
	//加载所有特征词
	public static LinkedHashMap<String,Double> featruesWeight=new LinkedHashMap<String,Double>();
	static{
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(FEATURE_PATH);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line="";
			while((line=br.readLine())!=null)
			{
				featruesWeight.put(line, 0.0);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 编码并设置权重
	 * @param content
	 * @return
	 */
	public Map<String,Double>  generateWeight(String content)
	{
		Map<String,Double>  codeFeatureWeight = new  LinkedHashMap<String,Double>();
		WordBag wordBag = new  WordBag();
		Map<String, Integer>  wordCountMap = wordBag.generateWordCount(content);
		//填充weight
		Set<String> weightSet=featruesWeight.keySet();
		Iterator<String> it=weightSet.iterator();
		int numberCode=0;
		while(it.hasNext())
		{
			String word=it.next();
			Double  countWeight=0.0;
			if(wordCountMap.containsKey(word))
			{
				int count=wordCountMap.get(word);
				countWeight=Double.valueOf(count);
			}
			codeFeatureWeight.put(String.valueOf((++numberCode)), countWeight);
		}
		return codeFeatureWeight;
	}
	/**
	 * 
	 * @param inputPath   输入路径
	 * @param category    测试还是训练集
	 * @param outputPath  输出路径
	 * @param isTrain     是否训练是训练集
	 */
	public void  writeWeightFile(String inputPath,String category,String outputPath,boolean isTrain)
	{
		List<String> contents=FileKit.read2List(inputPath);
		for(int i=0;i<contents.size();i++)
		{
			String content=contents.get(i);
			Map<String, Double> codeFeatureWeight = generateWeight(content);
			Set<String> keySet=codeFeatureWeight.keySet();
			StringBuilder sb=new StringBuilder();
			if(isTrain)
			{
				sb.append(category);
			}
			Iterator<String> it=keySet.iterator();
			while(it.hasNext())
			{
				String wordCode=it.next();
				Double weight=codeFeatureWeight.get(wordCode);
				sb.append(" ");
				sb.append(wordCode);
				sb.append(":");
				sb.append(weight);
				sb.append(" ");
			}
			sb.append("\n");
			FileKit.write2File(sb.toString(), outputPath, true);
			sb.delete(0, sb.length());
	  }
	}
	
	public String  generatePredictData(String content)
	{
		Map<String,Double>  codeFeatureWeight =generateWeight(content);
		StringBuilder sb = new StringBuilder();
		Set<String> set=codeFeatureWeight.keySet();
		Iterator<String> it=set.iterator();
		sb.append("1");
		sb.append(" ");
		while(it.hasNext())
		{
			String code=it.next();
			Double weight=codeFeatureWeight.get(code);
			sb.append(code);
			sb.append(":");
			sb.append(weight);
			sb.append(" ");
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		    TFIDF tf_idf = new TFIDF();
		    //文件如果存在，先删除
			File test_file = new File(TFIDF.TEST_FEATURE_WEIGHT_PATH);
			File train_file = new File(TFIDF.TRAIN_FEATURE_WEIGHT_PATH);
			if(test_file.exists())
			{
				test_file.delete();
			}
//			if(train_file.exists())
//			{
//				train_file.delete();
//			}
//			//产生预测集 特征，权重
//		    tf_idf.writeWeightFile("data/sexy_content","1",TFIDF.TRAIN_FEATURE_WEIGHT_PATH,true);
//		    List<String> filePaths=FileKit.recurReadFile("ordernaryYuliao");
//		    for(int i=0;i<filePaths.size();i++)
//		    {
//		    	String filePath=filePaths.get(i);
//		    	tf_idf.writeWeightFile(filePath, "0",TFIDF.TRAIN_FEATURE_WEIGHT_PATH,true);
//		    }
		    //产生测试集 特征，权重
		    tf_idf.writeWeightFile("data/sexy_test","1",TFIDF.TEST_FEATURE_WEIGHT_PATH,true);
		    tf_idf.writeWeightFile("data/ordernary_test", "1",TFIDF.TEST_FEATURE_WEIGHT_PATH,true);
		}
}
