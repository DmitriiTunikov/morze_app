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
    private Timer m_timer;

    // rect
    private int m_point_time;
    private ArrayList<Integer> colors;
    private int m_cur_color_idx;
    private boolean m_need_repeat;

    public void setText(char[] text)
    {
        //rerash colors
        colors = new ArrayList<>();
        //add 10 black point for waiting receiver ready
        for (int i = 0; i < 10; i++)
        {
            colors.add(Color.BLACK);
        }

        //add first white point
        colors.add(Color.WHITE);

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

    public void setPointTime(int pointTime)
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
                    m_cur_color_idx = -1;
                    cancel();
                }
                else
                    m_cur_color_idx = (m_cur_color_idx + 1) % colors.size();
                Log.d(ActivityMain.APP_NAME, "change color:" + m_cur_color_idx);
            }
        };
        m_timer.scheduleAtFixedRate(m_task, 100L, m_point_time);
    }

    public void refreshSender(int point_time, boolean need_to_repeat, char[] text)
    {
        setNeedRepeat(need_to_repeat);
        setPointTime(point_time);
        setText(text);
        m_cur_color_idx = 0;
        initTimer();
    }

    public AppSender(ActivityMain ctx)
    {
        m_ctx = ctx;
        m_timer = new Timer("Timer");
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
        if (m_cur_color_idx == -1) {
            canvas.drawColor(Color.RED);
            return;
        }

        canvas.drawColor(colors.get(m_cur_color_idx));
    }
}
