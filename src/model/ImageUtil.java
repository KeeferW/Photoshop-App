package model;

import controller.CollageController;
import controller.CollageControllerImpl;
import view.CollageView;
import view.CollageViewImpl;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file and save a PPM image to a file.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static Image readPPM(String filename) throws FileNotFoundException {
    Scanner sc;

    sc = new Scanner(new FileInputStream(filename));

    StringBuilder builder = new StringBuilder();
    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();



    Pixel[][] pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels[i][j] = new PixelImpl(new Color(r, g, b));
      }
    }
    return new ImageImpl("", pixels, maxValue);
  }

  /**
   * Saves the provided image as a ppm file with the provided file name, which includes the path
   * of where the file should be saved.
   * ex: img was saved as "ab" but typed "aB" for save func --> throws NPE
   *
   * @param image    the image to save
   * @param filename the name to use, including the path
   * @throws IOException if an I/O error occurs writing to or creating the file,
   *                     or the text cannot be encoded using the specified charset
   */
  public static void savePPM(Image image, String filename) throws IOException {
    StringBuilder ppm = new StringBuilder();
    ppm.append("P3").append(System.lineSeparator());
    ppm.append(image.getWidth())
            .append(" ")
            .append(image.getHeight())
            .append(System.lineSeparator())
            .append(image.getMaxValue())
            .append(System.lineSeparator());
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int r = image.getPixel(i, j).getRed();
        int g = image.getPixel(i, j).getGreen();
        int b = image.getPixel(i, j).getBlue();
        ppm.append(r).append(System.lineSeparator());
        ppm.append(g).append(System.lineSeparator());
        ppm.append(b).append(System.lineSeparator());

      }
    }
    Files.writeString(Paths.get(filename), ppm.toString(), StandardCharsets.UTF_8);
  }

  /**
   * Runs the program.
   *
   * @param args command line arguments, not yet supported
   */
  public static void main(String[] args) {
    Readable r;
    if (args.length > 0) {
      StringBuilder commands = new StringBuilder();

      if (!args[0].equalsIgnoreCase("-l")) {
        throw new IllegalArgumentException("Command line must start by loading file with -l");
      }
      if (!args[args.length - 2].equalsIgnoreCase("-s")) {
        throw new IllegalArgumentException("Command line must end by saving file with -s filename");
      }
      commands.append("load ").append(args[1]).append(" file "); // load file

      for (int i = 2; i < args.length - 2; i++) {
        if (args[i].equalsIgnoreCase("-e")) {
          i++;
          String effect = args[i];
          commands.append(effect).append(" file file"); // process
        }
      }

      commands.append(" save ").append(args[args.length - 1]).append(".ppm ").append("file q");

      System.out.println(commands);
      System.out.println();
      r = new StringReader(commands.toString());
    } else {
      r = new InputStreamReader(System.in);
    }
    CollageModel m = new CollageModelImpl();
    CollageView v = new CollageViewImpl(m);
    CollageController c = new CollageControllerImpl(m, v, r);
    c.run();
  }
}