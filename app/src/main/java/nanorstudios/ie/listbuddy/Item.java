package nanorstudios.ie.listbuddy;

/**
 * A model that represents a list item.
 *
 * @author Ronan Doyle
 */
public class Item {
    private String complete;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String isComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }
}
