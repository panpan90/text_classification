package text_classification;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
/**
 * @author Jin Guopan
   @creation 2016年12月8日
 */
public class TestIntersection {
	public static void main(String[] args) {
		
	}
	/**
	 * 两个集合的交集个数
	 */
	@Test
	public void testIntersection()
	{
		List<String> list1 = new ArrayList<String>();  
        List<String> list2 = new ArrayList<String>();  
        list1.add("abc");  list2.add("abc");  
        list1.add("123");  list2.add("123");  
        list1.add("ABC");  
        list2.add("XYZ");  
        list1.retainAll(list2);  
        System.out.println("交集元素个数是："+list1.size());  
	}
	
	 @Test
	 public void testUnion()
	 {
		 List<String> list1 = new ArrayList<String>();  
         List<String> list2 = new ArrayList<String>();  
         list1.add("123");  list2.add("123");  
         list1.add("123");  list2.add("123");  
         list1.add("123");  
         list2.add("456");  
         Set<String> set =new HashSet<String>();  
         set.addAll(list1);
         set.addAll(list2);
         System.out.println("并集元素个数是：" + set.size());    
	 }
	
	
}
