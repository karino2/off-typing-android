package com.livejournal.karino;

import junit.framework.Assert;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextGeneraterTest {
	TextGenerater tg = new TextGenerater();

	@Test
	public void testGetCurrent_MultipleCall_shouldReturnSame() {
		assertEquals(tg.getCurrent(), tg.getCurrent());
	}
	
	@Test
	public void testGetCurrent_moveNext_makeGetCurrentDifferenct() {
		String firstCur = tg.getCurrent();
		tg.moveNext();
		assertNotSame(firstCur, tg.getCurrent());
	}
	
	@Test
	public void testGetCurrent_getNext_shouldBeDifferent() {
		assertNotSame(tg.getCurrent(), tg.getNext());
	}	

	@Test
	public void testGetCurrent_getAfterNext_shouldBeDifferent() {
		assertNotSame(tg.getCurrent(), tg.getAfterNext());
	}	

	@Test
	public void testGetNext_getAfterNext_shouldBeDifferent() {
		assertNotSame(tg.getNext(), tg.getAfterNext());
	}	
	
	@Test
	public void testIsFinished_initiallyShouldFalse() {
		assertFalse(tg.isFinished());
	}
	
	@Test
	public void testIsFinished_shouldReturnTrueWhenAllTextDone() {
		int size = tg.oneGameSize;
		for(int i = 0; i < size; i++) {
			assertFalse(tg.isFinished());
			tg.moveNext();
		}
		assertTrue(tg.isFinished());
	}

	@Test
	public void testInsertRetryAtAfterNext() {
		int initialGameSize = 3;
		tg.oneGameSize = initialGameSize;
		tg.initializeAsJapanese();
		String toBeRetried = tg.getCurrent();
		tg.insertRetry();
		Assert.assertEquals(toBeRetried, tg.getAfterNext());
		Assert.assertEquals(initialGameSize + 1, tg.oneGameSize);
	}

	@Test
	public void testInsertNewRetryOnlyAfterMove() {
		int initialGameSize = 3;
		tg.oneGameSize = initialGameSize;
		tg.initializeAsJapanese();
		assertTrue(tg.canRetry());
		tg.insertRetry();
		assertFalse(tg.canRetry());
		tg.moveNext();
		assertTrue(tg.canRetry());
	}

	@Test
	public void testInsertRetryAtAfterNextAsLast() {
		int initialGameSize = 2;
		tg.oneGameSize = initialGameSize;
		tg.initializeAsJapanese();
		String toBeRetried = tg.getCurrent();
		tg.insertRetry();
		Assert.assertEquals(toBeRetried, tg.getAfterNext());
		Assert.assertEquals(initialGameSize + 1, tg.oneGameSize);
	}

	@Test
	public void testInsertRetryAtNext() {
		int initialGameSize = 1;
		tg.oneGameSize = initialGameSize;
		tg.initializeAsJapanese();
		String toBeRetried = tg.getCurrent();
		tg.insertRetry();
		Assert.assertEquals(toBeRetried, tg.getNext());
		Assert.assertEquals(initialGameSize + 1, tg.oneGameSize);
	}
}
