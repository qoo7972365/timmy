package java.awt.print;

public interface Pageable {
  public static final int UNKNOWN_NUMBER_OF_PAGES = -1;
  
  int getNumberOfPages();
  
  PageFormat getPageFormat(int paramInt) throws IndexOutOfBoundsException;
  
  Printable getPrintable(int paramInt) throws IndexOutOfBoundsException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/print/Pageable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */