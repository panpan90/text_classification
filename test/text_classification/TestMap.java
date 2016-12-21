package text_classification;

import java.util.HashMap;

/**
 * @author Jin Guopan
   @creation 2016年12月2日
 */
public class TestMap {
	public static void main(String[] args) {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		map.put("我的", 1);
		Integer k=map.get("她的");
		if(k!=null)
		{
			System.out.println(k);
		}
		
	}
}
