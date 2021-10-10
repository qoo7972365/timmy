package javax.transaction.xa;

public interface Xid {
  public static final int MAXGTRIDSIZE = 64;
  
  public static final int MAXBQUALSIZE = 64;
  
  int getFormatId();
  
  byte[] getGlobalTransactionId();
  
  byte[] getBranchQualifier();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/transaction/xa/Xid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */