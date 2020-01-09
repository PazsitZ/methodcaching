package hu.pazsit.methodcaching.cache;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class CacheClassTest {
    private CacheClass cacheClass;

    @Test
    public void testCacheIdFromSameObject() throws NoSuchMethodException {
        Object obj = new Object();
        Method met = obj.getClass().getMethod("equals", Object.class);
        Object[] args = new Object[]{};
        CacheClass.CacheId cacheId = new CacheClass.CacheId(obj, met, args);

        Method met2 = obj.getClass().getMethod("equals", Object.class);
        Object[] args2 = new Object[]{};
        CacheClass.CacheId cacheId2 = new CacheClass.CacheId(obj, met2, args2);

        Assert.assertEquals(cacheId, cacheId2);
    }

    @Test
    public void testCacheIdFromDiffObject() throws NoSuchMethodException {
        Object obj = new Object();
        Method met = obj.getClass().getMethod("equals", Object.class);
        Object[] args = new Object[]{};
        CacheClass.CacheId cacheId = new CacheClass.CacheId(obj, met, args);

        Object obj2 = new Object();
        Method met2 = obj2.getClass().getMethod("equals", Object.class);
        Object[] args2 = new Object[]{};
        CacheClass.CacheId cacheId2 = new CacheClass.CacheId(obj2, met2, args2);

        Assert.assertNotEquals(cacheId, cacheId2);
    }

    @Test
    public void testCacheIdDiffArgs() throws NoSuchMethodException {
        Object obj = new Object();
        Method met = obj.getClass().getMethod("equals", Object.class);
        Object[] args = new Object[]{};
        CacheClass.CacheId cacheId = new CacheClass.CacheId(obj, met, args);

        Method met2 = obj.getClass().getMethod("equals", Object.class);
        Object[] args2 = new Object[]{ new String("asd")};
        CacheClass.CacheId cacheId2 = new CacheClass.CacheId(obj, met2, args2);

        Assert.assertNotEquals(cacheId, cacheId2);
    }
}
