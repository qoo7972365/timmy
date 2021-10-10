package java.sql;

public interface Savepoint {
  int getSavepointId() throws SQLException;
  
  String getSavepointName() throws SQLException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/Savepoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */