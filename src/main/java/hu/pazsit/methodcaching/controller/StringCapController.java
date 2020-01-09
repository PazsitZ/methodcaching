package hu.pazsit.methodcaching.controller;

import hu.pazsit.methodcaching.cache.CacheClass;
import hu.pazsit.methodcaching.stringutils.CustomStringUtils;
import hu.pazsit.methodcaching.stringutils.StringUtils;
import org.springframework.stereotype.Controller;

import javax.websocket.server.PathParam;

@Controller
public class StringCapController {
    private CustomStringUtils stringUtil;
    private CustomStringUtils stringUtilCached;
    private CustomStringUtils stringUtilGloballyCached;

    public StringCapController() {
        stringUtil = new StringUtils();
        postConstruct(stringUtil);
    }

    public StringCapController(CustomStringUtils stringUtil) {
        this.stringUtil = stringUtil;
        postConstruct(stringUtil);
    }

    private void postConstruct(CustomStringUtils stringUtil) {
        stringUtilCached = CacheClass.localCache(CustomStringUtils.class, this.stringUtil, CacheClass.CacheType.ACCESS, 30);
        stringUtilGloballyCached = CacheClass.globalCache(CustomStringUtils.class, this.stringUtil);
    }


    public String getCapString(@PathParam("input") String inputStr) {
        return stringUtil.capitalize(inputStr);
    }

    public String getCapStringCached(@PathParam("input") String inputStr) {
        return stringUtilCached.capitalize(inputStr);
    }

    public String getCapStringGloballyCached(@PathParam("input") String inputStr) {
        return stringUtilGloballyCached.capitalize(inputStr);
    }
}
