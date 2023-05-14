package view;

import javax.swing.DefaultListModel;

import controller.NewGuiController;


/**
 * Represents a view for the GUI. Helps to display the image when loaded and updates the image
 * when a filter is applied to it.
 */
public interface GuiView extends CollageView {

  /**
   * Lets the Gui have action-listener functionality.
   * @param options of the controller
   */
  void addListeners(NewGuiController options);

  /**
   * Adds an image to this view so that it can display it through the GUI.
   *
   * @param destName    the name of the image
   */
  void addImage(String destName, model.Image toSave);

  /**
   * Updates the view to display the image being worked on.
   *
   * @param imageName the image to display
   */
  void updateToCurrent(String imageName, model.Image toSave);

  /**
   * Copies the list of images loaded in the program.
   *
   * @return a list of images that are loaded.
   */
  DefaultListModel<String> getImgPixels();


}
