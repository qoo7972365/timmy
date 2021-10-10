package sun.reflect;

interface ByteVector {
  int getLength();
  
  byte get(int paramInt);
  
  void put(int paramInt, byte paramByte);
  
  void add(byte paramByte);
  
  void trim();
  
  byte[] getData();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/ByteVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */