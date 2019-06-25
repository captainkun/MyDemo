package com.kun.WebCrawler.crawl.link;

public interface LinkFilter {
    boolean accept(String url);
}
