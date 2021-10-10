package java.awt.print;

import java.awt.Graphics;

public interface Printable {
  public static final int PAGE_EXISTS = 0;
  
  public static final int NO_SUCH_PAGE = 1;
  
  int print(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt) throws PrinterException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/print/Printable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */