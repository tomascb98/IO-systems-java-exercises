package edu.epam.fop.lambdas;

import java.util.*;

// TODO write your implementation here
public interface IntArrayReducers {

  static IntArrayReducer SUMMARIZER = (array) -> {
    int sum = 0;
    for (int number: array) sum += number;
    return sum;
  };

  static IntArrayReducer MULTIPLIER = (array) -> {
    int mult = array[0];
    for (int i = 1; i< array.length; i++) mult *= array[i];
    return mult;
  };

  static IntArrayReducer MIN_FINDER = array -> {
    int min = array[0];
    for (int number:array) {
      if(min > number) min = number;
    }
    return min;
  };

  static IntArrayReducer MAX_FINDER = array -> {
    int max = array[0];
    for (int number:array) {
      if(max < number) max = number;
    }
    return max;
  };

  static IntArrayReducer AVERAGE_CALCULATOR = array -> {
    int sum = SUMMARIZER.reduce(array);
    return (int) Math.round(sum/(double)array.length);
  };

  static IntArrayReducer UNIQUE_COUNTER = array -> {
    Set<Integer> set = new HashSet<>();
    for (int number: array) {
      set.add(number);
    }
    return set.size();
  };

  static IntArrayReducer SORT_DIRECTION_DEFINER = array -> {
    int direction = 0;
    for (int i = 1; i < array.length; i++) {
      if (array[i - 1] > array[i]) {
        if (direction != 1 && direction != -1) {
          direction = -1;
        } else if (direction == 1) {
          return 0; // Already identified as ascending, so not sorted
        }
      } else if (array[i - 1] < array[i]) {
        if (direction != 1 && direction != -1) {
          direction = 1;
        } else if (direction == -1) {
          return 0; // Already identified as descending, so not sorted
        }
      }
    }
    return direction;
  };


}
