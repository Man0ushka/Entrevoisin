package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class ClickedNeighbourEvent {
    /**
     * Neighbour clicked
     */
    public Neighbour neighbour;
    public int tabPos;

    /**
     * Constructor.
     * @param neighbour
     */
    public ClickedNeighbourEvent(Neighbour neighbour, int tabPos) {
        this.tabPos=tabPos;
        this.neighbour = neighbour;
    }
}
