package java.io;

public interface ObjectOutput extends DataOutput, AutoCloseable {
  void writeObject(Object paramObject) throws IOException;
  
  void write(int paramInt) throws IOException;
  
  void write(byte[] paramArrayOfbyte) throws IOException;
  
  void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  void flush() throws IOException;
  
  void close() throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/ObjectOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */