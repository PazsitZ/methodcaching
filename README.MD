# Class - Method Caching
Example of caching class methods
uses Guava cache, but that's just an otpional implementation

# Features:
- types:
    - local (uses individual cache, affects only the present instance)
    - global (uses a common caching object the same objects sharing the cache pool across the application (classloading context))
- guava specific options:
    - ACCESS or WRITE level caching
    - cache time limit

```java
  CustomStringUtils localStringUtil = CacheClass.localCache(CustomStringUtils.class, this.stringUtil, CacheClass.CacheType.ACCESS, 30);
  CustomStringUtils globalStringUtil = CacheClass.globalCache(CustomStringUtils.class, stringUtil)
```

## Jar package available from:

http://pazsitz.hu/repo/hu/pazsitz/methodcaching/0.0.1-SNAPSHOT/methodcaching-0.0.1-SNAPSHOT.jar
