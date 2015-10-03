package com.livejournal.karino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class TextGenerater {
	static public class PhraseList {
		private final String[] arrray;

		public PhraseList(String[] list) {
			arrray = list;
		}

		public ArrayList<String> toList() {
			return new ArrayList<>(Arrays.asList(arrray));
		}
	}

	public static final PhraseList ENGLISH_PHRASES = new PhraseList(new String[]{
		"Unix", "Linux", "UnitTest", "Wikipedia",
				"SharePoint", "Windows", "Mac OS", "OpenCL", "Android",
				"eclipse", "Java", "FireAlpaca", "OffTyping",
				"When I was young,", "This is old.",
				"Are you ready?", "I'm lady.",
				"It's show time!", "Go my way!!", "highway"
	});

	public static final PhraseList JAPANESE_PHRASES = new PhraseList(new String[]{
		"さいきんは", "さむくなってきました",
				"ちきゅうおんだんか", "きゅうれき",
				"ぷろぐらみんぐ", "ちゅうしゅうのめいげつ",
				"あきのよなが",
				"つうきんでんしゃ", "かんえつじどうしゃどう",
				"つづく。", "したづつみをうつ",
				"おふぴーくつうきん", "そめいよしの",
				"ことしのすまほ", "ふりっくにゅうりょく", "れんしゅうあぷり",
				"あつすぎる", "おだやかなかぜ", "すずしくなってきた",
				"かくていしんこく",
				"やくしま", "じょうもんすぎ", "とびしまかいどう",
				"せとおおはし", "のとはんとう", "あわじしま", "ほっかいどう",
				"しんかんせん", "こうべぎゅう", "おはようございます", "さようなら", "おやすみなさい",
				"さどがしま", "ふたござりゅうせいぐん", "ふゆのせいざ",
				"なつのだいさんかっけい", "はくちょうざ", "ぺるせうすざ", "ぷらねたりうむ",
				"おつかれさまでした。", "あんどろいどすたでぃお",
				"にいがたけんみん", "ほくりくしんかんせん"
	});

	private static final int DEFAULT_GAME_SIZE = 10;

	final ArrayList<String> texts;
	int currentIndex;
	int oneGameSize;
	boolean retryInserted = false;

	private TextGenerater(PhraseList phrases, int gameSize)
	{
		currentIndex = 0;
		oneGameSize = gameSize;
		texts = phrases.toList();
		Collections.shuffle(texts, new Random());
	}

	public TextGenerater(PhraseList phrases) {
		this(phrases, DEFAULT_GAME_SIZE);
	}

	// These are for testing.

	public static TextGenerater createForJapanese() {
		return new TextGenerater(JAPANESE_PHRASES);
	}

	public static TextGenerater createForJapanese(int gameSize) {
		return new TextGenerater(JAPANESE_PHRASES, gameSize);
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
		if(isFinished())
			return;
		currentIndex++;
		retryInserted = false;
	}

	public void insertRetry() {
		if (retryInserted)
			return;

		if (currentIndex + 2 <= oneGameSize) {
			texts.add(currentIndex + 2, getCurrent());
		} else {
			texts.add(currentIndex + 1, getCurrent());
		}

		oneGameSize++;
		retryInserted = true;
	}

	public boolean canRetry() { return !retryInserted; }
	public boolean isFinished()
	{
		return currentIndex >= oneGameSize;
	}
}
