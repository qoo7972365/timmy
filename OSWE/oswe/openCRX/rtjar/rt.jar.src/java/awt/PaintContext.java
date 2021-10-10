package java.awt;

import java.awt.image.ColorModel;
import java.awt.image.Raster;

public interface PaintContext {
  void dispose();
  
  ColorModel getColorModel();
  
  Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/PaintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */