package model;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Implements CollageModel, holds all the functions the user can apply.
 * Includes adding images and layers, loading images, applying filters,
 * and saving composite images.
 */
public class CollageModelImpl implements CollageModel {
  private final Map<String, Image> images;
  private final ArrayList<LayerImpl> layers;
  private final List<String> filterOptions;
  private int width;
  private int height;
  private boolean projectMade;

  /**
   * Constructs a model object, initializes images and layers and the filter options.
   */
  public CollageModelImpl() {
    this.images = new HashMap<>();
    this.layers = new ArrayList<>();
    this.filterOptions = new ArrayList<>();
    projectMade = false;

    filterOptions.add("normal");
    filterOptions.add("red-component");
    filterOptions.add("green-component");
    filterOptions.add("green-component");
    filterOptions.add("brighten-value");
    filterOptions.add("brighten-intensity");
    filterOptions.add("brighten-luma");
    filterOptions.add("darken-value");
    filterOptions.add("darken-intensity");
    filterOptions.add("darken-luma");
    filterOptions.add("darken-multiply");
    filterOptions.add("brighten-screen");
    filterOptions.add("difference");

  }


  /**
   * Creates a new project to place layers and images ontop of.
   * @param width - the width in pixels of the project
   * @param height - the height in pixels of the project
   * @throws IllegalArgumentException if the arguments passed are too big or too small
   */
  @Override
  public void createNewProject(int width, int height) throws IllegalArgumentException {
    if (width <= 100 || height <= 100 || width >= 1000 || height >= 1000) {
      throw new IllegalArgumentException("Invalid height/width.");
    }
    this.width = width;
    this.height = height;

    projectMade = true;
  }

  /**
   * Adds a layer to the project to place images ontop of.
   */
  @Override
  public void addLayer() {
    layers.add(new LayerImpl(width, height));
  }

  /**
   * Sets the filter to be applied to the whole image.
   * @param layerNumber - the number of the layer
   * @param filterOption - the option
   */
  @Override
  public void setFilter(int layerNumber, String filterOption) {
    if (layers.size() == 0) {
      throw new IllegalArgumentException("Unable to find layer: " + layerNumber);
    }
    if (!filterOptions.contains(filterOption.toLowerCase())) {
      throw new IllegalArgumentException("Unable to find filter option: " + filterOption);
    }
    switch (filterOption) {
      case "normal":
        break;
      case "red-component":
        this.redComponent("", "");
        break;
      case "green-component":
        this.greenComponent("", "");
        break;
      case "blue-component":
        this.blueComponent("", "");
        break;
      case "brighten-value":
        this.brightenValue("", "");
        break;
      case "brighten-intensity":
        this.brightenIntensity("", "");
        break;
      case "brighten-luma":
        this.brightenLuma("", "");
        break;
      case "darken-value":
        this.darkenValue("", "");
        break;
      case "darken-intensity":
        this.darkenIntensity("", "");
        break;
      case "darken-luma":
        this.darkenLuma("", "");
        break;
      default:
        throw new IllegalArgumentException("Please enter a valid command");
    }
  }

