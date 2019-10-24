package edu.spbspu.amd.morze_app.receiver.image_processing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.util.Pair;

public class ImageProcessing {
    static class RGB implements Cloneable
    {
        public Object clone()
        {
            try {
                return super.clone();
            }
            catch (Exception ignored)
            {
                return null;
            }
        }

        RGB(int r_, int g_, int b_)
        {
            r = r_;
            g = g_;
            b = b_;
        }

        void incrementColor(int r_, int g_, int b_)
        {
            r += r_;
            g += g_;
            b += b_;
        }

        void devideColors(int devider)
        {
            r = r / devider;
            g = g / devider;
            b = b / devider;
        }

        void setZeroColor()
        {
            r = 0;
            g = 0;
            b = 0;
        }

        int r;
        int g;
        int b;
    }

    static class AverageColors
    {
        AverageColors(RGB prev_, RGB cur_)
        {
            prev = prev_;
            cur = cur_;
        }

        void setZeroColors()
        {
            prev.setZeroColor();
            cur.setZeroColor();
        }

        void deviceColors(int devicer)
        {
            prev.devideColors(devicer);
            cur.devideColors(devicer);
        }

        RGB prev;
        RGB cur;
    }


    private boolean first = true;
    private Bitmap m_prevFrameImage = null;
    private static int epsilon;
    private RGB m_prevAverageColor;
    private int m_newAverageColor = -1;

    private static AverageColors m_averageColors;
    static {
        m_averageColors = new AverageColors(new RGB(0, 0, 0), new RGB(0, 0, 0 ));
        epsilon = 100;
    }

    private final int LIMIT_DIFF_PIXEL_NUM = 7000;
    private final int MISMATCH_COLOR = 0x80ff0000;

    public int compareWithCurrentFrameImage(Bitmap curFrameImage)
    {
        if (m_prevFrameImage == null) {
            m_prevFrameImage = curFrameImage;
            return 0;
        }

        int res = _isDiffFrom(curFrameImage);
        m_prevFrameImage = curFrameImage;

        return res;
    }

    public int getNewAverageColor()
    {
        return m_newAverageColor;
    }

    public Bitmap getPrevFrameImage()
    {
        return m_prevFrameImage;
    }

    public int getColorA(int color)
    {
        return 0xff & color >> 24;
    }

    public int getColorR(int color)
    {
        return 0xff & color >> 16;
    }

    public int getColorG(int color)
    {
        return 0xff & color >> 8;
    }

    public int getColorB(int color)
    {
        return 0xff & color;
    }

    private void getAvarageColor(Bitmap cur) {
        //prev = last current
        m_averageColors.prev = (RGB) m_averageColors.cur.clone();

        //count average for new current
        m_averageColors.cur.setZeroColor();

        int height = cur.getHeight();
        int width = cur.getWidth();

        int pixels_count = 0;
        for (int y = 0; y < height; y += 3) {
            for (int x = 0; x < width; x += 3) {
                pixels_count++;
                try {
                    int pixel = cur.getPixel(x,y);
                    m_averageColors.cur.incrementColor(Color.red(pixel), Color.green(pixel), Color.blue(pixel));
                }
                catch (Exception ignored)
                {
                }
            }
        }

        m_averageColors.cur.devideColors(pixels_count);
    }

    private boolean isDifferentColors(RGB c1, RGB c2)
    {
        return (Math.abs(c1.r - c2.r) > epsilon)
                && (Math.abs(c1.g - c2.g) > epsilon)
                && (Math.abs(c1.b - c2.b) > epsilon);
    }

    private int _isDiffFrom(Bitmap curFrameImage)
    {
        getAvarageColor(curFrameImage);

        return isDifferentColors(m_averageColors.prev, m_averageColors.cur) ? 1 : 0;
    }
}