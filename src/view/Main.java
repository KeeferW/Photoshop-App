package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import controller.CollageController;
import controller.CollageControllerImpl;
import model.CollageModel;
import model.CollageModelFactory;
import model.CollageModelImplFactory;

/**
 * Accepts command line arguments AND input from System.in. Arguments are
 * accepted in the format of .txt files where you enter -file script.txt.
 * If the user chooses text based version of the program, type -text into the command line
 * If none are supplied, the user will be asked to use the GUI instead.
 */
public final class Main {

  /**
   * Used to run the program and use the command line arguments from the file or input from the
   * user.
   *
   * @param args Needs to be supplied as a .txt file. If there is none, input is taken directly
   *             from user typing.
   */
  public static void main(String[] args) throws IOException {
    StringBuilder builder = new StringBuilder();
    CollageModelFactory factory = new CollageModelImplFactory();
    CollageModel model = factory.createCollageModel();
    CollageView simpleView;
    GuiView guiView;
    NewGuiController guiController;
    CollageController controller;

    StringBuilder contents = new StringBuilder();

    for (String s : args) {
      contents.append(s);
    }

    // accepts argument from user - GUI
    if (contents.toString().equals("")) {
      guiView = new JavaSwingView(900, 800);
      guiController = new GuiControllerImlp(model);
      guiController.setView(guiView);
    }
    // accepts argument from user - Console
    else if (args.length == 1 && args[0].equals("-text")) {
      simpleView = new CollageViewImpl(model);
      controller = new CollageControllerImpl(model, simpleView, new InputStreamReader(System.in));
      controller.run();
    }
    // runs through script of commands - Console
    else if (args.length == 2 && args[0].equals("-file") && args[1].endsWith(".txt")) {
      simpleView = new CollageViewImpl(model);

      BufferedReader bufferedReader;
      try {
        bufferedReader = new BufferedReader(new FileReader(args[1]));
      } catch (FileNotFoundException e) {
        simpleView.renderMessage("Supplied script could not be read.");
        return;
      }

      try {
        String line = bufferedReader.readLine();

        while (line != null) {
          builder.append(line);
          builder.append(System.lineSeparator());
          line = bufferedReader.readLine();
        }
        bufferedReader.close();
      } catch (IOException e) {
        simpleView.renderMessage("Cannot read supplied file");
      }

      controller = new CollageControllerImpl(model, simpleView,
              new StringReader(builder.toString()));
      try {
        controller.run();
      } catch (IllegalStateException e) {
        simpleView.renderMessage("Program quit.");
      }

    } else {
      simpleView = new CollageViewImpl(model);
      simpleView.renderMessage("Invalid command line arguments.");
    }
  }

}

