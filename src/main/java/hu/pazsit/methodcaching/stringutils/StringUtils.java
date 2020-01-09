package hu.pazsit.methodcaching.stringutils;

import org.springframework.stereotype.Component;

public class StringUtils implements CustomStringUtils {
    public String capitalize(String str) {
        return org.springframework.util.StringUtils.capitalize(str);
    }
}
