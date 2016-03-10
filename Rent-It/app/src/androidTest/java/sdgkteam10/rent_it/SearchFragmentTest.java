package sdgkteam10.rent_it;

import junit.framework.TestCase;


import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


/*
 * The following is a User Scenario test for searching of items in the database
 *
 * First: it will wait 10 seconds or so for the items to populate locally from the
 * database.
 *
 * Second: it will search varies items based on item name, item description, item category,
 * as well as item zip code.
 *
 * Third: it will display these relevant items. if no items are found it will display
 * "no results found :(" message
 */

@RunWith(AndroidJUnit4.class)
public class SearchFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> cActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    private String search1ToBeTyped;
    private String search2ToBeTyped;
    private String search3ToBeTyped;
    private String search4ToBeTyped;
    private String search5ToBeTyped;

    @Before
    public void initValidString() {

        //strings to be typed by tester
        search1ToBeTyped = "mustang\n";
        search2ToBeTyped = "90210\n";
        search5ToBeTyped = "vehicle\n";
        search4ToBeTyped = "grass\n";
        search3ToBeTyped = "uag9uj\n";
    }

    @Test
    public void setSearchFields() throws InterruptedException {


        //wait for initial items to populate from database
        Thread.sleep(15000);

        //search by item name
        onView(withId(R.id.searchView_S))
                .perform(typeText(search1ToBeTyped), closeSoftKeyboard());



        Thread.sleep(5000);
        //search by partial zip code first delete previous entry
        onView(withId(R.id.searchView_S))
                .perform(pressKey(67), pressKey(67),pressKey(67),pressKey(67), pressKey(67),
                        pressKey(67),pressKey(67),closeSoftKeyboard());


        //search by zip code
        onView(withId(R.id.searchView_S))
                .perform(typeText(search2ToBeTyped), closeSoftKeyboard());


        Thread.sleep(5000);


        //press backspace to remove previous search
        onView(withId(R.id.searchView_S))
                .perform(pressKey(67), pressKey(67), pressKey(67), pressKey(67), pressKey(67),
                        pressKey(67), pressKey(67), closeSoftKeyboard());

        //search by item category
        onView(withId(R.id.searchView_S))
                .perform(typeText(search3ToBeTyped), closeSoftKeyboard());

        Thread.sleep(5000);


        //backspace
        onView(withId(R.id.searchView_S))
                .perform(pressKey(67), pressKey(67), pressKey(67), pressKey(67), pressKey(67),
                        pressKey(67), pressKey(67), closeSoftKeyboard());

        //search by description
        onView(withId(R.id.searchView_S))
                .perform(typeText(search4ToBeTyped), closeSoftKeyboard());

        Thread.sleep(7000);


        //backspace
        onView(withId(R.id.searchView_S))
                .perform(pressKey(67), pressKey(67), pressKey(67), pressKey(67), pressKey(67),
                        pressKey(67), pressKey(67), closeSoftKeyboard());


        //search by category
        onView(withId(R.id.searchView_S))
                .perform(typeText(search5ToBeTyped), closeSoftKeyboard());

        Thread.sleep(7000);


        //click on the list view
        //onData(anything()).inAdapterView(withId(R.id.listView_S)).atPosition(0).perform(click());
        //Thread.sleep(9000);
    }


}