package AbstractArachnid;

import org.graphstream.ui.view.Viewer;

import java.util.List;

/**
 * Abstract class that automatically builds a Graph based on visited Websites.
 * <p>
 * Created by muderjul on 07.02.2017.
 */
public abstract class GraphSpider extends AbstractSpider {

    private final GraphBuilder builder;
    private final boolean earlyDisplay;
    private Viewer viewer = null;

    public GraphSpider (String[] start, int depth, String userAgent, int delay, boolean earlyDisplay, GraphBuilder builder) {
        super(start, depth, userAgent, delay);
        this.builder = builder;
        this.earlyDisplay = earlyDisplay;
    }

    public GraphSpider (String[] start, int depth) {
        super(start, depth);
        this.builder = new GraphStreamBuilder();
        this.earlyDisplay = true;
    }

    @Override
    protected void startSpider () {
        if (earlyDisplay) {
            this.viewer = builder.display();
        }
        super.startSpider();

        if (!earlyDisplay) {
            this.viewer = builder.display();
        }
    }

    @Override
    public void processLink (URLHandler link, List<URLHandler> linksOnSite, String content) {
        this.builder.step();
        System.out.println(link.getURL() + "   " + link.getDepth() + "      " + link.getLinkto());
        this.builder.addNode(link.getName());
        if (!link.getLinkto().equals("")) {
            this.builder.addEdge(link.getName(), link.getLinkto());
        }
        for (URLHandler newLink : linksOnSite) {
            if (newLink.equals(link)) {
                break;
            }
            this.builder.addNode(link.getName());
            if (!super.isSeen(link) && this.builder.getAttribute(link.getName(), "ui.color") == 0.0) {
                this.builder.setAttribute(link.getName(), "ui.color",
                                          ((double) link.getDepth() + 1) / ((double) super.getDepth()));
            }
        }
        if (!super.isSeen(link)) {
            this.builder.setAttribute(link.getName(), "ui.size", 5 + (super.getDepth() * 3) / (link.getDepth() + 1));
        }
    }
}
