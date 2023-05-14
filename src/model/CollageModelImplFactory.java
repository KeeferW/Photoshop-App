package model;

/**
 * Factory class that contains one method to create a model object.
 */
public class CollageModelImplFactory implements CollageModelFactory {

  /**
   * Returns a CollageModelObject.
   * @return a CollageModelObject
   */
  @Override
  public CollageModel createCollageModel() {
    return new CollageModelImpl();
  }
}