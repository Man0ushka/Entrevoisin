package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class ClickedNeighbourEvent {
    /**
     * Neighbour clicked
     */
    public final Neighbour neighbour;
    public final int tabPos;

    /**
     * Constructor.
     * @param neighbour
     */
    public ClickedNeighbourEvent(Neighbour neighbour, int tabPos) {
        this.tabPos=tabPos;
        this.neighbour = neighbour;
    }
}
