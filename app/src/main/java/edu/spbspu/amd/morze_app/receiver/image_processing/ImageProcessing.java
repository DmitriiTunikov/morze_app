package edu.spbspu.amd.morze_app.receiver.image_processing;

import android.graphics.Bitmap;

public class ImageProcessing {
    private Bitmap m_prevFrameImage = null;
    private int m_newAverageColor = -1;

    private final int LIMIT_DIFF_PIXEL_NUM = 100;
    private final int MISMATCH_COLOR = 0x80ff0000;

    public int compareWithCurrentFrameImage(Bitmap curFrameImage)
    {
        if (m_prevFrameImage == null) {
            m_prevFrameImage = curFrameImage;
            return 0;
        }

        return _isDiffFrom(curFrameImage);
    }

    public int getNewAverageColor()
    {
        return m_newAverageColor;
    }

    private int _getColorA(int color)
    {
        return 0xff & color >> 24;
    }

    private int _getColorR(int color)
    {
        return 0xff & color >> 16;
    }

    private int _getColorG(int color)
    {
        return 0xff & color >> 8;
    }

    private int _getColorB(int color)
    {
        return 0xff & color;
    }

    private int _isDiffFrom(Bitmap curFrameImage)
    {
        int curDiffPixelNum = 0;
        int diffPixelValueSum = 0;
        int height = curFrameImage.getHeight();
        int width = curFrameImage.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                try {
                    int pixelC = curFrameImage.getPixel(x, y);
                    int pixelB = m_prevFrameImage.getPixel(x, y);
                    if (pixelB == pixelC) {
                        continue;
                    }

                    curDiffPixelNum++;
                    int a = _getColorA(pixelC);
                    int r = _getColorR(pixelC);
                    int g = _getColorG(pixelC);
                    int b = _getColorB(pixelC);

                    diffPixelValueSum += (a << 24) | (r << 16) | (g << 8) | b;
                } catch (Exception e) {
                    // handled height or width mismatch
                    diffPixelValueSum += MISMATCH_COLOR;
                }
            }
        }

        boolean isDiff = curDiffPixelNum >= LIMIT_DIFF_PIXEL_NUM;
        if (isDiff) {
            m_newAverageColor = diffPixelValueSum / curDiffPixelNum;
        }

        return isDiff ? 1 : 0;
    }
}
