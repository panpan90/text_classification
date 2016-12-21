package com.sohu.mrd.classification.libsvm;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.StringTokenizer;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;

import com.sohu.mrd.classification.utils.FileKit;
/**
 * 调用libSVM api 处理 进行训练和预测
 * @author guopanjin
 */
public class SVMHandle {
    private static Logger LOG = Logger.getLogger(SVMHandle.class);
	public static void main(String[] args) {
		//gernerateMode("svm_files/train_file","svm_files/train_mode");
		String title="日媒:日开始加强在东海海域警戒监视力度";
		InputStream  is=FileKit.Str2Inputstr(title);
		String modelPath="svm_files/train_model";
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		try {
			String predictResult=customPredict(br, modelPath);
			System.out.println(predictResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void gernerateMode(String trainPath,String modePath)
	{
		//生成模型
		svm_train svmTrain=new svm_train();
		String[]  svmTrainAvg={trainPath,modePath};
		try {
			svmTrain.main(svmTrainAvg);
		} catch (Exception e) {
			Log.info("生成模型失败");
			e.printStackTrace();
		}
	}
	/**
	 * 交叉验证
	 * @param trainPath
	 * @param foldNumber
	 */
	public static void  crossValidation(String trainPath,int foldNumber)
	{
		try {
			String[] svmCrossArgs={"-v",foldNumber+"",trainPath};
			svm_train.main(svmCrossArgs);
		} catch (Exception e) {
			Log.info("交叉验证异常");
			e.printStackTrace();
		}
	}
	/**
	 * 预测
	 */
	public static String  predict(String feature_weight,String modelPath)
	{
		String result="";
		try {
			InputStream is=FileKit.Str2Inputstr(feature_weight);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			result=customPredict(br, modelPath);
			LOG.info("result "+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] results=result.split(" |\n",-1);
		String lable=results[3];
		return lable;
	}
	/**
	 * 重新覆写的预测方法
	 * @param input
	 * @param model
	 * @throws IOException
	 */
	public  static String  customPredict(BufferedReader titleReader, String modelPath) throws IOException
	{
		svm_model model = svm.svm_load_model(modelPath);
		StringBuilder  outputSb=new StringBuilder();
		int predict_probability=1;
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		double[] prob_estimates=null;

		if(predict_probability == 1)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
			   svm_type == svm_parameter.NU_SVR)
			{
				svm_predict.info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				int[] labels=new int[nr_class];
				svm.svm_get_labels(model,labels);
				prob_estimates = new double[nr_class];
				outputSb.append("labels");
				for(int j=0;j<nr_class;j++)
					outputSb.append(" "+labels[j]);
			   outputSb.append("\n");
			}
		}
		while(true)
		{
			String line = titleReader.readLine();
			if(line == null) break;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			double target = atof(st.nextToken());
			int m = st.countTokens()/2;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = Integer.parseInt(st.nextToken());
				x[j].value = Double.valueOf(st.nextToken()).doubleValue();
			}

			double v;
			if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
			{
				v = svm.svm_predict_probability(model,x,prob_estimates);
				outputSb.append(v+" ");
				for(int j=0;j<nr_class;j++)
					outputSb.append(prob_estimates[j]+" ");
				outputSb.append("\n");
			}
			else
			{
				v = svm.svm_predict(model,x);
				outputSb.append(v+"\n");
			}

			if(v == target)
				++correct;
			error += (v-target)*(v-target);
			sumv += v;
			sumy += target;
			sumvv += v*v;
			sumyy += target*target;
			sumvy += v*target;
			++total;
		}
		if(svm_type == svm_parameter.EPSILON_SVR ||
		   svm_type == svm_parameter.NU_SVR)
		{
			svm_predict.info("Mean squared error = "+error/total+" (regression)\n");
			svm_predict.info("Squared correlation coefficient = "+
				 ((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
				 ((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+
				 " (regression)\n");
		}
		else
			svm_predict.info("Accuracy = "+(double)correct/total*100+
				 "% ("+correct+"/"+total+") (classification)\n");
		return outputSb.toString();
	}
	
	public static double  atof(String s)
	{
		return   Double.valueOf(s).doubleValue();
	}
	
}
