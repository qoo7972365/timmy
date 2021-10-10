package javax.swing.filechooser;

import java.io.File;

public abstract class FileFilter {
  public abstract boolean accept(File paramFile);
  
  public abstract String getDescription();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/filechooser/FileFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */