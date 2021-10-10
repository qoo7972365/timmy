package java.lang.reflect;

public interface Member {
  public static final int PUBLIC = 0;
  
  public static final int DECLARED = 1;
  
  Class<?> getDeclaringClass();
  
  String getName();
  
  int getModifiers();
  
  boolean isSynthetic();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Member.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */