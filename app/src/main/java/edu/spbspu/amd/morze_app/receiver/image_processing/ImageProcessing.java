package edu.spbspu.amd.morze_app.receiver.image_processing;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

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
    private Handler handler;
    private Handler m_graph_h;

    @SuppressLint("SetTextI18n")
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
                if (ViewReceiver.m_queue.peek() != null) {
                    //Log.d(ActivityMain.APP_NAME, "queue size is: " + ViewReceiver.m_queue.size());
                    ViewReceiver.m_sem.acquire();
                    curImage = ViewReceiver.m_queue.pop();
                } else {
                    continue;
                }


                int compareRes = 0;
                if (!correctRectListFound) {
                    //Log.d(ActivityMain.APP_NAME, "Comparing before rectFound started.");
                    compareRes = compareWithCurrentFrameImageBeforeReadyRects(curImage);
                } else {
                    //Log.d(ActivityMain.APP_NAME, "Comparing into correct Rectangles started.");
                    compareRes = compareWithCurrentFrameImage(curImage);
                }

                //Log.d(ActivityMain.APP_NAME, "Comparing finished.");

                if (dotDurationCounting && diffAmount > 0)
                    dotDurationInFrames++;
                else if (diffAmount >= 2)
                    curDurationInFrames++;


                ViewReceiver.m_sem.release();
                if (compareRes != 0) {
                    //Log.d(ActivityMain.APP_NAME, "DIFF!!!!");
                    diffAmount++;
                    if (diffAmount == 2) {
                        dotDurationCounting = false;
                        curDurationInFrames++;
                        Log.d(ActivityMain.APP_NAME, "dot duration is " + dotDurationInFrames);
                        continue;
                    }

                    if (diffAmount < 2) {
                        continue;
                    }

                    if (curDurationInFrames <= dotDurationInFrames + 2 &&
                            curDurationInFrames >= dotDurationInFrames - 2 &&
                        diffAmount % 2 == 0) {
                        morzeСoder.appendSym('.');
                        Log.d(ActivityMain.APP_NAME, "send . to decoder");

                    }
                    else if (curDurationInFrames <= 3 * dotDurationInFrames + 6 &&
                            curDurationInFrames >= 3 * dotDurationInFrames - 6 && diffAmount % 2 == 0) {
                        morzeСoder.appendSym('-');
                        Log.d(ActivityMain.APP_NAME, "send - to decoder");
                    }
                    else if (curDurationInFrames <= 3 * dotDurationInFrames + 6 &&
                            curDurationInFrames >= 3 * dotDurationInFrames - 6 &&
                            diffAmount % 2 == 1) {
                        morzeСoder.appendSym('&');
                        Log.d(ActivityMain.APP_NAME, "send & to decoder");
                    }

                    if ((morzeСoder.canDecode())) {
                        char curSym = '#';
                        curSym = morzeСoder.getDecodedSym();
                        Log.d(ActivityMain.APP_NAME, "new symbol is " + curSym);
                        handler.sendMessage(handler.obtainMessage(0, curSym));
                    }

                    curDurationInFrames = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<AverageColorsParams> m_averageColorsParamsCorrectRectList;
    private boolean correctRectListFound;
    private ArrayList<AverageColorsParams> m_averageColorsParamsList;
    public ImageProcessing(Handler h, Handler graph_h)
    {
        handler = h;
        m_graph_h = graph_h;
        correctRectListFound = false;
        m_averageColorsParamsCorrectRectList = new ArrayList<>();
        m_averageColorsParamsList = null;
    }

    private static int x_delta = 0;
    private static int y_delta = 0;
    private static final int x_rect_count = 4;
    private static final int y_rect_count = 5;

    private int compareWithCurrentFrameImageBeforeReadyRects(Bitmap curFrameImage)
    {
        int res = 0;
        if (m_averageColorsParamsList == null) {
            x_delta = curFrameImage.getWidth() / x_rect_count;
            y_delta = curFrameImage.getWidth() / y_rect_count;

            //Log.d(ActivityMain.APP_NAME, "x_delta and y_delta :" + x_delta + ", " + y_delta);

            m_averageColorsParamsList = new ArrayList<>();
            for (int i = 0; i < x_rect_count; i++) {
                for (int j = 0; j < y_rect_count; j++) {
                    AverageColorsParams curElem = new AverageColorsParams();

                    curElem.start_x = x_delta * i;
                    curElem.start_y = y_delta * j;

                    //Log.d(ActivityMain.APP_NAME, "newRect coords: (" + curElem.start_x + ", " + curElem.start_y + ")");
                    m_averageColorsParamsList.add(curElem);

                    getAvarageColor(curFrameImage, curElem.cur, curElem.start_x, curElem.start_y);
                }
            }
            return res;
        }

        for (AverageColorsParams elem : m_averageColorsParamsList) {
            res = _isDiffFrom(curFrameImage, elem);
            if (res == 1) {
                correctRectListFound = true;
                m_averageColorsParamsCorrectRectList.add(elem);
            }
        }

        if (correctRectListFound) {
            //Log.d(ActivityMain.APP_NAME, "correct rects were FOUND");
            return 1;
        } else {
            return 0;
        }
    }

    private int compareWithCurrentFrameImage(Bitmap curFrameImage)
    {
        int diff_count = 0;
        int aver_intensity = 0;
        for (AverageColorsParams curRect : m_averageColorsParamsCorrectRectList) {
            diff_count = diff_count + _isDiffFrom(curFrameImage, curRect);
            aver_intensity += curRect.cur.intensity;
        }

        aver_intensity /= m_averageColorsParamsCorrectRectList.size();

        m_graph_h.sendEmptyMessage(aver_intensity);
        if (diff_count >= m_averageColorsParamsCorrectRectList.size() / 2) {
            return 1;
        }

        return 0;
    }

    //count average for new current
    private void getAvarageColor(Bitmap image, AverageColorParam col_params, int start_x, int start_y) {
        RGB col = col_params.color;

        col.setZeroColor();
        col_params.intensity = 0;

        int pixels_count = 0;
        for (int y = start_y ; y < start_y + y_delta; y += 6) {
            for (int x = start_x; x < start_x + x_delta; x += 6) {
                try {
                    int pixel = image.getPixel(x,y);
                    col.incrementColor(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
                    pixels_count++;
                } catch (Exception ignored) {
                }
            }
        }

        col.devideColors(pixels_count);
        col_params.intensity = (int) (0.299 * col.r + 0.587 * col.g + 0.114 * col.b);
    }

    private boolean isDifferentColors(AverageColorParam param1, AverageColorParam param2)
    {
        int epsilonIntensity = 80;

        /*
        Log.d(ActivityMain.APP_NAME, "PrevColor(" + param1.color.r + "," + param1.color.g + "," + param1.color.b + "), " +
                "PrevIntensity = " + param1.intensity);
        Log.d(ActivityMain.APP_NAME, "CurColor(" + param2.color.r + "," + param2.color.g + "," + param2.color.b + "), " +
                "CurIntensity = " + param2.intensity);*/

        return Math.abs(param1.intensity - param2.intensity) > epsilonIntensity;
    }

    private int _isDiffFrom(Bitmap curFrameImage, AverageColorsParams averageParams)
    {
        //prev = last current
        averageParams.prev = (ColorsSupp.AverageColorParam) averageParams.cur.clone();

        getAvarageColor(curFrameImage, averageParams.cur, averageParams.start_x, averageParams.start_y);

        //Log.d(ActivityMain.APP_NAME, "Get diff for (" + averageParams.start_x + ", " + averageParams.start_y + ")");
        return isDifferentColors(averageParams.prev, averageParams.cur) ? 1 : 0;
    }
}