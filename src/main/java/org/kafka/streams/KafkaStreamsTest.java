package org.kafka.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class KafkaStreamsTest {
    public static void main(String[] args){
        String sentence = "This is a Sentence with large text. It has a new way of writing a sentence with this.";
        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        List<String> wordList = Arrays.asList(pattern.split(sentence.toLowerCase()));

        /** HashMap<String, Integer> wordCount = text.stream()
                .flatMap(value -> Arrays.asList(pattern.split(sentence.toLowerCase())))
                .groupBy((key, word)->word)
                .count();
         **/

        Map<String, Long> collect = wordList.stream()
                .collect(
                    groupingBy(Function.identity(), counting())
                );

        LinkedHashMap<String, Long> countByWordsSorted = collect.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(toMap(
                    Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));

        Map<String, Integer> items = new HashMap<>();
        items.put("A", 1);
        items.put("B", 1);
        items.put("C", 1);
        items.put("D", 1);

        items.forEach((k, v) -> System.out.println("Key = "+ k + " Value = "+ v));

        System.out.println(collect);

        System.out.println(countByWordsSorted);
    }
}
