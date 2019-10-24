package edu.spbspu.amd.morze_app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.*;
import android.graphics.*;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

import edu.spbspu.amd.morze_app.receiver.ViewReceiver;
import edu.spbspu.amd.morze_app.receiver.image_processing.ImageProcessing;
import edu.spbspu.amd.morze_app.sender.AppSender;
import edu.spbspu.amd.morze_app.sender.ViewSender;
import edu.spbspu.amd.morze_app.sender.ViewSenderParams;
import edu.spbspu.amd.morze_app.receiver.image_processing.*;


public class ActivityMain extends Activity implements View.OnTouchListener, OnCompletionListener
{

  // ********************************************
  // CONST
  // ********************************************

  static public final String APP_NAME = "MORZE";

  public static final int	VIEW_INTRO		   = 0;
  public static final int	VIEW_MENU 		   = 1;
  public static final int	VIEW_SENDER		   = 3;
  public static final int	VIEW_SENDER_PARAMS = 4;
  public static final int   VIEW_RECEIVER      = 5;


  // *************************************************
  // DATA
  // *************************************************
  int m_viewCur = -1;

  private AppIntro  m_appIntro;
  private AppMenu   m_appMenu;
  private AppSender m_appSender;

  private ViewIntro        m_viewIntro;
  private ViewMenu	       m_viewMenu;
  private ViewSender       m_viewSender;
  private ViewReceiver     m_viewReceiver;
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

    Log.d(ActivityMain.APP_NAME, "Screen size is " + m_screenW + " * " +  m_screenH);

    // Detect language
    String strLang = Locale.getDefault().getDisplayLanguage();
    int language;
    if (strLang.equalsIgnoreCase("english")) {
      Log.d(ActivityMain.APP_NAME, "LOCALE: English");
      language = AppIntro.LANGUAGE_ENG;
    } else if (strLang.equalsIgnoreCase("русский")) {
      Log.d(ActivityMain.APP_NAME, "LOCALE: Russian");
      language = AppIntro.LANGUAGE_RUS;
    } else {
      Log.d(ActivityMain.APP_NAME, "LOCALE unknown: " + strLang);
      language = AppIntro.LANGUAGE_UNKNOWN;
    }

    CameraPermision();

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

    /*
    // HACK!!!!! -> testing ImageProcessing
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;

    Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.drawable.test_image_processing_1, options);
    Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.test_image_processing_2, options);

    ImageProcessing ip = new ImageProcessing();
    ip.compareWithCurrentFrameImage(image1);
    int res = ip.compareWithCurrentFrameImage(image2);

    if (res != 0) {
      ImageProcessing.RGB newColor = ip.getNewAverageColor();
      Log.d(ActivityMain.APP_NAME, "Success image processing test!");
    }*/
  }

  private void CameraPermision()
  {
    // Here, thisActivity is the current activity
    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
              Manifest.permission.READ_CONTACTS)) {
      } else {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                1);

      }
    }
    else
      {
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
    } else if (m_viewCur == VIEW_MENU) {
      m_viewMenu = new ViewMenu(this);
      Log.d(ActivityMain.APP_NAME, "Switch to m_viewMenu");
      setContentView(m_viewMenu);
      m_viewMenu.start();
    } else if (m_viewCur == VIEW_SENDER) {
      m_viewSender = new ViewSender(this);
      Log.d(ActivityMain.APP_NAME, "Switch to sender view");
      setContentView(m_viewSender);
      m_viewSender.start();
    } else if (m_viewCur == VIEW_RECEIVER) {
      Log.d(ActivityMain.APP_NAME, "Switch to receiver's view");
      setContentView(R.layout.sample_view_camera);
      m_viewReceiver = new ViewReceiver(this, (TextureView) findViewById(R.id.textureView),
              (TextView) findViewById(R.id.decodedTextView), Camera.open(0));
    } else if (m_viewCur == VIEW_SENDER_PARAMS) {
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

    if (evt.getAction() == MotionEvent.ACTION_MOVE) {
      touchType = AppIntro.TOUCH_MOVE;
    } else if (evt.getAction() == MotionEvent.ACTION_UP) {
      touchType = AppIntro.TOUCH_UP;
    }

    if (m_viewCur == VIEW_INTRO) {
      return m_viewIntro.onTouch(x, y, touchType);
    } else if (m_viewCur == VIEW_MENU) {
      return m_viewMenu.onTouch(x, y, touchType);
    }

    return true;
  }

  public boolean onKeyDown(int keyCode, KeyEvent evt)
  {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (m_viewCur == VIEW_MENU) {
        setView(VIEW_INTRO);
        return true;
      } else if (m_viewCur == VIEW_RECEIVER) {
        m_viewReceiver.interrupt();
        setView(VIEW_MENU);
        return true;
      } else if (m_viewCur == VIEW_SENDER_PARAMS) {
        setView(VIEW_MENU);
        return true;
      } else if (m_viewCur == VIEW_SENDER) {
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
    if (m_viewCur == VIEW_INTRO) {
      m_viewIntro.start();
    } else if (m_viewCur == VIEW_MENU) {
      m_viewMenu.start();
    } else if (m_viewCur == VIEW_RECEIVER) {
      m_viewReceiver.onResume(Camera.open(0));
    }
  }

  protected void onPause()
  {
    // stop anims
    if (m_viewCur == VIEW_INTRO) {
      m_viewIntro.stop();
    } else if (m_viewCur == VIEW_MENU) {
      m_viewMenu.stop();
    } else if (m_viewCur == VIEW_RECEIVER) {
      m_viewReceiver.onPause();
    }

    // complete system
    super.onPause();
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
