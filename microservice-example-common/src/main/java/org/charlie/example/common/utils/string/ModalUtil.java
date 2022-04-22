package org.charlie.example.common.utils.string;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Set;

public class ModalUtil {

    final static Set<String> CHINESE_MODAL_WORD_SET = Sets.newHashSet("嘞|欸|诶|吧|吱|呀|呃|呐|呓|呕|呗|呜|呢|呵|呸|呼|咋|和|咚|咦|咧|咯|咳|咿|哇|哈|哉|哎|哒|哗|哟|哦|哧|哩|哼|唉|唷|啊|啐|啥|啦|啪|啷|喀|喂|喏|喔|喽|嗡|嗬|嗳|嗷|嘎|嘘|嘛|嘻|嘿|噢|噫".split("\\|"));

    public static boolean isModal(String str) {
        Preconditions.checkNotNull(str);
        return CHINESE_MODAL_WORD_SET.contains(str);
    }

    public static boolean withModalStarts(String str) {
        Preconditions.checkNotNull(str);
        for (String modalWord : CHINESE_MODAL_WORD_SET) {
            if (str.startsWith(modalWord)) {
                return true;
            }
        }
        return false;
    }

    public static boolean withModalEnds(String str) {
        Preconditions.checkNotNull(str);
        for (String modalWord : CHINESE_MODAL_WORD_SET) {
            if (str.endsWith(modalWord)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsModal(String str) {
        Preconditions.checkNotNull(str);
        for (String modalWord : CHINESE_MODAL_WORD_SET) {
            if (str.contains(modalWord)) {
                return true;
            }
        }
        return false;
    }
}
