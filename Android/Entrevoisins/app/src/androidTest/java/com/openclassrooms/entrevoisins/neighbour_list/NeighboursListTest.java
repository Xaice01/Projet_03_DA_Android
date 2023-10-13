
package com.openclassrooms.entrevoisins.neighbour_list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertFalse;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static final int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    private NeighbourApiService service;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        service = DI.getNewInstanceApiService();
    }

    //pour éviter les erreurs de double View
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    //pour pouvoir récupérer tous les items d'une vue
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new org.hamcrest.TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(matches(hasMinimumChildCount(1)));
    }


    //test vérifiant que lorsqu’on clique sur un élément de la liste, l’écran de détails est bien lancé
    @Test
    public void onClickItem_Show_Details() {
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));  //simulate the click on frist item of recyclerView

        onView(withId(R.id.constraintLayout_NeighbourViewActivity)).check(matches(isDisplayed()));
    }

    //test vérifiant qu’au démarrage de l'écran Détail, le TextView indiquant le nom de l’utilisateur en question est bien rempli
    // + l'image de l'Avatar exist + le button de favori exist + un bouton de retour à l'élément précédent
    @Test
    public void check_Detailview() {
        onView(withIndex(withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        String name = service.getNeighbours().get(0).getName();

        onView(withId(R.id.Text_Prenom_Neighbour_Avatar)).check(matches(withText(name)));  //check if the name of TextView is "Caroline"
        onView(withId(R.id.imageView_Avatar)).check(matches(isDisplayed()));                //check if the picture is created
        onView(withId(R.id.floatingActionButton_favoris)).check(matches(isDisplayed()));    //check if the button favori is created
        onView(withId(R.id.imageButton_Arrow_Back)).check(matches(isDisplayed()));          //check if the button Back is created
    }

    //test vérifiant que l’onglet Favoris n’affiche que les voisins marqués comme favoris +(ajout d'un voisin en favori)
    @Test
    public void check_List_Of_Favori() {
        Neighbour neighbour;

        ViewInteraction tabView2 = onView(
                allOf(childAtPosition(childAtPosition(withId(R.id.tabs), 0), 1)));          //sur l'onglet favori
        tabView2.perform(click());
        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(0));  //check if list of favori is equal at 0

        ViewInteraction tabView1 = onView(allOf(childAtPosition(childAtPosition(withId(R.id.tabs), 0), 0)));                //sur l'onglet Neighbour
        tabView1.perform(click());

        ///------------------------------------///
        ViewInteraction recyclerView = onView(allOf(withIndex(withId(R.id.list_neighbours), 0)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction floatingActionButton = onView(allOf(withId(R.id.floatingActionButton_favoris)));        //add favori Neighbour "Jack"
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageButton = onView(allOf(withId(R.id.imageButton_Arrow_Back)));              //callback
        appCompatImageButton.perform(click());

        tabView2.perform(click());                                                                              //positionnement sur l'onglet favori

        onView(withIndex(withId(R.id.list_neighbours), 1)).check(withItemCount(1));            //check if list of favori is equal at 1
        neighbour = service.getNeighbours().get(1);                                                              //neighbour equal at "Jack"
        onView(withIndex(withId(R.id.item_list_name), 1)).check(matches(withText(neighbour.getName())));


        neighbour = service.getNeighbours().get(3);
        assertFalse(neighbour.isFavori());                                                                      //check if neighbour is not a favori
        onView(withIndex(allOf(withId(R.id.item_list_name), withText(neighbour.getName()), isNotChecked()), 1)); //check if neighbour is not in a list of favori
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withIndex(withId(R.id.list_neighbours), 0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withIndex(withId(R.id.list_neighbours), 0)).check(withItemCount(ITEMS_COUNT - 1));
    }


}