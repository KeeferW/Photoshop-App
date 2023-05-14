package controller;

import java.io.IOException;

/**
 * Represents a controller for the user to run and interact with the Collage operations.
 * Contains the run method that prompts the user to enter commands and starts accepting input.
 * Also allows for menu to be displayed so that operations can be viewed.
 */
public interface CollageController {

  /**
   * Displays all commands for a Project in list form.
   * Makes it so all possible operations and button options can be
   * viewed so that the user can click on and use them.
   *
   * @throws IOException if the view cannot be transmitted.
   */
  void menu() throws IOException;

  /**
   * Displays possible commands and begins accepting user input.
   * This is done so operations can be carried out on images
   * and the program can be used effectively used.
   *
   * @throws IllegalStateException if the controller cannot read/write a file.
   */
  void run() throws IllegalStateException;

}
