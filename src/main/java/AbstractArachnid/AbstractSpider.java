package AbstractArachnid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * An Abstract class that implements a Simple Webcrawler.
 * Provides procedures for Requesting a Webpage by it's link and
 * Filtering all links (by HTML href tag) from a given Webpage.
 * <p>
 * Created by muderjul on 01.02.2017.
 */
public abstract class AbstractSpider {

    private final URLQueue queue = new URLQueue();
    private final String userAgent;
    private final int depth;
    private final int delay;

    /**
     * Creates a new Webcrawler using the specified UserAgent.
     *
     * @param start     The URLs at which the Webcrawler should start it's journey.
     * @param depth     The maximum distance in Links from the start URL that should be inspected.
     * @param userAgent The UserAgent the Webcrawler should use instead of the default one.
     */
    public AbstractSpider (String[] start, int depth, String userAgent, int delay) {
        for (String link : start) {
            this.queue.addUnseen(new URLHandler(link, 0, ""));
        }
        this.userAgent = userAgent;
        this.depth = depth;
        this.delay = delay;
    }

    /**
     * Creates a new Webcrawler and uses the default UserAgent.
     *
     * @param depth The maximum distance in Links from the start URL that should be inspected.
     * @param start The URLs at which the Webcrawler should start it's journey.
     */
    public AbstractSpider (String[] start, int depth) {
        for (String link : start) {
            this.queue.addUnseen(new URLHandler(link, 0, ""));
        }
        this.userAgent = null;
        this.depth = depth;
        this.delay = 0;
    }

    /**
     * Checks if a Link has already been seen. (Reduces exposure to inheriting classes.)
     *
     * @param link The Link to be checked.
     * @return True if the Link has been seen, false otherwise.
     */
    protected boolean isSeen (URLHandler link) {
        return this.queue.isSeen(link);
    }

    /**
     * @return Returns the Depth at which the Spider should stop.
     */
    protected int getDepth () {
        return this.depth;
    }

    /**
     * Starts the routine of the Webcrawler.
     * Terminates when no more Links can be found or the specified Depth is reached.
     */
    protected void startSpider () {
        URLHandler currentURL = this.queue.getNext();
        while (currentURL != null) {
            if (currentURL.getDepth() >= this.depth) {
                currentURL = this.queue.getNext();
                continue;
            }


            String page = this.requestURL(currentURL);

            List<URLHandler> foundLinks = this.filterLinks(this.findLinks(page), currentURL);
            for (URLHandler link : foundLinks) {
                link.setDepth(currentURL.getDepth() + 1);
                link.setLinkto(currentURL.getName());
            }
            this.queue.addAllUnseen(foundLinks);
            this.processLink(currentURL, foundLinks, page);
            this.queue.addSeen(currentURL);
            currentURL = this.queue.getNext();
        }
    }

    /**
     * Requests the HTML code from the given URL.
     *
     * @param link The URL to be requested.
     * @return The raw HTML of the URL.
     */
    private String requestURL (URLHandler link) {
        URL target;
        HttpURLConnection connection = null;
        try {
            target = new URL(link.getURL());
            connection = (HttpURLConnection) target.openConnection();
            if (this.userAgent != null) {
                connection.setRequestProperty("User-Agent", this.userAgent);
            }
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            sleep(delay);

            return result.toString();

        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.err.println("Sleep Interrupted.");
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Connection Failed.");
            System.err.println(link.getURL());
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Filters all Links from a List that should not be Processed by the Webcrawler.
     *
     * @param links The List of Links to be filtered.
     * @param currentLink The link that is currently being processed.
     * @return A List containing only Links that should be looked at.
     */
    protected abstract List<URLHandler> filterLinks(List<URLHandler> links, URLHandler currentLink);

    /**
     * Filters all Links (by HTML href-Tag) from a String.
     *
     * @param content The HTML to be inspected.
     * @return A List of URLHandlers where each Handler contains a Link from the HTML.
     */
    protected List<URLHandler> findLinks (String content) {
        Pattern regexp = Pattern.compile("href=[\'\"]?([^\'\" >]+)");
        Matcher matcher = regexp.matcher(content);
        List<URLHandler> links = new ArrayList<URLHandler>();

        while (matcher.find()) {
            links.add(new URLHandler(matcher.group(1), ""));
        }

        return links;
    }

    /**
     * Runs a User Defined routine on the given link.
     *
     * @param link        The Link that is processed.
     * @param linksOnSite All Links (by href) found on this page AFTER filtering.
     * @param content     The raw HTML found at the position of the Link.
     */
    protected abstract void processLink (URLHandler link, List<URLHandler> linksOnSite, String content);
}
