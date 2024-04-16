# Headline Formatting

The purpose of this task is to practice using Java Formatting utilities.

Duration: _1 hour_

## Description

In this task, you will provide various formatting patterns and write a simple template engine.
Template engine - it is a utility that replaces placeholders in a template text with provided values.
Binding between placeholder and its value we will call `reference`.

First, let's take a look at the record `ReferencedText`. It has a constructor with two arguments:

1. String text - template text with placeholders
2. Map<String, ReferenceValue> references - a map that contains placeholder names against their values
   It has one explicit method - `format(ReferenceFormat)` which must return processed text,
   i.e. with replaced placeholders. Implementing it is a part of your task.

Next, let's go through the interface `ReferenceFormat`. It has some predefined inner classes:

* enum Type - specifies the type of the placeholder.
  We will need it to determine how specific values must be processed.
* record ReferenceValue - it's just a pair of `Type` and values that defines the placeholder value

Also, this interface has plenty of abstract methods and one default method. Let's describe abstract ones first.
All of them must return a specific pattern, which will be described below. For now, we will go through their purpose:

* `companyPattern` - returns a pattern for placeholders with `Type` = `COMPANY`
* `personNamePattern` - returns a pattern for placeholders with `Type` = `PERSON_NAME`
* `integerPattern` - returns a pattern for placeholders with `Type` = `INTEGER`
* `doublePattern` - returns a pattern for placeholders with `Type` = `DOUBLE`
* `percentPattern` - returns a pattern for placeholders with `Type` = `PERCENT`
* `dateTimePattern` - returns a pattern for placeholders with `Type` = `DATE_TIME`

Their implementation will be described later in the requirements section.

Also, there is `format(ReferenceValue)` method. It is needed to make API more fluent and robust.
It accepts the `ReferenceValue` object, and using its `Type` should determine which pattern
must be used to process the passed value. Please be aware, that not all types might be formatted using the same API.

And the last class is `ReferenceFormatFactory`. It has 2 methods:

* `simpleFormat`
* `formalFormat`

Both of these methods return `ReferenceFormat` instances, their description will be described below.

## Requirements

There will be no invalid inputs during the testing.

* `ReferenceText#format` is implemented:
    * All placeholders are replaced with their corresponding values
    * placeholder looks like `${name}`
    * `Map` of references contains only placeholder names (without ${})
    * if a placeholder occurs multiple times in a template text, then all of its occurrences must be replaced
    * placeholder must be replaced with a properly formatted value
* `ReferenceFormat#format` is implemented:
    * it defines the proper pattern of reference value based on its type and uses it to format the value's parameters
* `simpleFormat` is implemented:
    * company name must be enclosed in double quotas
    * person name goes as usual (first name <space> last name), but the last name must be in uppercase
    * integers must have group separators
    * percents must always have signs, exactly one digit precision, and percent symbol in the end
    * doubles must have group separators and two-digit precision
    * date must be converted to `on <date> at <hour> o'clock`
        * date is just a year, month, and day separated by a dash symbol (-). Month and day must have preceding 0
        * hour must not have any preceding symbols. So if we try to format 7, then the result must be exactly 7, not 07.
* `formatFormat` is implemented:
    * company name must be in uppercase and have leading `LLC`
    * person name must look like last name and first name separated by a comma, exactly in that order
    * integers must have a reserved space symbol for a plus sign and have at least 3 symbols width
    * doubles must be in scientific form, with a preceding sign, and have 2 digits precision
    * percents must have 2 digits precision, leading percent symbol, and preceding 0 if the percent is less than 100
    * date must be converted to `at <time> of <date>`
        * date is a short day (without any preceding symbols), month name abbreviation (Jan, Feb, etc.),
          and year separated by space
        * time is a good old hour and time separated by a colon

## Examples

```java
String template = """
${name} works in ${company} since ${date}. His salary is ${salary} per year, he spends ${spending} of it on games
and buys at least ${games} games a month.
""";

    ReferencedText refText = new ReferencedText(template,Map.of(
    "name",new ReferenceValue(Type.PERSON_NAME,"John","Doe"),
    "company",new ReferenceValue(Type.COMPANY,"Macrohard"),
    "date",new ReferenceValue(Type.DATE_TIME,LocalDateTime.now()),
    "salary",new ReferenceValue(Type.DOUBLE,450000.50),
    "spending",new ReferenceValue(Type.PERCENT,40.37),
    "games",new ReferenceValue(Type.INTEGER,5)
    ));

    refText.format(ReferenceFormatFactory.simpleFormat());
/* result
John DOE works in "Macrohard" since on 2022-09-30 at 5 o'clock. His salary is 450,000.50 per year, he spends +40.4% of it on games
and buys at least 5 games a month.
 */

    refText.format(ReferenceFormatFactory.formatFormat());
/* result
Doe, John works in MACROHARD LLC since at 05:51 of 30 Sep 2022. His salary is +4.50E+05 per year, he spends 40.37% of it on games
and buys at least   5 games a month.
 */
```
