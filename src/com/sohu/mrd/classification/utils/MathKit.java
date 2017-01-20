package com.sohu.mrd.classification.utils;
/**
 * @author Jin Guopan
 * @creation 2016年12月2日
 */
public class MathKit {
	/**
	 * 计算对数
	 * 
	 * @param base
	 *            表示底数
	 * @param value
	 *            表示值
	 * @return
	 */
	public static double getLog(double base, double value) {
		return Math.log(value) / Math.log(base);
	}

	public static boolean isNumeric(String str){  
		  if(null==str || str.trim().equals(""))
		  {
			  return false;
		  }
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){  
		    return false;  
		   }  
		  }  
		  return true;  
		}
	public static void main(String[] args) {
		boolean is=isNumeric("10");
		System.out.println(is);
	}
}
