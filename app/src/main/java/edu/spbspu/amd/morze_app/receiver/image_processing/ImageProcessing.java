package edu.spbspu.amd.morze_app.receiver.image_processing;

import android.graphics.Bitmap;

public class ImageProcessing {
    private boolean first = true;
    private Bitmap m_prevFrameImage = null;
    private int m_newAverageColor = -1;

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

    private int _isDiffFrom(Bitmap curFrameImage)
    {
        int curDiffPixelNum = 0;
        int diffPixelValueSum = 0;
        int height = curFrameImage.getHeight();
        int width = curFrameImage.getWidth();

        for (int y = 0; y < height; y+=3) {
            for (int x = 0; x < width; x+=3) {
                try {
                    int pixelC = curFrameImage.getPixel(x, y);
                    int pixelB = m_prevFrameImage.getPixel(x, y);
                    if (pixelB == pixelC) {
                        continue;
                    }

                    curDiffPixelNum++;
                    diffPixelValueSum += pixelC;
                } catch (Exception e) {
                    // handled height or width mismatch
                    diffPixelValueSum += MISMATCH_COLOR;
                }
            }
        }

        boolean isDiff = curDiffPixelNum >= LIMIT_DIFF_PIXEL_NUM;
        if (isDiff) {
            int oldAverageColor = m_newAverageColor;
            m_newAverageColor = diffPixelValueSum / curDiffPixelNum;

            if (first) {
                first = false;
            } else {
                int new_r = getColorR(m_newAverageColor);
                int new_g = getColorG(m_newAverageColor);
                int new_b = getColorB(m_newAverageColor);

                int r = getColorR(oldAverageColor);
                int g = getColorG(oldAverageColor);
                int b = getColorB(oldAverageColor);

                if (Math.abs(new_r - r) <= 100 && Math.abs(new_g - g) <= 100 && Math.abs(new_b - b) <= 100) {
                    isDiff = false;
                }
            }
        }

        return isDiff ? 1 : 0;
    }
}