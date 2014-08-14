package com.ibm.classification.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

import com.ibm.classification.Config;

public class ChineseStopWords {
	private static HashSet<String> SWSet;
	public static ChineseStopWords INSTANCE = new ChineseStopWords();
	
	private ChineseStopWords()  {
		File stopWords = new File(Config.CH_STOPWORDS_FILE); //read the stop-word library
		SWSet = new HashSet<String>();
		try {
				Scanner scan = new Scanner(stopWords);
				while (scan.hasNextLine()) 
				{
					SWSet.add(scan.nextLine()); //put all the stop words into an hashset
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isStopWord(String word) {
		return SWSet.contains(word);
	}

}
