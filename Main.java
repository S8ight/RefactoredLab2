package edu.pro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String FILE_PATH = "src/edu/pro/txt/harry.txt";
    private static final int TOP_N_WORDS = 30;

    public static String cleanText(String content) {
        return content.replaceAll("[^A-Za-z ]", " ").toLowerCase(Locale.ROOT);
    }

    public static void main(String[] args) {
        try {
            Instant start = Instant.now();

            // Reading and cleaning up text
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            content = cleanText(content);

            // Splitting into words
            String[] words = content.split("\\s+");

            // Counting the frequency of words
            Map<String, Integer> wordFreq = new HashMap<>();
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                }
            }

            // Sort words by frequency
            List<Map.Entry<String, Integer>> sortedWords = wordFreq.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .collect(Collectors.toList());

            // Output of top-N words
            for (int i = 0; i < Math.min(TOP_N_WORDS, sortedWords.size()); i++) {
                Map.Entry<String, Integer> entry = sortedWords.get(i);
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            Instant finish = Instant.now();
            System.out.println("------");
            System.out.println(Duration.between(start, finish).toMillis() + " ms");

        } catch (IOException e) {
            System.err.println("File reading error: " + e.getMessage());
        }
    }
}
