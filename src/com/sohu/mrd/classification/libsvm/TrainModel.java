package com.sohu.mrd.classification.libsvm;
import java.util.List;
/**
 * @author Jin Guopan
   @creation 2017年2月7日
   训练模型
 */
public class TrainModel{
	public static void main(String[] args) {
//		List<String> contents=FileKit.read2List("data/online_ads/hbase_ads");
//		GenerateArff.generateArff(contents, "1", "arff_file/ads.arff");
//		List<String> contents2=FileKit.read2List("data/online_ads/ordernary_test");
//		GenerateArff.generateArff(contents2, "0", "arff_file/ordernary.arff");
//		String[] options = { "-S", "0", "-k", "0", "-C", "3" };
//		
//		WekaSVM.train("arff_file/train.arff", "data/online_ads/new_train.model", options);
//		WekaSVM.crossPredict("data/online_ads/new_train.model", "arff_file/train.arff");
		WekaSVM.predictByBatch("arff_file/train.arff", "data/online_ads/new_train.model");
	}
}
