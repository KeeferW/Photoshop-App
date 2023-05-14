package model;

import java.util.Map;

public interface Layer {

  /**
   * Places the given image to the specified coordinates of the Layer.
   * If the size of the image extends beyond the dimensions of the layer,
   * the trailing parts are not displayed.
   * If it is found that an image is overlapping another image, it will
   * be modified pixel by pixel to account for the newer image.
   * @param image the image to be added.
   * @param xCoord the x coordinate of the image to be added.
   * @param yCoord the y coordinate of the image to be added.
   * @throws IllegalArgumentException if coordinate values:
   *                                    less than or equal to 0
   *                                    greater than or equal to the total dimensions of the image
   */
  public void placeImage(Image image, int xCoord, int yCoord) throws IllegalArgumentException;

  /**
   * Returns the images in this layer.
   * @return a hashmap of images stored in this layer.
   */

  public Map<String, Image> getImages();

  /**
   * Returns a specific pixel in the layer, given a width and height.
   * @param x - row of the pixel
   * @param y - col of the pixel
   * @return a Pixel in the given row and col
   */
  public Pixel getPixel(int x, int y) throws IllegalArgumentException;

  /**
   * Returns the width in pixels of this layer.
   * @return the width in pixels
   */
  public int getWidth();

  /**
   * Returns the height in pixels of this layer.
   * @return the height in pixels
   */
  public int getHeight();
}
