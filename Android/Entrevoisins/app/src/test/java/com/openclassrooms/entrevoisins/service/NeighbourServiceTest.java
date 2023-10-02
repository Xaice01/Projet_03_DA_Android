package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.utils.RecupNeighbour;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
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
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    //test ajout favori
    @Test
    public void addNeighbourToFavoris(){
        Neighbour neighbourToFavori = service.getNeighbours().get(0);
        service.addfavori(neighbourToFavori);                           //ajout du voisin dans les favori

        assertTrue(neighbourToFavori.isFavori());                       //vérification si le voisin est un favori
    }

    //test delete favori
    @Test
    public void deleteNeighbourToFavoris(){
        Neighbour neighbourToFavoriDelete = service.getNeighbours().get(0);
        neighbourToFavoriDelete.setFavori(true);                                // inisiatlisation un favoris
        service.deleteFavori(neighbourToFavoriDelete);                          //supprime le voisin des favori

        assertFalse(neighbourToFavoriDelete.isFavori());                        //vérification si le voisin est un favori
    }

    //test d’ajout du voisin à la liste de favoris
    @Test
    public void checkToListFavori(){

        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourToFavori = service.getNeighbours().get(0);
        service.addfavori(neighbourToFavori);                           //ajout de l'utilisateur dans les favori
        List<Neighbour> favoris = neighbours.stream().filter(Neighbour::isFavori).collect(Collectors.toList()); //création de la liste de favoris

        assertTrue(favoris.contains(neighbourToFavori));                //vérification si le favori se trouve dans la liste de favori
    }

    //récuperer l'utilisateur de la liste par son id recupTrueNeighbour(long idNeighbour)
    @Test
    public void getNeighbourWithId(){
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourToCheck = service.getNeighbours().get(0);
        long id=neighbourToCheck.getId();
        Neighbour neighbour;
        neighbour = RecupNeighbour.recupTrueNeighbour(id,neighbours);

        assertTrue(neighbour.equals(neighbourToCheck));

    }



}
