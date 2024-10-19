package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private Map<String, TimeSeries> countwords = new HashMap<>();
    private TimeSeries counttotal = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordfile = new In(wordsFilename);
        In countfile = new In(countsFilename);
        while (wordfile.hasNextLine()) {
            String nextline = wordfile.readLine();
            if (nextline == null) {
                break;
            }
            String[] splitline = nextline.split("\t");
            String word = splitline[0];
            int year = Integer.parseInt(splitline[1]);
            long count = Long.parseLong(splitline[2]);
            TimeSeries empty = new TimeSeries();
            countwords.putIfAbsent(word, empty);
            TimeSeries timeseries = countwords.get(word);
            Double curr = timeseries.get(year);
            if (curr == null) {
                timeseries.put(year, (double) count);
            }
            else {
                timeseries.put(year, curr + count);
            }
        }
        while (countfile.hasNextLine()) {
            String nextline = countfile.readLine();
            if (nextline == null) {
                break;
            }
            String[] splitline = nextline.split(",");
            int year = Integer.parseInt(splitline[0]);
            long totalWords = Long.parseLong(splitline[1]);
            counttotal.put(year, (double) totalWords);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!countwords.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries timeseries = countwords.get(word);
        return new TimeSeries(timeseries, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!countwords.containsKey(word)) {
            return new TimeSeries();
        }
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(counttotal, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!countwords.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries hist = countHistory(word, startYear, endYear);
        TimeSeries histcount = totalCountHistory();
        return hist.dividedBy(histcount);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!countwords.containsKey(word)) {
            return new TimeSeries();
        }
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries tsreturn = new TimeSeries();
        for (String word : words) {
            TimeSeries weightHistory = weightHistory(word, startYear, endYear);
            tsreturn = tsreturn.plus(weightHistory); // Sum the results
        }
        return tsreturn;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
