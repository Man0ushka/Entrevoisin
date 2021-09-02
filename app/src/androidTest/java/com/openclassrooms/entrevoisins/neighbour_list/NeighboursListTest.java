
package com.openclassrooms.entrevoisins.neighbour_list;




import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ViewNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.ShowDetailAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    @Rule
    public final IntentsTestRule<ListNeighbourActivity> mActivityRule =
            new IntentsTestRule<>(ListNeighbourActivity.class);


    // This is fixed
    private static final int ITEMS_COUNT = 12;

    private static final int FAVORITE_ITEMS_COUNT = 0;

    private ListNeighbourActivity mActivity;


    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }
    /**
     * We ensure that the favorites tab is empty
     */
    @Test
    public void myFavoriteNeighboursList_shouldBeEmpty() {
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(FAVORITE_ITEMS_COUNT));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }
//
    /**
     * When a neighbour is clicked, the new screen is shown
     */
    @Test
    public void myNeighboursList_clickAction_shouldOpenDetails(){
        // Init new action to show details
        ShowDetailAction showDetailAction = new ShowDetailAction();
        // Click on name on the list of neighbours
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(4,showDetailAction));
        // Save the name clicked in nameList
        String nameList = showDetailAction.getName();
        // Check if viewing detail activity is launched
        intended(hasComponent(ViewNeighbourActivity.class.getName()));
        // Check if name displayed is the same as name clicked
        onView(ViewMatchers.withId(R.id.name_card_text)).check(matches(withText(nameList)));
    }

    /**
     * When the add to favorite button is clicked, the neighbour is added to the favorite list and displayed in the right tab
     */
    @Test
    public void myNeighbourView_clickAddToFavorite_shouldDisplayInFavoritesTab(){
        // Init new action to show details
        ShowDetailAction showDetailAction = new ShowDetailAction();
        // Click on name on the list of neighbours
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(4,showDetailAction));
        // Save the name clicked in nameList
        String nameList = showDetailAction.getName();
        // Click on the add favorite button
        onView(ViewMatchers.withId(R.id.add_favorite)).perform(click());
        // Go to main activity
        pressBack();
        // Check that the favorite neighbour list has one more member
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(FAVORITE_ITEMS_COUNT+1));
        // Check that the right neighbour is displayed
        // Click on favorites tab
        onView(withText("Favorites")).perform(click());
        showDetailAction = new ShowDetailAction();
        // Click on first item
        onView(ViewMatchers.withId(R.id.list_favorites)).perform(RecyclerViewActions.actionOnItemAtPosition(0,showDetailAction));
        // Check that name matches
        onView(ViewMatchers.withId(R.id.name_card_text)).check(matches(withText(nameList)));
    }
    /**
     //     * When we delete an item, the item is no more shown in favorites
     //     */
    @Test
    public void myFavoriteNeighboursList_deleteAction_shouldRemoveItem() {

        onView(withText("Favorites")).perform(click());
        // Given : We remove the element at position 0
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(1));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_favorites))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 0
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(0));


    }
}