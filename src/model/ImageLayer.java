package model;

/**
 * Represents a layer that images can be applied to.
 */
public class ImageLayer implements Image {

  private Pixel[][] pixels;
  private final int width;
  private final int height;
  private int maxValue;
  private FileType fileType;
  private final String name;

  /**
   * Constructs a named image layer.
   * @param name the name of the layer
   * @param width the width of the layer
   * @param height the height of the layer
   */
  public ImageLayer(String name, int width, int height) {
    this.width = width;
    this.height = height;
    this.name = name;
  }

  @Override
  public Pixel getPixel(int row, int col) {
    return pixels[row][col];
  }

  @Override
  public int getMaxValue() {
    return 0;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public String getName() {
    return name;
  }
}
