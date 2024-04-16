package edu.epam.fop.io.headline;

import java.time.LocalDateTime;
import java.util.Arrays;

public interface ReferenceFormat {
  
  enum Type {
    COMPANY(String.class),
    PERSON_NAME(String.class, String.class),
    INTEGER(Integer.class),
    PERCENT(Double.class),
    DOUBLE(Double.class),
    DATE_TIME(LocalDateTime.class);
    
    private final Class<?>[] validParameters;
    
    Type(Class<?>... validParameters) {
      this.validParameters = validParameters;
    }
    
    private boolean isValid(Object[] parameters) {
      if (validParameters.length != parameters.length) {
        return false;
      }
      for (int i = 0; i < validParameters.length; i++) {
        if (!validParameters[i].isAssignableFrom(parameters[i].getClass())) {
          return false;
        }
      }
      return true;
    }
  }
  
  record ReferenceValue(Type type, Object... parameters) {

    public ReferenceValue {
      if (!type.isValid(parameters)) {
        throw new IllegalArgumentException(type + " does not accept " + Arrays.toString(parameters) + " as input");
      }
    }
  }
  
  default String format(ReferenceValue value) {
    String formattedValue;
    switch (value.type){
      case COMPANY:
        formattedValue = String.format(companyPattern(), value.parameters[0]);
        break;
      case DOUBLE:
        formattedValue = String.format(doublePattern(), value.parameters[0]);
        break;
      case PERCENT:
        formattedValue = String.format(percentPattern(), value.parameters[0]);
        break;
      case INTEGER:
        formattedValue = String.format(integerPattern(), value.parameters[0]);
        break;
      default: formattedValue = "default";
    }
    return formattedValue;
  }
  
  String companyPattern();
  
  String personNamePattern();
  
  String integerPattern();
  
  String doublePattern();
  
  String percentPattern();
  
  String dateTimePattern();
}
