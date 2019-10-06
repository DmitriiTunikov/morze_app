package edu.spbspu.amd.morze_app;

public class V2d
{
  public int	x;
  public int	y;

  public int dotProduct(V2d v)
  {
    final int res = x * v.x + y * v.y;
    return res;
  }

  public void sub(V2d p0, V2d p1)
  {
    x = p0.x - p1.x;
    y = p0.y - p1.y;
  }
};
