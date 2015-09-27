package com.livejournal.karino;

import java.util.ArrayList;
import java.util.Random;

public class TextGenerater {
	ArrayList<String> texts;
	int currentIndex;
	int oneGameSize = 10;
	public TextGenerater()
	{
		initializeAsJapanese();
		shuffle();
		
		currentIndex = 0;
	}
	public void initializeAsEnglish() {
		String[] arr = new String[]{
				"Unix", "Linux", "UnitTest","Wikipedia",
				"SharePoint", "Windows", "Mac OS","OpenCL", "Android", 
				"eclipse", "Java", "FireAlpaca", "OffTyping",
				"When I was young,", "This is old.",
				"Are you ready?", "I'm lady.",
				"It's show time!", "Go my way!!", "highway"
		};
		initializeTexts(arr);
	}
	public void initializeAsJapanese() {
		String[] arr = new String[]{
                "さいきんは","さむくなってきました",
                "ちきゅうおんだんか", "きゅうれき",
                "ぷろぐらみんぐ", "いいね",
                "おおがたのたいふう", 
                "つうきんでんしゃ", "しゅうきゅうふつか",
                "つづく。", "したづつみをうつ",
                "おふぴーくつうきん", "そめいよしの",
                "ことしのすまほ", "ふりっくにゅうりょく", "れんしゅうあぷり",
                "あつすぎる", "おだやかなかぜ", "すずしくなってきた",
                "かくていしんこく",
                "やくしま", "じょうもんすぎ", "とびしまかいどう",
                "せとおおはし", "しまなみかいどう", "あわじしま",
                "しんかんせん", "こうべぎゅう", "おはようございます", "さようなら","おやすみなさい",
                "さどがしま", "ふたござりゅうせいぐん", "ふゆのせいざ",
                "なつのだいさんかっけい", "はくちょうざ",
                "おつかれさまでした。", "あんどろいどすたでぃお",
                "さいたまけんみん",
		};
		initializeTexts(arr);
	}
	void initializeTexts(String[] arr) {
		texts = new ArrayList<String>();
		for(String st : arr)
		{
			texts.add(st);
		}
	}
	private void shuffle() {
		int size = texts.size();
		Random rand = new Random();
		for(int i = 0; i < size; i++)
		{
			int nextInt = rand.nextInt(size);
			swap(i, nextInt);
		}
	}
	
	private void swap(int i, int j) {
		String tmp = texts.get(i);
		texts.set(i, texts.get(j));
		texts.set(j, tmp);
	}
	public String getCurrent()
	{
		return getAt(currentIndex);
	}
	
	public String getNext()
	{
		return getAt(currentIndex+1);
	}
	
	public String getAfterNext()
	{
		return getAt(currentIndex+2);
	}
	
	public String getAt(int i)
	{
		if(i < oneGameSize)
			return texts.get(i);
		return "";
	}
	
	public void reset()
	{
		shuffle();
		currentIndex = 0;
	}
	
	public int getTotalCharacterNum()
	{
		int sum = 0;
		for(int i = 0; i < oneGameSize; i++){
			String st = texts.get(i);
			sum += st.length();
		}
		return sum;
	}
	
	public void moveNext()
	{
		if(!isFinished())
			currentIndex++;
	}
	
	public boolean isFinished()
	{
		return currentIndex >= oneGameSize;
	}
}
