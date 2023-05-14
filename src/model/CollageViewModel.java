package model;

/**
 * Represents an interface for viewing the model. Contains a getImage method which returns
 * an Image object with the given name.
 */
public interface CollageViewModel {
  /**
   * Returns an image with the given name.
   * @param name name of the image
   * @return the image
   * @throws IllegalArgumentException if an image with the given name cannot be retrieved
   */
  Image getImage(String name);
}
