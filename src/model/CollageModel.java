package model;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Holds all the functions the user can apply. Includes adding images and layers, loading
 * images, applying filters, and saving composite images.
 */
public interface CollageModel extends CollageViewModel {


  /**
   * Creates a new project for the user to add layers or images on.
   * @throws IllegalArgumentException if the input cannot be converted to an int
   * @throws IOException if the view cannot be transmitted
   */
  void createNewProject(int width, int height) throws IllegalArgumentException;

  /**
   * Determines if there is a project present.
   * @return true if a project has been made
   */
  boolean projectMade();

  /**
   * Adds a layer to the roject to place images ontop of.
   */
  void addLayer();

  /**
   * Sets the filter to be applied to the whole image.
   * @param layernumber - the number of the layer
   * @param filterOption - the option
   */
  void setFilter(int layernumber, String filterOption);

  /**
   * Adds an image to an existing layer to a given position.
   * @param layernumber name of the layer
   * @param imageName the name of the image
   * @param x the row to place the image on
   * @param y the col to place the image on
   */
  void addImageToLayer(int layernumber, String imageName, int x, int y);

  /**
   * Adds an image onto a project without, disregarding layer.
   * @param imageName - the name of the image
   * @param pixels - the pixel array
   * @param maxValue - the maximum rgb value
   */
  void addImage(String imageName, Pixel[][] pixels, int maxValue, BufferedImage loadImage);

  /**
   * Loads an image from the path and names it, once an image is loaded filters can be applied.
   * @param path the path to the image
   * @param name the name to refer to
   * @throws FileNotFoundException cannot be accessed.
   * @throws IllegalStateException if no project created.
   */
  void load(String path, String name) throws FileNotFoundException, IllegalStateException;

  /**
   * Saves the image to the path.
   *
   * @param path the path to the image
   * @param name the name to refer to
   * @throws IOException if the iamge cannot be transmitted to the path
   * @throws IllegalStateException if no project created.
   */
  void save(String path, String name) throws IOException, IllegalStateException;

  /**
   * Displays only the green channel of the pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void redComponent(String name, String dest) throws IllegalStateException;

  /**
   * Displays only the green channel of the pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void greenComponent(String name, String dest) throws IllegalArgumentException;

  /**
   * Displays only the blue channel of the pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void blueComponent(String name, String dest) throws IllegalStateException;

  /**
   * Brighten an image by getting the maximum value for rgb of every pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void brightenValue(String name, String dest) throws IllegalArgumentException,
          IllegalStateException;

  /**
   * Darken an image by getting the maximum value for rgb of every pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void darkenValue(String name, String dest) throws IllegalArgumentException, IllegalStateException;

  /**
   * Create a greyscale image by calculating the luma of every pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void brightenLuma(String name, String dest) throws IllegalStateException;

  /**
   * Darkens an image by calculating the luma of every pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void darkenLuma(String name, String dest) throws IllegalArgumentException, IllegalStateException;

  /**
   * Brightens an image by calculating the intensity of every pixel
   * and heightening it.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void brightenIntensity(String name, String dest) throws IllegalArgumentException,
          IllegalStateException;

  /**
   * Darkens an image by calculating the intensity of every pixel.
   *
   * @param name the name of the image
   * @param dest what to call the image
   * @throws IllegalStateException if no project created.
   */
  void darkenIntensity(String name, String dest) throws IllegalStateException;

  /**
   * Darkens an image by converting a Pixel's RGB value to an HSL value then multiplying the
   * L value by an increment.
   * @param name the name of the image
   * @param dest what to call the image
   */
  void darkenMultiply(String name, String dest);

  /**
   * Brightens an image by converting a Pixel's RGB value to an HSL value then screening the
   * L value by an increment.
   * @param name the name of the image
   * @param dest what to call the image
   */
  void brightenScreen(String name, String dest);

  /**
   * Differences an image by calculating the difference between the current image and the
   * composite image below it.
   * @param name the name of the image
   * @param dest what to call the image
   */

  void difference(String name, String dest);

  /**
   * Returns the height of the image.
   * @return the height in pixels
   */
  int getHeight();

  /**
   * Returns the width of the image.
   * @return the width in pixels
   */
  int getWidth();

  /**
   * Returns the hashmap of layers containing images which contain pixels.
   * @return a hashmap of layers
   */
  ArrayList<LayerImpl> getLayers();

  /**
   * Loads a PPM image.
   * @param imagePath the path of the image.
   */
  void loadPPM(String imagePath);
}
