package java.util.zip;

public interface Checksum {
  void update(int paramInt);
  
  void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  long getValue();
  
  void reset();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/Checksum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */