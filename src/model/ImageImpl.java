package model;

/**
 * Represents an image containing a 2d array of pixels.
 */
public class ImageImpl implements Image {
  private Pixel[][] pixels;
  private final int width;
  private final int height;
  private final int maxValue;
  private final String name;

  //dimensions of pixels should be the right size

  /**
   * Constructs an Image.
   * @param name - name of the image
   * @param pixels - 2d array of pixels that make up the image
   * @param maxValue - the maximum RGB value of each pixel
   * @throws IllegalArgumentException if the pixel array is null
   */
  public ImageImpl(String name, Pixel[][] pixels, int maxValue)
          throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixel array must be non-null");
    }
    this.pixels = pixels;
    this.width = this.pixels[0].length;
    this.height = this.pixels.length;
    this.maxValue = maxValue;

    this.name = name;
  }

  @Override
  public Pixel getPixel(int col, int row) {
    return this.pixels[col][row];
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public String getName() {
    return name;
  }
}
