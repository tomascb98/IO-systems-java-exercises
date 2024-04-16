package edu.epam.fop.io;

import java.util.zip.ZipEntry;

public interface RollingPolicy {

  boolean readyToRoll(ZipEntry entry);
}
