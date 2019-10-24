package edu.spbspu.amd.morze_app.receiver.image_processing;

public class ColorsSupp {
    public static class RGB implements Cloneable
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

        public RGB(int r_, int g_, int b_)
        {
            r = r_;
            g = g_;
            b = b_;
        }

        public void incrementColor(int r_, int g_, int b_)
        {
            r += r_;
            g += g_;
            b += b_;
        }

        public void devideColors(int devider)
        {
            r = r / devider;
            g = g / devider;
            b = b / devider;
        }

        public void setZeroColor()
        {
            r = 0;
            g = 0;
            b = 0;
        }

        public int r;
        public int g;
        public int b;
    }

    public static class AverageColors
    {
        public AverageColors(RGB prev_, RGB cur_)
        {
            prev = prev_;
            cur = cur_;
        }

        public RGB prev;
        public RGB cur;
    }

}
