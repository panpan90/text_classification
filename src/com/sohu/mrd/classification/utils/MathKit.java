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

	public static void main(String[] args) {
		double base = 1.0 + 0.1;
		double value = 1.1;
		System.out.println(getLog(base, value));
	}
}
