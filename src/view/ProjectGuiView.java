package view;


import javax.swing.JPanel;

/**
 * Holds methods to render and refresh the GUI.
 */
public interface ProjectGuiView {
  /**
   * Refresh the screen. This is called when the something on the
   * screen is updated and therefore it must be redrawn.
   */
  void refresh(JPanel panel);

  /**
   * Display a message in a suitable area of the GUI.
   * @param message the message to be displayed
   */
  void renderMessage(String message);
}
