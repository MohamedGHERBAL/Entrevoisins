package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Add a Neighbour to FavoriteNeighbour
     * @param neighbour
     */
    void addNeighbourFavorite(Neighbour neighbour);

    /**
     * Get all my NeighboursFavorite
     * @return {@link List}
     */
    List<Neighbour> getNeighboursFavorite();

    /**
     * boolean to check if your Neighbour is in favorite or not
     * @param neighbour
     * @return
     */
    Boolean getFavorite(Neighbour neighbour);

    /**
     * Delete from favorite Neighbour
     */
    void removeFavoriteNeighbour();

    /**
     * Delete from favorite Neighbour
     */
    void removeFavoriteNeighbourForToast(Neighbour neighbour);

    /**
     * Get all my NeighboursById
     * @param id
     * @return
     */
    Neighbour getNeighboursById(long id);

    /**
     * Check favorite status
     */
    void checkStatusFab(long neighbourId);
}
