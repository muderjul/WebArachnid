package SpecificSpiders;

import AbstractArachnid.GraphBuilder;
import AbstractArachnid.GraphSpider;
import AbstractArachnid.GraphStreamBuilder;
import AbstractArachnid.URLHandler;

import java.util.ArrayList;
import java.util.List;

public class WikiSpider extends GraphSpider {


    public WikiSpider(String[] start, int depth, String userAgent, int delay, boolean earlyDisplay, GraphBuilder builder) {
        super(start, depth, userAgent, delay, earlyDisplay, builder);
    }

    protected List<URLHandler> filterLinks(List<URLHandler> links, URLHandler currentLink) {
        List<URLHandler> filteredLinks = new ArrayList<URLHandler>();
        for (URLHandler link : links) {
            if (filteredLinks.size() >= 5 && !super.isSeen(link)) {
                continue;
            } else if (currentLink.equals(link)) {
                continue;
            } else if (link.getName().startsWith("/wiki/")) {
                if (link.getName().contains(":")) {
                    continue;
                }
                filteredLinks.add(new URLHandler("https://de.wikipedia.org" + link.getURL(), link.getLinkto()));
            } else if (link.getName().startsWith("https://de.wikipedia.org")) {
                filteredLinks.add(link);
            }
        }
        return filteredLinks;
    }

    public static void main(String[] args) {
        String[] start = {"https://de.wikipedia.org/wiki/Informatik"};

        WikiSpider spider = new WikiSpider(start, 3, "RandomWebcrawler", 2000, true, new GraphStreamBuilder());
        spider.startSpider();
    }
}
