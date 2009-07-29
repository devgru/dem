package test.quanta;

import dem.quanta.Log;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class LogTest {
    @Test
    public void testBasicOps() {
        String offset = Log.offset("");
        String doubleOffset = Log.offset("", 2);
        Assert.assertTrue((offset + offset).equals(doubleOffset));

        LinkedList<Object> objectLinkedList = new LinkedList<Object>();
        objectLinkedList.add(new Object());
        objectLinkedList.add(null);
        Log.inline(objectLinkedList);
    }

    @Test(expected = AssertionError.class)
    public void testFailOnNull() {
        String offset = Log.offset(null);
    }

    @Test(expected = AssertionError.class)
    public void testFailOnNull2() {
        String offset = Log.offset(null, 1);
    }

    @Test(expected = AssertionError.class)
    public void testInlineFail() {
        Log.inline(null);
    }
}
