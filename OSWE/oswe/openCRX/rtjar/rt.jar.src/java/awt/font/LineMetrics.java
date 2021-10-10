package java.awt.font;

public abstract class LineMetrics {
  public abstract int getNumChars();
  
  public abstract float getAscent();
  
  public abstract float getDescent();
  
  public abstract float getLeading();
  
  public abstract float getHeight();
  
  public abstract int getBaselineIndex();
  
  public abstract float[] getBaselineOffsets();
  
  public abstract float getStrikethroughOffset();
  
  public abstract float getStrikethroughThickness();
  
  public abstract float getUnderlineOffset();
  
  public abstract float getUnderlineThickness();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/LineMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */