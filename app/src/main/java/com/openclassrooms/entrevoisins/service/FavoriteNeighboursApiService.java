package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

public class FavoriteNeighboursApiService implements NeighbourApiService{
    private final List<Neighbour> favoriteNeighbours = FavoriteNeighboursGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        favoriteNeighbours.remove(neighbour);
    }


    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        favoriteNeighbours.add(neighbour);
    }


    public boolean neighbourIsAlreadyFavorite(Neighbour neighbour)
    {
        if(favoriteNeighbours.size()!=0)
        {
            for(int i=0; i<favoriteNeighbours.size();i++)
            {
                if(favoriteNeighbours.get(i).getId()==neighbour.getId())
                    return true;
            }
        }

        return false;
    }
}
