package text_classification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sohu.mrd.classification.preProcess.WordBag;
import com.sohu.mrd.classification.utils.FileKit;
import com.sohu.mrd.classification.utils.HttpClientUtil;
import com.sohu.mrd.classification.utils.KillTag;
import com.sohu.mrd.classification.utils.commonMethod;
/**
 * @author Jin Guopan
   @creation 2016年12月7日
 */
public class TestSexy {
	public static final Logger LOG = Logger.getLogger(TestSexy.class);
	public static void main(String[] args) {
			TestSexy  testSexy = new TestSexy();
			List<String> docIds=testSexy.extract();
			for(int i=0;i<docIds.size();i++)
			{
				if(null!=docIds.get(i) && !docIds.get(i).trim().equals(""))
				{
					String content=commonMethod.getNewsByDocId(docIds.get(i));
					FileKit.write2File(docIds.get(i)+"\t"+content, "data/sexy_docId");
					if(content!=null&&!content.trim().equals(""))
					{
						WordBag wordBag = new WordBag();
						List<String>  wordBags=wordBag.generateWordBag(content);
						LOG.info("wordBags "+wordBags);
					}
				}
			}
	}
	@Test
	public List<String>  extract()
	{
		List<String> list=FileKit.read2List("data/sexy.log");
		List<String> docIds = new ArrayList<String>();
		for(int i=0;i<list.size();i++)
		{
			String line=list.get(i);
			int  start=line.indexOf("docid");
			int  end = line.indexOf("duplicate");
			String docId=line.substring(start+8, end-3);
			docIds.add(docId);
		}
		return docIds;
	}
}
