package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.*;


public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;

    public HyponymsHandler(String synsetsFile, String hyponymsFile) {
        wordNet = new WordNet(synsetsFile, hyponymsFile);
    }

    @Override
    public String handle(NgordnetQuery q) {
        String words = q.get("words");
        String kStr = q.get("k");
        int k = kStr == null ? 0 : Integer.parseInt(kStr);

        String[] wordsArray = words.split(",");
        Set<String> commonHyponyms = getCommonHyponyms(wordsArray);

        if (k > 0) {
            List<String> sortedHyponyms = new ArrayList<>(commonHyponyms);
            Collections.sort(sortedHyponyms);
            return sortedHyponyms.subList(0, Math.min(k, sortedHyponyms.size())).toString();
        } else {
            List<String> sortedHyponyms = new ArrayList<>(commonHyponyms);
            Collections.sort(sortedHyponyms);
            return sortedHyponyms.toString();
        }
    }

    private Set<String> getCommonHyponyms(String[] wordsArray) {
        Set<String> commonHyponyms = null;

        for (String word : wordsArray) {
            Set<String> hyponyms = wordNet.hyponyms(word);
            if (commonHyponyms == null) {
                commonHyponyms = new HashSet<>(hyponyms);
            } else {
                commonHyponyms.retainAll(hyponyms);
            }
        }

        return commonHyponyms != null ? commonHyponyms : new HashSet<>();
    }
}
