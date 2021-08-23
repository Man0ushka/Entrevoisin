package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }
    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_FAVORITES;
        assertThat(favoriteNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void isNeighbourAlreadyInFavoriteList()
    {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour mockNeighbour = new Neighbour(28,"manu","https://i.pravatar.cc/150?u=a042581f4e29026704d","earth","0","nothing");

        // Make sure mock neighbour doesn't exist in either list
        assertFalse(neighbours.contains(mockNeighbour));
        assertFalse(favoriteNeighbours.contains(mockNeighbour));
        // Add mockNeighbour to main list
        service.createNeighbour(mockNeighbour);
        assertTrue(neighbours.contains(mockNeighbour));
        // MockNeighbour is only in main list
        boolean expectedAnswer = false;
        boolean actualAnswer = service.neighbourIsAlreadyFavorite(mockNeighbour);
        assertEquals(actualAnswer,expectedAnswer);
        // MockNeighbour is in both lists
        service.createFavoriteNeighbour(mockNeighbour);
        assertTrue(favoriteNeighbours.contains(mockNeighbour));
        expectedAnswer = true;
        actualAnswer = service.neighbourIsAlreadyFavorite(mockNeighbour);
        assertEquals(actualAnswer,expectedAnswer);

    }


}
