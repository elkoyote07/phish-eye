package com.jesus.phisheye.operator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class HomoglyphOperatorImpl implements HomoglyphOperator {

    private static int maxDepthReached = 0;
    private static boolean applyVisualSimilarity;
    private static int visualSimilarityScoreThreshold;

    @Override
    public Set<String> genByDnsWithoutTld(String originDns, Boolean applyVisualSimilarityParam, int visualSimilarityScore) {
        Set<String> results = new HashSet<>();
        maxDepthReached = 0;
        applyVisualSimilarity = applyVisualSimilarityParam;
        visualSimilarityScoreThreshold = visualSimilarityScore;
        long startTime = System.currentTimeMillis();
        log.info("Starting generating homoglyphs");
        generateHomoglyphsRecursive(originDns, "", results, 0);
        log.info("Finished generating homoglyphs");

        if (applyVisualSimilarity) {
            Set<String> filteredResults = new HashSet<>();
            for (String result : results) {
                double score = calculateVisualSimilarityScore(originDns, result);
                if (score >= visualSimilarityScoreThreshold / 100.0) { // Assuming the threshold is a percentage
                    filteredResults.add(result);
                    log.info("Visual similarity score for {}: {}", result, score);
                }
            }
            results = filteredResults;
        }

        long endTime = System.currentTimeMillis();
        log.info("Generation time: {} ms", (endTime - startTime));
        log.info("Total dns generated: " + results.size());
        log.info("Maximum depth reached: " + maxDepthReached);
        return results;
    }

    private static final Map<Character, char[]> homoglyphs = new HashMap<>();

    static {
        homoglyphs.put('a', new char[]{'а', 'α', '@', 'а', '4', 'ä'});
        homoglyphs.put('b', new char[]{'b', 'в', 'β', 'ь', 'Ь', '6'});
        homoglyphs.put('c', new char[]{'c', 'с', 'ϲ', '¢', 'ç'});
        homoglyphs.put('e', new char[]{'e', 'ε', '3', 'ё', 'е', 'ë'});
        homoglyphs.put('g', new char[]{'g', 'ɡ', 'ğ'});
        homoglyphs.put('h', new char[]{'h', 'н', 'η', 'һ'});
        homoglyphs.put('i', new char[]{'i', 'і', 'ι', '1', 'l', '¡', 'í', 'ï'});
        homoglyphs.put('k', new char[]{'k', 'к', 'κ', 'ķ'});
        homoglyphs.put('l', new char[]{'l', '1', 'і', 'l', 'ĺ', 'ļ'});
        homoglyphs.put('m', new char[]{'m', 'м', 'м', 'ṁ'});
        homoglyphs.put('n', new char[]{'n', 'п', 'η', 'и', 'ń'});
        homoglyphs.put('o', new char[]{'o', 'о', 'ο', '0', 'о', 'ö', 'ø'});
        homoglyphs.put('p', new char[]{'p', 'р', 'ρ', 'р', 'þ'});
        homoglyphs.put('r', new char[]{'r', 'г', 'r', 'г', 'ř'});
        homoglyphs.put('s', new char[]{'s', 'ѕ', 'ѕ', 'ś', 'š'});
        homoglyphs.put('t', new char[]{'t', 'т', 'τ', '+', 'ţ'});
        homoglyphs.put('u', new char[]{'u', 'υ', 'μ', 'ü'});
        homoglyphs.put('x', new char[]{'x', 'х', 'χ', '×', 'ẋ'});
        homoglyphs.put('y', new char[]{'y', 'у', 'γ', 'у', 'ý', 'ÿ'});
        homoglyphs.put('z', new char[]{'z', 'ź', 'ż', 'ž'});
    }

    private static final Map<String, Double> visualSimilarityScores = new HashMap<>();

    static {
        visualSimilarityScores.put("a", 1.0); // La propia 'a'
        visualSimilarityScores.put("а", 0.95); // Cyrillic 'a'
        visualSimilarityScores.put("α", 0.9);  // Greek alpha
        visualSimilarityScores.put("@", 0.2);  // At symbol
        visualSimilarityScores.put("4", 0.1);  // Number four
        visualSimilarityScores.put("ä", 0.6);  // 'a' with umlaut

        visualSimilarityScores.put("b", 1.0); // La propia 'b'
        visualSimilarityScores.put("в", 0.7);  // Cyrillic 've'
        visualSimilarityScores.put("β", 0.6);  // Greek beta
        visualSimilarityScores.put("ь", 0.9);  // Cyrillic 'soft sign'
        visualSimilarityScores.put("Ь", 0.9);  // Cyrillic 'soft sign' (capital)
        visualSimilarityScores.put("6", 0.2);  // Number six

        visualSimilarityScores.put("c", 1.0); // La propia 'c'
        visualSimilarityScores.put("с", 0.95); // Cyrillic 'es'
        visualSimilarityScores.put("ϲ", 0.9);  // Greek lunate sigma
        visualSimilarityScores.put("¢", 0.2);  // Cent symbol
        visualSimilarityScores.put("ç", 0.43); // 'c' with cedilla

        visualSimilarityScores.put("e", 1.0); // La propia 'e'
        visualSimilarityScores.put("е", 0.95); // Cyrillic 'ie'
        visualSimilarityScores.put("ε", 0.7);  // Greek epsilon
        visualSimilarityScores.put("3", 0.1);  // Number three
        visualSimilarityScores.put("ё", 0.4);  // Cyrillic 'io'
        visualSimilarityScores.put("ë", 0.4);  // 'e' with diaeresis

        visualSimilarityScores.put("g", 1.0); // La propia 'g'
        visualSimilarityScores.put("ɡ", 0.95); // Latin small letter script g
        visualSimilarityScores.put("ğ", 0.4);  // 'g' with breve

        visualSimilarityScores.put("h", 1.0); // La propia 'h'
        visualSimilarityScores.put("н", 0.85); // Cyrillic 'en'
        visualSimilarityScores.put("η", 0.3);  // Greek eta
        visualSimilarityScores.put("һ", 0.9);  // Cyrillic 'shha'

        visualSimilarityScores.put("i", 1.0); // La propia 'i'
        visualSimilarityScores.put("і", 0.9);  // Cyrillic 'i'
        visualSimilarityScores.put("ι", 0.85); // Greek iota
        visualSimilarityScores.put("1", 0.1);  // Number one
        visualSimilarityScores.put("l", 0.55);  // Lowercase L
        visualSimilarityScores.put("¡", 0.87);  // Inverted exclamation mark
        visualSimilarityScores.put("í", 0.75);  // 'i' with acute accent
        visualSimilarityScores.put("ï", 0.3);  // 'i' with diaeresis

        visualSimilarityScores.put("k", 1.0); // La propia 'k'
        visualSimilarityScores.put("к", 0.95); // Cyrillic 'ka'
        visualSimilarityScores.put("κ", 0.85); // Greek kappa
        visualSimilarityScores.put("ķ", 0.65); // 'k' with cedilla

        visualSimilarityScores.put("l", 1.0); // La propia 'l'
        visualSimilarityScores.put("1", 0.8);  // Number one
        visualSimilarityScores.put("і", 0.8);  // Cyrillic 'i'
        visualSimilarityScores.put("ĺ", 0.55); // 'l' with acute accent
        visualSimilarityScores.put("ļ", 0.65); // 'l' with cedilla

        visualSimilarityScores.put("m", 1.0); // La propia 'm'
        visualSimilarityScores.put("м", 0.95); // Cyrillic 'em'
        visualSimilarityScores.put("ṁ", 0.98); // 'm' with dot above

        visualSimilarityScores.put("n", 1.0); // La propia 'n'
        visualSimilarityScores.put("п", 0.85);  // Cyrillic 'pe'
        visualSimilarityScores.put("η", 0.9);  // Greek eta
        visualSimilarityScores.put("и", 0.5);  // Cyrillic 'i corta'
        visualSimilarityScores.put("ń", 0.45); // 'n' with acute accent

        visualSimilarityScores.put("o", 1.0); // La propia 'o'
        visualSimilarityScores.put("о", 0.95); // Cyrillic 'o'
        visualSimilarityScores.put("ο", 0.95); // Greek omicron
        visualSimilarityScores.put("0", 0.7);  // Number zero
        visualSimilarityScores.put("ö", 0.6);  // 'o' with diaeresis
        visualSimilarityScores.put("ø", 0.4);  // 'o' with stroke

        visualSimilarityScores.put("p", 1.0); // La propia 'p'
        visualSimilarityScores.put("р", 0.9);  // Cyrillic 'er'
        visualSimilarityScores.put("ρ", 0.85); // Greek rho
        visualSimilarityScores.put("þ", 0.3);  // Thorn

        visualSimilarityScores.put("r", 1.0); // La propia 'r'
        visualSimilarityScores.put("г", 0.4);  // Cyrillic 'ghe'
        visualSimilarityScores.put("ř", 0.2);  // 'r' with caron

        visualSimilarityScores.put("s", 1.0); // La propia 's'
        visualSimilarityScores.put("ѕ", 0.95); // Cyrillic 'dze'
        visualSimilarityScores.put("ś", 0.65); // 's' with acute accent
        visualSimilarityScores.put("š", 0.55); // 's' with caron

        visualSimilarityScores.put("t", 1.0); // La propia 't'
        visualSimilarityScores.put("т", 0.8);  // Cyrillic 'te'
        visualSimilarityScores.put("τ", 0.7);  // Greek tau
        visualSimilarityScores.put("+", 0.2);  // Plus sign
        visualSimilarityScores.put("ţ", 0.55); // 't' with cedilla

        visualSimilarityScores.put("u", 1.0); // La propia 'u'
        visualSimilarityScores.put("υ", 0.8);  // Greek upsilon
        visualSimilarityScores.put("μ", 0.2);  // Greek mu
        visualSimilarityScores.put("ü", 0.65); // 'u' with diaeresis

        visualSimilarityScores.put("x", 1.0); // La propia 'x'
        visualSimilarityScores.put("х", 0.98); // Cyrillic 'ha'
        visualSimilarityScores.put("χ", 0.85); // Greek chi
        visualSimilarityScores.put("×", 0.7);  // Multiplication sign
        visualSimilarityScores.put("ẋ", 0.55); // 'x' with dot above

        visualSimilarityScores.put("y", 1.0); // La propia 'y'
        visualSimilarityScores.put("у", 0.8);  // Cyrillic 'u'
        visualSimilarityScores.put("γ", 0.3);  // Greek gamma
        visualSimilarityScores.put("ý", 0.55); // 'y' with acute accent
        visualSimilarityScores.put("ÿ", 0.55); // 'y' with diaeresis

        visualSimilarityScores.put("z", 1.0); // La propia 'z'
        visualSimilarityScores.put("ź", 0.55); // 'z' with acute accent
        visualSimilarityScores.put("ż", 0.55); // 'z' with dot above
        visualSimilarityScores.put("ž", 0.45); // 'z' with caron

    }



    private static void generateHomoglyphsRecursive(String remaining, String current, Set<String> results, int depth) {
        if (depth > maxDepthReached) {
            maxDepthReached = depth;
        }

        if (remaining.isEmpty()) {
            results.add(current);
            return;
        }

        char c = remaining.charAt(0);
        String rest = remaining.substring(1);

        if (homoglyphs.containsKey(c)) {
            for (char homoglyph : homoglyphs.get(c)) {
                generateHomoglyphsRecursive(rest, current + homoglyph, results, depth + 1);
            }
        } else {
            generateHomoglyphsRecursive(rest, current + c, results, depth + 1);
        }
    }

    private double calculateVisualSimilarityScore(String original, String homoglyph) {
        double totalScore = 0.0;
        int count = 0;

        for (int i = 0; i < original.length(); i++) {
            char originalChar = original.charAt(i);
            char homoglyphChar = homoglyph.charAt(i);

            if (originalChar != homoglyphChar) {
                String key = String.valueOf(homoglyphChar);
                if (visualSimilarityScores.containsKey(key)) {
                    totalScore += visualSimilarityScores.get(key);
                    count++;
                }
            }
        }

        return count > 0 ? totalScore / count : 1.0;
    }

}
