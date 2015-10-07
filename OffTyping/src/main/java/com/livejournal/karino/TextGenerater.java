package com.livejournal.karino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TextGenerater {
	static public class PhraseList {
		private final String[] arrray;

		public PhraseList(String[] list) {
			arrray = list;
		}

		public ArrayList<String> toList() {
			return new ArrayList<>(Arrays.asList(arrray));
		}

		public ArrayList<String> toShuffledSublist(int size) {
			ArrayList shuffled = toList();
			Collections.shuffle(shuffled, new Random());
			return new ArrayList<>(shuffled.subList(0, size));
		}

		public ArrayList<String> toShuffledSublistWith(int originalSize, Set<String> additions) {
			// This allows duplication of the same entry.
			ArrayList result = toShuffledSublist(originalSize);
			result.addAll(additions);
			Collections.shuffle(result);
			return result;
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

	final Set<String> retriedPhrases = new HashSet<>();
	final ArrayList<String> texts;
	int currentIndex;
	boolean retryInserted = false;

	public TextGenerater(PhraseList phrases, int gameSize, Set<String> additional)
	{
		currentIndex = 0;
		texts = phrases.toShuffledSublistWith(gameSize - additional.size(), additional);
	}

	public TextGenerater(PhraseList phrases, Set<String> additional) {
		this(phrases, DEFAULT_GAME_SIZE, additional);
	}

	public TextGenerater(PhraseList phrases) {
		this(phrases, DEFAULT_GAME_SIZE, Collections.<String>emptySet());
	}

	// These are for testing.

	public static TextGenerater createForJapanese() {
		return new TextGenerater(JAPANESE_PHRASES);
	}

	public static TextGenerater createForJapanese(int gameSize) {
		return new TextGenerater(JAPANESE_PHRASES, gameSize, Collections.<String>emptySet());
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
		if(i < texts.size())
			return texts.get(i);
		return "";
	}

	public int getGameSize() {
		return texts.size();
	}

	public int getTotalCharacterNum()
	{
		int sum = 0;
		for(int i = 0; i < texts.size(); i++){
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
		retryInserted = true;

		int retryIndex = currentIndex + 2 <= texts.size() ? currentIndex + 2 : currentIndex + 1;
		texts.add(retryIndex, getCurrent());
		retriedPhrases.add(getCurrent());
	}

	public boolean canRetry() {
		return !retryInserted;
	}

	public boolean isFinished()  {
		return currentIndex >= texts.size();
	}
}
