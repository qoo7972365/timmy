package sun.security.krb5.internal;

public interface SeqNumber {
  void randInit();
  
  void init(int paramInt);
  
  int current();
  
  int next();
  
  int step();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/SeqNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */