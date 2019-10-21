package edu.spbspu.amd.morze_app.sender;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import edu.spbspu.amd.morze_app.ActivityMain;
import edu.spbspu.amd.morze_app.AppIntro;
import edu.spbspu.amd.morze_app.R;
import edu.spbspu.amd.morze_app.morzeCoder.MorzeСoder;

/**
 * TODO: document your custom view class.
 */
public class ViewSenderParams extends View {
    // CONST
    private static final int UPDATE_TIME_MS = 30;
    ActivityMain m_ctx;
    public EditText m_text;
    public CheckBox m_repeat;
    public Button m_startBtn;

    public ViewSenderParams(ActivityMain ctx)
    {
        super(ctx);
        m_ctx = ctx;
    }

    public boolean onTouch(View v)
    {
        if (v.getId() == m_startBtn.getId())
        {
            AppSender appSender = m_ctx.getAppSender();
            appSender.refreshSender(100, m_repeat.isChecked(), MorzeСoder.encode(m_text.getText().toString()));
            m_ctx.setView(ActivityMain.VIEW_SENDER);
        }

        return true;
    }

    /*
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == m_startBtn.getId())
        {
            AppSender appSender = m_ctx.getAppSender();
            appSender.refreshSender(100, m_repeat.isChecked(), MorzeСoder.encode(m_text.getText().toString()));
            m_ctx.setView(ActivityMain.VIEW_SENDER);
            return true;
        }
        return false;
    }*/
}
