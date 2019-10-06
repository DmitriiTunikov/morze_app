/*
 * This is an example test project created for students work
 * project : Knack Pask 2D
 *
 *
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 *
 * @author Vladislav Shubnikov
 *
 */

package edu.spbspu.amd.morze_app;

// imports

import com.robotium.solo.Solo;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


// class
@RunWith(AndroidJUnit4.class)
public class KnackPackTest
{

  // ******************************************************
  // Data
  // ******************************************************

  private Solo solo = null;

  // ******************************************************
  // Methods
  // ******************************************************

  @Rule
  public ActivityTestRule<ActivityMain> activityTestRule =
      new ActivityTestRule<>(ActivityMain.class);


  @Before
  public void setUp() throws Exception {
    //setUp() is run before a test case is started.
    //This is where the solo object is created.

    solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityTestRule.getActivity());
  }

  @After
  public void tearDown() throws Exception {
    if (solo != null)
      solo.finishOpenedActivities();
  }

  @Test
  public void testRunPacking() throws Exception {

    // wait before start tests
    final int WAIT_LARGE_TIME_MS = 6000;
    solo.sleep(WAIT_LARGE_TIME_MS);

    // get activity
    Activity acti = solo.getCurrentActivity();
    ActivityMain activityMain = (ActivityMain) acti;

    AppPlay appPlay = activityMain.getAppPlay();

    ShapeSrc shapes = appPlay.m_shapes;

    final int INDEX_0 = 0;
    float points[] = shapes.getShape(INDEX_0);
    //GeoFeatures features = new GeoFeatures();
    GeoFeatures features = appPlay.m_features;

    final float difPerimiter = Math.abs(features.m_perimeter - 463.895f);
    assertTrue(difPerimiter < 2.0f);
    final float difCx = Math.abs(features.m_cx - 11.302f);
    assertTrue(difCx < 2.0f);
    final float difCy = Math.abs(features.m_cy - (-46.137f));
    assertTrue(difCy < 2.0f);

    final float difRadMin = Math.abs(features.m_radMin - (16.3f));
    assertTrue(difRadMin < 2.0f);
    final float difRadMax = Math.abs(features.m_radMax - (102.44f));
    assertTrue(difRadMax < 2.0f);

    final int MAX_ATTEMPTS = 1024 * 2;


    KnackPackRect knackPack = appPlay.m_packRect;

    knackPack.startAttempts(MAX_ATTEMPTS);
    int res = 1;
    int iter;
    for (iter = 0; (iter < MAX_ATTEMPTS) && (res == 1); iter++) {
      if ((iter & 127) == 127) {
        Log.d(ActivityMain.APP_NAME, "Iteration = " + String.valueOf(iter));
      }
      res = knackPack.doAttempt();
    }
    knackPack.stopAttempts();

    final int NUM_SHAPES_EXPECTED = 55;
    final int numShapes = knackPack.getNumObjects();
    Log.d(ActivityMain.APP_NAME, "Num shapes = " + String.valueOf(numShapes));
    assertTrue(numShapes >= NUM_SHAPES_EXPECTED);

  }
  @Test
  public void testRunUi() throws Exception
  {
    final int WAIT_MS = 400;
    final int WAIT_LARGE_TIME_MS = 3000;

    Rect rect;
    RectF rectf;
    int xClick, yClick;

    solo.unlockScreen();

    solo.assertCurrentActivity("wrong activity", ActivityMain.class);

    // wait for 3 sec
    Log.d(ActivityMain.APP_NAME, "TestRunUI. Wait 3 sec...");
    solo.sleep(WAIT_LARGE_TIME_MS);

    // travel thru app screens
    // get activity
    Activity acti = solo.getCurrentActivity();
    ActivityMain activityMain = (ActivityMain) acti;

    AppPlay appPlay = activityMain.getAppPlay();
    AppMenu appMenu = activityMain.getAppMenu();
    AppIntro appIntro = activityMain.getAppIntro();

    // ViewIntro viewIntro = activityMain.getViewIntro();
    // solo.clickOnView(viewIntro);

    // visit to main menu
    int isReady = appIntro.m_canPressButtons;
    // System.out.printf("%s. AppIntro. Tested app is ready to press buttons = %d", ActivityMain.APP_NAME, isReady);
    // System.out.println(ActivityMain.APP_NAME + "AppIntro. Tested app is ready to press buttons = " + String.valueOf(isReady));
    if (isReady == 1)
      Log.d(ActivityMain.APP_NAME, "AppIntro. Tested app is ready to press buttons");
    else
      Log.d(ActivityMain.APP_NAME, "AppIntro. Tested app is NOT ready to press buttons: waiting...");
    while (isReady == 0)
    {
      isReady = appIntro.m_canPressButtons;
    }
    // System.out.printf("%s. AppIntro. Emulate press buttons in tested app", ActivityMain.APP_NAME);
    Log.d(ActivityMain.APP_NAME, "AppIntro. Emulate press button in tested app");

    rectf = appIntro.m_rectBtnStart;
    xClick = (int)(rectf.left + rectf.right) / 2;
    yClick = (int)(rectf.top + rectf.bottom) / 2;
    // System.out.printf("%s. AppIntro. Button coordinates to press = (%d, %d)", ActivityMain.APP_NAME, xClick, yClick);
    Log.d(ActivityMain.APP_NAME, "AppIntro. Click in point = " + String.valueOf(xClick) + ", " + String.valueOf(yClick));

    solo.clickOnScreen(xClick, yClick);
    solo.sleep(WAIT_MS);


    // visit single shape screen
    rect = appMenu.m_rectShow;
    xClick = (rect.left + rect.right) / 2;
    yClick = (rect.top + rect.bottom) / 2;

    solo.clickOnScreen(xClick, yClick);
    solo.sleep(WAIT_MS);

    // change 3 models
    final int w = activityMain.getScreenWidth();
    final int h = activityMain.getScreenHeight();

    final int NUM_SHAPES_TO_SHOW = 3;
    for (int i = 0; i < NUM_SHAPES_TO_SHOW; i++)
    {
      xClick = w * 3 / 4;
      yClick = h / 2;

      solo.clickOnScreen(xClick, yClick);
      solo.sleep(WAIT_MS);
    }

    // return to menu
    solo.goBack();
    solo.sleep(WAIT_MS);


    // Visit pack screen
    rect = appMenu.m_rectPack;
    xClick = (rect.left + rect.right) / 2;
    yClick = (rect.top + rect.bottom) / 2;

    solo.clickOnScreen(xClick, yClick);
    solo.sleep(WAIT_MS);

    // click to start packing
    xClick = w / 2;
    yClick = h / 2;

    solo.clickOnScreen(xClick, yClick);
    solo.sleep(WAIT_LARGE_TIME_MS);

    // click to stop packing
    solo.clickOnScreen(xClick, yClick);
    solo.sleep(WAIT_MS);

    // go back to main menu
    solo.goBack();
    solo.sleep(WAIT_MS);

    // go back to intro screen
    solo.goBack();
    solo.sleep(WAIT_MS);
  }


}
