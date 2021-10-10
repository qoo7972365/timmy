package sun.java2d.xr;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import sun.font.GlyphList;
import sun.font.XRGlyphCacheEntry;
import sun.java2d.jules.TrapezoidList;
import sun.java2d.pipe.Region;

public interface XRBackend {
  void freePicture(int paramInt);
  
  void freePixmap(int paramInt);
  
  int createPixmap(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  int createPicture(int paramInt1, int paramInt2);
  
  long createGC(int paramInt);
  
  void freeGC(long paramLong);
  
  void copyArea(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
  
  void putMaskImage(int paramInt1, long paramLong, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, float paramFloat);
  
  void setGCClipRectangles(long paramLong, Region paramRegion);
  
  void GCRectangles(int paramInt, long paramLong, GrowableRectArray paramGrowableRectArray);
  
  void setClipRectangles(int paramInt, Region paramRegion);
  
  void setGCExposures(long paramLong, boolean paramBoolean);
  
  void setGCForeground(long paramLong, int paramInt);
  
  void setPictureTransform(int paramInt, AffineTransform paramAffineTransform);
  
  void setPictureRepeat(int paramInt1, int paramInt2);
  
  void setFilter(int paramInt1, int paramInt2);
  
  void renderRectangle(int paramInt1, byte paramByte, XRColor paramXRColor, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  void renderRectangles(int paramInt, byte paramByte, XRColor paramXRColor, GrowableRectArray paramGrowableRectArray);
  
  void renderComposite(byte paramByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11);
  
  int XRenderCreateGlyphSet(int paramInt);
  
  void XRenderAddGlyphs(int paramInt, GlyphList paramGlyphList, List<XRGlyphCacheEntry> paramList, byte[] paramArrayOfbyte);
  
  void XRenderFreeGlyphs(int paramInt, int[] paramArrayOfint);
  
  void XRenderCompositeText(byte paramByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, GrowableEltArray paramGrowableEltArray);
  
  int createRadialGradient(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOffloat, int[] paramArrayOfint, int paramInt);
  
  int createLinearGradient(Point2D paramPoint2D1, Point2D paramPoint2D2, float[] paramArrayOffloat, int[] paramArrayOfint, int paramInt);
  
  void setGCMode(long paramLong, boolean paramBoolean);
  
  void renderCompositeTrapezoids(byte paramByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, TrapezoidList paramTrapezoidList);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRBackend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */