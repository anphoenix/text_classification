package com.ibm.classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.ibm.classification.util.ChineseStopWords;
import com.ibm.classification.util.ChineseTraditionalTokenizator;

public class Preprocess {

	private HashMap<String, Integer> totalVector= new HashMap<String, Integer>();
	private ArrayList<JsonObject> totalVectorNum= new ArrayList<JsonObject>();
	private ArrayList<HashMap<Integer, Integer>> articleVectorList= new ArrayList<HashMap<Integer, Integer>>();
	private ArrayList<Double> lable = new ArrayList<Double>();
	
	public ArrayList<JsonObject> getTotalVectorNum() {
		return totalVectorNum;
	}
	public ArrayList<Double> getLable() {
		return lable;
	}

	public static Preprocess INSTANCE = new Preprocess();
	private Preprocess()
	{
	}
	public HashMap<String, Integer> getTotalVector() {
		return totalVector;
	}
	public ArrayList<HashMap<Integer, Integer>> getArticleVector() {
		return articleVectorList;
	}
	
	private JsonArray getOriginalData(String fileName)
	{
		JsonArray array = null;
		try {
				JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(new File(fileName)), "utf-8"));
				JsonParser parser = new JsonParser();
			    array = (JsonArray)parser.parse(reader);		
			return array;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void processData(String fileName) throws IOException
	{
//		JsonArray originalData = getOriginalData(fileName);
//		for (JsonElement je : originalData)
//		{
		 JsonParser parser = new JsonParser();
		 BufferedReader dr=new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
         String line = dr.readLine();
         while(line != null) {
        	JsonElement element = parser.parse(line);
			JsonObject article = element.getAsJsonObject();
			StringBuilder content = new StringBuilder();
			content.append(article.get("title").getAsString());
			if(article.has("reply_title"))
			{
				content.append(article.get("title").getAsString());
			}
			if(article.has("reply_content"))
			{
				content.append(article.get("reply_content").getAsString());
			}
			
			ArrayList<String> tokens = new ArrayList <String>();
			try {
				ChineseTraditionalTokenizator.INSTANCE.tokenizeByBlue(content.toString(), tokens);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HashMap<Integer, Integer> articleVector = new HashMap<Integer, Integer>();
			for (String word : tokens)
			{
				if(ChineseStopWords.INSTANCE.isStopWord(word)) continue;
				Integer index = -1;
				if(!totalVector.containsKey(word))
				{
					index = totalVector.size();
					totalVector.put(word, index);
					JsonObject jo = new JsonObject();
					jo.addProperty("word", word);
					jo.addProperty("num", 1);
					totalVectorNum.add(jo);
				}
				else {
					index = totalVector.get(word);
					totalVectorNum.get(index).addProperty("num", totalVectorNum.get(index).get("num").getAsInt()+1);
				}
				if(articleVector.containsKey(index))
				{
					articleVector.put(index, articleVector.get(index)+1);
				}
				else 
				{
					articleVector.put(index, 1);
				}
			}
			articleVectorList.add(articleVector);
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
