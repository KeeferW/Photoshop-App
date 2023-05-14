package model;

import java.awt.Color;

public interface PixelFactory extends Pixel {
  Pixel createPixel(Color color);
}
