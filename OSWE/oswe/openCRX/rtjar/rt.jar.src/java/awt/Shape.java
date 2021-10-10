package java.awt;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface Shape {
  Rectangle getBounds();
  
  Rectangle2D getBounds2D();
  
  boolean contains(double paramDouble1, double paramDouble2);
  
  boolean contains(Point2D paramPoint2D);
  
  boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
  
  boolean intersects(Rectangle2D paramRectangle2D);
  
  boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
  
  boolean contains(Rectangle2D paramRectangle2D);
  
  PathIterator getPathIterator(AffineTransform paramAffineTransform);
  
  PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Shape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */