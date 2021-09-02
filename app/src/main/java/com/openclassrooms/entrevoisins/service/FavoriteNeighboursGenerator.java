package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FavoriteNeighboursGenerator {
    public static final List<Neighbour> FAVORITES = Collections.emptyList();

    static List<Neighbour> generateNeighbours() {
        return new ArrayList<>(FAVORITES);
    }
}
