package edu.spbspu.amd.morze_app;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.graphics.*;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Locale;

import edu.spbspu.amd.morze_app.receiver.ViewReceiver;
import edu.spbspu.amd.morze_app.sender.AppSender;
import edu.spbspu.amd.morze_app.sender.ViewSender;
import edu.spbspu.amd.morze_app.sender.ViewSenderParams;


public class ActivityMain extends Activity implements View.OnTouchListener, OnCompletionListener
{

  // ********************************************
  // CONST
  // ********************************************

  static public final boolean APP_RUN_MODE    = false;

  static public final String APP_NAME = "MORZE";

  public static final int	VIEW_INTRO		= 0;
  public static final int	VIEW_MENU 		= 1;
  public static final int	VIEW_SENDER		  = 3;
  public static final int	VIEW_SENDER_PARAMS		  = 4;
  public static final int   VIEW_RECEIVER   = 5;
  
  public static final int MODE_SOURCE_SHAPE	= 0;
  public static final int MODE_KNACK_PACK   = 1;

  // *************************************************
  // DATA
  // *************************************************
  int						    m_viewCur = -1;
  int               m_modeCur = -1;

  private AppIntro   m_appIntro;
  private AppMenu    m_appMenu;
  private AppSender m_appSender;

  private ViewIntro    m_viewIntro;
  private ViewMenu	   m_viewMenu;
  private ViewSender   m_viewSender;
  private ViewReceiver m_viewReceiver;
  private ViewSenderParams m_viewSenderParams;

  // screen dim
  private int        m_screenW;
  private int        m_screenH;

  // *************************************************
  // METHODS
  // *************************************************
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    //overridePendingTransition(0, 0);
    // No Status bar
    final Window win = getWindow();
    win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    // Application is never sleeps
    win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    Display display = getWindowManager().getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    m_screenW = point.x;
    m_screenH = point.y;

    Log.d(ActivityMain.APP_NAME, "Screen size is " + String.valueOf(m_screenW) + " * " +  String.valueOf(m_screenH) );

    // Detect language
    String strLang = Locale.getDefault().getDisplayLanguage();
    int language;
    if (strLang.equalsIgnoreCase("english"))
    {
      Log.d(ActivityMain.APP_NAME, "LOCALE: English");
      language = AppIntro.LANGUAGE_ENG;
    }
    else if (strLang.equalsIgnoreCase("русский"))
    {
      Log.d(ActivityMain.APP_NAME, "LOCALE: Russian");
      language = AppIntro.LANGUAGE_RUS;
    }
    else
    {
      Log.d(ActivityMain.APP_NAME, "LOCALE unknown: " + strLang);
      language = AppIntro.LANGUAGE_UNKNOWN;
    }
    // Create application intro
    m_appIntro = new AppIntro(this, language);
    // Create application menu
    m_appMenu = new AppMenu(this, language);
    //create app_sender
    m_appSender = new AppSender(this);

    // Create view
    if (m_viewCur == -1) {
      setView(VIEW_INTRO);
    } else {
      setView(m_viewCur);
    }
  }

  public AppIntro getAppIntro()
  {
    return m_appIntro;
  }
  public AppMenu getAppMenu()
  {
    return m_appMenu;
  }

  public AppSender getAppSender()
  {
    return m_appSender;
  }

  public int getScreenWidth() { return m_screenW; }
  public int getScreenHeight() { return m_screenH; }

  public void setView(int viewID) {
    if (m_viewCur == viewID) {
      Log.d(ActivityMain.APP_NAME, "setView: already set");
      return;
    }

    m_viewCur = viewID;
    if (m_viewCur == VIEW_INTRO) {
      m_viewIntro = new ViewIntro(this);
      setContentView(m_viewIntro);
    }
    if (m_viewCur == VIEW_MENU) {
      m_viewMenu = new ViewMenu(this);
      Log.d(ActivityMain.APP_NAME, "Switch to m_viewMenu");
      setContentView(m_viewMenu);
      m_viewMenu.start();
    }
    if (m_viewCur == VIEW_SENDER) {
      m_viewSender = new ViewSender(this);
      Log.d(ActivityMain.APP_NAME, "Switch to sender view");
      setContentView(m_viewSender);
      m_viewSender.start();
    }
    if (m_viewCur == VIEW_RECEIVER) {
      Log.d(ActivityMain.APP_NAME, "Switch to receiver's view");
      setContentView(R.layout.sample_view_camera);
      m_viewReceiver = new ViewReceiver(this, (SurfaceView) findViewById(R.id.surfaceView), Camera.open(0));
    }
    if (m_viewCur == VIEW_SENDER_PARAMS) {
      m_viewSenderParams = new ViewSenderParams(this);
      Log.d(ActivityMain.APP_NAME, "Switch to senderParams view");
      setContentView(R.layout.sample_view_sender_params);
      m_viewSenderParams.setParams((EditText) findViewById(R.id.editText4), (CheckBox) findViewById(R.id.repeatBtn),
              (Button) findViewById(R.id.buttonStart));
    }
  }

  protected void onPostCreate(Bundle savedInstanceState)
  {
    super.onPostCreate(savedInstanceState);
  }

  public void onCompletion(MediaPlayer mp)
  {
    Log.d(ActivityMain.APP_NAME, "onCompletion: Video play is completed");
  }

  public boolean onTouch(View v, MotionEvent evt)
  {
    int x = (int)evt.getX();
    int y = (int)evt.getY();
    int touchType = AppIntro.TOUCH_DOWN;

    if (evt.getAction() == MotionEvent.ACTION_MOVE)
      touchType = AppIntro.TOUCH_MOVE;
    if (evt.getAction() == MotionEvent.ACTION_UP)
      touchType = AppIntro.TOUCH_UP;

    if (m_viewCur == VIEW_INTRO)
      return m_viewIntro.onTouch( x, y, touchType);
    if (m_viewCur == VIEW_MENU)
      return m_viewMenu.onTouch(x, y, touchType);

    return true;
  }

  public boolean onKeyDown(int keyCode, KeyEvent evt)
  {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (m_viewCur == VIEW_MENU) {
        setView(VIEW_INTRO);
        return true;
      } 
      if (m_viewCur == VIEW_RECEIVER) {
        setView(VIEW_MENU);
        return true;
      }
      if (m_viewCur == VIEW_SENDER_PARAMS)
      {
        setView(VIEW_MENU);
        return true;
      }
      if (m_viewCur == VIEW_SENDER)
      {
        setView(VIEW_MENU);
        return true;
      }
    }

    boolean ret = super.onKeyDown(keyCode, evt);
    return ret;
  }

  protected void onResume()
  {
    super.onResume();
    if (m_viewCur == VIEW_INTRO)
      m_viewIntro.start();
    if (m_viewCur == VIEW_MENU)
      m_viewMenu.start();
    if (m_viewCur == VIEW_RECEIVER)
      m_viewReceiver.onResume(Camera.open(0));
  }

  protected void onPause()
  {
    // stop anims
    if (m_viewCur == VIEW_INTRO)
      m_viewIntro.stop();
    if (m_viewCur == VIEW_MENU)
      m_viewMenu.stop();
    if (m_viewCur == VIEW_RECEIVER)
      m_viewReceiver.onPause();

    // complete system
    super.onPause();
    //Log.d(ActivityMain.APP_NAME, "App onPause");
  }
  protected void onDestroy()
  {
    if (m_viewCur == VIEW_MENU)
    {
      //m_viewMenu.onDestroy();
    }

    super.onDestroy();
  }
}
