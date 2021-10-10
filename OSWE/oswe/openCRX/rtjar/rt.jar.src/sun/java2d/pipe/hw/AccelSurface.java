package sun.java2d.pipe.hw;

import java.awt.Rectangle;
import sun.java2d.Surface;

public interface AccelSurface extends BufferedContextProvider, Surface {
  public static final int UNDEFINED = 0;
  
  public static final int WINDOW = 1;
  
  public static final int RT_PLAIN = 2;
  
  public static final int TEXTURE = 3;
  
  public static final int FLIP_BACKBUFFER = 4;
  
  public static final int RT_TEXTURE = 5;
  
  int getType();
  
  long getNativeOps();
  
  long getNativeResource(int paramInt);
  
  void markDirty();
  
  boolean isValid();
  
  boolean isSurfaceLost();
  
  Rectangle getBounds();
  
  Rectangle getNativeBounds();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/hw/AccelSurface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */