package com.udacity.webcrawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Pattern;

class CrawlInternalCustom extends RecursiveAction {
    private final String url;
    private final Instant deadline;
    private final int maxDepth;
    private final ConcurrentMap<String, Integer> counts;
    private final ConcurrentSkipListSet<String> visitedUrls;
    private final Clock clock;
    private final List<Pattern> ignoredUrls;
    private final PageParserFactory parserFactory;

    CrawlInternalCustom(String url, ConcurrentMap<String, Integer> counts,
                    ConcurrentSkipListSet<String> visitedUrls, Clock clock, List<Pattern> ignoredUrls,
                    PageParserFactory parserFactory, Instant deadline, int maxDepth) {
        this.url = url;
        this.counts = counts;
        this.visitedUrls = visitedUrls;
        this.clock = clock;
        this.ignoredUrls = ignoredUrls;
        this.parserFactory = parserFactory;
        this.deadline = deadline;
        this.maxDepth = maxDepth;
    }

    @Override
    protected void compute() {
        if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
            return;
        }
        for (Pattern pattern : ignoredUrls) {
            if (pattern.matcher(url).matches()) {
                return;
            }
        }
        if (visitedUrls.contains(url)) {
            return;
        }
        visitedUrls.add(url);
        PageParser.Result result = parserFactory.get(url).parse();

        for (ConcurrentMap.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
            counts.compute(e.getKey(), (key, val) -> (val == null) ? e.getValue() : e.getValue() + val);
        }
        List<CrawlInternalCustom> subtasks = new ArrayList<>();
        for (String link : result.getLinks()) {
            subtasks.add(new CrawlInternalCustom(link, counts, visitedUrls, clock, ignoredUrls, parserFactory, deadline, maxDepth - 1));
        }
        invokeAll(subtasks);
    }
}

