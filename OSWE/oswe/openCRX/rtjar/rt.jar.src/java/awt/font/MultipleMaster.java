package java.awt.font;

import java.awt.Font;

public interface MultipleMaster {
  int getNumDesignAxes();
  
  float[] getDesignAxisRanges();
  
  float[] getDesignAxisDefaults();
  
  String[] getDesignAxisNames();
  
  Font deriveMMFont(float[] paramArrayOffloat);
  
  Font deriveMMFont(float[] paramArrayOffloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/MultipleMaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */