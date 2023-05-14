package model;

import java.awt.Color;
import java.util.HashMap;

/**
 * Represents a pixel that contains RGB values as a color object.
 */
public interface Pixel {

  /**
   * Returns the largest RGB value when compared to each other.
   * @return the largest RGB value
   */
  int getValue();

  /**
   * Returns the red channel of the Pixel.
   * @return the red value
   */
  int getRed();

  /**
   * Returns the green channel of the Pixel.
   * @return the green value
   */
  int getGreen();

  /**
   * Returns the blue channel of the Pixel.
   * @return the blue value
   */
  int getBlue();

  /**
   * Returns the alpha value of the pixel.
   * @return the alpha value
   */
  int getAlpha();

  /**
   * Returns the maximum RGB value of the Pixel.
   * @return
   */
  int getMaxRGB();


  /**
   * Returns the color object of the pixel.
   * @return the color of the pixel in RGB
   */
  Color getColor();

  /**
   * Changes the color when overlapped with another Pixel.
   * @param otherPixel the Pixel overlapping this Pixel
   */
  void overlapWith(Pixel otherPixel);

  /**
   * Converts an RGB image to HSL values.
   * @return a HashMa of HSL values.
   */
  HashMap<String, Double> convertRGBtoHSL();
}
