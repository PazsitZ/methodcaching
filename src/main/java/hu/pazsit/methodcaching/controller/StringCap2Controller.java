package hu.pazsit.methodcaching.controller;

import hu.pazsit.methodcaching.cache.CacheClass;
import hu.pazsit.methodcaching.stringutils.CustomStringUtils;
import hu.pazsit.methodcaching.stringutils.StringUtils;
import org.springframework.stereotype.Controller;

import javax.websocket.server.PathParam;

@Controller
public class StringCap2Controller {
    private CustomStringUtils stringUtil;
    private CustomStringUtils stringUtilGloballyCached;

    public StringCap2Controller() {
        stringUtil = new StringUtils();
        stringUtilGloballyCached = CacheClass.globalCache(CustomStringUtils.class, stringUtil);
    }

    public StringCap2Controller(CustomStringUtils stringUtil) {
        this.stringUtil = stringUtil;
        stringUtilGloballyCached = CacheClass.globalCache(CustomStringUtils.class, stringUtil);
    }


    public String getCapString(@PathParam("input") String inputStr) {
        return stringUtil.capitalize(inputStr);
    }

    public String getCapStringGloballyCached(@PathParam("input") String inputStr) {
        return stringUtilGloballyCached.capitalize(inputStr);
    }
}
