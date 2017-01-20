package text_classification;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.sohu.mrd.classification.utils.FileKit;
/**
 * @author Jin Guopan
   @creation 2017年1月16日
 */
public class TestConver {
	public static void main(String[] args) {
		InputStream is=TestConver.class.getClassLoader().getResourceAsStream("filter_dic/word_filter.txt");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"gb2312"));
			String temp="";
			while((temp=br.readLine())!=null)
			{
				String ads=temp.split("\t", -1)[0];
				FileKit.write2File(ads, "data/ads.txt", true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
