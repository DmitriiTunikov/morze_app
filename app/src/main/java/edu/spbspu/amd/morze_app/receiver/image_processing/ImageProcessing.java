package edu.spbspu.amd.morze_app.receiver.image_processing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
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

    private int x_start_watch = 0;
    private static int x_delta = 0;
    private int y_start_watch = 0;
    private static int y_delta = 0;

    private static final int x_rect_count = 4;
    private static final int y_rect_count = 5;

    private AverageColorsParams m_averageColorsParams = null;

    private int compareWithCurrentFrameImage(Bitmap curFrameImage)
    {
        if (m_averageColorsParams == null) {
            m_averageColorsParams = new AverageColorsParams();

            x_delta = curFrameImage.getWidth() / x_rect_count;
            y_delta = curFrameImage.getWidth() / y_rect_count;

            getAvarageColor(curFrameImage, m_averageColorsParams.cur);
            return 0;
        }

        return _isDiffFrom(curFrameImage);
    }

    //count average for new current
    private void getAvarageColor(Bitmap image, AverageColorParam col_params) {
        RGB col = col_params.color;

        col.setZeroColor();
        col_params.intensity = 0;

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
        col_params.intensity = (int) (0.299 * col.r + 0.587 * col.g + 0.114 * col.b);
    }

    private boolean isDifferentColors(AverageColorParam param1, AverageColorParam param2)
    {
        int epsilonR = 60;
        int epsilonG = 60;
        int epsilonB = 60;
        int epsilonIntensity = 50;

        Log.d(ActivityMain.APP_NAME, "PrevColor(" + param1.color.r + "," + param1.color.g + "," + param1.color.b + ")");
        Log.d(ActivityMain.APP_NAME, "CurColor(" + param2.color.r + "," + param2.color.g + "," + param2.color.b + ")");

        return Math.abs(param1.intensity - param2.intensity) > epsilonIntensity;
    }

    private int _isDiffFrom(Bitmap curFrameImage)
    {
        //prev = last current
        m_averageColorsParams.prev = (ColorsSupp.AverageColorParam) m_averageColorsParams.prev.clone();

        getAvarageColor(curFrameImage, m_averageColorsParams.cur);

        return isDifferentColors(m_averageColorsParams.prev, m_averageColorsParams.cur) ? 1 : 0;
    }
}