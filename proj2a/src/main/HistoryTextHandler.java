package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear();
        int endYear = query.endYear();
        StringBuilder response = new StringBuilder();
        for (String word : words) {
            TimeSeries ts = map.weightHistory(word, startYear, endYear);
            response.append(word).append(": ").append(ts.toString()).append("\n");
        }
        return response.toString();
    }
}
