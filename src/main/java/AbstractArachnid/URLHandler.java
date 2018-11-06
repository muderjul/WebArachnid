package AbstractArachnid;

/**
 * A Class that handles URL addresses.
 * Ensures that the URL is saved in Lowercase.
 * <p>
 * Created by muderjul on 01.02.2017.
 */
public class URLHandler {

    private final String name;
    private String linkto;
    private int depth;

    /**
     * Creates a new Handler representing the given URL.
     *
     * @param name  The link to the URL this Handler represents.
     * @param depth The Depth of the Link as distance from a Start Link.
     */
    public URLHandler (String name, int depth, String linkto) {
        this.name = name;
        this.depth = depth;
        this.linkto = linkto;
    }

    /**
     * Creates a new Handler representing the given URL.
     *
     * @param name The link to the URL this Handler represents.
     */
    public URLHandler (String name, String linkto) {
        this.name = name;
        this.depth = -1;
        this.linkto = linkto;
    }

    /**
     * @return The name of the URL saved in this Handler.
     */
    public String getName() {
        return this.name.toLowerCase();
    }

    /**
     * @return The URL saved in this Handler.
     */
    public String getURL() {
        return this.name;
    }

    /**
     * Returns the Depth of this URL as distance from a Start URL.
     *
     * @return The Depth of this URL.
     * @throws IllegalArgumentException If the Depth has not been initialized greater than 0.
     */
    public int getDepth () throws IllegalArgumentException {
        if (depth < 0) {
            throw new IllegalArgumentException("Depth not defined!");
        } else {
            return depth;
        }
    }

    /**
     * Sets the Depth of this URL as distance from a start URL.
     * Only works if the Depth was previously smaller 0
     *
     * @param depth The new depth to be set.
     */
    public void setDepth (int depth) {
        // TODO maybe use a Write-once-Read-many class?
        if (this.depth < 0) {
            this.depth = depth;
        }
    }

    /**
     * @return The URL this handler was found on.
     */
    public String getLinkto() {
        return this.linkto;
    }

    /**
     * Sets the Linkto of this URL.
     *
     * @param linkto The new linkto to be set.
     */
    public void setLinkto(String linkto) {
        this.linkto = linkto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URLHandler that = (URLHandler) o;
        return this.getName().equals(that.getName());
    }
}
