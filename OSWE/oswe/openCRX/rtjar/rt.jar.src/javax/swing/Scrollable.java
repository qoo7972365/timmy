package javax.swing;

import java.awt.Dimension;
import java.awt.Rectangle;

public interface Scrollable {
  Dimension getPreferredScrollableViewportSize();
  
  int getScrollableUnitIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2);
  
  int getScrollableBlockIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2);
  
  boolean getScrollableTracksViewportWidth();
  
  boolean getScrollableTracksViewportHeight();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/Scrollable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */