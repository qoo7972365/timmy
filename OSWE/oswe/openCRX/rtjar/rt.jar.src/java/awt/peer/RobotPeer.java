package java.awt.peer;

import java.awt.Rectangle;

public interface RobotPeer {
  void mouseMove(int paramInt1, int paramInt2);
  
  void mousePress(int paramInt);
  
  void mouseRelease(int paramInt);
  
  void mouseWheel(int paramInt);
  
  void keyPress(int paramInt);
  
  void keyRelease(int paramInt);
  
  int getRGBPixel(int paramInt1, int paramInt2);
  
  int[] getRGBPixels(Rectangle paramRectangle);
  
  void dispose();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/peer/RobotPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */