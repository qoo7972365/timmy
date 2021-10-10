package java.sql;

public interface RowId {
  boolean equals(Object paramObject);
  
  byte[] getBytes();
  
  String toString();
  
  int hashCode();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/RowId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */