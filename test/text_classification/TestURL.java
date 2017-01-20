package text_classification;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * @author Jin Guopan
   @creation 2017年1月13日
 */
public class TestURL {
   public static void main(String[] args) {
	   URL url = null;
	try {
		url = new URL("https://www.baidu.com/123");
	} catch (MalformedURLException e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   String host=url.getHost();
	   System.out.println("host "+host);
	   URI uri = null;
	try {
		uri = url.toURI();
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  String   http=uri.getScheme();
	  System.out.println("http "+http);
    }
}
