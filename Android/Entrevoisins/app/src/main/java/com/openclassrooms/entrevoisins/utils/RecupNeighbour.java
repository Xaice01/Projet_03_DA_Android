package com.openclassrooms.entrevoisins.utils;

import androidx.annotation.NonNull;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

public class RecupNeighbour {
    //retake a Neighbour via this ID
    public static Neighbour recupTrueNeighbour(long idNeighbour, @NonNull List<Neighbour> Neighbours){
        Neighbour Fakeneighbour= new Neighbour(idNeighbour,"a","b","c","d","e");
        Neighbour neighbourToReturn = null;
        for(Neighbour i:Neighbours){
            if(i.equals(Fakeneighbour)) {
                neighbourToReturn= i;
            }
        }
        return neighbourToReturn;
    }

}
