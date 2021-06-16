package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    @Override
    public void addNeighbourFavorite (Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(true);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }


    @Override
    public List<Neighbour> getNeighboursFavorite() {
        ArrayList<Neighbour> favoriteNeighbours = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getFavorite()) {
                favoriteNeighbours.add(neighbour);
            }
        }
        return favoriteNeighbours;
    }

    @Override
    public Boolean getFavorite (Neighbour neighbour) {
        return neighbours.get(neighbours.indexOf(neighbour)).getFavorite();
    }

    @Override
    public void removeFavoriteNeighbour () {
        for (Neighbour neighbour : neighbours) {
            neighbour.setFavorite(false);
        }
    }

    @Override
    public void removeFavoriteNeighbourForToast(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(false);
    }

    @Override
    public Neighbour getNeighboursById(long id) {
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getId() == id) {
                return neighbour;
            }
        }
        return null;
    }

    @Override
    public void checkStatusFab(long neighbourId) {
        Neighbour neighbour = getNeighboursById(neighbourId);
        neighbour.setFavorite(!neighbour.getFavorite());
    }
}
