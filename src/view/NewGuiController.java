package view;

import java.io.IOException;

/**
 * Represents a GUIController that can accept input for the GUI and sends it to the model.
 */
public interface NewGuiController {

  /**
   * Loads image by clicking the Open button.
   */
  void loadImage() throws IOException;

  /**
   * Displays the chosen image onto the screen in the designated image box.
   *
   * @param imageName name of the image
   */
  void selectAndDisplayImage(String imageName);

  /**
   * Applies a filter to the image/layer.
   *
   * @param filter name of the filter
   * @param imageName   name of the image
   */
  void filter(String filter, String imageName) throws IOException;

  /**
   * Save the image that's currently loaded.
   *
   * @param selectedImage name of the image to be saved
   */
  void saveImage(String selectedImage) throws IOException;

  /**
   * Initializes the view.
   *
   * @param view to be displayed as GUI
   */
  void setView(GuiView view);
}
