package hu.pazsit.methodcaching.controller;

import hu.pazsit.methodcaching.controller.StringCapController;
import org.junit.Assert;
import org.junit.Test;

public class EntrypointTest {
    private static final String BASE_STRING = "the test input String.";
    private StringCapController controller = new StringCapController();

    @Test
    public void testCapString() {
        String result = controller.getCapString(BASE_STRING);
        String result2 = controller.getCapString(BASE_STRING);

        Assert.assertEquals(result, result2);
        Assert.assertFalse(result == result2);
    }

    @Test
    public void testCapStringCached() {
        String result = controller.getCapStringCached(BASE_STRING);
        String result2 = controller.getCapStringCached(BASE_STRING);

        Assert.assertEquals(result, result2);
        Assert.assertTrue(result == result2);
    }
}
