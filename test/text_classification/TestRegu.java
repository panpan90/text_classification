package text_classification;
/**
 * @author Jin Guopan
   @creation 2016年11月30日
 */
public class TestRegu {
	public static void main(String[] args) {
		String regex="。|；|;|\\?|\\？|！|!|,|，|\\.";
		String sentence="这是；分词;的句?子。我的 ？她的";
		String[] segment=sentence.split(regex, -1);
	    for(int i=0;i<segment.length;i++)
	    {
	    	String word=segment[i];
	    	System.out.println("word "+word);
	    }
	}
}
