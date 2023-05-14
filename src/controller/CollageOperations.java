package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is an Interface for different operations the user can run.
 * All methods are options to modify images that can be displayed as buttons.
 */
public interface CollageOperations {

  /**
   * Creates a new project for the user to add images, layers, and filters onto.
   * @throws IllegalArgumentException if the input cannot be converted to an int.
   * @throws IOException if the view cannot be transmitted.
   */
  void newProject() throws IllegalArgumentException, IOException;

  /**
   * Loads an image from the path and names it, once an image is loaded filters can be applied.
   * @throws FileNotFoundException cannot be accessed.
   * @throws IOException if view cannot be transmitted.
   */
  void load() throws FileNotFoundException, IOException;

  /**
   * Loads an image of png or jpg from the path and names it.
   * @param imagePath - the image path to be loaded.
   * @param imageName - name of the image to be loaded.
   * @throws IllegalArgumentException if the path for the image
   *                                  or the image name does not exist.
   */
  void load(String imagePath, String imageName) throws IllegalArgumentException;

  /**
   * Adds an image on top of an existing layer.
   * This is done to enable more image editing and more detailed collages.
   * @throws IOException if the view cannot be transmitted
   */
  void addImageToLayer() throws IOException;

  /**
   * Applies a specified filter onto all images on a layer.
   * @throws IOException if the view cannot be transmitted.
   */
  void setFilter() throws IOException;

  /**
   * Saves the image to the path that the interface is currently working with.
   * @throws IOException if the image cannot be transmitted to the path.
   */
  void save() throws IOException;

  /**
   * Displays only the green channel of the pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void redComponent() throws IOException;

  /**
   * Displays only the green channel of the pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void greenComponent() throws IOException;

  /**
   * Displays only the blue channel of the pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void blueComponent() throws IOException;

  /**
   * Brightens an image by getting the maximum value for rgb of every pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void brightenValue() throws IOException;

  /**
   * Darken an image by getting the maximum value for rgb of every pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void darkenValue() throws IOException;

  /**
   * Create a greyscale image by calculating the luma of every pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void brightenLuma() throws IOException;

  /**
   * Darkens an image by calculating the luma of every pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void darkenLuma() throws IOException;

  /**
   * Brightens an image by calculating the intensity of every pixel
   * and heightening it.
   * @throws IOException if view cannot be transmitted.
   */
  void brightenIntensity() throws IOException;

  /**
   * Darkens the intensity of an image by calculating the intensity of every pixel
   * and lowering it.
   * @throws IOException if view cannot be transmitted.
   */
  void darkenIntensity() throws IOException;

  /**
   * Darkens an image by multiplying the L value of a Pixel.
   * @throws IOException if view cannot be transmitted.
   */
  void darkenMultiply() throws IOException;

  /**
   *  Brightens the screen by converting a Pixel's RGB value to an HSL value then screening the
   *  L value by an increment.
   * @throws IOException if view cannot be transmitted.
   */
  void brightenScreen() throws IOException;

  /**
   * Combines two images by calculating the difference between the current image and the
   * composite image below it.
   */
  void difference() throws IOException;

  /**
   * Adds an image layer to the project.
   * This is done so that more images can be modified and added to create a more detailed collage.
   * @throws IOException if view cannot be transmitted.
   */
  void addLayer() throws IOException;
}
