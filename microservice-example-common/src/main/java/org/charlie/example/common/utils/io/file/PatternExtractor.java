package org.charlie.example.common.utils.io.file;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class PatternExtractor {

    public static List<String> parse(String text) {

        List<String> strings = Lists.newLinkedList();
        Preconditions.checkNotNull(text);

        if (text.length() == 0) {
            return strings;
        }

        State state = State.Othor;

        String docStr = "";

        List<Pair<State, String[]>> segments = Lists.newLinkedList();

        Stack<State> stateStack = new Stack<>();

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            if (State.Othor == state) {

                if ('(' == c) {
                    if (docStr.length() > 0) {
                        segments.add(new ImmutablePair<>(state, new String[]{docStr}));
                    }
                    stateStack.push(state);
                    state = State.Nessesary;
                    docStr = new String();
                } else if ('[' == c) {
                    if (docStr.length() > 0) {
                        segments.add(new ImmutablePair<>(state, new String[]{docStr}));
                    }
                    stateStack.push(state);
                    state = State.Optional;
                    docStr = new String();
                } else {
                    docStr += c;
                }

            } else if (State.Nessesary == state) {
                if (')' == c) {
                    segments.add(new ImmutablePair<>(state, docStr.split("\\|")));
                    state = stateStack.pop();
                    docStr = new String();
                } else {
                    docStr += c;
                }

            } else if (State.Optional == state) {
                if (']' == c) {
                    segments.add(new ImmutablePair<>(state, docStr.split("\\|")));
                    state = stateStack.pop();
                    docStr = new String();
                } else {
                    docStr += c;
                }
            }
        }
        if (docStr.length() > 0) {
            segments.add(new ImmutablePair<>(state, new String[]{docStr}));
        }
        return generateStrings(segments);
    }

    private static Pair<State, String[]> f(int index, List<Pair<State, String[]>> segments) {

        if (segments.size() - 1 == index) {

            Pair<State, String[]> last = segments.get(index);

            if (last.getLeft() == State.Optional) {

                List<String> wordsWithEmpty = Lists.newArrayList(last.getRight());
                wordsWithEmpty.add("");

                return new ImmutablePair<>(last.getLeft(), wordsWithEmpty.toArray(new String[0]));
            }


            return segments.get(index);
        }

        Pair<State, String[]> pair = segments.get(index);

        Pair<State, String[]> result = f(index + 1, segments);

        List<String> resultStrings = Lists.newLinkedList();


        // current is optional
        if (State.Optional == pair.getLeft()) {
            for (String resultStr : result.getRight()) {
                resultStrings.add(resultStr);
            }
        }

        for (String word : pair.getRight()) {

            for (String resultStr : result.getRight()) {
                resultStrings.add(word + resultStr);
            }
        }
        return new ImmutablePair<>(pair.getLeft(), resultStrings.toArray(new String[0]));

    }

    private static List<String> generateStrings(List<Pair<State, String[]>> segments) {
        List<String> strings = Lists.newLinkedList();
        Pair<State, String[]> result = f(0, segments);
        for (String s : result.getRight()) {
            strings.add(s);
        }

        return strings;
    }

    public static void extractLinesFromInputStream(String sourceAbsolutePath, String targetAbsolutePath) throws IOException {
        List<String> lines = Files.readLines(new File(sourceAbsolutePath), Charsets.UTF_8);
        FileWriter fileWriter = new FileWriter(targetAbsolutePath);
        for (String line : lines) {
            if (line.trim().length() == 0) continue;
            List<String> expandedStrs = parse(line.trim());
            for (String expanded : expandedStrs) {
                fileWriter.write(expanded);
                fileWriter.write("\r\n");
            }
        }
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
//        parse("");
        /*for (String s : parse("卡片被别人拿走了，人家又把我的(卡|信用卡|贷记卡)弄丢了")) {
            System.out.println(s);
        }*/
//        extractLinesFromInputStream("g:/time.txt", "g:/time.expanded.txt");

        Set<String> sentset = Sets.newHashSet();

        FileWriter fw = new FileWriter("g:/intent_test.txt");

        List<String> lines = Files.readLines(new File("C:\\developer\\project\\intellij-workspace\\nlp\\nlp-app-outdialing-service\\src\\main\\resources\\application\\outdialing\\corpus\\intents\\customer_intent_classification.corpus"), Charset.forName("utf-8"));
        for (String line : lines) {
            if (line.trim().length() == 0) continue;
            String[] splits = line.split("\\t");
            String text = splits[0];
            String label = splits[1].trim();
            List<String> sents = parse(text);
            for (String sent : sents) {

                if (sentset.contains(sent)) continue;
                sentset.add(sent);
                fw.write(sent);
                fw.write("\t");
                fw.write(label);
                fw.write("\n");
            }
        }
        fw.close();
    }

    private enum State {
        Othor, Optional, Nessesary
    }
}