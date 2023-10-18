package com.openclassrooms.entrevoisins.utils;

import androidx.annotation.NonNull;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

public class GetNeighbourByID {
    //retake a Neighbour via this ID
    public static Neighbour getNeighbourByID(long idNeighbour, @NonNull List<Neighbour> Neighbours) {
        Neighbour Fakeneighbour = new Neighbour(idNeighbour, "a", "b", "c", "d", "e");
        Neighbour neighbourToReturn = null;
        for (Neighbour i : Neighbours) {
            if (i.equals(Fakeneighbour)) {
                neighbourToReturn = i;
            }
        }
        return neighbourToReturn;
    }

}
