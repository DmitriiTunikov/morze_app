package edu.spbspu.amd.morze_app.sender;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import edu.spbspu.amd.morze_app.ActivityMain;

public class ViewSender extends View {
    class RedrawHandler extends Handler
    {
        ViewSender m_ViewSender;

        public RedrawHandler(ViewSender v)
        {
            m_ViewSender = v;
        }

        public void handleMessage(Message msg)
        {
            m_ViewSender.update();
            m_ViewSender.invalidate();
        }

        public void sleep(long delayMillis)
        {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };

    // CONST
    private static final int UPDATE_TIME_MS = 30;


    // DATA
    ActivityMain m_app;
    AppSender m_app_sender;
    RedrawHandler   m_handler;
    long			      m_startTime;
    boolean			    m_active;

    // METHODS
    public ViewSender(ActivityMain app)
    {
        super(app);
        m_app = app;
        m_app_sender = app.getAppSender();

        m_handler 	= new RedrawHandler(this);
        m_startTime = 0;
        m_active 	= false;
        setOnTouchListener(app);
    }
    public boolean performClick()
    {
        boolean b = super.performClick();
        return b;
    }

    public void start()
    {
        m_active 	= true;
        m_handler.sleep(UPDATE_TIME_MS);
    }
    public void stop()
    {
        m_active 	= false;
        //m_handler.sleep(UPDATE_TIME_MS);
    }

    public void update()
    {
        if (!m_active)
            return;
        // send next update to game
        if (m_active)
            m_handler.sleep(UPDATE_TIME_MS);
    }
    public boolean onTouch(int x, int y, int evtType)
    {
        AppSender app = m_app.getAppSender();
        return app.onTouch(x,  y, evtType);
    }

    public void onDraw(Canvas canvas)
    {
        try {
            m_app_sender.drawCanvas(canvas);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
