package hu.pazsit.methodcaching.controller;

import hu.pazsit.methodcaching.stringutils.CustomStringUtils;
import hu.pazsit.methodcaching.stringutils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class GlobalCacheTest {
    private static final String BASE_STRING = "the test input String.";
    private CustomStringUtils stringUtil = new StringUtils();
    private StringCapController controller = new StringCapController(stringUtil);
    private StringCap2Controller controller2 = new StringCap2Controller(stringUtil);
    private StringCap2Controller controller3 = new StringCap2Controller();

    @Test
    public void testCapStringGloballyCached() {
        String result = controller.getCapStringGloballyCached(BASE_STRING);
        String result2 = controller2.getCapStringGloballyCached(BASE_STRING);

        Assert.assertEquals(result, result2);
        Assert.assertTrue(result == result2);
    }


    @Test
    public void testCapStringNatives() {
        String result = controller.getCapString(BASE_STRING);
        String result2 = controller2.getCapString(BASE_STRING);

        Assert.assertEquals(result, result2);
        Assert.assertFalse(result == result2);
    }

    @Test
    public void testCapStringLocalCaches() {
        String result = controller.getCapStringCached(BASE_STRING);
        String result2 = controller2.getCapString(BASE_STRING);

        Assert.assertEquals(result, result2);
        Assert.assertFalse(result == result2);
    }

    @Test
    public void testCapStringCachedGlobalVsLocal() {
        String result = controller.getCapStringCached(BASE_STRING);
        String result2 = controller.getCapStringGloballyCached(BASE_STRING);

        Assert.assertEquals(result, result2);
        Assert.assertFalse(result == result2);
    }
}
