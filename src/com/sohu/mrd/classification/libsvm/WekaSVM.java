package com.sohu.mrd.classification.libsvm;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import weka.classifiers.CostMatrix;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
/**
 * @author Jin Guopan
 * @creation 2017年1月9日 //
 *           这里需要注意的一点是，当你创建一个Instance时，必须要为之指定Attribute，如何创建instance之前的一篇博客有讲过
 *           ，在此不在赘述。 // 如果想创建一个新的Instances又不想输入属性，可以直接从已有的数据直接获取Attribute信息，方法是
 *           // DataSource source = new DataSource("/some/where/data.arff"); //
 *           Instances A = source.getDataSet();
 */
public class WekaSVM {
	private static final Logger LOG = Logger.getLogger(WekaSVM.class);
	public static void main(String[] args) {
		LibSVM libSVM = new LibSVM();
		String[] options = { "-S", "0", "-k", "0", "-C", "3" };
		try {
			FileInputStream fis = new FileInputStream("data/train.arff");
		} catch (Exception e) {
		}
	}
	/**
	 * 训练并保存模型
	 */
	public static void train(String trainArffPath, String modelPath, String[] options) {
		ArffLoader arffLoader = new ArffLoader();
		try {
			arffLoader.setSource(new File(trainArffPath));
			Instances trainInstances = arffLoader.getDataSet();
			trainInstances.setClassIndex(trainInstances.numAttributes() - 1);// 设置class所在的列
			LibSVM libSVM = new LibSVM();
			libSVM.setOptions(options); // 设置调优参数
			libSVM.buildClassifier(trainInstances);
			saveModelByKit(libSVM, modelPath);
		} catch (IOException e) {
			LOG.error("训练模型异常 ",e);
		} catch (Exception e) {
			LOG.error("训练模型异常 ",e);
		}
	}
	/**
	 * 交叉验证
	 * @param libSVM
	 * @param trainInstances
	 */
	public static void crossPredict(String modelPath ,Instances trainInstances) {
		try {
			LibSVM  libsvm=loadModelBykit(modelPath);
			Evaluation eva = new Evaluation(trainInstances);
			eva.crossValidateModel(libsvm, trainInstances, 10, new Random());
			LOG.info("输出总结信息  " + eva.toSummaryString());
			LOG.info("输出分类详细信息 " + eva.toClassDetailsString());
			LOG.info("输出分类的混淆矩阵  " + eva.toMatrixString());
		} catch (Exception e) {
			LOG.error("交叉验证出现异常  ", e);
		}
	}
	
	/**
	 * 批量预测并计算相应指标
	 */
	public static void predictByBatch(String testArffPath,String modelPath )
	{
		ArffLoader arffLoader = new ArffLoader();
		try {
			arffLoader.setSource(new File(testArffPath));
			Instances testInstances = arffLoader.getDataSet();
			testInstances.setClassIndex(testInstances.numAttributes() - 1);// 设置class所在的列
			LibSVM  libsvm=loadModelBykit(modelPath);
			int testNumber=testInstances.numInstances();
			int posRightCount=0;
			int posCount=0;
			for(int i=0;i<testNumber;i++)
			{
				double predictResult=libsvm.classifyInstance(testInstances.instance(i));
				double realResult=testInstances.instance(i).classValue();
				if(predictResult==realResult&&predictResult==1) // 默认1 代表色情和广告，代表的是正样本
				{
					//如果预测结果和测试集的结果相同且预测为色情
					posRightCount++;
				}
				//本来正样本的个数
				if(testInstances.instance(i).classValue()==1)
				{
					posCount++;
				}
				LOG.info("预测结果 "+predictResult);
				LOG.info("真实值 "+realResult);
				LOG.info("该条测试数据的内容为 "+testInstances.instance(i));
			}
			double precise=(double)posRightCount/testNumber;//准确率
			double recall=(double)posRightCount/posCount;//召回率
			double F1=2*precise*recall/(precise+recall);//F1值
		    LOG.info("被分类为1的个数 posRightCount "+posRightCount);
		    LOG.info("测试集中1的总个数posCount "+posCount);
			LOG.info("准确率 precise "+precise);
			LOG.info("召回率 recall "+recall);
			LOG.info("F1值 "+F1);
		} catch (IOException e) {
			LOG.error("批量预测异常 ",e);
		} catch (Exception e) {
			LOG.error("批量预测异常 ",e);
		}
	}
	/**
	 * 线上实时单个预测
	 */
	public static Double  predictOnlineSigle(String modelPath,Instance instance)
	{
		LibSVM  libsvm=loadModelBykit(modelPath);
		try {
			return libsvm.classifyInstance(instance);
		} catch (Exception e) {
			LOG.error("线上实时测试异常",e);
		}
		return null;
	}
	/**
	 * 序列号模型，把训练好的模型进行保存
	 * 
	 * @param libSVM
	 * @param path
	 */
	public static void saveModel(LibSVM libSVM, String path) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(libSVM);
			oos.flush();
		} catch (FileNotFoundException e) {
			LOG.error("文件不存在 ", e);
		} catch (IOException e) {
			LOG.error("序列化模型出错 ", e);
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				LOG.error("关闭流异常 ", e);
			}
		}
	}

	public static void saveModelByKit(LibSVM libSVM, String path) {
		try {
			SerializationHelper.write(path, libSVM);// 参数一为模型保存文件，classifier4为要保存的模型
		} catch (Exception e) {
			LOG.error("保存模型异常 ", e);
		}

	}

	public static LibSVM loadModelBykit(String path) {
		try {
			LibSVM libsvm = (LibSVM) SerializationHelper.read(path);
			return libsvm;
		} catch (Exception e) {
			LOG.error("加载模型异常 " + e);
		}
		return null;
	}

	/**
	 * 重新加载模型，即对模型进行反序列化
	 * 
	 * @param path
	 * @return
	 */
	public static LibSVM loadModel(String path) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			LibSVM libSVM = (LibSVM) ois.readObject();
			return libSVM;
		} catch (FileNotFoundException e) {
			LOG.error("文件不存在 ", e);
		} catch (IOException e) {
			LOG.error("反序列化模型出错 ", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				LOG.error("关闭流异常 ", e);
			}
		}
		return null;
	}

}
