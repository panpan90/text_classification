package com.sohu.mrd.classification.utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import com.sohu.mrd.classification.constant.Constant;
import com.sohu.mrd.classification.preProcess.WordBag;
/**
 * @author Jin Guopan
 * @creation 2016年12月8日 分词的帮助类
 */
public class SegmentKit {
	private  static List<String> stopWords = new ArrayList<String>();
	static {
		InputStream is = WordBag.class.getClassLoader().getResourceAsStream(
				Constant.STOP_WORDS_PATH);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		stopWords = FileKit.is2String(is);
	}

	
	public  static List<String> article2List(String content) {
		// 对文章去标签
		String pureContent = KillTag.killTags(content);
		String[] sentences = segmentPage(pureContent);
		List<String> words = splitWord(sentences);
		return words;
	}

	private  static String[] segmentPage(String content) {
		String[] sentences = null;
		String regex = "。|；|;|\\?|\\？|！|!|,|，|\\.|:|“|”";
		if (content != null && !content.trim().equals("")) {
			sentences = content.split(regex, -1);
		}
		return sentences;
	}
	/**
	 * 对句子进行切分，返回切分后的单词集合
	 * 
	 * @param sentences
	 * @return
	 */
	private  static List<String> splitWord(String[] sentences) {
		List<String> list = new ArrayList<String>();
		if (sentences != null) {
			for (int i = 0; i < sentences.length; i++) {
				String sentence = sentences[i];
				sentence=KillPuctuation.killPuctuation(sentence);
				List<Term> terms = NlpAnalysis.parse(sentence).getTerms();
				for (int j = 0; j < terms.size(); j++) {
					Term term = terms.get(j);
					String word = term.getName();
					// 过滤停用词
					if (!stopWords.contains(word)) {
						list.add(word);
					}
				}
			}
		}
		return list;
	}
}
