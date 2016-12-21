package com.sohu.mrd.classification.preProcess;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.log4j.Logger;

import com.sohu.mrd.classification.constant.Constant;
import com.sohu.mrd.classification.utils.FileKit;
import com.sohu.mrd.classification.utils.KillTag;
import com.sohu.mrd.classification.utils.MapKit;
/**
 * @author Jin Guopan
   @creation 2016年11月29日
     把训练集的文档转换为词袋模型,并写入相应文件
 */
public class WordBag {
	public static final Logger LOG=Logger.getLogger(WordBag.class);
	public static String   TRAIN_PATH="data//SogouC-UTF8//train";
	public static String   wordBagPath="data/wordBag";
	public static String   wordCountPath="data/wordCount";
	public static String   sexy_content_path="data/sexy_content";
	public static String   sexy_wordCount_path="data/sexy_wordcount";
	public static String   ordernaryYuliao="ordernaryYuliao";
	public static String   ordernaryYuliao_wordCount_path="data/ordernary_wordcount";
	public static List<String>  stopWords = new ArrayList<String>();
	static{
		InputStream  is=WordBag.class.getClassLoader().getResourceAsStream(Constant.STOP_WORDS_PATH);
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		stopWords=FileKit.is2String(is);
	}
	public static void main(String[] args) {
		WordBag wordBag=new WordBag();
		//wordBag.generateWordBag(TRAIN_PATH, wordBagPath);
		//wordBag.generateWordCount(TRAIN_PATH, wordCountPath);
		//wordBag.generateWordCount(sexy_content_path, sexy_wordCount_path);
		wordBag.generateWordCount(ordernaryYuliao, ordernaryYuliao_wordCount_path);
	}
	public  void   generateWordBag(String inputPath,String outputPath)
	{
		HashSet<String>  wordSet=new HashSet<String>();
		FileOutputStream fos=null;
		List<String> trainPaths=FileKit.recurReadFile(inputPath);
		try {
			if(trainPaths!=null&&trainPaths.size()>=1)
			{
				for(int i=0;i<trainPaths.size();i++)
				{
					String content=FileKit.read2String(trainPaths.get(i));
					if(content!=null&&!content.trim().equals(""))
					{
						String[]  sentences=segmentPage(content);
						if(sentences!=null)
						{
								List<String> wordList=splitWord(sentences);
								wordSet.addAll(wordList);
						}
					}
				}
			}
			fos=new FileOutputStream(outputPath,true); // true 表示追加写
			Iterator<String> it=wordSet.iterator();
			while(it.hasNext())
			{
				String word=it.next();
				StringBuilder sb=new StringBuilder();
				sb.append(word);
				sb.append("\n");
				fos.write(sb.toString().getBytes());
			}
		} catch (Exception e) {
			throw new RuntimeException("写入数据异常 "+e.getMessage());
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void generateWordCount(String inputPath,String outputPath)
	{
		List<String>  list = new ArrayList<String>();
		FileOutputStream fos=null;
		List<String> trainPaths=FileKit.recurReadFile(inputPath);
		try {
			if(trainPaths!=null&&trainPaths.size()>=1)
			{
				for(int i=0;i<trainPaths.size();i++)
				{
					String content=FileKit.read2String(trainPaths.get(i));
					if(content!=null&&!content.trim().equals(""))
					{
						String[]  sentences=segmentPage(content);
						if(sentences!=null)
						{
								List<String> wordList=splitWord(sentences);
								list.addAll(wordList);
						}
					}
				}
			}
			fos=new FileOutputStream(outputPath,true); // true 表示追加写
			Map<String, Integer>  map=MapKit.wordCountsort(list);
			Set<String> set=map.keySet();
			Iterator<String> it=set.iterator();
			while(it.hasNext())
			{
				String word=it.next();
				int value=map.get(word);
				StringBuilder sb=new StringBuilder();
				sb.append(word);
				sb.append("\t");
				sb.append(value);
				sb.append("\n");
				LOG.info(sb.toString());
				fos.write(sb.toString().getBytes());
			}
		} catch (Exception e) {
			throw new RuntimeException("写入数据异常 "+e.getMessage());
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//根据文章内容产生 wordCount  
	public Map<String,Integer>  generateWordCount(String content)
	{
		String[] sentences=segmentPage(content);
		List<String> words=splitWord(sentences);
		Map<String,Integer> map=MapKit.wordCountsort(words);
		return map;
	}
	public String[]  segmentPage(String content)
	{
		String[] sentences=null;
		String regex="。|；|;|\\?|\\？|！|!|,|，|\\.|:|“|”";
		if(content!=null&&!content.trim().equals(""))
		{
			sentences=content.split(regex, -1);
		}
		return sentences;
	}
	/**
	 * 对句子进行切分，返回切分后的单词集合
	 * @param sentences
	 * @return
	 */
	public List<String> splitWord(String[] sentences)
	{
		List<String> list = new ArrayList<String>();
		if(sentences!=null)
		{
			for(int i=0;i<sentences.length;i++)
			{
				String sentence=sentences[i];
				Result  result=NlpAnalysis.parse(sentence);
				List<Term> terms=ToAnalysis.parse(sentence).getTerms();
				for(int j=0;j<terms.size();j++)
				{
					Term  term = terms.get(j);
					String word=term.getName();
					//过滤停用词
					if(!stopWords.contains(word))
					{
						list.add(word);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 产生词袋模型
	 * @param content
	 * @return
	 */
	public List<String>  generateWordBag(String content)
	{
		List<String> wordBag = new ArrayList<String>();
		//对文章去标签
		String pureContent=KillTag.killTags(content);
		String[] sentences=segmentPage(pureContent);
		List<String> words=splitWord(sentences);
		HashSet<String> wordBagSet = new HashSet<String>(); 
		wordBagSet.addAll(words);
		wordBag.addAll(wordBagSet);
		return wordBag;
	}
	
	

}
