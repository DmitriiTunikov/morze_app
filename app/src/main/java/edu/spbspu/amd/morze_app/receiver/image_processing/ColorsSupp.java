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

    public static class AverageIntensities
    {
        public AverageIntensities(int prev_, int cur_)
        {
            prev = prev_;
            cur = cur_;
        }

        public Integer prev;
        public Integer cur;
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

    public static class AverageColorParam implements Cloneable
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

        public AverageColorParam()
        {
            color = new RGB(0, 0, 0);
            intensity = 0;
        }

        public RGB color;
        public Integer intensity;
    }

    public static class AverageColorsParams
    {
        public AverageColorsParams()
        {
            prev = new AverageColorParam();
            cur = new AverageColorParam();
        }

        public AverageColorParam prev;
        public AverageColorParam cur;
    }




}
