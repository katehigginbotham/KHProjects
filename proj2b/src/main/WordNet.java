package main;

import java.io.*;
import java.util.*;

public class WordNet {
    private Graph graph;
    private Map<String, Set<Integer>> wordToSynsets;
    private Map<Integer, Set<String>> synsetToWords;

    public WordNet(String synsetsFile, String hyponymsFile) {
        graph = new Graph();
        wordToSynsets = new HashMap<>();
        synsetToWords = new HashMap<>();
        loadSynsets(synsetsFile);
        loadHyponyms(hyponymsFile);
    }

    private void loadSynsets(String synsetsFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(synsetsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                int synsetId = Integer.parseInt(parts[0]);
                String[] words = parts[1].split(" ");
                synsetToWords.put(synsetId, new HashSet<>(Arrays.asList(words)));
                for (String word : words) {
                    wordToSynsets.putIfAbsent(word, new HashSet<>());
                    wordToSynsets.get(word).add(synsetId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHyponyms(String hyponymsFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(hyponymsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int synsetId = Integer.parseInt(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    int hyponymId = Integer.parseInt(parts[i]);
                    graph.addEdge(synsetId, hyponymId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> hyponyms(String word) {
        Set<String> result = new HashSet<>();
        Set<Integer> synsets = wordToSynsets.get(word);
        if (synsets != null) {
            for (int synsetId : synsets) {
                result.addAll(synsetToWords.get(synsetId));
                result.addAll(getAllHyponyms(synsetId));
            }
        }
        return result;
    }

    private Set<String> getAllHyponyms(int synsetId) {
        Set<String> hyponyms = new HashSet<>();
        Set<Integer> reachableNodes = graph.dfs(synsetId);
        for (int nodeId : reachableNodes) {
            hyponyms.addAll(synsetToWords.get(nodeId));
        }
        return hyponyms;
    }
}
