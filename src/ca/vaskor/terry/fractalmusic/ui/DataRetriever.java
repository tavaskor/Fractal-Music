package ca.vaskor.terry.fractalmusic.ui;

/**
 * Gets data of the supplied type.
 * 
 * @author Terry Vaskor
 */
public interface DataRetriever<E> {
    /**
     * @return The data being stored in this user interface element.
     */
    E getData();
}
