package view;

import java.io.IOException;

/**
 * Represents the view for a Collage.
 */
public interface CollageView {
  /**
   * Transmits a message to the view.
   * @param message a message
   * @throws IOException if the message cannot be transmitted to the view
   */
  void renderMessage(String message) throws IOException;
}