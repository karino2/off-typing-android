package com.livejournal.karino;

import junit.framework.Assert;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextGeneraterTest {

	@Test
	public void testGetCurrent_MultipleCall_shouldReturnSame() {
		TextGenerater tg = TextGenerater.createForJapanese();
		assertEquals(tg.getCurrent(), tg.getCurrent());
	}
	
	@Test
	public void testGetCurrent_moveNext_makeGetCurrentDifferenct() {
		TextGenerater tg = TextGenerater.createForJapanese();
		String firstCur = tg.getCurrent();
		tg.moveNext();
		assertNotSame(firstCur, tg.getCurrent());
	}
	
	@Test
	public void testGetCurrent_getNext_shouldBeDifferent() {
		TextGenerater tg = TextGenerater.createForJapanese();
		assertNotSame(tg.getCurrent(), tg.getNext());
	}	

	@Test
	public void testGetCurrent_getAfterNext_shouldBeDifferent() {
		TextGenerater tg = TextGenerater.createForJapanese();
		assertNotSame(tg.getCurrent(), tg.getAfterNext());
	}	

	@Test
	public void testGetNext_getAfterNext_shouldBeDifferent() {
		TextGenerater tg = TextGenerater.createForJapanese();
		assertNotSame(tg.getNext(), tg.getAfterNext());
	}	
	
	@Test
	public void testIsFinished_initiallyShouldFalse() {
		TextGenerater tg = TextGenerater.createForJapanese();
		assertFalse(tg.isFinished());
	}
	
	@Test
	public void testIsFinished_shouldReturnTrueWhenAllTextDone() {
		TextGenerater tg = TextGenerater.createForJapanese();
		int size = tg.getGameSize();
		for(int i = 0; i < size; i++) {
			assertFalse(tg.isFinished());
			tg.moveNext();
		}
		assertTrue(tg.isFinished());
	}

	@Test
	public void testInsertRetryAtAfterNext() {
		int initialGameSize = 3;
		TextGenerater tg = TextGenerater.createForJapanese(initialGameSize);
		String toBeRetried = tg.getCurrent();
		tg.insertRetry();
		Assert.assertEquals(toBeRetried, tg.getAfterNext());
		Assert.assertEquals(initialGameSize + 1, tg.getGameSize());
	}

	@Test
	public void testInsertNewRetryOnlyAfterMove() {
		int initialGameSize = 3;
		TextGenerater tg = TextGenerater.createForJapanese(initialGameSize);
		assertTrue(tg.canRetry());
		tg.insertRetry();
		assertFalse(tg.canRetry());
		tg.moveNext();
		assertTrue(tg.canRetry());
	}

	@Test
	public void testInsertRetryAtAfterNextAsLast() {
		int initialGameSize = 2;
		TextGenerater tg = TextGenerater.createForJapanese(initialGameSize);
		String toBeRetried = tg.getCurrent();
		tg.insertRetry();
		Assert.assertEquals(toBeRetried, tg.getAfterNext());
		Assert.assertEquals(initialGameSize + 1, tg.getGameSize());
	}

	@Test
	public void testInsertRetryAtNext() {
		int initialGameSize = 1;
		TextGenerater tg = TextGenerater.createForJapanese(initialGameSize);
		String toBeRetried = tg.getCurrent();
		tg.insertRetry();
		Assert.assertEquals(toBeRetried, tg.getNext());
		Assert.assertEquals(initialGameSize + 1, tg.getGameSize());
	}
}
