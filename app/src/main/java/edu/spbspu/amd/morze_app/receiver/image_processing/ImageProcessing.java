package edu.spbspu.amd.morze_app.receiver.image_processing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import edu.spbspu.amd.morze_app.ActivityMain;
import edu.spbspu.amd.morze_app.morzeCoder.MorzeСoder;
import edu.spbspu.amd.morze_app.receiver.ViewReceiver;
import edu.spbspu.amd.morze_app.receiver.image_processing.ColorsSupp.*;


public class ImageProcessing implements Runnable {

    private MorzeСoder morzeСoder = new MorzeСoder();
    private int     dotDurationInFrames = 0;
    private int     curDurationInFrames = 0;
    private boolean dotDurationCounting = true;
    private int diffAmount = 0;
    private TextView outputText;

    @Override
    public void run() {
        try {
            Log.d(ActivityMain.APP_NAME, "start proccess thread");
            while (true) {
                if (Thread.interrupted()) {
                    Log.d(ActivityMain.APP_NAME, "interrupt proccess thread");
                    return;
                }

                Bitmap curImage;
                if (ViewReceiver.m_queue.peek() != null)
                    curImage = ViewReceiver.m_queue.pop();
                else
                    continue;

                Log.d(ActivityMain.APP_NAME, "pop from queue");

                Log.d(ActivityMain.APP_NAME, "Comparing started.");
                int compareRes = compareWithCurrentFrameImage(curImage);
                Log.d(ActivityMain.APP_NAME, "Comparing finished.");

                if (dotDurationCounting && diffAmount > 0) {
                    dotDurationInFrames++;
                } else if (diffAmount >= 2){
                    curDurationInFrames++;
                }

                if (compareRes != 0) {
                    Log.d(ActivityMain.APP_NAME, "DIFF!!!!");
                    diffAmount++;
                    if (diffAmount == 2) {
                        dotDurationCounting = false;
                        curDurationInFrames++;
                        Log.d(ActivityMain.APP_NAME, "dot duration is " + dotDurationInFrames);
                        continue;
                    }

                    if (diffAmount < 2)
                        continue;

                    if (curDurationInFrames <= dotDurationInFrames + 2 &&
                            curDurationInFrames >= dotDurationInFrames - 2 &&
                        diffAmount % 2 == 0) {
                        try {
                            morzeСoder.appendSym('.');
                            Log.d(ActivityMain.APP_NAME, "send . to decoder");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (curDurationInFrames <= 3 * dotDurationInFrames + 6 &&
                            curDurationInFrames >= 3 * dotDurationInFrames - 6 &&
                            diffAmount % 2 == 0) {
                        try {
                            morzeСoder.appendSym('-');
                            Log.d(ActivityMain.APP_NAME, "send - to decoder");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (curDurationInFrames <= 3 * dotDurationInFrames + 6 &&
                            curDurationInFrames >= 3 * dotDurationInFrames - 6 &&
                            diffAmount % 2 == 1) {
                        try {
                            morzeСoder.appendSym('&');
                            Log.d(ActivityMain.APP_NAME, "send & to decoder");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if ((morzeСoder.canDecode())) {
                        char curSym = '#';
                        try {
                            curSym = morzeСoder.getDecodedSym();
                            Log.d(ActivityMain.APP_NAME, "new symbol is " + curSym);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        outputText.setText(outputText.getText().toString() + curSym);
                    }

                    curDurationInFrames = 0;
                } else {
                    Log.d(ActivityMain.APP_NAME, "Identical images.");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ImageProcessing(TextView outputText_)
    {
        outputText = outputText_;
    }

    private Bitmap m_prevFrameImage = null;
    private AverageColors m_averageColors = new AverageColors(new RGB(0, 0, 0), new RGB(0, 0, 0 ));

    private int compareWithCurrentFrameImage(Bitmap curFrameImage)
    {
        if (m_prevFrameImage == null) {
            m_prevFrameImage = curFrameImage;
            getAvarageColor(curFrameImage, m_averageColors.cur);
            return 0;
        }

        int res = _isDiffFrom(curFrameImage);
        m_prevFrameImage = curFrameImage;

        return res;
    }

    private void getAvarageColor(Bitmap image, ColorsSupp.RGB col) {

        //count average for new current
        col.setZeroColor();

        int height = image.getHeight();
        int width = image.getWidth();

        int pixels_count = 0;
        for (int y = height / 4 ; y < height - height / 4; y += 3) {
            for (int x = width / 4; x < width - width / 4; x += 3) {
                pixels_count++;
                try {
                    int pixel = image.getPixel(x,y);
                    col.incrementColor(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
                }
                catch (Exception ignored)
                {
                }
            }
        }

        col.devideColors(pixels_count);
    }

    private boolean isDifferentColors(RGB c1, RGB c2)
    {
        int epsilonR = 60;
        int epsilonG = 60;
        int epsilonB = 60;

        Log.d(ActivityMain.APP_NAME, "PrevColor(" + c1.r + "," + c1.g + "," + c1.b + ")");
        Log.d(ActivityMain.APP_NAME, "CurColor(" + c2.r + "," + c2.g + "," + c2.b + ")");

        return (Math.abs(c1.r - c2.r) > epsilonR)
                && (Math.abs(c1.g - c2.g) > epsilonG)
                && (Math.abs(c1.b - c2.b) > epsilonB);
    }

    private int _isDiffFrom(Bitmap curFrameImage)
    {
        //prev = last current
        m_averageColors.prev = (ColorsSupp.RGB) m_averageColors.cur.clone();

        getAvarageColor(curFrameImage, m_averageColors.cur);

        return isDifferentColors(m_averageColors.prev, m_averageColors.cur) ? 1 : 0;
    }
}