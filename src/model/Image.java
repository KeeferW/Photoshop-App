package model;

/**
 * Interface representing an Image.
 */
public interface Image {

  /**
   * Returns the pixel at the designated location.
   * @param row - row of the pixel
   * @param col - column of the pixel
   * @return a pixel object
   */
  Pixel getPixel(int row, int col);

  /**
   * Returns the maximum RGB value of each pixel.
   * @return the maxValue
   */
  int getMaxValue();

  /**
   * Returns the width of the image.
   * @return the width
   */
  int getWidth();

  /**
   * Returns the height of the image.
   * @return the height
   */
  int getHeight();

  /**
   * Returns the name of the image.
   * @return the name
   */
  String getName();
}
