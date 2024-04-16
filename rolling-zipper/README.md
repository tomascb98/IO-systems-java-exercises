# Rolling Zipper

The purpose of this task is to practice using ZIP related streams in Java.

Duration: _2 hours_

## Description

The idea of rolling zipper is quite simple: zip content into one output, but when a certain condition is met,
change the output destination, repeat. So in this task you'll implement such mechanism and 2 conditions:
1. a specified size of ZIP archive is exceeded
2. a specified amount of files in ZIP archive is met

Those conditions we will call from now on - `RollingPolicy` or just policies.

The size policy is inaccurate by it's nature, so no measures must be taken in order to make an archive of the exact
threshold size. All you have to do is to track the size of the archive and if it reaches or exceeds the specified size,
then you just "roll" to another archive immediately, i.e. with size is already exceeded, then no additional file must
be written into it. Overall, you might have archives which:
* has size more or equal to the threshold
* has size less than the threshold, but AT MOST one

The amount policy is somewhat precise, so the archives might contain either the specified amount of files or less.
But the archive should NEVER contain more files, then was specified.

These policies must be represented in the code as `RollingPolicy` instances, and be returned by the corresponding
methods in `RollingPolicyFactory`.
`RollingPolicy` has just one method, which accepts `ZipEntry` (which specifies a file in the ZIP archive) and returns
`boolean`. If `true` is returned, then the output archive must be changed.

Also, you need to implement the mechanism to "roll" archives.
For that purpose you're provided with class `RollingZipper`. It has just one method `zip` which you must implement.
This method does not return anything, however it has quite a few arguments, so let's take a look at them:
1. `Iterable<File> inputFiles` - some sequence of files, which must be archived. Since the file's order matters for
archiving, we guarantee, that files will not come in some random order, so you don't need to worry about that
2. `RollingPolicy policy` - policy instance which specifies, when archive must be rolled
3. `Iterator<OutputStream> outputs` - it's output parameter of this method, which describes a sequence of outputs,
where archives must be written. When we need to roll to a new archive, we need to pick it from this iterator

Please note, that this method might throw `IOException`.

Let's describe the overall workflow of this method:
```
We zip each input file into the archive provided by outputs iterator. Using rolling policy we check should we change
the output or not. If it returns true, then we closing the current output file and get a new one from iterator.
Otherwise, we continue using the current output.
```

## Requirements

Here a few assumptions:
* `RollingZipper#zip` arguments are never `null` as well as their contents
* `Iterator<OutputStream>` is infinite
* `Iterable<File>` are finite and all it's files are unique in terms of name

The requirements:
* all methods in `RollingPolicyFactory` are implemented
* `RollingZipper#zip` is implemented
* `zip` method writes zip archives into `OutputStream` objects provided by `outputs` iterator
* All input files must be zipped in the order determined by `inputFiles`
* `zip` method must check whether output archive must be replaced by a new one using `policy` argument
* amount policy must check does amount of files in the current archive equal to it's threshold
* size policy must check does the sum of compressed size of files in the current archive
grater or equal to the specified value
* `zip` might throw `IOException` if needed
* input files must preserve their names in the resulting archives
* content of archived files must not be corrupted
* the total amount of files in the resulting archives must be equal to the amount of input files

## Examples

Files:
* file1 - has size 50
* file2 - has size 100
* file3 - has size 100
* file4 - has size 50

```java
Iterable<Files> inputFiles = List.of(file1, file2, file3, file4);
RollingPolicy sizePolicy = RollingPolicyFactory.sizePolicy(100);
RollingPolicy amountPolicy = RollingPolicyFactory.amountPolicy(3);
RollingZipper zipper = new RollingZipper();
Iterator<OutputStream> sizeOutputs = // some infinite iterator which returns files with name prefix: "size-"
Iterator<OutputStream> amountOutputs = // some infinite iterator which returns files with name prefix: "amount-"

zipper.zip(inputFiles, sizePolicy, sizeOutputs);
zipper.zip(inputFiles, amountPolicy, amountOutputs);
```

The resulting files:
* size-1
  * file1
  * file2
* size-2
  * file3
* size-3
  * file4

* amount-1
  * file1
  * file2
* amount-2
  * file3
  * file4

If we change the order of files to `file1, file4, file2, file3`, then the result for size policy in that case will be:
* size-1
  * file1
  * file4
* size-2
  * file2
* size-3
  * file3
