package sun.java2d.cmm;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public interface ColorTransform {
  public static final int Any = -1;
  
  public static final int In = 1;
  
  public static final int Out = 2;
  
  public static final int Gamut = 3;
  
  public static final int Simulation = 4;
  
  int getNumInComponents();
  
  int getNumOutComponents();
  
  void colorConvert(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2);
  
  void colorConvert(Raster paramRaster, WritableRaster paramWritableRaster, float[] paramArrayOffloat1, float[] paramArrayOffloat2, float[] paramArrayOffloat3, float[] paramArrayOffloat4);
  
  void colorConvert(Raster paramRaster, WritableRaster paramWritableRaster);
  
  short[] colorConvert(short[] paramArrayOfshort1, short[] paramArrayOfshort2);
  
  byte[] colorConvert(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/ColorTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */