package hu.pazsit.methodcaching.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheClass {
    private static final Logger logger = LoggerFactory.getLogger(CacheClass.class);

    private static final int DEFAULT_CACHE_TIME = 30;
    private static Cache<Object, Object> globalCacher = initCache(CacheType.ACCESS, DEFAULT_CACHE_TIME);

    public enum CacheType {
        ACCESS,
        WRITE
    }

    protected static final class CacheId {
        private final Object targetObj;
        private final String clName;
        private final Method method;
        private final Object[] args;

        public CacheId(Object target, Method method, Object[] args) {
            targetObj = target;
            clName = target.getClass().getCanonicalName();
            this.method = method;
            this.args = args;
        }

        public boolean equals(Object obj) {
            if (obj == null ||getClass() != obj.getClass()) {
                return false;
            }

            final CacheId other = (CacheId) obj;

            return Objects.equals(targetObj, other.targetObj)
                    && Objects.equals(method, other.method)
                    && Objects.deepEquals(args, other.args);
        }

        public int hashCode() {
            return Objects.hash(clName, method.getName(), Arrays.deepHashCode(args));
        }

        public String toString() {
            return clName + "." + method.getName() + Arrays.deepToString(args);
        }
    }

    public static <T> T localCache(Class<T> clInterface, T target, CacheType cType, int cLimit) {
        final Cache<Object, Object> cache = initCache(cType, cLimit);
        return CacheClass.create(clInterface, target, cache);
    }

    public static <T> T globalCache(Class<T> clInterface, T target) {
        return create(clInterface, target, globalCacher);
    }

    public static <T> T create(Class<T> clInterface, final T target, Cache<Object, Object> cacheObject) {
        InvocationHandler proxyInvokeHandler = (proxy, method, args) -> {
            final CacheId argKey = new CacheId(target, method, args);
            Object result = null;

            try {
                result = cacheObject.get(argKey, () -> method.invoke(target, args));
                logger.info("cacheClass for " + clInterface.getName() + "." + argKey + (result == null ? "" : result.hashCode())+ ")");
            } catch (ExecutionException ex) {
                throw ex.getCause();
            }

            return result;
        };

        return (T) Proxy.newProxyInstance(clInterface.getClassLoader(), new Class[] {clInterface}, proxyInvokeHandler);
    }

    public static Cache<Object, Object> initCache(CacheType cType, int cLimit) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

        if (cType == CacheType.ACCESS) {
            cacheBuilder = cacheBuilder.expireAfterAccess(cLimit, TimeUnit.SECONDS);
        } else {
            cacheBuilder = cacheBuilder.expireAfterWrite(cLimit, TimeUnit.SECONDS);
        }

        return cacheBuilder.build();
    }
}
