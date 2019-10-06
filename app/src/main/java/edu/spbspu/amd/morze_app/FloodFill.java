package edu.spbspu.amd.morze_app;

import android.util.Log;

public class FloodFill
{
  // ******************************************************
  // Types
  // ******************************************************

  class FillSegment2D
  {
    public int y;
    public int xL;
    public int xR;
    public int dy;
  };

  // ******************************************************
  // Data
  // ******************************************************

  final public int  MAX_FILL_STACK          = (1024 * 2);

  final public int FE_OK                    = 1;
  final public int FE_STACK_OVERFLOW        = -1;
  final public int FE_STACK_UNDERFLOW       = -2;
  final public int FE_TOO_MUCH_ITERATIONS   = -3;

  int              s_stackIndex;
  FillSegment2D    s_stack2D[];

  static private String  m_log = "KP2D";

  // ******************************************************
  // Methods
  // ******************************************************
  public FloodFill()
  {
    s_stackIndex = 0;
    s_stack2D = new FillSegment2D[MAX_FILL_STACK];
    for (int i = 0; i < MAX_FILL_STACK; i++)
      s_stack2D[i] = new FillSegment2D();
  }

  private int stackPush(final int y, final int xL, final int xR, final int dy)
  {
    if (s_stackIndex >= MAX_FILL_STACK)
      return FE_STACK_OVERFLOW;
    s_stack2D[s_stackIndex].dy = dy;
    s_stack2D[s_stackIndex].xL = xL;
    s_stack2D[s_stackIndex].xR = xR;
    s_stack2D[s_stackIndex].y = y;
    s_stackIndex++;
    return FE_OK;
  }
  private int stackPop()
  {
    if (s_stackIndex <= 0)
      return FE_STACK_UNDERFLOW;
    s_stackIndex --;
    return FE_OK;
  }
  private int stackGetY()
  {
    return s_stack2D[s_stackIndex - 1].y;
  }
  private int stackGetDY()
  {
    return s_stack2D[s_stackIndex - 1].dy;
  }
  private int stackGetXL()
  {
    return s_stack2D[s_stackIndex - 1].xL;
  }
  private int stackGetXR()
  {
    return s_stack2D[s_stackIndex - 1].xR;
  }

  public int fill2D(
                      final int pixelsSrc[],
                      int pixelsDst[],
                      final int wImage, final int hImage,
                      final int xStart, final int yStart,
                      final int valReplacement
  )
  {
    s_stackIndex = 0;
    // initial value to be replaces
    int yOff = yStart * wImage;
    final int valSeed = pixelsSrc[xStart + yOff];
    if (valSeed == valReplacement)
    {
      Log.d(m_log,
          "fill2D: valSeed should be not equal to valReplacement: " +
              String.valueOf(valSeed) + " != " +
              String.valueOf(valReplacement));
    }

    int xL, xR;
    // find first line, based on (xStart, yStart)
    for (xL = xStart; xL >= 0; xL--)
    {
      // if pixel is not target color: stop
      if (pixelsSrc[xL + yOff] != valSeed)
        break;
    }
    xL++;
    for (xR = xStart; xR < wImage; xR++)
    {
      // if pixel is not target color: stop
      if (pixelsSrc[xR + yOff] != valSeed)
        break;
    }
    xR--;

    // write 1st detected line
    int x;
    for (x = xL; x <= xR; x++)
    {
      pixelsDst[x + yOff] = valReplacement;
    }
    int resStack;
    // push neighbour lines: upper
    if (yStart > 0)
    {
      resStack = stackPush(yStart, xL, xR, -1);
      if (resStack != FE_OK)
        return resStack;
    }
    // push neighbour lines: lower
    if (yStart < hImage - 1)
    {
      resStack = stackPush(yStart, xL, xR, +1);
      if (resStack != FE_OK)
        return resStack;
    }
    // while stack not empty
    final int MAX_ITERATIONS = 1024 * 8;
    int iter;
    for (iter = 0; (iter < MAX_ITERATIONS) && (s_stackIndex > 0); iter++)
    {
      int y, dy;

      dy = stackGetDY();
      xL = stackGetXL();
      xR = stackGetXR();
      y = stackGetY() + dy;

      resStack = stackPop();
      if ((y < 0) || (y >= hImage))
        break;
      yOff = y * wImage;

      // scan line from xL to 0 and stop
      for (x = xL; x >= 0; x--)
      {
        if (pixelsSrc[x + yOff] != valSeed)
          break;
        // write dest
        pixelsDst[x + yOff] = valReplacement;
      }
      int newL = x + 1;

      // push in inverse y direction
      if (newL < xL)
      {
        resStack = stackPush(y, newL, xL - 1, -dy);
        if (resStack != FE_OK)
          return resStack;
      }

      // scan line from xR to wImage and stop
      for (x = xR; x < wImage; x++)
      {
        if (pixelsSrc[x + yOff] != valSeed)
          break;
        // write dest
        pixelsDst[x + yOff] = valReplacement;
      }
      int newR = x - 1;

      // push in inverse y direction

      if (newR > xR)
      {
        resStack = stackPush(y, xR + 1, newR, -dy);
        if (resStack != FE_OK)
          return resStack;
      }

      // process pixels between [newL, newR] and try to push in the same y direction
      // int xs = xL;
      int xs = newL;
      int xe = xs;
      while (xe <= newR)
      {
        // scan same feeded segment
        for (; xe < wImage; xe++)
        {
          if (pixelsSrc[xe + yOff] != valSeed)
            break;
          // write dest
          pixelsDst[xe + yOff] = valReplacement;
        }
        if (xe > xs)
        {
          resStack = stackPush(y, xs, xe - 1, dy);
          if (resStack != FE_OK)
            return resStack;
        }

        // skip non seed pixels
        for (xe++; xe < wImage; xe++)
        {
          if (pixelsSrc[xe + yOff] == valSeed)
            break;
        }
        xs = xe;
      }
    } // while stak not empty
    // Log.d(m_log, "Flood fill num itrerations = " + String.valueOf(iter));
    if (iter >= MAX_ITERATIONS)
      return FE_TOO_MUCH_ITERATIONS;
    return FE_OK;
  } // fill2D

}
