package edu.epam.fop.io.headline;

import java.time.LocalDateTime;

public final class ReferenceFormatFactory {
  
  private ReferenceFormatFactory() {}
  
  public static ReferenceFormat simpleFormat() {
    return new ReferenceFormat() {
      @Override
      public String format(ReferenceValue value) {
        String formattedValue = ReferenceFormat.super.format(value);
        if(!formattedValue.equals("default"))
          return formattedValue;
        else{
          switch (value.type()){
            case DATE_TIME:
              LocalDateTime dateTime = (LocalDateTime) value.parameters()[0];
              formattedValue = String.format(dateTimePattern(), dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour());
              break;
            case PERSON_NAME:
              formattedValue = String.format(personNamePattern(), value.parameters()[0], value.parameters()[1]);
              break;
          }
        }
        return formattedValue;
      }

      @Override
      public String companyPattern() {
        return "\"%s\"";
      }

      @Override
      public String personNamePattern() {
        return "%s %S";
      }

      @Override
      public String integerPattern() {
        return "%,d";
      }

      @Override
      public String doublePattern() {
        return "%,.2f";
      }

      @Override
      public String percentPattern() {
        return "%+.1f%%";
      }

      @Override
      public String dateTimePattern() {
        return "on %d-%02d-%02d at %d o'clock";
      }
    };
  }
  
  public static ReferenceFormat formalFormat() {
    return new ReferenceFormat() {
      @Override
      public String format(ReferenceValue value) {
        String formattedValue = ReferenceFormat.super.format(value);
        if(!formattedValue.equals("default"))
          return formattedValue;
        else{
          switch (value.type()){
            case DATE_TIME:
//              "at %02d:02d of %d %3s %d";
              LocalDateTime dateTime = (LocalDateTime) value.parameters()[0];
              String month = dateTime.getMonth().toString().toLowerCase();
              formattedValue = String.format(dateTimePattern(), dateTime.getHour(), dateTime.getMinute(), dateTime.getDayOfMonth(), month.substring(0,1).toUpperCase() + month.substring(1,3), dateTime.getYear());
              break;
            case PERSON_NAME:
              formattedValue = String.format(personNamePattern(), value.parameters()[1], value.parameters()[0]);
              break;
          }
        }
        return formattedValue;
      }
      @Override
      public String companyPattern() {
        return "%S LLC";
      }

      @Override
      public String personNamePattern() {
        return "%s, %s";
      }

      @Override
      public String integerPattern() {
        return "% 3d";
      }

      @Override
      public String doublePattern() {
        return "%+.2E";
      }

      @Override
      public String percentPattern() {
        return "%05.2f%%";
      }

      @Override
      public String dateTimePattern() {
        return "at %02d:%02d of %d %s %d";
      }
    };
  }
}
