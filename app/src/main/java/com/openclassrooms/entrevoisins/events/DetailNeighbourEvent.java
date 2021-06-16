package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Created by Mohamed GHERBAL (pour OC) on 14/05/2021
 */
public class DetailNeighbourEvent {

    /**
     * Neighbour to show detail
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public DetailNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
