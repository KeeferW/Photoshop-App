package view;


import java.awt.Component;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;



import controller.CollageControllerImpl;
import controller.CollageOperations;
import controller.NewGuiController;
import model.CollageModel;

import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Class represents the controller to allow user to perform
 * image processing effects via text interface.
 */
public class GuiControllerImlp implements NewGuiController {
  private final CollageModel model;
  private final CollageOperations delegate;
  private GuiView view;

  /**
   * Create a controller for a text view to process an image.
   *
   * @param model the model object to use
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public GuiControllerImlp(CollageModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model is null");
    }
    this.model = model;
    this.delegate = new CollageControllerImpl(this.model, new EmptyView(), new StringReader(""));
  }


  @Override
  public void setView(GuiView view) {
    this.view = view;
    view.addListeners(this);
  }

  @Override
  public void loadImage() throws IOException {
    JFileChooser fchooser = new JFileChooser("res");
    fchooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "ppm", "bpm", "png"));

    if (fchooser.showOpenDialog((Component) this.view) == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String destName = JOptionPane.showInputDialog("Open as: ");
      if (destName.equals("")) {
        this.view.renderMessage("Need to name file to Open as.");
        return;
      }
      String path = f.getAbsolutePath();

      this.delegate.load(path, destName);
      model.Image toSave = this.model.getImage(destName);
      this.view.addImage(path, toSave);
    }
  }

  /**
   * Displays the image on when it is selected in the image list.
   *
   * @param imageName the image to display once selected
   */
  @Override
  public void selectAndDisplayImage(String imageName) {
    model.Image toSave = this.model.getImage(imageName);
    this.view.updateToCurrent(imageName, toSave);
  }

  @Override
  public void filter(String commandName, String name) throws IOException {
    if (commandName == null) {
      this.view.renderMessage("Filter command not selected.");
      return;
    }

    String dest = JOptionPane.showInputDialog("Save as: ");
    switch (commandName) {
      case "add-layer":
        this.model.addLayer();
        break;
      case "add-image-to-layer":
        String layernumber = JOptionPane.showInputDialog("Layer number: ");
        String x = JOptionPane.showInputDialog("x: ");
        String y = JOptionPane.showInputDialog("y: ");
        this.model.addImageToLayer(Integer.parseInt(layernumber), name, Integer.parseInt(x),
            Integer.parseInt(y));
        break;
      case "red-component":
        this.model.redComponent(name, dest);
        break;
      case "green-component":
        this.model.blueComponent(name, dest);
        break;
      case "blue-component":
        this.model.greenComponent(name, dest);
        break;
      case "brighten-value":
        this.model.brightenValue(name, dest);
        break;
      case "brighten-luma":
        this.model.brightenLuma(name, dest);
        break;
      case "brighten-intensity":
        this.model.brightenIntensity(name, dest);
        break;
      case "darken-value":
        this.model.darkenValue(name, dest);
        break;
      case "darken-luma":
        this.model.darkenLuma(name, dest);
        break;
      case "darken-intensity":
        this.model.darkenIntensity(name, dest);
        break;
      case "darken-multiply":
        this.model.darkenMultiply(name, dest);
        break;
      case "brighten-screen":
        this.model.brightenScreen(name, dest);
        break;
      case "difference":
        this.model.difference(name, dest);
        break;
      default:
        return;
    }

    model.Image toSave = this.model.getImage(dest);
    this.view.addImage(dest, toSave);
  }

  @Override
  public void saveImage(String selectedImageName) throws IOException {
    DefaultListModel<String> values = this.view.getImgPixels();
    if (values.getSize() == 0) {
      this.view.renderMessage("No images to save.");
      return;
    }

    if (selectedImageName == null) {
      this.view.renderMessage("No image selected to save.");
      return;
    }

    this.view.renderMessage("In the \"Save As\" box on the next screen, enter the name you would"
            + " like to save"
            + " the file as, followed by one of the following:\n"
            + ".ppm\n"
            + ".png\n"
            + ".bmp\n"
            + ".jpg");

    JFileChooser fileChooser = new JFileChooser("res");
    int response = fileChooser.showSaveDialog((Component) this.view);
    fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Images", "jpg", "ppm", "bmp", "png"));
    if (response == JFileChooser.CANCEL_OPTION) {
      return;
    }
    if (response == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      if (f == null) {
        this.view.renderMessage("Invalid supplied file type. Only .ppm," +
                " .png, .jpg, and .bmp are allowed.");
        return;
      }
      String destinationPath = f.getAbsolutePath();

      model.Image toSave = this.model.getImage(selectedImageName);

      if (toSave == null) {
        throw new IllegalArgumentException("Image to save does not exist.");
      }

      File fileOutput = new File(destinationPath);

      if (f.getAbsolutePath().endsWith(".ppm")) {
        this.view.renderMessage("PPM File is saving. This will take a few seconds." +
                " Please wait...");
        this.savePPM(toSave, fileOutput);
        this.view.renderMessage("PPM Image file saved.");
        return;
      }

      BufferedImage src = new BufferedImage(toSave.getWidth(), toSave.getHeight(), TYPE_INT_RGB);

      for (int col = 0; col < src.getHeight(); col++) {
        for (int row = 0; row < src.getWidth(); row++) {
          Color color = new Color(
                  toSave.getPixel(col, row).getRed(),
                  toSave.getPixel(col, row).getGreen(),
                  toSave.getPixel(col, row).getBlue(),
                  toSave.getPixel(col, row).getAlpha());
          src.setRGB(row, col, color.getRGB());
        }
      }

      try {
        ImageIO.write(src,
                destinationPath.substring(destinationPath.length() - 3),
                new FileOutputStream(fileOutput));
      } catch (IOException e) {
        this.view.renderMessage("File to save to invalid.");
        return;
      }

    }

    this.view.renderMessage("File saved.");
  }


  // saves image as a PPM file
  private void savePPM(model.Image toSave, File fileOutput)
          throws IllegalArgumentException {
    DataOutputStream out;
    try {
      out = new DataOutputStream(new FileOutputStream(fileOutput));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File save failed");
    }
    try {
      out.writeBytes(toSave.toString());
      out.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("File save failed");
    }
  }

}