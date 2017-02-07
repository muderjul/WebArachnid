package AbstractArachnid;

import org.graphstream.ui.view.Viewer;

/**
 * Interface to be able to switch away from GraphStream later on.
 * <p>
 * Created by muderjul on 05.02.2017.
 */
public interface GraphBuilder {

    /**
     * Adds a Node with the given name, if possible.
     *
     * @param name The name of the new Node.
     */
    void addNode (String name);

    /**
     * Removes the Node with the given name, if possible.
     *
     * @param name The name of the Node to be removed.
     */
    void removeNode (String name);

    /**
     * Adds an Edge between the two given Nodes, if possible.
     *
     * @param startName The name of the start Node of the Edge to be added.
     * @param endName   The name of the end Node of the Edge to be added.
     */
    void addEdge (String startName, String endName);

    /**
     * Removes the Edge between the two given Nodes, if possible.
     *
     * @param startName The name of the start Node of the Edge to be removed.
     * @param endName   The name of the end Node of the Edge to be removed.
     */
    void removeEdge (String startName, String endName);

    /**
     * Sets an Attribute of a Node to a given value.
     *
     * @param nodeName      The Node whose attribute should be set.
     * @param attributeName The name of the Attribute to be set.
     * @param value         The value to which the Attribute should be set to.
     */
    void setAttribute (String nodeName, String attributeName, double value);

    /**
     * Gets the value of a certain Attribute for a Node.
     *
     * @param nodeName      The name of the Node whose Attribute should be read.
     * @param attributeName The name of the Attribute to be read.
     * @return The value of the Attribute or 0.0 if it could not be found.
     */
    double getAttribute (String nodeName, String attributeName);

    /**
     * Writes a Step to the output file (if output is written to file).
     * Used for more organized displaying of written output.
     */
    void step ();

    /**
     * Displays the Graph (opens a JavaSwing Window).
     */
    Viewer display ();
}