  @Override
  public void addImageToLayer(int layernumber, String imageName, int x, int y) {
    if (layernumber > layers.size()) {
      throw new IllegalArgumentException("Unable to find layer: " + layernumber);
    }
    if (!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Unable to find image: " + imageName);
    }
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid x or y position");
    }
    layers.get(layernumber).placeImage(images.get(imageName), x, y);
  }

  @Override
  public void addImage(String imageName, Pixel[][] pixels, int maxValue, BufferedImage loadImage) {
    maxValue = 0;
    pixels = new Pixel[this.getHeight()][this.getWidth()];

    for (int col = 0; col < this.getWidth(); col++) {
      for (int row = 0; row < this.getHeight(); row++) {
        Color color = new Color(loadImage.getRGB(col, row));
        int localMax = Math.max(color.getGreen(), Math.max(color.getRed(), color.getBlue()));
        if (localMax > maxValue) {
          maxValue = localMax;
        }

        pixels[row][col] = new PixelImpl(new Color(color.getRed(), color.getGreen(),
                color.getBlue()));
      }
    }
    this.images.put(imageName, new ImageImpl(imageName, pixels, maxValue));
  }

  @Override
  public Image getImage(String name) throws IllegalArgumentException {
    Image i = this.images.get(name);
    if (i == null) {
      throw new IllegalArgumentException(
              String.format("Image with the provided name '%s' does not exist", name));
    }
    return i;
  }

  @Override
  public void load(String path, String name) throws FileNotFoundException {
    Image loaded;
    try {
      loaded = ImageUtil.readPPM(path);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(String.format("The provided path '%s' does not exist", path));
    }
    this.images.put(name, loaded);
  }

  @Override
  public void save(String path, String name) throws IllegalArgumentException, IOException,
          IllegalStateException {
    ImageUtil.savePPM(this.getImage(name), path);
  }

  @Override
  public void redComponent(String name, String dest) throws IllegalArgumentException {
    Func<Image, Integer, Integer, Pixel> red = (original, i, j) ->
            new PixelImpl(new Color(original.getPixel(i, j).getBlue(), 0 , 0));
    this.applyToImage(name, dest, red);
  }

  @Override
  public void greenComponent(String name, String dest) throws IllegalArgumentException {
    Func<Image, Integer, Integer, Pixel> green = (original, i, j) ->
            new PixelImpl(new Color(0, original.getPixel(i, j).getGreen(), 0));
    this.applyToImage(name, dest, green);
  }

  @Override
  public void blueComponent(String name, String dest) throws IllegalArgumentException {
    Func<Image, Integer, Integer, Pixel> blue = (original, i, j) ->
            new PixelImpl(new Color(0, 0, original.getPixel(i, j).getBlue()));
    this.applyToImage(name, dest, blue);
  }

  @Override
  public void brightenValue(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> value = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      int maxVal = p.getValue();
      return new PixelImpl(new Color(
              Math.min(p.getMaxRGB(), p.getRed() + maxVal),
              Math.min(p.getMaxRGB(), p.getGreen() + maxVal),
              Math.min(p.getMaxRGB(), p.getBlue() + maxVal)));
    };
    this.applyToImage(name, dest, value);
  }

  @Override
  public void brightenLuma(String name, String dest)  {
    Func<Image, Integer, Integer, Pixel> luma = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      int lumaVal = (int) (0.2126 * p.getRed()
              + 0.7152 * p.getGreen() + 0.0722 * p.getBlue());
      return new PixelImpl(new Color(
              Math.min(p.getMaxRGB(), p.getRed() + lumaVal),
              Math.min(p.getMaxRGB(), p.getGreen() + lumaVal),
              Math.min(p.getMaxRGB(), p.getBlue() + lumaVal)));
    };
    this.applyToImage(name, dest, luma);
  }

  @Override
  public void brightenIntensity(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> intensity = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      int intensityVal = ((p.getRed() + p.getBlue() + p.getGreen()) / 3);
      return new PixelImpl(new Color(
              Math.min(p.getMaxRGB(), p.getRed() + intensityVal),
              Math.min(p.getMaxRGB(), p.getGreen() + intensityVal),
              Math.min(p.getMaxRGB(), p.getBlue() + intensityVal)));
    };
    this.applyToImage(name, dest, intensity);
  }

  @Override
  public void darkenValue(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> value = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      int maxVal = p.getValue();
      return new PixelImpl(new Color(
              Math.max(0, p.getRed() - maxVal),
              Math.max(0, p.getGreen() - maxVal),
              Math.max(0, p.getBlue() - maxVal)));
    };
    this.applyToImage(name, dest, value);
  }

  @Override
  public void darkenLuma(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> luma = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      int lumaVal = (int) (0.2126 * p.getRed()
              + 0.7152 * p.getGreen() + 0.0722 * p.getBlue());
      return new PixelImpl(new Color(
              Math.max(0, p.getRed() - lumaVal),
              Math.max(0, p.getGreen() - lumaVal),
              Math.max(0, p.getBlue() - lumaVal)));
    };
    this.applyToImage(name, dest, luma);
  }

  @Override
  public void darkenIntensity(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> intensity = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      int intensityVal = ((p.getRed() + p.getBlue() + p.getGreen()) / 3);
      return new PixelImpl(new Color(
              Math.max(0, p.getRed() - intensityVal),
              Math.max(0, p.getGreen() - intensityVal),
              Math.max(0, p.getBlue() - intensityVal)));
    };
    this.applyToImage(name, dest, intensity);
  }


  @Override
  public void darkenMultiply(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> darkenMultiply = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      HashMap<String, Double> hsl = p.convertRGBtoHSL();
      int dL = findDl(original, i, j, name);
      hsl.replace("l", hsl.get("l") * dL);
      return convertHSLtoRGB(hsl.get("h"), hsl.get("s"), hsl.get("l"));
    };

    this.applyToImage(name, dest, darkenMultiply);
  }

  /**
   * Convers an HSL representation where.
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   * into an RGB representation where each component is in the range 0-1
   * @param hue hue of the HSL representation
   * @param saturation saturation of the HSL representation
   * @param lightness lightness of the HSL representation
   */
  private Pixel convertHSLtoRGB(double hue, double saturation, double lightness) {
    double r = convertFn(hue, saturation, lightness, 0) * 255;
    double g = convertFn(hue, saturation, lightness, 8) * 255;
    double b = convertFn(hue, saturation, lightness, 4) * 255;
    return new PixelImpl(new Color((int) r, (int) g, (int) b));
  }

  /*
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model
   */
  private double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue / 30)) % 12;
    double a  = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }

  @Override
  public void brightenScreen(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> brightenScreen = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      HashMap<String, Double> hsl = p.convertRGBtoHSL();
      int dL = findDl(original, i, j, name);
      hsl.replace("l", 1 - ((1 - hsl.get("l")) * (1 - dL)));
      return convertHSLtoRGB(hsl.get("h"), hsl.get("s"), hsl.get("l"));
    };
    this.applyToImage(name, dest, brightenScreen);
  }

  @Override
  public void difference(String name, String dest) {
    Func<Image, Integer, Integer, Pixel> difference = (original, i, j) -> {
      Pixel p = original.getPixel(i, j);
      Color currentColor = p.getColor();
      LayerImpl bottomLayer = layers.get(layers.size() - 1);
      Image bottomImg = bottomLayer.getImages().get(name);

      int rDiff = Math.abs(currentColor.getRed() - bottomImg.getPixel(i, j).getRed());
      int gDiff = Math.abs(currentColor.getGreen() - bottomImg.getPixel(i, j).getGreen());
      int bDiff = Math.abs(currentColor.getBlue() - bottomImg.getPixel(i, j).getBlue());
      return new PixelImpl(new Color(rDiff, gDiff, bDiff));
    };
    this.applyToImage(name, dest, difference);
  }

  /**
   * Finds the dL value for the composite image underneath.
   * @param original - the original image
   * @param x - the x value of the pixel position
   * @param y - the y value of the pixel position
   * @param name - the name of the image underneath
   * @return the dL value as an int
   */
  private int findDl(Image original, int x, int y, String name) {
    Pixel p = original.getPixel(x, y);
    LayerImpl bottomLayer = layers.get(layers.size() - 1);
    Image bottomImg = bottomLayer.getImages().get(name);
    int dL;
    if (p.getBlue() == 255 && p.getRed() == 255 && p.getGreen() == 255) {
      dL = 1;
    }
    else {
      dL = bottomImg.getMaxValue() / 255;
    }
    return dL;
  }

  @Override
  public boolean projectMade() {
    return projectMade;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Applies a filter to the entire image and saves it in a map.
   * @param name name of the image
   * @param dest name of the new image
   * @param filter type of filter to be applied
   * @throws IllegalArgumentException if any of the args are null
   */
  private void applyToImage(String name, String dest,
                            Func<Image, Integer, Integer, Pixel> filter)
          throws IllegalArgumentException {

    if (name == null || dest == null || filter == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    Image original = this.getImage(name);
    int height = original.getHeight();
    int width = original.getWidth();
    Pixel[][] newPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newPixels[i][j] = filter.apply(original, i, j);
      }
    }
    this.images.put(dest, new ImageImpl(dest, newPixels, original.getMaxValue()));
  }

  @Override
  public ArrayList<LayerImpl> getLayers() {
    return this.layers;
  }

  public void loadPPM(String imagePath) throws IllegalArgumentException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid supplied file.");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.equals("") && s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator()); //add all non-comment lines
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;

    if (sc.hasNext()) {
      token = sc.next();
    } else {
      throw new IllegalArgumentException("Image to load is blank.");
    }
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int height = 0;
    int width = 0;
    if (sc.hasNextInt()) {
      width = sc.nextInt();
    }
    if (sc.hasNextInt()) {
      height = sc.nextInt();
    }

    Pixel[][] pixels = new Pixel[height][width];

    int red = 0;
    int green = 0;
    int blue = 0;
    int maxValue = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (sc.hasNextInt()) {
          red = sc.nextInt();
        }
        if (sc.hasNextInt()) {
          green = sc.nextInt();
        }
        if (sc.hasNextInt()) {
          blue = sc.nextInt();
        }
        if (sc.hasNextInt()) {
          maxValue = sc.nextInt();
        }
        pixels[row][col] = new PixelImpl(new Color(red, green, blue));
      }
    }
  }

}
