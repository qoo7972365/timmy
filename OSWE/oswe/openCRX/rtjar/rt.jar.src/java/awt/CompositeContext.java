package java.awt;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public interface CompositeContext {
  void dispose();
  
  void compose(Raster paramRaster1, Raster paramRaster2, WritableRaster paramWritableRaster);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/CompositeContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */