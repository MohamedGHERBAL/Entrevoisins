package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat (neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbour_WithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse (service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     * Unit test on Neighbour service
     */
    @Test
    public void addNeighbourToFavorite_WithSuccess () {
        Neighbour neighbourToAddFavorite = service.getNeighbours().get(0);

        service.addNeighbourFavorite(neighbourToAddFavorite);
        assertTrue (service.getNeighboursFavorite().contains(neighbourToAddFavorite));
    }

    /**
     *  Check if NeighboursFavorite is not empty
     */
    @Test
    public void getNeighboursFavorite_NotEmpty() {
        service.removeFavoriteNeighbour();
        List<Neighbour> neighbours = service.getNeighbours();
        assertEquals (0, service.getNeighboursFavorite().size());
        assertFalse (neighbours.get(0).getFavorite());

        service.checkStatusFab(neighbours.get(0).getId());
        assertEquals (1, service.getNeighboursFavorite().size());
        assertEquals (neighbours.get(0).getId(),service.getNeighboursFavorite().get(0).getId());
    }

    @Test
    public void removeNeighbourToFavorite_WithSuccess () {
        Neighbour neighbourToRemoveFavorite = service.getNeighbours().get(3);

        service.addNeighbourFavorite(neighbourToRemoveFavorite);
        assertTrue (service.getNeighboursFavorite().contains(neighbourToRemoveFavorite));

        service.deleteNeighbour(neighbourToRemoveFavorite);
        assertFalse (service.getNeighboursFavorite().contains(neighbourToRemoveFavorite));
    }
}
