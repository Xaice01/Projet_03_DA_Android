package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     *
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     *
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * set favori attribut to true to a neighbour
     *
     * @param neighbour
     */
    void addfavori(Neighbour neighbour);

    /**
     * set favori attribut to false to a neighbour
     *
     * @param neighbour
     */
    void deleteFavori(Neighbour neighbour);
}
