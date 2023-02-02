package functionalProgramming;

import java.util.*;
import java.util.Map;

public class WordCount {
    private static final Map<String, Integer> wordCount = new HashMap<>();

    public static void addWords(String s) {
        for (String word : s.split("\\s")) {
            if (wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                wordCount.put(word, 1);
            }
        }
        System.out.println("В тексте: " + wordCount.size() + " слов");
        printWordCount();
    }

    public static void printWordCount() {
        System.out.println("ТОП 10: ");
        wordCount.entrySet()
                .stream()
                .limit(10)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(word -> System.out.println(word.getValue() + " - " + word.getKey()));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите текст: ");
        String text = scanner.nextLine();
        addWords(text);
    }
}