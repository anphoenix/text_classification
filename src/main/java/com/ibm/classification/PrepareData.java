package com.ibm.classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.classification.util.ChineseStopWords;
import com.ibm.classification.util.ChineseTraditionalTokenizator;

public class PrepareData {

	public static PrepareData INSTANCE = new PrepareData();
	private HashMap<String, Integer> totalWordsVector= new HashMap<String, Integer>();
	private ArrayList<HashMap<Integer, Integer>> articleList= new ArrayList<HashMap<Integer, Integer>>();
	private ArrayList<Double> lable = new ArrayList<Double>();

	
	private PrepareData(){
		loadTotalWordsVector(Config.FEATURE_VECTOR);
	}
	public ArrayList<Double> getLable() {
		return lable;
	}

	public HashMap<String, Integer> getTotalWordsVector() {
		return totalWordsVector;
	}
	public ArrayList<HashMap<Integer, Integer>> getArticleList() {
		return articleList;
	}
	
	public int loadTotalWordsVector(String fileName)
	{
		int index = 0;
		BufferedReader dr;
		try {
			dr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
			String line = dr.readLine();
	        while(line != null) {
	        	totalWordsVector.put(line, index);
	        	index++;
	        	line = dr.readLine();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return index;
	}
	
	public String getArticleContent(JsonObject article){
		StringBuilder content = new StringBuilder();
		content.append(article.get(Config.SOURCE_INFO_TITLE).getAsString());
		if(article.has(Config.SOURCE_INFO_REPLY_TITLE)){
			content.append(article.get(Config.SOURCE_INFO_REPLY_TITLE).getAsString());
		}
		if(article.has(Config.SOURCE_INFO_REPLY_CONTENT)){
			content.append(article.get(Config.SOURCE_INFO_REPLY_CONTENT).getAsString());
		}
		if(article.has(Config.SOURCE_INFO_DESCRIBE)){
			content.append(article.get(Config.SOURCE_INFO_DESCRIBE).getAsString());
		}
		return content.toString();
	}
	
	public void processData(String fileName) throws IOException
	{
		 JsonParser parser = new JsonParser();
		 BufferedReader dr=new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
         String line = dr.readLine();
         while(line != null) {
        	JsonElement element = parser.parse(line);
			JsonObject article = element.getAsJsonObject();
			
			String articleContent = getArticleContent(article);
			
			ArrayList<String> tokens = new ArrayList <String>();
			ChineseTraditionalTokenizator.INSTANCE.tokenizeByBlue(articleContent, tokens);
			
			HashMap<Integer, Integer> articleVector = new HashMap<Integer, Integer>();
			for (String word : tokens)
			{
				if(ChineseStopWords.INSTANCE.isStopWord(word)) continue;
				Integer index = -1;
				if(totalWordsVector.containsKey(word)){
					index = totalWordsVector.get(word);
					
					if(articleVector.containsKey(index)){
						articleVector.put(index, articleVector.get(index)+1);
					}
					else 
					{
						articleVector.put(index, 1);
					}
				}
			}
			articleList.add(articleVector);
			if(article.has("valid"))
				lable.add(-1.0);
			else lable.add(1.0);
			line = dr.readLine();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
