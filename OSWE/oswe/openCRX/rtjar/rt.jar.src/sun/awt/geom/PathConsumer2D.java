package sun.awt.geom;

public interface PathConsumer2D {
  void moveTo(float paramFloat1, float paramFloat2);
  
  void lineTo(float paramFloat1, float paramFloat2);
  
  void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
  
  void closePath();
  
  void pathDone();
  
  long getNativeConsumer();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/geom/PathConsumer2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */