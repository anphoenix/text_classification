package com.ibm.classification.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.JsonObject;
import com.ibm.classification.Preprocess;

public class WordStatistics {

	public static void getWordStatistics() {
		ArrayList<JsonObject> totolVectorNum = Preprocess.INSTANCE.getTotalVectorNum();

		Collections.sort(totolVectorNum, new SortByNum());

		System.out.println("size: " + totolVectorNum.size());
		for (int i = 0; i < totolVectorNum.size(); i++)
			CommonUtil.saveContent(totolVectorNum.get(i).get("word").getAsString()+ "\n", "E:/test4java/words.txt");
	}

}

class SortByNum implements Comparator {
	public int compare(Object o1, Object o2) {
		JsonObject w1 = (JsonObject) o1;
		JsonObject w2 = (JsonObject) o2;
		if (w1.get("num").getAsInt() > w2.get("num").getAsInt())
			return -1;
		else if (w1.get("num").getAsInt() < w2.get("num").getAsInt())
			return 1;
		return 0;
	}
}