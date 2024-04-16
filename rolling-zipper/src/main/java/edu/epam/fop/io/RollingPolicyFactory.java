package edu.epam.fop.io;


import java.util.zip.ZipEntry;


public final class RollingPolicyFactory {

  private RollingPolicyFactory() {
    throw new UnsupportedOperationException();
  }

  public static RollingPolicy sizePolicy(long sizeThreshold) {
    return new RollingPolicy() {
      long zipSize = 0;
      @Override
      public boolean readyToRoll(ZipEntry entry) {
        zipSize += entry.getCompressedSize();
        if(zipSize <= sizeThreshold)
          return false;
        zipSize = 0;
        return true;
      }
    };
  }

  public static RollingPolicy amountPolicy(long amountThreshold) {
    return new RollingPolicy() {
      long entryCount = 0;
      @Override
      public boolean readyToRoll(ZipEntry entry) {
        entryCount++;
        if(entryCount < amountThreshold)
          return false;
        entryCount = 0;
        return true;
      }
    };
  }
}
