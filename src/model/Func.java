package model;

/**
 * Represents a function that takes in three inputs and changes a specific value.
 * @param <T> first input
 * @param <Q> second input
 * @param <S> third input
 * @param <R> type of output
 */
public interface Func<T, Q, S, R> {
  /**
   * Applies the function.
   * @param input1 the first input
   * @param input2 the second input
   * @param input3 the third input
   * @return something of R type
   */
  R apply(T input1, Q input2, S input3);
}