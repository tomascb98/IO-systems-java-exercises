package edu.epam.fop.io.headline;

import edu.epam.fop.io.headline.ReferenceFormat.ReferenceValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public record ReferencedText(String text, Map<String, ReferenceValue> references) {
  
  public String format(ReferenceFormat format) {
    String textFormatted = text;
    Set<Map.Entry<String, ReferenceValue>> entries = references.entrySet();
    for(Map.Entry<String, ReferenceValue> entry : entries)
        textFormatted = textFormatted.replaceAll("\\$\\{"+entry.getKey()+"\\}", format.format(entry.getValue()));
    return textFormatted;
  }

  public static void main(String[] args) {
    String inputString = "Hello, ${name}! Welcome to ${place}${name}${name}${name}${name}${name}.";
    Map<String, String> values = new HashMap<>();
    values.put("name", "John");
    values.put("place", "Java World");

    String outputString = inputString;

    for (String key : values.keySet()) {
      outputString = outputString.replaceAll("\\$\\{" + key + "\\}", values.get(key));
    }

    System.out.println(outputString); // Output: Hello, John! Welcome to Java World.

  }
}
