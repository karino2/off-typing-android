package com.livejournal.karino;

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


}
