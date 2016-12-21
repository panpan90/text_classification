package com.sohu.mrd.classification.libsvm;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.sohu.mrd.classification.utils.FileKit;
/**
 * @author Jin Guopan
   @creation 2016年12月6日
 */
public class Processor {
	public static void main(String[] args) {
		//SVMHandle.gernerateMode("svm_data/train_feature_weight_path", "svm_data/train_model");
		String path="svm_data/train_feature_weight_path";
		List<String>  list=FileKit.read2List(path);
		for(int i=0;i<list.size();i++)
		{
			String  result=SVMHandle.predict(list.get(i), "svm_data/train_model");
			System.out.println("label "+result);
			
		}
		
		//SVMHandle.crossValidation("svm_data/train_feature_weight_path",5);
	}
	
	public String   predict(String predictData)
	{
		String  result=SVMHandle.predict(predictData, "svm_data/train_model");
		return result;
	}
	
	
	@Test
	public void   predict() throws IOException
	{
		long startTime = System.currentTimeMillis();
		 String[] parg = { "svm_data/test_feature_weight", // 这个是存放测试数据  
	                "svm_data/train_model", // 调用的是训练以后的模型  
	                "svm_data\\out_r.txt" ,
	                "1"}; // 生成的结果的文件的路径  
		 svm_predict p = new svm_predict();  
		 p.main(parg);
		 long endTime = System.currentTimeMillis();
		 System.out.println(endTime - startTime);
	}
	
	public void officialPredict() throws IOException
	{
	     // TODO Auto-generated method stub  
        String[] arg = { "trainfile\\train1.txt", // 存放SVM训练模型用的数据的路径  
                "trainfile\\model_r.txt" }; // 存放SVM通过训练数据训/ //练出来的模型的路径  
  
        String[] parg = { "trainfile\\train2.txt", // 这个是存放测试数据  
                "trainfile\\model_r.txt", // 调用的是训练以后的模型  
                "trainfile\\out_r.txt" }; // 生成的结果的文件的路径  
        System.out.println("........SVM运行开始..........");  
        // 创建一个训练对象  
        svm_train t = new svm_train();  
        // 创建一个预测或者分类的对象  
        svm_predict p = new svm_predict();
        t.main(arg); // 调用  
        p.main(parg); // 调用  
	}
}
