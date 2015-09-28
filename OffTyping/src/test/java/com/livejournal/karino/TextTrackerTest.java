package com.livejournal.karino;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TextTrackerTest {

    private TextTracker target;

    @Before
    public void setup() {
        target = new TextTracker("Hello");
    }

    @Test
    public void testTypingWell() {
        Assert.assertEquals(TextTracker.TypeResult.DOING_WELL, target.reportTypedText("Hel"));
        Assert.assertEquals(TextTracker.TypeResult.DOING_WELL, target.reportTypedText("Hell"));
    }

    @Test
    public void testTypingDone() {
        Assert.assertEquals(TextTracker.TypeResult.DOING_WELL, target.reportTypedText("Hell"));
        Assert.assertEquals(TextTracker.TypeResult.DONE, target.reportTypedText("Hello"));
    }

    @Test
    public void testReplaceDoesntMakePending() {
        Assert.assertEquals(TextTracker.TypeResult.DOING_WELL, target.reportTypedText("Hel"));
        Assert.assertEquals(TextTracker.TypeResult.PENDING, target.reportTypedText("Heli"));
        Assert.assertEquals(TextTracker.TypeResult.DOING_WELL, target.reportTypedText("Hell"));
    }

    @Test
    public void testRemovalMakesMistyping() {
        Assert.assertEquals(TextTracker.TypeResult.DOING_WELL, target.reportTypedText("Hel"));
        Assert.assertEquals(TextTracker.TypeResult.PENDING, target.reportTypedText("Heli"));
        Assert.assertEquals(TextTracker.TypeResult.MISSTYPING, target.reportTypedText("Helio"));
    }

    @Test
    public void testMisstypingCount() {
        target.reportTypedText("Helaa");
        Assert.assertEquals(target.getMisstypingCount(), 0);
        target.reportTypedText("Hela");
        Assert.assertEquals(target.getMisstypingCount(), 1);
        target.reportTypedText("Hel");
        Assert.assertEquals(target.getMisstypingCount(), 2);
        target.reportTypedText("He");
        Assert.assertEquals(target.getMisstypingCount(), 2);
        target.reportTypedText("Hel");
        Assert.assertEquals(target.getMisstypingCount(), 2);
    }
}
