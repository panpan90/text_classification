package com.sohu.mrd.classification.utils;
import java.util.HashSet;
import java.util.List;
/**
   @author Jin Guopan
   @creation 2016年12月8日
      两个集合的并集和交集
 */
public class SetKit {
	/**
	 * 交个集合的交集
	 * @return
	 */
	public int  getListIntersection(List<String> list1,List<String> list2)
	{
		int intersectionNumber=0;
		if(list1==null || list2 ==null)
		{
			return intersectionNumber;
		}else {
			list1.retainAll(list2);
			return list1.size();
		}
	}
	/**
	 * 两个集合的并集
	 * @param list1
	 * @param list2
	 * @return
	 */
	public int  getListUnion(List<String> list1,List<String> list2)
	{
		int unionNumber = 0;
		if(list1 == null && list2!= null)
		{
			unionNumber=list2.size();
		}
		if(list1 != null && list2 == null)
		{
			unionNumber = list1.size();
		}
		if(list1 != null && list2!=null)
		{
			HashSet<String>  set = new HashSet<String>();
			set.addAll(list1);
			set.addAll(list2);
			unionNumber = set.size();
		}
		return unionNumber;
	}
}
