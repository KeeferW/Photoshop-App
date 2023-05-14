package view;

import java.io.IOException;
import model.CollageViewModel;

/**
 * Class that displays user interaction with an image.
 */
public class CollageViewImpl implements CollageView {
  private final Appendable ap;

  /**
   * Construct a view of the image from the provided model.
   * @param model - the model
   * @throws IllegalArgumentException if the model is null
   */
  public CollageViewImpl(CollageViewModel model) throws IllegalArgumentException {
    this(model, System.out);
  }

  /**
   * Constructs a view for a model and a destination.
   *
   * @param model - the model
   * @param ap - the appendable
   * @throws IllegalArgumentException if the given model or appendable is null
   */
  public CollageViewImpl(CollageViewModel model, Appendable ap)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable is null");
    }
    this.ap = ap;
  }


  @Override
  public void renderMessage(String message) throws IOException {
    this.ap.append(message + "\n");
  }
}