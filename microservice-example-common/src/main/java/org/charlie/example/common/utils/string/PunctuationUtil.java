package org.charlie.example.common.utils.string;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Set;

public class PunctuationUtil {

    final static Set<String> ASCII_PUNC_SET = Sets.newHashSet("[", "]", "!", "!!", "!?", "\"", "#", "##", "###", "$", "%", "&", "'", "''", "(", ")", "*", "+", ",",
            "-", "--", "/", "//", ":", "::", ";", "<", "=", ">", ">>", "?", "?", "??", "@", "[", "\\", "]", "^", "_", "±", "·", "×");

    final static Set<String> CHINESE_PUNC_SET = Sets.newHashSet("“", "”", "、", "。", "〈", "〉", "《", "》", "」", "『", "』", "【", "】", "〔", "〕", "︿", "！", "＃", "＄", "％", "＆", "＇", "（", "）",
            "＊", "＋", "，", "－", "．", "／", "：", "；", "＜", "＝", "＞", "？", "＠", "［", "［］", "］", "＿", "｛", "｜", "｝", "～", "￥", ".", "{",
            "}", "~", "‘", "|", "……", "…", "——", "—");

    public static boolean isAsciiPunc(String punc) {
        Preconditions.checkNotNull(punc);
        return ASCII_PUNC_SET.contains(punc);
    }

    public static boolean isChinesePunc(String punc) {
        Preconditions.checkNotNull(punc);
        return CHINESE_PUNC_SET.contains(punc);
    }

    public static boolean isPunc(String punc) {
        return isAsciiPunc(punc) || isChinesePunc(punc);
    }

    public static boolean withPuncEnds(String str) {
        Preconditions.checkNotNull(str);
        for (String punc : ASCII_PUNC_SET) {
            if (str.endsWith(punc)) {
                return true;
            }
        }

        for (String punc : CHINESE_PUNC_SET) {
            if (str.endsWith(punc)) {
                return true;
            }
        }

        return false;
    }
}
