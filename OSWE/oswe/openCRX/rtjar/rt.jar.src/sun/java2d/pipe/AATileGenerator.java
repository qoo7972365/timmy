package sun.java2d.pipe;

public interface AATileGenerator {
  int getTileWidth();
  
  int getTileHeight();
  
  int getTypicalAlpha();
  
  void nextTile();
  
  void getAlpha(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void dispose();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/AATileGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */