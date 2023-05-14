package model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a Pixel object that makes up an image.
 */
public class PixelImpl implements Pixel {
  private Color color;
  private final int maxRGB;

  /**
   * Constructs that has an RGB value and alpha value.
   * @param color - the RGB value of the pixel.
   */
  public PixelImpl(Color color) {
    this.color = color;
    this.maxRGB = 255;
  }


  @Override
  public int getValue() {
    return Math.max(Math.max(color.getRed(), color.getGreen()), color.getBlue());
  }

  @Override
  public int getRed() {
    return color.getRed();
  }

  @Override
  public int getGreen() {
    return color.getGreen();
  }

  @Override
  public int getBlue() {
    return color.getBlue();
  }

  @Override
  public int getAlpha() {
    return this.color.getAlpha();
  }

  @Override
  public int getMaxRGB() {
    return maxRGB;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void overlapWith(Pixel otherPixel) {
    if (this.equals(otherPixel)) {
      return;
    }

    int r = this.getRed();
    int g = this.getGreen();
    int b = this.getBlue();
    int a = this.getAlpha();

    int dr = otherPixel.getRed();
    int dg = otherPixel.getGreen();
    int db = otherPixel.getBlue();
    int da = otherPixel.getAlpha();

    double aPP = (a / 255. + da / 255. * (1 - a / 255.));
    double aP = aPP * 255.;

    int rP = (int) ((a / 255 * r + dr * (da / 255)) * (1 - a / 255) * (1 / aPP));
    int gP = (int) ((a / 255 * g + dg * (da / 255)) * (1 - a / 255) * (1 / aPP));
    int bP = (int) ((a / 255 * b + db * (da / 255)) * (1 - a / 255) * (1 / aPP));

    this.color = new Color(rP, gP, bP);

  }

  @Override
  public HashMap<String, Double> convertRGBtoHSL() {
    double componentMax = Math.max(this.getRed(), Math.max(this.getGreen(), this.getBlue()))
            / 255.0;
    double componentMin = Math.min(this.getRed(), Math.min(this.getGreen(), this.getBlue()))
            / 255.0;
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue;
    double saturation;
    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == this.getRed()) {
        hue = (this.getGreen() - this.getBlue()) / delta;
        while (hue < 0) {
          hue += 6; //hue must be positive to find the appropriate modulus
        }
        hue = hue % 6;
      } else if (componentMax == this.getGreen()) {
        hue = (this.getBlue() - this.getRed()) / delta;
        hue += 2;
      } else if (componentMax == this.getBlue()) {
        hue = (this.getRed() - this.getGreen()) / delta;
        hue += 4;
      }

      hue = hue * 60;
    }
    HashMap<String, Double> hsl = new HashMap<>();
    hsl.put("h", hue);
    hsl.put("s", saturation);
    hsl.put("l", lightness);
    return hsl;

  }


  @Override
  public boolean equals(Object o) {
    if (o.equals(this)) {
      return true;
    }
    if (!(o instanceof Pixel)) {
      return false;
    }
    Pixel p = (Pixel) o;
    return color.equals(p.getColor());
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, maxRGB);
  }

  @Override
  public String toString() {
    return (this.getRed() + " " + this.getGreen() + " " + this.getBlue());
  }

}