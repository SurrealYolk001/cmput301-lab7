package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import static java.lang.Thread.sleep;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
        scenario.getScenario().recreate();
    }


    @Test
    public void testAddCity(){

        // Click on the Add City Button
        onView(withId(R.id.button_add)).perform(click());

        // type 'Edmonton' in the editText
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));

        // CLick on confirm
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if text 'Edmonton' is matched with any of the text displayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));

    }

    @Test
    public void testClearCity(){
        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());

        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));

        onView(withId(R.id.button_confirm)).perform(click());

        // Add another city to the list
        onView(withId(R.id.button_add)).perform(click());

        onView(withId(R.id.editText_name)).perform(typeText("Vancouver"));

        onView(withId(R.id.button_confirm)).perform(click());

        // clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView() {
        // add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // check if in the Adapter view (given id of that adapter view) there is a data
        // (which is an instance of String) located at position 0.
        // If this data matches the text we provided then Voila! Our test should pass
        // You can also use anything() in place of is(instanceOf(String.class))

        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches(withText("Edmonton")));
    }

    @Test
    public void testSwitchScreen() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // go to ShowActivity screen
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // if "back" button present, that means we must have correctly switched
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowActivityCity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Sylhet"));
        onView(withId(R.id.button_confirm)).perform(click());

        // go to ShowActivity screen
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // if "Sylhet" button present, that means we are correctly displaying the city name in the ShowActivity screen
        onView(withText("Sylhet")).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButton() throws InterruptedException {

        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Dhaka"));
        onView(withId(R.id.button_confirm)).perform(click());

        // go to ShowActivity screen
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // click 'back' button
        onView(withId(R.id.button_back)).perform(click());
        sleep(2000);
        // check for 'add city' button; if present, we came back successfully
        onView(withText("Dhaka")).check(matches(isDisplayed()));
    }
}
