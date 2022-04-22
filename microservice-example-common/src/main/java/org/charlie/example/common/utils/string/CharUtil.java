package org.charlie.example.common.utils.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtil {

    final static Pattern CHINESE_CHAR_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    final static Pattern ALL_CHINESE_CHAR_PATTERN = Pattern.compile("^[\u4e00-\u9fa5]{1,}$");

    public static boolean isChinsese(String str) {
        Matcher matcher = ALL_CHINESE_CHAR_PATTERN.matcher(str);
        return matcher.find();
    }

    public static boolean isChinsese(char c) {
        Matcher matcher = ALL_CHINESE_CHAR_PATTERN.matcher(c + "");
        return matcher.find();
    }

    public static boolean containsChinsese(String str) {
        Matcher matcher = CHINESE_CHAR_PATTERN.matcher(str);
        return matcher.find();
    }
}
