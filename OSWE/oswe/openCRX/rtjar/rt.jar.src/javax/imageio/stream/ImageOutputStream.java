package javax.imageio.stream;

import java.io.DataOutput;
import java.io.IOException;

public interface ImageOutputStream extends ImageInputStream, DataOutput {
  void write(int paramInt) throws IOException;
  
  void write(byte[] paramArrayOfbyte) throws IOException;
  
  void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void writeBoolean(boolean paramBoolean) throws IOException;
  
  void writeByte(int paramInt) throws IOException;
  
  void writeShort(int paramInt) throws IOException;
  
  void writeChar(int paramInt) throws IOException;
  
  void writeInt(int paramInt) throws IOException;
  
  void writeLong(long paramLong) throws IOException;
  
  void writeFloat(float paramFloat) throws IOException;
  
  void writeDouble(double paramDouble) throws IOException;
  
  void writeBytes(String paramString) throws IOException;
  
  void writeChars(String paramString) throws IOException;
  
  void writeUTF(String paramString) throws IOException;
  
  void writeShorts(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws IOException;
  
  void writeChars(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException;
  
  void writeInts(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException;
  
  void writeLongs(long[] paramArrayOflong, int paramInt1, int paramInt2) throws IOException;
  
  void writeFloats(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException;
  
  void writeDoubles(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws IOException;
  
  void writeBit(int paramInt) throws IOException;
  
  void writeBits(long paramLong, int paramInt) throws IOException;
  
  void flushBefore(long paramLong) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/stream/ImageOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */