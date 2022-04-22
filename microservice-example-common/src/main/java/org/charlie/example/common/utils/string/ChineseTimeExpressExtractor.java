package org.charlie.example.common.utils.string;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.apache.commons.lang3.tuple.Pair;
import org.charlie.example.common.utils.io.file.PatternExtractor;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class ChineseTimeExpressExtractor {

    final static String CHINESE_TIME_EXPRESS_DEFAULT_FILEPATH = "timeexpress/chinese_time_express.txt";

    final static String CHINESE_TIME_EXPRESS_COMPRESS_DEFAULT_FILEPATH = "timeexpress/chinese_time_express.compress.txt";

    final static String[] FUTURE_MODIFIER = "下|下下|再下个|下个|下下个|明天|后天|大后天|过|等|内|之内|以内|后|之后|过后|以后".split("\\|");

    final static String[] PAST_MODIFIER = "头|头个|前个|上|上上|上个|上上个|大前天|前天|昨天|前|之前|以前".split("\\|");

    List<String> expresses;

    public void initFromCompress() throws IOException {
        URL url = Resources.getResource(CHINESE_TIME_EXPRESS_COMPRESS_DEFAULT_FILEPATH);
        List<String> patterns = Lists.newArrayList(Resources.asCharSource(url, Charsets.UTF_8).readLines());
        expresses = Lists.newLinkedList();
        for (String pattern : patterns) {
            expresses.addAll(PatternExtractor.parse(pattern));
        }
        Collections.sort(expresses, (left, right) -> {
            if (left.length() > right.length()) {
                return -1;
            } else if (left.length() == right.length()) {
                return 0;
            } else {
                return 1;
            }
        });
    }

    public void init() throws IOException {
        URL url = Resources.getResource(CHINESE_TIME_EXPRESS_DEFAULT_FILEPATH);
        expresses = Lists.newArrayList(Resources.asCharSource(url, Charsets.UTF_8).readLines());
        Collections.sort(expresses, (left, right) -> {
            if (left.length() < right.length()) {
                return -1;
            } else if (left.length() == right.length()) {
                return 0;
            } else {
                return 1;
            }
        });
    }

    public boolean isFuture(String timeExpr) {

        for (String modifier : FUTURE_MODIFIER) {
            if (timeExpr.indexOf(modifier) >= 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isPast(String timeExpr) {

        for (String modifier : PAST_MODIFIER) {
            if (timeExpr.indexOf(modifier) >= 0) {
                return true;
            }
        }
        return false;
    }

    public Pair<String, int[]> matchFirst(int start, String target) {
        int index = 0;
        String targetExpr = null;
        for (String expr : expresses) {
            index = target.substring(start).indexOf(expr);
            if (index >= 0) {
                targetExpr = expr;
                break;
            }
        }

        if (null != targetExpr) {
            return Pair.of(targetExpr, new int[]{index + start, targetExpr.length()});
        } else {
            return null;
        }
    }

    public List<Pair<String, int[]>> matchAll(String target) {
        List<Pair<String, int[]>> all = Lists.newLinkedList();
        Pair<String, int[]> pair = matchFirst(0, target);

        int subStrStart = 0;
        while (null != pair) {
            all.add(pair);
            int[] posInfo = pair.getRight();
            int startIndex = posInfo[0];
            int exprLen = posInfo[1];
            subStrStart = startIndex + exprLen;
            pair = matchFirst(subStrStart, target);
        }
        return all;
    }


    public static void main(String[] args) throws IOException {
        ChineseTimeExpressExtractor extractor = new ChineseTimeExpressExtractor();

        extractor.initFromCompress();
        long start =System.currentTimeMillis();
        String s = "不是3号才还了吗";
        Pair<String, int[]> pair = extractor.matchFirst(0, s);
        System.out.println(pair.getLeft());
        System.out.println(pair.getRight()[0]);
        System.out.println(pair.getRight()[1]);
        System.out.println(pair.getLeft().equals(s.substring(pair.getRight()[0], pair.getRight()[1])));
        System.out.println(extractor.isFuture(pair.getLeft()));
        System.out.println(extractor.isPast(pair.getLeft()));

        long end = System.currentTimeMillis();
        System.out.println("程序运行时间： "+(end-start)+"ms");

    }

}
