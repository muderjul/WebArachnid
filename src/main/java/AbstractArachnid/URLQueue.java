package AbstractArachnid;

import java.util.*;

/**
 * A Class that contains a Set of seen URLs and a queue for unseen URLs.
 * Enforces consistency on seen and unseen URLs.
 * <p>
 * Created by muderjul on 01.02.2017.
 */
public class URLQueue {

    private final Set<URLHandler> seen = new HashSet<URLHandler>();
    private final List<URLHandler> unseen = new LinkedList<URLHandler>();

    /**
     *
     */
    public URLQueue () {
    }

    /**
     * Adds a new Handler to the seen List if it is not already contained in it.
     * Also removes it from the unseen List if applicable.
     *
     * @param handler The new Handler to be added.
     */
    public void addSeen (URLHandler handler) {
        if (!this.seen.contains(handler)) {
            this.seen.add(handler);
            this.unseen.remove(handler);
        }
    }

    /**
     * Checks if a Handler is in the seen List.
     *
     * @param handler The Handler to be checked.
     * @return True if the Handler if contained in seen, False otherwise.
     */
    public boolean isSeen (URLHandler handler) {
        return this.seen.contains(handler);
    }

    /**
     * Adds a Collection of Handlers to the unseen List.
     * Only adds a Handler if it is neither contained in seen and unseen.
     *
     * @param handlers The Collection of Handlers to be added.
     */
    public void addAllUnseen (Collection<URLHandler> handlers) {
        // BFS
        for (URLHandler handler : handlers) {
            this.addUnseen(handler);
        }
    }

    /**
     * Adds a new Handler to the unseen List.
     * Adds only if it is not already contained in either seen or unseen.
     *
     * @param handler The Handler to be added.
     */
    public void addUnseen (URLHandler handler) {
        if (!this.seen.contains(handler) && !this.unseen.contains(handler)) {
            this.unseen.add(handler);
        }
    }

    /**
     * Gets the next queued Handler in unseen and removes it from unseen.
     *
     * @return The next queued Handler in unseen.
     */
    public URLHandler getNext () {
        if (this.unseen.isEmpty()) {
            return null;
        }
        return this.unseen.remove(0);
    }
}
