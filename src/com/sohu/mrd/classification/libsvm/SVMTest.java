package com.sohu.mrd.classification.libsvm;

public class SVMTest {
	public static void main(String[] args) {
		try {
			//String[] arg={"-v","10","svm_test/train_file1"};
			String[] arg={"-t","2","-v","5","-c","1024","svm_files/train_file"};
			svm_train.main(arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
