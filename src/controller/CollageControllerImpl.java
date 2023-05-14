package controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.CollageModel;
import model.Pixel;
import view.CollageView;

import javax.imageio.ImageIO;


import static java.lang.Double.parseDouble;

/**
 * This controller allows the user to load, save, add images/layers, and perform filters
 * on them using given commands. The user has to first start a project, then add a layer, then load
 * an image onto the layer in order to add.
 */
public class CollageControllerImpl
        implements CollageController, CollageOperations {
  private final CollageModel model;
  private final CollageView view;
  private final Scanner sc;
  private final String resPath = "";
  private BufferedImage loadImage;

  /**
   * Constructs a controller for the text view to process an image.
   * This is done so that all non-static methods can be carried out.
   *
   * @param model the model object to use.
   * @param view  the view object to use.
   * @param rd    the readable object to use.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public CollageControllerImpl(
          CollageModel model, CollageView view, Readable rd)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model is null");
    }
    if (view == null) {
      throw new IllegalArgumentException("The view is null");
    }
    if (rd == null) {
      throw new IllegalArgumentException("The readable object is null");
    }
    this.model = model;
    this.view = view;
    this.sc = new Scanner(rd);
    this.sc.useDelimiter(",|\\s+");
  }

  /**
   * Displays possible commands and begins accepting user input.
   * This is done so operations can be carried out on images
   * and the program can be used effectively used.
   *
   * @throws IllegalStateException if the controller cannot read/write a file.
   */
  @Override
  public void run() throws IllegalStateException {
    boolean quit = false;
    String operation;
    try {
      this.menu();
      while (!quit) {
        this.view.renderMessage("Enter Operation: ");
        operation = sc.next().toLowerCase();
        try {
          switch (operation) {
            case "new-project":
              this.newProject();
              break;
            case "add-layer":
              this.addLayer();
              break;
            case "add-image-to-layer":
              this.addImageToLayer();
              break;
            case "set-filter":
              this.setFilter();
              break;
            case "load":
              this.load();
              break;
            case "save":
              this.save();
              break;
            case "red-component":
              this.redComponent();
              break;
            case "green-component":
              this.greenComponent();
              break;
            case "blue-component":
              this.blueComponent();
              break;
            case "brighten-value":
              this.brightenValue();
              break;
            case "darken-value":
              this.darkenValue();
              break;
            case "brighten-luma":
              this.brightenLuma();
              break;
            case "darken-luma":
              this.darkenLuma();
              break;
            case "brighten-intensity":
              this.brightenIntensity();
              break;
            case "darken-intensity":
              this.darkenIntensity();
              break;
            case "darken-multiply":
              this.darkenMultiply();
              break;
            case "brighten-screen":
              this.brightenScreen();
              break;
            case "difference":
              this.difference();
              break;
            case "q":
              quit = true;
              this.view.renderMessage("Quitting, please wait...");
              break;
            default:
              this.view.renderMessage("The provided operation is not supported");
          }
        } catch (IllegalArgumentException | IllegalStateException e) {
          this.view.renderMessage("Error: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Inputs or outputs failed." + System.lineSeparator());
    }
  }

  /**
   * Displays all commands for a Project in list form.
   * Makes it so all possible operations and button options can be
   * viewed so that the user can click on and use them.
   *
   * @throws IOException if the view cannot be transmitted.
   */
  @Override
  public void menu() throws IOException {
    String menu = "new-project canvas-height canvas-width\n"
            + "add-layer layer-name\n"
            + "add-image-to-layer layer-name image-name x-pos y-pos\n"
            + "set-filter layer-name filter-option\n"
            + "load-project path-to-project-file\n"
            + "red-component name destination\n"
            + "green-component name destination\n"
            + "blue-component name destination\n"
            + "brighten-value name destination\n"
            + "darken-value name destination\n"
            + "brighten-luma name destination\n"
            + "darken-luma name destination\n"
            + "brighten-intensity name destination\n"
            + "darken-intensity name destination\n"
            + "darken-multiply name destination increment\n"
            + "brighten-screen name destination increment\n"
            + "difference name destination\n"
            + "save path name\n"
            + "q";
    this.view.renderMessage(menu);
  }

  @Override
  public void newProject() throws IllegalArgumentException, IOException {
    int w = Integer.parseInt((sc.next()));
    int h = Integer.parseInt((sc.next()));
    this.model.createNewProject(w, h);
    this.view.renderMessage(
            String.format("New %s x %s project created.",
                    w, h));
  }

  @Override
  public void load() throws IOException {
    String path = this.resPath + sc.next();
    String name = sc.next();
    try {
      this.model.load(path, name);
    } catch (FileNotFoundException e) {
      this.view.renderMessage(e.getMessage());
      return;
    }
    this.view.renderMessage(String.format("Image %s was loaded and called %s", path, name));
  }

  @Override
  public void load(String imagePath, String imageName) throws IllegalArgumentException {
    if (imagePath.endsWith(".ppm")) {
      try {
        this.model.loadPPM(imagePath);
        return;
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }

    try {
      loadImage = ImageIO.read(new FileInputStream(imagePath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Cant read file.");
    }

    int maxValue = 0;
    Pixel[][] pixels = new Pixel[model.getHeight()][model.getWidth()];

    this.model.addImage(imageName, pixels, maxValue, loadImage);
  }

  @Override
  public void addLayer() throws IOException {
    String name = sc.next();
    this.model.addLayer();
    this.view.renderMessage("layer was saved as " + name);
  }

  @Override
  public void addImageToLayer() throws IOException {
    String layerN = sc.next();
    int layernumber = Integer.parseInt(layerN);
    String imageName = sc.next();
    int x = Integer.parseInt(sc.next());
    int y = Integer.parseInt(sc.next());
    this.model.addImageToLayer(layernumber, imageName, x, y);
    this.view.renderMessage(
            String.format("Image %s added to Layer %s at (%s, %s)",
                    imageName, layernumber, x, y));
  }

  @Override
  public void setFilter() throws IOException {
    String layerN = sc.next();
    int layernumber = Integer.parseInt(layerN);
    String filterOption = sc.next();
    this.model.setFilter((layernumber), filterOption);

    this.view.renderMessage(String.format("Filter %s applied to Layer %s",
            filterOption, layernumber));
  }

  @Override
  public void save() throws IOException {
    String path = this.resPath + sc.next();
    String name = sc.next();
    this.model.save(path, name);
    this.view.renderMessage(String.format("%s was saved as %s", name, path));
  }

  @Override
  public void redComponent() throws IllegalArgumentException, IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.redComponent(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a red image called %s",
                    name, dest));
  }


  @Override
  public void greenComponent() throws IllegalArgumentException, IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.greenComponent(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a green image called %s",
                    name, dest));
  }


  @Override
  public void blueComponent() throws IllegalArgumentException, IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.blueComponent(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a blue image called %s",
                    name, dest));
  }


  @Override
  public void brightenValue() throws IllegalArgumentException, IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.brightenValue(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a brightened-value image called %s",
                    name, dest));
  }

  @Override
  public void darkenValue() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.darkenValue(name, dest);
    this.view.renderMessage(name + " was saved as a darkened-value image called " + dest);
  }


  @Override
  public void brightenLuma() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.brightenLuma(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a brightened-luma image called %s",
                    name, dest));
  }

  @Override
  public void darkenLuma() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.darkenLuma(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a darkened-luma image called %s",
                    name, dest));
  }

  @Override
  public void brightenIntensity() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.brightenIntensity(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a brightened-intensity image called %s",
                    name, dest));
  }

  @Override
  public void darkenIntensity() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.darkenIntensity(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a darkened-intensity image called %s",
                    name, dest));
  }

  @Override
  public void darkenMultiply() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    double increment = parseDouble(sc.next());
    this.model.darkenMultiply(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a darkened-multiply image " +
                            "called %s and darkened by %s",
                    name, dest, increment));
  }

  @Override
  public void brightenScreen() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    double increment = parseDouble(sc.next());
    this.model.brightenScreen(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a brightened-screen image " +
                            "called %s and brightened by %s",
                    name, dest, increment));
  }

  @Override
  public void difference() throws IOException {
    String name = sc.next();
    String dest = sc.next();
    this.model.difference(name, dest);
    this.view.renderMessage(
            String.format("%s was saved as a differenced image " +
                            "called %s",
                    name, dest));
  }
}
