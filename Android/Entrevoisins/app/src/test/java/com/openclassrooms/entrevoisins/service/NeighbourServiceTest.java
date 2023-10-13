package com.openclassrooms.entrevoisins.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.utils.RecupNeighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.stream.Collectors;


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

    //test récupérer la liste des Neighbour
    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    //test d'ajout d'un Neighbour
    @Test
    public void addNeighbourWithSucces() {
        Neighbour neighbourToAdd = new Neighbour(14, "Test_name", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Saint-Pierre-du-Mont ; 5km", "+33 6 86 57 90 14", "Bonjour ceci est un test");
        service.createNeighbour(neighbourToAdd);
        assertTrue(service.getNeighbours().contains(neighbourToAdd));

    }

    //test delete neighbour
    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    //test ajout favori
    @Test
    public void addNeighbourToFavoris() {
        Neighbour neighbourToFavori = service.getNeighbours().get(0);
        service.addfavori(neighbourToFavori);                           //ajout du voisin dans les favori

        assertTrue(neighbourToFavori.isFavori());                       //vérification si le voisin est un favori
    }

    //test delete favori
    @Test
    public void deleteNeighbourToFavoris() {
        Neighbour neighbourToFavoriDelete = service.getNeighbours().get(0);
        neighbourToFavoriDelete.setFavori(true);                                // inisiatlisation un favoris
        service.deleteFavori(neighbourToFavoriDelete);                          //supprime le voisin des favori

        assertFalse(neighbourToFavoriDelete.isFavori());                        //vérification si le voisin est un favori
    }

    //test d’ajout du voisin à la liste de favoris
    @Test
    public void checkToListFavori() {

        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourToFavori = service.getNeighbours().get(0);
        service.addfavori(neighbourToFavori);                           //ajout de l'utilisateur dans les favori
        List<Neighbour> favoris = neighbours.stream().filter(Neighbour::isFavori).collect(Collectors.toList()); //création de la liste de favoris

        assertTrue(favoris.contains(neighbourToFavori));                //vérification si le favori se trouve dans la liste de favori
    }

    //récuperer l'utilisateur de la liste par son id getNeighbourByID(long idNeighbour,list neighbours)
    @Test
    public void getNeighbourWithId() {
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbourToCheck = service.getNeighbours().get(0);
        long id = neighbourToCheck.getId();
        Neighbour neighbour;
        neighbour = RecupNeighbour.getNeighbourByID(id, neighbours);

        assertEquals(neighbour, neighbourToCheck);

    }


}
