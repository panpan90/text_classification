package text_classification;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.MyStaticValue;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.sohu.mrd.classification.preProcess.WordBag;
import com.sohu.mrd.classification.utils.FileKit;
/**
 * @author Jin Guopan
   @creation 2016年12月7日
 */
public class TestAsj {
	public static final Logger LOG=Logger.getLogger(TestAsj.class);
	public static void main(String[] args) {
		
		List<Term> terms=NlpAnalysis.parse("我是中国人。。。色情。AV下载你是我的小苹果。性交测试是否操b能把色情次分开丰乳肥臀。").getTerms();
		for(int i=0;i<terms.size();i++)
		{
			String name=terms.get(i).getName();
			System.out.println(name);
		}
	}
	
	@Test
	public void  testSexyAnsj()
	{
		WordBag wordBag = new WordBag();
		List<String> contents=FileKit.read2List("data/sexy_content");
		for(int i=0;i<contents.size();i++)
		{
			String content=contents.get(i);
			Map<String, Integer> map=wordBag.generateWordCount(content);
			LOG.info("map "+map);
		}
		
	}
}
