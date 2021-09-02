package com.openclassrooms.entrevoisins.di;

import com.openclassrooms.entrevoisins.service.FavoriteNeighboursApiService;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static NeighbourApiService service = new DummyNeighbourApiService();
    private static final NeighbourApiService serviceFavorite = new FavoriteNeighboursApiService();

    /**
     * Get an instance on @{@link NeighbourApiService}
     * @return
     */
    public static NeighbourApiService getNeighbourApiService() {
        return service;
    }
    public static NeighbourApiService getFavoriteNeighbourApiService() {
        return serviceFavorite;
    }

    /**
     * Get always a new instance on @{@link NeighbourApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static NeighbourApiService getNewInstanceApiService(NeighbourApiService dummyApiService) {
        service = dummyApiService;
        return dummyApiService;
    }
}
