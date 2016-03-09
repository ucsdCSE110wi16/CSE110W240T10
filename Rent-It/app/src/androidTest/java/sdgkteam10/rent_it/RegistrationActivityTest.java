package sdgkteam10.rent_it;

import junit.framework.TestCase;

import java.util.UUID;

import android.app.Instrumentation;
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
 * The following tests registering a new user for Rent-it, it does so in the following way
 *
 * First: it fill out only some of the fields than press the finish button
 * than it waits to see it moves on to the main activity, which it wont since
 * all field marked with an asterik are required
 *
 * Second: Fills out all field and clicks submit,checks to see
 * if we moved onto new activity
 *
 * Third: There is a visual indication in our database that a new user
 * was successfully created.
 *
 */

@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {

    @Rule
    public ActivityTestRule<RegistrationActivity> rActivityRule =
            new ActivityTestRule<>(RegistrationActivity.class);

    private String nameToBeTyped;
    private String emailToBeTyped;
    private String passwordToBeTyped;
    private String address1ToBeTyped;
    private String address2ToBeTyped;
    private String cityToBeTyped;
    private String zipToBeTyped;
    private String phoneToBeTyped;


    @Before
    public void initValidString() {
        //strings to be typed by tester
        nameToBeTyped = "Espresso Tester";
        passwordToBeTyped = "espresso";
        address1ToBeTyped = "123 Espresso Street";
        address2ToBeTyped = "Apt. 789";
        cityToBeTyped = "San Diego";
        zipToBeTyped = "92122";
        phoneToBeTyped = "1238675309";

        //generate a random email
        String rand = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        emailToBeTyped = rand + "@espresso.com";
    }


    @Test
    public void setTextFields() throws InterruptedException {
        Thread.sleep(1000);

        //set new user name
        onView(withId(R.id.fill_name))
                .perform(typeText(nameToBeTyped), closeSoftKeyboard());

        //set new user email
        onView(withId(R.id.fill_email))
                .perform(ViewActions.scrollTo(),typeText(emailToBeTyped), closeSoftKeyboard());

        //presses submit button when only half the fields are filled out
        onView(withId(R.id.submit_button)).perform(ViewActions.scrollTo(), click());


        //set user password
        onView(withId(R.id.fill_pw1))
                .perform(ViewActions.scrollTo(),typeText(passwordToBeTyped), closeSoftKeyboard());

        //reenter user password
        onView(withId(R.id.fill_pw2))
                .perform(ViewActions.scrollTo(),typeText(passwordToBeTyped), closeSoftKeyboard());

        //set address 1
        onView(withId(R.id.fill_address1))
                .perform(ViewActions.scrollTo(),typeText(address1ToBeTyped), closeSoftKeyboard());

        //set address2
        onView(withId(R.id.fill_address2))
                .perform(ViewActions.scrollTo(),typeText(address2ToBeTyped), closeSoftKeyboard());

        //type city name
        onView(withId(R.id.fill_city))
                .perform(ViewActions.scrollTo(),typeText(cityToBeTyped), closeSoftKeyboard());


        //select the state from drop down
        onView(withId(R.id.fill_state)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("California"))).perform(click());


        //enter the zip code
        onView(withId(R.id.fill_zip))
                .perform(ViewActions.scrollTo(),typeText(zipToBeTyped), closeSoftKeyboard());

        //enter the phone number
        onView(withId(R.id.fill_phone))
                .perform(ViewActions.scrollTo(),typeText(phoneToBeTyped), closeSoftKeyboard());

        //presses submit button when everything is filled out
        onView(withId(R.id.submit_button)).perform(ViewActions.scrollTo(), click());


        //wait for database to create a user
        Thread.sleep(10000);

        //verified in database under *databaseURL*/users
    }
}