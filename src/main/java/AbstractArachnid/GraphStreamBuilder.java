package AbstractArachnid;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

/**
 * Implements a AbstractArachnid.GraphBuilder using the GraphStream Library
 * <p>
 * Created by muderjul on 02.02.2017.
 */
public class GraphStreamBuilder implements GraphBuilder {

    private final Graph graph;


    public GraphStreamBuilder () {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        this.graph = new SingleGraph("Webcrawler");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet",
                           "node {\n" + "\tfill-mode: dyn-plain;\n" + "\tfill-color: teal, purple, olive, maroon;\n" +
                                   "\ttext-mode: hidden;\n" + "\tsize-mode: dyn-size;\n" + "\tsize: 10px;\n" + "}\n" +
                                   "\n" + "edge {\n" + "\tshape: line;\n" + "\tfill-mode: dyn-plain;\n" +
                                   "\tfill-color: #000;\n" + "\tarrow-size: 3px, 2px;\n" + "}\n");
    }

    public void addNode (String name) {
        if (this.graph.getNode(name.toLowerCase()) == null) {
            this.graph.addNode(name.toLowerCase());
        }
    }

    public void removeNode (String name) {
        if (this.graph.getNode(name.toLowerCase()) != null) {
            this.graph.removeNode(name.toLowerCase());
        }
    }

    public void addEdge (String startName, String endName) {
        this.addNode(startName);
        this.addNode(endName);
        if (this.graph.getEdge(startName.toLowerCase() + endName.toLowerCase()) == null && this.graph.getEdge(
                endName.toLowerCase() + startName.toLowerCase()) == null) {
            this.graph.addEdge(startName.toLowerCase() + endName.toLowerCase(), startName.toLowerCase(),
                               endName.toLowerCase());
        }
    }

    public void removeEdge (String startName, String endName) {
        if (this.graph.getEdge(startName.toLowerCase() + endName.toLowerCase()) != null) {
            this.graph.removeEdge(startName.toLowerCase() + endName.toLowerCase());
        } else if (this.graph.getEdge(endName.toLowerCase() + startName.toLowerCase()) != null) {
            this.graph.removeEdge(endName.toLowerCase() + startName.toLowerCase());
        }
    }

    public void setAttribute (String nodeName, String attributeName, double value) {
        this.graph.getNode(nodeName.toLowerCase()).setAttribute(attributeName, value);
    }

    public double getAttribute (String nodeName, String attributeName) {
        if (this.graph.getNode(nodeName.toLowerCase()).getAttribute(attributeName) == null) {
            return 0.0;
        } else {
            return this.graph.getNode(nodeName.toLowerCase()).getAttribute(attributeName);
        }
    }

    public void step () {
        // Purposely unused.
    }

    public Viewer display () {
        return this.graph.display();
    }

}
