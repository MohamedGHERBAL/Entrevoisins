
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollRight;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

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
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * Open DetailNeighbourActivity when click on Neighbour from NeighbourFragment
     * AND check if the Neighbour name is correct on DetailNeighbourActivity
     */
    @Test
    public void launchDetailNeighbourANDCheckName_WithSuccess() {
        // Perform click on item at position 0
        onView(allOf(withId (R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition (0, click()));
        // And check if name_1 in DetailNeighbourActivity is displayed
        onView(withId(R.id.name_1)).check(matches (isDisplayed ()));
    }

    /**
     * Test for Favorite
     */
    @Test
    public void addNeighboursToFavoriteList() {
        // Start app and perform click on first item/neighbour from the list
        onView(allOf(withId (R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition (0, click()));
        // Check if the app is in DetailNeighbourActivity
        onView(allOf(withId(R.id.activity_detail), isDisplayed()))
                .check(matches(isDisplayed()));
        // Perform click on FloattingActionButton Favorite and go to back page
        onView(allOf(withId(R.id.fab), isDisplayed()))
                .perform(click());
        pressBack();
        // Perform scrollRight to show the Favorite fragment
        // WARNING! Don't use "swipeLeft()" instead of "scrollRight()"
        // This change causes the "AmbiguousViewMatcherException" error.
        onView(allOf(withId (R.id.container), isDisplayed()))
                .perform(scrollRight());
        // Finish by check if there is one neighbour on the favorite
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(1));
    }
}
