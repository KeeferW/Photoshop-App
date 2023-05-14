package view;

import view.CollageView;

/**
 * An empty view which that acts as a placeholder in the GuiController.
 */
public class EmptyView implements CollageView {

  /**
   * Render message which returns nothing.
   *
   * @param message to be transmitted
   * @throws IllegalStateException never
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    //does nothing
  }
}
