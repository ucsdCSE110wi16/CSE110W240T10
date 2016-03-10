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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


/*
 * The following is a User Scenario test case that creates an item to be posted,
 * it does so in the following way:
 *
 * First: it fill out only some of the fields than press the finish button
 * than checks for an error message from setError stating all item fields
 * need to be filled.
 *
 * Second: presses submit again when only third of the buttons are clicked and checks
 * for error messages from unfilled text fields.
 *
 * Third: fills out all required fields than presses the finish button
 * and checks for an alert dialog message stating that the item has been posted.
 *
 * Fourth: The new Items very visibly added in our database, Firebase
 *
 */

@RunWith(AndroidJUnit4.class)
public class CreateListingActivityTest {

    @Rule
    public ActivityTestRule<CreateListingActivity> cActivityRule =
            new ActivityTestRule<>(CreateListingActivity.class);

    private String itemNameToBeTyped;
    private String itemPriceToBeTyped;
    private String itemDescToBeTyped;
    private String itemMinRentToBeTyped;
    private String itemDepositToBeTyped;
    private String itemZipToBeTyped;
    //private String itemContactToBeTyped;

    @Before
    public void initValidString() {
        //strings to be typed by tester
        itemNameToBeTyped = "Espresso Test Item";
        itemPriceToBeTyped = "10.99";
        itemDescToBeTyped = "This is a test description of for the Espresso Test Item ";
        itemMinRentToBeTyped = "4";
        itemDepositToBeTyped = "45";
        itemZipToBeTyped = "90210";
      //  itemContactToBeTyped = "test contact info input";


        //Database.setContext();
        //Database db = Database.getInstance();
        //User user = new User();
        //db.requestLogin("a@a.com", "a", user);
    }




    @Test
    public void setTextFields() throws InterruptedException {

        //set item name
        onView(withId(R.id.itemNameField_CL))
                .perform(typeText(itemNameToBeTyped), closeSoftKeyboard());

        //set item price
        onView(withId(R.id.itemPriceField_CL))
                .perform(typeText(itemPriceToBeTyped), closeSoftKeyboard());

        //select the price length from drop down box
        onView(withId(R.id.itemPriceSpinner_CL)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Per Day"))).perform(click());


        //presses submit button when only half the required fields are set
        onView(withId(R.id.finishButton_CL)).perform(ViewActions.scrollTo(), click());
        onView(withId(R.id.zipCodeField_CL)).check(matches(hasErrorText("Item Zip Code is required!")));

        //set item description
        onView(withId(R.id.itemDescriptionField_CL))
                .perform(ViewActions.scrollTo(),typeText(itemDescToBeTyped), closeSoftKeyboard());

        //selects item category from drop down box
        onView(withId(R.id.categorySpinner_CL)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Other"))).perform(click());

        //set min rent duration
        onView(withId(R.id.minRentDuration_CL))
                .perform(typeText(itemMinRentToBeTyped), closeSoftKeyboard());

        //set min rent rate
        onView(withId(R.id.minRentSpinner_CL)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Day(s)"))).perform(click());

        //clicks price negotiable
        onView(withId(R.id.priceNegtble)).perform(click());


        //clicks price negotiable
        onView(withId(R.id.itemBuyout)).perform(click());

        //clicks price negotiable
        onView(withId(R.id.depositYes)).perform(click());

        //presses submit button when only third of  the required fields are set
        onView(withId(R.id.finishButton_CL)).perform(ViewActions.scrollTo(), click());
       onView(withId(R.id.zipCodeField_CL)).check(matches(hasErrorText("Item Zip Code is required!")));

        //set the deposit amount
        onView(withId(R.id.amountOfDeposit_CL))
                .perform(ViewActions.scrollTo(),typeText(itemDepositToBeTyped), closeSoftKeyboard());

        onView(withId(R.id.finishButton_CL)).perform(click());
        onView(withId(R.id.zipCodeField_CL)).check(matches(hasErrorText("Item Zip Code is required!")));

        //enter the zip code
        onView(withId(R.id.zipCodeField_CL))
                .perform(typeText(itemZipToBeTyped), closeSoftKeyboard());



        //set the contact information(removed)
        //onView(withId(R.id.contactInfoField_CL))
         //       .perform(typeText(itemContactToBeTyped), closeSoftKeyboard());


        //wait for error message to go away and show finish button again
        Thread.sleep(2000);
        onView(withId(R.id.finishButton_CL)).perform(ViewActions.scrollTo(), click());

        //wait for alert dialog to appear
        Thread.sleep(5000);
        onView(withText("Your Item has been Posted!")).check(matches(isDisplayed()));
        Thread.sleep(2000);


    }
}