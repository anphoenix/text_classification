package com.ibm.classification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonObject;
import com.ibm.classification.util.CommonUtil;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class TextClassfication {

	int vnum = 0;
	int wnum = 0;
	
	public svm_node[][] getSourceData(String fileName) throws IOException {
		PrepareData.INSTANCE.processData(fileName);
		HashMap<String, Integer> TotalWordsVector = PrepareData.INSTANCE.getTotalWordsVector();
		ArrayList<HashMap<Integer, Integer>> articleList = PrepareData.INSTANCE.getArticleList();

		vnum = articleList.size();
		wnum = TotalWordsVector.size();

		svm_node[][] datas = new svm_node[vnum][wnum]; // 训练集的向量表
		
		for (int d = 0; d < vnum; d++) {
			svm_node[] tv = new svm_node[wnum];
			for (int i = 0; i < wnum; i++) {
				svm_node tvd = new svm_node();
				tvd.index = i;
				tvd.value = 0;
				tv[i] = tvd;
			}
			for (Entry<Integer, Integer> entry : articleList.get(d).entrySet()) {
				// System.out.println("key:"+entry.getKey()+" value:"+entry.getValue());
				tv[entry.getKey()].value = entry.getValue();
			}
			
				datas[d] = tv;
		}
		return datas;
	}
	public svm_node[][] getTrainData(svm_node[][] datas, int trainNum)
	{
		svm_node[][] data = new svm_node[trainNum][wnum];
		for (int i=0; i<trainNum; i++ )
			data[i] = datas[i];
		return data;
	}
	public svm_node[][] getTestData(svm_node[][] datas, int trainNum)
	{
		svm_node[][] data = new svm_node[vnum-trainNum][wnum];
		for (int i=0; i<vnum-trainNum; i++ )
			data[i] = datas[i+trainNum];
		return data;
	}
	public void train() throws IOException {
		svm_node[][] datas = getSourceData(Config.SOURCE_DATA_PATH);
		CommonUtil.ArrayNormalization(datas);
		svm_node[][] train_data = getTrainData(datas,Config.TRAIN_NUM);
		svm_node[][] test_data = getTestData(datas,Config.TRAIN_NUM);
		
		//CommonUtil.PrintData(train_data);
		
		ArrayList<Double> temp = PrepareData.INSTANCE.getLable();
		double[] train_lables = new double[Config.TRAIN_NUM];
		double[] test_lables = new double[temp.size()-Config.TRAIN_NUM];
		
		for (int j = 0; j < temp.size(); j++)
		{
			if(j < Config.TRAIN_NUM)
			train_lables[j] = temp.get(j);
			else 
			test_lables[j-Config.TRAIN_NUM] =temp.get(j); 
		}

		svm_problem problem = new svm_problem();
		problem.l = Config.TRAIN_NUM; // 向量个数
		problem.x = train_data; // 训练集向量表
		problem.y = train_lables; // 对应的lable数组

		// 定义svm_parameter对象
		svm_parameter param = new svm_parameter();
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.cache_size = 1000;
		param.eps = 0.00001;
		param.C = 2000;
		param.gamma =0.01; 
		// 训练SVM分类模型
		System.out.println(svm.svm_check_parameter(problem, param)); // 如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
		svm_model model = svm.svm_train(problem, param); // svm.svm_train()训练出SVM分类模型
		System.out.println(model.toString());
		int correct =0;
		double predict =0;
		for (int i=0; i<test_data.length; i++)
		{
			predict = svm.svm_predict(model, test_data[i]);
			System.out.println(predict +" : "+test_lables[i]);
			if(predict*test_lables[i]>0)
				correct++;
		}
		
		System.out.println("预测结果："+ (double)correct/test_data.length);
	}

	
	public static void main(String[] args) throws IOException {
		TextClassfication tc = new TextClassfication();
		tc.train();
	}
}
