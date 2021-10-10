package sun.security.util.math;

import java.nio.ByteBuffer;

public interface MutableIntegerModuloP extends IntegerModuloP {
  void conditionalSet(IntegerModuloP paramIntegerModuloP, int paramInt);
  
  void conditionalSwapWith(MutableIntegerModuloP paramMutableIntegerModuloP, int paramInt);
  
  MutableIntegerModuloP setValue(IntegerModuloP paramIntegerModuloP);
  
  MutableIntegerModuloP setValue(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, byte paramByte);
  
  MutableIntegerModuloP setValue(ByteBuffer paramByteBuffer, int paramInt, byte paramByte);
  
  MutableIntegerModuloP setSquare();
  
  MutableIntegerModuloP setSum(IntegerModuloP paramIntegerModuloP);
  
  MutableIntegerModuloP setDifference(IntegerModuloP paramIntegerModuloP);
  
  MutableIntegerModuloP setProduct(IntegerModuloP paramIntegerModuloP);
  
  MutableIntegerModuloP setProduct(SmallValue paramSmallValue);
  
  MutableIntegerModuloP setAdditiveInverse();
  
  MutableIntegerModuloP setReduced();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/math/MutableIntegerModuloP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */