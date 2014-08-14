package com.ibm.classification.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.ibm.nlp.chinese.tokenizaer.core.Token;
import org.ibm.nlp.chinese.tokenizaer.core.Tokenizer;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;



public class ChineseTraditionalTokenizator {
	public static ChineseTraditionalTokenizator INSTANCE = new ChineseTraditionalTokenizator();
	
	Tokenizer tokenizer = new Tokenizer(true);
	private ChineseTraditionalTokenizator(){
		
	}
	
	public void tokenizeByIK(String line, ArrayList<String> tokens) throws IOException
	{
		StringReader sr=new StringReader(line);
		IKSegmentation iks=new IKSegmentation(sr, true);
		Lexeme lex=null;
		while((lex=iks.next())!=null){
			tokens.add(lex.getLexemeText());
		}
		sr.close();
	}
	public void tokenizeByBlue(String line, ArrayList<String> tokens) throws IOException
	{
		tokenizer.reset();
		tokenizer.setInputStream(line);
		Token token = tokenizer.getNextToken();
		while(token != null){
			tokens.add(token.getString());
			token = tokenizer.getNextToken();
			
		}
		
	}

	public static void main(String[] args) throws IOException {
		//ChineseTraditionalTokenizator ctt = new ChineseTraditionalTokenizator();
		String str="打造一个有效的虚拟社区绝非易事大能是走鸭";
		//System.out.println(ctt.getTokenList(str).toString());
		
		ArrayList<String> tokens = new ArrayList <String>();
		ChineseTraditionalTokenizator.INSTANCE.tokenizeByBlue(str, tokens);
		
		System.out.println(tokens);
		

		
	}
}
