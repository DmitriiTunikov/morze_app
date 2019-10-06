package edu.spbspu.amd.morze_app;

import android.util.Log;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class ShapeSrc {
  public float m_shape01[];
  public float m_shape02[];
  public float m_shape03[];
  public float m_shape04[];
  public float m_shape05[];
  public float m_shape06[];
  public float m_shape07[];
  public float m_shape08[];
  public float m_shape09[];
  public float m_shape10[];
  public float m_shape11[];
  public float m_shape12[];
  public float m_shape13[];
  public float m_shape14[];
  public float m_shape15[];
  public float m_shape16[];
  public float m_shape17[];
  public float m_shape18[];
  public float m_shape19[];
  public float m_shape20[];

  static private String  m_log = "KP2D";

  private ArrayList<float[]> m_shapes;


  ShapeSrc() {
    m_shape01 = new float[]{  41.81f,   34.98f,   32.57f,   32.03f,   36.09f,   27.12f,   38.60f,   21.63f,   40.02f,   15.76f,   40.30f,    9.73f,   39.42f,    3.75f,   37.41f,   -1.94f,   34.36f,   -7.16f,   30.37f,  -11.69f,   25.59f,  -15.38f,   20.20f,  -18.09f,   14.38f,  -19.72f,    8.36f,  -20.22f,    8.36f,  -27.72f,    0.79f,  -33.67f,    0.79f, -101.27f,   -0.82f, -102.43f,   -2.31f, -103.74f,   -3.67f, -105.19f,   -4.87f, -106.77f,   -5.92f, -108.46f,   -6.80f, -110.24f,   -7.50f, -112.10f,   -8.01f, -114.02f,   -8.34f, -115.98f,   -8.47f, -117.96f,   -8.40f, -119.95f,   -8.15f, -121.92f,  -28.15f, -121.92f,  -28.15f,   48.08f,   41.81f,   48.08f};
    m_shape02 = new float[]{   3.70f,  -64.12f,    6.50f,  -61.53f,    8.86f,  -58.54f,   10.74f,  -55.22f,   12.08f,  -51.65f,   12.86f,  -47.91f,   13.04f,  -44.10f,   12.64f,  -40.31f,   11.66f,  -36.62f,   10.12f,  -33.13f,    8.07f,  -29.92f,    8.07f,  -19.12f,    8.79f,  -17.72f,    9.58f,  -16.36f,   10.43f,  -15.04f,   11.34f,  -13.75f,   12.31f,  -12.51f,   13.33f,  -11.32f,   14.41f,  -10.17f,   15.54f,   -9.07f,   16.72f,   -8.03f,   17.95f,   -7.05f,   19.22f,   -6.12f,   20.53f,   -5.25f,   19.59f,   -2.64f,   19.01f,    0.07f,   18.82f,    2.84f,   19.01f,    5.61f,   19.59f,    8.32f,   20.53f,   10.93f,   21.83f,   13.38f,   23.45f,   15.63f,   25.37f,   17.63f,   27.55f,   19.35f,   29.94f,   20.75f,   32.51f,   21.80f,   35.20f,   22.49f,   37.95f,   22.80f,   40.73f,   22.72f,   40.73f,   51.42f,  -34.27f,   51.42f,  -34.27f, -128.58f,  -14.27f, -128.58f,  -14.27f,  -63.38f,  -13.54f,  -64.23f,  -12.72f,  -65.01f,  -11.85f,  -65.71f,  -10.91f,  -66.33f,   -9.10f,  -67.01f,   -7.21f,  -67.43f,   -5.29f,  -67.56f,   -3.36f,  -67.42f,   -1.48f,  -67.00f,    0.33f,  -66.32f,    0.78f,  -66.07f,    1.08f,  -65.88f};
    m_shape03 = new float[]{ -55.27f,   83.52f,   54.04f,   82.91f,   36.85f, -122.83f,  -33.16f, -122.83f,  -55.27f,   82.91f};
    m_shape04 = new float[]{  10.98f,  -43.34f,    8.93f,  -38.45f,    7.55f,  -33.32f,    6.87f,  -28.06f,    6.90f,  -22.75f,    7.65f,  -17.50f,    9.09f,  -12.39f,   11.20f,   -7.52f,   13.95f,   -2.98f,   17.28f,    1.15f,   21.14f,    4.80f,   25.45f,    7.90f,   25.45f,   15.32f,   29.20f,   17.85f,   32.35f,   21.11f,   34.75f,   24.96f,   36.29f,   29.21f,   36.91f,   33.70f,   36.59f,   38.22f,   35.33f,   42.57f,   33.20f,   46.57f,   30.28f,   50.03f,   26.70f,   52.81f,   22.62f,   54.78f,   18.22f,   55.86f,  -22.69f,   60.51f,  -22.69f,  -83.99f,    7.81f,  -83.99f,    7.81f,  -78.72f,   13.68f,  -78.72f,   13.68f,  -59.26f,   12.80f,  -59.14f,   11.98f,  -58.81f,   11.28f,  -58.27f,   10.75f,  -57.57f,   10.41f,  -56.75f,   10.29f,  -55.88f,   10.41f,  -55.00f,   10.75f,  -54.18f,   11.28f,  -53.48f,   11.98f,  -52.95f,   12.80f,  -52.61f,   13.68f,  -52.49f,   13.68f,  -47.92f};
    m_shape05 = new float[]{ -10.29f,  -57.45f,   -8.61f,  -55.74f,   -6.69f,  -54.27f,   -4.57f,  -53.08f,   -2.30f,  -52.20f,    0.09f,  -51.65f,    2.53f,  -51.43f,    4.98f,  -51.55f,    4.98f,  -45.66f,   21.28f,  -45.66f,   21.28f,  -17.91f,   27.43f,  -17.91f,   27.43f,  -11.32f,   33.94f,  -11.32f,   33.94f,   -5.37f,   40.27f,   -5.37f,   40.27f,    0.81f,   46.59f,    0.81f,   46.59f,   18.91f,  -23.33f,   30.74f,  -23.33f,  -69.40f,  -14.03f,  -69.40f,  -13.96f,  -66.05f,  -13.54f,  -63.73f,  -12.78f,  -61.49f,  -11.69f,  -59.39f};
    m_shape06 = new float[]{  59.98f,   26.67f,   61.43f,   27.64f,   63.00f,   28.41f,   64.65f,   28.97f,   66.37f,   29.31f,   68.11f,   29.43f,   75.11f,   29.43f,   75.11f,   51.35f,  -44.89f,   66.70f,  -44.89f,  -93.30f,   15.11f,  -93.30f,   15.11f,  -63.40f,   15.25f,  -61.41f,   15.65f,  -59.46f,   16.32f,  -57.57f,   17.24f,  -55.80f,   18.39f,  -54.17f,   19.75f,  -52.70f,   21.30f,  -51.44f,   23.00f,  -50.40f,   24.83f,  -49.60f,   26.76f,  -49.06f,   28.73f,  -48.78f,   35.13f,  -48.78f,   35.13f,  -30.98f,   40.73f,  -30.98f,   42.27f,  -30.90f,   43.79f,  -30.66f,   45.28f,  -30.26f,   46.72f,  -29.71f,   48.09f,  -29.01f,   49.38f,  -28.18f,   50.58f,  -27.21f,   51.67f,  -26.12f,   52.64f,  -24.93f,   53.49f,  -23.64f,   54.19f,  -22.27f,   54.75f,  -20.84f,   54.75f,   16.06f,   54.86f,   17.81f,   55.20f,   19.52f,   55.77f,   21.18f,   56.54f,   22.74f,   57.51f,   24.20f,   58.66f,   25.51f};
    m_shape07 = new float[]{ -11.31f,   40.72f,   38.46f,   40.72f,   38.78f,   20.36f,   28.76f,   20.36f,   29.09f,   10.67f,   28.12f,    8.08f,   25.53f,    5.17f,   21.33f,    2.59f,   18.10f,    1.29f,   13.90f,   -0.00f,   10.67f,   -0.97f,    8.40f,   -1.62f,    7.76f,  -76.60f,   -1.94f,  -76.92f,   -1.94f, -109.56f,  -10.67f, -109.56f};
    m_shape08 = new float[]{  57.58f,   32.30f,   59.00f,   33.48f,   60.56f,   34.47f,   62.23f,   35.25f,   63.98f,   35.82f,   65.79f,   36.17f,   67.63f,   36.28f,   73.03f,   36.28f,   73.03f,   57.64f,  -46.97f,   73.30f,  -46.97f,  -86.70f,   13.03f,  -86.70f,   13.03f,  -55.50f,   13.14f,  -53.78f,   13.47f,  -52.09f,   14.01f,  -50.46f,   14.76f,  -48.91f,   15.70f,  -47.46f,   16.81f,  -46.15f,   18.08f,  -44.99f,   19.49f,  -44.00f,   21.02f,  -43.20f,   22.63f,  -42.60f,   24.31f,  -42.22f,   26.02f,  -42.05f,   33.02f,  -42.05f,   33.02f,  -23.35f,   43.03f,  -23.35f,   44.41f,  -22.99f,   45.73f,  -22.47f,   46.98f,  -21.81f,   48.15f,  -21.01f,   49.22f,  -20.08f,   50.18f,  -19.04f,   51.02f,  -17.89f,   51.71f,  -16.66f,   52.27f,  -15.35f,   52.66f,  -13.99f,   52.91f,  -12.60f,   52.99f,  -11.18f,   52.99f,   22.52f,   53.22f,   24.35f,   53.67f,   26.14f,   54.35f,   27.85f,   55.24f,   29.47f,   56.32f,   30.96f};
    m_shape09 = new float[]{   0.48f,  -94.85f,  -18.26f,  -94.85f,  -18.26f,   75.15f,   -6.90f,   75.15f,   -6.25f,   74.64f,   -5.58f,   74.18f,   -4.87f,   73.76f,   -4.13f,   73.40f,   -3.38f,   73.09f,   -2.60f,   72.83f,   -1.80f,   72.62f,   -1.00f,   72.47f,   -0.69f,   72.43f,    4.32f,   72.43f,    5.00f,   72.38f,    5.67f,   72.23f,    6.30f,   71.99f,    6.89f,   71.65f,    7.42f,   71.23f,    7.89f,   70.73f,    8.28f,   70.17f,    8.58f,   69.56f,    8.81f,   68.77f,    8.81f,   65.06f,    8.89f,   63.59f,    9.12f,   62.14f,    9.30f,   61.43f,    9.77f,   60.04f,   10.06f,   59.36f,   10.75f,   58.06f,   11.14f,   57.45f,   11.57f,   56.85f,   12.40f,   55.82f,   13.28f,   54.84f,   14.21f,   53.90f,   15.19f,   53.01f,   16.21f,   52.17f,   17.27f,   51.39f,   18.37f,   50.66f,   19.50f,   49.98f,   23.72f,   47.11f,   27.37f,   43.53f,   30.34f,   39.37f,   32.52f,   34.75f,   33.86f,   29.82f,   34.31f,   24.73f,   33.86f,   19.64f,   32.52f,   14.71f,   30.34f,   10.10f,   27.37f,    5.94f,   23.72f,    2.36f,   19.50f,   -0.52f,   18.37f,   -1.21f,   17.30f,   -1.99f,   16.30f,   -2.86f,   15.37f,   -3.80f,   14.51f,   -4.81f,   13.75f,   -5.89f,   13.07f,   -7.03f,   12.48f,   -8.22f,   11.99f,   -9.45f,   11.60f,  -10.72f,   11.32f,  -12.01f,   11.14f,  -13.33f,   11.14f,  -27.88f,   11.21f,  -28.87f,   11.39f,  -29.85f,   11.68f,  -30.81f,   12.08f,  -31.72f,   12.32f,  -32.16f,   13.07f,  -33.24f,   13.59f,  -34.07f,   13.89f,  -34.87f,   14.04f,  -35.71f,   14.04f,  -36.57f,   13.89f,  -37.41f,   13.58f,  -38.22f,   13.14f,  -38.95f,   12.57f,  -39.59f,   11.90f,  -40.13f,   11.14f,  -40.53f,   11.14f,  -42.83f,   12.66f,  -43.85f,   13.98f,  -45.13f,   15.04f,  -46.62f,   15.83f,  -48.28f,   16.32f,  -50.05f,   16.48f,  -51.88f,   16.32f,  -53.70f,   15.83f,  -55.47f,   15.04f,  -57.13f,   13.98f,  -58.62f,   12.66f,  -59.90f,    5.19f,  -64.94f,    5.19f,  -74.14f,    0.48f,  -77.35f};
    m_shape10 = new float[]{ -16.54f,  -75.46f,   -1.04f,  -75.46f,   -1.04f,  -36.35f,   13.76f,  -36.35f,   13.76f,    4.65f,   28.46f,    4.65f,   28.46f,   44.54f,  -16.54f,   44.54f};
    m_shape11 = new float[]{   4.58f,  -50.96f,    3.95f,  -52.73f,    3.74f,  -54.60f,    3.95f,  -56.47f,    4.58f,  -58.24f,    5.60f,  -59.82f,    6.94f,  -61.13f,    8.55f,  -62.11f,   10.34f,  -62.70f,   10.34f,  -73.72f,    5.40f,  -78.88f,  -16.50f,  -78.88f,  -16.50f,   41.12f,   17.35f,   41.12f,   17.35f,   34.37f,   19.49f,   33.54f,   21.39f,   32.27f,   22.98f,   30.62f,   24.16f,   28.67f,   24.90f,   26.50f,   25.15f,   24.22f,   24.90f,   21.95f,   24.16f,   19.78f,   22.98f,   17.82f,   21.39f,   16.17f,   19.49f,   14.90f,   17.35f,   14.07f,   17.35f,    9.43f,   10.34f,    3.92f,   10.34f,  -46.50f,    8.55f,  -47.08f,    6.94f,  -48.06f,    5.60f,  -49.37f};
    m_shape12 = new float[]{ -12.12f,  -22.47f,  -12.32f,  -39.49f,  -12.32f,  -49.11f,   -6.09f,  -55.62f,   14.46f,  -55.62f,   14.46f,   64.38f,  -17.42f,   64.38f,  -17.42f,   57.45f,  -17.42f,   36.99f,  -17.42f,   31.38f,  -12.12f,   26.64f};
    m_shape13 = new float[]{ -58.65f,   82.40f,  105.44f,   62.84f,  105.44f,   51.67f,  103.34f,   50.97f,  100.55f,   50.28f,   94.96f,   48.88f,   88.68f,   46.78f,   83.09f,   44.69f,   78.21f,   40.50f,   76.11f,   36.31f,   75.41f,   31.42f,   75.41f,   27.93f,   76.11f,   20.25f,   77.51f,   16.06f,   81.00f,    8.38f,   82.40f,    2.09f,   82.40f,   -1.40f,   82.40f,   -7.68f,   81.70f,   -9.78f,   78.21f,  -13.97f,   74.02f,  -17.46f,   69.13f,  -19.55f,   64.24f,  -21.65f,   54.47f,  -23.74f,   45.39f,  -25.84f,   35.61f,  -27.23f,   32.12f,  -28.63f,   28.63f,  -34.22f,   27.93f,  -36.31f,   27.23f,  -40.50f,   27.23f,  -43.99f,   27.23f,  -59.35f,    8.38f,  -60.75f,    9.08f, -110.33f,    6.98f, -110.33f,    2.79f, -112.42f,   -0.70f, -115.21f,   -2.79f, -120.80f,   -3.49f, -127.09f,   -4.19f, -133.37f,   -3.49f, -138.96f,   -2.79f, -170.38f,  -59.35f, -168.98f};
    m_shape14 = new float[]{ -16.84f,  -25.95f,  -16.84f,   44.05f,   -4.43f,   44.05f,   -4.43f,   38.19f,    3.74f,   38.19f,    3.74f,   30.79f,   10.86f,   30.79f,   10.86f,   14.07f,   14.98f,   14.07f,   14.98f,    5.22f,   23.35f,   -6.44f,   23.35f,  -10.47f,   28.16f,  -10.47f,   28.16f,  -25.95f};
    m_shape15 = new float[]{ -21.99f,   54.53f,   17.59f,   54.53f,   17.81f,   41.12f,   14.95f,   40.90f,   13.85f,   39.80f,   11.65f,   38.26f,   10.55f,   36.28f,   10.55f,   34.08f,   11.21f,   31.44f,   12.31f,   28.58f,   13.85f,   25.95f,   15.17f,   23.31f,   16.27f,   21.11f,   17.81f,   18.91f,   19.79f,   16.27f,   22.65f,   12.31f,   24.41f,    9.45f,   25.73f,    5.06f,   27.04f,    1.10f,   28.14f,   -2.20f,   28.80f,   -5.72f,   28.80f,   -9.01f,   29.02f,  -12.75f,   28.14f,  -17.81f,   27.26f,  -21.55f,   25.95f,  -26.17f,   24.63f,  -29.24f,   22.43f,  -33.20f,   20.89f,  -36.72f,   19.13f,  -40.02f,   17.81f,  -42.22f,   16.93f,  -44.64f,   16.71f,  -46.61f,   16.71f,  -47.71f,   16.71f,  -49.69f,   18.25f,  -51.67f,   19.57f,  -53.65f,   21.55f,  -54.97f,   23.75f,  -56.95f,   23.97f,  -57.83f,   23.53f,  -59.37f,   23.31f,  -60.91f,   21.99f,  -61.79f,   20.89f,  -62.88f,   19.57f,  -64.20f,   17.59f,  -64.86f,  -21.99f,  -64.86f,  -21.55f,   54.31f};
    m_shape16 = new float[]{ -38.22f,   66.58f,   81.78f,   55.45f,   81.78f,   19.01f,   64.87f,   -6.35f,   41.72f,  -25.17f,   41.72f,  -38.57f,   21.78f,  -53.42f,   21.78f,  -93.42f,  -38.22f,  -93.42f};
    m_shape17 = new float[]{ -16.58f,   41.15f,   25.18f,   40.53f,   29.48f,   36.85f,   28.86f,   23.34f,   27.64f,   20.88f,   23.34f,   20.27f,   23.95f,   11.05f,   20.27f,   11.05f,   21.49f,  -18.42f,   13.51f,  -19.65f,   13.51f,  -35.62f,   11.05f,  -35.62f,   11.67f,  -38.69f,  -62.03f,  -38.69f,  -62.03f,  -14.13f,  -17.20f,  -13.51f,  -17.20f,   41.15f,  -16.58f,   41.15f};
    m_shape18 = new float[]{ -11.87f,   52.37f,   36.31f,   51.67f,   37.71f,   48.88f,   37.71f,   30.72f,   35.61f,   27.23f,   25.14f,   27.23f,   25.14f,   16.76f,   16.06f,   16.06f,   18.16f,  -18.85f,    9.78f,  -19.55f,    9.78f,  -42.59f,    4.19f,  -43.29f,    2.09f,  -48.88f,  -63.54f,  -48.18f,  -62.84f,  -19.55f,  -12.57f,  -20.25f};
    m_shape19 = new float[]{ -79.84f,   98.26f,  119.14f,   66.94f,  119.14f,   45.45f,  109.93f,   42.99f,  103.17f,   39.92f,   91.51f,   31.93f,   85.98f,   19.65f,   82.91f,    6.14f,   81.07f,   -6.76f,   82.29f,  -15.35f,   83.52f,  -26.41f,   84.14f,  -36.85f,   80.45f,  -44.83f,   74.31f,  -54.66f,   67.55f,  -58.34f,   50.97f,  -62.64f,   31.32f,  -66.33f,   18.42f,  -66.33f,   19.04f,  -85.36f,   -9.21f,  -85.36f,   -9.21f, -169.50f,   -9.21f, -169.50f,  -12.90f, -170.73f,  -16.58f, -173.19f,  -19.04f, -176.87f,  -20.88f, -179.94f,  -27.02f, -191.61f,  -29.48f, -199.59f,  -30.71f, -211.88f,  -79.84f, -213.10f,  -78.61f,   98.26f};
    m_shape20 = new float[]{ -36.38f,   58.06f,   82.68f,   39.32f,   83.42f,   20.58f,   72.76f,   20.21f,   56.22f,   19.11f,   46.30f,   15.80f,   35.64f,    9.55f,   31.23f,   -0.73f,   29.77f,  -15.07f,   30.13f,  -22.05f,   30.87f,  -40.05f,   28.30f,  -42.99f,   21.68f,  -46.67f,   13.60f,  -49.24f,    4.04f,  -51.08f,    3.67f,  -69.08f,    2.57f,  -69.08f,   -4.04f,  -70.55f,   -9.19f,  -72.39f,  -15.07f,  -76.80f,  -17.27f,  -80.48f,  -19.11f,  -86.72f,  -19.48f, -100.32f,  -36.75f, -100.32f,  -35.64f,   57.69f};

    m_shapes = new ArrayList<float[]>();
    m_shapes.add(m_shape01);
    m_shapes.add(m_shape02);
    m_shapes.add(m_shape03);
    m_shapes.add(m_shape04);
    m_shapes.add(m_shape05);
    m_shapes.add(m_shape06);
    m_shapes.add(m_shape07);
    m_shapes.add(m_shape08);
    m_shapes.add(m_shape09);
    m_shapes.add(m_shape10);
    m_shapes.add(m_shape11);
    m_shapes.add(m_shape12);
    m_shapes.add(m_shape13);
    m_shapes.add(m_shape14);
    m_shapes.add(m_shape15);
    m_shapes.add(m_shape16);
    m_shapes.add(m_shape17);
    m_shapes.add(m_shape18);
    m_shapes.add(m_shape19);
    m_shapes.add(m_shape20);

    // GeoFeatures features = new GeoFeatures();
    // getGeometricFeatures(m_shape01, features);
    // Log.d(m_log, "model 01. Perimeter = " + String.valueOf(features.m_perimeter));
    // Log.d(m_log, "model 01. cx = " + String.valueOf(features.m_cx));
    // Log.d(m_log, "model 01. cy = " + String.valueOf(features.m_cy));
    // Log.d(m_log, "model 01. radMin = " + String.valueOf(features.m_radMin));
    // Log.d(m_log, "model 01. radMax = " + String.valueOf(features.m_radMax));
  }
  public int getNumShapes()
  {
    return m_shapes.size();
  }
  public float[] getShape(final int index)
  {
    if (index >= m_shapes.size())
    {
      return null;
    }
    float[] shp = m_shapes.get(index);
    return shp;
  }

  private int   rasterizePolyLineIntoPointArray(
                                              float points[],
                                              V2d pointsDst[],
                                              final int maxPoints,
                                              final int wImage,
                                              final int hImage
                                          )
  {
    GeoFeatures features = new GeoFeatures();
    getGeometricFeatures(points, features);
    final float xBoxMin = features.m_xBoxMin;
    final float xBoxMax = features.m_xBoxMax;
    final float yBoxMin = features.m_yBoxMin;
    final float yBoxMax = features.m_yBoxMax;

    final int numPoints = points.length / 2;

    int numFinalPoints = 0;

    final int LINE_ACC = 10;
    final int LINE_HALF = (1 << (LINE_ACC-1) );

    float xScale, yScale;
    xScale = (wImage) / (xBoxMax - xBoxMin);
    yScale = (hImage) / (yBoxMax - yBoxMin);
    V2d p0 = new V2d(), p1 = new V2d();
    V2d vDelta = new V2d();
    int i;
    for (i = 0; i < numPoints; i++)
    {
      final int iNext = (i + 1 < numPoints) ? (i + 1) : 0;
      final int i2 = i << 1;
      final int in2 = iNext << 1;
      p0.x = (int)((points[i2 + 0] - xBoxMin) * xScale);
      p0.y = (int)((points[i2 + 1] - yBoxMin) * yScale);
      p1.x = (int)((points[in2 + 0] - xBoxMin) * xScale);
      p1.y = (int)((points[in2 + 1] - yBoxMin) * yScale);
      // draw line from p0 to p1

      vDelta.sub(p0, p1);
      // abs
      vDelta.x = (vDelta.x >= 0) ? vDelta.x : -vDelta.x;
      vDelta.y = (vDelta.y >= 0) ? vDelta.y : -vDelta.y;
      if (vDelta.x > vDelta.y)
      {
        // more hor line
        if (vDelta.x == 0)
          continue;
        int stepAcc = ((p1.y - p0.y) << LINE_ACC) / vDelta.x;
        int yAcc = (p0.y << LINE_ACC) + LINE_HALF;
        final int xStep = (p1.x > p0.x) ? +1 : -1;

        for (int iter = 0, x = p0.x; iter < vDelta.x; iter++, x += xStep, yAcc += stepAcc)
        {
          int y = yAcc >> LINE_ACC;
          if ((x >= 0) && (x < wImage) && (y >= 0) && (y < hImage))
          {
            // add pixel (x,y)
            pointsDst[numFinalPoints].x = x;
            pointsDst[numFinalPoints].y = y;
            numFinalPoints++;
            if (numFinalPoints >= maxPoints)
              return -1;
          }
        }
      }
      else
      {
        // more vert line
        if (vDelta.y == 0)
          continue;
        int stepAcc = ((p1.x - p0.x) << LINE_ACC) / vDelta.y;
        int xAcc = (p0.x << LINE_ACC) + LINE_HALF;
        final int yStep = (p1.y > p0.y) ? +1 : -1;
        for (int iter = 0, y = p0.y; iter < vDelta.y; iter++, y += yStep, xAcc += stepAcc)
        {
          int x = xAcc >> LINE_ACC;
          if ((x >= 0) && (x < wImage) && (y >= 0) && (y < hImage))
          {
            // add pixel (x,y)
            pointsDst[numFinalPoints].x = x;
            pointsDst[numFinalPoints].y = y;
            numFinalPoints++;
            if (numFinalPoints >= maxPoints)
              return -1;
          }
        }

      } // if more vert line
    } // for (i) all lines in poly
    return numFinalPoints;

  }
  public void getGeometricFeatures(float points[], GeoFeatures features)
  {
    int numPoints = points.length / 2;
    int i, j;
    // boxMin, boxMax
    features.m_xBoxMin = features.m_yBoxMin = +1.0e12f;
    features.m_xBoxMax = features.m_yBoxMax = -1.0e12f;
    for (i = 0, j = 0; i < numPoints; i++, j += 2)
    {
      float x = points[j + 0];
      float y = points[j + 1];
      features.m_xBoxMin = (x < features.m_xBoxMin) ? x : features.m_xBoxMin;
      features.m_yBoxMin = (y < features.m_yBoxMin) ? y : features.m_yBoxMin;
      features.m_xBoxMax = (x > features.m_xBoxMax) ? x : features.m_xBoxMax;
      features.m_yBoxMax = (y > features.m_yBoxMax) ? y : features.m_yBoxMax;
    }
    float dx, dy;
    // enlarge bbox
    dx = features.m_xBoxMax - features.m_xBoxMin;
    dy = features.m_yBoxMax - features.m_yBoxMin;
    final float minSize =  (dx < dy) ? dx : dy;
    final float enlarge = minSize * 0.04f;
    features.m_xBoxMin -= enlarge;
    features.m_yBoxMin -= enlarge;
    features.m_xBoxMax += enlarge;
    features.m_yBoxMax += enlarge;

    // perimeter
    float perimeter = 0.0f;
    for (i = 0; i < numPoints; i++)
    {
      int iNext = (i + 1 < numPoints) ? (i + 1) : 0;
      dx = points[iNext * 2 + 0] - points[i * 2 + 0];
      dy = points[iNext * 2 + 1] - points[i * 2 + 1];
      float d = (float)sqrt(dx * dx + dy * dy);
      perimeter += d;
    }
    features.m_perimeter = perimeter;

    // centroid
    float cx = 0.0f, cy = 0.0f;
    for (i = 0; i < numPoints; i++)
    {
      cx += points[i * 2 + 0];
      cy += points[i * 2 + 1];
    }
    cx = cx / numPoints;
    cy = cy / numPoints;
    features.m_cx = cx;
    features.m_cy = cy;

    // min, max bounding sphere
    float radMin = +1.0e12f;
    float radMax = -1.0e12f;
    for (i = 0; i < numPoints; i++)
    {
      dx = points[i * 2 + 0] - cx;
      dy = points[i * 2 + 1] - cy;
      float rad = (float)sqrt(dx * dx + dy * dy);
      radMin = (rad < radMin) ? rad : radMin;
      radMax = (rad > radMax) ? rad : radMax;
    }
    features.m_radMin = radMin;
    features.m_radMax = radMax;
  }
  public int getSquareScanned(
                              float points[],
                              int bufContour[],
                              int bufFilled[],
                              final int wImage,
                              final int hImage
  )
  {
    int numInternalPixels = 0;

    int i;
    final int numPixels = wImage * hImage;
    for (i = 0; i < numPixels; i++)
    {
      bufContour[i] = 0;
    }
    if (bufFilled != null)
    {
      for (i = 0; i < numPixels; i++)
      {
        bufFilled[i] = 0;
      }
    }
    int numPointsArrayMax = (wImage + hImage) * 3;
    V2d pointsArray[] = new V2d[numPointsArrayMax];
    for (i = 0; i < numPointsArrayMax; i++)
      pointsArray[i] = new V2d();
    final int numPointsRasterized = rasterizePolyLineIntoPointArray(points, pointsArray, numPointsArrayMax, wImage, hImage);
    if (numPointsRasterized <= 0)
      return 0;
    // Log.d(m_log, "Rasterized points = " + String.valueOf(numPointsRasterized));
    // rasterize by points
    final int COL = 0xffffffff;
    for (i = 0; i < numPointsRasterized; i++)
    {
      V2d p = pointsArray[i];
      bufContour[p.x + p.y * wImage] = COL;
    }
    // scan for first seed point
    if (bufFilled != null)
    {
      // copy contour to filled
      for (i = 0 ; i < numPixels; i++)
        bufFilled[i] = bufContour[i];

      // find first point
      int xEntry = -1;
      int xExit = -1;

      int xs = -1, ys;
      for (ys = hImage / 2; ys < hImage; ys++)
      {
        int ysOff = ys * wImage;
        xEntry = -1;
        xExit = -1;

        for (xs = 0; xs < wImage; xs++)
        {
          if (bufContour[xs + ysOff] == COL)
          {
            xEntry = xs;
            break;
          }
        }
        if (xEntry < 0)
          continue;
        while ((bufContour[xs + ysOff] == COL) && (xs < wImage))
          xs++;
        xs ++;
        for (; xs < wImage; xs++)
        {
          if (bufContour[xs + ysOff] == COL)
          {
            xExit = xs;
            break;
          }
        }
        if (xExit < 0)
          continue;
        break;
      } // for (ys)
      if ((xEntry < 0) || (xExit < 0))
      {
        // logic error cant find start point
        Log.d(m_log, " !!! Logic error for shape rasterizer !!! ");
      }
      xs = (xEntry + xExit) >> 1;

      // Log.d(m_log, "Seed point = " + String.valueOf(xs) + ", " + String.valueOf(ys));

      // apply flood fill 2d for seed point
      FloodFill filler = new FloodFill();
      final int resFill = filler.fill2D(bufContour, bufFilled, wImage, hImage, xs, ys, COL);
      if (resFill != 1)
      {
        Log.d(m_log, " !!! Error flood fill !!! ");
      }
      // acount filled pixels
      for (i = 0; i < numPixels; i++)
      {
        numInternalPixels += (bufFilled[i] == COL) ? 1 : 0;
      }
    }

    return numInternalPixels;
  }

} // class
