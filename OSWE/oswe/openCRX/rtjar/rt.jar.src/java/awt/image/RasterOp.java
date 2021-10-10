package java.awt.image;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface RasterOp {
  WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster);
  
  Rectangle2D getBounds2D(Raster paramRaster);
  
  WritableRaster createCompatibleDestRaster(Raster paramRaster);
  
  Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2);
  
  RenderingHints getRenderingHints();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/RasterOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */