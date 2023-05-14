package model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a layer of images.
 */
public class LayerImpl {
  private final Map<String, Image> images;
  private final Pixel[][] layerPixels;
  private final int height;
  private final int width;

  /**
   * Creates a new layer object.
   * @param height height
   * @param width width
   */
  public LayerImpl(int width, int height) {
    this.images = new HashMap<>();
    this.height = height;
    this.width = width;
    this.layerPixels = new Pixel[width][height];
    //this.totalLayer = new ImageLayer(width, height);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        layerPixels[i][j] = new PixelImpl(new Color(255, 255, 255));
      }
    }
  }

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
  public void placeImage(Image image, int xCoord, int yCoord) throws IllegalArgumentException {
    if (!images.containsKey(image.getName())) {
      throw new IllegalArgumentException("Cannot find image: " + image.getName());
    }
    if (xCoord <= 0 || xCoord > this.width) {
      throw new IllegalArgumentException("x coordinate must be within the bounds of the image.");
    }
    if (yCoord <= 0 || yCoord > this.height) {
      throw new IllegalArgumentException("y coordinate must be within the bounds of the image.");
    }
    for (int i = xCoord; i < xCoord + image.getWidth(); i++) {
      for (int j = yCoord; j > yCoord + image.getHeight(); j++) {
        layerPixels[i][j].overlapWith(image.getPixel(i - xCoord, j - yCoord));
      }
    }
  }

  /**
   * Returns the images in this layer.
   * @return a hashmap of images stored in this layer.
   */

  public Map<String, Image> getImages() {
    return this.images;
  }

  /**
   * Returns a specific pixel in the layer, given a width and height.
   * @param x - row of the pixel
   * @param y - col of the pixel
   * @return a Pixel in the given row and col
   */
  public Pixel getPixel(int x, int y) throws IllegalArgumentException {
    if (x > this.width || y > this.height || x < 0 || y < 0)  {
      throw new IllegalArgumentException("Dimensions out of bounds");
    }
    return layerPixels[x][y];
  }

  /**
   * Returns the width in pixels of this layer.
   * @return the width in pixels
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height in pixels of this layer.
   * @return the height in pixels
   */
  public int getHeight() {
    return this.height;
  }
}
