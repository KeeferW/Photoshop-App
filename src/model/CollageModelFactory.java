package model;

/**
 * Represents an interface to create a model object to help with decoupling.
 */
public interface CollageModelFactory {

  /**
   * Returns a CollageModel.
   * @return
   */
  CollageModel createCollageModel();
}