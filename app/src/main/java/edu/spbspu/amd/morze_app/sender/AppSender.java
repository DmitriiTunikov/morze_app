package edu.spbspu.amd.morze_app.sender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import edu.spbspu.amd.morze_app.ActivityMain;
import edu.spbspu.amd.morze_app.AppIntro;

public class AppSender {
    private ActivityMain m_ctx;
    private Timer m_timer = null;
    public static final long delay = 5000L;
    private static int timer_i = 0;

    // rect
    public static long m_point_time = 3000L;
    private ArrayList<Integer> colors;
    private int m_cur_color_idx = -1;
    private boolean m_need_repeat;

    public void setText(char[] text)
    {
        //rerash colors
        colors = new ArrayList<>();

        colors.add(Color.WHITE);
        colors.add(Color.BLACK);

        for (char sym : text)
        {
            if (sym == '.')
            {
                colors.add(Color.WHITE);
                colors.add(Color.BLACK);
            }
            else if (sym == '-')
            {
                colors.add(Color.WHITE);
                colors.add(Color.WHITE);
                colors.add(Color.WHITE);
                colors.add(Color.BLACK);
            }
            else if (sym == '&')
            {
                colors.add(Color.BLACK);
                colors.add(Color.BLACK);
                colors.add(Color.BLACK);
            }
        }

        m_cur_color_idx = 0;
    }

    public void setPointTime(long pointTime)
    {
        m_point_time = pointTime;
    }

    public void setNeedRepeat(boolean need_repeat)
    {
        m_need_repeat = need_repeat;
    }

    private void initTimer()
    {
        TimerTask m_task = new TimerTask() {
            public void run() {
                if (m_cur_color_idx == colors.size() - 1 && !m_need_repeat) {
                    m_cur_color_idx = -2;
                    cancel();
                } else {
                    if (m_cur_color_idx == colors.size() - 1)
                        m_cur_color_idx = 2;
                    else
                        m_cur_color_idx++;
                }
                //Log.d(ActivityMain.APP_NAME, "change color:" + m_cur_color_idx);
            }
        };
        m_timer.scheduleAtFixedRate(m_task, delay, m_point_time);
    }

    public void refreshSender(long point_time, boolean need_to_repeat, char[] text)
    {
        Log.d(ActivityMain.APP_NAME, "refresh sender");
        if (m_timer != null) {
            m_timer.cancel();
        }
        m_timer = new Timer("Timer" + (timer_i++));
        Log.d(ActivityMain.APP_NAME, "Timer " + timer_i);
        setNeedRepeat(need_to_repeat);
        setPointTime(point_time);
        setText(text);
        m_cur_color_idx = -1;
        initTimer();
    }

    public AppSender(ActivityMain ctx)
    {
        m_ctx = ctx;
    }

    public boolean	onTouch(int x, int y, int touchType)
    {
        if (touchType != AppIntro.TOUCH_DOWN)
            return false;

        m_ctx.setView(ActivityMain.VIEW_MENU);
        m_timer.cancel();
        return true;
    }

    public void drawCanvas(Canvas canvas) throws InterruptedException {
        //THE END
        if (m_cur_color_idx == -2) {
            canvas.drawColor(Color.RED);
            return;
        }
        else if (m_cur_color_idx == -1)
        {
            canvas.drawColor(Color.BLACK);
            return;
        }

        canvas.drawColor(colors.get(m_cur_color_idx));
    }
}
